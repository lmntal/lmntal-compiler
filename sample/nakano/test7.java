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

public class test7 extends Applet implements PickingCallback {

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

    public test7()
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
        GraphNode n2 = new GraphNode(greenApp, root);
        graphNodes.addElement(n2);
        GraphNode n3 = new GraphNode(greenApp, root);
        graphNodes.addElement(n3);
        GraphNode n4 = new GraphNode(greenApp, root);
        graphNodes.addElement(n4);
        GraphNode n5 = new GraphNode(greenApp, root);
        graphNodes.addElement(n5);
        GraphNode n6 = new GraphNode(greenApp, root);
        graphNodes.addElement(n6);
        GraphNode n13 = new GraphNode(greenApp, root);
        graphNodes.addElement(n13);
        GraphNode n7 = new GraphNode(greenApp, root);
        graphNodes.addElement(n7);
        GraphNode n8 = new GraphNode(greenApp, root);
        graphNodes.addElement(n8);
        GraphNode n9 = new GraphNode(greenApp, root);
        graphNodes.addElement(n9);
        GraphNode n10 = new GraphNode(greenApp, root);
        graphNodes.addElement(n10);
        GraphNode n11 = new GraphNode(greenApp, root);
        graphNodes.addElement(n11);
        GraphNode n12 = new GraphNode(greenApp, root);
        graphNodes.addElement(n12);
        graphEdges.addElement(new GraphEdge(n12, n0, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n9, n8, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n8, n4, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n12, n11, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n2, n10, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n8, n7, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n11, n10, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n7, n6, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n9, n10, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n11, n1, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n7, n5, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n9, n3, whiteApp, root));
        graphEdges.addElement(new GraphEdge(n13, n12, whiteApp, root));

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
        Frame frame = new MainFrame(new test7(), 500, 500);
    }

}
