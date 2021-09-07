package randommagics.entities.mobs;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector2f;

import org.lwjgl.BufferUtils;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import randommagics.Init;
import randommagics.customs.RandomUtils;
import randommagics.entities.ai.AIAttackEntity;
import randommagics.entities.ai.AIAttackExcept;
import randommagics.entities.ai.AITeleortRapidly;

public class EntityDemon extends EntityMob implements IEntityAdditionalSpawnData {

	private EntityLivingBase summoner;
	private String summonerName;
	private boolean summoned = false;
	private boolean idle = false;

	public EntityDemon(World world) {
		super(world);
		this.summoner = null;
		this.summonerName = "";
		this.summoned = false;
		this.idle = false;
		//forceSpawn = true;
		setSize(0.6F, 1.8F);
		isImmuneToFire = true;
		setupAI();
	}
	
	public EntityDemon(World world, EntityPlayer player) {
		this(world);
		this.summoner = player;
		this.summonerName = player.getCommandSenderName();
		this.summoned = true;
		setupAI();
	}
	
	public EntityDemon(World world, EntitySupremeDemon summoner) {
		this(world);
		this.summoner = summoner;
		this.summoned = true;
		setupAI();
	}
	
	@Override
	protected Item getDropItem() {
		if (summoned)
			return null;
		return Init.PrimIngot;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		if (summoner != null && !summonerName.isEmpty())
			nbt.setString("summoner", summonerName);
		nbt.setBoolean("idle", idle);
		nbt.setBoolean("summoned", this.summoned);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		summonerName = nbt.getString("summoner");
		summoner = RandomUtils.getServerEntityPlayerByName(summonerName);
		idle = nbt.getBoolean("idle");
		this.summoned = nbt.getBoolean("summoned");
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		fallDistance = 0;
		if (summoned && summoner == null) {
			idle = true;
			if (!summonerName.isEmpty()) {
				summoner = RandomUtils.getServerEntityPlayerByName(summonerName);
			}
		}
		else {
			if (idle) {
				idle = false;
				setupAI();
			}
		}
		if (ticksExisted > 1000) {
			idle = true;
			if (!worldObj.isRemote)
				setHealth(0);
		}
		
		//System.out.println("prev " + this.rotationYaw + "   " + RandomUtils.remainder(this.rotationYaw, 360));
		/*
		if (this.getAITarget() != null) {
			Vector2d look = new Vector2d(this.posX, this.posZ);
			//look.sub(new Vector2d(this.summoner.posX, this.summoner.posZ));
			look.sub(new Vector2d(this.getAITarget().posX, this.getAITarget().posZ));
			look.normalize();
			this.rotationYaw = ((float)(look.angle(new Vector2d(1, 0)) * (look.dot(new Vector2d(0, -1)) > 0 ? 1 : -1) / Math.PI) + 1) * 180f;
			System.out.println("ai  " + this.rotationYaw);
		} else if (this.getAttackTarget() != null) {
			Vector2d look = new Vector2d(this.posX, this.posZ);
			//look.sub(new Vector2d(this.summoner.posX, this.summoner.posZ));
			look.sub(new Vector2d(this.getAttackTarget().posX, this.getAttackTarget().posZ));
			look.normalize();
			this.rotationYaw = ((float)(look.angle(new Vector2d(1, 0)) * (look.dot(new Vector2d(0, -1)) > 0 ? 1 : -1) / Math.PI) + 1) * 180f;
			System.out.println("attack " + this.rotationYaw);
		}
		*/
		
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		amount /= 2;
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
	
	protected void setupAI() {
		getNavigator().setAvoidsWater(true);
		clearAITasks(); // clear any tasks assigned in super classes
		//tasks.addTask(0, new AIAttackOnCollide(this, EntityPlayer.class, 0.6D, false));
		//tasks.addTask(1, new AIWander(this, 1F));
		int taskid = 0;
		//tasks.addTask(taskid++, new AIAttackExcept(this, 2D, EntityDemon.class));
		//tasks.addTask(taskid++, new EntityAILookIdle(this));
		//tasks.addTask(taskid++, new EntityAIWander(this, 0.8D));
		tasks.addTask(taskid++, new EntityAIAttackOnCollide(this, 1.0D, false));
		//tasks.addTask(taskid++, new EntityAIMoveTowardsTarget(this, 1D, 15F));
		//tasks.addTask(taskid++, new EntityAIWatchClosest(this, EntityLivingBase.class, 8.0F));
		this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 10, true));
		//this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeBoolean(this.summoned);
		if (this.summoned && this.summonerName != null)
			ByteBufUtils.writeUTF8String(buffer, this.summonerName);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.summoned = additionalData.readBoolean();
		if (this.summoned)
			this.summonerName = ByteBufUtils.readUTF8String(additionalData);
	}
}
