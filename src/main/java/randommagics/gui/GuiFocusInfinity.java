package randommagics.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import randommagics.containers.ContainerFocusInfinity;
import randommagics.containers.InventoryFocusInfinity;
import randommagics.items.FocusInfinity;

@SideOnly(Side.CLIENT)
public class GuiFocusInfinity extends GuiContainer
{
    private ResourceLocation texture = new ResourceLocation("randommagics", "textures/gui/container/FocusInfinityGuiContainer.png");

    private InventoryPlayer inventory;

    public GuiFocusInfinity(EntityPlayer player)
    {
        super(new ContainerFocusInfinity(player.inventory, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ));
        inventory = player.inventory;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        fontRendererObj.drawString(I18n.format("Focus Infinity"), (xSize / 2) - (fontRendererObj.getStringWidth(I18n.format("Focus Infinity")) / 2), 6, 4210752, false);
        fontRendererObj.drawString(I18n.format(inventory.getInventoryName()), 8, ySize - 96 + 2, 4210752);
    }
}
