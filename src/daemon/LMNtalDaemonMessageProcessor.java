package daemon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * メッセージの中身を見て処理する。基本的にLMNtalDaemonのソケットが開かれると、
 * これが生成される。つまり物理的な計算機1台の中に複数存在しうる。
 * 
 * @author nakajima
 */
public class LMNtalDaemonMessageProcessor implements Runnable {
	static boolean DEBUG = true;

	BufferedReader in;
	BufferedWriter out;
	Socket socket;

	/*
	 * コンストラクタ。
	 * 
	 * @param socket 開かれたソケット。
	 * @param in 入力。BufferedReader。
	 * @param out 出力。BufferedWriter。
	 */
	public LMNtalDaemonMessageProcessor(
		Socket tmpSocket,
		BufferedReader inTmp,
		BufferedWriter outTmp) {
		in = inTmp;
		out = outTmp;
		socket = tmpSocket;
	}

	/*
	 * ホストfqdnが自分自身か判定。
	 * 
	 * @param fqdn Fully Qualified Domain Name
	 * @return 自分自身に割り振られているIPアドレスからホスト名を引いて、fqdnとstringで比較。同じだったらtrue、それ以外はfalse。
	 */
	public static boolean isMyself(String fqdn) {
		try {
			return InetAddress.getLocalHost().getHostAddress().equals(
				InetAddress.getByName(fqdn).getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void run() {
		if (DEBUG) {
			System.out.println("LMNtalDaemonMessageProcessor.run()");
		}

		String input = "";

		while (true) {
			try {
				input = in.readLine();
			} catch (IOException e) {
				System.out.println("ERROR:このスレッドには書けません!");
				e.printStackTrace();
				break;
			}

			if (DEBUG) {
				System.out.println("in.readLine(): " + input);
			}

			if (input == null) {
				System.out.println("（　´∀｀）＜　inputがぬる");
				break;
			}

			/* 
			 * inputの可能性。
			 * 
			 * コマンドからはじまるメッセージ
			 * msgid fqdn rgid メッセージ 
			 *   - fqdn が自分宛
			 *   - fqdn が他人宛
			 * BEGIN~ENDの途中
			 * 
			 */

			/* コマンドからはじまるメッセージを処理。
			 * 
			 * ここで処理される命令一覧。これ以外のは下スクロールしてね
			 *  res msgid メッセージ本文
			 *  registerlocal
			 *  dumphash - デバッグ用
			 */
			Integer msgid;
			Integer rgid;
			String fqdn;
			boolean result;
			String[] parsedInput = new String[4];
			parsedInput = input.split(" ", 4);

			if (parsedInput[0].equalsIgnoreCase("res")) {
				//res msgid 結果
				msgid = new Integer(parsedInput[1]);

				//戻す先
				LMNtalNode returnNode = LMNtalDaemon.getNodeFromMsgId(msgid);

				if (DEBUG)
					System.out.println(
						"res: returnNode is: " + returnNode.toString());

				if (returnNode == null) {
					//戻し先がnull
					try {
						out.write("fail\n");
						out.flush();
						continue;
					} catch (IOException e1) {
						e1.printStackTrace();
						continue;
					}
				} else {
					//System.out.println(returnNode.getOutputStream().toString());
					//System.out.println(input);
					try {
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
				rgid = new Integer(parsedInput[1]);
				result = LMNtalDaemon.registerLocal(rgid, socket);
				if (result == true) {
					//成功
					try {
						out.write("ok\n");
						out.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					continue;
				} else {
					//失敗
					try {
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
			} else {
				//msgidからつづく命令列とみなす
				//msgid "FQDN" rgid メッセージ
				msgid = new Integer(parsedInput[0]);
				fqdn = (parsedInput[1].split("\"", 3))[1];

				//メッセージを登録
				LMNtalNode returnNode =
					new LMNtalNode(socket.getInetAddress(), in, out);
				result = LMNtalDaemon.registerMessage(msgid, returnNode);

				if (result == true) {
					//メッセージ登録成功

					//自分自身宛かどうか判断
					try {
						if (isMyself(fqdn)) {
							if(DEBUG) System.out.println(
								"This message is for me: "
									+ InetAddress
										.getLocalHost()
										.getHostAddress());

							//自分自身宛なら、自分自身で処理する

							/* ここで処理される命令一覧
							 * 
							 *  begin
							 *  connect
							 *  lock taskid
							 *  terminate
							 */

							String command = (parsedInput[3].split(" ", 3))[0];

							if (command.equalsIgnoreCase("connect")) {
								//connectがきたら、ランタイムを生成する。

								LMNtalDaemon.createRemoteRuntime(
									msgid.intValue());

								continue;
								//OK返すのは生成されたライタイムがする。
							} else if (command.equalsIgnoreCase("begin")) {
								//TODO 実装
								out.write("not implemented yet\n");
								out.flush();
								continue;
							} else if (command.equalsIgnoreCase("lock")) {
								//TODO 実装
								out.write("not implemented yet\n");
								out.flush();
								continue;
							} else if (command.equalsIgnoreCase("terminate")) {
								//TODO 実装

								//terminateだけ変
								//
								//やるべきことはEnv.theRuntimeのterminate
								//でも呼べないからどうしよう？
								//LocalLMNtalRuntime.terminate();
								

								out.write("not implemented yet\n");
								out.flush();
								continue;
							} else {
								//未知のコマンド or それ以外の何か
								out.write("fail\n");
								out.flush();
								continue;
							}
						} else {
							//他ノード宛ならメッセージをいじらずにそのまま転送する

							//宛先ノードは既知か？
							LMNtalNode targetNode =
								LMNtalDaemon.getLMNtalNodeFromFQDN(fqdn);
							if (targetNode == null) {
								//宛先ノードへ接続するのが初めての場合
								result = LMNtalDaemon.connect(fqdn);

								if (result) {
									targetNode =
										LMNtalDaemon.getLMNtalNodeFromFQDN(
											fqdn);

									if (targetNode == null) {
										//接続失敗
										out.write("fail\n");
										out.flush();
										continue;
									} else {
										LMNtalDaemon.sendMessage(
											targetNode,
											input + "\n");
									}

									continue;
								} else {
									//宛先ノードへの接続失敗
									out.write("fail\n");
									out.flush();
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
									out.write("fail\n");
									out.flush();
									continue;
								}
							}
						}
					} catch (IOException e1) {
						e1.printStackTrace();
						//continue;
						break;
					} catch (NullPointerException nurupo) {
						System.out.println("（　´∀｀）＜　ぬるぽ");
						nurupo.printStackTrace();
						//continue;
						break;
					}
				} else {
					//既にmsgTableに登録されている時 or 通信失敗時
					try {
						out.write("fail\n");
						out.flush();
						//continue;
						break;
					} catch (IOException e1) {
						e1.printStackTrace();
						//continue;
						break;
					}
				}
			}
		}
	}
}