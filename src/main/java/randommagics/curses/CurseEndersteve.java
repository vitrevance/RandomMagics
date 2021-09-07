package randommagics.curses;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import randommagics.customs.RandomUtils;

public class CurseEndersteve extends Curse {
	
	private int seed;
	private int cooldown;

	public CurseEndersteve() {
		super("endersteve");
		this.isInfinite = false;
		this.stackable = true;
		this.timeInTicks = 1200;
		this.revokeAfterDeath = true;
		
		this.seed = 13558;
		this.cooldown = 0;
	}
	
	@Override
	public void doEffects(EntityPlayer player) {
		this.cooldown--;
		if (this.cooldown < 0) {
			this.cooldown = 0;
		}
		if (!player.worldObj.isRemote && player.isInWater() || (player.worldObj.isRaining() && RandomUtils.isUnderOpenSky(player.worldObj, (int)Math.round(player.posX), (int)Math.round(player.posY), (int)Math.round(player.posZ)))) {
			if (this.timeInTicks % 10 == 0) {
				player.attackEntityFrom(DamageSource.inWall, this.level);
			}
			player.addPotionEffect(new PotionEffect(Potion.blindness.id, 40, 5));
			if (this.cooldown <= 0) {
				this.cooldown = 30;
				player.worldObj.playSoundAtEntity(player, "mob.endermen.portal", 1f, 0.6f);
				Random rand = new Random(this.seed);
				double posx = player.posX + rand.nextDouble() * 20d - 10d;
				double posz = player.posZ + rand.nextDouble() * 20d - 10d;
				player.setPositionAndUpdate(posx, RandomUtils.getWorldTopBlock(player.worldObj, (int)posx, (int)posz) + 1, posz);
				player.worldObj.playSoundAtEntity(player, "mob.endermen.portal", 2f, 0.6f);
				this.seed = rand.nextInt();
			}
		}
		else {
			this.timeInTicks++;
		}
	}

}
