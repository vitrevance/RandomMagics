package randommagics.curses;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

public class CurseShortLife extends Curse {
	
	public CurseShortLife() {
		super("shortlife");
		isInfinite = true;
		revokeAfterDeath = false;
		timeInTicks = 500;
		stackable = false;
	}
	
	@Override
	public void doEffects(EntityPlayer player) {
		timeInTicks--;
		if (timeInTicks <= 0) {
			timeInTicks = 500;
			player.setHealth(0);
		}
	}
	
	@Override
	public void onApply(EntityPlayer player, EntityPlayer caster) {
		player.attackEntityFrom(DamageSource.magic, 1);
	}
	
	@Override
	public void onRevoke(EntityPlayer player) {
		player.motionY = 2;
	}
	
	@Override
	public void writeCustomToNBT(NBTTagCompound nbt) {
		
	}
	
	@Override
	public void readCustomFromNBT(NBTTagCompound nbt) {
		
	}
}
