package randommagics.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import randommagics.blocks.TileInventoryAccess;
import thaumcraft.common.config.ConfigBlocks;

public class ContainerInventoryAccess extends Container {
	
	private TileInventoryAccess tile;
	
	public ContainerInventoryAccess(TileInventoryAccess tile) {
		if (tile.player != null) {
			//Slot slot = tile.player.inventoryContainer.getSlot(tile.slotid);
			//slot.xDisplayPosition = 44 + 0 * 18;
			//slot.yDisplayPosition = 17 + 1 * 18;
			//addSlotToContainer(slot);
			
			this.tile = tile;
			
			InventoryPlayer iinventory = tile.player.inventory;
			
			addSlotToContainer(new Slot(tile, 0, 44 + 2 * 18, 17 + 1 * 18));
			
			//���������
	        for (int i = 0; i < 3; i++)
	        {
	            for (int j = 0; j < 9; j++)
	            {
	            		addSlotToContainer(new Slot(iinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
	            }
	        }
	        //������
	        for (int i = 0; i < 9; i++)
	        {
            		addSlotToContainer(new Slot(iinventory, i, 8 + i * 18, 142));
	        }
		}
		detectAndSendChanges();
	}
	
	@Override
	public ItemStack slotClick(int slot, int p_75144_2_, int p_75144_3_, EntityPlayer player) {
		if (player == tile.player)
		if (slot > 0)
			if (slot > 27)
				tile.slotid = slot - 28;
			else
				tile.slotid = slot + 8;
		return null;
		//return super.slotClick(slot, p_75144_2_, p_75144_3_, player);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

}
