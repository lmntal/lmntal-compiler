package test3d;

import javax.media.j3d.*;
import java.awt.*;
import javax.vecmath.Vector3d;
import javax.media.j3d.TransparencyAttributes;




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
		
		/*半透明化*/
		TransparencyAttributes transparent=new TransparencyAttributes(
                TransparencyAttributes.BLENDED,0.8f);
		appearance.setTransparencyAttributes(transparent);


		
		ball=new Sphere(0.05f,Sphere.GENERATE_NORMALS ,appearance);
		System.out.println(txt);


        Font font = new Font( "sansserif", Font.PLAIN, 1 );
        Font3D font3D = new Font3D( font, new FontExtrusion() );
        Text3D text = new Text3D( font3D, txt );
        Transform3D translation_txt = new Transform3D();
        translation_txt.setScale(0.1);
        TransformGroup transform_txt = new TransformGroup();

        
        Shape3D shape=new Shape3D();
        Appearance appearance2=new Appearance();
        Material material2=new Material();
    	material2.setEmissiveColor(0.5f,0.02f,0.01f);
		material2.setDiffuseColor(0.10f,0.02f,0.01f);
    	appearance2.setMaterial(material2);	
    	// 光の反射具合を設定
		shape.setGeometry(text);
		shape.setAppearance(appearance2);
		
		//Text2D text = new Text2D(txt, new Color3f(1.0f, 0.0f, 0.0f),
		//	       "Dialog", 20, java.awt.Font.BOLD);
		transform_txt.setTransform(translation_txt);
        transform_txt.addChild(shape);
		transform.addChild(ball);
		transform.addChild(transform_txt);
		
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
