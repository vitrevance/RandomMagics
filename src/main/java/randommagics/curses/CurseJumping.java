package randommagics.curses;

import net.minecraft.entity.player.EntityPlayer;

public class CurseJumping extends Curse {
	
	public CurseJumping() {
		super("jumping");
		this.isInfinite = false;
		this.stackable = false;
		this.revokeAfterDeath = false;
		this.timeInTicks = 36000;
	}
	
	@Override
	public void doEffects(EntityPlayer player) {
		if (player.onGround) {
			player.jump();
		}
	}
}
