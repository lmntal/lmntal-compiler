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
            Sphere sphere = new Sphere(.1f, app);
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
        GraphNode n0 = new GraphNode(greenApp, root);
        graphNodes.addElement(n0);
        GraphNode n1 = new GraphNode(greenApp, root);
        graphNodes.addElement(n1);
        GraphNode n27 = new GraphNode(greenApp, root);
        graphNodes.addElement(n27);
        GraphNode n4 = new GraphNode(greenApp, root);
        graphNodes.addElement(n4);
        GraphNode n105 = new GraphNode(greenApp, root);
        graphNodes.addElement(n105);
        GraphNode n7 = new GraphNode(greenApp, root);
        graphNodes.addElement(n7);
        GraphNode n111 = new GraphNode(greenApp, root);
        graphNodes.addElement(n111);
        GraphNode n10 = new GraphNode(greenApp, root);
        graphNodes.addElement(n10);
        GraphNode n12 = new GraphNode(greenApp, root);
        graphNodes.addElement(n12);
        GraphNode n117 = new GraphNode(greenApp, root);
        graphNodes.addElement(n117);
        GraphNode n14 = new GraphNode(greenApp, root);
        graphNodes.addElement(n14);
        GraphNode n26 = new GraphNode(greenApp, root);
        graphNodes.addElement(n26);
        GraphNode n17 = new GraphNode(greenApp, root);
        graphNodes.addElement(n17);
        GraphNode n50 = new GraphNode(greenApp, root);
        graphNodes.addElement(n50);
        GraphNode n20 = new GraphNode(greenApp, root);
        graphNodes.addElement(n20);
        GraphNode n74 = new GraphNode(greenApp, root);
        graphNodes.addElement(n74);
        GraphNode n23 = new GraphNode(greenApp, root);
        graphNodes.addElement(n23);
        GraphNode n98 = new GraphNode(greenApp, root);
        graphNodes.addElement(n98);
        GraphNode n122 = new GraphNode(greenApp, root);
        graphNodes.addElement(n122);
        GraphNode n123 = new GraphNode(greenApp, root);
        graphNodes.addElement(n123);
        GraphNode n96 = new GraphNode(greenApp, root);
        graphNodes.addElement(n96);
        GraphNode n72 = new GraphNode(greenApp, root);
        graphNodes.addElement(n72);
        GraphNode n115 = new GraphNode(greenApp, root);
        graphNodes.addElement(n115);
        GraphNode n109 = new GraphNode(greenApp, root);
        graphNodes.addElement(n109);
        GraphNode n103 = new GraphNode(greenApp, root);
        graphNodes.addElement(n103);
        GraphNode n48 = new GraphNode(greenApp, root);
        graphNodes.addElement(n48);
        GraphNode n29 = new GraphNode(greenApp, root);
        graphNodes.addElement(n29);
        GraphNode n35 = new GraphNode(greenApp, root);
        graphNodes.addElement(n35);
        GraphNode n41 = new GraphNode(greenApp, root);
        graphNodes.addElement(n41);
        GraphNode n47 = new GraphNode(greenApp, root);
        graphNodes.addElement(n47);
        GraphNode n53 = new GraphNode(greenApp, root);
        graphNodes.addElement(n53);
        GraphNode n59 = new GraphNode(greenApp, root);
        graphNodes.addElement(n59);
        GraphNode n65 = new GraphNode(greenApp, root);
        graphNodes.addElement(n65);
        GraphNode n71 = new GraphNode(greenApp, root);
        graphNodes.addElement(n71);
        GraphNode n77 = new GraphNode(greenApp, root);
        graphNodes.addElement(n77);
        GraphNode n83 = new GraphNode(greenApp, root);
        graphNodes.addElement(n83);
        GraphNode n89 = new GraphNode(greenApp, root);
        graphNodes.addElement(n89);
        GraphNode n95 = new GraphNode(greenApp, root);
        graphNodes.addElement(n95);
        GraphNode n101 = new GraphNode(greenApp, root);
        graphNodes.addElement(n101);
        GraphNode n107 = new GraphNode(greenApp, root);
        graphNodes.addElement(n107);
        GraphNode n113 = new GraphNode(greenApp, root);
        graphNodes.addElement(n113);
        GraphNode n119 = new GraphNode(greenApp, root);
        graphNodes.addElement(n119);
        GraphNode n120 = new GraphNode(greenApp, root);
        graphNodes.addElement(n120);
        GraphNode n121 = new GraphNode(greenApp, root);
        graphNodes.addElement(n121);
        GraphNode n124 = new GraphNode(greenApp, root);
        graphNodes.addElement(n124);
        graphEdges.addElement(new GraphEdge(n121, n117, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n96, n95, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n95, n89, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n89, n113, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n95, n71, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n124, n123, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n59, n53, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n53, n29, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n29, n0, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n47, n41, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n72, n48, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n96, n74, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n124, n121, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n115, n109, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n65, n59, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n77, n101, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n124, n120, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n121, n119, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n115, n111, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n26, n103, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n59, n35, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n89, n83, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n72, n71, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n109, n107, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n115, n113, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n4, n35, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n71, n65, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n120, n119, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n96, n72, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n109, n103, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n65, n41, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n77, n53, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n77, n20, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n83, n77, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n83, n107, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n96, n120, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n48, n14, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n103, n101, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n41, n35, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n53, n17, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n48, n47, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n48, n12, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n23, n101, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n107, n101, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n83, n59, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n89, n65, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n7, n41, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n72, n50, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n35, n29, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n71, n47, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n119, n113, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n113, n107, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n29, n1, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n47, n10, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n109, n105, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n27, n103, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n98, n120, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n121, n115, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n124, n122, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n95, n119, whiteApp, root));

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
