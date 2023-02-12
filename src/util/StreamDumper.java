package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 *  ストリームを受け取ってコンソールに出力するクラス。
 * Runtime.exec()で別プロセスにしてしまうと、コンソールに出なくなってデバッグに困るので。
 *
 * @author nakajima
 */
public class StreamDumper implements Runnable {

  private InputStream childIn;
  private String processName;

  //	private int nextLine, lineCount;

  public StreamDumper(String processName, InputStream in) {
    this.childIn = in;
    this.processName = processName;
  }

  public void run() {
    Util.println(
      "StreamDumper: now starting dumping the console log of: " + processName
    );

    BufferedReader buff = new BufferedReader(new InputStreamReader(childIn));

    String input;

    while (true) {
      try {
        input = buff.readLine();

        if (input == null) {
          Util.println("StreamDumper of: " + processName + " finished");
          break;
        } else {
          Util.println(processName + ": " + input);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
