// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntityEldritchOrb.java

package randommagics.entities;

import java.util.List;
import java.util.Random;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;
import thaumcraft.common.CommonProxy;
import thaumcraft.common.Thaumcraft;

public class EntityPowerBlast extends EntityThrowable
{

    public EntityPowerBlast(World par1World)
    {
        super(par1World);
    }

    public EntityPowerBlast(World par1World, EntityLivingBase par2EntityLiving)
    {
        super(par1World, par2EntityLiving);
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
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(getThrower(), boundingBox.expand(2D, 2D, 2D));
            for(int i = 0; i < list.size(); i++)
            {
                try
                {
                	if((Entity)list.get(i) instanceof EntityLiving) {
                		EntityLiving entity = (EntityLiving)list.get(i);
                		entity.attackEntityFrom(DamageSource.magic, Float.MAX_VALUE);
                	} else
                	if((Entity)list.get(i) instanceof EntityPlayer) {
                		EntityPlayer entity = (EntityPlayer)list.get(i);
                		entity.attackEntityFrom(DamageSource.magic, Float.MAX_VALUE);
                	}
                	/*
                	if((Entity)list.get(i) instanceof EntityLiving) {
                		EntityLiving entity = (EntityLiving)list.get(i);
                		entity.onDeath(DamageSource.outOfWorld);
                		entity.setDead();
                	}*/
                }
                catch(Exception e) { }
            }
            //worldObj.playSoundAtEntity(this, "random.fizz", 0.5F, 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F);
            worldObj.createExplosion(this, mop.blockX, mop.blockY, mop.blockZ, 2, false);
            ticksExisted = 100;
            worldObj.setEntityState(this, (byte)16);
        }
    }

    public float getShadowSize()
    {
        return 0.1F;
    }
}
