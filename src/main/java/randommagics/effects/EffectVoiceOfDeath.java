package randommagics.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;

public class EffectVoiceOfDeath extends Potion {
	
	public EffectVoiceOfDeath(int id) {
		super(id, false, 14846);
		this.setPotionName("potion.voiceofdeath");
	}
	
	@Override
	public void performEffect(EntityLivingBase target, int lvl) {
		if (lvl > 0) {
			target.attackEntityFrom(DamageSource.magic, lvl);
		}
		else {
			target.attackEntityFrom(DamageSource.magic, -lvl);
		}
	}
	
	@Override
	public boolean isReady(int p_76397_1_, int p_76397_2_) {
		return true;
	}
	
	@Override
	public void removeAttributesModifiersFromEntity(EntityLivingBase entity, BaseAttributeMap p_111187_2_,
			int lvl) {
		if (lvl > 0 && entity != null && ((entity.getActivePotionEffect(this) != null && entity.getActivePotionEffect(this).getDuration() > 5 && entity.isEntityAlive()) || (entity.isEntityAlive() && entity.getHealth() < entity.getMaxHealth()))) {
			entity.onDeath(DamageSource.outOfWorld);
			entity.setDead();
		}
	}
}
