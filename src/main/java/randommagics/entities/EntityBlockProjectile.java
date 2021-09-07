package randommagics.entities;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import randommagics.Init;

public class EntityBlockProjectile extends EntityThrowable {
	
	private Block block;
	
	public EntityBlockProjectile(World par1World)
    {
        super(par1World);
    }

    public EntityBlockProjectile(World par1World, EntityLivingBase par2EntityLiving, Block block)
    {
        super(par1World, par2EntityLiving);
        this.block = block;
    }

    protected float getGravityVelocity()
    {
        return 0.0F;
    }

    public void onUpdate()
    {
        super.onUpdate();
        if(ticksExisted > 100)
            setDead();
    }

    public void handleHealthUpdate(byte b)
    {
        super.handleHealthUpdate(b);
    }

    protected void onImpact(MovingObjectPosition mop)
    {
        if(!worldObj.isRemote && getThrower() != null)
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
        		z = 1;
        		break;
        	case 3:
        		z = -1;
        		break;
        	case 4:
        		x = 1;
        		break;
        	case 5:
        		x = -1;
        		break;
        	}
        	if (worldObj.getBlock(mop.blockX + x, mop.blockY + y, mop.blockZ + z).getMaterial() == Material.air)
        	worldObj.setBlock((int)mop.blockX + x, mop.blockY + y, mop.blockZ + z, this.block);
            ticksExisted = 100;
            worldObj.setEntityState(this, (byte)16);
        }
    }

    public float getShadowSize()
    {
        return 0.1F;
    }

}
