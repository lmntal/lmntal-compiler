package test.distribute;


class DaemonTester{
	public static void main(String args[]){
		Thread r1 = new Thread(new DummyRuntime(100));
		r1.start();	
	}
}

