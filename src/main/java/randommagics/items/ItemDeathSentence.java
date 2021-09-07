package randommagics.items;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import randommagics.Init;
import thaumcraft.api.damagesource.DamageSourceThaumcraft;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXBlockZap;
import thaumcraft.common.lib.network.fx.PacketFXInfusionSource;
import thaumcraft.common.lib.utils.EntityUtils;

public class ItemDeathSentence extends ItemSword {
	
	public ItemDeathSentence() {
		super(Init.INFINITE_STONE);
		this.setCreativeTab(Init.TabRandomMagics);
		this.setUnlocalizedName("ItemDeathSentence");
		this.setTextureName("randommagics:DeathSentence");
		this.hasSubtypes = false;
		this.setMaxDamage(0);
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		super.onUsingTick(stack, player, count);
		int range = 24;
		if (player.isSneaking()) {
			range = 64;
		}
		List list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(range, range, range));
		for (int i = 0; i < list.size(); i++) {
			Entity entity = (Entity)list.get(i);
			if(!entity.canBeCollidedWith() || !(entity instanceof EntityLivingBase)
					|| !((EntityLivingBase)entity).isEntityAlive() || !EntityUtils.isVisibleTo(0.75F, player, entity, range) 
					|| entity == null || !player.canEntityBeSeen(entity) || (entity instanceof EntityPlayer) && !MinecraftServer.getServer().isPVPEnabled()) {
				continue;
			}
			if (player.isSneaking()) {
				this.applyVoiceOfDeath((EntityLivingBase)entity, player, 120, -50, true);
			}
			else {
				this.applyVoiceOfDeath((EntityLivingBase)entity, player, 40, -5, false);
			}
			((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 80, 20));
		}
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.epic;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if (entity instanceof EntityLivingBase) {
			ArrayList<EntityLivingBase> ignore = new ArrayList<EntityLivingBase>();
			ignore.add(player);
			this.attack(stack, player, (entity instanceof EntityPlayer ? EntityPlayer.class
					: (entity instanceof EntityMob ? EntityMob.class
					: (entity instanceof EntityAnimal ? EntityAnimal.class : (EntityLivingBase.class)))), (EntityLivingBase)entity, ignore, 100, (player.isSneaking() ? 30 : 6));
			return true;
		}
		return false;
	}
	
	private void attack(ItemStack stack, EntityPlayer player, Class original, EntityLivingBase entity, List<EntityLivingBase> ignore, int remain, int expand) {
		if (remain <= 0)
			return;
		
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(entity.posX-1, entity.posY-1, entity.posZ-1, entity.posX+1, entity.posY+1, entity.posZ+1).expand(expand, expand, expand);
		List<EntityLivingBase> nearEntities = entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
		
		nearEntities.removeAll(ignore);
		for (EntityLivingBase target : nearEntities) {
			if (!original.isInstance(target)) {
				continue;
			}
			if (original == EntityPlayer.class) {
				remain = 0;
			}
			else {
				if (target instanceof EntityPlayer) {
					continue;
				}
			}
			float damage = this.getAttackDamage(target);
			target.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
			this.applyVoiceOfDeath(target, player, 100, 10, true);
			PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockZap((float)entity.posX, (float)entity.posY + 0.5f, (float)entity.posZ, (float)target.posX, (float)target.posY, (float)target.posZ), new cpw.mods.fml.common.network.NetworkRegistry.TargetPoint(target.worldObj.provider.dimensionId, target.posX, target.posY, target.posZ, (player.isSneaking() ? 256D : 32D)));
            target.worldObj.playSoundAtEntity(target, "thaumcraft:zap", 1F, 0.8F);
			
			ignore.add(target);
			attack(stack, player, original, target, ignore, remain - 1, expand);
		}
	}
	
	private void applyVoiceOfDeath(EntityLivingBase target, EntityPlayer player, int duration, int lvl, boolean killIfCantApply) {
		PotionEffect voiceOfDeath = new PotionEffect(Init.EffectVoiceOfDeath.id, duration, lvl);
		if (target.isPotionApplicable(voiceOfDeath)) {
			target.addPotionEffect(voiceOfDeath);
		}
		else {
			target.onDeath(DamageSource.causePlayerDamage(player));
		}
	}
	
	private float getAttackDamage(EntityLivingBase entity) {
		return entity.getMaxHealth() * (entity.getTotalArmorValue() + entity.getAbsorptionAmount() + 1);
	}
}
