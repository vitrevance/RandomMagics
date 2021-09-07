package randommagics.render;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import randommagics.entities.EntityBigExplosion;
import randommagics.entities.EntityExpulosion;
import randommagics.entities.mobs.EntityDemon;
import randommagics.entities.mobs.EntitySupremeDemon;

public class EntitySpecialRenderer extends Render {
	
	public static final IModelCustom modelEntityExplosion = AdvancedModelLoader.loadModel(new ResourceLocation("randommagics", "textures/models/EntityExpulosion.obj"));
	public static final IModelCustom modelEntityDemon = AdvancedModelLoader.loadModel(new ResourceLocation("randommagics", "textures/models/EntityDemon.obj"));
	public static final IModelCustom modelEntityBigExplosion = AdvancedModelLoader.loadModel(new ResourceLocation("randommagics", "textures/models/EntityBigExplosion.obj"));
	
	public static final ModelHumanoid modelDemon = new ModelHumanoid(modelEntityDemon, new ResourceLocation("randommagics", "textures/models/EntityDemon.png"));

	public EntitySpecialRenderer() {
		super();
		
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f1,
			float f2) {
		if (entity instanceof EntityExpulosion) {
			EntityExpulosion te = (EntityExpulosion)entity;
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);
			//GL11.glColor4f(1, 1, 1, 1);
    		GL11.glTranslated(x, y + 0.5, z);
    		GL11.glRotatef(te.timer, 0.F, 1.F, 0.F);
    		bindTexture(new ResourceLocation("randommagics", "textures/models/EntityExpulosion.png"));
    		modelEntityExplosion.renderPart("0");
    		if (te.timer < te.time - 40)
    			modelEntityExplosion.renderPart("6");
    		if (te.timer < te.time - 45)
    			modelEntityExplosion.renderPart("5");
    		if (te.timer < te.time - 50)
    			modelEntityExplosion.renderPart("4");
    		if (te.timer < te.time - 55)
    			modelEntityExplosion.renderPart("3");
    		if (te.timer < te.time - 60)
    			modelEntityExplosion.renderPart("2");
    		if (te.timer < te.time - 65)
    			modelEntityExplosion.renderPart("1");
    		GL11.glEnable(GL11.GL_CULL_FACE);
    		GL11.glPopMatrix();
		}
		if (entity instanceof EntitySupremeDemon) {
			EntitySupremeDemon te = (EntitySupremeDemon)entity;
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);
			//GL11.glColor4f(1, 1, 1, 1);
    		GL11.glTranslated(x, y, z);
    		GL11.glRotated(te.rotationYaw, 0, 1, 0);
    		GL11.glScaled(2, 2, 2);
    		bindTexture(new ResourceLocation("randommagics", "textures/models/EntitySupremeDemon.png"));
    		modelEntityDemon.renderAll();
    		GL11.glEnable(GL11.GL_CULL_FACE);
    		GL11.glPopMatrix();
		}
		if (entity instanceof EntityDemon) {
			/*
			EntityDemon te = (EntityDemon)entity;
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);
			//GL11.glColor4f(1, 1, 1, 1);
    		GL11.glTranslated(x, y, z);
    		GL11.glRotated(te.rotationYaw, 0, 1, 0);
    		bindTexture(new ResourceLocation("randommagics", "textures/models/EntityDemon.png"));
    		modelEntityDemon.renderAll();
    		GL11.glEnable(GL11.GL_CULL_FACE);
    		GL11.glPopMatrix();
    		*/
			modelDemon.render(this.renderManager.renderEngine, (EntityDemon)entity, x, y, z, f1, f2);
		}
		if (entity instanceof EntityBigExplosion) {
			/*
			EntityBigExplosion te = (EntityBigExplosion)entity;
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);
			//GL11.glColor4f(1, 1, 1, 1);
			//GL11.glScalef(te.size, te.size, te.size);
			
    		GL11.glTranslated(x, y, z);
    		//GL11.glScalef(10, 10, 10);
    		GL11.glScalef(te.size, te.size, te.size);
    		//GL11.glRotated(te.rotationYaw, 0, 1, 0);
    		bindTexture(new ResourceLocation("randommagics", "textures/models/EntityBigExplosion1.png"));
    		modelEntityBigExplosion.renderAll();
    		GL11.glEnable(GL11.GL_CULL_FACE);
    		GL11.glPopMatrix();
    		*/
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

}
