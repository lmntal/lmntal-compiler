package daemon;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import runtime.Env;
import runtime.Membrane;

/**
 * ランタイムが生成するオブジェクト。
 * デーモンとのコネクションに対して生成され、メッセージの受信を行う。
 * @author nakajima, n-kato
 */
public class LMNtalRuntimeMessageProcessor extends LMNtalNode implements Runnable {
	static boolean DEBUG = true;

	public LMNtalRuntimeMessageProcessor(Socket socket) {
		super(socket);
		// TODO send("REGISTERLOCAL");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		if (DEBUG){System.out.println("LMNtalDaemonMessageProcessor.run()");

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
			 * inputの可能性。
			 * 
			 * コマンドからはじまるメッセージ
			 *  msgid fqdn rgid メッセージ
			 *   - fqdn が自分宛 
			 *   - fqdn が他人宛
			 * BEGIN~ENDの途中
			 *  
			 */

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
			String[] parsedInput = new String[4];
			parsedInput = input.split(" ", 4);

			if (parsedInput[0].equalsIgnoreCase("res")) {
				//res msgid 結果
				msgid = parsedInput[1];

				//戻す先
				LMNtalNode returnNode = LMNtalDaemon.getNodeFromMsgId(msgid);

				if (DEBUG)
					System.out.println(
						"res: returnNode is: " + returnNode.toString());

				if (returnNode == null) {
					//戻し先がnull
					// TODO (n-kato) 失敗は無視する。または、msgidでない別の新しいmsgidを作り失敗をoutに通知する
					LMNtalDaemon.respondAsFail(out, msgid);
					continue;
				} else {
					try {  //todo LMNtalDaemonに移す
						returnNode.out.write(input + "\n");
						returnNode.out.flush();
						continue;
					} catch (IOException e1) {
						e1.printStackTrace();
						continue;
					}
				}
			} else if (parsedInput[0].equalsIgnoreCase("registerlocal")) {
				//registerlocal rgid
				//rgidとソケットを登録
				rgid = parsedInput[1];

				result = LMNtalDaemon.registerLocal(rgid, socket);
				if (result == true) {  //todo LMNtalDaemonで面倒を見る
					try {
						//成功
						out.write("ok\n");
						out.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					continue;
				} else {
					try {
						//成功
						out.write("fail\n");
						out.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					continue;
				}
			} else if (parsedInput[0].equalsIgnoreCase("dumphash")) {
				//dumphash
				LMNtalDaemon.dumpHashMap();
				continue;
				
				/* コマンドからはじまる文字列の処理：ここまで */
				
			} else {
				// TODO 耐故障性や拡張性のため、msgid の前に共通コマンド名（例えばcmd）を書いた方がよい
				/* msgid "fqdn" rgid メッセージ の処理 */
			
				//msgidからつづく命令列とみなす
				msgid = parsedInput[0];
				fqdn = (parsedInput[1].split("\"", 3))[1];
				rgid = parsedInput[2];

				//メッセージを登録
				LMNtalNode returnNode = this;
				result = LMNtalDaemon.registerMessage(msgid, returnNode);

				if (result == true) {
					//メッセージ登録成功

					//自分自身宛かどうか判断
					try {
						if (LMNtalDaemonMessageProcessor.isMyself(fqdn)) {
							if (DEBUG)
								System.out.println(
									"This message is for me: "
										+ InetAddress
											.getLocalHost()
											.getHostAddress());

							//自分自身宛なら、自分自身で処理する

							/*
							 * ここで処理される命令一覧
							 * 
							 *  connect
							 *  begin
							 *  beginrule
							 * 
							 * lock
							 * blockinglock
							 * asynclock recursivelock
							 * 
							 * unlock
							 * blockingunlock
							 * asyncunlock
							 * recursiveunlock
							 * 
							 * terminate
							 *  
							 */

							String[] command = new String[3];
							command = parsedInput[3].split(" ", 3);
							//String srcmem, dstmem, parentmem, atom1, atom2, pos1, pos2, ruleset, func;
							Membrane realMem;

							if (command[0].equalsIgnoreCase("connect")) {
								
								if (!LMNtalDaemon.isRuntimeGroupRegistered(rgid)) {
									
									/** 登録されていない時 */
									
									//新規にランタイムを作る。
									//OK返すのは生成されたランタイムがする。
									
									//応答メッセージとmsgidの組から判断し、registeRemoteを呼ぶ
									
									String newCmdLine =
										new String(
											"java daemon/SlaveLMNtalRuntimeLauncher "
												+ rgid
												+ " "
												+ msgid.toString());

									if (DEBUG)
										System.out.println(newCmdLine);

									try {
										Process remoteRuntime =
											Runtime.getRuntime().exec(newCmdLine);
									} catch (IOException e) {
										e.printStackTrace();
										LMNtalDaemon.respondAsFail(out,msgid);
									}
									
									continue;
									/** 登録されていない時：ここまで */

//									//タグなどを使ってメッセージの継続を記録する。
//									//resを受けたときの継続：									
//									//daemonに登録。
//									LMNtalDaemon.registerRemote(rgid, socket);
									
								} else {
									/** 既に登録済みの時 */
									Socket localSocket = LMNtalDaemon.getLocalSocket(rgid);
									
									if(localSocket == null){
										LMNtalDaemon.respondAsFail(out,msgid); 
									} else {
										//TODO  内部同士でやりとりするメッセージのフォーマットを考える。とりあえず内部では死なないものと思って放置する
										
										//LMNtalDaemon.sendLocal(out, rgid, "CONNECT");
										
										//OKを返すのはローカルランタイムが行う。todo	  不正確。実際はSlaveLMNtalRuntimeLauncher（の予定
										/** 既に登録済みの時：ここまで */
									}
								}

								continue;
							} else {
								Socket localSocket = LMNtalDaemon.getLocalSocket(rgid);
								// TODO rgidに対してsocketではなくWriterを手に入れる
								BufferedWriter localout = null; // localSocket.out;
								localout.write(input + "\n");
								if (command[0].equalsIgnoreCase("begin")) {
									// endが来るまで転送
									try {
										while(true){
											String inputline = in.readLine();
											if (inputline == null) break;
											if (inputline.equalsIgnoreCase("end")) {
												break;
											}
											localout.write(inputline + "\n");
										}
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									localout.write("end\n");
								}
								localout.flush();
								continue;
							}
						} else {
							//他ノード宛ならメッセージをいじらずにそのまま転送する

							//宛先ノードは既知か？
							LMNtalNode targetNode =
								LMNtalDaemon.getLMNtalNodeFromFQDN(fqdn);
				
							if (targetNode == null) {
								//宛先ノードへ接続するのが初めての場合
								result = LMNtalDaemon.connect(fqdn, rgid);

								if (result) {
									targetNode =
										LMNtalDaemon.getLMNtalNodeFromFQDN(
											fqdn);

									if (targetNode == null) {
										//接続失敗
										LMNtalDaemon.respondAsFail(out,msgid);
										continue;
									} else {
										LMNtalDaemon.sendMessage(
											targetNode,
											input + "\n");
									}
									continue;
								} else {
									//宛先ノードへの接続失敗
									LMNtalDaemon.respondAsFail(out,msgid);
									continue;
								}
							} else {
								//宛先ノードが既知の場合
								if (LMNtalDaemon
									.sendMessage(targetNode, input + "\n")) {
									//OKを返すのは、転送先がやるのでここではOKを返さない
									continue;
								} else {
									//転送失敗
									LMNtalDaemon.respondAsFail(out,msgid);
									continue;
								}
							}
						}
					} catch (IOException e1) {
						e1.printStackTrace();
						//continue;
						break;
					} catch (NullPointerException nullpo) {
						System.out.println("（　´∀｀）＜　ぬるぽ");
						nullpo.printStackTrace();
						//continue;
						break;
					}
				} else {
					//既にmsgTableに登録されている時 or 通信失敗
					LMNtalDaemon.respondAsFail(out,msgid);
				}
				}
			}
		}
	}
	
	// TODO 以下はruntime用の別クラスのメソッドであるから、そこに移動して実装すること
	
	/** LMNtalRuntime側で使用する */
	void onMsg() {
		String command[] = null;
		String msgid = null;
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
	
	/** LMNtalRuntime側で使用する */
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
}