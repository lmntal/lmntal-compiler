package daemon;

import java.io.IOException;
import java.net.Socket;

import util.StreamDumper;

//import runtime.Env;
//import runtime.Membrane;

/**
 * デーモンが生成するオブジェクト。
 * コネクションごとに生成され、メッセージの受信を行う。
 * <p>
 * <strike>メッセージの中身を見て処理する。</strike>
 * 基本的にLMNtalDaemonのソケットが開かれると、これが生成される。
 * つまり物理的な計算機1台の中に複数存在しうる。
 * 
 * @author nakajima, n-kato
 */
public class LMNtalDaemonMessageProcessor extends LMNtalNode implements Runnable {
	boolean DEBUG = true; //LMNtalDaemon.main()から呼ぶときにEnv.debugは使えない...
	
	public LMNtalDaemonMessageProcessor(Socket socket) {
		super(socket);
	}

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
				System.out.println("LMNtalDaemonMessageProcessor.run(): ERROR:このスレッドには書けません!");
				e.printStackTrace();
				break;
			}
			if (input == null) {
				System.out.println("LMNtalDaemonMessageProcessor.run(): in.readLine(): （　´∀｀）＜　inputがぬる");
				break;
			}
			if (DEBUG) System.out.println("LMNtalDaemonMessageProcessor.run(): in.readLine(): " + input);

			/* メッセージ:
			 *   RES msgid 返答
			 *   REGISTERLOCAL (MASTER|SLAVE) msgid rgid
			 *   DUMPHASH
			 *   CMD msgid fqdn rgid コマンド
			 *     - fqdn が自分宛 
			 *     - fqdn が他人宛
			 * 返答:
			 *   OK | FAIL | UNCHANGED | RAW bytes \n data
			 * コマンド:
			 *   BEGIN \n ボディ命令... END
			 *   CONNECT dst_nodedesc src_nodedesc
			 *   TERMINATE
			 *   SETENV variable value  //TODO SETENV
			 *   ...
			 */

			String[] parsedInput = input.split(" ", 5);

			if (parsedInput[0].equalsIgnoreCase("RES")) {
				// RES msgid 返答
				String msgid = parsedInput[1];
				//転送する内容を取得する
				String content = input + "\n";
				byte[] rawData = null;
				if (parsedInput[2].equalsIgnoreCase("RAW")) {
					try {
						//バイト数指定や、末尾の改行記号はreadBytes内で処理
						rawData = readBytes();
					}
					catch (Exception e) {
						content = "RES " + msgid + " FAIL\n";
					}
				}
				
				//戻す先
				LMNtalNode returnNode = LMNtalDaemon.unregisterMessage(msgid);
				
				if (DEBUG) System.out.println("res: returnNode is: " + returnNode);

				if (returnNode == null) {
					//戻し先がnull
					// todo (n-kato) 失敗は無視する。または、msgidでない別の新しいmsgidを作り失敗をoutに通知する
					respondAsFail(msgid);
					continue;
				} else {
					returnNode.sendMessage(content, rawData);
					continue;
				}
			} else if (parsedInput[0].equalsIgnoreCase("REGISTERLOCAL")) {
				// REGISTERLOCAL (MASTER|SLAVE) msgid rgid
				// rgidとLMNtalNodeを登録
				String type  = parsedInput[1];
				String msgid = parsedInput[2];
				String rgid  = parsedInput[3];
				// todo （中島君）ここはすでに登録されていたらFAILを返すのが正しいみたいですので直さないでください。
				//(nakajima 2004-10-13) 了解しました。
				boolean result = LMNtalDaemon.registerRuntimeGroup(rgid, this);
				respond(msgid, result);
				continue;
			} else if (parsedInput[0].equalsIgnoreCase("UNREGISTERLOCAL")){
				//UNREGISTERLOCAL rgid
				
				//rgid を削除
				String rgid  = parsedInput[1];
				if(LMNtalDaemon.unregisterRuntimeGroup(rgid)){
					//	自分自身にある、マスタランタイム(LMNtalRuntimeMessageProcessor)との通信路を切る
					close();
				}
				return;
			} else if (parsedInput[0].equalsIgnoreCase("DUMPHASH")) {
				// DUMPHASH
				LMNtalDaemon.dumpHashMap();
				continue;
			} else if (parsedInput[0].equalsIgnoreCase("CMD")) {
				// CMD msgid fqdn rgid コマンド
			
				String msgid = parsedInput[1];
				String fqdn = parsedInput[2].replaceAll("\"","");
				String rgid = parsedInput[3];

				//メッセージを登録
				LMNtalNode returnNode = this;
				if (LMNtalDaemon.registerMessage(msgid, returnNode)) {
					//メッセージ登録成功
					try {
						String[] command = parsedInput[4].split(" ", 3);

						//転送する内容を取得する
						String content = input + "\n";
						if (command[0].equalsIgnoreCase("BEGIN")) {
							StringBuffer buf = new StringBuffer(content);
							// endが来るまで積み込む
							while(true){ // BEGINだけきてその後が来ないとbreakしない→それは仕様ですので
								String inputline = readLine();
								if (DEBUG) System.out.println("LMNtalDaemonMessageProcessor.run(): after BEGIN:  " + inputline);
								if (inputline == null) break;
								if (inputline.equalsIgnoreCase("END")) break;
								buf.append(inputline);
								buf.append("\n");
							}
							buf.append("END\n");
							content = buf.toString();
						} 
						
						LMNtalNode targetNode = null;
						
						if (command[0].equalsIgnoreCase("CONNECT")) {
							//自分自身宛かどうか判断
							if (isMyself(fqdn)) { //自分自身宛
								if (!LMNtalDaemon.isRuntimeGroupRegistered(rgid)) {	
									/* 登録されていない時 */
									
									//新規にランタイムを作る。
									String classpath = System.getProperty("java.class.path");
									String newCmdLine =
										new String(
												"java -classpath"
												+ " "
												+ classpath
												+ " "
												+"daemon.SlaveLMNtalRuntimeLauncher"
												+ " "
												+ msgid
												+ " "
												+ rgid);

									if (DEBUG) System.out.println(newCmdLine);

									Process slave = Runtime.getRuntime().exec(newCmdLine);
									
									Thread dumpErr = new Thread(new StreamDumper("slave runtime.error", slave.getErrorStream()));
									Thread dumpOut = new Thread(new StreamDumper("slave runtime.stdout", slave.getInputStream()));
									dumpErr.start();
									dumpOut.start();
									
									//OK返すのは生成されたランタイムがする。
									continue;
								} else {
									// 既に登録済みの時
									targetNode = LMNtalDaemon.getRuntimeGroupNode(rgid);
								}
							} else { //他ノード宛ならconnectをそのまま転送する
								LMNtalDaemon.makeRemoteConnection(fqdn); // TODO（効率改善）ブロックしないようにする
								targetNode = LMNtalDaemon.getLMNtalNodeFromFQDN(fqdn);
							}
						} else {
							// connect以外のとき
							// 自分自身宛かどうか判断
							if (isMyself(fqdn)) { //自分自身宛
								targetNode = LMNtalDaemon.getRuntimeGroupNode(rgid);
							}
							else {
								targetNode = LMNtalDaemon.getLMNtalNodeFromFQDN(fqdn);
							}
						}
						if (targetNode != null && targetNode.sendMessage(content)) {
							if(DEBUG)System.out.println("LMNtalDaemonMessageProcessor.run(): target node is " + targetNode.toString() + " and now sending: " + content);
							continue;
						}
						// 転送失敗したら下に抜ける
					} catch (IOException e) {
						e.printStackTrace();
					} catch (NullPointerException nullpo) {
						System.out.println("（　´∀｀）＜　ぬるぽ");
						nullpo.printStackTrace();
						break;
					}
					LMNtalDaemon.unregisterMessage(msgid);
				}
				//既にmsgTableに登録されている時 or 通信失敗
				respondAsFail(msgid);
			}
		}
	}
}