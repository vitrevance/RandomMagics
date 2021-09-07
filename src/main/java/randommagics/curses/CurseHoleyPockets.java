package randommagics.curses;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class CurseHoleyPockets extends Curse {
	
	private int seed;
	
	public CurseHoleyPockets() {
		super("holeypockets");
		this.isInfinite = true;
		this.revokeAfterDeath = false;
		this.stackable = true;
		
		this.seed = 13578;
	}
	
	@Override
	public void onApply(EntityPlayer player, EntityPlayer caster) {
		
	}
	
	@Override
	public void onRevoke(EntityPlayer player) {
		
	}
	
	@Override
	public void doEffects(EntityPlayer player) {
		if (this.timeInTicks < 10) {
			Random r = new Random(this.seed);
			for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
				if (r.nextBoolean() && r.nextBoolean() && player.inventory.getStackInSlot(i) != null) {
					if (!player.worldObj.isRemote)
						player.entityDropItem(player.inventory.getStackInSlot(i).copy(), 0);
					player.inventory.setInventorySlotContents(i, null);
				}
			}
			this.timeInTicks = 10;
		}
		
		Random r = new Random(this.seed);
		this.seed = r.nextInt();
		if (r.nextFloat() < 0.001 * this.Lvl()) {
			int i = r.nextInt(player.inventory.getSizeInventory() - 1);
			boolean loop = false;
			while (player.inventory.getStackInSlot(i) == null) {
				if (i < player.inventory.getSizeInventory() - 1) {
					i++;
				}
				else {
					if (loop)
						return;
					loop = true;
					i = 0;
				}
			}
			if (!player.worldObj.isRemote) {
				player.entityDropItem(player.inventory.getStackInSlot(i).copy(), 0).delayBeforeCanPickup = 60;
			}
			player.inventory.setInventorySlotContents(i, null);
		}
	}
	
	@Override
	public void writeCustomToNBT(NBTTagCompound nbt) {
		nbt.setInteger("seed", this.seed);
	}
	
	@Override
	public void readCustomFromNBT(NBTTagCompound nbt) {
		this.seed = nbt.getInteger("seed");
	}
}
