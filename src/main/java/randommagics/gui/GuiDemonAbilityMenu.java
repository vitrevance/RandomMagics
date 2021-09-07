package randommagics.gui;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.XRandR.Screen;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ibxm.ScreamTracker3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import randommagics.KeyHandler;
import randommagics.customs.CustomExtendedEntityProperties;
import randommagics.render.RenderUtils;

public class GuiDemonAbilityMenu extends Gui {
	
	public int cur;
	private ResourceLocation texture_main_plain = new ResourceLocation("randommagics", "textures/models/DemonAbilityMenuMain.png");
	boolean lastState;
	Minecraft mc = Minecraft.getMinecraft();
	
	public GuiDemonAbilityMenu() {
		super();
		cur = 0;
	}
	
	public GuiDemonAbilityMenu(int current_ability) {
		super();
		cur = current_ability;
	}
	
	@SubscribeEvent
	public void onRenderGameOverlayEventPost(RenderGameOverlayEvent.Post event) {
		/*Tessellator tesselator = Tessellator.instance;
		tesselator.setColorRGBA(200, 20, 10, 156);
		tesselator.addVertex(0, 0, 0);
		tesselator.addVertex(0, 0, 100);
		tesselator.addVertex(100, 0, 100);
		tesselator.addVertex(100, 0, 0);
		tesselator.draw();*/
		if (event.type == ElementType.TEXT) {
			CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(Minecraft.getMinecraft().thePlayer);
			if (ex == null)
				return;
			if (ex.demonLevel < 1)
				return;
		if (KeyHandler.keyDemonAbilityMenu.getIsKeyPressed()) {
			if (mc.inGameHasFocus) {
				mc.inGameHasFocus = false;
				mc.mouseHelper.ungrabMouseCursor();
			}
			lastState = true;
			GL11.glPushMatrix();
			//GL11.glEnable(GL11.GL_ALPHA);
			//GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			int h = event.resolution.getScaledHeight(); //Minecraft.getMinecraft().displayHeight;
			int w = event.resolution.getScaledWidth(); //Minecraft.getMinecraft().displayWidth;
			//Minecraft.getMinecraft().getTextureManager().bindTexture(texture_main_plain);
			//drawTexturedModalRect(w / 4 - 128, h / 4 - 128, 0, 0, 256, 256);
			//GL11.glTranslated(w / 2 - 128, h / 2 - 128, 0);
			//drawTexturedModalRect(0, 0, 0, 0, 256, 256);
			GL11.glTranslated(w / 2, h / 2, 0);
			GL11.glAlphaFunc(516, 0.003921569F);
	        GL11.glEnable(3042);
	        GL11.glBlendFunc(770, 771);
			RenderUtils.renderQuadCenteredFromTexture(texture_main_plain, w * 0.46F, 0.5F, 0.5F, 0.5F, 200, 771, 1F);
			GL11.glDisable(3042);
	        GL11.glAlphaFunc(516, 0.1F);
			//GL11.glScalef(0.2F, 0.2F, 0.2F);
			GL11.glPopMatrix();
			for (int i = 1; i < 9; i++) {
				//Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("randommagics", "textures/models/DemonAbility_" + i + "_" + (ex.demonLevel > i + 1 ? "ON" : "OFF") + ".png"));
				//drawTexturedModalRect(0, 0, 0, 0, 256, 256);
				GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glTranslated(w / 2, h / 2, 0);
				int ang = (i - 1) * 45 - 90;
				double xx = MathHelper.cos((ang / 180F) * 3.141593F) * h * 0.3F;
	            double yy = MathHelper.sin((ang / 180F) * 3.141593F) * h * 0.3F;
	            GL11.glTranslated(xx, yy, 0);
				float col = (ex.demonLevel > i + 1 ? 1F : 0.5F);
				//RenderUtils.renderQuadCenteredFromTexture(new ResourceLocation("randommagics", "textures/models/DemonAbility_" + i + "_" + (ex.demonLevel > i + 1 ? "ON" : "OFF") + ".png"), h * 0.1F/*w * 0.46F*/, col, col, col, 200, 771, 1F);
				RenderUtils.renderQuadCenteredFromTexture(new ResourceLocation("randommagics", "textures/models/DemonAbility_" + i + ".png"), h * 0.1F/*w * 0.46F*/, col, col, col, 200, 771, 1F);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}
			
			if (ex.demonLevel > ex.demonability + 2) {
				GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glTranslated(w / 2, h / 2, 0);
				//GL11.glScaled(0.2, 0.2, 1);
				int ang = ex.demonability * 45 - 90;
				double xx = MathHelper.cos((ang / 180F) * 3.141593F) * h * 0.3F;
	            double yy = MathHelper.sin((ang / 180F) * 3.141593F) * h * 0.3F;
	            GL11.glTranslated(xx, yy, 0);
				RenderUtils.renderQuadCenteredFromTexture(new ResourceLocation("randommagics", "textures/models/DemonAbilityChoose.png"), w * 0.1F, 1F, 1F, 1F, 200, 771, 1F);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}
		}
		else {
			if (mc.currentScreen == null && lastState) {
				if (Display.isActive() && !mc.inGameHasFocus) {
					mc.inGameHasFocus = true;
					mc.mouseHelper.grabMouseCursor();
				}
				lastState = false;
			}
		}
		if (ex.demonLevel > ex.demonability + 2 && ex.usesdemonability) {
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			int h = event.resolution.getScaledHeight();
			int w = event.resolution.getScaledWidth();
			GL11.glTranslated(w * 0.05F, h * 0.05F, 0);
			RenderUtils.renderQuadCenteredFromTexture(new ResourceLocation("randommagics", "textures/models/DemonAbility_" + (ex.demonability + 1) + ".png"), h * 0.1F, 1, 1, 1, 200, 771, 1F);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
		}
	}
}
