package randommagics.entities;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import randommagics.packets.RmNetworkRegistry;

public class EntityPotionProjectile extends EntityThrowable {
	
	private PotionEffect effect;

	public EntityPotionProjectile(World world) {
		super(world);
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0F;
	}
	
	public EntityPotionProjectile(World world, EntityLivingBase entity, PotionEffect effect) {
		super(world, entity);
		this.effect = effect;
	}

	@Override
	public void onImpact(MovingObjectPosition mop) {
		if(!worldObj.isRemote && getThrower() != null)
        {
			//System.out.println("world is " + worldObj.isRemote + " and not null " + (getThrower() != null));
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(getThrower(), boundingBox.expand(2D, 2D, 2D));
            for(int i = 0; i < list.size(); i++)
            {
                try
                {
                	Entity e = (Entity)list.get(i);
                	if ((Entity)list.get(i) instanceof EntityLiving) {
                		((EntityLiving)list.get(i)).addPotionEffect(this.effect);
                	} else
                	if ((Entity)list.get(i) instanceof EntityPlayer) {
                		((EntityPlayer)list.get(i)).addPotionEffect(this.effect);
                	}
                }
                catch(Exception e) { }
            }
            
            worldObj.playSoundAtEntity(this, "random.fizz", 0.5F, 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F);
            ticksExisted = 100;
            worldObj.setEntityState(this, (byte)16);
        }
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if (ticksExisted > 60)
			setDead();
	}
	
}
