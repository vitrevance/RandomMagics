package randommagics.blocks;

import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.api.TileThaumcraft;

public class TileRitualPylon extends TileThaumcraft {
	
	public float rotation;
	public boolean active;
	
	public TileRitualPylon() {
		this.rotation = 0f;
		this.active = false;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		this.rotation += 0.2f;
		if (this.active)
			worldObj.spawnParticle("flame", xCoord + worldObj.rand.nextDouble(), yCoord + worldObj.rand.nextDouble(), zCoord + worldObj.rand.nextDouble(), 0, worldObj.rand.nextDouble() / 3d, 0);
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setFloat("rotation", this.rotation);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound nbttagcompound) {
		this.rotation = nbttagcompound.getFloat("rotation");
	}
}