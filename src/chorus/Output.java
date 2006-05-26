package chorus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;

import runtime.AbstractMembrane;
import runtime.Atom;

/**
 * Chorus3Dのソースを出力する
 * @author nakano
 *
 */
public final class Output{
	
	final static
	private String ENCODE = "MS932";
	private static Setting setting;
	static{
		setting = new Setting();
	}
	/**
	 * Chorus3Dのソースを出力する
	 * @param file　出力するファイル名
	 * @param mem　出力する膜（子膜は無視される）
	 */
	public static void out(String file, AbstractMembrane mem){
		try {
			String msg;
			HashSet linkSet = new HashSet();
			
			FileOutputStream fos = new FileOutputStream(file+".java");
			OutputStreamWriter osw = new OutputStreamWriter(fos , ENCODE);
			BufferedWriter bw = new BufferedWriter(osw);
			Iterator atomIte = mem.atomIterator();
			
			msg = header(file);
			bw.write(msg);
			
			while(atomIte.hasNext()){
				Atom atom = (Atom)atomIte.next();
				String atomName = getAtomName(atom.getid());
				msg = "        GraphNode " + atomName + " = new GraphNode(greenApp, root);\n" +
				"        graphNodes.addElement(" + atomName + ");\n";
				bw.write(msg);
				for(int i = 0; i < atom.getEdgeCount(); i++){
					Atom atom2 = atom.nthAtom(i);
					String atomName2 = getAtomName(atom2.getid());
					if(atomName.compareTo(atomName2) > 0){
						linkSet.add("        graphEdges.addElement(new GraphEdge(" +
								atomName +
								", " +
								atomName2 +
								", whiteApp, root));\n");
					}
				}
			}
			
			Iterator linkIte = linkSet.iterator();
			while(linkIte.hasNext()){
				msg = (String)linkIte.next();
				bw.write(msg);
			}

			
			
			msg = footer(file);
			bw.write(msg);
			
			bw.close();
			osw.close();
			fos.close();
			
			File javaFile = new File(file+".java");
			if(!javaFile.exists()){ return; }
			String filePath = javaFile.getAbsolutePath().substring(0, javaFile.getAbsolutePath().length() - file.length() - ".java".length());
			
			makeMakefile(file, filePath);
			Runtime runtime = Runtime.getRuntime();
			Process p;
//			p = runtime.exec("cp ./"+file+".java C:\\cygwin\\home\\Nakano\\chorus3d-0.5.6\\javademo\\");
			try {
//				p.waitFor();
				p = runtime.exec("make " + file + ".class", null, new File(filePath));
				p.waitFor();
				p = runtime.exec("cmd.exe /c start cmd.exe /c make " + file + "_run", null, new File(filePath));
				p.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("output:"+file+".java");
	}
	
	private static void makeMakefile(String file, String parentPath){
		System.out.println(Setting.getRelativeAddress(setting.getValue("JNIDIR"), file));
		String msg =
			"LIBDIR=" + Setting.getRelativeAddress(setting.getValue("JNIDIR"), file) + "\n" +
			"#\n" +
			"# Linux\n" +
			"#\n" +
			"#CLASSPATH=.:$(LIBDIR):" + setting.getValue("JDKDIR") + "/lib/ext/vecmath.jar\n" +
			"#\n" +
			"# Windows\n" +
			"#\n" +
			"CLASSPATH=.\\;$(LIBDIR)\\;" + setting.getValue("JDKDIR") + "/lib/ext/vecmath.jar\\;" + setting.getValue("JDKDIR") + "/lib/ext/j3dcore.jar\\;" + setting.getValue("JDKDIR") + "/lib/ext/j3dutils.jar\n" +
			"#\n" +
			"# common\n" +
			"#\n" +
			"#JAVAC=javac -g -classpath $(CLASSPATH)\n" +
			"JAVAC=javac -O -classpath $(CLASSPATH)\n" +
			"JAVA=java -classpath $(CLASSPATH)\n" +
			"JDB=jdb -classpath $(CLASSPATH)\n" +
			"RM=rm -f\n" +
			"\n" +
			"all: " + file + ".class\n" +
			"\n" +
			"chorus3d: \n" +
			"#	cd $(LIBDIR); make chorus3d\n" +
			"\n" +
			"" + file + ".class: chorus3d " + file + ".java\n" +
			"	$(JAVAC) " + file + ".java\n" +
			"\n" +
			"" + file + "_run: #" + file + ".class\n" +
			"	LD_LIBRARY_PATH=$(LIBDIR) PATH=\"$(LIBDIR):$(PATH)\" $(JAVA) " + file + "\n" +
			"\n" +
			"" + file + "_debug: " + file + ".class\n" +
			"	LD_LIBRARY_PATH=$(LIBDIR) PATH=\"$(LIBDIR):$(PATH)\" $(JDB) " + file + "\n" +
			"\n" +
			"clean:\n" +
			"	cd $(LIBDIR); make clean\n" +
			"	$(RM) *.class\n";
		
		try {
			System.out.println(parentPath + "Makefile");
			FileOutputStream fos = new FileOutputStream(parentPath + "Makefile");
			OutputStreamWriter osw = new OutputStreamWriter(fos , ENCODE);
			BufferedWriter bw = new BufferedWriter(osw);
			
			bw.write(msg);
			
			bw.close();
			osw.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * アトム名を返す
	 * @param i
	 * @return
	 */
	private static String getAtomName(int i){
		return "n" + i;
	}
	
	/**
	 * ソースのヘッダー
	 * @param file　ソースのファイル名
	 * @return
	 */
	private static String header(String file){
		return "import java.util.*;\n" +
		"import java.awt.*;\n" +
		"import java.awt.event.*;\n" +
		"import java.applet.*;\n" +
		"import javax.vecmath.*;\n" +
		"import javax.media.j3d.*;\n" +
		"import com.sun.j3d.utils.geometry.*;\n" +
		"import com.sun.j3d.utils.universe.*;\n" +
		"import com.sun.j3d.utils.behaviors.mouse.*;\n" +
		"import com.sun.j3d.utils.behaviors.keyboard.*;\n" +
		"import com.sun.j3d.utils.picking.*;\n" +
		"import com.sun.j3d.utils.picking.behaviors.*;\n" +
		"import com.sun.j3d.utils.applet.*; \n" +
		"import jp.ac.nii.chorus3d.*;\n" +
		"\n" +
		"public class "+file+" extends Applet implements PickingCallback {\n" +
		"\n" +
		"    private class GraphNode {\n" +
		"\n" +
		"        TransformGroup grp;\n" +
		"\n" +
		"        C3Variable3D pos;\n" +
		"\n" +
		"        GraphNode(Appearance app, Group parentGrp)\n" +
		"        {\n" +
		"            grp = new TransformGroup();\n" +
		"            grp.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);\n" +
		"            grp.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);\n" +
		"            grp.setCapability(TransformGroup.ENABLE_PICK_REPORTING);\n" +
		"            parentGrp.addChild(grp);\n" +
		"            Sphere sphere = new Sphere(.3f, app);\n" +
		"            grp.addChild(sphere);\n" +
		"\n" +
		"            pos = new C3Variable3D(\n" +
		"                new C3Domain3D(-100, -100, -100, 100, 100, 100));\n" +
		"        }\n" +
		"\n" +
		"    }\n" +
		"\n" +
		"    private class GraphEdge {\n" +
		"\n" +
		"        /*\n" +
		"        LineArray line;\n" +
		"        */\n" +
		"        ThickLineGroup lineGrp;\n" +
		"\n" +
		"        GraphNode node0;\n" +
		"        GraphNode node1;\n" +
		"\n" +
		"        GraphEdge(GraphNode node0, GraphNode node1,\n" +
		"                  Appearance app, Group parentGrp)\n" +
		"        {\n" +
		"            /*            \n" +
		"            line = new LineArray(2, GeometryArray.COORDINATES);\n" +
		"            line.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);\n" +
		"            Shape3D shape = new Shape3D(line);\n" +
		"            parentGrp.addChild(shape);\n" +
		"            */\n" +
		"            lineGrp = new ThickLineGroup(app);\n" +
		"            lineGrp.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);\n" +
		"            lineGrp.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);\n" +
		"            parentGrp.addChild(lineGrp);\n" +
		"\n" +
		"            this.node0 = node0;\n" +
		"            this.node1 = node1;\n" +
		"        }\n" +
		"\n" +
		"    }\n" +
		"\n" +
		"    private Canvas3D canvas;\n" +
		"\n" +
		"    private Vector graphNodes = new Vector();\n" +
		"    private Vector graphEdges = new Vector();\n" +
		"\n" +
		"    private C3Solver s;\n" +
		"\n" +
		"    public " + file + "()\n" +
		"    {\n" +
		"        // canvas\n" +
		"        setLayout(new BorderLayout());\n" +
		"        GraphicsConfiguration config\n" +
		"            = SimpleUniverse.getPreferredConfiguration();\n" +
		"        canvas = new Canvas3D(config);\n" +
		"        add(\"Center\", canvas);\n" +
		"\n" +
		"        //\n" +
		"        // scene graph\n" +
		"        //\n" +
		"\n" +
		"        BoundingSphere bounds\n" +
		"            = new BoundingSphere(new Point3d(0, 0, 0), 1000);\n" +
		"\n" +
		"        BranchGroup root = new BranchGroup();\n" +
		"\n" +
		"        Background bg = new Background(new Color3f(.05f, .05f, .2f));\n" +
		"        bg.setApplicationBounds(bounds);\n" +
		"        root.addChild(bg);\n" +
		"\n" +
		"        // directional light\n" +
		"        DirectionalLight dl = new DirectionalLight();\n" +
		"        dl.setDirection(new Vector3f(-1f, -3f, -2f));\n" +
		"        dl.setInfluencingBounds(bounds);\n" +
		"        root.addChild(dl);\n" +
		"\n" +
		"        // ambient light\n" +
		"        AmbientLight al = new AmbientLight();\n" +
		"        al.setInfluencingBounds(bounds);\n" +
		"        root.addChild(al);\n" +
		"\n" +
		"        // appearance for red\n" +
		"        Appearance redApp = new Appearance();\n" +
		"        Material redMat = new Material();\n" +
		"        redMat.setSpecularColor(new Color3f(1f, .4f, .1f));\n" +
		"        redMat.setDiffuseColor(new Color3f(1f, .4f, .1f));\n" +
		"        redMat.setAmbientColor(new Color3f(.25f, .1f, .025f));\n" +
		"        redApp.setMaterial(redMat);\n" +
		"\n" +
		"        // appearance for white\n" +
		"        Appearance whiteApp = new Appearance();\n" +
		"        Material whiteMat = new Material();\n" +
		"        whiteMat.setSpecularColor(new Color3f(1f, 1f, 1f));\n" +
		"        whiteMat.setDiffuseColor(new Color3f(1f, 1f, 1f));\n" +
		"        whiteMat.setAmbientColor(new Color3f(.25f, .25f, .25f));\n" +
		"        whiteApp.setMaterial(whiteMat);\n" +
		"\n" +
		"        // appearance for green\n" +
		"        Appearance greenApp = new Appearance();\n" +
		"        Material greenMat = new Material();\n" +
		"        greenMat.setSpecularColor(new Color3f(.4f, 1f, .2f));\n" +
		"        greenMat.setDiffuseColor(new Color3f(.4f, 1f, .2f));\n" +
		"        greenMat.setAmbientColor(new Color3f(.1f, .25f, .05f));\n" +
		"        greenApp.setMaterial(greenMat);\n" +
		"\n" +
		"        // graph nodes\n";
	}
	
	/**
	 * ソースのフッター
	 * @return
	 */
	private static String footer(String file){
		return "\n" +
		"        // picking\n" +
		"        PickTranslateBehavior pickTranslate\n" +
		"            = new PickTranslateBehavior(root, canvas, bounds);\n" +
		"        pickTranslate.setMode(PickTool.BOUNDS);\n" +
		"        pickTranslate.setupCallback(this);\n" +
		"        root.addChild(pickTranslate);\n" +
		"        /*\n" +
		"        PickZoomBehavior pickZoom\n" +
		"            = new PickZoomBehavior(root, canvas, bounds);\n" +
		"        pickZoom.setMode(PickTool.BOUNDS);\n" +
		"        pickZoom.setupCallback(this);\n" +
		"        root.addChild(pickZoom);\n" +
		"        */\n" +
		"\n" +
		"        SimpleUniverse univ = new SimpleUniverse(canvas);\n" +
		"\n" +
		"        // navigation\n" +
		"        univ.getViewingPlatform().setNominalViewingTransform();\n" +
		"        TransformGroup vpTG\n" +
		"            = univ.getViewingPlatform().getViewPlatformTransform();\n" +
		"        Transform3D vpTGM = new Transform3D();\n" +
		"        vpTGM.set(new Vector3d(0, 0, 2.6));\n" +
		"        vpTG.setTransform(vpTGM);\n" +
		"        KeyNavigatorBehavior keyBehavior = new KeyNavigatorBehavior(vpTG);\n" +
		"        keyBehavior.setSchedulingBounds(bounds);\n" +
		"        root.addChild(keyBehavior);\n" +
		"\n" +
		"        root.compile();\n" +
		"        univ.addBranchGraph(root);\n" +
		"\n" +
		"        //\n" +
		"        // constraint system\n" +
		"        //\n" +
		"\n" +
		"        // constraint solver\n" +
		"//         s = new C3Solver(10, 10);\n" +
		"        s = new C3Solver(1, 1);\n" +
		"\n" +
		"        for (int i = 0; i < graphEdges.size(); i++) {\n" +
		"            GraphEdge e = (GraphEdge) graphEdges.elementAt(i);\n" +
		"            C3GraphLayoutConstraint c\n" +
		"                = new C3GraphLayoutConstraint(e.node0.pos, e.node1.pos, 1);\n" +
		"            s.add(c);\n" +
		"        }\n" +
		"\n" +
		"        //s.addStay(nd.tfm.translation(), C3.REQUIRED);\n" +
//		"        s.add(new C3LinearConstraint(1, n0.pos.x(), C3.EQ, 0, C3.REQUIRED));\n" +
//		"        s.add(new C3LinearConstraint(1, n0.pos.y(), C3.EQ, 0, C3.REQUIRED));\n" +
//		"        s.add(new C3LinearConstraint(1, n0.pos.z(), C3.EQ, 0, C3.REQUIRED));\n" +
		"\n" +
		"        // solve the system\n" +
		"        solve(false);\n" +
		"    }\n" +
		"\n" +
		"    public void transformChanged(int type, TransformGroup tg)\n" +
		"    {\n" +
		"        System.out.println(\"\" + type + \": \" + tg);\n" +
		"\n" +
		"        GraphNode node = null;\n" +
		"        if (tg != null) {\n" +
		"            for (int i = 0; i < graphNodes.size(); i++) {\n" +
		"                GraphNode n = (GraphNode) graphNodes.elementAt(i);\n" +
		"                if (n.grp == tg) {\n" +
		"                    node = n;\n" +
		"                    break;\n" +
		"                }\n" +
		"            }\n" +
		"            \n" +
		"            // get new position\n" +
		"            Transform3D t = new Transform3D();\n" +
		"            tg.getTransform(t);\n" +
		"            Vector3d tln = new Vector3d();\n" +
		"            t.get(tln);\n" +
		"\n" +
		"            // suggest new position\n" +
		"            s.addEditVar(node.pos);\n" +
		"            s.beginEdit();\n" +
		"            s.suggestValue(node.pos, tln);\n" +
		"        }\n" +
		"\n" +
		"        solve(true);\n" +
		"\n" +
		"        if (node != null)\n" +
		"            s.endEdit();\n" +
		"    }\n" +
		"\n" +
		"    private void solve(boolean resolve)\n" +
		"    {\n" +
		"        long t0 = System.currentTimeMillis();\n" +
		"        if (resolve)\n" +
		"            s.resolve();\n" +
		"        else\n" +
		"            s.solve();\n" +
		"        long t1 = System.currentTimeMillis();\n" +
		"        System.out.println(\"time: \" + (t1 - t0) + \" ms\");\n" +
		"\n" +
		"        for (int i = 0; i < graphNodes.size(); i++) {\n" +
		"            GraphNode n = (GraphNode) graphNodes.elementAt(i);\n" +
		"            Transform3D t = new Transform3D();\n" +
		"            t.set(n.pos.vectorValue());\n" +
		"            n.grp.setTransform(t);\n" +
		"        }        \n" +
		"\n" +
		"        for (int i = 0; i < graphEdges.size(); i++) {\n" +
		"            GraphEdge e = (GraphEdge) graphEdges.elementAt(i);\n" +
		"            /*\n" +
		"            e.line.setCoordinate(0, e.node0.pos.pointValue());\n" +
		"            e.line.setCoordinate(1, e.node1.pos.pointValue());\n" +
		"            */\n" +
		"            e.lineGrp.setCoordinates(e.node0.pos.pointValue(),\n" +
		"                                     e.node1.pos.pointValue());\n" +
		"        }\n" +
		"    }\n" +
		"\n" +
		"    public static void main(String[] args)\n" +
		"    {\n" +
		"        Frame frame = new MainFrame(new " + file + "(), 500, 500);\n" +
		"    }\n" +
		"\n" +
		"}\n";
	}
}