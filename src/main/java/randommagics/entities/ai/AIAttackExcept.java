package randommagics.entities.ai;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

public class AIAttackExcept extends EntityAIBase {
	
	private Class[] exclass = null;
	private EntityLivingBase[] exentity = null;
	private String[] exnames = null;
	private EntityCreature entity;
	private double dist;
	
	public AIAttackExcept(EntityCreature entity, double distance, Class...classes) {
		this.entity = entity;
		this.dist = distance;
		this.exclass = classes;
	}
	
	public AIAttackExcept(EntityCreature entity, double distance, EntityLivingBase...livings) {
		this.entity = entity;
		this.dist = distance;
		this.exentity = livings;
	}
	
	public AIAttackExcept(EntityCreature entity, double distance, String...names) {
		this.entity = entity;
		this.dist = distance;
		this.exnames = names;
	}

	@Override
	public boolean shouldExecute() {
		return entity != null;
	}
	
	@Override
	public boolean continueExecuting() {
		return shouldExecute();
	}
	
	@Override
	public void updateTask() {
		super.updateTask();
		List list = entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(entity.posX - dist, entity.posY, entity.posZ - dist, entity.posX + dist, entity.posY + dist, entity.posZ + dist));
		for (Object obj : list) {
			EntityLivingBase e = (EntityLivingBase)obj;
			if (e == entity)
				continue;
			boolean attack = true;
			if (exclass != null) {
				for (Class c : exclass) {
					if (c.isInstance(e)) {
						attack = false;
						break;
					}
				}
			}
			if (attack && exentity != null) {
				for (EntityLivingBase ent : exentity) {
					if (e == ent) {
						attack = false;
						break;
					}
				}
			}
			if (attack && exnames != null && e instanceof EntityPlayer) {
				for (String s : exnames) {
					if (e.getCommandSenderName().contentEquals(s)) {
						attack = false;
						break;
					}
				}
			}
			if (attack) {
				entity.attackEntityAsMob(e);
			}
		}
	}
}