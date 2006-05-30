import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.vecmath.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.behaviors.keyboard.*;
import com.sun.j3d.utils.picking.*;
import com.sun.j3d.utils.picking.behaviors.*;
import com.sun.j3d.utils.applet.*; 
import jp.ac.nii.chorus3d.*;

public class test extends Applet implements PickingCallback {

    private class GraphNode {

        TransformGroup grp;

        C3Variable3D pos;

        GraphNode(Appearance app, Group parentGrp)
        {
            grp = new TransformGroup();
            grp.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            grp.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
            grp.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
            parentGrp.addChild(grp);
            Sphere sphere = new Sphere(0.2f, app);
            grp.addChild(sphere);

            pos = new C3Variable3D(
                new C3Domain3D(-100, -100, -100, 100, 100, 100));
        }

    }

    private class ThickLineGroup extends TransformGroup {
    
        Cylinder cylinder;

        ThickLineGroup(Appearance app)
        {
            cylinder = new Cylinder(.025f, 2f, app);
            addChild(cylinder);
        }

        void setCoordinates(Point3d c0, Point3d c1)
        {
            // compute translation
            Vector3d mid = new Vector3d((c0.x + c1.x) / 2,
                                        (c0.y + c1.y) / 2,
                                        (c0.z + c1.z) / 2);

            // compute axes
            Vector3d y = new Vector3d();
            y.sub(c0, mid);
            Vector3d x = new Vector3d();
            x.cross(y, new Vector3d(0, 0, 1));
            Vector3d z = new Vector3d();
            if (x.lengthSquared() > .01) {
                x.normalize();
                z.cross(x, y);
                z.normalize();
            }
            else {
                z.cross(new Vector3d(1, 0, 0), y);
                z.normalize();
                x.cross(y, z);
                x.normalize();
            }

            // generate matrix
            Matrix3d m = new Matrix3d();
            m.setColumn(0, x);
            m.setColumn(1, y);
            m.setColumn(2, z);
            Transform3D t = new Transform3D(m, mid, 1);

            setTransform(t);
        }

    }
    private class GraphEdge {

        /*
        LineArray line;
        */
        ThickLineGroup lineGrp;

        GraphNode node0;
        GraphNode node1;

        GraphEdge(GraphNode node0, GraphNode node1,
                  Appearance app, Group parentGrp)
        {
            /*            
            line = new LineArray(2, GeometryArray.COORDINATES);
            line.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
            Shape3D shape = new Shape3D(line);
            parentGrp.addChild(shape);
            */
            lineGrp = new ThickLineGroup(app);
            lineGrp.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            lineGrp.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
            parentGrp.addChild(lineGrp);

            this.node0 = node0;
            this.node1 = node1;
        }

    }

    private Canvas3D canvas;

    private Vector graphNodes = new Vector();
    private Vector graphEdges = new Vector();
    private TransformGroup needResolve = null;
    final static private boolean RESOLVE_AFTER_RELEASE = true;

    private C3Solver s;

    public test()
    {
        // canvas
        setLayout(new BorderLayout());
        GraphicsConfiguration config
            = SimpleUniverse.getPreferredConfiguration();
        canvas = new Canvas3D(config);
        canvas.addMouseListener(new MouseListener() {
		
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
		
			}
		
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
		
			}
		
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
		    	if(null != needResolve){

                   GraphNode node = null;
                   for (int i = 0; i < graphNodes.size(); i++) {
                       GraphNode n = (GraphNode) graphNodes.elementAt(i);
                       if (n.grp == needResolve) {
                           node = n;
                           break;
                       }
                   }

                   // get new position
                   Transform3D t = new Transform3D();
                   needResolve.getTransform(t);
                   Vector3d tln = new Vector3d();
                   t.get(tln);

                   // suggest new position
                   s.addEditVar(node.pos);
                   s.beginEdit();
                   s.suggestValue(node.pos, tln);
		    	    solve(true);
		    	    needResolve = null;
		    	    if (node != null)
		    	        s.endEdit();
		    	}
			}
		
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
		
			}
		
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
		
			}
		
		});
        add("Center", canvas);

        //
        // scene graph
        //

        BoundingSphere bounds
            = new BoundingSphere(new Point3d(0, 0, 0), 1000);

        BranchGroup root = new BranchGroup();

        Background bg = new Background(new Color3f(.05f, .05f, .2f));
        bg.setApplicationBounds(bounds);
        root.addChild(bg);

        // directional light
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-1f, -3f, -2f));
        dl.setInfluencingBounds(bounds);
        root.addChild(dl);

        // ambient light
        AmbientLight al = new AmbientLight();
        al.setInfluencingBounds(bounds);
        root.addChild(al);

        // appearance for white
        Appearance whiteApp = new Appearance();
        Material whiteMat = new Material();
        whiteMat.setSpecularColor(new Color3f(1f, 1f, 1f));
        whiteMat.setDiffuseColor(new Color3f(1f, 1f, 1f));
        whiteMat.setAmbientColor(new Color3f(.25f, .25f, .25f));
        whiteApp.setMaterial(whiteMat);

        // appearance for atom
        Appearance atomApp = new Appearance();
        Material atomMat = new Material();
        atomMat.setSpecularColor(new Color3f(0.4f,1f,0.1f));
        atomMat.setDiffuseColor(new Color3f(0.4f,1f,0.1f));
        atomMat.setAmbientColor(new Color3f(0.1f,0.25f,0.05f));
        atomApp.setMaterial(atomMat);

        // graph nodes
        GraphNode n0 = new GraphNode(atomApp, root);
        graphNodes.addElement(n0);
        GraphNode n1 = new GraphNode(atomApp, root);
        graphNodes.addElement(n1);
        GraphNode n2 = new GraphNode(atomApp, root);
        graphNodes.addElement(n2);
        GraphNode n3 = new GraphNode(atomApp, root);
        graphNodes.addElement(n3);
        GraphNode n4 = new GraphNode(atomApp, root);
        graphNodes.addElement(n4);
        GraphNode n5 = new GraphNode(atomApp, root);
        graphNodes.addElement(n5);
        GraphNode n6 = new GraphNode(atomApp, root);
        graphNodes.addElement(n6);
        GraphNode n7 = new GraphNode(atomApp, root);
        graphNodes.addElement(n7);
        GraphNode n8 = new GraphNode(atomApp, root);
        graphNodes.addElement(n8);
        GraphNode n9 = new GraphNode(atomApp, root);
        graphNodes.addElement(n9);
        GraphNode n10 = new GraphNode(atomApp, root);
        graphNodes.addElement(n10);
        GraphNode n11 = new GraphNode(atomApp, root);
        graphNodes.addElement(n11);
        GraphNode n12 = new GraphNode(atomApp, root);
        graphNodes.addElement(n12);
        GraphNode n13 = new GraphNode(atomApp, root);
        graphNodes.addElement(n13);
        GraphNode n14 = new GraphNode(atomApp, root);
        graphNodes.addElement(n14);
        GraphNode n15 = new GraphNode(atomApp, root);
        graphNodes.addElement(n15);
        GraphNode n16 = new GraphNode(atomApp, root);
        graphNodes.addElement(n16);
        GraphNode n17 = new GraphNode(atomApp, root);
        graphNodes.addElement(n17);
        GraphNode n18 = new GraphNode(atomApp, root);
        graphNodes.addElement(n18);
        GraphNode n19 = new GraphNode(atomApp, root);
        graphNodes.addElement(n19);
        GraphNode n20 = new GraphNode(atomApp, root);
        graphNodes.addElement(n20);
        GraphNode n21 = new GraphNode(atomApp, root);
        graphNodes.addElement(n21);
        GraphNode n22 = new GraphNode(atomApp, root);
        graphNodes.addElement(n22);
        GraphNode n23 = new GraphNode(atomApp, root);
        graphNodes.addElement(n23);
        GraphNode n24 = new GraphNode(atomApp, root);
        graphNodes.addElement(n24);
        GraphNode n25 = new GraphNode(atomApp, root);
        graphNodes.addElement(n25);
        GraphNode n26 = new GraphNode(atomApp, root);
        graphNodes.addElement(n26);
        GraphNode n27 = new GraphNode(atomApp, root);
        graphNodes.addElement(n27);
        GraphNode n28 = new GraphNode(atomApp, root);
        graphNodes.addElement(n28);
        GraphNode n29 = new GraphNode(atomApp, root);
        graphNodes.addElement(n29);
        GraphNode n30 = new GraphNode(atomApp, root);
        graphNodes.addElement(n30);
        GraphNode n31 = new GraphNode(atomApp, root);
        graphNodes.addElement(n31);
        GraphNode n32 = new GraphNode(atomApp, root);
        graphNodes.addElement(n32);
        GraphNode n33 = new GraphNode(atomApp, root);
        graphNodes.addElement(n33);
        GraphNode n34 = new GraphNode(atomApp, root);
        graphNodes.addElement(n34);
        GraphNode n35 = new GraphNode(atomApp, root);
        graphNodes.addElement(n35);
        GraphNode n36 = new GraphNode(atomApp, root);
        graphNodes.addElement(n36);
        GraphNode n37 = new GraphNode(atomApp, root);
        graphNodes.addElement(n37);
        GraphNode n38 = new GraphNode(atomApp, root);
        graphNodes.addElement(n38);
        GraphNode n39 = new GraphNode(atomApp, root);
        graphNodes.addElement(n39);
        GraphNode n40 = new GraphNode(atomApp, root);
        graphNodes.addElement(n40);
        GraphNode n41 = new GraphNode(atomApp, root);
        graphNodes.addElement(n41);
        GraphNode n42 = new GraphNode(atomApp, root);
        graphNodes.addElement(n42);
        GraphNode n43 = new GraphNode(atomApp, root);
        graphNodes.addElement(n43);
        GraphNode n44 = new GraphNode(atomApp, root);
        graphNodes.addElement(n44);
        GraphNode n45 = new GraphNode(atomApp, root);
        graphNodes.addElement(n45);
        GraphNode n46 = new GraphNode(atomApp, root);
        graphNodes.addElement(n46);
        GraphNode n47 = new GraphNode(atomApp, root);
        graphNodes.addElement(n47);
        GraphNode n48 = new GraphNode(atomApp, root);
        graphNodes.addElement(n48);
        GraphNode n49 = new GraphNode(atomApp, root);
        graphNodes.addElement(n49);
        GraphNode n50 = new GraphNode(atomApp, root);
        graphNodes.addElement(n50);
        GraphNode n51 = new GraphNode(atomApp, root);
        graphNodes.addElement(n51);
        GraphNode n52 = new GraphNode(atomApp, root);
        graphNodes.addElement(n52);
        GraphNode n53 = new GraphNode(atomApp, root);
        graphNodes.addElement(n53);
        GraphNode n54 = new GraphNode(atomApp, root);
        graphNodes.addElement(n54);
        GraphNode n55 = new GraphNode(atomApp, root);
        graphNodes.addElement(n55);
        GraphNode n56 = new GraphNode(atomApp, root);
        graphNodes.addElement(n56);
        GraphNode n57 = new GraphNode(atomApp, root);
        graphNodes.addElement(n57);
        GraphNode n58 = new GraphNode(atomApp, root);
        graphNodes.addElement(n58);
        GraphNode n59 = new GraphNode(atomApp, root);
        graphNodes.addElement(n59);
        GraphNode n60 = new GraphNode(atomApp, root);
        graphNodes.addElement(n60);
        GraphNode n61 = new GraphNode(atomApp, root);
        graphNodes.addElement(n61);
        GraphNode n62 = new GraphNode(atomApp, root);
        graphNodes.addElement(n62);
        GraphNode n63 = new GraphNode(atomApp, root);
        graphNodes.addElement(n63);
        GraphNode n64 = new GraphNode(atomApp, root);
        graphNodes.addElement(n64);
        GraphNode n65 = new GraphNode(atomApp, root);
        graphNodes.addElement(n65);
        GraphNode n66 = new GraphNode(atomApp, root);
        graphNodes.addElement(n66);
        GraphNode n67 = new GraphNode(atomApp, root);
        graphNodes.addElement(n67);
        GraphNode n68 = new GraphNode(atomApp, root);
        graphNodes.addElement(n68);
        GraphNode n69 = new GraphNode(atomApp, root);
        graphNodes.addElement(n69);
        GraphNode n70 = new GraphNode(atomApp, root);
        graphNodes.addElement(n70);
        GraphNode n71 = new GraphNode(atomApp, root);
        graphNodes.addElement(n71);
        GraphNode n72 = new GraphNode(atomApp, root);
        graphNodes.addElement(n72);
        GraphNode n73 = new GraphNode(atomApp, root);
        graphNodes.addElement(n73);
        GraphNode n74 = new GraphNode(atomApp, root);
        graphNodes.addElement(n74);
        GraphNode n75 = new GraphNode(atomApp, root);
        graphNodes.addElement(n75);
        GraphNode n76 = new GraphNode(atomApp, root);
        graphNodes.addElement(n76);
        GraphNode n77 = new GraphNode(atomApp, root);
        graphNodes.addElement(n77);
        GraphNode n78 = new GraphNode(atomApp, root);
        graphNodes.addElement(n78);
        GraphNode n79 = new GraphNode(atomApp, root);
        graphNodes.addElement(n79);
        GraphNode n80 = new GraphNode(atomApp, root);
        graphNodes.addElement(n80);
        GraphNode n81 = new GraphNode(atomApp, root);
        graphNodes.addElement(n81);
        GraphNode n82 = new GraphNode(atomApp, root);
        graphNodes.addElement(n82);
        GraphNode n83 = new GraphNode(atomApp, root);
        graphNodes.addElement(n83);
        GraphNode n84 = new GraphNode(atomApp, root);
        graphNodes.addElement(n84);
        GraphNode n85 = new GraphNode(atomApp, root);
        graphNodes.addElement(n85);
        GraphNode n86 = new GraphNode(atomApp, root);
        graphNodes.addElement(n86);
        GraphNode n87 = new GraphNode(atomApp, root);
        graphNodes.addElement(n87);
        GraphNode n88 = new GraphNode(atomApp, root);
        graphNodes.addElement(n88);
        GraphNode n89 = new GraphNode(atomApp, root);
        graphNodes.addElement(n89);
        GraphNode n90 = new GraphNode(atomApp, root);
        graphNodes.addElement(n90);
        GraphNode n91 = new GraphNode(atomApp, root);
        graphNodes.addElement(n91);
        GraphNode n92 = new GraphNode(atomApp, root);
        graphNodes.addElement(n92);
        GraphNode n93 = new GraphNode(atomApp, root);
        graphNodes.addElement(n93);
        GraphNode n94 = new GraphNode(atomApp, root);
        graphNodes.addElement(n94);
        GraphNode n95 = new GraphNode(atomApp, root);
        graphNodes.addElement(n95);
        GraphNode n96 = new GraphNode(atomApp, root);
        graphNodes.addElement(n96);
        GraphNode n97 = new GraphNode(atomApp, root);
        graphNodes.addElement(n97);
        GraphNode n98 = new GraphNode(atomApp, root);
        graphNodes.addElement(n98);
        GraphNode n99 = new GraphNode(atomApp, root);
        graphNodes.addElement(n99);
        GraphNode n100 = new GraphNode(atomApp, root);
        graphNodes.addElement(n100);
        GraphNode n101 = new GraphNode(atomApp, root);
        graphNodes.addElement(n101);
        GraphNode n102 = new GraphNode(atomApp, root);
        graphNodes.addElement(n102);
        GraphNode n103 = new GraphNode(atomApp, root);
        graphNodes.addElement(n103);
        GraphNode n104 = new GraphNode(atomApp, root);
        graphNodes.addElement(n104);
        GraphNode n105 = new GraphNode(atomApp, root);
        graphNodes.addElement(n105);
        GraphNode n106 = new GraphNode(atomApp, root);
        graphNodes.addElement(n106);
        GraphNode n107 = new GraphNode(atomApp, root);
        graphNodes.addElement(n107);
        GraphNode n108 = new GraphNode(atomApp, root);
        graphNodes.addElement(n108);
        GraphNode n109 = new GraphNode(atomApp, root);
        graphNodes.addElement(n109);
        GraphNode n110 = new GraphNode(atomApp, root);
        graphNodes.addElement(n110);
        GraphNode n111 = new GraphNode(atomApp, root);
        graphNodes.addElement(n111);
        GraphNode n112 = new GraphNode(atomApp, root);
        graphNodes.addElement(n112);
        GraphNode n113 = new GraphNode(atomApp, root);
        graphNodes.addElement(n113);
        GraphNode n114 = new GraphNode(atomApp, root);
        graphNodes.addElement(n114);
        GraphNode n115 = new GraphNode(atomApp, root);
        graphNodes.addElement(n115);
        GraphNode n116 = new GraphNode(atomApp, root);
        graphNodes.addElement(n116);
        GraphNode n117 = new GraphNode(atomApp, root);
        graphNodes.addElement(n117);
        GraphNode n118 = new GraphNode(atomApp, root);
        graphNodes.addElement(n118);
        GraphNode n119 = new GraphNode(atomApp, root);
        graphNodes.addElement(n119);
        GraphNode n120 = new GraphNode(atomApp, root);
        graphNodes.addElement(n120);
        GraphNode n121 = new GraphNode(atomApp, root);
        graphNodes.addElement(n121);
        GraphNode n122 = new GraphNode(atomApp, root);
        graphNodes.addElement(n122);
        GraphNode n123 = new GraphNode(atomApp, root);
        graphNodes.addElement(n123);
        GraphNode n124 = new GraphNode(atomApp, root);
        graphNodes.addElement(n124);
        GraphNode n125 = new GraphNode(atomApp, root);
        graphNodes.addElement(n125);
        GraphNode n126 = new GraphNode(atomApp, root);
        graphNodes.addElement(n126);
        GraphNode n127 = new GraphNode(atomApp, root);
        graphNodes.addElement(n127);
        GraphNode n128 = new GraphNode(atomApp, root);
        graphNodes.addElement(n128);
        GraphNode n129 = new GraphNode(atomApp, root);
        graphNodes.addElement(n129);
        GraphNode n130 = new GraphNode(atomApp, root);
        graphNodes.addElement(n130);
        GraphNode n131 = new GraphNode(atomApp, root);
        graphNodes.addElement(n131);
        GraphNode n132 = new GraphNode(atomApp, root);
        graphNodes.addElement(n132);
        GraphNode n133 = new GraphNode(atomApp, root);
        graphNodes.addElement(n133);
        GraphNode n134 = new GraphNode(atomApp, root);
        graphNodes.addElement(n134);
        GraphNode n135 = new GraphNode(atomApp, root);
        graphNodes.addElement(n135);
        GraphNode n136 = new GraphNode(atomApp, root);
        graphNodes.addElement(n136);
        GraphNode n137 = new GraphNode(atomApp, root);
        graphNodes.addElement(n137);
        GraphNode n138 = new GraphNode(atomApp, root);
        graphNodes.addElement(n138);
        GraphNode n139 = new GraphNode(atomApp, root);
        graphNodes.addElement(n139);
        GraphNode n140 = new GraphNode(atomApp, root);
        graphNodes.addElement(n140);
        GraphNode n141 = new GraphNode(atomApp, root);
        graphNodes.addElement(n141);
        GraphNode n142 = new GraphNode(atomApp, root);
        graphNodes.addElement(n142);
        GraphNode n143 = new GraphNode(atomApp, root);
        graphNodes.addElement(n143);
        GraphNode n144 = new GraphNode(atomApp, root);
        graphNodes.addElement(n144);
        GraphNode n145 = new GraphNode(atomApp, root);
        graphNodes.addElement(n145);
        GraphNode n146 = new GraphNode(atomApp, root);
        graphNodes.addElement(n146);
        GraphNode n147 = new GraphNode(atomApp, root);
        graphNodes.addElement(n147);
        GraphNode n148 = new GraphNode(atomApp, root);
        graphNodes.addElement(n148);
        GraphNode n149 = new GraphNode(atomApp, root);
        graphNodes.addElement(n149);
        GraphNode n150 = new GraphNode(atomApp, root);
        graphNodes.addElement(n150);
        GraphNode n151 = new GraphNode(atomApp, root);
        graphNodes.addElement(n151);
        GraphNode n152 = new GraphNode(atomApp, root);
        graphNodes.addElement(n152);
        GraphNode n153 = new GraphNode(atomApp, root);
        graphNodes.addElement(n153);
        GraphNode n154 = new GraphNode(atomApp, root);
        graphNodes.addElement(n154);
        GraphNode n155 = new GraphNode(atomApp, root);
        graphNodes.addElement(n155);
        GraphNode n156 = new GraphNode(atomApp, root);
        graphNodes.addElement(n156);
        GraphNode n157 = new GraphNode(atomApp, root);
        graphNodes.addElement(n157);
        GraphNode n158 = new GraphNode(atomApp, root);
        graphNodes.addElement(n158);
        GraphNode n159 = new GraphNode(atomApp, root);
        graphNodes.addElement(n159);
        GraphNode n160 = new GraphNode(atomApp, root);
        graphNodes.addElement(n160);
        GraphNode n161 = new GraphNode(atomApp, root);
        graphNodes.addElement(n161);
        GraphNode n162 = new GraphNode(atomApp, root);
        graphNodes.addElement(n162);
        GraphNode n163 = new GraphNode(atomApp, root);
        graphNodes.addElement(n163);
        GraphNode n164 = new GraphNode(atomApp, root);
        graphNodes.addElement(n164);
        GraphNode n165 = new GraphNode(atomApp, root);
        graphNodes.addElement(n165);
        GraphNode n166 = new GraphNode(atomApp, root);
        graphNodes.addElement(n166);
        GraphNode n167 = new GraphNode(atomApp, root);
        graphNodes.addElement(n167);
        GraphNode n168 = new GraphNode(atomApp, root);
        graphNodes.addElement(n168);
        GraphNode n169 = new GraphNode(atomApp, root);
        graphNodes.addElement(n169);
        GraphNode n170 = new GraphNode(atomApp, root);
        graphNodes.addElement(n170);
        GraphNode n171 = new GraphNode(atomApp, root);
        graphNodes.addElement(n171);
        GraphNode n172 = new GraphNode(atomApp, root);
        graphNodes.addElement(n172);
        GraphNode n173 = new GraphNode(atomApp, root);
        graphNodes.addElement(n173);
        GraphNode n174 = new GraphNode(atomApp, root);
        graphNodes.addElement(n174);
        GraphNode n175 = new GraphNode(atomApp, root);
        graphNodes.addElement(n175);
        GraphNode n176 = new GraphNode(atomApp, root);
        graphNodes.addElement(n176);
        GraphNode n177 = new GraphNode(atomApp, root);
        graphNodes.addElement(n177);
        GraphNode n178 = new GraphNode(atomApp, root);
        graphNodes.addElement(n178);
        GraphNode n179 = new GraphNode(atomApp, root);
        graphNodes.addElement(n179);
        GraphNode n180 = new GraphNode(atomApp, root);
        graphNodes.addElement(n180);
        GraphNode n181 = new GraphNode(atomApp, root);
        graphNodes.addElement(n181);
        GraphNode n182 = new GraphNode(atomApp, root);
        graphNodes.addElement(n182);
        GraphNode n183 = new GraphNode(atomApp, root);
        graphNodes.addElement(n183);
        GraphNode n184 = new GraphNode(atomApp, root);
        graphNodes.addElement(n184);
        GraphNode n185 = new GraphNode(atomApp, root);
        graphNodes.addElement(n185);
        GraphNode n186 = new GraphNode(atomApp, root);
        graphNodes.addElement(n186);
        GraphNode n187 = new GraphNode(atomApp, root);
        graphNodes.addElement(n187);
        GraphNode n188 = new GraphNode(atomApp, root);
        graphNodes.addElement(n188);
        GraphNode n189 = new GraphNode(atomApp, root);
        graphNodes.addElement(n189);
        GraphNode n190 = new GraphNode(atomApp, root);
        graphNodes.addElement(n190);
        GraphNode n191 = new GraphNode(atomApp, root);
        graphNodes.addElement(n191);
        GraphNode n192 = new GraphNode(atomApp, root);
        graphNodes.addElement(n192);
        GraphNode n193 = new GraphNode(atomApp, root);
        graphNodes.addElement(n193);
        GraphNode n194 = new GraphNode(atomApp, root);
        graphNodes.addElement(n194);
        GraphNode n195 = new GraphNode(atomApp, root);
        graphNodes.addElement(n195);
        GraphNode n196 = new GraphNode(atomApp, root);
        graphNodes.addElement(n196);
        GraphNode n197 = new GraphNode(atomApp, root);
        graphNodes.addElement(n197);
        GraphNode n198 = new GraphNode(atomApp, root);
        graphNodes.addElement(n198);
        GraphNode n199 = new GraphNode(atomApp, root);
        graphNodes.addElement(n199);
        GraphNode n200 = new GraphNode(atomApp, root);
        graphNodes.addElement(n200);
        GraphNode n201 = new GraphNode(atomApp, root);
        graphNodes.addElement(n201);
        GraphNode n202 = new GraphNode(atomApp, root);
        graphNodes.addElement(n202);
        GraphNode n203 = new GraphNode(atomApp, root);
        graphNodes.addElement(n203);
        GraphNode n204 = new GraphNode(atomApp, root);
        graphNodes.addElement(n204);
        GraphNode n205 = new GraphNode(atomApp, root);
        graphNodes.addElement(n205);
        GraphNode n206 = new GraphNode(atomApp, root);
        graphNodes.addElement(n206);
        GraphNode n207 = new GraphNode(atomApp, root);
        graphNodes.addElement(n207);
        GraphNode n208 = new GraphNode(atomApp, root);
        graphNodes.addElement(n208);
        GraphNode n209 = new GraphNode(atomApp, root);
        graphNodes.addElement(n209);
        GraphNode n210 = new GraphNode(atomApp, root);
        graphNodes.addElement(n210);
        GraphNode n211 = new GraphNode(atomApp, root);
        graphNodes.addElement(n211);
        GraphNode n212 = new GraphNode(atomApp, root);
        graphNodes.addElement(n212);
        GraphNode n213 = new GraphNode(atomApp, root);
        graphNodes.addElement(n213);
        GraphNode n214 = new GraphNode(atomApp, root);
        graphNodes.addElement(n214);
        GraphNode n215 = new GraphNode(atomApp, root);
        graphNodes.addElement(n215);
        GraphNode n216 = new GraphNode(atomApp, root);
        graphNodes.addElement(n216);
        GraphNode n217 = new GraphNode(atomApp, root);
        graphNodes.addElement(n217);
        GraphNode n218 = new GraphNode(atomApp, root);
        graphNodes.addElement(n218);
        GraphNode n219 = new GraphNode(atomApp, root);
        graphNodes.addElement(n219);
        GraphNode n220 = new GraphNode(atomApp, root);
        graphNodes.addElement(n220);
        GraphNode n221 = new GraphNode(atomApp, root);
        graphNodes.addElement(n221);
        GraphNode n222 = new GraphNode(atomApp, root);
        graphNodes.addElement(n222);
        GraphNode n223 = new GraphNode(atomApp, root);
        graphNodes.addElement(n223);
        GraphNode n224 = new GraphNode(atomApp, root);
        graphNodes.addElement(n224);
        GraphNode n225 = new GraphNode(atomApp, root);
        graphNodes.addElement(n225);
        GraphNode n226 = new GraphNode(atomApp, root);
        graphNodes.addElement(n226);
        GraphNode n227 = new GraphNode(atomApp, root);
        graphNodes.addElement(n227);
        GraphNode n228 = new GraphNode(atomApp, root);
        graphNodes.addElement(n228);
        GraphNode n229 = new GraphNode(atomApp, root);
        graphNodes.addElement(n229);
        GraphNode n230 = new GraphNode(atomApp, root);
        graphNodes.addElement(n230);
        GraphNode n231 = new GraphNode(atomApp, root);
        graphNodes.addElement(n231);
        GraphNode n232 = new GraphNode(atomApp, root);
        graphNodes.addElement(n232);
        GraphNode n233 = new GraphNode(atomApp, root);
        graphNodes.addElement(n233);
        GraphNode n234 = new GraphNode(atomApp, root);
        graphNodes.addElement(n234);
        GraphNode n235 = new GraphNode(atomApp, root);
        graphNodes.addElement(n235);
        GraphNode n236 = new GraphNode(atomApp, root);
        graphNodes.addElement(n236);
        GraphNode n237 = new GraphNode(atomApp, root);
        graphNodes.addElement(n237);
        GraphNode n238 = new GraphNode(atomApp, root);
        graphNodes.addElement(n238);
        GraphNode n239 = new GraphNode(atomApp, root);
        graphNodes.addElement(n239);
        GraphNode n240 = new GraphNode(atomApp, root);
        graphNodes.addElement(n240);
        GraphNode n241 = new GraphNode(atomApp, root);
        graphNodes.addElement(n241);
        GraphNode n242 = new GraphNode(atomApp, root);
        graphNodes.addElement(n242);
        GraphNode n243 = new GraphNode(atomApp, root);
        graphNodes.addElement(n243);
        GraphNode n244 = new GraphNode(atomApp, root);
        graphNodes.addElement(n244);
        GraphNode n245 = new GraphNode(atomApp, root);
        graphNodes.addElement(n245);
        GraphNode n246 = new GraphNode(atomApp, root);
        graphNodes.addElement(n246);
        GraphNode n247 = new GraphNode(atomApp, root);
        graphNodes.addElement(n247);
        GraphNode n248 = new GraphNode(atomApp, root);
        graphNodes.addElement(n248);
        GraphNode n249 = new GraphNode(atomApp, root);
        graphNodes.addElement(n249);
        GraphNode n250 = new GraphNode(atomApp, root);
        graphNodes.addElement(n250);
        GraphNode n251 = new GraphNode(atomApp, root);
        graphNodes.addElement(n251);
        GraphNode n252 = new GraphNode(atomApp, root);
        graphNodes.addElement(n252);
        GraphNode n253 = new GraphNode(atomApp, root);
        graphNodes.addElement(n253);
        GraphNode n254 = new GraphNode(atomApp, root);
        graphNodes.addElement(n254);
        GraphNode n255 = new GraphNode(atomApp, root);
        graphNodes.addElement(n255);
        GraphNode n256 = new GraphNode(atomApp, root);
        graphNodes.addElement(n256);
        GraphNode n257 = new GraphNode(atomApp, root);
        graphNodes.addElement(n257);
        GraphNode n258 = new GraphNode(atomApp, root);
        graphNodes.addElement(n258);
        GraphNode n259 = new GraphNode(atomApp, root);
        graphNodes.addElement(n259);
        GraphNode n260 = new GraphNode(atomApp, root);
        graphNodes.addElement(n260);
        GraphNode n261 = new GraphNode(atomApp, root);
        graphNodes.addElement(n261);
        GraphNode n262 = new GraphNode(atomApp, root);
        graphNodes.addElement(n262);
        GraphNode n263 = new GraphNode(atomApp, root);
        graphNodes.addElement(n263);
        GraphNode n264 = new GraphNode(atomApp, root);
        graphNodes.addElement(n264);
        GraphNode n265 = new GraphNode(atomApp, root);
        graphNodes.addElement(n265);
        GraphNode n266 = new GraphNode(atomApp, root);
        graphNodes.addElement(n266);
        GraphNode n267 = new GraphNode(atomApp, root);
        graphNodes.addElement(n267);
        graphEdges.addElement(new GraphEdge(n231, n230, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n202, n201, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n201, n200, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n245, n126, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n238, n237, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n86, n85, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n241, n240, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n54, n53, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n164, n112, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n47, n243, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n32, n228, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n206, n205, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n222, n103, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n25, n221, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n214, n213, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n37, n36, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n158, n157, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n36, n35, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n38, n37, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n95, n94, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n258, n184, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n35, n231, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n105, n104, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n102, n101, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n80, n79, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n264, n249, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n35, n34, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n168, n167, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n259, n258, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n81, n133, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n120, n119, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n187, n186, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n34, n230, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n5, n201, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n166, n165, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n151, n150, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n236, n235, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n51, n247, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n22, n21, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n262, n261, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n125, n124, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n220, n219, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n183, n182, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n39, n235, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n114, n113, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n247, n245, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n231, n112, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n88, n207, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n39, n38, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n33, n229, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n162, n161, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n64, n63, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n14, n13, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n94, n213, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n166, n114, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n161, n109, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n192, n191, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n93, n145, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n96, n95, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n259, n254, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n77, n196, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n55, n54, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n33, n32, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n30, n226, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n18, n17, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n79, n198, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n246, n127, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n77, n129, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n247, n246, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n89, n141, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n53, n52, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n65, n61, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n152, n151, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n57, n251, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n50, n246, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n162, n110, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n75, n52, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n67, n261, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n97, n216, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n232, n113, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n93, n212, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n221, n102, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n58, n57, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n235, n116, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n139, n138, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n233, n232, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n91, n210, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n86, n205, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n46, n242, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n4, n3, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n143, n142, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n73, n182, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n75, n74, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n157, n156, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n50, n0, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n205, n204, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n216, n20, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n99, n98, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n56, n55, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n40, n39, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n51, n50, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n169, n168, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n239, n238, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n42, n41, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n22, n218, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n141, n140, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n198, n197, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n92, n91, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n159, n107, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n24, n220, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n99, n100, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n131, n130, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n227, n226, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n92, n144, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n28, n27, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n72, n256, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n24, n23, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n106, n105, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n26, n25, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n108, n107, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n21, n20, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n31, n227, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n214, n18, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n61, n60, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n172, n120, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n152, n100, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n44, n43, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n77, n127, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n197, n1, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n167, n166, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n84, n83, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n216, n215, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n208, n207, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n90, n142, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n215, n19, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n203, n202, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n37, n233, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n250, n249, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n97, n149, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n147, n146, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n163, n162, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n82, n201, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n66, n65, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n155, n103, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n195, n187, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n224, n223, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n103, n102, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n146, n145, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n223, n222, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n73, n72, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n182, n181, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n265, n264, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n252, n251, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n217, n21, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n117, n116, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n172, n171, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n78, n130, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n38, n234, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n104, n103, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n86, n138, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n189, n188, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n211, n210, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n160, n159, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n226, n107, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n76, n189, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n119, n118, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n176, n175, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n165, n113, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n200, n199, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n195, n191, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n26, n222, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n174, n173, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n71, n67, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n255, n254, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n251, n250, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n179, n127, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n43, n239, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n232, n231, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n134, n133, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n126, n125, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n212, n16, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n190, n186, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n36, n232, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n19, n18, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n257, n253, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n89, n88, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n257, n256, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n83, n202, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n219, n218, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n64, n185, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n249, n248, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n94, n146, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n43, n42, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n153, n152, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n66, n252, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n169, n117, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n123, n122, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n197, n196, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n56, n52, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n70, n248, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n59, n58, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n199, n198, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n164, n163, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n82, n134, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n174, n122, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n207, n11, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n256, n255, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n78, n77, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n47, n46, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n128, n127, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n260, n193, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n255, n183, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n63, n262, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n128, n126, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n211, n15, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n98, n150, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n90, n89, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n176, n124, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n81, n200, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n208, n12, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n230, n111, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n69, n68, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n235, n234, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n41, n237, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n109, n108, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n266, n191, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n79, n131, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n195, n194, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n20, n19, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n7, n6, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n56, n190, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n267, n186, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n113, n112, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n212, n211, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n58, n53, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n219, n100, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n112, n111, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n93, n92, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n149, n148, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n217, n216, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n6, n202, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n158, n106, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n81, n80, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n180, n128, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n78, n197, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n253, n194, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n94, n93, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n16, n15, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n110, n109, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n99, n151, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n6, n5, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n210, n209, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n87, n86, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n8, n7, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n32, n31, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n29, n225, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n185, n184, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n173, n121, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n4, n200, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n63, n62, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n220, n101, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n153, n101, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n65, n64, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n188, n187, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n7, n203, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n1, n0, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n3, n199, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n130, n129, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n136, n135, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n48, n47, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n247, n128, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n234, n115, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n115, n114, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n85, n137, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n11, n10, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n178, n177, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n9, n10, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n177, n125, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n260, n259, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n229, n110, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n196, n0, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n245, n244, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n160, n108, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n156, n155, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n267, n266, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n124, n123, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n241, n122, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n66, n62, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n142, n141, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n170, n118, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n95, n214, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n8, n204, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n55, n263, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n25, n24, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n206, n10, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n210, n14, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n9, n205, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n223, n104, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n70, n69, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n180, n178, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n27, n26, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n116, n115, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n242, n241, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n80, n199, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n218, n217, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n222, n221, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n246, n196, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n168, n116, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n30, n29, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n244, n125, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n92, n211, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n2, n198, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n76, n72, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n230, n229, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n28, n224, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n79, n78, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n243, n242, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n91, n143, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n2, n1, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n193, n192, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n213, n212, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n41, n40, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n54, n250, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n3, n2, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n154, n153, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n177, n176, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n236, n117, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n82, n81, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n107, n106, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n140, n139, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n150, n149, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n96, n215, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n213, n17, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n74, n59, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n132, n131, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n145, n144, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n229, n228, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n68, n192, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n98, n217, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n228, n109, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n99, n218, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n46, n45, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n262, n258, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n242, n123, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n98, n97, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n85, n204, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n31, n30, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n252, n248, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n91, n90, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n185, n181, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n157, n105, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n267, n263, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n179, n129, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n240, n121, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n237, n236, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n171, n119, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n137, n136, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n175, n174, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n175, n123, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n71, n70, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n83, n135, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n207, n206, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n90, n209, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n97, n96, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n45, n241, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n69, n265, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n171, n170, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n194, n193, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n23, n219, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n84, n203, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n159, n158, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n40, n236, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n254, n253, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n68, n67, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n29, n28, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n228, n227, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n85, n84, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n238, n119, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n234, n233, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n154, n102, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n45, n44, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n48, n244, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n60, n59, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n9, n8, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n204, n203, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n51, n49, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n44, n240, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n133, n132, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n34, n33, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n121, n120, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n215, n214, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n180, n179, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n257, n188, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n101, n100, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n237, n118, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n84, n136, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n225, n224, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n88, n87, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n27, n223, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n111, n110, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n156, n104, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n173, n172, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n42, n238, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n12, n11, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n80, n132, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n167, n115, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n264, n263, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n184, n183, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n243, n124, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n96, n148, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n17, n16, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n49, n245, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n138, n137, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n89, n208, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n221, n220, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n118, n117, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n148, n147, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n209, n13, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n226, n225, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n161, n160, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n15, n14, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n13, n12, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n135, n134, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n209, n208, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n76, n75, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n61, n57, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n5, n4, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n178, n126, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n224, n105, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n190, n189, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n227, n108, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n261, n260, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n155, n154, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n239, n120, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n88, n140, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n170, n169, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n87, n206, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n87, n139, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n144, n143, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n122, n121, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n165, n164, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n95, n147, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n225, n106, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n244, n243, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n23, n22, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n71, n62, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n60, n181, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n74, n73, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n83, n82, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n49, n48, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n240, n239, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n163, n111, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n266, n265, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n233, n114, whiteApp, root));

        // picking
        PickTranslateBehavior pickTranslate
            = new PickTranslateBehavior(root, canvas, bounds);
        pickTranslate.setMode(PickTool.BOUNDS);
        pickTranslate.setupCallback(this);
        root.addChild(pickTranslate);
        /*
        PickZoomBehavior pickZoom
            = new PickZoomBehavior(root, canvas, bounds);
        pickZoom.setMode(PickTool.BOUNDS);
        pickZoom.setupCallback(this);
        root.addChild(pickZoom);
        */

        SimpleUniverse univ = new SimpleUniverse(canvas);

        // navigation
        univ.getViewingPlatform().setNominalViewingTransform();
        TransformGroup vpTG
            = univ.getViewingPlatform().getViewPlatformTransform();
        Transform3D vpTGM = new Transform3D();
        vpTGM.set(new Vector3d(0, 0, 2.6));
        vpTG.setTransform(vpTGM);
        KeyNavigatorBehavior keyBehavior = new KeyNavigatorBehavior(vpTG);
        keyBehavior.setSchedulingBounds(bounds);
        root.addChild(keyBehavior);

        root.compile();
        univ.addBranchGraph(root);

        //
        // constraint system
        //

        // constraint solver
//         s = new C3Solver(10, 10);
        s = new C3Solver(1, 1);

        for (int i = 0; i < graphEdges.size(); i++) {
            GraphEdge e = (GraphEdge) graphEdges.elementAt(i);
            C3GraphLayoutConstraint c
                = new C3GraphLayoutConstraint(e.node0.pos, e.node1.pos, 1);
            s.add(c);
        }

        //s.addStay(nd.tfm.translation(), C3.REQUIRED);
        s.add(new C3LinearConstraint(1, n0.pos.x(), C3.EQ, 0, C3.REQUIRED));
        s.add(new C3LinearConstraint(1, n0.pos.y(), C3.EQ, 0, C3.REQUIRED));
        s.add(new C3LinearConstraint(1, n0.pos.z(), C3.EQ, 0, C3.REQUIRED));
        

        // solve the system
        solve(false);
    }

    public void transformChanged(int type, TransformGroup tg)
    {
        System.out.println("" + type + ": " + tg);
        if(RESOLVE_AFTER_RELEASE){
        	needResolve = tg;
        }
        else{
            GraphNode node = null;
            if (tg != null) {
                for (int i = 0; i < graphNodes.size(); i++) {
                    GraphNode n = (GraphNode) graphNodes.elementAt(i);
                    if (n.grp == tg) {
                        node = n;
                        break;
                    }
                }
                
                // get new position
                Transform3D t = new Transform3D();
                tg.getTransform(t);
                Vector3d tln = new Vector3d();
                t.get(tln);

                // suggest new position
                s.addEditVar(node.pos);
                s.beginEdit();
                s.suggestValue(node.pos, tln);
            }

            solve(true);

            if (node != null)
                s.endEdit();
        }

    }

    private void solve(boolean resolve)
    {
        long t0 = System.currentTimeMillis();
        if (resolve)
            s.resolve();
        else
            s.solve();
        long t1 = System.currentTimeMillis();
        System.out.println("time: " + (t1 - t0) + " ms");

        for (int i = 0; i < graphNodes.size(); i++) {
            GraphNode n = (GraphNode) graphNodes.elementAt(i);
            Transform3D t = new Transform3D();
            t.set(n.pos.vectorValue());
            n.grp.setTransform(t);
        }        

        for (int i = 0; i < graphEdges.size(); i++) {
            GraphEdge e = (GraphEdge) graphEdges.elementAt(i);
            /*
            e.line.setCoordinate(0, e.node0.pos.pointValue());
            e.line.setCoordinate(1, e.node1.pos.pointValue());
            */
            e.lineGrp.setCoordinates(e.node0.pos.pointValue(),
                                     e.node1.pos.pointValue());
        }
    }

    public static void main(String[] args)
    {
        Frame frame = new MainFrame(new test(), 500, 500);
    }

}
