package test.distribute;

import daemon.LMNtalDaemon;

/*
 * DaemonTester
 * @author nakajima
 *
 * 使い方：
 *  - cureでLMNtalDaemonTestを実行
 *  - brieやbanonなどにログイン、仮想ターミナルを2つあげる（以下、ターミナル1とターミナル2）
 *  - [ターミナル1-2] cd eclipse/devel/bin/
 *  - scpでログイン先の~/eclipse/devel/bin/[daemon,test/distribute]以下を最新に
 *  - [ターミナル1] java daemon/LMNtalDaemon
 *  - [ターミナル2] java test/distribute/DaemonTester
 */

class DaemonTester{
	public static void main(String args[]){
		Thread r1 = new Thread(new DummyRuntime(LMNtalDaemon.makeID()));
		r1.start();	
	}
}

