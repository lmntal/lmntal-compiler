package daemon;

import java.io.IOException;
import java.net.Socket;

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
		if (DEBUG) System.out.println("LMNtalDaemonMessageProcessor.run()");

		String input = "";

		while (true) {
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
			String[] parsedInput = input.split(" ", 4);

			if (parsedInput[0].equalsIgnoreCase("res")) {
				//res msgid 結果
				String msgid = parsedInput[1];

				//戻す先
				LMNtalNode returnNode = LMNtalDaemon.getNodeFromMsgId(msgid);

				if (DEBUG)
					System.out.println(
						"res: returnNode is: " + returnNode.toString());

				if (returnNode == null) {
					//戻し先がnull
					// TODO (n-kato) 失敗は無視する。または、msgidでない別の新しいmsgidを作り失敗をoutに通知する
					respondAsFail(msgid);
					continue;
				} else {
					returnNode.sendMessage(input + "\n");
					continue;
				}
			} else if (parsedInput[0].equalsIgnoreCase("registerlocal")) {
				//REGISTERLOCAL MASTER/SLAVE msgid rgid
				//rgidとLMNtalNodeを登録
				String type = parsedInput[1];
				String msgid = parsedInput[2];
				String rgid = parsedInput[3];
				boolean result = LMNtalDaemon.registerRuntimeGroup(rgid, this);
				respond(msgid, result);
				continue;
			} else if (parsedInput[0].equalsIgnoreCase("dumphash")) {
				//dumphash
				LMNtalDaemon.dumpHashMap();
				continue;
				
				/* コマンドからはじまる文字列の処理：ここまで */
				
			} else {
				// TODO 耐故障性や拡張性のため、msgid の前に共通コマンド名（例えばcmd）を書いた方がよい
				// todo 半角空白で分割するので " を書く意味がないのを何とかする
				
				/* msgid "fqdn" rgid メッセージ の処理 */
			
				//msgidからつづく命令列とみなす
				String msgid = parsedInput[0];
				String fqdn = (parsedInput[1].split("\"", 3))[1];
				String rgid = parsedInput[2];

				//メッセージを登録
				LMNtalNode returnNode = this;
				boolean result = LMNtalDaemon.registerMessage(msgid, returnNode);
				
				if (result == true) {
					//メッセージ登録成功
					try {
						String[] command = parsedInput[3].split(" ", 3);

						//転送する内容を取得する
						String content = input + "\n";
						if (command[0].equalsIgnoreCase("begin")) {
							StringBuffer buf = new StringBuffer(content);
							// endが来るまで積み込む
							while(true){
								String inputline = in.readLine();
								if (inputline == null) break;
								if (inputline.equalsIgnoreCase("end")) break;
								buf.append(inputline);
								buf.append("\n");
							}
							buf.append("end\n");
							content = buf.toString();
						}
						
						LMNtalNode targetNode;
						
						if (command[0].equalsIgnoreCase("connect")) {
							//自分自身宛かどうか判断
							if (isMyself(fqdn)) { //自分自身宛
								if (!LMNtalDaemon.isRuntimeGroupRegistered(rgid)) {	
									/* 登録されていない時 */
									
									//新規にランタイムを作る。
									String newCmdLine =
										new String(
											"java daemon/SlaveLMNtalRuntimeLauncher "
												+ msgid
												+ " "
												+ rgid);

									if (DEBUG)
										System.out.println(newCmdLine);

									Process remoteRuntime =
										Runtime.getRuntime().exec(newCmdLine);
									
									//OK返すのは生成されたランタイムがする。
									continue;
								} else {
									/* 既に登録済みの時 */
									targetNode = LMNtalDaemon.getRuntimeGroupNode(rgid);
								}
							}
							else { //他ノード宛ならconnectをそのまま転送する
								LMNtalDaemon.makeRemoteConnection(fqdn); // todo ブロックしないようにする
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