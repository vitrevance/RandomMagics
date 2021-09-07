package randommagics.entities.ai;

import java.util.Vector;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import randommagics.customs.RandomUtils;

public class AITeleortRapidly extends EntityAIBase {
	
	private EntityCreature entity;
	private EntityLivingBase target;
	private int timer;
	private double partialTimer;
	private boolean follower;
	private int time;
	private double dist;
	
	public AITeleortRapidly(EntityCreature entity) {
		this.entity = entity;
		this.target = null;
		this.timer = 0;
		this.partialTimer = 0D;
		this.follower = false;
		time = 0;
		this.dist = 10;
	}
	
	public AITeleortRapidly(EntityCreature entity, int timer, double part, double distance) {
		this(entity);
		this.timer = timer;
		this.partialTimer = part;
		this.dist = distance;
	}
	
	public AITeleortRapidly(EntityCreature entity, EntityLivingBase target) {
		this(entity);
		this.target = target;
		this.follower = true;
	}
	
	public AITeleortRapidly(EntityCreature entity, EntityLivingBase target, int timer, double partial, double distance) {
		this(entity, target);
		this.timer = timer;
		this.partialTimer = partial;
		this.dist = distance;
	}

	@Override
	public boolean shouldExecute() {
		if (follower) {
			return target != null && entity != null && entity.isEntityAlive() && target.isEntityAlive();
		}
		return entity != null && entity.isEntityAlive();
	}
	
	@Override
	public boolean continueExecuting() {
		return shouldExecute();
	}
	
	@Override
	public void updateTask() {
		super.updateTask();
		time--;
		if (time - Math.random() * timer * partialTimer < 0) {
			time = timer;
			if (follower) {
				double dist = Math.random() * this.dist;
				double x = -(dist / 2) + Math.random() * dist + target.posX;
				double z = -(dist / 2) + Math.random() * dist + target.posZ;
				RandomUtils.LookAt(target.posX, target.posY + 0.5D, target.posZ, entity);
				entity.setPositionAndUpdate(x, target.posY + Math.random(), z);
			}
			else {
				double dist = Math.random() * 10;
				double x = -(dist / 2) + Math.random() * dist + entity.posX;
				double z = -(dist / 2) + Math.random() * dist + entity.posZ;
				double y = RandomUtils.getWorldTopBlock(entity.worldObj, (int)x, (int)z) + 1 + Math.random();
				entity.rotationPitch = (float)(Math.random() * 360);
				entity.rotationYaw = (float)(Math.random() * 360);
				entity.setPositionAndUpdate(x, y, z);
				//System.out.println("pos " + x + " " + y + " " + z);
			}
			entity.worldObj.playSoundAtEntity(entity, "mob.endermen.portal", 1F, 1F);
		}
	}
}
