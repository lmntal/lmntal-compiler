package runtime;

/*
 * 分散処理のテスト用
 *
 * @author nakajima
 *
 */
class TestRemote{
	public static void main(String args[]){
		/*参考コード from FrontEnd.java*/		
		//LMNParser lp = new LMNParser(src);
			
		//compile.structure.Membrane m = lp.parse();

		//Ruleset rs = RulesetCompiler.compileMembrane(m, unitName);
		//((InterpretedRuleset)rs).showDetail();
		//m.showAllRules();
			
		// 実行
		//LMNtalRuntimeManager.init();
//		MasterLMNtalRuntime rt = new MasterLMNtalRuntime();
//		Membrane root = (Membrane)rt.getGlobalRoot();
//		Env.initGUI(root);
		//root.blockingLock();
//		rs.react(root); 
//		if (Env.gui != null) {
//			Env.gui.lmnPanel.getGraphLayout().calc();
//			Env.gui.onTrace();
//		}
//		//root.blockingUnlock();
//		((Task)root.getTask()).execAsMasterTask();
//		LMNtalRuntimeManager.terminateAll();
		/*ここまで：参考コード from FrontEnd.java*/		

		//↓のコードはEnv.theRuntime.runtimeidがnullなのでぬるぽ   
		//RemoteLMNtalRuntime rr = (RemoteLMNtalRuntime)(LMNtalRuntimeManager.connectRuntime("banon.ueda.info.waseda.ac.jp"));
		//		if(rr==null){
//			//接続失敗
//			System.out.println("failed to connect");
//			System.exit(0);
//		}
		
/*
 * ↓のコードはEnv.theRuntime.runtimeidがnullなのでぬるぽ   
 *  
 * 		if(LMNtalRuntimeManager.connectToDaemon()){
			System.out.println("success");
		} else {
			System.out.println("orz");
		}*/

		/* 
		 * デフォルトのtimeout値を調べてみた。default == 0 のようです。
		 * （つまりtimeoutしない）  
		 * 
		Socket s = new Socket();
		try {
			System.out.println(s.getSoTimeout());
		} catch (SocketException e) {
			// todo Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
		*/
	
		LMNtalRuntimeManager.init();
		MasterLMNtalRuntime masterRuntime = new MasterLMNtalRuntime();
		
		Membrane rootMem = (Membrane)masterRuntime.getGlobalRoot();
		
		if(!LMNtalRuntimeManager.connectToDaemon()){
			System.out.println("orz");
			System.exit(1);
		}
		System.out.println("connectToDaemon success");

		RemoteLMNtalRuntime banon = (RemoteLMNtalRuntime)LMNtalRuntimeManager.connectRuntime("banon.ueda.info.waseda.ac.jp");

		if(banon == null){
			System.out.println("orz");
			System.exit(1);
		}
		System.out.println("connectRuntime success");
		
		
		//終了処理
//		LMNtalRuntimeManager.disconnectFromDaemon(); //これだと遠隔ノードへのコネクション張りっぱなし、slave runtimeあがりっぱなし
		LMNtalRuntimeManager.terminateAllNeighbors();	 //もっといい方法ないかな
		System.out.println("terminateAllNaighbours() success");

		/*
		 *  試しに実行してみるコード。
		 * a, a:-b.
		 * 
 Compiled Rule (  :- ( a a :- b ) )
	--atommatch:
		spec           [2, 2]
	--memmatch:
		spec           [1, 1]
		jump           [L165, [0], [], []]
	--body:L165:
		spec           [1, 1]
		commit         [(  :- ( a a :- b ) )]
		loadruleset    [0, @609]
		proceed        []

Compiled Rule ( a a :- b )
	--atommatch:
		spec           [2, 3]
	--memmatch:
		spec           [1, 3]
		findatom    [1, 0, a_0]
		findatom    [2, 0, a_0]
		neqatom        [2, 1]
		jump           [L159, [0], [1, 2], []]
	--body:L159:
		spec           [3, 4]
		commit         [( a a :- b )]
		dequeueatom    [1]
		dequeueatom    [2]
		removeatom     [1, 0, a_0]
		removeatom     [2, 0, a_0]
		newatom     [3, 0, b_0]
		enqueueatom    [3]
		freeatom       [1]
		freeatom       [2]
		proceed        []
		 * 
		 */
	}
}
