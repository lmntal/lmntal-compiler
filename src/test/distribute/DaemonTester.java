package test.distribute;

/*
 * DaemonTester
 * @author nakajima
 *
 * 使い方：
 *  - cureでLMNtalDaemonTestを実行
 *  - brieやbanonなどにログイン、仮想ターミナルを2つあげる（以下、ターミナル1とターミナル2）
 *  - [ターミナル1-2] cd eclipse/devel/bin/
 *  - [ターミナル1] java test/distribute/LMNtalDaemonTest
 *  - [ターミナル2] java test/distribute/DaemonTester  
 */

class DaemonTester{
	public static void main(String args[]){
		Thread r1 = new Thread(new DummyRuntime(100));
		r1.start();	
	}
}

