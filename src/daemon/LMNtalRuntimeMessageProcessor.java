package daemon;

import java.io.BufferedWriter;
import java.io.IOException;
//import java.net.InetAddress;
import java.net.Socket;

import runtime.Env;
import runtime.Membrane;
import java.util.HashMap;
import runtime.LMNtalRuntimeManager;

/**
 * ランタイムが生成するオブジェクト。
 * デーモンとのコネクションに対して生成され、メッセージの受信を行う。
 * 
 * TODO LMNtalDaemonMessageProcessorと共通の処理をLMNtalNodeに移管する。
 * @author nakajima, n-kato
 */
public class LMNtalRuntimeMessageProcessor extends LMNtalNode implements Runnable {
	static boolean DEBUG = true;
	
	/** このVMで実行するLMNtalRuntimeが所属するruntimeGroupID（コンストラクタで設定）*/
	protected String rgid;
	
	/** このVMで実行するLMNtalRuntimeのruntimeid（未使用）*/
	protected String runtimeid;
	
	/** 通常のコンストラクタ */
	public LMNtalRuntimeMessageProcessor(Socket socket, String rgid) {
		super(socket);
		this.rgid = rgid;
	}
	
	/** ローカルデーモンに対して REGISTERLOCAL を発行し、返答を待つ */
	public boolean sendWaitRegisterLocal(String type) {
		// REGSITERLOCAL MASTER/SLAVE msgid rgid
		String msgid = LMNtalDaemon.makeID();
		String command = "registerlocal " + type + " " + msgid + " " + rgid + "\n";
		if (!sendMessage(command)) return false;
		return waitForResult(msgid);
	}
	
	////////////////////////////////
	// 送信用
	
	/** 指定のホストにメッセージを送信し、返答を待つ。
	 * @return 返答がOKかどうか */
	public boolean sendWait(String fqdn, String command){
		Object obj = sendWaitObject(fqdn, command);
		if (obj instanceof String) {
			return ((String)obj).equalsIgnoreCase("OK");
		}
		return false;
	}
	/** 指定のホストにメッセージを送信し、返答を待つ。
	 * @return 返答に含まれるオブジェクト */
	public Object sendWaitObject(String fqdn, String command){
		try {
			BufferedWriter out = getOutputStream();
			String msgid = LMNtalDaemon.makeID();
			out.write(msgid + " \"" + fqdn + "\" " + rgid + " " + command + "\n");
			out.flush();
			return waitForResponseObject(msgid);
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.send()");
			e.printStackTrace();
			return null;
		}
	}
	
	////////////////////////////////////////////////////////////////
	
//	/** msgid (String) -> ブロックしている Object */
//	protected HashMap blockingObjects = new HashMap();

	/** msgid (String) -> メッセージmsgidに対するresの内容 (String または byte[]) */
	HashMap messagePool = new HashMap();
	
	/** 指定したメッセージに対する返答を待ってブロックする。
	 * @return 返答が格納されたオブジェクト */
	synchronized public Object waitForResponseObject(String msgid) {
		while (messagePool.containsKey(msgid)) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		return messagePool.remove(msgid);
	}	
	/** 指定したメッセージに対する返答を待ってブロックする。
	 * @return 返答に含まれる文字列 */
	public String waitForResponseText(String msgid) {
		Object obj = waitForResponseObject(msgid);
		if (obj instanceof String) return (String)obj;
		return "fail";
	}
	/** 指定したメッセージに対する結果を待ってブロックする。
	 * @return 返答がOKかどうか */
	public boolean waitForResult(String msgid) {
		return waitForResponseText(msgid).equalsIgnoreCase("ok");
	}
	
	////////////////////////////////////////////////////////////////
	// todo Cacheへ移管する

//	/*
//	 * この計算機にある膜のグローバルなIDを管理
//	 */
//	//static HashMap localMemTable = new HashMap();

	////////////////////////////////////////////////////////////////
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		if (DEBUG) System.out.println("LMNtalDaemonMessageProcessor.run()");
		String input;
		while (true) {
			try {
				input = readLine();
			} catch (IOException e) {
				System.out.println("ERROR:このスレッドには書けません!");
				e.printStackTrace();
				break;
			}
			if (input == null) {
				System.out.println("（　´∀｀）＜　inputがぬる");
				break;
			}
			if (DEBUG) System.out.println("in.readLine(): " + input);

			/* メッセージ:
			 *   RES msgid 返答
			 *   DUMPHASH
			 *   CMD msgid fqdn rgid コマンド
			 *     - fqdn が自分宛 
			 *     - fqdn が他人宛
			 * 返答:
			 *   OK | FAIL | UNCHANGED | RAW bytes \n data
			 */
			String[] parsedInput = input.split(" ", 4);

			if (parsedInput[0].equalsIgnoreCase("RES")) {
				// RES msgid (OK | FAIL | UNCHANGED | RAW bytes \n data)
				String msgid = parsedInput[1];
				String content = parsedInput[2];
				if (content.equalsIgnoreCase("RAW")) {
					try {
						int bytes = Integer.parseInt(parsedInput[3]);
						byte[] data = readBytes(bytes);
						readLine();	// 改行文字を読み飛ばす
						messagePool.put(msgid, data);
					} catch (Exception e) {
						messagePool.put(msgid, "FAIL");
						continue;
					}
				}
				else messagePool.put(msgid, content);
				
//				Object suspended = blockingObjects.remove(msgid);
//				if (suspended == null) {
//					System.out.println(
//						"ERROR: no objects waiting for message id = " + msgid);
//					continue;
//				}
				synchronized(this) {
					notifyAll();
				}
				continue;
			} else if (parsedInput[0].equalsIgnoreCase("REGISTERLOCAL")) {
				System.out.println("invalid message: registerlocal");
				continue;
			} else if (parsedInput[0].equalsIgnoreCase("DUMPHASH")) {
				// DUMPHASH
				LMNtalDaemon.dumpHashMap();
				continue;
			} else if (parsedInput[0].equalsIgnoreCase("CMD")) {
				// CMD msgid fqdn rgid コマンド
				String msgid = parsedInput[0];
				// 自分自身宛なので、自分自身で処理する
				
				/* コマンド:
				 *   BEGIN \n ボディ命令... END -> OK
				 *   CONNECT        dst_nodedesc src_nodedesc -> OK | FAIL
				 *   TERMINATE -> OK
				 *   REQUIRERULESET globalrulesetid -> RAW bytes \n data | FAIL
				 *   LOCK           globalmemid prio -> UNCHANGED | RAW bytes \n data | FAIL
				 *   BLOCKINGLOCK   globalmemid prio -> UNCHANGED | RAW bytes \n data
				 *   RECURSIVELOCK  globalmemid -> OK | FAIL
				 *   UNLOCK         globalmemid -> OK | FAIL
				 *   BLOCKINGUNLOCK globalmemid -> OK | FAIL
				 *   ASYNCLOCK      globalmemid -> OK | FAIL
				 *   ASYNCUNLOCK    globalmemid -> OK | FAIL
				 */

				String[] command = parsedInput[3].split(" ", 3);
				
				if (command[0].equalsIgnoreCase("TERMINATE")) {
					Env.theRuntime.terminate();
					LMNtalRuntimeManager.terminateAllNeighbors();
					respondAsOK(msgid);
					return;
				} else if (command[0].equalsIgnoreCase("CONNECT")) {
					// CONNECT dst_nodedesc src_nodedesc
					String nodedesc = command[2];
					LMNtalRuntimeManager.connectedFromRemoteRuntime(nodedesc);
					respondAsOK(msgid);
					continue;
				} else {
					if (command[0].equalsIgnoreCase("BEGIN")) {
						// endが来るまで処理
						try {
							while(true){
								String inputline = readLine();
								if (inputline == null) break;
								if (inputline.equalsIgnoreCase("END")) {
									break;
								}
								// doSomething(inputline);
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					else onCmd(msgid, command);
					continue;
				}
				//respondAsFail(msgid);
			}
		}
	}
	
	void onCmd(String msgid, String[] command) {
		//
		if (command[0].equalsIgnoreCase("begin")) {
			onBegin();
			return;
		}
		else if (command[0].equalsIgnoreCase("LOCK")
			   || command[0].equalsIgnoreCase("BLOCKINGLOCK")
			   || command[0].equalsIgnoreCase("ASYNCLOCK")) {
			// LOCK globalmemid
			// ローカルの膜をロック
			Membrane mem = IDConverter.lookupLocalMembrane(command[1]);
			if (mem != null) {
				boolean result = false;
				if (command[0].equalsIgnoreCase("LOCK"))         result = mem.lock();
				if (command[0].equalsIgnoreCase("BLOCKINGLOCK")) result = mem.blockingLock();
				if (command[0].equalsIgnoreCase("ASYNCLOCK"))    result = mem.asyncLock();
				if (result) { // ロック取得成功
					if (true) { // キャッシュ再送信チェック
						byte[] data = mem.cache();
						// todo 文字列結合でいいのか調べる
						respond(msgid, "RAW " + data.length + "\n" + data);
					}
					else {
						respond(msgid, "UNCHANGED");
					}
					return;
				}
			}
		} else if (command[0].equalsIgnoreCase("UNLOCK")
				 || command[0].equalsIgnoreCase("RECURSIVEUNLOCK")) {
			// UNLOCK          globalmemid # ローカルの膜をロック解放			
			// RECURSIVEUNLOCK globalmemid # ローカルの膜の全世界の子孫膜を再帰的にロック解放
			Membrane mem = IDConverter.lookupLocalMembrane(command[1]);
			if (mem != null) {
				if (command[0].equalsIgnoreCase("UNLOCK"))          mem.unlock();
				if (command[0].equalsIgnoreCase("RECURSIVEUNLOCK")) mem.recursiveUnlock();
				respondAsOK(msgid);
				return;
			}
		} else if (command[0].equalsIgnoreCase("RECURSIVELOCK")) {
			// RECURSIVELOCK globalmemid
			// ロックしたローカルの膜の全世界の子孫膜を再帰的にロック（キャッシュは更新しない）
			Membrane mem = IDConverter.lookupLocalMembrane(command[1]);
			if (mem != null) {
				mem.recursiveLock();
				respondAsOK(msgid);
				return;
			}
		}
		respondAsFail(msgid);
	}

	void onBegin() {
		/*
		 * ここで処理される命令： (最新版はRemoteMembraneクラス参照。)
		 * 
		 * end
		 * 
		 * ルールの操作
		 *  clearrules  dstmem
		 *  loadruleset  dstmem ruleset
		 * 
		 * アトムの操作 
		 * newatom srcmem atomid 
		 * alteratomfunctor srcmem atomid func.getName() 
		 * removeatom
		 * srcmem atomid 
		 * enqueueatom srcmem atomid
		 * 
		 * 子膜の操作
		 *  newmem srcmem dstmem 
		 * removemem srcmem
		 * parentmem
		 * 
		 * リンクの操作 
		 * newlink mem atomid1 pos1 atomid2 pos2
		 * relinkatomargs mem atomid1 pos1 atomid2 pos2
		 * unifyatomargs mem atomid1 pos1 atomid2 pos2
		 * 
		 * 膜自身や移動に関する操作 
		 * movecells dstmem srcmem 
		 * moveto srcmem dstmem
		 * 
		 * 仮称
		 *  requireruleset globalRulesetID 
		 *  newroot mem
		 */
		
		String input;
		String srcmem, dstmem, parentmem, atom1, atom2, pos1, pos2, ruleset, func;

		String[] commandInsideBegin = new String[5]; //RemoteMembrane.send()の引数の個数を参照せよ
		BufferedWriter out = getOutputStream();

		beginEndLoop:while(true){
			try {
				input = readLine();
				commandInsideBegin = input.split(" ",5);
		
				//TODO ここで命令を書くのではなくて、Instruction.javaの命令番号を引いてくる。
				//そして変換表もひける。
				//案: new InstructionListをする。
				
				//案：BEGINからENDまで出てくる引数の中でNEWがつかないものを動的に仮引数リストにいれてやると
				//InterpretedRulsetのコードが使えるので、そうする？
				
				if(commandInsideBegin[0].equalsIgnoreCase("end")){
					//糸冬
					return;
				} else if (commandInsideBegin[0].equalsIgnoreCase("clearrules")){
					dstmem = commandInsideBegin[1];
					
					//dstmem.clearRules()を呼ぶ
					//(IDConverter.getMem(dstmem)).clearRules();
		
					out.write("not implemented yet\n");
					out.flush();
		
					continue beginEndLoop;
				} else if (commandInsideBegin[0].equalsIgnoreCase("loadruleset")){
					dstmem = commandInsideBegin[1];
					ruleset = commandInsideBegin[2];
		
					//mem.loadRulesest(Ruleset)を呼ぶ
					//(IDConverter.getMem(dstmem)).loadRuleset(Env.theRuntime.getRuleset(ruleset));
															
					out.write("not implemented yet\n");
					out.flush();
		
					continue beginEndLoop;
				} else if (commandInsideBegin[0].equalsIgnoreCase("newatom")){
					srcmem = commandInsideBegin[1]; //グローバル膜ID
					atom1 = commandInsideBegin[2]; //NEW_1とか
					
					//NEW_1をatomidに変換
					//キャッシュからatomidに対応するAtomオブジェクトをもってくる
		
					//srcmem.addAtom(atom1)呼び出し
					//(IDConverter.getMem(srcmem)).addAtom(Cache.getAtom(atom1));
					
					out.write("not implemented yet\n");
					out.flush();
		
					continue beginEndLoop;
				} else if (commandInsideBegin[0].equalsIgnoreCase("alteratomfunctor")){
					srcmem = commandInsideBegin[1];
					atom1 = commandInsideBegin[2];
					func = commandInsideBegin[3];
					
					//(IDConverter.getMem(srcmem)).alterAtomFunctor(Cache.getAtom(atom1),Cache.getFunctor(func));
					
					
					out.write("not implemented yet\n");
					out.flush();
		
					continue beginEndLoop;
				} else if (commandInsideBegin[0].equalsIgnoreCase("removeatom")){
					srcmem = commandInsideBegin[1];
					atom1 = commandInsideBegin[2];
					
					//(IDConverter.getMem(srcmem)).removeAtom(Cache.getAtom(atom1));
					
					out.write("not implemented yet\n");
					out.flush();
		
					continue beginEndLoop;
				} else if (commandInsideBegin[0].equalsIgnoreCase("enqueueatom")){
					srcmem = commandInsideBegin[1];
					atom1 = commandInsideBegin[2];
					
					//(IDConverter.getMem(srcmem)).enqueueAtom(Cache.getAtom(atom1));
					
					out.write("not implemented yet\n");
					out.flush();
		
					continue beginEndLoop;
				} else if (commandInsideBegin[0].equalsIgnoreCase("newmem")){
					srcmem = commandInsideBegin[1];
					dstmem = commandInsideBegin[2];
					
					//realMem =
					// IDConverter.getMem(srcmem).newMem();
					//if(IDConverter.registerMem(dstmem,realMem)){
					//	continue beginEndLoop;
					//} else {
					//     //todo: 失敗時になにかする
					//     //System.out.println("failed to
					// register new membrane");
					//     continue beginEndLoop;
					//}
					
					out.write("not implemented yet\n");
					out.flush();
		
					continue beginEndLoop;
				} else if (commandInsideBegin[0].equalsIgnoreCase("removemem")){
					srcmem = commandInsideBegin[1];
					parentmem = commandInsideBegin[2];
					
					//IDConverter.getMem(parentmem).removeMem(IDConverter.getMem(srcmem));
					
					out.write("not implemented yet\n");
					out.flush();
		
					continue beginEndLoop;
				} else if (commandInsideBegin[0].equalsIgnoreCase("newroot")){
					srcmem = commandInsideBegin[1];
					
					//TODO 実装
					
					//create new LMNtalLocalRuntime
					
					//create new Task & root membrane
					//AbstractTask task =
					// (IDConverter.getMem(srcmem)).task.runtime.newTask(this);
					
					//todo この後どうするのかな
					
					out.write("not implemented yet\n");
					out.flush();
		
					continue beginEndLoop;
				} else if (commandInsideBegin[0].equalsIgnoreCase("newlink")){
					srcmem = commandInsideBegin[1];
					atom1 = commandInsideBegin[2];
					pos1  = commandInsideBegin[3];
					atom2 = commandInsideBegin[4];
					pos2  = commandInsideBegin[5];
					
					//Cache.getAtom(atom1).mem.newLink(Cache.getAtom(atom1),pos1,Cache.getAtom(atom2),pos2);
					
					out.write("not implemented yet\n");
					out.flush();
		
					continue beginEndLoop;
				} else if (commandInsideBegin[0].equalsIgnoreCase("relinkatomargs")){
					srcmem = commandInsideBegin[1];
					atom1 = commandInsideBegin[2];
					pos1  = commandInsideBegin[3];
					atom2 = commandInsideBegin[4];
					pos2  = commandInsideBegin[5];
					
					//Cache.getAtom(atom1).mem.relinkAtomArgs(Cache.getAtom(atom1),pos1,Cache.getAtom(atom2),pos2);
					
					out.write("not implemented yet\n");
					out.flush();
		
					continue beginEndLoop;
				} else if (commandInsideBegin[0].equalsIgnoreCase("unifyatomargs")){
					srcmem = commandInsideBegin[1];
					atom1 = commandInsideBegin[2];
					pos1  = commandInsideBegin[3];
					atom2 = commandInsideBegin[4];
					pos2  = commandInsideBegin[5];
					
					//IDConverter.getMem(srcmem).uniftyAtomArgs(Cache.getAtom(atom1),pos1,Cache.getAtom(atom2),pos2);
					
					out.write("not implemented yet\n");
					out.flush();
		
					continue beginEndLoop;
				} else if (commandInsideBegin[0].equalsIgnoreCase("movecells")){
					dstmem = commandInsideBegin[1];
					srcmem = commandInsideBegin[2];
					
					//Membrane dstmemobj =
					// IDConverter.getMem(dstmem);
					//Membrane srcmemobj =
					// IDConverter.getMem(srcmem);
		//
		//										if (dstmemobj == srcmemobj) continue beginEndLoop;
		//
		//										dstmemobj.mems.addAll(srcmemobj.mems);
		//										Iterator it = srcmemobj.atomIterator();
		//										while (it.hasNext()) {
		//											dstmemobj.addAtom((Atom)it.next());
		//										}
		//										it = srcmemobj.memIterator();
		//										while (it.hasNext()) {
		//											((Membrane)it.next()).parent = dstmemobj;
		//										}
		//										if (srcmemobj.task != dstmemobj.task) {
		//											srcmemobj.setTask(dstmemobj.task);
		//										}
					
					out.write("not implemented yet\n");
					out.flush();
		
					continue beginEndLoop;
				} else if (commandInsideBegin[0].equalsIgnoreCase("moveto")){
					srcmem = commandInsideBegin[1];
					dstmem = commandInsideBegin[2];
					
					//IDConverter.getMem(srcmem).moveTo(IDConverter.getMem(dstmem));
					
					out.write("not implemented yet\n");
					out.flush();
		
					continue beginEndLoop;
				//} else if
				// (commandInsideBegin[0].equalsIgnoreCase("requireruleset")){
					//TODO 実装
				} else {
					//未知の命令
					out.write("not implemented yet\n");
					out.flush();
					continue beginEndLoop;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				//continue;
				break;
			}
		}
	}

}