package randommagics.blocks;

import net.minecraft.nbt.NBTTagCompound;
import randommagics.customs.ILifeContainer;
import thaumcraft.api.TileThaumcraft;

public class TileEssenceContainer extends TileThaumcraft implements ILifeContainer {
	
	private int amount, capacity;
	
	public TileEssenceContainer() {
		amount = 0;
		capacity = 0;
	}
	
	public TileEssenceContainer(int capacity) {
		this.capacity = capacity;
		amount = 0;
	}
	
	public TileEssenceContainer(int cap, int am) {
		amount = am;
		capacity = cap;
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound nbt) {
		nbt.setInteger("am", amount);
		nbt.setInteger("cap", capacity);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound nbt) {
		amount = nbt.getInteger("am");
		capacity = nbt.getInteger("cap");
	}

	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public int getEssence() {
		return amount;
	}

	@Override
	public int canAccept(int amount) {
		if (this.amount + amount <= capacity) {
			this.amount += amount;
			return amount;
		}
		this.amount = capacity;
		return capacity - this.amount;
	}

	@Override
	public int drain(int amount) {
		if (amount <= this.amount) {
			this.amount -= amount;
			return amount;
		}
		int ret = this.amount;
		this.amount = 0;
		return ret;
	}
}