package daemon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;



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
		System.out.println("LMNtalDaemonThread2.run()");

		while (true) {
			try {
				String input = in.readLine();
				//テスト用：
				//String input = new String("msgid \"localhost\" runtimegroupid connect\n");
				System.out.println("input: " + input);
				if (input == null) {
					break;
				}

				//ここからメッセージ処理部分
				/* ここで処理される命令一覧。これ以外のは下スクロールしてね
				 *  res
				 *  registerlocal
				 *  (dumphash)
				 *  
				 */
							
				Integer msgid;
				Integer rgid;
				String fqdn;
				boolean result;
				String[] tmpString = new String[3];

				tmpString = input.split(" ", 3);

				if (tmpString[0].equalsIgnoreCase("res")) {
					//res msgid 結果
					//直接戻せばよい

					//TODO 単体テスト
					msgid = new Integer(tmpString[1]);

					LMNtalNode returnNode =
						LMNtalDaemon.getNodeFromMsgId(msgid);

					returnNode.getOutputStream().write(input);
					returnNode.getOutputStream().flush();
				} else if (tmpString[0].equalsIgnoreCase("registerlocal")) {
					//registerlocal rgid
					//rgidとソケットを登録
					rgid = new Integer(tmpString[1]);
					result = LMNtalDaemon.registerLocal(rgid, socket);
					if (result == true) {
						//成功
						out.write("ok\n");
						out.flush();
					} else {
						//失敗
						out.write("fail\n");
						out.flush();
					}
				} else if (tmpString[0].equalsIgnoreCase("dumphash")) {
					//dumphash
					LMNtalDaemon.dumpHashMap();
				} else {
					//msgidからつづく命令列とみなす
					//msgid "FQDN" rgid メッセージ
					msgid = new Integer(tmpString[0]);
					fqdn = (tmpString[1].split("\"", 3))[1];
					
					//メッセージを登録
					LMNtalNode returnNode =
						new LMNtalNode(socket.getInetAddress(), in, out);
					result = LMNtalDaemon.registerMessage(msgid, returnNode);

					if (result == true) {
						//メッセージ登録成功
						
						//自分自身宛かどうか判断
						if (socket.getInetAddress().isAnyLocalAddress()){
							//自分自身宛なら、自分自身で処理する
							
							/* ここで処理される命令一覧
							 * 
							 *  begin
							 *  connect
							 *  lock taskid
							 *  terminate
							 */
							
							String command =  (tmpString[2].split(" ", 3))[0];
							if(command.equalsIgnoreCase("connect")){
								//connectがきたら、ランタイムを生成して ok を返す
								
								Thread remoteRuntime = new Thread(new DummyRemoteRuntime());
								remoteRuntime.start();



								out.write("ok\n");
								out.flush();
							} else if(command.equalsIgnoreCase("begin")){
								//仮
								out.write("not implemented yet\n");
								out.flush();							 
							} else if(command.equalsIgnoreCase("lock")){
								//仮
								out.write("not implemented yet\n");
								out.flush();
							} else if(command.equalsIgnoreCase("terminate")){
								//仮
								out.write("not implemented yet\n");
								out.flush();
							} else {
								//未知のコマンド or それ以外の何か
								out.write("fail\n");
								out.flush();	
							}						
						} else {
							//他ノード宛ならメッセージをいじらずにそのまま転送する
							if (LMNtalDaemon.sendMessage( (tmpString[1].split("\"", 3))[1]  , input  )){
								out.write("ok\n");
								out.flush();
							} else {
								out.write("fail\n");
								out.flush();
							}
						}
					} else {
						//既にmsgTableに登録されている時 or 通信失敗時
						out.write("fail\n");
						out.flush();
					}

				}

			} catch (IOException e) {
				System.out.println("ERROR:このスレッドには書けません! " + e.toString());
				break;
			} catch (ArrayIndexOutOfBoundsException ae) {
				//送られてきたメッセージが短かすぎるとき（＝不正な時
				//'hoge' とかそういう時
				System.out.println("Invalid Message: " + ae.toString());
				break;
			}
		}
	}

}