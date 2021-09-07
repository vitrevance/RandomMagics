package randommagics.blocks;

import java.util.List;
import java.util.UUID;

import javax.swing.text.html.parser.Entity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import thaumcraft.api.TileThaumcraft;

public class TileInventoryAccess extends TileThaumcraft implements IInventory {
	
	public int slotid;
	public EntityPlayer player = null;
	public UUID playerID = null;
	
	public TileInventoryAccess() {
		slotid = 0;
		player = null;
		playerID = null;
		//player = Minecraft.getMinecraft().thePlayer;
		if (playerID != null)
			player = worldObj.func_152378_a(playerID);
		markDirty();
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (player == null && playerID != null) {
			player = worldObj.func_152378_a(playerID);
		}
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound nbttagcompound) {
		if (playerID != null) {
			nbttagcompound.setString("uuid", playerID.toString());
		}
		else if (player != null) {
			playerID = player.getUniqueID();
			nbttagcompound.setString("uuid", playerID.toString());
		}
		nbttagcompound.setInteger("slotid", slotid);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound nbttagcompound) {
		if (nbttagcompound.getString("uuid") != "") {
			playerID = UUID.fromString(nbttagcompound.getString("uuid"));
		}
		slotid = nbttagcompound.getInteger("slotid");
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (player == null)
			return null;
		return player.inventory.getStackInSlot(slotid);
	}

	@Override
	public ItemStack decrStackSize(int slot, int num) {
		if (player == null)
			return null;
		return player.inventory.decrStackSize(slotid, num);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (player != null)
			player.inventory.setInventorySlotContents(slotid, stack);
	}

	@Override
	public String getInventoryName() {
		return "Player Inventory";
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
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (player == null)
			return false;
		return player.inventory.isItemValidForSlot(slotid, stack);
		//return true;
	}
	
	public AxisAlignedBB getRenderBoundingBox()
    {
        return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 1, zCoord - 1, xCoord + 1, yCoord + 1, zCoord + 1);
    }

}
