package randommagics.curses;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import randommagics.Init;
import randommagics.customs.CustomExtendedEntityProperties;

public class CurseOutOfTime extends Curse {
	
	public CurseOutOfTime() {
		super("outoftime");
		isInfinite = true;
		revokeAfterDeath = true;
		stackable = false;
	}
	
	@Override
	public void doEffects(EntityPlayer player) {
		List entities = player.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(player.posX - 10, player.posY - 10, player.posZ - 10, player.posX + 10, player.posY + 10, player.posZ + 10));
		for (Object entity : entities) {
			if (entity instanceof EntityPlayer) {
				if ((EntityPlayer)entity == player) {
					player.motionX /= 1.5;
					player.motionZ /= 1.5;
					if (player.motionY < 0) {
						player.motionY /= 1.5;
					}
					continue;
				}
				else {
					EntityPlayer entp = ((EntityPlayer)entity);
					if (!CurseRegistry.isPlayerCursed(entp, "outoftime")) {
						CustomExtendedEntityProperties enex = CustomExtendedEntityProperties.get(entp);
						if (enex.demonLevel >= 9) {
							CurseRegistry.applyOnPlayerSided(entp, entp, new CurseOutOfTime(), entp.worldObj.isRemote);
						}
						else {
							entp.addPotionEffect(new PotionEffect(Init.EffectStuckInTime.id, 5));
						}
					}
					
				}
			}
			else {
				if (entity instanceof EntityLivingBase) {
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Init.EffectStuckInTime.id, 5));
				}
				else {
					Entity ent = (Entity)entity;
					ent.motionX = 0;
					ent.motionY = 0;
					ent.motionZ = 0;
					ent.velocityChanged = true;
				}
			}
			
		}
		/*
		if (player.worldObj.isRemote) {
			if (!Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(Sound.heartbeat)) {
				Minecraft.getMinecraft().getSoundHandler().playSound(Sound.heartbeat);
			}
		}
		*/
	}
	
	@Override
	public void onRevoke(EntityPlayer player) {
		/*
		if (player.worldObj.isRemote) {
			if (Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(Sound.heartbeat)) {
				Minecraft.getMinecraft().getSoundHandler().stopSound(Sound.heartbeat);
			}
		}
		*/
	}
}
