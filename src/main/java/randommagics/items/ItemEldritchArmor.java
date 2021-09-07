package randommagics.items;

import java.awt.image.RasterOp;
import java.util.UUID;

import org.lwjgl.Sys;

import com.typesafe.config.ConfigParseOptions;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.shader.ShaderDefault;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import randommagics.Init;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.armor.Hover;

public class ItemEldritchArmor extends ItemArmor implements IRevealer, IGoggles {
	
	public final static UUID HEALTHMOD_UUID = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"); //UUID.randomUUID();//"38400000-8cf0-11bd-b23e-10b96e4ef00d"
	public final static UUID SPEEDMOD_UUID = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00f");
	
	public static final AttributeModifier healthmod = new AttributeModifier(HEALTHMOD_UUID, "healthboost", 3, 1).setSaved(false);
	public static final AttributeModifier speedmod = new AttributeModifier(SPEEDMOD_UUID, "speedboost", 50, 2).setSaved(false);
	
	public ItemEldritchArmor(String unlocalizedName, ArmorMaterial material, String textureName, int type) {
		super(material, 0, type);
		//this.textureName = textureName;
	    this.setUnlocalizedName(unlocalizedName);
	    this.setCreativeTab(Init.TabRandomMagics);
	    //this.setTextureName("randommagics:" + unlocalizedName);
	    this.setTextureName("randommagics:" + textureName);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
	    return "randommagics:textures/armor/EldArmor_" + (this.armorType == 2 ? "2" : "1") + ".png";
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		
		ItemStack head = player.getCurrentArmor(3);
        ItemStack body = player.getCurrentArmor(2);
        ItemStack legs = player.getCurrentArmor(1);
        ItemStack feet = player.getCurrentArmor(0);
        
        IAttributeInstance playerAhealth = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        IAttributeInstance playerAspeed = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        
        if((head != null && body != null && legs != null && feet != null) &&
        		(head.getItem() == Init.EldHelmet && body.getItem() == Init.EldChestplate && legs.getItem() == Init.EldLeggins && feet.getItem() == Init.EldBoots)) {
        	/*
        	if (world.isRemote) {
        		System.out.println("client " + feet.hasTagCompound());
        	}
        	else {
        		System.out.println("server " + feet.hasTagCompound());
        	}*/
        	
        	if(playerAhealth.getModifier(HEALTHMOD_UUID) == null) {
        		playerAhealth.applyModifier(healthmod);
        	}
        	
        	if(!player.capabilities.isCreativeMode && !player.capabilities.allowFlying) {
        		player.capabilities.allowFlying = true;
            	player.sendPlayerAbilities();
        	}
        	
        	if (!world.isRemote) {
        		//player.addPotionEffect(new PotionEffect(Potion.resistance.id, 25, 3, true));
        		player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 25, 100, true));
        		player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 25, 1, true));
        	}
        	
        	if(player.getCurrentArmor(0).hasTagCompound()) {
        		NBTTagCompound tag = player.getCurrentArmor(0).stackTagCompound;
        		if(tag.getBoolean("nightvision") == true && player.ticksExisted % 40 == 0) {
        			if (!world.isRemote)
        				player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 300, 0, true));
        		}
        		if (tag.getBoolean("speedboost") == true) {
        			if (playerAspeed.getModifier(SPEEDMOD_UUID) == null) {
        				playerAspeed.applyModifier(speedmod);
        			}
        		}
        		else {
        			if (playerAspeed.getModifier(SPEEDMOD_UUID) != null) {
        				playerAspeed.removeModifier(speedmod);
        			}
        		}
        		if (tag.getBoolean("autostep") == true) {
        			if(!player.capabilities.isFlying && player.moveForward > 0.0F)
        	        {
        	            if(player.worldObj.isRemote && !player.isSneaking())
        	            {
        	                if(!Thaumcraft.instance.entityEventHandler.prevStep.containsKey(Integer.valueOf(player.getEntityId())))
        	                    Thaumcraft.instance.entityEventHandler.prevStep.put(Integer.valueOf(player.getEntityId()), Float.valueOf(player.stepHeight));
        	                player.stepHeight = 1.0F;
        	            }
        	        }
        	        if(player.fallDistance > 0.0F)
        	            player.fallDistance -= 0.25F;
        		}
        	}
        }
        else 
        if(playerAhealth.getModifier(HEALTHMOD_UUID) != null) {
        	playerAhealth.removeModifier(healthmod);
        	if (playerAspeed.getModifier(SPEEDMOD_UUID) != null)
        		playerAspeed.removeModifier(speedmod);
        	if(!player.capabilities.isCreativeMode) {
        		player.capabilities.allowFlying = false;
        		player.capabilities.isFlying = false;
            	player.sendPlayerAbilities();
        	}
        }
        if (this.getDamage(itemStack) > 0) {
        	int dif = this.getDamage(itemStack);
        	int rep = 0;
        	if (dif / 1000 >= 1)
        		rep = 1000;
        	if ((dif - rep) / 100 >= 1)
        		rep += 100;
        	if ((dif - rep) / 10 >= 1)
        		rep += 10;
        	if (dif - rep >= 1)
        		rep += 1;
        	this.setDamage(itemStack, this.getDamage(itemStack) - rep);
        }
	}

	@Override
	public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
	
		return true;
	}

	@Override
	public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player) {
		
		return true;
	}
	
	public EnumRarity getRarity(ItemStack itemstuck) {
		
		return EnumRarity.epic;
	}

}