package randommagics.curses;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import randommagics.customs.RandomUtils;

public class CurseBadLuck extends Curse {

	public CurseBadLuck() {
		super("badluck");
		this.stackable = false;
		this.level = 1;
		this.isInfinite = false;
		this.revokeAfterDeath = true;
		this.timeInTicks = 6000;
	}
	
	@Override
	public void onApply(EntityPlayer player, EntityPlayer caster) {
	}
	
	@Override
	public void onRevoke(EntityPlayer player) {
	}
	
	@Override
	public void doEffects(EntityPlayer player) {
	}
}
