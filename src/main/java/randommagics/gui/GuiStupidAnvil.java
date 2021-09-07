package randommagics.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import randommagics.blocks.TileInventoryAccess;
import randommagics.containers.ContainerInventoryAccess;
import randommagics.containers.ContainerStupidAnvil;

public class GuiStupidAnvil extends GuiContainer {
	
	private ResourceLocation texture = new ResourceLocation("randommagics", "textures/gui/container/StupidAnvilGuiContainer.png");

	public GuiStupidAnvil(EntityPlayer player) {
		super(new ContainerStupidAnvil(player));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        fontRendererObj.drawString(I18n.format(StatCollector.translateToLocal("text.stupidanvil")), (xSize / 2) - (fontRendererObj.getStringWidth(I18n.format(StatCollector.translateToLocal("text.stupidanvil"))) / 2), 6, 4210752, false);
        fontRendererObj.drawString(I18n.format("Inventory"), 8, ySize - 96 + 2, 4210752);
        //GL11.glColor4f(1, 1, 1, 255);
        //GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        //drawTexturedModalRect(x, y, 0, 0, 16, 16);
    }
}
