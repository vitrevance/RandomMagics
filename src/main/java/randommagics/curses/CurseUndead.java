package randommagics.curses;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import randommagics.customs.RandomUtils;

public class CurseUndead extends Curse {
	
	public CurseUndead() {
		super("undead");
		this.isInfinite = true;
		this.revokeAfterDeath = false;
		this.stackable = false;
	}
	
	@Override
	public void onApply(EntityPlayer player, EntityPlayer caster) {
		player.addPotionEffect(new PotionEffect(Potion.hunger.id, 300, 5));
	}
	
	@Override
	public void onRevoke(EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.confusion.id, 100));
	}
	
	@Override
	public void doEffects(EntityPlayer player) {
		this.timeInTicks++;
		if (this.timeInTicks % 20 == 0 && player.worldObj.isDaytime() && !player.worldObj.isRaining()) {
			World world = player.worldObj;
			for (int y = world.getActualHeight(); y > player.posY + 1; y--) {
				if (world.getBlock((int)Math.round(player.posX - 0.5), y, (int)Math.round(player.posZ - 0.5)).isOpaqueCube()) {
					return;
				}
			}
			
			player.setFire(1);
			player.attackEntityFrom(DamageSource.inFire, 1f);
		}
	}
}
