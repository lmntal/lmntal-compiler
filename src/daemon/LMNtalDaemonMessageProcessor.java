package daemon;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

//import runtime.Env;
//import runtime.Membrane;

/**
 * デーモンが生成するオブジェクト。
 * コネクションごとに生成され、メッセージの受信を行う。
 * <p>
 * メッセージの中身を見て処理する。
 * 基本的にLMNtalDaemonのソケットが開かれると、これが生成される。
 * つまり物理的な計算機1台の中に複数存在しうる。
 * 
 * @author nakajima, n-kato
 */
public class LMNtalDaemonMessageProcessor extends LMNtalNode implements Runnable {
	static boolean DEBUG = true;

	public LMNtalDaemonMessageProcessor(Socket socket) {
		super(socket);
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
						if (isMyself(fqdn)) {
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
							//Membrane realMem;

							if (command[0].equalsIgnoreCase("connect")) {
								
								if (!LMNtalDaemon.isRuntimeGroupRegistered(rgid)) {
									
									/** 登録されていない時 */
									
									//新規にランタイムを作る。
									//OK返すのは生成されたランタイムがする。
									
									//応答メッセージとmsgidの組から判断し、registerRemoteを呼ぶ
									
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
	


}