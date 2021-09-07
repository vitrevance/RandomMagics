package randommagics.blocks;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.api.TileThaumcraft;

public class TileRitualStoneSpirit extends TileThaumcraft {
	
	private boolean active;
	
	public TileRitualStoneSpirit() {
		active = false;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (active) {
			
		}
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound nbt) {
		nbt.setBoolean("active", active);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound nbt) {
		active = nbt.getBoolean("active");
	}
}
