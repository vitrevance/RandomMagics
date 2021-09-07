package randommagics.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.tiles.TileJar;
import thaumcraft.common.tiles.TileJarFillable;

public class TileAlchemyPlant extends TileThaumcraft implements IAspectContainer, IInventory, IEssentiaTransport {
	
	public ItemStack stack;
	public AspectList aspects;
	public int max = 1048576;
	public byte side = 2;
	
	public TileAlchemyPlant() {
		stack = null;
		aspects = new AspectList();
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (stack != null && stack.stackSize > 0) {
			AspectList asp = ThaumcraftCraftingManager.getObjectTags(stack);
			asp = ThaumcraftCraftingManager.getBonusTags(stack, asp);
			while (aspects.visSize() + asp.visSize() <= max && stack.stackSize > 0) {
				aspects.add(asp);
				//decrStackSize(0, 1);
				stack.stackSize--;
				
			}
		}
		if (stack != null && stack.stackSize <= 0)
			this.stack = null;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound nbttagcompound) {
		NBTTagCompound asp = new NBTTagCompound();
		aspects.writeToNBT(asp);
		NBTTagCompound st = new NBTTagCompound();
		if (stack != null) {
			stack.writeToNBT(st);
		}
		nbttagcompound.setTag("aspects", asp);
		nbttagcompound.setTag("stack", st);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound nbttagcompound) {
		aspects.readFromNBT(nbttagcompound.getCompoundTag("aspects"));
		stack = ItemStack.loadItemStackFromNBT(nbttagcompound.getCompoundTag("stack"));
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return stack;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		/*
		if (stack.stackSize > amount) {
			stack.stackSize -= amount;
			return new ItemStack(stack.getItem(), amount, stack.getItemDamage());
		}
		else {
			if (stack.stackSize == amount) {
				ItemStack ret = new ItemStack(stack.getItem(), amount, stack.getItemDamage());
				stack = null;
				return ret;
			}
			else {
				ItemStack ret = stack.copy();
				stack = null;
				return ret;
			}
		}*/
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.stack = stack;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}

	@Override
	public String getInventoryName() {
		return "Alchemy inventory";
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
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack stack) {
		if (this.stack != null && stack != null) {
			return this.stack.stackSize == 0 || (this.stack.getItem() == stack.getItem() && this.stack.getItemDamage() == stack.getItemDamage() && this.stack.stackSize + stack.stackSize < 65);
		}
		//System.out.println("Is item valid: " + this.stack == null || this.stack.stackSize == 0 || (this.stack.getItem() == stack.getItem() && this.stack.getItemDamage() == stack.getItemDamage() && this.stack.stackSize + stack.stackSize < 65));
		return true;
	}

	@Override
	public AspectList getAspects() {
		return this.aspects;
	}

	@Override
	public void setAspects(AspectList aspects) {
	}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return false;
	}

	@Override
	public int addToContainer(Aspect tag, int amount) {
		return amount;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		if (doesContainerContainAmount(tag, amount)) {
			aspects.remove(tag, amount);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			markDirty();
			return true;
		}
		return false;
	}

	@Override
	public boolean takeFromContainer(AspectList ot) {
		if (doesContainerContain(ot)) {
			Aspect[] in = ot.getAspects();
			for (int i = 0; i < ot.size(); i++) {
				aspects.remove(in[i], ot.getAmount(in[i]));
			}
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			markDirty();
			return true;
		}
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount) {
		return aspects.getAmount(tag) >= amount;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		boolean has = true;
		Aspect[] in = ot.getAspects();
		for (int i = 0; i < ot.size() && has; i++) {
			if (aspects.getAmount(in[i]) < ot.getAmount(in[i])) {
				has = false;
			}
		}
		return has;
	}

	@Override
	public int containerContains(Aspect tag) {
		return aspects.getAmount(tag);
	}

	@Override
	public boolean isConnectable(ForgeDirection face) {
		return face != ForgeDirection.DOWN && face != ForgeDirection.UP;
	}

	@Override
	public boolean canInputFrom(ForgeDirection face) {
		return false;
	}

	@Override
	public boolean canOutputTo(ForgeDirection face) {
		//return face != ForgeDirection.DOWN && face != ForgeDirection.UP;
		return true;
	}

	@Override
	public void setSuction(Aspect aspect, int amount) {
	}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		return null;
	}

	@Override
	public int getSuctionAmount(ForgeDirection face) {
		return 0;
	}

	@Override
	public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
		return !canOutputTo(face) || !takeFromContainer(aspect, amount) ? 0 : amount;
	}

	@Override
	public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
		return 0;
	}

	@Override
	public Aspect getEssentiaType(ForgeDirection face) {
		return aspects.getAspects().length > 0 ? aspects.getAspects()[0] : null;
	}

	@Override
	public int getEssentiaAmount(ForgeDirection face) {
		return aspects.visSize();
	}

	@Override
	public int getMinimumSuction() {
		return 0;
	}

	@Override
	public boolean renderExtendedTube() {
		return false;
	}

}
