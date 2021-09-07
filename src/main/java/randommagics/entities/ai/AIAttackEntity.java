package randommagics.entities.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class AIAttackEntity extends EntityAIBase {
	
	private EntityCreature entity;
	private EntityLivingBase target;
	private double dist;
	
	public AIAttackEntity(EntityCreature entity, EntityLivingBase targetEntity, double distatnce) {
		this.entity = entity;
		this.target = targetEntity;
		this.dist = distatnce;
	}

	@Override
	public boolean shouldExecute() {
		if (target != null && target.isEntityAlive() && entity.getDistance(target.posX, target.posY, target.posZ) < dist) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean continueExecuting() {
		return shouldExecute();
	}
	
	@Override
	public void updateTask() {
		super.updateTask();
		entity.attackEntityAsMob(target);
	}
}
