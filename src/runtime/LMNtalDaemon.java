package runtime;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.net.Socket;

//TODO 実装

/**
 * 物理的な計算機の境界にあって、LMNtalRuntimeインスタンスとリモートノードの対応表を保持する。
 * @author nakajima
 *
 */
public class LMNtalDaemon{
	/*
	 * とりあえず考えてみたプロトコル
	 * LMNtalDaemonがやりとりしなきゃいけないのはこれぐらいかなぁ…
	 * 
	 * 登録関係
	 * HELO …（ノﾟДﾟ）おはよう
	 * READY …ack
	 * REGISTLOCAL runtimeid …ローカルにあるマスタランタイムを登録
	 * OK … 成功
	 * FAIL … 失敗
	 * REGISTREMOTE runtimeid …手元に登録してあるマスタランタイムを相手に送る
	 * REGISTFINISHED …黒を作成できたことをマスタランタイムがある計算機のdaemonに送る
	 * 
	 * 実行関係
	 * COPYRULESET …ルールセットを全て送る
	 * COPYRULE …ルールを一つ送る
	 * COPYPROCESSCONTEXT …$pを送る
	 * COPYFREELINK
	 * COPYATOM
	 * 
	 */
}