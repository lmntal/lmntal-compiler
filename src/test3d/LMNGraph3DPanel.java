package test3d;

import javax.media.j3d.BranchGroup;






public class LMNGraph3DPanel implements Runnable{
	private LMNtal3DFrame frame;
	private Graph3DLayout gLayout;
	private Thread th = null;
	private BranchGroup objB;
	
	
	public LMNGraph3DPanel(LMNtal3DFrame f){
		frame = f;
		gLayout = new Graph3DLayout(this);
		objB = new BranchGroup();
		/*TODO:ここにマウスリスナー追加*/
	}
	
	public void run_Graph3DLayout(){
		gLayout.start();
	}
	/*オブジェクトを追加するブランチの受け渡し*/
	public void setBranch(BranchGroup objBranchs){
		gLayout.setBranch(objBranchs);
	}
	
	/*
	public void creat(BranchGroup objBranchs){
		//Atom3D atom= new Atom3D();
		//atom.createNode(objB);
		//gLayout.make_atom(objB);
		//objBranchs.addChild(objB);
		//System.out.println(objB.toString());
		//System.out.println("objB ok");
	}
	*/
	public Graph3DLayout getGraph3DLayout(){
		return gLayout;
	}

	public void start() {
		if (th == null) {
			th = new Thread(this);
			th.start();
			gLayout.start();
		}
	}
	
	public void stop() {
		th = null;
		gLayout.stop();
	}
	public void run() {
		Thread me = Thread.currentThread();
		while (me == th) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			//repaint();
		}
	}
	
}