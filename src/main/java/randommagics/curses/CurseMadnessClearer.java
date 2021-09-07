package randommagics.curses;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import randommagics.customs.CustomExtendedEntityProperties;

public class CurseMadnessClearer extends Curse {
	
	private Random random;
	private int seed;
	
	public CurseMadnessClearer() {
		super("madnessclearer");
		this.isInfinite = false;
		this.timeInTicks = 1200;
		this.revokeAfterDeath = true;
		this.stackable = false;
		
		this.seed = 1;
		this.random = null;
	}
	
	@Override
	public void onApply(EntityPlayer player, EntityPlayer caster) {
		this.seed = player.experienceTotal;
		this.random = null;
	}
	
	@Override
	public void doEffects(EntityPlayer player) {
		if (this.random == null) {
			this.random = new Random(this.seed);
		}
		if (player.ticksExisted % 20 == 0 && this.random.nextBoolean()) {
			this.seed = this.random.nextInt();
			CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(player);
			if (ex.madnessLvl > 0) {
				ex.madnessLvl--;
				player.attackEntityFrom(DamageSource.magic, this.random.nextFloat() * 1000000.f);
				player.attackEntityFrom(DamageSource.generic, this.random.nextFloat() * 500000.f);
			}
		}
	}
	
	@Override
	public void writeCustomToNBT(NBTTagCompound nbt) {
		nbt.setInteger("seed", this.seed);
	}
	
	@Override
	public void readCustomFromNBT(NBTTagCompound nbt) {
		this.seed = nbt.getInteger("seed");
		this.random = null;
	}
}
