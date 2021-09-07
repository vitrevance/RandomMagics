package randommagics.effects;

import java.util.UUID;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public class EffectStuckInTime extends Potion {
	
	private static UUID speedModifierUUID = UUID.randomUUID();
	private static UUID damageModifierUUID = UUID.randomUUID();
	private AttributeModifier speedmod = new AttributeModifier(speedModifierUUID, "speedmod", Double.NEGATIVE_INFINITY, 0);
	private AttributeModifier damagemod = new AttributeModifier(damageModifierUUID, "damagemod", Double.NEGATIVE_INFINITY, 1);

	public EffectStuckInTime(int id, boolean isBad, int i) {
		super(id, isBad, i);
		setPotionName("potion.stuckintime");
	}
	
	@Override
	public void performEffect(EntityLivingBase entity, int par2) {
	}
	
	@Override
	public boolean isBadEffect() {
		return true;
	}
	
	@Override
	public boolean isReady(int p_76397_1_, int p_76397_2_) {
		return false;
	}
	/*
	@Override
	public void applyAttributesModifiersToEntity(EntityLivingBase entity, BaseAttributeMap map,
			int p_111185_3_) {
		if (!(entity instanceof EntityPlayer)) {
			IAttributeInstance speed = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			speed.applyModifier(speedmod);
			IAttributeInstance damage = entity.getEntityAttribute(SharedMonsterAttributes.attackDamage);
			damage.applyModifier(damagemod);
		}
		//speed.setBaseValue(0D);
	}
	*/
	/*
	@Override
	public void removeAttributesModifiersFromEntity(EntityLivingBase entity, BaseAttributeMap map,
			int p_111187_3_) {
		IAttributeInstance speed = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
		if (speed != null && speed.getModifier(speedModifierUUID) != null) {
			speed.removeModifier(speedmod);
		}
		IAttributeInstance damage = entity.getEntityAttribute(SharedMonsterAttributes.attackDamage);
		if (damage != null && damage.getModifier(damageModifierUUID) != null) {
			damage.removeModifier(damagemod);
		}
	}
	 */
}
