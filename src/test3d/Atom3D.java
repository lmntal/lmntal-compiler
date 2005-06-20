package test3d;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;
import com.sun.j3d.utils.geometry.Text2D;
import javax.vecmath.*;


import com.sun.j3d.utils.geometry.Sphere;

public class Atom3D {
	Double3DPoint pos;
	
	public Atom3D(){
		//System.out.println("Atom3D ok");
	}
	
	public void createNode(BranchGroup objBranch){
		initNode();
		objBranch.addChild(createBall(pos.toPoint()));
		//System.out.println("creatNode ok");
	}
	public void createNode(BranchGroup objBranch,Double3DPoint vpos){
		objBranch.addChild(createBall(vpos.toPoint()));		
	}
	
	public void createNode(LMNTransformGroup objT,Double3DPoint vpos,String txt){
		createBall(vpos.toPoint(),txt,objT);
	}
	
	public void initNode() {
		pos = new Double3DPoint();
	}
	public Double3DPoint getPosition() {
		return pos;
	}
	public void setPosition(Double3DPoint p) {
		pos = p;
	}
	/**球と文字の生成**/
    public Node createBall(Vector3d v,String txt,LMNTransformGroup transform){
		Transform3D translation;
		Appearance appearance;
		Material material;
		Sphere ball;
	
		translation=new Transform3D();
		translation.setTranslation(v);
		//transform=new TransformGroup(translation);// 平行移動
		transform.setTransform(translation);
		transform.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		transform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		transform.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		transform.setCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ);

		appearance=new Appearance();
		
		material=new Material();
		
		/*オブジェクトの色分け*/
		if(txt.matches("^[0-9]*$")){
			material.setEmissiveColor(0.50f,0.02f,0.01f);
			material.setDiffuseColor(0.10f,0.02f,0.01f);
		}
		else if(txt=="."){
			material.setEmissiveColor(0.01f,0.02f,0.50f);
			material.setDiffuseColor(0.01f,0.02f,0.10f);
		}
		else if(txt=="[]"){
			material.setEmissiveColor(0.01f,0.50f,0.50f);
			material.setDiffuseColor(0.01f,0.10f,0.10f);
		}else{
			material.setEmissiveColor(0.01f,0.50f,0.02f);
			material.setDiffuseColor(0.01f,0.10f,0.02f);
		}
		
		material.setLightingEnable(true);
		appearance.setMaterial(material);			// 光の反射具合を設定
		
		ball=new Sphere(0.05f,Sphere.GENERATE_NORMALS ,appearance);
		System.out.println(txt);
		Text2D text = new Text2D(txt, new Color3f(1.0f, 0.0f, 0.0f),
			       "Dialog", 20, java.awt.Font.BOLD);

		transform.addChild(ball);
		transform.addChild(text);
		
		return transform;
    }
    /**球のみの生成**/
    public Node createBall(Vector3d v){
		Transform3D translation;
		TransformGroup transform;
		Appearance appearance;
		Material material;
		Sphere ball;
	
		translation=new Transform3D();
		translation.setTranslation(v);
		transform=new TransformGroup(translation);// 平行移動
		transform.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		transform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		transform.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		appearance=new Appearance();
		
		material=new Material();
		material.setDiffuseColor(0.01f,0.10f,0.02f);
		material.setLightingEnable(true);
		material.setEmissiveColor(0.01f,0.50f,0.02f);
		appearance.setMaterial(material);			// 光の反射具合を設定
		
		ball=new Sphere(0.05f,Sphere.GENERATE_NORMALS ,appearance);
		
		transform.addChild(ball);
		
		return transform;
    }
	
}
