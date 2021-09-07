package randommagics;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import randommagics.customs.CustomExtendedEntityProperties;
import randommagics.enchantments.EnchantmentsHelper;

public class EnchantmentEvents {
	
	@SubscribeEvent
	public void onAttackEntityEvent(AttackEntityEvent event) {
		if (!event.isCanceled()) {
			if (EnchantmentsHelper.hasEnchantment(event.entityPlayer.getCurrentEquippedItem(), Config.enchantmentDisintegrationId)) {
				ItemStack cur = event.entityPlayer.getCurrentEquippedItem();
				CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(event.entityPlayer);
				ex.madnessLvl += 5;
				if (cur.getTagCompound().getBoolean("honest") && (cur.isItemStackDamageable() && cur.getItemDamage() < cur.getMaxDamage() || cur.getItem() == Init.ItemSwordOfInfiniteDestruction)) {
					cur.damageItem(500, event.entityPlayer);
					if (event.target instanceof EntityPlayer) {
						((EntityPlayer)event.target).onDeath(DamageSource.outOfWorld);
						((EntityPlayer)event.target).setHealth(0);
					}
					else
					if (event.target instanceof EntityLiving) {
						((EntityLiving)event.target).prevHealth = 0;
						((EntityLiving)event.target).setHealth(0);
						((EntityLiving)event.target).setDead();
						((EntityLiving)event.target).onDeath(DamageSource.outOfWorld);
					}
				}
				else {
					event.entityPlayer.attackEntityFrom(DamageSource.magic, 50);
					event.entityPlayer.dropOneItem(true);
					event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "randommagics:spell.wind", 1, 1);
					event.setCanceled(true);
				}
				/*
				if (!cur.getTagCompound().getBoolean("honest") || Math.ceil(Math.random() * 10) == (cur.getItemDamage() % 10)) {
					if (cur.isItemStackDamageable())
						cur.setItemDamage(cur.getItemDamage() + 500 <= cur.getMaxDamage() ? cur.getItemDamage() + 500 : cur.getMaxDamage());
					//event.entityPlayer.attackEntityFrom(DamageSource.magic, 50);
					//event.entityPlayer.dropOneItem(true);
					event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "randommagics:spell.wind", 1, 1);
					//event.setCanceled(true);
					
				}
				else
				if (event.target instanceof EntityPlayer) {
					((EntityPlayer)event.target).onDeath(DamageSource.outOfWorld);
					((EntityPlayer)event.target).setHealth(0);
				}
				else
				if (event.target instanceof EntityLiving) {
					((EntityLiving)event.target).onDeath(DamageSource.outOfWorld);
					((EntityLiving)event.target).setDead();
				}
				*/
			}
		}
	}
}
