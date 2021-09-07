package randommagics.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderUtils {
	
	public static void renderQuadCenteredFromTexture(ResourceLocation texture, float scale, float red, float green, float blue, int brightness, int blend, float opacity)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        renderQuadCenteredFromTexture(scale, red, green, blue, brightness, blend, opacity);
    }

    public static void renderQuadCenteredFromTexture(float scale, float red, float green, float blue, int brightness, int blend, float opacity)
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glScalef(scale, scale, scale);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, blend);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, opacity);
        tessellator.startDrawingQuads();
        if(brightness > 0)
            tessellator.setBrightness(brightness);
        tessellator.setColorRGBA_F(red, green, blue, opacity);
        tessellator.addVertexWithUV(-0.5D, 0.5D, 0.0D, 0.0D, 1.0D);
        tessellator.addVertexWithUV(0.5D, 0.5D, 0.0D, 1.0D, 1.0D);
        tessellator.addVertexWithUV(0.5D, -0.5D, 0.0D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(-0.5D, -0.5D, 0.0D, 0.0D, 0.0D);
        tessellator.draw();
        GL11.glDisable(3042);
    }
    
    public static void renderItemStack(ItemStack stack, float partTicks) {
    	EntityItem entityitem = null;
        float ticks = (float)Minecraft.getMinecraft().renderViewEntity.ticksExisted + partTicks;
        float h = MathHelper.sin((ticks % 32767F) / 16F) * 0.05F;
        GL11.glTranslatef(0f, h, 0f);
        GL11.glRotatef(ticks % 360F, 0.0F, 1.0F, 0.0F);
        if(stack.getItem() instanceof ItemBlock)
            GL11.glScalef(2.0F, 2.0F, 2.0F);
        else
            GL11.glScalef(1.0F, 1.0F, 1.0F);
        ItemStack is = stack.copy();
        is.stackSize = 1;
        entityitem = new EntityItem(null, 0.0D, 0.0D, 0.0D, is);
        entityitem.hoverStart = 0.0F;
        RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
        if(!Minecraft.isFancyGraphicsEnabled())
        {
            GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
            RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
        }
    }
}
