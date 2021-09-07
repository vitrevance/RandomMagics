package randommagics.curses;

import java.util.UUID;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import randommagics.customs.CustomExtendedEntityProperties;
import thaumcraft.common.lib.potions.PotionSunScorned;

public class CurseGhostForm extends Curse {
	
	private boolean prevNoClip;
	private boolean prevFly;
	private boolean prevDam;
	private int hpboost;
	private UUID modId = UUID.randomUUID();
	private int timer = 80;
	
	public CurseGhostForm() {
		super("ghostmode");
		isInfinite = true;
		revokeAfterDeath = true;
		hpboost = 0;
	}
	
	@Override
	public void doEffects(EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.invisibility.id, 20));
		player.capabilities.isFlying = true;
		player.capabilities.disableDamage = true;
		player.capabilities.allowFlying = true;
		player.noClip = true;
		player.sendPlayerAbilities();
		timer--;
		if (timer <= 0 && !player.worldObj.isRemote) {
			timer = 80;
			player.setHealth(player.getHealth() - 2);
			hpboost -= 2;
			AttributeModifier hpmod = new AttributeModifier(modId, "curseghostmodehealth", hpboost, 0).setSaved(false);
			if (player.getEntityAttribute(SharedMonsterAttributes.maxHealth).getModifier(modId) != null) {
				player.getEntityAttribute(SharedMonsterAttributes.maxHealth).removeModifier(hpmod);
			}
			if (player.getEntityAttribute(SharedMonsterAttributes.maxHealth).getModifier(modId) == null) {
				player.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(hpmod);
			}
			
			if (player.getHealth() <= 0) {
				CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(player);
				ex.revokeCurse(this);
				player.setDead();
				player.onDeath(DamageSource.outOfWorld);
			}
		}
		/*
		if (player.ticksExisted % 80 == 0 && !player.worldObj.isRemote) {
			player.setHealth(player.getHealth() - 2);
			hpboost--;
			AttributeModifier hpmod = new AttributeModifier(modId, "curseghostmodehealth", hpboost, 0).setSaved(false);
			if (player.getEntityAttribute(SharedMonsterAttributes.maxHealth).getModifier(modId) != null) {
				player.getEntityAttribute(SharedMonsterAttributes.maxHealth).removeModifier(hpmod);
				System.out.println("removed attribute");
			}
			player.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(hpmod);
			
			AttributeModifier hpmod = new AttributeModifier(modId, "curseghostmodehealth", hpboost, 0).setSaved(false);
			player.getEntityAttribute(SharedMonsterAttributes.maxHealth).removeModifier(hpmod);
			hpboost--;
			AttributeModifier hpmod2 = new AttributeModifier(modId, "curseghostmodehealth", hpboost, 0).setSaved(false);
			player.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(hpmod2);
		}
		*/
	}
	
	@Override
	public void onApply(EntityPlayer player, EntityPlayer caster) {
		prevNoClip = player.noClip;
		player.noClip = true;
		prevFly = player.capabilities.allowFlying;
		player.capabilities.allowFlying = true;
		player.capabilities.isFlying = true;
		prevDam = player.capabilities.disableDamage;
		player.capabilities.disableDamage = true;
		player.sendPlayerAbilities();
	}
	
	@Override
	public void onRevoke(EntityPlayer player) {
		player.noClip = prevNoClip;
		player.capabilities.allowFlying = prevFly;
		player.capabilities.isFlying = prevFly;
		player.capabilities.disableDamage = prevDam;
		player.sendPlayerAbilities();
		AttributeModifier hpmod = new AttributeModifier(modId, "curseghostmodehealth", hpboost, 0).setSaved(false);
		player.getEntityAttribute(SharedMonsterAttributes.maxHealth).removeModifier(hpmod);
	}
	
	@Override
	public void writeCustomToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("clip", prevNoClip);
		nbt.setBoolean("fly", prevFly);
		nbt.setBoolean("damage", prevDam);
	}
	
	@Override
	public void readCustomFromNBT(NBTTagCompound nbt) {
		prevNoClip = nbt.getBoolean("clip");
		prevFly = nbt.getBoolean("fly");
		prevDam = nbt.getBoolean("damage");
	}
}
