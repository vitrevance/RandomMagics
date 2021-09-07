package randommagics.blocks;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import randommagics.Init;
import thaumcraft.api.TileThaumcraft;

public class TileLifeStealingPlant extends TileThaumcraft {
	
	public int energy = 0;
	public boolean dead = false;
	private boolean needsUpdates = true;
	private int deathTimer = 0;
	
	public TileLifeStealingPlant() {
		energy = 1;
		dead = false;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (energy == 0)
			dead = true;
		if (needsUpdates) {
			if (Math.random() * 100 < 5) {
				energy++;
			}
			ArrayList<TileLifeStealingPlant> neibs = getNeighbours();
			if (neibs.size() >= 4)
				needsUpdates = false;
			if (!neibs.isEmpty()) {
				for (TileLifeStealingPlant i : neibs) {
					if (energy > i.energy && energy > 1) {
						i.energy++;
						energy--;
					}
					if (dead) {
						i.dead = true;
					}
					i.markDirty();
					worldObj.markBlockForUpdate(i.xCoord, i.yCoord, i.zCoord);
				}
				markDirty();
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			if (worldObj.getBlock(xCoord, yCoord - 1, zCoord) == Init.BlockLifeStealingPlant) {
				TileLifeStealingPlant tile = (TileLifeStealingPlant)worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
				tile.energy += energy;
				energy = 0;
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);
				return;
			}
			if (energy >= 40) {
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						for (int k = -1; k < 2; k++) {
							if (!(i == 0 && k == 0)) {
								if (worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) == Blocks.air && worldObj.getBlock(xCoord + i, yCoord + j - 1, zCoord + k).canPlaceTorchOnTop(worldObj, xCoord + i, yCoord + j - 1, zCoord + k)) {
									worldObj.setBlock(xCoord + i, yCoord + j, zCoord + k, Init.BlockLifeStealingPlant);
									worldObj.markBlockForUpdate(xCoord + i, yCoord + j, zCoord + k);
									energy -= 30;
									markDirty();
									worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
									return;
								}
							}
						}
					}
				}
			}
		}
		if (dead) {
			deathTimer++;
			needsUpdates = true;
			if (deathTimer == 20) {
				//worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, new ItemStack(Init.PrimBlend, energy / 10)));
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
		if (!worldObj.getBlock(xCoord, yCoord - 1, zCoord).canPlaceTorchOnTop(worldObj, xCoord, yCoord - 1, zCoord)) {
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	private ArrayList<TileLifeStealingPlant> getNeighbours() {
		ArrayList<TileLifeStealingPlant> neibs = new ArrayList<TileLifeStealingPlant>();
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				for (int k = -1; k < 2; k++) {
					if (!(i == 0 && j == 0 && k == 0)) {
						if (worldObj.getTileEntity(xCoord + i, yCoord + j, zCoord + k) instanceof TileLifeStealingPlant) {
							neibs.add((TileLifeStealingPlant)worldObj.getTileEntity(xCoord + i, yCoord + j, zCoord + k));
						}
					}
				}
			}
		}
		return neibs;
	}
	
	private int getFreeSpaces() {
		int neibs = 0;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				for (int k = -1; k < 2; k++) {
					if (!(i == 0 && k == 0)) {
						if (worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) == Blocks.air) {
							neibs++;
						}
					}
				}
			}
		}
		return neibs;
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("energy", energy);
	}
	
	
	@Override
	public void readCustomNBT(NBTTagCompound nbttagcompound) {
		this.energy = nbttagcompound.getInteger("energy");
	}
	
	public void stealHealth(EntityLivingBase entity) {
		if (entity != null) {
			int power = getNeighbours().size() + 1;
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer)entity;
				if (player.capabilities.disableDamage) {
					return;
				}
			}
			if (entity.getHealth() > 0) {
				entity.setHealth(entity.getHealth() - power);
				energy += power;
			}
		}
	}
}
