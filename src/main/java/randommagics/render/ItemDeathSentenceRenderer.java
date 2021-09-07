package randommagics.render;

import java.util.Random;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

public class ItemDeathSentenceRenderer implements IItemRenderer {
	
	private static Random rand = new Random();
	
	public ItemDeathSentenceRenderer() {
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return type != ItemRenderType.EQUIPPED_FIRST_PERSON && type != ItemRenderType.ENTITY;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if(item != null)
		{
			GL11.glPushMatrix();
			
			if (rand.nextBoolean() && Minecraft.getMinecraft().getSystemTime() % 1000 < 200) {
				if (rand.nextBoolean()) {
					ModShaders.glitch.start();
					ARBShaderObjects.glUniform1fARB(ModShaders.glitch.getUniform("randomUniform"), rand.nextFloat());
					ARBShaderObjects.glUniform1fARB(ModShaders.glitch.getUniform("dislocForce"), 0.01f);
				}
				else {
					ModShaders.grey.start();
				}
			}
			
			if(type == ItemRenderType.EQUIPPED_FIRST_PERSON)
			{
				GL11.glScaled(3, 3, 3);
				GL11.glTranslated(0, -0.4D, 0.3D);
				GL11.glRotated(15, 0, 1, 0);
				GL11.glRotated(15, 0, 0, 1);
				IIcon icon = item.getIconIndex();
				ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMinU(), icon.getMinV(), icon.getMaxU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.03125F);
			}
			
			if(type == ItemRenderType.INVENTORY)
			{
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				
				GL11.glTranslatef(0.7F, -0.9F, 1);
				GL11.glScaled(2, 2, 2);
				GL11.glRotated(60, 0, 1, 1);
				IIcon icon = item.getIconIndex();
				ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMinU(), icon.getMinV(), icon.getMaxU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
			}
			
			if(type == ItemRenderType.ENTITY)
			{
				GL11.glScaled(4, 4, 4);
				GL11.glRotated(90, 1, 0, 0);
				GL11.glRotated(35, 0, 0, 1);
				GL11.glTranslated(-0.5D, -0.5D, 0.05D);
				IIcon icon = item.getIconIndex();
				ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMinU(), icon.getMinV(), icon.getMaxU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
			}
			
			if(type == ItemRenderType.EQUIPPED)
			{
				GL11.glScaled(6, 6, 6);
				GL11.glRotated(135, 0, 1, 0);
				GL11.glRotatef(-35, 0, 0, 1);
				GL11.glTranslated(-0.7D, -0.4D, 0.01D);
				IIcon icon = item.getIconIndex();
				ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMinU(), icon.getMinV(), icon.getMaxU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0225F);
			}
			
			if (ShaderProgram.currentShaderID == ModShaders.glitch.programID || ShaderProgram.currentShaderID == ModShaders.grey.programID) {
				ModShaders.glitch.stop();
			}
			
			GL11.glPopMatrix();
		}
	}
	
	
}
