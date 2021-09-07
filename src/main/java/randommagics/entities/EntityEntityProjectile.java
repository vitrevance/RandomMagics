package randommagics.entities;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEntityProjectile extends EntityThrowable {
	
	private Entity entity;

	public EntityEntityProjectile(World p_i1776_1_) {
		super(p_i1776_1_);
	}
	
	public EntityEntityProjectile(World world, EntityLivingBase living, Entity entity) {
		super(world, living);
		this.entity = entity;
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0F;
	}

	@Override
	public void onImpact(MovingObjectPosition mop) {
		if(!worldObj.isRemote && getThrower() != null)//if !worldObj.isRemote && 
        {
        	int x = 0;
        	int y = 0;
        	int z = 0;
        	switch(mop.sideHit) {
        	case 0:
        		y = -1;
        		break;
        	case 1:
        		y = 1;
        		break;
        	case 2:
        		z = -1;
        		break;
        	case 3:
        		z = 1;
        		break;
        	case 4:
        		x = -1;
        		break;
        	case 5:
        		x = 1;
        		break;
        	}
        	if (worldObj.getBlock(mop.blockX + x, mop.blockY + y, mop.blockZ + z).getMaterial() == Material.air) {
        		if (this.entity != null) {
        			World world = this.entity.worldObj;
        			this.entity.setPosition(mop.blockX + x + 0.5, mop.blockY + y, mop.blockZ + z + 0.5);
        			this.entity.resetEntityId();
        			world.spawnEntityInWorld(entity);
        		}
        	}
            ticksExisted = 100;
            worldObj.setEntityState(this, (byte)16);
            setDead();
        }
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if (ticksExisted > 100)
			setDead();
	}
	
}
