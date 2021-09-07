package randommagics.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

public class ModelHumanoid {
	
	private double armsH, armsW, legsH, headH;
	private String leftArm, rightArm, leftleg, rightLeg, body, head;
	private final IModelCustom model;
	private final ResourceLocation texture;
	
	public ModelHumanoid(IModelCustom model, ResourceLocation texture) {
		this.model = model;
		this.texture = texture;
	}
	
	public ModelHumanoid setArmsHeight(double h) {
		this.armsH = h;
		return this;
	}
	
	public ModelHumanoid setArmsWidth(double w) {
		this.armsW = w;
		return this;
	}
	
	public ModelHumanoid setLegsHeight(double h) {
		this.legsH = h;
		return this;
	}
	
	public ModelHumanoid setHeadHeight(double h) {
		this.headH = h;
		return this;
	}
	
	public ModelHumanoid setLeftArm(String name) {
		this.leftArm = name;
		return this;
	}
	
	public ModelHumanoid setRightArm(String name) {
		this.rightArm = name;
		return this;
	}
	
	public ModelHumanoid setLeftleg(String name) {
		this.leftleg = name;
		return this;
	}
	
	public ModelHumanoid setRightLeg(String name) {
		this.rightLeg = name;
		return this;
	}
	
	public ModelHumanoid setBody(String name) {
		this.body = name;
		return this;
	}
	
	public ModelHumanoid setHead(String name) {
		this.head = name;
		return this;
	}
	
	public void render(TextureManager texManager, EntityMob entity, double x, double y, double z, float f1, float f2) {
		GL11.glPushMatrix();
		
		GL11.glTranslated(x, y, z);
		GL11.glRotated(entity.rotationYaw, 0, 1, 0);
		//System.out.println(entity.rotationYaw);
		texManager.bindTexture(this.texture);
		this.model.renderAll();
		
		GL11.glPopMatrix();
	}
}