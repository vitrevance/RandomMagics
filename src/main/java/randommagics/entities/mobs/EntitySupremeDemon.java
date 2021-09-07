package randommagics.entities.mobs;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import randommagics.Init;
import randommagics.customs.DemonBossFight;
import randommagics.customs.RandomUtils;
import randommagics.entities.ai.AIAttackEntity;
import randommagics.entities.ai.AITeleortRapidly;
import thaumcraft.common.entities.ai.combat.AIAttackOnCollide;
import thaumcraft.common.entities.ai.combat.AITarget;
import thaumcraft.common.entities.ai.misc.AIWander;

public class EntitySupremeDemon extends EntityMob {
	
	private ItemStack[] inventory = new ItemStack[1];
	
	private EntityPlayer summoner;
	private String summonerName;
	
	private boolean idle = false;
	
	public EntitySupremeDemon(World world, EntityPlayer summoner) {
		this(world);
		this.summoner = summoner;
		this.summonerName = summoner.getCommandSenderName();
		setPositionAndUpdate(summoner.posX, summoner.posY + 0.5D, summoner.posZ);
		setupAI();
	}
	
	public EntitySupremeDemon(World world) {
		super(world);
		this.summoner = null;
		this.summonerName = "";
		setSize(1.2F, 3.6F);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1);
		isImmuneToFire = true;
	}

	@Override
	public ItemStack getHeldItem() {
		return null;
	}

	@Override
	public ItemStack getEquipmentInSlot(int slot) {
		return slot < inventory.length ? inventory[slot] : null;
	}

	@Override
	public void setCurrentItemOrArmor(int p_70062_1_, ItemStack p_70062_2_) {
	}

	@Override
	public ItemStack[] getLastActiveItems() {
		return inventory;
	}
	
	@Override
	public EntityLivingBase getAITarget() {
		return summoner;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		//super.writeEntityToNBT(nbt);
		nbt.setString("summoner", summonerName);
		nbt.setBoolean("idle", idle);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		//super.readEntityFromNBT(nbt);
		summonerName = nbt.getString("summoner");
		summoner = RandomUtils.getEntityPlayerByName(summonerName, this.worldObj.isRemote);
		idle = nbt.getBoolean("idle");
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		fallDistance = 0;
		if (!idle) {
			if (!worldObj.isRemote && ticksExisted % 100 == 0) {
				EntityDemon demon = new EntityDemon(worldObj, this);
				demon.setPosition(posX, posY + 1, posZ);
				worldObj.spawnEntityInWorld(demon);
			}
		}
		if (summoner == null) {
			System.out.println("update " + summonerName + " " + this.worldObj.isRemote);
			idle = true;
			if (!summonerName.isEmpty()) {
				summoner = RandomUtils.getEntityPlayerByName(summonerName, this.worldObj.isRemote);
			}
		}
		else {
			if (idle) {
				idle = false;
				setupAI();
			}
		}
		if (this.summoner != null) {
			DemonBossFight dbf = DemonBossFight.beginDemonBossFight(this.summoner);
			System.out.println("asdasdasdasdsadsadsa " + this.worldObj.isRemote);
		}
		/*
		if (ticksExisted > 1000) {
			idle = true;
			if (!worldObj.isRemote)
				setHealth(0);
		}
		*/
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		amount /= 1000;
		if (amount >= getHealth()) {
			amount /= getMaxHealth();
		}
		//amount = (float)Math.pow(amount, 0.0001D);
		return super.attackEntityFrom(source, amount);
	}
	
	@Override
	protected boolean isAIEnabled() {
		return true;
	}
	
	private void clearAITasks() {
		tasks.taskEntries.clear();
		targetTasks.taskEntries.clear();
	}
	
	protected void setupAI()
	{
	   getNavigator().setAvoidsWater(true);
	   clearAITasks(); // clear any tasks assigned in super classes
	   //tasks.addTask(0, new AIAttackOnCollide(this, EntityPlayer.class, 0.6D, false));
	   //tasks.addTask(1, new AIWander(this, 1F));
	   tasks.addTask(0, new AIAttackEntity(this, summoner, 3D));
	   tasks.addTask(1, new AITeleortRapidly(this, summoner, 240, 0.7D, 10D));
	   tasks.addTask(2, new AITeleortRapidly(this, 30, 0.3D, 30D));
	   tasks.addTask(3, new EntityAILookIdle(this));
	   tasks.addTask(4, new EntityAIWander(this, 2D));
	}
	
	@Override
	protected Item getDropItem() {
		if (idle)
			return null;
		return Init.RitualCatalyst;
	}
	
	@Override
	protected void dropFewItems(boolean hit, int lvl) {
		super.dropFewItems(hit, lvl);
		if (hit) {
			if (lvl > Math.random() * 20) {
				dropItem(Init.ItemDemonHeart, 1);
			}
		}
	}
}
