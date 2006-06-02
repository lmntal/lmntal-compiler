package chorus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

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
			HashMap atomMap = new HashMap();
			TreeMap idMap = new TreeMap();
			boolean afterRelease = true;
			int sequence = 0;
			
			FileOutputStream fos = new FileOutputStream(file+".java");
			OutputStreamWriter osw = new OutputStreamWriter(fos , ENCODE);
			BufferedWriter bw = new BufferedWriter(osw);
			Iterator atomIte = mem.atomIterator();
			
			while(atomIte.hasNext()){
				Atom atom = (Atom)atomIte.next();
				Integer atomName = new Integer(sequence);
				sequence++;
				atomMap.put(atom, atomName);
				idMap.put(atomName, atom);
			}
			
			if(sequence < Integer.parseInt(setting.getValue("AFTER_RELEASE"))){ afterRelease = false;}
			msg = header(file, afterRelease);
			bw.write(msg);
			
			msg = "        LinkedList nodeList = new LinkedList();\n" +
				"        HashMap atomMap = getAtom();\n";
			bw.write(msg);
			
			msg = "        for(int nodeCount = 0; nodeCount < " + idMap.size() + "; nodeCount++){\n"+
			"        	GraphNode node = new GraphNode(atomApp, root, (Float)atomMap.get(nodeCount));\n"+
			"        	graphNodes.addElement(node);\n"+
			"        	nodeList.add(node);\n"+
			"        }\n";
			bw.write(msg);
			
			msg = footer(file);
			bw.write(msg);
			
			bw.close();
			osw.close();
			fos.close();
			
			File javaFile = new File(file+".java");
			if(!javaFile.exists()){ return; }
			String filePath = javaFile.getAbsolutePath().substring(0, javaFile.getAbsolutePath().length() - file.length() - ".java".length());
			
			outPutEdgeFile(idMap, atomMap, file);
			outPutAtomFile(idMap, atomMap, file);
			makeMakefile(file, filePath);
			Runtime runtime = Runtime.getRuntime();
			Process p;
			try {
				p = runtime.exec("make -f " + file + "_Makefile " + file + ".class", null, new File(filePath));
				p.waitFor();
				p = runtime.exec("cmd.exe /c start cmd.exe /c make -f " + file + "_Makefile " + file + "_run", null, new File(filePath));
				p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("output:"+file+".java");
	}
	
	/**
	 * リンクの設定ファイルを出力する．
	 * @param idMap IDとアトムを対応させたマップ
	 * @param atomMap　アトムとIDを対応させたマップ
	 * @param file
	 */
	private static void outPutAtomFile(Map idMap, Map atomMap, String file){
		String msg;
		Iterator atomIte = idMap.keySet().iterator();

		try {
			FileOutputStream fos = new FileOutputStream(file+"_Atom.dat");
			OutputStreamWriter osw = new OutputStreamWriter(fos , ENCODE);
			BufferedWriter bw = new BufferedWriter(osw);
			
			while(atomIte.hasNext()){
				Integer atomID = (Integer)atomIte.next();
				Atom atom = (Atom)idMap.get(atomID);
				String size =
					(null != setting.tryGetValue(atom.getName()))
					? setting.tryGetValue(atom.getName())
					: setting.getValue("ATOM_SIZE");

				for(int i = 0; i < atom.getEdgeCount(); i++){
					msg = atomID +
					"," +
					size +
					"\n";
					bw.write(msg);
				}
			}
			
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
		System.out.println("output:" + file + "_Atom.dat");
	}
	
	private static void outPutEdgeFile(Map idMap, Map atomMap, String file){
		String msg;
		Iterator atomIte = idMap.keySet().iterator();

		try {
			FileOutputStream fos = new FileOutputStream(file+"_Edge.dat");
			OutputStreamWriter osw = new OutputStreamWriter(fos , ENCODE);
			BufferedWriter bw = new BufferedWriter(osw);
			
			while(atomIte.hasNext()){
				Integer atomID = (Integer)atomIte.next();
				Atom atom = (Atom)idMap.get(atomID);
				String atomName = getAtomName(atomID.intValue());

//			msg = "        GraphNode " + getAtomName(atomID.intValue()) + " = new GraphNode(atomApp, root);\n" +
//			"        graphNodes.addElement(" + getAtomName(atomID.intValue()) + ");\n";
//			bw.write(msg);
				
				for(int i = 0; i < atom.getEdgeCount(); i++){
					Atom atom2 = atom.nthAtom(i);
					String atomName2 = getAtomName(((Integer)atomMap.get(atom2)).intValue());
					if(atomName.compareTo(atomName2) > 0){
						msg = atomID +
								"," +
								((Integer)atomMap.get(atom2)).intValue() +
								",1" +
								"\n";
						bw.write(msg);
					}
				}
			}
			
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
		System.out.println("output:" + file + "_Edge.dat");
	}
	
	/**
	 * Makefileを出力する
	 * @param file　出力するjavaファイルのファイル名．
	 * @param parentPath 出力するディレクトリ
	 */
	private static void makeMakefile(String file, String parentPath){
		String msg =
			"LIBDIR=" + Setting.getRelativeAddress(setting.getFilePass("JNIDIR"), file) + "\n" +
			"#\n" +
			"# Linux\n" +
			"#\n" +
			"#CLASSPATH=.:$(LIBDIR):" + setting.getFilePass("JDKDIR") + "/lib/ext/vecmath.jar\n" +
			"#\n" +
			"# Windows\n" +
			"#\n" +
			"CLASSPATH=.\\;$(LIBDIR)\\;" + setting.getFilePass("JDKDIR") + "/lib/ext/vecmath.jar\\;" + setting.getFilePass("JDKDIR") + "/lib/ext/j3dcore.jar\\;" + setting.getFilePass("JDKDIR") + "/lib/ext/j3dutils.jar\n" +
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
			System.out.println("output:" + file + "_Makefile");
			FileOutputStream fos = new FileOutputStream(parentPath + file + "_Makefile");
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
	private static String header(String file, boolean afterRelease){
		return "import java.util.*;\n" +
		"import java.awt.*;\n" +
		"import java.awt.event.*;\n" +
		"import java.applet.*;\n" +
		"import java.util.LinkedList;\n" +
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
		"import java.io.FileReader;\n" +
		"import java.io.IOException;\n" +
		"\n" +
		"public class "+file+" extends Applet implements PickingCallback {\n" +
		"\n" +
		"    private class GraphNode {\n" +
		"\n" +
		"        TransformGroup grp;\n" +
		"\n" +
		"        C3Variable3D pos;\n" +
		"\n" +
		"        GraphNode(Appearance app, Group parentGrp, float size)\n" +
		"        {\n" +
		"            grp = new TransformGroup();\n" +
		"            grp.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);\n" +
		"            grp.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);\n" +
		"            grp.setCapability(TransformGroup.ENABLE_PICK_REPORTING);\n" +
		"            parentGrp.addChild(grp);\n" +
		"            Sphere sphere = new Sphere(size, app);\n" +
		"            grp.addChild(sphere);\n" +
		"\n" +
		"            pos = new C3Variable3D(\n" +
		"                new C3Domain3D(-100, -100, -100, 100, 100, 100));\n" +
		"        }\n" +
		"\n" +
		"    }\n" +
		"\n" +
		"    private class ThickLineGroup extends TransformGroup {\n" +
		"    \n" +
		"        Cylinder cylinder;\n" +
		"\n" +
		"        ThickLineGroup(Appearance app)\n" +
		"        {\n" +
		"            cylinder = new Cylinder(.025f, 2f, app);\n" +
		"            addChild(cylinder);\n" +
		"        }\n" +
		"\n" +
		"        void setCoordinates(Point3d c0, Point3d c1)\n" +
		"        {\n" +
		"            // compute translation\n" +
		"            Vector3d mid = new Vector3d((c0.x + c1.x) / 2,\n" +
		"                                        (c0.y + c1.y) / 2,\n" +
		"                                        (c0.z + c1.z) / 2);\n" +
		"\n" +
		"            // compute axes\n" +
		"            Vector3d y = new Vector3d();\n" +
		"            y.sub(c0, mid);\n" +
		"            Vector3d x = new Vector3d();\n" +
		"            x.cross(y, new Vector3d(0, 0, 1));\n" +
		"            Vector3d z = new Vector3d();\n" +
		"            if (x.lengthSquared() > .01) {\n" +
		"                x.normalize();\n" +
		"                z.cross(x, y);\n" +
		"                z.normalize();\n" +
		"            }\n" +
		"            else {\n" +
		"                z.cross(new Vector3d(1, 0, 0), y);\n" +
		"                z.normalize();\n" +
		"                x.cross(y, z);\n" +
		"                x.normalize();\n" +
		"            }\n" +
		"\n" +
		"            // generate matrix\n" +
		"            Matrix3d m = new Matrix3d();\n" +
		"            m.setColumn(0, x);\n" +
		"            m.setColumn(1, y);\n" +
		"            m.setColumn(2, z);\n" +
		"            Transform3D t = new Transform3D(m, mid, 1);\n" +
		"\n" +
		"            setTransform(t);\n" +
		"        }\n" +
		"\n" +
		"    }\n" +
		"    private class GraphEdge {\n" +
		"\n" +
		"        /*\n" +
		"        LineArray line;\n" +
		"        */\n" +
		"        ThickLineGroup lineGrp;\n" +
		"\n" +
		"        double edgeLength;\n" +
		"        GraphNode node0;\n" +
		"        GraphNode node1;\n" +
		"\n" +
		"        GraphEdge(GraphNode node0, GraphNode node1,\n" +
		"                  Appearance app, Group parentGrp, double length)\n" +
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
		"            edgeLength = length;\n" +
		"        }\n" +
		"\n" +
		"    }\n" +
		"\n" +
		"    private Canvas3D canvas;\n" +
		"\n" +
		"    private Vector graphNodes = new Vector();\n" +
		"    private Vector graphEdges = new Vector();\n" +
		"    private TransformGroup needResolve = null;\n" +
		"    final static private boolean RESOLVE_AFTER_RELEASE = "+ afterRelease +";\n" +
		"\n" +
		"    private C3Solver s;\n" +
		"\n" +
		"	private LinkedList getEdge(){\n" +
		"        //  Edge取得\n" +
		"		int i;\n" +
		"		StringBuffer s = new StringBuffer();\n" +
		"		String key = null;\n" +
		"		String value = null;\n" +
		"		String length = null;\n" +
		"		boolean isKey = true;\n" +
		"		LinkedList edgeList = new LinkedList();\n" +
		"		try {\n" +
		"			FileReader file = new FileReader(\""+ file +"_Edge.dat\");\n" +
		"			while((i = file.read()) != -1){\n" +
		"				// key\n" +
		"				if((char)i == ',' && isKey){\n" +
		"					key = s.toString();\n" +
		"					s = new StringBuffer();\n" +
		"					isKey = false;\n" +
		"					continue;\n" +
		"				}\n" +
		"				// value\n" +
		"				if((char)i == ',' && !isKey){\n" +
		"					value = s.toString();\n" +
		"					s = new StringBuffer();\n" +
		"					continue;\n" +
		"				}\n" +
		"				// value\n" +
		"				else if((char)i == '\\n' || (char)i == '\\r'){\n" +
		"					if(isKey || key == null){\n" +
		"						key = value = length = null;\n" +
		"						continue;\n" +
		"					}\n" +
		"					length = s.toString();\n" +
		"					s = new StringBuffer();\n" +
		"					isKey = true;\n" +
		"					edgeList.add(Integer.parseInt(key));\n" +
		"					edgeList.add(Integer.parseInt(value));\n" +
		"					edgeList.add(Integer.parseInt(length));\n" +
		"					key = value = length = null;\n" +
		"					continue;\n" +
		"				}\n" +
		"				// 文字取得\n" +
		"				s.append((char)i);\n" +
		"			}\n" +
		"		} catch (IOException e) {\n" +
		"			// TODO Auto-generated catch block\n" +
		"			e.printStackTrace();\n" +
		"		}\n" +
		"       return edgeList;\n" +
		"	}\n" +
		"	private HashMap getAtom(){\n" +
		"        //  Atom取得\n" +
		"		int i;\n" +
		"		StringBuffer s = new StringBuffer();\n" +
		"		String key = null;\n" +
		"		String value = null;\n" +
		"		boolean isKey = true;\n" +
		"		HashMap atomMap = new HashMap();\n" +
		"		try {\n" +
		"			FileReader file = new FileReader(\""+ file +"_Atom.dat\");\n" +
		"			while((i = file.read()) != -1){\n" +
		"				// key\n" +
		"				if((char)i == ','){\n" +
		"					key = s.toString();\n" +
		"					s = new StringBuffer();\n" +
		"					isKey = false;\n" +
		"					continue;\n" +
		"				}\n" +
		"				// value\n" +
		"				else if((char)i == '\\n' || (char)i == '\\r'){\n" +
		"					if(isKey || key == null){\n" +
		"						key = value = null;\n" +
		"						continue;\n" +
		"					}\n" +
		"					value = s.toString();\n" +
		"					s = new StringBuffer();\n" +
		"					isKey = true;\n" +
		"					atomMap.put(Integer.parseInt(key), Float.parseFloat(value));\n" +
		"					key = value = null;\n" +
		"					continue;\n" +
		"				}\n" +
		"				// 文字取得\n" +
		"				s.append((char)i);\n" +
		"			}\n" +
		"		} catch (IOException e) {\n" +
		"			// TODO Auto-generated catch block\n" +
		"			e.printStackTrace();\n" +
		"		}\n" +
		"       return atomMap;\n" +
		"	}\n" +
		"    public " + file + "()\n" +
		"    {\n" +
		"        // canvas\n" +
		"        setLayout(new BorderLayout());\n" +
		"        GraphicsConfiguration config\n" +
		"            = SimpleUniverse.getPreferredConfiguration();\n" +
		"        canvas = new Canvas3D(config);\n" +
		"        canvas.addMouseListener(new MouseListener() {\n" +
		"		\n" +
		"			public void mouseExited(MouseEvent e) {\n" +
		"				// TODO Auto-generated method stub\n" +
		"		\n" +
		"			}\n" +
		"		\n" +
		"			public void mouseEntered(MouseEvent e) {\n" +
		"				// TODO Auto-generated method stub\n" +
		"		\n" +
		"			}\n" +
		"		\n" +
		"			public void mouseReleased(MouseEvent e) {\n" +
		"				// TODO Auto-generated method stub\n" +
		"		    	if(null != needResolve){\n" +
		"\n" +
		"                   GraphNode node = null;\n" +
		"                   for (int i = 0; i < graphNodes.size(); i++) {\n" +
		"                       GraphNode n = (GraphNode) graphNodes.elementAt(i);\n" +
		"                       if (n.grp == needResolve) {\n" +
		"                           node = n;\n" +
		"                           break;\n" +
		"                       }\n" +
		"                   }\n" +
		"\n" +
		"                   // get new position\n" +
		"                   Transform3D t = new Transform3D();\n" +
		"                   needResolve.getTransform(t);\n" +
		"                   Vector3d tln = new Vector3d();\n" +
		"                   t.get(tln);\n" +
		"\n" +
		"                   // suggest new position\n" +
		"                   s.addEditVar(node.pos);\n" +
		"                   s.beginEdit();\n" +
		"                   s.suggestValue(node.pos, tln);\n" +
		"		    	    solve(true);\n" +
		"		    	    needResolve = null;\n" +
		"		    	    if (node != null)\n" +
		"		    	        s.endEdit();\n" +
		"		    	}\n" +
		"			}\n" +
		"		\n" +
		"			public void mousePressed(MouseEvent e) {\n" +
		"				// TODO Auto-generated method stub\n" +
		"		\n" +
		"			}\n" +
		"		\n" +
		"			public void mouseClicked(MouseEvent e) {\n" +
		"				// TODO Auto-generated method stub\n" +
		"		\n" +
		"			}\n" +
		"		\n" +
		"		});\n" +
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
		"        // appearance for white\n" +
		"        Appearance whiteApp = new Appearance();\n" +
		"        Material whiteMat = new Material();\n" +
		"        whiteMat.setSpecularColor(new Color3f(1f, 1f, 1f));\n" +
		"        whiteMat.setDiffuseColor(new Color3f(1f, 1f, 1f));\n" +
		"        whiteMat.setAmbientColor(new Color3f(.25f, .25f, .25f));\n" +
		"        whiteApp.setMaterial(whiteMat);\n" +
		"\n" +
		"        // appearance for atom\n" +
		"        Appearance atomApp = new Appearance();\n" +
		"        Material atomMat = new Material();\n" +
		"        atomMat.setSpecularColor(new Color3f(" + setting.getValue("SPECULAR_COLOR") + "));\n" +
		"        atomMat.setDiffuseColor(new Color3f(" + setting.getValue("DIFFUSE_COLOR") + "));\n" +
		"        atomMat.setAmbientColor(new Color3f(" + setting.getValue("AMBIENT_COLOR") + "));\n" +
		"        atomApp.setMaterial(atomMat);\n" +
		"\n" +
		"        // graph nodes\n";
	}
	
	/**
	 * ソースのフッター
	 * @return
	 */
	private static String footer(String file){
		return "\n" +
		"        // graph edges\n" +
		"        LinkedList edgeList = getEdge();\n" +
		"        for(int edgeCount = 0; edgeCount < edgeList.size(); edgeCount = edgeCount + 3){\n" +
		"        	GraphNode node1 = (GraphNode)nodeList.get(((Integer)edgeList.get(edgeCount)).intValue());\n" +
		"        	GraphNode node2 = (GraphNode)nodeList.get(((Integer)edgeList.get(edgeCount + 1)).intValue());\n" +
		"            graphEdges.addElement(new GraphEdge(node1, node2, whiteApp, root, ((Integer)edgeList.get(edgeCount + 2)).intValue()));\n" +
		"        }\n" +	
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
		"                = new C3GraphLayoutConstraint(e.node0.pos, e.node1.pos, e.edgeLength);\n" +
		"            s.add(c);\n" +
		"        }\n" +
		"\n" +
		"        //s.addStay(nd.tfm.translation(), C3.REQUIRED);\n" +
		"        s.add(new C3LinearConstraint(1, ((GraphNode)nodeList.get(0)).pos.x(), C3.EQ, 0, C3.REQUIRED));\n" +
		"        s.add(new C3LinearConstraint(1, ((GraphNode)nodeList.get(0)).pos.y(), C3.EQ, 0, C3.REQUIRED));\n" +
		"        s.add(new C3LinearConstraint(1, ((GraphNode)nodeList.get(0)).pos.z(), C3.EQ, 0, C3.REQUIRED));\n" +
		"\n" +
		"        // solve the system\n" +
		"        solve(false);\n" +
		"    }\n" +
		"\n" +
		"    public void transformChanged(int type, TransformGroup tg)\n" +
		"    {\n" +
		"        System.out.println(\"\" + type + \": \" + tg);\n" +
		"        if(RESOLVE_AFTER_RELEASE){\n" +
		"        	needResolve = tg;\n" +
		"        }\n" +
		"        else{\n" +
		"            GraphNode node = null;\n" +
		"            if (tg != null) {\n" +
		"                for (int i = 0; i < graphNodes.size(); i++) {\n" +
		"                    GraphNode n = (GraphNode) graphNodes.elementAt(i);\n" +
		"                    if (n.grp == tg) {\n" +
		"                        node = n;\n" +
		"                        break;\n" +
		"                    }\n" +
		"                }\n" +
		"                \n" +
		"                // get new position\n" +
		"                Transform3D t = new Transform3D();\n" +
		"                tg.getTransform(t);\n" +
		"                Vector3d tln = new Vector3d();\n" +
		"                t.get(tln);\n" +
		"\n" +
		"                // suggest new position\n" +
		"                s.addEditVar(node.pos);\n" +
		"                s.beginEdit();\n" +
		"                s.suggestValue(node.pos, tln);\n" +
		"            }\n" +
		"\n" +
		"            solve(true);\n" +
		"\n" +
		"            if (node != null)\n" +
		"                s.endEdit();\n" +
		"        }\n" +
		"\n" +
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