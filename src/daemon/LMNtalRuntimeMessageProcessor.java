package daemon;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import runtime.AbstractLMNtalRuntime;
import runtime.AbstractMembrane;
import runtime.Atom;
import runtime.Functor;
import runtime.LMNtalRuntimeManager;
import runtime.Membrane;
import runtime.RemoteLMNtalRuntime;
import runtime.RemoteMembrane;
import runtime.RemoteTask;
import runtime.Ruleset;
import util.HybridOutputStream;

/**
 * ランタイムが生成するオブジェクト。
 * デーモンとのコネクションに対して生成され、メッセージの受信を行う。
 * 
 * todo LMNtalDaemonMessageProcessorと共通の処理をLMNtalNodeに移管する。
 * @author nakajima, n-kato
 */
public class LMNtalRuntimeMessageProcessor extends LMNtalNode implements Runnable {
	boolean DEBUG = true;
	
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
		// REGSITERLOCAL (MASTER|SLAVE) msgid rgid
		String msgid = LMNtalDaemon.makeID();
		String command = "registerlocal " + type + " " + msgid + " " + rgid + "\n";
		if (!sendMessage(command)) return false;
		return waitForResult(msgid);
	}
	
	/** ローカルデーモンに対してUN REGISTERLOCAL を発行する。（現状のUNREGISTERLOCALの実装では返事はこない）*/
	public void sendWaitUnregisterLocal() {
		// UNREGSITERLOCAL rgid
		String command = "UNREGISTERLOCAL " + rgid + "\n";
		sendMessage(command);
	}
	
	////////////////////////////////
	// 送信用
	
	/** 指定のホストにメッセージを送信し、返答を待つ。
	 * @return 返答がOKかどうか */
	public boolean sendWait(String fqdn, String command){
		return sendWaitText(fqdn, command).equalsIgnoreCase("OK");
	}
	/** 指定のホストにメッセージを送信し、返答を待つ。
	 * @return 返答に含まれる文字列 */
	public String sendWaitText(String fqdn, String command){
		//if(DEBUG)System.out.println("LMNtalRuntimeMessageProcessor.sendWaitText()");
		Object obj = sendWaitObject(fqdn, command);
		if (obj instanceof String) {
			return (String)obj;
		}
		return "FAIL";
	}
	/** 指定のホストにメッセージを送信し、返答を待つ。
	 * @return 返答に含まれるオブジェクト */
	public Object sendWaitObject(String fqdn, String command){
		try {
			HybridOutputStream out = getOutputStream();
			String msgid = LMNtalDaemon.makeID();
			out.write("CMD " + msgid + " \"" + fqdn + "\" " + rgid + " " + command + "\n");
			out.flush();
			return waitForResponseObject(msgid);
		} catch (IOException e) {
			System.out.println("ERROR in LMNtalRuntimeMessageProcessor.sendWaitObject(): ");
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
		//if(DEBUG)System.out.println("LMNtalRuntimeMessageProcessor.waitForResponseObject(" + msgid + ")");
		while (!messagePool.containsKey(msgid)) { 
			try {
				//if(DEBUG)System.out.println("LMNtalRuntimeMessageProcessor.waitForResponseObject(): waiting...");
				wait(); 
			} catch (InterruptedException e) {	}
		}
		//if(DEBUG)System.out.println("LMNtalRuntimeMessageProcessor.waitForResponseObject(): loop quit");
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
		if (DEBUG) System.out.println("LMNtalRuntimeMessageProcessor.run()");
		String input;
		while (true) {
			//ソケットを閉じるとき、この段階で閉じらていれるよりreadLine()まで行って待っている場合のほうが考えられる。
/*			if(isSocketClosed()){
				return;
			}*/

			try {
				input = readLine();
			} catch (IOException e) {
				//todo disconnectFromDaemon()する時に、必ずこの例外が発生するのを防ぐ
				//済 2004-08-24 nakajima

				if(true){//TODO ちゃんと判定する
					System.out.println("program finished successfully");
					break;
				} else {
					//System.out.println("LMNtalRuntimeMessageProcessor.run(): ERROR:このスレッドには書けません!");
					e.printStackTrace(); 
				}
			
				break;
			}
			if (input == null) {
				System.out.println("LMNtalRuntimeMessageProcessor.run(): （　´∀｀）＜　inputがぬる");
				break;
			}
			if (DEBUG) System.out.println("LMNtalRuntimeMessageProcessor.run(): in.readLine(): " + input);

			/* メッセージ:
			 *   RES msgid 返答
			 *   DUMPHASH
			 *   CMD msgid fqdn rgid コマンド
			 *     - fqdn が自分宛 
			 *     - fqdn が他人宛
			 * 返答:
			 *   OK | FAIL | UNCHANGED | RAW bytes \n data
			 */
			String[] parsedInput = input.split(" ", 5);

			if (parsedInput[0].equalsIgnoreCase("RES")) {
				// RES msgid (OK | FAIL | UNCHANGED | RAW bytes \n data)
				String msgid = parsedInput[1];
				String content = parsedInput[2];
				Object res;
				if (content.equalsIgnoreCase("RAW")) {
					try {
						//バイト数指定や、末尾の改行記号はreadBytes内で処理
						res = readBytes();
					} catch (Exception e) {
						res = "FAIL";
					}
				}
				else res = content;
				synchronized(this) {	
					messagePool.put(msgid, res);
					//if (DEBUG) System.out.println(res.toString());
					//if (DEBUG) System.out.println(messagePool.toString());
					
//					Object suspended = blockingObjects.remove(msgid);
//					if (suspended == null) {
//						System.out.println(
//							"ERROR: no objects waiting for message id = " + msgid);
//						continue;
//					}
					
					//if (DEBUG) System.out.println("notifyALL");
					notifyAll();
				}
				continue;
			} else if (parsedInput[0].equalsIgnoreCase("DUMPHASH")) {
				// DUMPHASH
				LMNtalDaemon.dumpHashMap();
				continue;
			} else if (parsedInput[0].equalsIgnoreCase("CMD")) {
				// CMD msgid fqdn rgid コマンド
				String msgid = parsedInput[1];
				// 自分自身宛なので、自分自身で処理する
				
				/* コマンド:
				 *   BEGIN \n ボディ命令... END -> OK
				 *   CONNECT        dst_nodedesc src_nodedesc -> OK | FAIL
				 *   TERMINATE -> OK
				 *  DISCONNECTRUNTIME
				 *   REQUIRERULESET globalrulesetid  ->             RAW bytes \n data | FAIL
				 *   LOCK           globalmemid prio -> UNCHANGED | RAW bytes \n data | FAIL
				 *   BLOCKINGLOCK   globalmemid prio -> UNCHANGED | RAW bytes \n data | FAIL
				 *   ASYNCLOCK      globalmemid      -> OK | FAIL
				 *   RECURSIVELOCK  globalmemid      -> OK | FAIL
				 */

				String[] command = parsedInput[4].split(" ", 3);
				
				if (command[0].equalsIgnoreCase("TERMINATE")) {  
					// TERMINATE
					
					Thread t1 = new Thread(new TerminateProcessor(msgid, this));
					t1.start();
										
					continue;
				} else if (command[0].equalsIgnoreCase("DISCONNECTRUNTIME")) { 
					//DISCONNECTRUNTIME
					
					Thread t1 = new Thread(new DisconnectProcessor());
					t1.start();
					
				} else if (command[0].equalsIgnoreCase("CONNECT")) {
					// CONNECT dst_nodedesc src_nodedesc
					String nodedesc = command[2];
					LMNtalRuntimeManager.connectedFromRemoteRuntime(nodedesc);
					respondAsOK(msgid);
					continue;
				} else if (command[0].equalsIgnoreCase("BEGIN")) {
					// BEGIN \n ボディ命令... END
					try {
						LinkedList insts = new LinkedList();
						while(true){
							String inputline = readLine();
							if (inputline == null) break;
							if (inputline.equalsIgnoreCase("END")) {
								break;
							}
							insts.add(inputline);
						}
						InstructionBlockProcessor ibp;
						ibp = new InstructionBlockProcessor(this, msgid, insts);						
						new Thread(ibp,"ibp").start();
					} catch (IOException e) {
						e.printStackTrace();
						respondAsFail(msgid);
					}
				} else if (command[0].equalsIgnoreCase("REQUIRERULESET")) {
					// REQUIRERULESET globalrulesetid
					Ruleset rs = IDConverter.lookupRuleset(command[1]);
					if (rs != null) {
						byte[] data = rs.serialize();
						respondRawData(msgid,data);
						continue;
					}
					respondAsFail(msgid);
				} else onCmd(msgid, command);
			} else {
				//どれにも合致しない時
				System.out.println("LMNtalRuntimeMessageProcessor.run(): invalid message: " + parsedInput[0]);
				continue;
			}
		}
	}
	
	void onCmd(String msgid, String[] command) {
		AbstractMembrane obj = IDConverter.lookupGlobalMembrane(command[1]); //TODO globalidを作成する時に間違ったIDを作成している？  or 登録されていない？（こっちを先に調べる）
		if (!(obj instanceof Membrane)) {
			respondAsFail(msgid);
			if(true)System.out.println("LMNtalRuntimeMessageProcessor.onCmd(" + command[1] + " is not found!)"); //TODO Env.debug
			return;
		}
		Membrane mem = (Membrane)obj;
		if(true)System.out.println("LMNtalRuntimeMessageProcessor.onCmd(" + command[1] + " is found.)"); //TODO Env.debug
		
		if (command[0].equalsIgnoreCase("LOCK")
		 || command[0].equalsIgnoreCase("BLOCKINGLOCK")
		 || command[0].equalsIgnoreCase("ASYNCLOCK")) {
			Thread t1 = new Thread(new LockProcessor(command[0], this, mem, msgid));
			t1.start();
			return;
		} else if (command[0].equalsIgnoreCase("RECURSIVELOCK")) { 
			// RECURSIVELOCK globalmemid
			// ロックしたローカルの膜の全世界の子孫膜を再帰的にロック（キャッシュは更新しない）
			Thread t1 = new Thread(new RecursiveLockProcessor(command[0],this,mem,msgid));
			t1.start();
			return;
		}
	}
}

class InstructionBlockProcessor implements Runnable {
	LMNtalRuntimeMessageProcessor remote;
//	String fqdn;
	String msgid;
	LinkedList insts;
	
	static boolean DEBUG = true;  //todo Env.debugを使う
	
	InstructionBlockProcessor(LMNtalRuntimeMessageProcessor remote, 
//		String fqdn, 
		String msgid, LinkedList insts) {
		this.remote = remote;
//		this.fqdn = fqdn;
		this.msgid = msgid;
		this.insts = insts;
	}
	//
	
	/** リモート転送表（remoteの代用）: RemoteTask -> RemoteTask */
	HashMap remoteTable = new HashMap();
	
	////////////////////////////////////////////////////////////////	

	/** グローバル膜ID (String) -> AbstractMembrane */
	HashMap newMemTable = new HashMap();
//	/** ローカルアトムIDまたはNEW_ (String) -> Atom */
//	HashMap newAtomTable = new HashMap();

	/** 指定された膜を表に登録する */
	public void registerNewMembrane(String globalMemID, AbstractMembrane mem) {
		newMemTable.put(globalMemID, mem);
		
	}
	/** グローバル膜IDまたはNEW_に対応する膜を探す
	 * @return Membrane（見つからなかった場合はnull）*/
	public AbstractMembrane lookupMembrane(String memid) {
		if(DEBUG)System.out.println("LMNtalRuntimeMessageProcessor.lookupMembrane(" + memid + ")");
		
		Object obj = newMemTable.get(memid);
		if (obj instanceof AbstractMembrane) return (AbstractMembrane)obj;
		return IDConverter.lookupGlobalMembrane(memid);
	}

//	/** 指定されたアトムを表に登録する */
//	public void registerNewAtom(String atomID, Atom atom) {
//		newAtomTable.put(atomID, atom);
//	}
//	/** アトムIDに対応するアトムを探す
//	 * @param mem 所属膜
//	 * @return Atom（見つからなかった場合はnull）*/
//	public Atom lookupAtom(AbstractMembrane mem, String atomid) {
//		Object obj = newAtomTable.get(atomid);
//		if (obj instanceof Atom) return (Atom)obj;
//		if (mem instanceof Membrane) {
//			return (Atom)((Membrane)mem).lookupAtom(atomid);
//		}
//		return null;
//	}
	
	//
	public void run() {
		/* ボディ命令:
		 * 
		 * [1] ルールの操作
		 *   CLEARRULES       dstmemid
		 *   LOADRULESET      dstmemid rulesetid
		 * 
		 * [2] アトムの操作
		 *   NEWATOM          srcmemid NEW_atomid func
		 *   NEWFREELINK      srcmemid NEW_atomid        // 現在は NEWATOM - - $inside と同じ
		 *   ALTERATOMFUNCTOR srcmemid atomid func
		 *   ENQUEUEATOM      srcmemid atomid
		 *   REMOVEATOM       srcmemid atomid
		 * 
		 * [3] 子膜の操作
		 *   NEWMEM           srcmemid NEW_memid
		 *   REMOVEMEM        srcmemid
		 *   NEWROOT          parentmemid NEW_memid nodedesc
		 * 
		 * [4] リンクの操作 
		 *   NEWLINK          srcmemid atomid1 pos1 atomid2 pos2
		 *   RELINKATOMARGS   srcmemid atomid1 pos1 atomid2 pos2
		 *   UNIFYATOMARGS    srcmemid atomid1 pos1 atomid2 pos2
		 * 
		 * [5] 膜自身や移動に関する操作 
		 *   ACTIVATE         srcmemid
		 *   MOVECELLSFROM    dstmemid srcmemid
		 *   MOVETO           srcmemid dstmemid
		 * 
		 * [6] ロック解放操作
		 *   UNLOCK           srcmemid
		 *   ASYNCUNLOCK      srcmemid
		 *   RECURSIVEUNLOCK  srcmemid
		 *  QUIETUNLOCK srcmemid
		 */
		
		String result = "";	// 新しい子膜およびinside_proxyに対するID代入列を積み込む
		Iterator it = insts.iterator();
		while (it.hasNext()) {
			String input = (String)it.next();
			try {
				String[] command = input.split(" ",6); // RemoteMembrane.send()の引数の個数を参照せよ
				command[0] = command[0].toUpperCase();
				
				if(DEBUG){
					System.out.println("InstructionBlockProcessor.run(): command is : ");
					for(int i = 0; i < command.length; i ++){
						System.out.println("command[" + i + "] is:" + command[i]);
					}
				}
				
//				//todo （将来） ここで命令を書くのではなくて、Instruction.javaの命令番号を引いてくる。
//				//そして変換表もひける。
//				//案: new InstructionListをする。
//				
//				//案：BEGINからENDまで出てくる引数の中でNEWがつかないものを動的に仮引数リストにいれてやると
//				//InterpretedRulsetのコードが使えるので、そうする？

				String memid = command[1];
				AbstractMembrane m = lookupMembrane(memid);

				if (m == null) {
					// 未知の膜の場合、擬似膜の作成を試みる
					String fqdn = memid.split(":",2)[0];
					AbstractLMNtalRuntime rt = LMNtalRuntimeManager.connectRuntime(fqdn);
					if (rt instanceof RemoteLMNtalRuntime) {
						RemoteLMNtalRuntime rrt = (RemoteLMNtalRuntime)rt;
						m = rrt.createPseudoMembrane(memid);
						registerNewMembrane(memid,m);
						IDConverter.registerGlobalMembrane(memid,m);
					}
					if (m == null) {
						throw new RuntimeException("cannot lookup membrane: " + memid);
					}
				}
				
	// === リモート膜に対するボディ命令の場合 ===
	
				if (m instanceof RemoteMembrane) {
					// いくつかの特例を除き、その膜のホストに転送するだけ。
					
					// [特例1] このホストに対するNEWROOT命令（リモート膜mは新しいルート膜の親膜）
					// (1) 親膜mに対してnewRootメソッドを発行
					// (2) リモートホストHに対して、NEWROOT m H 命令を送信
					// (3) ホストHでは、mを擬似膜として作成し、newRootメソッドを発行
					if (command[0].equals("NEWROOT") && m.remote == null) {
						String nodedesc = command[3];
						String fqdn = LMNtalRuntimeManager.nodedescToFQDN(nodedesc);
						if (LMNtalNode.isMyself(fqdn)) {
							String tmpID = command[2];
							AbstractMembrane newmem = m.newRoot(nodedesc);
							registerNewMembrane(tmpID,newmem);
							IDConverter.registerGlobalMembrane(newmem.getGlobalMemID(),newmem);
							result += tmpID + "=" + newmem.getGlobalMemID() + ";";
							continue;
						}
					}
					
					// [特例終わり]
					
					// 転送先 m.remote を決定する
					RemoteTask tmpremote = (RemoteTask)remoteTable.get(m.getTask());
					if (tmpremote == null) { // リモートが未定義の場合
						// - 先祖で最初のリモートタスクにリモートが存在する場合、そのリモートを継承する
						AbstractMembrane tmpmem = m.getTask().getRoot().getParent();
						while (tmpmem instanceof Membrane) {
							tmpmem = tmpmem.getTask().getRoot().getParent();
						}
						if (tmpmem != null) {
							tmpremote = (RemoteTask)remoteTable.get(tmpmem.getTask());
						}
						// - 存在しない場合、mを管理するタスクをリモートに設定する
						if (tmpremote == null) {
							tmpremote = (RemoteTask)m.getTask();
							tmpremote.init();
						}
						remoteTable.put(m.getTask(), tmpremote);
					}
					m.remote = tmpremote;	
									
					// 転送する
					m.remote.send(input);
					continue;
				}
								
	// === ローカル膜に対するボディ命令の場合 ===

				Membrane mem = (Membrane)m;

				if (command[0].equals("END")) { // 実際には起こらない
					break;
	// [1] ルールの操作
				} else if (command[0].equals("CLEARRULES")) {
					mem.clearRules();
				} else if (command[0].equals("LOADRULESET")) {
					String rulesetid = command[2];
					Ruleset rs = IDConverter.lookupRuleset(rulesetid);
					if (rs == null) {
						String fqdn = rulesetid.split(":",2)[0]; // TODO （効率改善）依頼元に取りにいくようにする
						Object obj = remote.sendWaitObject(fqdn, "REQUIRERULESET " + rulesetid);
						if (obj instanceof byte[]) {
							rs = Ruleset.deserialize((byte[])obj);
						}
						if (rs == null) {
							throw new RuntimeException("cannot lookup ruleset: " + rulesetid);
						}
						IDConverter.registerRuleset(rulesetid, rs);
					}
					mem.loadRuleset(rs);
	// [2] アトムの操作
				} else if (command[0].equals("NEWATOM")) {
					String tmpID = command[2];
					Functor func = Functor.deserialize(command[3]);
					Atom newatom = mem.newAtom(func);
					mem.registerAtom(tmpID,newatom);
					//idconv.registerNewAtom(tmpID,newatom);
					if (func.equals(Functor.INSIDE_PROXY)) {
						result += tmpID + "=" + mem.getAtomID(newatom) + ";";
					}
				} else if (command[0].equals("NEWFREELINK")) {
					String tmpID = command[2];
					Functor func = Functor.INSIDE_PROXY;
					Atom newatom = mem.newAtom(func);
					mem.registerAtom(tmpID,newatom);
					result += tmpID + "=" + mem.getAtomID(newatom) + ";";
				} else if (command[0].equals("ALTERATOMFUNCTOR")) {
					Atom atom = mem.lookupAtom(command[2]);
					mem.alterAtomFunctor(atom,Functor.deserialize(command[3]));
				} else if (command[0].equals("REMOTEATOM")) {
					mem.removeAtom(mem.lookupAtom(command[2]));
				} else if (command[0].equals("ENQUEUEATOM")) {
					mem.enqueueAtom(mem.lookupAtom(command[2]));
	// [3] 子膜の操作
				} else if (command[0].equals("NEWMEM")) {
					String tmpID = command[2];
					AbstractMembrane newmem = mem.newMem();
					registerNewMembrane(tmpID,newmem);
					IDConverter.registerGlobalMembrane(newmem.getGlobalMemID(), newmem);
					result += tmpID + "=" + newmem.getGlobalMemID() + ";";
				} else if (command[0].equals("REMOVEMEM")) {
					mem.removeMem(lookupMembrane(command[2]));
				} else if (command[0].equals("NEWROOT")) { 
					String tmpID = command[2];
					AbstractMembrane newmem = mem.newRoot(command[3]); // 新しいリモートが初期化される
					if (newmem instanceof RemoteMembrane) {
						remoteTable.put(newmem.getTask(), newmem.getTask()); // 新しいリモートを登録する
					}
					registerNewMembrane(tmpID,newmem);
					result += tmpID + "=" + newmem.getGlobalMemID() + ";";
	// [4] リンクの操作
				} else if (command[0].equals("NEWLINK")
						 || command[0].equals("RELINKATOMARGS")
						 || command[0].equals("UNIFYATOMARGS")) {
					Atom atom1 = mem.lookupAtom(command[2]);
					int pos1 = Integer.parseInt(command[3]);
					Atom atom2 = mem.lookupAtom(command[4]);
					int pos2 = Integer.parseInt(command[5]);
					if (command[0].equals("NEWLINK")) {
						mem.newLink(atom1,pos1,atom2,pos2);
					} else if (command[0].equals("RELINKATOMARGS")) {
						mem.relinkAtomArgs(atom1,pos1,atom2,pos2);
					} else if (command[0].equals("UNIFYATOMARGS")) {
						mem.unifyAtomArgs(atom1,pos1,atom2,pos2);
					}
	// [5] 膜自身や移動に関する操作 
				} else if (command[0].equals("ACTIVATE")) { // ENQUEUEMEMボディ命令に対応（todo 名称変更？）
					mem.activate();
				} else if (command[0].equals("MOVECELLSFROM")) {
					// todo 【実装】command[2]がリモート膜の場合、内容を取得しなければならない。
					mem.moveCellsFrom(lookupMembrane(command[2]));
				} else if (command[0].equals("MOVETO")) {
					mem.moveTo(lookupMembrane(command[2]));
					// todo 【検証】flushの前後関係が正しいかどうか確認する
	// [6] ロック解放操作
				} else if (command[0].equals("UNLOCK")) {
					mem.unlock();
				} else if (command[0].equals("ASYNCUNLOCK")) {
					mem.asyncUnlock();
				} else if (command[0].equals("RECURSIVEUNLOCK")) {
					mem.recursiveUnlock();
				} else if (command[0].equals("QUIETUNLOCK")) {
					mem.quietUnlock();
				} else { //未知の命令
					System.out.println("InstructionBlockProcessor.run(): unknown body method: "
						+ command[0] + "\n\tin CMD = " + input);
					result = "FAIL;" + result;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("CMD = " + input);
				result = "FAIL;" + result;
			}
		}
		flush();
		// 返答
		if (result.length() > 0) result = result.substring(0, result.length() - 1);
		remote.respond(msgid,result);
	}
	/** リモートに命令ブロックを転送し、返答が来るまでブロックする */
	void flush() {
		Iterator it = remoteTable.keySet().iterator();
		while (it.hasNext()) {
			RemoteTask innerremote = (RemoteTask)remoteTable.get(it.next());
			innerremote.flush();
		}
		remoteTable.clear();

		// m.remoteがnullに初期化されないのが問題かもしれない
	}
}

/**
 * LOCK, ASYNCLOCK, BLOCKINGLOCKの中の人
  * @author nakajima
  */
class LockProcessor implements Runnable{
	String command;
	LMNtalNode node;
	Membrane mem;
	String msgid;
	
	LockProcessor(String command, LMNtalNode node, Membrane mem, String msgid){
		this.command = command;
		this.node = node;
		this.mem = mem;
		this.msgid = msgid;
	}	
	
	public void run() {
		// LOCK globalmemid
		// ローカルの膜をロック
		boolean result = false;
		if (command.equalsIgnoreCase("LOCK"))         result = mem.lock();
		if (command.equalsIgnoreCase("BLOCKINGLOCK")) result = mem.blockingLock();
		if (command.equalsIgnoreCase("ASYNCLOCK"))    result = mem.asyncLock();
		if (result) { // ロック取得成功
			if (true) { // キャッシュ再送信チェック
				byte[] data = mem.cache();
				node.respondRawData(msgid,data);
			}	else {
				node.respond(msgid, "UNCHANGED");
			}
		} else {
			node.respondAsFail(msgid);
		}
	}
}

/**
 * RECURSIVELOCKの中の人
 * @author nakajima
 *
 */
class RecursiveLockProcessor implements Runnable{
	String command;
	LMNtalNode node;
	Membrane mem;
	String msgid;	

	RecursiveLockProcessor(String command, LMNtalNode node, Membrane mem, String msgid){
		this.command = command;
		this.node = node;
		this.mem = mem;
		this.msgid = msgid;
	}
	
	public void run(){
		if (mem.recursiveLock()) {
			node.respondAsOK(msgid);
		} else {
			node.respondAsFail(msgid);			
		}
	}
}

/**
 * TERMINATEの中の人
 * @author nakajima
 *
 */
class TerminateProcessor implements Runnable {
	String msgid;
	LMNtalNode node;
	
	TerminateProcessor(String msgid, LMNtalNode node){
		this.msgid = msgid;
		this.node = node;
	}

	public void run(){
		if(LMNtalRuntimeManager.terminateAll()){
			node.respondAsOK(msgid);
		} else {
			//node.respondAsFail(msgid);
		}
	}
}

/**
 * DISCONNECTRUNTIMEの中の人
 * @author nakajima
 *
 */
class DisconnectProcessor implements Runnable {
	public void run(){
		LMNtalRuntimeManager.disconnectAll();
	}
}