package randommagics.curses;

import net.minecraft.entity.player.EntityPlayer;
import scala.util.Random;

public class CurseAnnoyedSteve extends Curse {
	
	public CurseAnnoyedSteve() {
		super("annoyedsteve");
		this.isInfinite = true;
		this.stackable = false;
		this.revokeAfterDeath = false;
	}
	
	@Override
	public void doEffects(EntityPlayer player) {
		if (player.worldObj.isRemote)
			return;
		this.timeInTicks--;
		if (this.timeInTicks < 0 || !player.isSwingInProgress)
			this.timeInTicks = 0;
		if (player.isSwingInProgress && this.timeInTicks <= 0) {
			Random rand = new Random();
			int r = rand.nextInt(4);
			if (r == 0)
				player.worldObj.playSoundAtEntity(player, "mob.villager.haggle", 10f, 0.6f + rand.nextFloat() / 5f);
			if (r == 1)
				player.worldObj.playSoundAtEntity(player, "mob.villager.idle", 10f, 0.6f + rand.nextFloat() / 5f);
			if (r == 2)
				player.worldObj.playSoundAtEntity(player, "mob.villager.no", 10f, 0.6f + rand.nextFloat() / 5f);
			if (r == 3)
				player.worldObj.playSoundAtEntity(player, "mob.villager.yes", 10f, 0.6f + rand.nextFloat() / 5f);
			this.timeInTicks = 20;
		}
	}
}