package randommagics.blocks;

import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.api.TileThaumcraft;

public class TileCloud extends TileThaumcraft {
	
	public int timer;
	
	public TileCloud() {
		timer = 80;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		timer--;
		if (timer <= 0) {
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound nbttagcompound) {
		if (nbttagcompound != null)
			nbttagcompound.setInteger("time", timer);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound nbttagcompound) {
		if (nbttagcompound != null)
			timer = nbttagcompound.getInteger("time");
	}
	
}
