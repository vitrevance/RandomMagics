package randommagics.entities;

import java.util.HashSet;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import randommagics.customs.BigExplosion;

public class EntityBigExplosion extends Entity {
	
	public static final HashSet<EntityBigExplosion> explosionsSet = new HashSet<EntityBigExplosion>();
	
	private BigExplosion explosion;
	public float size = 0;
	
	public EntityBigExplosion(World world) {
		super(world);
		this.explosion = null;
		//this.setPosition(0, 0, 0);
		this.setSize(0.2f, 0.2f);
		this.ignoreFrustumCheck = true;
	}
	
	public EntityBigExplosion(World world, BigExplosion expl) {
		super(world);
		this.explosion = expl;
		this.setPosition(expl.explosionX, expl.explosionY, expl.explosionZ);
		this.setSize(0.2f, 0.2f);
		this.ignoreFrustumCheck = true;
	}
	
	@Override
	public void onUpdate() {
		//System.out.println("test");
		if (this.explosion == null)
			return;
		if (!this.worldObj.isRemote && this.explosion.finished) {
			this.setDead();
			return;
		}
		if (this.worldObj.isRemote) {
			this.explosion.doExplosionClient();
		}
		else {
			//this.explosion.doExplosionClient();
			this.explosion.doExplosionServer();
		}
		//System.out.println(this.explosion.lastLvl + " " + this.explosion.finished);
		if (this.size != this.explosion.lastLvl) {
			this.size = this.explosion.lastLvl;
			this.worldObj.playSoundAtEntity(this, "random.explode", this.explosion.radius, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		}
	}

	@Override
	protected void entityInit() {
		explosionsSet.add(this);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.explosion = new BigExplosion(this.worldObj, nbt);
		this.setPosition(this.explosion.x + 0.5, this.explosion.y + 0.5, this.explosion.z + 0.5);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		if (this.explosion != null)
			explosion.writeToNBT(nbt);
	}
	
	@Override
	public void setDead() {
		if (this.explosion == null || this.explosion.finished) {
			super.setDead();
		}
	}
	
	@Override
	public int getBrightnessForRender(float p_70070_1_) {
		return 15728880;
	}
	
	@Override
	public float getBrightness(float p_70013_1_) {
		return 1F;
	}
	
	@Override
	public AxisAlignedBB getCollisionBox(Entity p_70114_1_) {
		return AxisAlignedBB.getBoundingBox(posX - 0.1D, posY, posZ - 0.1D, posX + 0.1D, posY + 0.1D, posZ + 0.1D);
	}
	
	@Override
	public boolean canBePushed() {
		return false;
	}
	
	@Override
	public boolean isInRangeToRenderDist(double dist) {
		if (this.explosion != null) {
			return dist < this.explosion.radius + 128d;
		}
		return dist < 128d;
	}
	
	 @Override
	public boolean isInRangeToRender3d(double x, double y, double z) {
		x -= this.posX;
		y -= this.posY;
		z -= this.posZ;
		return this.isInRangeToRenderDist(Math.sqrt(x * x + y * y + z * z));
	}
}
