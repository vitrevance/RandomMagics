package randommagics.containers;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ContainerStupidAnvil extends Container implements IInventory {
	
	EntityPlayer player;
	
	ItemStack slots[] = new ItemStack[3];
	
	public ContainerStupidAnvil(EntityPlayer player) {
		this.player = player;
		addSlotToContainer(new Slot(this, 0, 23, 35));
		addSlotToContainer(new Slot(this, 1, 63, 35));
		addSlotToContainer(new Slot(this, 2, 119, 35));
		
		for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++)
        {
            addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
        detectAndSendChanges();
	}
	
	private void updtaeResult(int slot) {
		if (slot < 2 && slots[0] != null && slots[1] != null) {
			if (slots[1].getEnchantmentTagList() == null) {
				slots[2] = null;
				return;
			}
			ItemStack res = slots[0].copy();
			int n = slots[1].getEnchantmentTagList().tagCount();
			if (n < 0) {
				return;
			}/*
			if (res.getEnchantmentTagList() == null) {
				if (res.getTagCompound() == null) {
					res.setTagCompound(new NBTTagCompound());
				}
				res.stackTagCompound.setTag("ench", res.getTagCompound().getTagList("ench", 10));
			}
			else*/
			for (int i = 0; i < n; i++) {
				res.addEnchantment(Enchantment.enchantmentsList[slots[1].getEnchantmentTagList().getCompoundTagAt(i).getShort("id")], slots[1].getEnchantmentTagList().getCompoundTagAt(i).getShort("lvl"));
			}
			slots[2] = res;
		}
		else {
			if (slot == 2 && slots[2] != null && slots[0] != null && slots[1] != null) {
				slots[0] = null;
				/*
				int i = slots[1].getEnchantmentTagList().tagCount() - 1;
				for (; i >= 0; i--) {
					slots[1].getEnchantmentTagList().removeTag(i);
				}
				*/
				slots[1].stackTagCompound.removeTag("ench");
			}
			if (slot < 2)
				slots[2] = null;
		}
		detectAndSendChanges();
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return slots[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int am) {
		if (slots[slot] != null) {
			ItemStack ret = slots[slot];
			slots[slot] = null;
			return ret;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (slot == 2) {
			slots[2] = null;
			return null;
		}
		return slots[slot];
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		if (!player.worldObj.isRemote) {
			for (int i = 0; i < 3; i++) {
				ItemStack itemstack = getStackInSlotOnClosing(i);
				if (itemstack != null) {
					player.dropPlayerItemWithRandomChoice(itemstack, false);
				}
			}
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		slots[slot] = stack;
	}

	@Override
	public String getInventoryName() {
		return "StupidAnvil";
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot == 2)
			return false;
		if (slot == 1 && stack != null) {
			return stack.getEnchantmentTagList() != null;
		}
		return true;
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}
	
	@Override
	public ItemStack slotClick(int slot, int p_75144_2_, int p_75144_3_, EntityPlayer p_75144_4_) {
		if (slot == 2 && slots[2] == null)
			return null;
		if (slot == 2)
			updtaeResult(slot);
		ItemStack ret = super.slotClick(slot, p_75144_2_, p_75144_3_, p_75144_4_);
		if (slot < 2)
			updtaeResult(slot);
		return ret;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		/*
		if (slot > 3) {
			if (slots[0] == null) {
				ItemStack st = ((Slot)inventorySlots.get(slot)).getStack();
				if (isItemValidForSlot(0, st)) {
					setInventorySlotContents(0, ((Slot)inventorySlots.get(slot)).decrStackSize(1));
				}
				updtaeResult(0);
			}
			else {
				if (slots[1] == null) {
					ItemStack st = ((Slot)inventorySlots.get(slot)).getStack();
					if (isItemValidForSlot(1, st)) {
						setInventorySlotContents(1, ((Slot)inventorySlots.get(slot)).decrStackSize(1));
					}
				}
				updtaeResult(1);
			}
		}
		return null;
		*/
		//ItemStack stack = null;
        Slot slotObject = (Slot)inventorySlots.get(slot);
        if(slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            //stack = stackInSlot.copy();
            if(slot < 3)
            {
                if(!isItemValidForSlot(slot, stackInSlot) || !mergeItemStack(stackInSlot, 3, inventorySlots.size(), true))
                    return null;
                else
                	slots[slot] = null;
                updtaeResult(slot);
            } else
            if (slots[0] == null && isItemValidForSlot(0, stackInSlot)) {
            	setInventorySlotContents(0, slotObject.decrStackSize(1));
                updtaeResult(0);
            }
            else
            if (slots[1] == null && isItemValidForSlot(1, stackInSlot)) {
            	setInventorySlotContents(1, slotObject.decrStackSize(1));
                updtaeResult(1);
            }
        }
        return null;
	}
}
