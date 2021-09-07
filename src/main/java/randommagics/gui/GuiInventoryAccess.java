package randommagics.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import randommagics.blocks.TileInventoryAccess;
import randommagics.containers.ContainerInventoryAccess;

public class GuiInventoryAccess extends GuiContainer {
	
	private ResourceLocation texture = new ResourceLocation("randommagics", "textures/gui/container/InventoryAccessGuiContainer.png");
	private TileInventoryAccess te;

	public GuiInventoryAccess(TileInventoryAccess container) {
		super(new ContainerInventoryAccess(container));
		te = container;
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
        fontRendererObj.drawString(I18n.format("Inventory Access"), (xSize / 2) - (fontRendererObj.getStringWidth(I18n.format("Inventory Access")) / 2), 6, 4210752, false);
        fontRendererObj.drawString(I18n.format("Inventory"), 8, ySize - 96 + 2, 4210752);
        fontRendererObj.drawString(I18n.format(String.valueOf(te.slotid)), (xSize / 2) - ((fontRendererObj.getStringWidth(I18n.format(String.valueOf(te.slotid))))) / 2, ySize - 110 + 2, 4210752);
        if (te.player != null)
        	fontRendererObj.drawString(I18n.format(te.player.getDisplayName()), (xSize / 2) - ((fontRendererObj.getStringWidth(I18n.format(te.player.getDisplayName())))) / 2, 16 + 2, 4210752);
        //GL11.glColor4f(1, 1, 1, 255);
        int x = 0;
        int y = 0;
        if (te.slotid < 9) {
        	x = 8 + te.slotid * 18;
        	y = 142;
        }
        else {
        	int row = te.slotid / 9 - 1;
        	int col = te.slotid % 9;
        	x = 8 + col * 18;
        	y = 84 + row * 18;
        }
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("randommagics", "textures/gui/container/plain.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(x, y, 0, 0, 16, 16);
        //drawRect(x, y, x + 18, y + 18, 128);
    }

}
