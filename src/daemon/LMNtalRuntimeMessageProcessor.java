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

	////////////////////////////////////////////////////////////////
	
//	/** msgid (String) -> ブロックしている Object */
//	protected HashMap blockingObjects = new HashMap();

	/** msgid (String) -> メッセージmsgidに対するresの内容 (String) */
	HashMap messagePool = new HashMap();
	
	/** 指定したメッセージに対する返答を待ってブロックする。*/
	synchronized public String waitForResponseText(String msgid) {
		while (!messagePool.containsKey(msgid)) {
			try {
				wait();
			}
			catch (InterruptedException e) {}
		}
		return (String)messagePool.remove(msgid);
	}
	/** 指定したメッセージに対する結果を待ってブロックする。*/
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

		String input = "";

		outsideloop:while (true) {
			try {
				input = in.readLine();
			} catch (IOException e) {
				System.out.println("ERROR:このスレッドには書けません!");
				e.printStackTrace();
				break;
			}

			if (DEBUG)System.out.println("in.readLine(): " + input);

			if (input == null) {
				System.out.println("（　´∀｀）＜　inputがぬる");
				break;
			}

			/*
			 * コマンドからはじまるメッセージを処理。
			 * 
			 * ここで処理される命令一覧。これ以外のは下スクロールしてね
			 * 
			 * res msgid メッセージ本文
			 * registerlocal rgid
			 * dumphash
			 *  
			 */
			String msgid;
			String rgid;
			String fqdn;
			boolean result;
			String[] parsedInput = input.split(" ", 4);

			if (parsedInput[0].equalsIgnoreCase("res")) {
				//res msgid 結果
				msgid = parsedInput[1];
				String content = parsedInput[2];
				messagePool.put(msgid, content);
				
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
			} else if (parsedInput[0].equalsIgnoreCase("registerlocal")) {
				System.out.println("invalid message: registerlocal");
				continue;
			} else if (parsedInput[0].equalsIgnoreCase("dumphash")) {
				//dumphash
				LMNtalDaemon.dumpHashMap();
				continue;
				
				/* コマンドからはじまる文字列の処理：ここまで */
				
			} else {
				// todo 耐故障性や拡張性のため、msgid の前に共通コマンド名（例えばcmd）を書いた方がよい
				/* msgid "fqdn" rgid メッセージ の処理 */
			
				//msgidからつづく命令列とみなす
				msgid = parsedInput[0];
				fqdn = (parsedInput[1].split("\"", 3))[1];
				rgid = parsedInput[2];

				// 自分自身宛なので、自分自身で処理する

				/*
				 * ここで処理される命令一覧
				 * 
				 *  CONNECT dst_nodedesc src_nodedesc
				 *  BEGIN
				 *  beginrule
				 * 
				 * LOCK           globalmemid -> UNCHANGED | CHANGED bytes content | FAIL
				 * BLOCKINGLOCK   globalmemid -> UNCHANGED | CHANGED bytes content
				 * asynclock
				 * recursivelock
				 * 
				 * 
				 * UNLOCK         globalmemid -> OK | FAIL
				 * BLOCKINGUNLOCK globalmemid -> OK | FAIL
				 * asyncunlock
				 * recursiveunlock
				 * 
				 * terminate
				 *  
				 */

				String[] command = parsedInput[3].split(" ", 3);
				//String srcmem, dstmem, parentmem, atom1, atom2, pos1, pos2, ruleset, func;
				Membrane realMem;

				if (command[0].equalsIgnoreCase("connect")) {
					// connect "my_fqdn" "remote_fqdn"
					String nodedesc = command[2];
					LMNtalRuntimeManager.connectedFromRemoteRuntime(nodedesc);
					respondAsOK(msgid);
					continue;
				} else {
					if (command[0].equalsIgnoreCase("begin")) {
						// endが来るまで処理
						try {
							while(true){
								String inputline = in.readLine();
								if (inputline == null) break;
								if (inputline.equalsIgnoreCase("end")) {
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
		else if (command[0].equalsIgnoreCase("lock")) {
									
			//ロック対象膜をロック
			//realMem = IDConverter.getMem(command[1]);
			//if(realMem.lock()){
			////ロック成功
			//
			////キャッシュ更新チェック
			//   キャッシュオブジェクト.update();
			//
			//
									
			//////更新されていたらキャッシュを返信する
	
			//////更新されていなかったら「更新されていませんメッセージ」
									
			//ロック失敗
			//out.write("res " + msgid.toString() + "
			// fail\n");
			//out.flush();
									
			runtime_errorNotImplemented();	
			return;
		} else if (command[0].equalsIgnoreCase("blockinglock")) {
			//IDConverter.getMem(command[1]).blockingLock();
	
			//キャッシュ更新
	
			runtime_errorNotImplemented();	
			return;
		} else if (command[0].equalsIgnoreCase("asynclock")) {
	
			//IDConverter.getMem(command[1]).asyncLock();
	
			//キャッシュ更新
	
			runtime_errorNotImplemented();	
			return;
		} else if (command[0].equalsIgnoreCase("unlock")) {
									
			//if(IDConverter.getMem(command[1]).unlock()){
				//unlock成功
				//continue;
			//} else {
				//不成功
				//System.out.println("UNLOCK failed");
				//out.write("res " + msgid.toString() + "
				// fail\n");
				//out.flush();
				//continue;
			//}
									
			runtime_errorNotImplemented();	
			return;
		} else if (command[0].equalsIgnoreCase("blockingunlock")) {
			//IDConverter.getMem(command[1]).blockingUnlock();
									
			runtime_errorNotImplemented();	
			return;
		} else if (command[0].equalsIgnoreCase("asyncunlock")) {
	
			//if(IDConverter.getMem(command[1]).asyncUnlock()){
				//unlock成功
				//continue;
			//} else {
				//不成功
				//System.out.println("ASYNCUNLOCK failed");
				//out.write("res " + msgid.toString() + "
				// fail\n");
				//out.flush();
				//continue;
			//}
	
			runtime_errorNotImplemented();	
			return;
		} else if (command[0].equalsIgnoreCase("recursivelock")) {
			//IDConverter.getMem(command[1]).recursiveLock();
	
			//キャッシュ更新
																	
			runtime_errorNotImplemented();	
			return;
		} else if (command[0].equalsIgnoreCase("recursiveunlock")) {
									
			//IDConverter.getMem(command[1]).recursiveUnlock();
			runtime_errorNotImplemented();	
			return;
		} else if (command[0].equalsIgnoreCase("terminate")) {
			//「terminateだけ変」(by n-kato)
			//
			//やるべきことはEnv.theRuntimeのterminate
			//でも呼べないからどうしよう？
			//LocalLMNtalRuntime.terminate();
			Env.theRuntime.terminate(); //TODO
															  // これでいいのかな
	
			//out.write("not implemented yet\n");
			//out.flush();
			return;
		} else {
			//未知のコマンド or それ以外の何か
			runtime_respondAsFail(out,msgid);
			return;
		}
	}
	void runtime_errorNotImplemented() {
//		out.write("not implemented yet\n");
//		out.flush();
	}
	void runtime_respondAsFail(BufferedWriter out, String msgid) {
//		LMNtalDaemon.respondAsFail(out,msgid);
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
	
		beginEndLoop:while(true){
			try {
				input = in.readLine();
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
	//
	/** 指定のホストに送信する。*/
	public boolean sendWait(String fqdn, String command){
		try {
			String msgid = LMNtalDaemon.makeID();
			out.write(msgid + " \"" + fqdn + "\" " + rgid + " " + command + "\n");
			out.flush();
			return waitForResult(msgid);
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalDaemon.send()");
			e.printStackTrace();
			return false;
		}
	}
}