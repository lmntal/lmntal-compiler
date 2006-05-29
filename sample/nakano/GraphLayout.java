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

public class GraphLayout extends Applet implements PickingCallback {

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
            Sphere sphere = new Sphere(.3f, app);
            grp.addChild(sphere);

            pos = new C3Variable3D(
                new C3Domain3D(-100, -100, -100, 100, 100, 100));
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

    private C3Solver s;

    public GraphLayout()
    {
        // canvas
        setLayout(new BorderLayout());
        GraphicsConfiguration config
            = SimpleUniverse.getPreferredConfiguration();
        canvas = new Canvas3D(config);
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

        // appearance for red
        Appearance redApp = new Appearance();
        Material redMat = new Material();
        redMat.setSpecularColor(new Color3f(1f, .4f, .1f));
        redMat.setDiffuseColor(new Color3f(1f, .4f, .1f));
        redMat.setAmbientColor(new Color3f(.25f, .1f, .025f));
        redApp.setMaterial(redMat);

        // appearance for white
        Appearance whiteApp = new Appearance();
        Material whiteMat = new Material();
        whiteMat.setSpecularColor(new Color3f(1f, 1f, 1f));
        whiteMat.setDiffuseColor(new Color3f(1f, 1f, 1f));
        whiteMat.setAmbientColor(new Color3f(.25f, .25f, .25f));
        whiteApp.setMaterial(whiteMat);

        // appearance for green
        Appearance greenApp = new Appearance();
        Material greenMat = new Material();
        greenMat.setSpecularColor(new Color3f(.4f, 1f, .2f));
        greenMat.setDiffuseColor(new Color3f(.4f, 1f, .2f));
        greenMat.setAmbientColor(new Color3f(.1f, .25f, .05f));
        greenApp.setMaterial(greenMat);

        // graph nodes
        GraphNode n8 = new GraphNode(greenApp, root);
        graphNodes.addElement(n8);
        GraphNode n11 = new GraphNode(greenApp, root);
        graphNodes.addElement(n11);
        GraphNode n14 = new GraphNode(greenApp, root);
        graphNodes.addElement(n14);
        GraphNode n17 = new GraphNode(greenApp, root);
        graphNodes.addElement(n17);
        GraphNode n20 = new GraphNode(greenApp, root);
        graphNodes.addElement(n20);
        GraphNode n23 = new GraphNode(greenApp, root);
        graphNodes.addElement(n23);
        GraphNode n26 = new GraphNode(greenApp, root);
        graphNodes.addElement(n26);
        GraphNode n29 = new GraphNode(greenApp, root);
        graphNodes.addElement(n29);
        GraphNode n32 = new GraphNode(greenApp, root);
        graphNodes.addElement(n32);
        GraphNode n35 = new GraphNode(greenApp, root);
        graphNodes.addElement(n35);
        GraphNode n38 = new GraphNode(greenApp, root);
        graphNodes.addElement(n38);
        GraphNode n41 = new GraphNode(greenApp, root);
        graphNodes.addElement(n41);
        GraphNode n44 = new GraphNode(greenApp, root);
        graphNodes.addElement(n44);
        GraphNode n47 = new GraphNode(greenApp, root);
        graphNodes.addElement(n47);
        GraphNode n50 = new GraphNode(greenApp, root);
        graphNodes.addElement(n50);
        GraphNode n53 = new GraphNode(greenApp, root);
        graphNodes.addElement(n53);
        GraphNode n56 = new GraphNode(greenApp, root);
        graphNodes.addElement(n56);
        GraphNode n59 = new GraphNode(greenApp, root);
        graphNodes.addElement(n59);
        GraphNode n62 = new GraphNode(greenApp, root);
        graphNodes.addElement(n62);
        GraphNode n65 = new GraphNode(greenApp, root);
        graphNodes.addElement(n65);
        GraphNode n68 = new GraphNode(greenApp, root);
        graphNodes.addElement(n68);
        GraphNode n71 = new GraphNode(greenApp, root);
        graphNodes.addElement(n71);
        GraphNode n74 = new GraphNode(greenApp, root);
        graphNodes.addElement(n74);
        GraphNode n77 = new GraphNode(greenApp, root);
        graphNodes.addElement(n77);
        GraphNode n80 = new GraphNode(greenApp, root);
        graphNodes.addElement(n80);
        GraphNode n83 = new GraphNode(greenApp, root);
        graphNodes.addElement(n83);
        GraphNode n86 = new GraphNode(greenApp, root);
        graphNodes.addElement(n86);
        GraphNode n89 = new GraphNode(greenApp, root);
        graphNodes.addElement(n89);
        GraphNode n92 = new GraphNode(greenApp, root);
        graphNodes.addElement(n92);
        GraphNode n95 = new GraphNode(greenApp, root);
        graphNodes.addElement(n95);
        GraphNode n98 = new GraphNode(greenApp, root);
        graphNodes.addElement(n98);
        GraphNode n101 = new GraphNode(greenApp, root);
        graphNodes.addElement(n101);
        GraphNode n104 = new GraphNode(greenApp, root);
        graphNodes.addElement(n104);
        GraphNode n107 = new GraphNode(greenApp, root);
        graphNodes.addElement(n107);
        GraphNode n110 = new GraphNode(greenApp, root);
        graphNodes.addElement(n110);
        GraphNode n113 = new GraphNode(greenApp, root);
        graphNodes.addElement(n113);
        GraphNode n116 = new GraphNode(greenApp, root);
        graphNodes.addElement(n116);
        GraphNode n119 = new GraphNode(greenApp, root);
        graphNodes.addElement(n119);
        GraphNode n122 = new GraphNode(greenApp, root);
        graphNodes.addElement(n122);
        GraphNode n125 = new GraphNode(greenApp, root);
        graphNodes.addElement(n125);
        GraphNode n128 = new GraphNode(greenApp, root);
        graphNodes.addElement(n128);
        GraphNode n131 = new GraphNode(greenApp, root);
        graphNodes.addElement(n131);
        GraphNode n134 = new GraphNode(greenApp, root);
        graphNodes.addElement(n134);
        GraphNode n137 = new GraphNode(greenApp, root);
        graphNodes.addElement(n137);
        GraphNode n140 = new GraphNode(greenApp, root);
        graphNodes.addElement(n140);
        GraphNode n143 = new GraphNode(greenApp, root);
        graphNodes.addElement(n143);
        GraphNode n146 = new GraphNode(greenApp, root);
        graphNodes.addElement(n146);
        GraphNode n149 = new GraphNode(greenApp, root);
        graphNodes.addElement(n149);
        GraphNode n152 = new GraphNode(greenApp, root);
        graphNodes.addElement(n152);
        GraphNode n155 = new GraphNode(greenApp, root);
        graphNodes.addElement(n155);
        GraphNode n158 = new GraphNode(greenApp, root);
        graphNodes.addElement(n158);
        GraphNode n161 = new GraphNode(greenApp, root);
        graphNodes.addElement(n161);
        GraphNode n164 = new GraphNode(greenApp, root);
        graphNodes.addElement(n164);
        GraphNode n167 = new GraphNode(greenApp, root);
        graphNodes.addElement(n167);
        GraphNode n170 = new GraphNode(greenApp, root);
        graphNodes.addElement(n170);
        GraphNode n173 = new GraphNode(greenApp, root);
        graphNodes.addElement(n173);
        GraphNode n176 = new GraphNode(greenApp, root);
        graphNodes.addElement(n176);
        GraphNode n179 = new GraphNode(greenApp, root);
        graphNodes.addElement(n179);
        GraphNode n182 = new GraphNode(greenApp, root);
        graphNodes.addElement(n182);
        GraphNode n185 = new GraphNode(greenApp, root);
        graphNodes.addElement(n185);
        GraphNode n188 = new GraphNode(greenApp, root);
        graphNodes.addElement(n188);
        GraphNode n191 = new GraphNode(greenApp, root);
        graphNodes.addElement(n191);
        GraphNode n194 = new GraphNode(greenApp, root);
        graphNodes.addElement(n194);
        GraphNode n197 = new GraphNode(greenApp, root);
        graphNodes.addElement(n197);
        GraphNode n200 = new GraphNode(greenApp, root);
        graphNodes.addElement(n200);
        GraphNode n203 = new GraphNode(greenApp, root);
        graphNodes.addElement(n203);
        GraphNode n206 = new GraphNode(greenApp, root);
        graphNodes.addElement(n206);
        GraphNode n209 = new GraphNode(greenApp, root);
        graphNodes.addElement(n209);
        GraphNode n212 = new GraphNode(greenApp, root);
        graphNodes.addElement(n212);
        GraphNode n215 = new GraphNode(greenApp, root);
        graphNodes.addElement(n215);
        GraphNode n218 = new GraphNode(greenApp, root);
        graphNodes.addElement(n218);
        GraphNode n221 = new GraphNode(greenApp, root);
        graphNodes.addElement(n221);
        GraphNode n224 = new GraphNode(greenApp, root);
        graphNodes.addElement(n224);
        GraphNode n227 = new GraphNode(greenApp, root);
        graphNodes.addElement(n227);
        GraphNode n230 = new GraphNode(greenApp, root);
        graphNodes.addElement(n230);
        GraphNode n233 = new GraphNode(greenApp, root);
        graphNodes.addElement(n233);
        GraphNode n236 = new GraphNode(greenApp, root);
        graphNodes.addElement(n236);
        GraphNode n239 = new GraphNode(greenApp, root);
        graphNodes.addElement(n239);
        GraphNode n242 = new GraphNode(greenApp, root);
        graphNodes.addElement(n242);
        GraphNode n245 = new GraphNode(greenApp, root);
        graphNodes.addElement(n245);
        GraphNode n248 = new GraphNode(greenApp, root);
        graphNodes.addElement(n248);
        GraphNode n251 = new GraphNode(greenApp, root);
        graphNodes.addElement(n251);
        GraphNode n254 = new GraphNode(greenApp, root);
        graphNodes.addElement(n254);
        GraphNode n257 = new GraphNode(greenApp, root);
        graphNodes.addElement(n257);
        GraphNode n260 = new GraphNode(greenApp, root);
        graphNodes.addElement(n260);
        GraphNode n263 = new GraphNode(greenApp, root);
        graphNodes.addElement(n263);
        GraphNode n266 = new GraphNode(greenApp, root);
        graphNodes.addElement(n266);
        GraphNode n269 = new GraphNode(greenApp, root);
        graphNodes.addElement(n269);
        GraphNode n272 = new GraphNode(greenApp, root);
        graphNodes.addElement(n272);
        GraphNode n275 = new GraphNode(greenApp, root);
        graphNodes.addElement(n275);
        GraphNode n278 = new GraphNode(greenApp, root);
        graphNodes.addElement(n278);
        GraphNode n281 = new GraphNode(greenApp, root);
        graphNodes.addElement(n281);
        GraphNode n284 = new GraphNode(greenApp, root);
        graphNodes.addElement(n284);
        GraphNode n287 = new GraphNode(greenApp, root);
        graphNodes.addElement(n287);
        GraphNode n290 = new GraphNode(greenApp, root);
        graphNodes.addElement(n290);
        GraphNode n293 = new GraphNode(greenApp, root);
        graphNodes.addElement(n293);
        GraphNode n296 = new GraphNode(greenApp, root);
        graphNodes.addElement(n296);
        GraphNode n299 = new GraphNode(greenApp, root);
        graphNodes.addElement(n299);
        GraphNode n302 = new GraphNode(greenApp, root);
        graphNodes.addElement(n302);
        GraphNode n304 = new GraphNode(greenApp, root);
        graphNodes.addElement(n304);
        GraphNode n3 = new GraphNode(greenApp, root);
        graphNodes.addElement(n3);
        GraphNode n9 = new GraphNode(greenApp, root);
        graphNodes.addElement(n9);
        GraphNode n12 = new GraphNode(greenApp, root);
        graphNodes.addElement(n12);
        GraphNode n15 = new GraphNode(greenApp, root);
        graphNodes.addElement(n15);
        GraphNode n18 = new GraphNode(greenApp, root);
        graphNodes.addElement(n18);
        GraphNode n21 = new GraphNode(greenApp, root);
        graphNodes.addElement(n21);
        GraphNode n24 = new GraphNode(greenApp, root);
        graphNodes.addElement(n24);
        GraphNode n27 = new GraphNode(greenApp, root);
        graphNodes.addElement(n27);
        GraphNode n30 = new GraphNode(greenApp, root);
        graphNodes.addElement(n30);
        GraphNode n33 = new GraphNode(greenApp, root);
        graphNodes.addElement(n33);
        GraphNode n36 = new GraphNode(greenApp, root);
        graphNodes.addElement(n36);
        GraphNode n39 = new GraphNode(greenApp, root);
        graphNodes.addElement(n39);
        GraphNode n42 = new GraphNode(greenApp, root);
        graphNodes.addElement(n42);
        GraphNode n45 = new GraphNode(greenApp, root);
        graphNodes.addElement(n45);
        GraphNode n48 = new GraphNode(greenApp, root);
        graphNodes.addElement(n48);
        GraphNode n51 = new GraphNode(greenApp, root);
        graphNodes.addElement(n51);
        GraphNode n54 = new GraphNode(greenApp, root);
        graphNodes.addElement(n54);
        GraphNode n57 = new GraphNode(greenApp, root);
        graphNodes.addElement(n57);
        GraphNode n60 = new GraphNode(greenApp, root);
        graphNodes.addElement(n60);
        GraphNode n63 = new GraphNode(greenApp, root);
        graphNodes.addElement(n63);
        GraphNode n66 = new GraphNode(greenApp, root);
        graphNodes.addElement(n66);
        GraphNode n69 = new GraphNode(greenApp, root);
        graphNodes.addElement(n69);
        GraphNode n72 = new GraphNode(greenApp, root);
        graphNodes.addElement(n72);
        GraphNode n75 = new GraphNode(greenApp, root);
        graphNodes.addElement(n75);
        GraphNode n78 = new GraphNode(greenApp, root);
        graphNodes.addElement(n78);
        GraphNode n81 = new GraphNode(greenApp, root);
        graphNodes.addElement(n81);
        GraphNode n84 = new GraphNode(greenApp, root);
        graphNodes.addElement(n84);
        GraphNode n87 = new GraphNode(greenApp, root);
        graphNodes.addElement(n87);
        GraphNode n90 = new GraphNode(greenApp, root);
        graphNodes.addElement(n90);
        GraphNode n93 = new GraphNode(greenApp, root);
        graphNodes.addElement(n93);
        GraphNode n96 = new GraphNode(greenApp, root);
        graphNodes.addElement(n96);
        GraphNode n99 = new GraphNode(greenApp, root);
        graphNodes.addElement(n99);
        GraphNode n102 = new GraphNode(greenApp, root);
        graphNodes.addElement(n102);
        GraphNode n105 = new GraphNode(greenApp, root);
        graphNodes.addElement(n105);
        GraphNode n108 = new GraphNode(greenApp, root);
        graphNodes.addElement(n108);
        GraphNode n111 = new GraphNode(greenApp, root);
        graphNodes.addElement(n111);
        GraphNode n114 = new GraphNode(greenApp, root);
        graphNodes.addElement(n114);
        GraphNode n117 = new GraphNode(greenApp, root);
        graphNodes.addElement(n117);
        GraphNode n120 = new GraphNode(greenApp, root);
        graphNodes.addElement(n120);
        GraphNode n123 = new GraphNode(greenApp, root);
        graphNodes.addElement(n123);
        GraphNode n126 = new GraphNode(greenApp, root);
        graphNodes.addElement(n126);
        GraphNode n129 = new GraphNode(greenApp, root);
        graphNodes.addElement(n129);
        GraphNode n132 = new GraphNode(greenApp, root);
        graphNodes.addElement(n132);
        GraphNode n135 = new GraphNode(greenApp, root);
        graphNodes.addElement(n135);
        GraphNode n138 = new GraphNode(greenApp, root);
        graphNodes.addElement(n138);
        GraphNode n141 = new GraphNode(greenApp, root);
        graphNodes.addElement(n141);
        GraphNode n144 = new GraphNode(greenApp, root);
        graphNodes.addElement(n144);
        GraphNode n147 = new GraphNode(greenApp, root);
        graphNodes.addElement(n147);
        GraphNode n150 = new GraphNode(greenApp, root);
        graphNodes.addElement(n150);
        GraphNode n153 = new GraphNode(greenApp, root);
        graphNodes.addElement(n153);
        GraphNode n156 = new GraphNode(greenApp, root);
        graphNodes.addElement(n156);
        GraphNode n159 = new GraphNode(greenApp, root);
        graphNodes.addElement(n159);
        GraphNode n162 = new GraphNode(greenApp, root);
        graphNodes.addElement(n162);
        GraphNode n165 = new GraphNode(greenApp, root);
        graphNodes.addElement(n165);
        GraphNode n168 = new GraphNode(greenApp, root);
        graphNodes.addElement(n168);
        GraphNode n171 = new GraphNode(greenApp, root);
        graphNodes.addElement(n171);
        GraphNode n174 = new GraphNode(greenApp, root);
        graphNodes.addElement(n174);
        GraphNode n177 = new GraphNode(greenApp, root);
        graphNodes.addElement(n177);
        GraphNode n180 = new GraphNode(greenApp, root);
        graphNodes.addElement(n180);
        GraphNode n183 = new GraphNode(greenApp, root);
        graphNodes.addElement(n183);
        GraphNode n186 = new GraphNode(greenApp, root);
        graphNodes.addElement(n186);
        GraphNode n189 = new GraphNode(greenApp, root);
        graphNodes.addElement(n189);
        GraphNode n192 = new GraphNode(greenApp, root);
        graphNodes.addElement(n192);
        GraphNode n195 = new GraphNode(greenApp, root);
        graphNodes.addElement(n195);
        GraphNode n198 = new GraphNode(greenApp, root);
        graphNodes.addElement(n198);
        GraphNode n201 = new GraphNode(greenApp, root);
        graphNodes.addElement(n201);
        GraphNode n204 = new GraphNode(greenApp, root);
        graphNodes.addElement(n204);
        GraphNode n207 = new GraphNode(greenApp, root);
        graphNodes.addElement(n207);
        GraphNode n210 = new GraphNode(greenApp, root);
        graphNodes.addElement(n210);
        GraphNode n213 = new GraphNode(greenApp, root);
        graphNodes.addElement(n213);
        GraphNode n216 = new GraphNode(greenApp, root);
        graphNodes.addElement(n216);
        GraphNode n219 = new GraphNode(greenApp, root);
        graphNodes.addElement(n219);
        GraphNode n222 = new GraphNode(greenApp, root);
        graphNodes.addElement(n222);
        GraphNode n225 = new GraphNode(greenApp, root);
        graphNodes.addElement(n225);
        GraphNode n228 = new GraphNode(greenApp, root);
        graphNodes.addElement(n228);
        GraphNode n231 = new GraphNode(greenApp, root);
        graphNodes.addElement(n231);
        GraphNode n234 = new GraphNode(greenApp, root);
        graphNodes.addElement(n234);
        GraphNode n237 = new GraphNode(greenApp, root);
        graphNodes.addElement(n237);
        GraphNode n240 = new GraphNode(greenApp, root);
        graphNodes.addElement(n240);
        GraphNode n243 = new GraphNode(greenApp, root);
        graphNodes.addElement(n243);
        GraphNode n246 = new GraphNode(greenApp, root);
        graphNodes.addElement(n246);
        GraphNode n249 = new GraphNode(greenApp, root);
        graphNodes.addElement(n249);
        GraphNode n252 = new GraphNode(greenApp, root);
        graphNodes.addElement(n252);
        GraphNode n255 = new GraphNode(greenApp, root);
        graphNodes.addElement(n255);
        GraphNode n258 = new GraphNode(greenApp, root);
        graphNodes.addElement(n258);
        GraphNode n261 = new GraphNode(greenApp, root);
        graphNodes.addElement(n261);
        GraphNode n264 = new GraphNode(greenApp, root);
        graphNodes.addElement(n264);
        GraphNode n267 = new GraphNode(greenApp, root);
        graphNodes.addElement(n267);
        GraphNode n270 = new GraphNode(greenApp, root);
        graphNodes.addElement(n270);
        GraphNode n273 = new GraphNode(greenApp, root);
        graphNodes.addElement(n273);
        GraphNode n276 = new GraphNode(greenApp, root);
        graphNodes.addElement(n276);
        GraphNode n279 = new GraphNode(greenApp, root);
        graphNodes.addElement(n279);
        GraphNode n282 = new GraphNode(greenApp, root);
        graphNodes.addElement(n282);
        GraphNode n285 = new GraphNode(greenApp, root);
        graphNodes.addElement(n285);
        GraphNode n288 = new GraphNode(greenApp, root);
        graphNodes.addElement(n288);
        GraphNode n291 = new GraphNode(greenApp, root);
        graphNodes.addElement(n291);
        GraphNode n294 = new GraphNode(greenApp, root);
        graphNodes.addElement(n294);
        GraphNode n297 = new GraphNode(greenApp, root);
        graphNodes.addElement(n297);
        GraphNode n300 = new GraphNode(greenApp, root);
        graphNodes.addElement(n300);
        GraphNode n303 = new GraphNode(greenApp, root);
        graphNodes.addElement(n303);
        graphEdges.addElement(new GraphEdge(n231, n230, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n93, n92, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n108, n107, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n21, n20, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n201, n200, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n303, n302, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n81, n80, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n186, n185, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n54, n53, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n129, n128, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n84, n83, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n216, n215, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n237, n236, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n36, n35, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n105, n104, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n210, n209, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n294, n293, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n147, n146, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n87, n86, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n72, n71, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n102, n101, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n168, n167, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n66, n65, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n63, n62, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n207, n206, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n120, n119, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n171, n170, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n282, n281, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n288, n287, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n159, n158, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n183, n182, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n252, n251, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n117, n116, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n48, n47, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n114, n113, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n300, n299, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n189, n188, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n39, n38, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n162, n161, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n228, n227, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n174, n173, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n234, n233, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n45, n44, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n255, n254, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n60, n59, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n192, n191, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n9, n8, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n204, n203, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n156, n155, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n270, n269, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n96, n95, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n267, n266, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n180, n179, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n126, n125, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n33, n32, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n285, n284, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n18, n17, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n225, n224, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n111, n110, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n27, n26, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n219, n218, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n222, n221, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n12, n11, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n249, n248, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n57, n56, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n264, n263, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n30, n29, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n153, n152, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n279, n278, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n138, n137, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n273, n272, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n123, n122, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n297, n296, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n15, n14, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n246, n245, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n135, n134, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n243, n242, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n213, n212, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n78, n77, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n261, n260, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n75, n74, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n90, n89, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n177, n176, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n99, n98, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n69, n68, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n144, n143, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n304, n3, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n51, n50, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n42, n41, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n258, n257, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n150, n149, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n141, n140, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n195, n194, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n198, n197, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n132, n131, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n165, n164, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n291, n290, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n24, n23, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n276, n275, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n240, n239, whiteApp, root));

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

        // solve the system
        solve(false);
    }

    public void transformChanged(int type, TransformGroup tg)
    {
        System.out.println("" + type + ": " + tg);

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
        Frame frame = new MainFrame(new GraphLayout(), 500, 500);
    }

}
