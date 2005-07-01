package test3d;
/**
 * @author TOKUNAGA Ken-ichi iam@tokunagakenichi.net
 * @version Time-stamp: <03/02/28 03:17:20 tkenichi>
 *
 * 物体をピッキングして、それをマウスカーソルに追従させる。
 */

import com.sun.j3d.utils.picking.*;
import com.sun.j3d.utils.picking.behaviors.*;
import com.sun.j3d.utils.behaviors.mouse.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class MouseDragBehavior extends Behavior 
    implements MouseBehaviorCallback{

    Vector3d currV3d = new Vector3d(),diffV3d = new Vector3d();
    Point3d cursorP3d = new Point3d(),viewP3d = new Point3d();
    Vector4d currV4d = new Vector4d();
    private PickingCallback callback = null;
	private LMNTransformGroup targetObj=null;

    private PickCanvas pickCanvas;
    private WakeupOr wakeupCondition;
    private boolean picked = false;
    
    // ピッキングした座標を格納する
    private TransformGroup currGrp;
    private Transform3D 
        currT3d = new Transform3D(),       // ピッキングした座標
        localT3d = new Transform3D(),      // 局所座標
        inverse = new Transform3D(),       // 逆変換
        plate2world = new Transform3D();   // imageplate から仮想空間への変換

    public MouseDragBehavior(Canvas3D canvas,
                             BranchGroup root,
                             Bounds bounds){
        currGrp = new TransformGroup();
        currGrp.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        currGrp.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        currGrp.setCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ);
        // Behavior は Leaf だからその上の BranchGroup につける
        root.addChild(currGrp);
        pickCanvas = new PickCanvas(canvas, root);
        pickCanvas.setMode(PickCanvas.BOUNDS);
        setSchedulingBounds(bounds);
    }

    public void setMode(int pickMode) {
        pickCanvas.setMode(pickMode);
    }
    public int getMode() {
        return pickCanvas.getMode();
    }
    public void setTolerance(float tolerance) {
        pickCanvas.setTolerance(tolerance);
    }
    public float getTolerance() {
        return pickCanvas.getTolerance();
    }
    
    public void initialize() {
        // マウスを押したとき、離したとき、ドラッグしたときに
        // 起動する
        WakeupCriterion[] conditions = new WakeupCriterion[3];
        conditions[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED);
        conditions[1] = new WakeupOnAWTEvent(MouseEvent.MOUSE_PRESSED);
        conditions[2] = new WakeupOnAWTEvent(MouseEvent.MOUSE_RELEASED);
        wakeupCondition = new WakeupOr(conditions);
        wakeupOn(wakeupCondition);
    }
    
    public void processStimulus (Enumeration criteria) {
        WakeupCriterion wakeup;
        AWTEvent[] evt = null;
        int xpos = 0, ypos = 0;
        MouseEvent mevent;

        // AWTEvent を取り出す
        while(criteria.hasMoreElements()) {
            wakeup = (WakeupCriterion)criteria.nextElement();
            if (wakeup instanceof WakeupOnAWTEvent)
                evt = ((WakeupOnAWTEvent)wakeup).getAWTEvent();
        }
    
        // MouseEvent の場合
        if (evt[0] instanceof MouseEvent){
            mevent = (MouseEvent) evt[0];
            // マウスカーソルの2次元ピクセル座標
            xpos = mevent.getPoint().x;
            ypos = mevent.getPoint().y;

            switch(mevent.getID()){
            case MouseEvent.MOUSE_PRESSED:
                // 押されたとき
                System.out.println("mouse pressed");
                // ピッキングする
                if (!mevent.isAltDown() && mevent.isMetaDown()){
                    pickingTransformGroup(xpos,ypos);
                }
                if (mevent.isAltDown() && mevent.isMetaDown()){
                	altpickingTransformGroup(xpos,ypos);
                }
                break;
            case MouseEvent.MOUSE_RELEASED:
                // 離したとき
                // ピッキングをやめる
            	if(targetObj!=null)targetObj.setVisible(true);
                picked = false;
                break;
            case MouseEvent.MOUSE_DRAGGED:
                // ドラッグしたとき
                // ピッキングされた TransformGroup を動かす
                if (picked && !mevent.isAltDown() && mevent.isMetaDown()){
                    translate(xpos,ypos);
                }
	            if (picked && mevent.isAltDown() && mevent.isMetaDown()){
	                alttranslate(xpos,ypos);
	            }
                break;
            default:
                System.out.println("other event");
            }
        }

        wakeupOn (wakeupCondition);
    }

    // ピッキングして TransformGroup を currGrp に格納する
    private void pickingTransformGroup(int xpos,int ypos){
        TransformGroup tg = null;
        
        // PickCanvas をつかってピッキングしているノードを探す
        pickCanvas.setShapeLocation(xpos, ypos);
        // LineArray と Vertex の両方が重なっている場合も
        // Vertex を取り出すために、pickAll() で全て取り出す
        PickResult pr[] = pickCanvas.pickAll();

        if(pr != null){
            for(int i=0;i<pr.length;i++){
                System.out.println(i + " = " + pr[i].getNode(PickResult.GROUP));
                // ピッキングした結果が読み書き可能な TransformGroup を取り出す
                if ((pr[i] != null) &&
                    ((tg = (TransformGroup)pr[i].getNode(PickResult.TRANSFORM_GROUP)) 
                     != null) &&
                    (tg.getCapability(TransformGroup.ALLOW_TRANSFORM_READ)) && 
                    (tg.getCapability(TransformGroup.ALLOW_TRANSFORM_WRITE)) && 
                    (tg.getCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ))){
                    
                    System.out.println("success to pick " + tg);
                    
                    picked = true;
                    // ピッキングした結果の参照を currGrp にセットする
                    currGrp = tg;
                    targetObj=(LMNTransformGroup)currGrp;
                    targetObj.setVisible(false);
                    // 物体が乗っている座標系とその逆変換を得る
                    currGrp.getLocalToVworld(localT3d);
                    //System.out.println(localT3d);
                    inverse.invert(localT3d);
                    System.out.println(inverse);
                    
                    // ImagePlate から仮想空間への変換行列を得る
                    pickCanvas.getCanvas().
                        getImagePlateToVworld(plate2world);
                    // 視点の位置を得る
                    pickCanvas.getCanvas().getCenterEyeInImagePlate(viewP3d);
                    plate2world.transform(viewP3d);
                    
                    // マウスカーソルの位置に物体を合わせる
                    translate(xpos,ypos);
                    //            freePickResult(pr);
                    break;
                }
            }
        }
    }

    // ピッキングして TransformGroup を currGrp に格納する
    private void altpickingTransformGroup(int xpos,int ypos){
        TransformGroup tg = null;
        
        // PickCanvas をつかってピッキングしているノードを探す
        pickCanvas.setShapeLocation(xpos, ypos);
        // LineArray と Vertex の両方が重なっている場合も
        // Vertex を取り出すために、pickAll() で全て取り出す
        PickResult pr[] = pickCanvas.pickAll();

        if(pr != null){
            for(int i=0;i<pr.length;i++){
                System.out.println(i + " = " + pr[i].getNode(PickResult.GROUP));
                // ピッキングした結果が読み書き可能な TransformGroup を取り出す
                if ((pr[i] != null) &&
                    ((tg = (TransformGroup)pr[i].getNode(PickResult.TRANSFORM_GROUP)) 
                     != null) &&
                    (tg.getCapability(TransformGroup.ALLOW_TRANSFORM_READ)) && 
                    (tg.getCapability(TransformGroup.ALLOW_TRANSFORM_WRITE)) && 
                    (tg.getCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ))){
                    
                    System.out.println("success to pick " + tg);
                    
                    picked = true;
                    // ピッキングした結果の参照を currGrp にセットする
                    currGrp = tg;

                    
                    // 物体が乗っている座標系とその逆変換を得る
                    currGrp.getLocalToVworld(localT3d);
                    //System.out.println(localT3d);
                    inverse.invert(localT3d);
                    System.out.println(inverse);
                    
                    // ImagePlate から仮想空間への変換行列を得る
                    pickCanvas.getCanvas().
                        getImagePlateToVworld(plate2world);
                    // 視点の位置を得る
                    pickCanvas.getCanvas().getCenterEyeInImagePlate(viewP3d);
                    plate2world.transform(viewP3d);
                    
                    // マウスカーソルの位置に物体を合わせる
                    translate(xpos,ypos);
                    //            freePickResult(pr);
                    break;
                }
            }
        }
    }


    private void translate(int xpos,int ypos){
        // マウスカーソルのimageplate上の位置を得る
        pickCanvas.getCanvas().
            getPixelLocationInImagePlate(xpos,ypos,cursorP3d);
        plate2world.transform(cursorP3d);
        // ピッキングした物体の座標を通る
        currGrp.getTransform(currT3d);
        currT3d.get(currV3d);
        currV4d.set(currV3d.x,
                    currV3d.y,
                    currV3d.z,
                    1);
        localT3d.transform(currV4d);
        currV3d.set(currV4d.x,
                    currV4d.y,
                    currV4d.z);
        //System.out.println("cursor = " + cursorP3d);
        //System.out.println("current = " + currV3d);

        // 物体の座標をマウスカーソルの位置にあわせる
        // view と垂直方向な平面上を移動する
        currV3d.sub(viewP3d);
        cursorP3d.sub(viewP3d);
        double alpha = 
            (currV3d.x * viewP3d.x +
             currV3d.y * viewP3d.y +
             currV3d.z * viewP3d.z) /
            (cursorP3d.x * viewP3d.x +
             cursorP3d.y * viewP3d.y +
             cursorP3d.z * viewP3d.z);
        currV3d.scaleAdd(alpha,cursorP3d,viewP3d);
        // 大域座標を局所座標に直す
        currV4d.set(currV3d.x,
                    currV3d.y,
                    currV3d.z,
                    1);
        inverse.transform(currV4d);
        currV3d.set(currV4d.x,
                    currV4d.y,
                    currV4d.z);
        currT3d.setTranslation(currV3d);
        currGrp.setTransform(currT3d);
        transformChanged(MouseBehaviorCallback.TRANSLATE,
                         currT3d);
    }


    private void alttranslate(int xpos,int ypos){
        // マウスカーソルのimageplate上の位置を得る
        pickCanvas.getCanvas().
            getPixelLocationInImagePlate(xpos,ypos,cursorP3d);
        plate2world.transform(cursorP3d);
        // ピッキングした物体の座標を通る
        currGrp.getTransform(currT3d);
        currT3d.get(currV3d);
        currV4d.set(currV3d.x,
                    currV3d.y,
                    currV3d.z,
                    1);
        localT3d.transform(currV4d);
        currV3d.set(currV4d.x,
                    currV4d.y,
                    currV4d.z);
        //System.out.println("cursor = " + cursorP3d);
        //System.out.println("current = " + currV3d);

        // 物体の座標をマウスカーソルの位置にあわせる
        // view と垂直方向な平面上を移動する
        currV3d.sub(viewP3d);
        cursorP3d.sub(viewP3d);
        double alpha = 
            (currV3d.x * viewP3d.x +
             currV3d.y * viewP3d.y +
             currV3d.z * viewP3d.z) /
            (cursorP3d.x * viewP3d.x +
             cursorP3d.y * viewP3d.y +
             cursorP3d.z * viewP3d.z);
        currV3d.scaleAdd(alpha,cursorP3d,viewP3d);
        // 大域座標を局所座標に直す
        currV4d.set(currV3d.x,
                    currV3d.y,
                    currV3d.z,
                    1);
        inverse.transform(currV4d);
        currV3d.set(currV4d.x,
                    currV4d.y,
                    currV4d.z);
        currT3d.setTranslation(currV3d);
        currGrp.setTransform(currT3d);
        transformChanged(MouseBehaviorCallback.TRANSLATE,
                         currT3d);
    }


    
    public void setupCallback(PickingCallback callback){
        this.callback = callback;
    }
    
    public void transformChanged(int type,Transform3D transform){
        if(callback != null){
            callback.transformChanged(type,currGrp);
        }
    }
}
