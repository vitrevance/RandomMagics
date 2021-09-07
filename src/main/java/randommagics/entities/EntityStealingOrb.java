// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntityEldritchOrb.java

package randommagics.entities;

import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;
import randommagics.render.BlockDeviceRenderer;
import thaumcraft.common.CommonProxy;
import thaumcraft.common.Thaumcraft;

public class EntityStealingOrb extends EntityThrowable
{

    public EntityStealingOrb(World par1World)
    {
        super(par1World);
    }

    public EntityStealingOrb(World par1World, EntityLivingBase par2EntityLiving)
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
        if(b == 16)
        {
            if(worldObj.isRemote)
            {
                for(int a = 0; a < 30; a++)
                {
                    float fx = (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.3F;
                    float fy = (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.3F;
                    float fz = (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.3F;
                    Thaumcraft.proxy.wispFX3(worldObj, posX + (double)fx, posY + (double)fy, posZ + (double)fz, posX + (double)(fx * 8F), posY + (double)(fy * 8F), posZ + (double)(fz * 8F), 0.3F, 5, true, 0.02F);
                }

            }
        } else
        {
            super.handleHealthUpdate(b);
        }
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
                		if(entity.getHeldItem() != null) {
                        	getThrower().worldObj.spawnEntityInWorld(new EntityItem(worldObj, getThrower().posX, getThrower().posY, getThrower().posZ, entity.getHeldItem()));
                        	entity.setCurrentItemOrArmor(0, null);
                        }
                	} else
                	if((Entity)list.get(i) instanceof EntityPlayer) {
                		EntityPlayer entity = (EntityPlayer)list.get(i);
                		if(entity.getHeldItem() != null) {
                        	getThrower().worldObj.spawnEntityInWorld(new EntityItem(worldObj, getThrower().posX, getThrower().posY, getThrower().posZ, entity.getHeldItem()));
                        	entity.setCurrentItemOrArmor(0, null);
                        }
                	}
                    
                }
                catch(Exception e) { }
            }

            worldObj.playSoundAtEntity(this, "random.fizz", 0.5F, 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F);
            ticksExisted = 100;
            worldObj.setEntityState(this, (byte)16);
        }
    }

    public float getShadowSize()
    {
        return 0.1F;
    }
}
