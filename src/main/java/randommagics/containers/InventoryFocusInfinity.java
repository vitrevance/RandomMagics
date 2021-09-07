package randommagics.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import randommagics.items.ItemInfinityStone;
import thaumcraft.api.wands.ItemFocusBasic;

public class InventoryFocusInfinity implements IInventory{

	public ItemStack items[];
	ItemStack stack;
	
	public InventoryFocusInfinity(ItemStack item) {
		items = new ItemStack[6];
		stack = item;
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		readFromNBT(stack.getTagCompound());
	}
	
	@Override
	public int getSizeInventory() {
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return slot < getSizeInventory() ? items[slot] : null;
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if(items[par1] != null)
        {
            ItemStack var3;
            if(items[par1].stackSize <= par2)
            {
                var3 = items[par1];
                items[par1] = null;
                return var3;
            }
            var3 = items[par1].splitStack(par2);
            if(items[par1].stackSize == 0)
            	items[par1] = null;
            return var3;
        } else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if(items[slot] != null)
        {
            ItemStack var2 = items[slot];
            items[slot] = null;
            return var2;
        } else
        {
            return null;
        }
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack stack) {
		items[par1] = stack;
		markDirty();
	}

	@Override
	public String getInventoryName() {
		return "inventory";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void markDirty() {
		for (int i = 0; i < getSizeInventory(); ++i)
		{
			if (getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0) {
				items[i] = null;
			}
		}
		
		// This line here does the work:		
		writeToNBT(stack.getTagCompound());
	}
	
	public void writeToNBT(NBTTagCompound tagcompound)
	{
		// Create a new NBT Tag List to store itemstacks as NBT Tags
		NBTTagList items = new NBTTagList();

		for (int i = 0; i < getSizeInventory(); ++i)
		{
			// Only write stacks that contain items
			if (getStackInSlot(i) != null)
			{
				// Make a new NBT Tag Compound to write the itemstack and slot index to
				NBTTagCompound item = new NBTTagCompound();
				item.setInteger("Slot", i);
				// Writes the itemstack in slot(i) to the Tag Compound we just made
				getStackInSlot(i).writeToNBT(item);

				// add the tag compound to our tag list
				items.appendTag(item);
			}
		}
		// Add the TagList to the ItemStack's Tag Compound with the name "ItemInventory"
		tagcompound.setTag("ItemInventory", items);
	}
	
	public void readFromNBT(NBTTagCompound compound)
	{
		// Gets the custom taglist we wrote to this compound, if any
		// 1.7.2+ change to compound.getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);
		NBTTagList items = compound.getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);

		for (int i = 0; i < items.tagCount(); ++i)
		{
			// 1.7.2+ change to items.getCompoundTagAt(i)
			NBTTagCompound item = (NBTTagCompound) items.getCompoundTagAt(i);
			int slot = item.getInteger("Slot");

			// Just double-checking that the saved slot index is within our inventory array bounds
			if (slot >= 0 && slot < getSizeInventory()) {
				this.items[slot] = ItemStack.loadItemStackFromNBT(item);
			}
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack != null && (stack.getItem() instanceof ItemInfinityStone) && stack.getItemDamage() == slot;

	}

}
