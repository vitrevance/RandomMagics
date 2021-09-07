package randommagics.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import randommagics.blocks.TileInventoryAccess;
import randommagics.containers.ContainerFocusInfinity;
import randommagics.containers.ContainerInventoryAccess;
import randommagics.containers.ContainerMagicianHat;
import randommagics.containers.ContainerStupidAnvil;
import randommagics.containers.InventoryFocusInfinity;
import randommagics.customs.DemonBossFight;
import randommagics.items.FocusInfinity;

public class GUIHandler implements IGuiHandler{
	
	public enum GuiIDs {
		GUI_ELD_ARMOR_ID,
		GUI_FOCUS_INFINITY_ID,
		GUI_INVENTORY_ACCESS,
		GUI_STUPID_ANVIL,
		GUI_MAGICIAN_HAT
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GuiIDs.GUI_ELD_ARMOR_ID.ordinal())
			return new GuiEldArmor();
		if (ID == GuiIDs.GUI_FOCUS_INFINITY_ID.ordinal())
			return new ContainerFocusInfinity(player.inventory, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
		if (ID == GuiIDs.GUI_INVENTORY_ACCESS.ordinal())
			return new ContainerInventoryAccess((TileInventoryAccess)world.getTileEntity(x, y, z));
		if (ID == GuiIDs.GUI_STUPID_ANVIL.ordinal())
			return new ContainerStupidAnvil(player);
		if (ID == GuiIDs.GUI_MAGICIAN_HAT.ordinal())
			return new ContainerMagicianHat(player.inventory, world, x, y, z);
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GuiIDs.GUI_ELD_ARMOR_ID.ordinal())
            return new GuiEldArmor();
		if (ID == GuiIDs.GUI_FOCUS_INFINITY_ID.ordinal())
			return new GuiFocusInfinity(player);
		if (ID == GuiIDs.GUI_INVENTORY_ACCESS.ordinal())
			return new GuiInventoryAccess((TileInventoryAccess)world.getTileEntity(x, y, z));
		if (ID == GuiIDs.GUI_STUPID_ANVIL.ordinal())
			return new GuiStupidAnvil(player);
		if (ID == GuiIDs.GUI_MAGICIAN_HAT.ordinal())
			return new GuiMagicianHat(player);
		
		return null;
	}

}
