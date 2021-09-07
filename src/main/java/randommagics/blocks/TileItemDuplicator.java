package randommagics.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.api.TileThaumcraft;

public class TileItemDuplicator extends TileThaumcraft implements IInventory {
	
	public ItemStack[] slots = new ItemStack[2];
	
	public TileItemDuplicator() {
		
	}
	
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (this.slots[0] != null) {
			if (this.slots[1] != null && slotStacksEqual()) {
				if (this.slots[1].stackSize < this.slots[1].getMaxStackSize()) {
					this.slots[1].stackSize++;
				}
			}
			else {
				this.slots[1] = this.slots[0].copy();
				this.slots[1].stackSize = 1;
			}
		}
	}
	
	private boolean slotStacksEqual() {
		if (this.slots[0] == null || this.slots[1] == null)
			return false;
		
		ItemStack s1 = this.slots[0].copy();
		ItemStack s2 = this.slots[1].copy();
		s1.stackSize = 1;
		s2.stackSize = 1;
		return ItemStack.areItemStacksEqual(s1, s2);
		
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound nbt) {
		if (nbt.getBoolean("firstStackis")) {
			this.slots[0] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("first"));
		}
		else {
			this.slots[0] = null;
		}
		if (nbt.getBoolean("secondStackis")) {
			this.slots[1] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("second"));
		}
		else {
			this.slots[1] = null;
		}
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound nbt) {
		if (this.slots[0] != null) {
			nbt.setBoolean("firstStackis", true);
			NBTTagCompound i1 = new NBTTagCompound();
			this.slots[0].writeToNBT(i1);
			nbt.setTag("first", i1);
		}
		else {
			nbt.setBoolean("firstStackis", true);
		}
		if (this.slots[1] != null) {
			nbt.setBoolean("secondStackis", true);
			NBTTagCompound i2 = new NBTTagCompound();
			this.slots[1].writeToNBT(i2);
			nbt.setTag("second", i2);
		}
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.slots[slot + 1];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (slot == 0) {
			if (amount >= this.slots[1].stackSize) {
				ItemStack ret = this.slots[1].copy();
				this.slots[1] = null;
				return ret;
			}
			else {
				this.slots[1].stackSize -= amount;
				ItemStack ret = this.slots[1].copy();
				ret.stackSize = amount;
				return ret;
			}
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return this.slots[1];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
	}

	@Override
	public String getInventoryName() {
		return "inventoryItemDuplicator";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return false;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return false;
	}
}
