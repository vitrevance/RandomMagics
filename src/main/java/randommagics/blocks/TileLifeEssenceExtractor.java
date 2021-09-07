package randommagics.blocks;

import net.minecraft.nbt.NBTTagCompound;
import randommagics.customs.ILifeContainer;
import thaumcraft.api.TileThaumcraft;

public class TileLifeEssenceExtractor extends TileThaumcraft {
	
	public int xAdd, zAdd;
	private int timeCount = 0;
	
	public TileLifeEssenceExtractor() {
		xAdd = 1;
		zAdd = 0;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		timeCount++;
		if (timeCount > 20) {
			timeCount = 0;
			if (worldObj.getTileEntity(xCoord, yCoord + 1, zCoord) instanceof ILifeContainer && worldObj.getTileEntity(xCoord + xAdd, yCoord, zCoord + zAdd) instanceof TileLifeStealingPlant) {
				ILifeContainer jar = (ILifeContainer)worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
				TileLifeStealingPlant plant = (TileLifeStealingPlant)worldObj.getTileEntity(xCoord + xAdd, yCoord, zCoord + zAdd);
				if (plant.energy > 0) {
					int take = jar.canAccept(1);
					plant.energy -= take;
				}
				plant.markDirty();
				worldObj.getTileEntity(xCoord, yCoord + 1, zCoord).markDirty();
				markDirty();
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				worldObj.markBlockForUpdate(xCoord, yCoord + 1, zCoord);
				worldObj.markBlockForUpdate(xCoord + xAdd, yCoord, zCoord + zAdd);
				
			}
		}
	}
	
	public void setSide(int side) {
		xAdd = 0;
		zAdd = 0;
		if (side == 3)
			zAdd = 1;
		if (side == 2)
			zAdd = -1;
		if (side == 5)
			xAdd = 1;
		if (side == 4)
			xAdd = -1;
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound nbt) {
		nbt.setInteger("xadd", xAdd);
		nbt.setInteger("zadd", zAdd);
		nbt.setInteger("timer", timeCount);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound nbt) {
		xAdd = nbt.getInteger("xadd");
		zAdd = nbt.getInteger("zadd");
		timeCount = nbt.getInteger("timer");
	}
}