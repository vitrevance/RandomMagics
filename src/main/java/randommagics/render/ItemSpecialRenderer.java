package randommagics.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import randommagics.Init;
import randommagics.blocks.TileLifeEssenceJar;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;

public class ItemSpecialRenderer implements IItemRenderer {
	
	private RenderBlocks rb;

	public ItemSpecialRenderer() {
		rb = new RenderBlocks();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return helper != net.minecraftforge.client.IItemRenderer.ItemRendererHelper.EQUIPPED_BLOCK;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		TileLifeEssenceJar jar = new TileLifeEssenceJar();
		if (stack != null && stack.hasTagCompound()) {
			jar.readCustomNBT(stack.getTagCompound());
		}
		double x = 0D;
		double y = 0D;
		double z = 0D;
		if (type == ItemRenderType.ENTITY) {
			x = -0.5D;
			y = -0.25D;
			z = -0.5D;
		}
		if (type == ItemRenderType.EQUIPPED) {
			z = -0.5D;
		}
		TileEntityRendererDispatcher.instance.renderTileEntityAt(jar, x, y, z, 0.0F);
		GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        rb.useInventoryTint = true;
        rb.renderBlockAsItem(Init.BlockLifeEssenceJar, 0, 1.F);
        GL11.glPopMatrix();
        GL11.glEnable(32826);
		//rb.renderBlockAsItem(Init.BlockLifeEssenceJar, 0, 1.F);
	}
	
}