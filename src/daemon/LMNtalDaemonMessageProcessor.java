package daemon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

class LMNtalDaemonMessageProcessor implements Runnable {
	BufferedReader in;
	BufferedWriter out;
	Socket socket;

	public LMNtalDaemonMessageProcessor(
		Socket tmpSocket,
		BufferedReader inTmp,
		BufferedWriter outTmp) {
		in = inTmp;
		out = outTmp;
		socket = tmpSocket;
	}

	public void run() {
		System.out.println("LMNtalDaemonMessageProcessor.run()");

		String input = "";

		while (true) {
			try {
				input = in.readLine();
			} catch (IOException e) {
				System.out.println("ERROR:このスレッドには書けません!");
				e.printStackTrace();
				break;
			}

			System.out.println("in.readLine(): " + input);
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

				System.out.println("res: returnNode is: " + returnNode.toString());
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
						continue;
					} catch (IOException e1) {
						e1.printStackTrace();
						continue;
					}
				} else {
					//失敗
					try {
						out.write("fail\n");
						out.flush();
						continue;
					} catch (IOException e1) {
						e1.printStackTrace();
						continue;
					}
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
						if (InetAddress
							.getLocalHost()
							.getHostAddress()
							.equals(
								InetAddress
									.getByName(fqdn)
									.getHostAddress())) {

							System.out.println(
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
								//仮
								out.write("not implemented yet\n");
								out.flush();
								continue;
							} else if (command.equalsIgnoreCase("lock")) {
								//仮
								out.write("not implemented yet\n");
								out.flush();
								continue;
							} else if (command.equalsIgnoreCase("terminate")) {
								//仮
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
								
								if(result){
									targetNode =
										LMNtalDaemon.getLMNtalNodeFromFQDN(fqdn);

									if(targetNode == null){
										out.write("fail\n");
										out.flush();
										continue;
									} else {
										LMNtalDaemon.sendMessage(targetNode, input + "\n");
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
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
						//continue;
						break;
					} catch (IOException e1) {
						e1.printStackTrace();
						//continue;
						break;
					}  catch (NullPointerException nurupo){
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