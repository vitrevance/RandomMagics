package randommagics.blocks;

import java.math.BigInteger;

import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.api.TileThaumcraft;
import vazkii.botania.api.mana.IManaPool;

public class TileManaPool extends TileThaumcraft implements IManaPool {
	
	private BigInteger mana;
	
	public TileManaPool() {
		this.mana = new BigInteger("0");
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public void recieveMana(int mana) {
		this.mana = this.mana.add(BigInteger.valueOf(mana));
		if (this.mana.compareTo(BigInteger.valueOf(0)) < 0) {
			this.mana = BigInteger.valueOf(0);
		}
	}

	@Override
	public boolean canRecieveManaFromBursts() {
		return true;
	}

	@Override
	public int getCurrentMana() {
		if (this.mana.compareTo(new BigInteger("1000000000")) > 0) {
			return 1000000000;
		}
		return this.mana.intValue();
	}

	@Override
	public boolean isOutputtingPower() {
		return false;
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setString("mana", this.mana.toString());
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound nbttagcompound) {
		this.mana = new BigInteger(nbttagcompound.getString("mana"));
	}
}