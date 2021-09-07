package randommagics.entities;

import java.util.Iterator;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import randommagics.customs.CustomExplosion;
import scala.math.Integral.IntegralOps;
import thaumcraft.codechicken.lib.vec.Vector3;

public class EntityExpulosion extends Entity {
	
	public int timer;
	private float strenght;
	public int time;
	
	public EntityExpulosion(World world) {
		super(world);
		timer = 0;
		time = 0;
		strenght = 0;
		//setSize(24, 40);
		setSize(0.2F, 0.2F);
		ignoreFrustumCheck = true;
	}

	public EntityExpulosion(World world, int timer, float strenght, double posX, double posY, double posZ) {
		this(world);
		this.timer = timer;
		this.time = timer;
		this.strenght = strenght;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}
	
	public EntityExpulosion(World world, int timer, float strenght) {
		this(world);
		this.timer = timer;
		this.time = timer;
		this.strenght = strenght;
	}
	
	@Override
	public boolean isBurning() {
		return false;
	}

	@Override
	public void entityInit() {
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		timer = nbt.getInteger("timer");
		strenght = nbt.getFloat("strenght");
		time = nbt.getInteger("time");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("timer", timer);
		nbt.setFloat("strenght", strenght);
		nbt.setInteger("time", time);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		timer--;
		if (timer == 0) {
			//worldObj.createExplosion(null, xCoord, yCoord, zCoord, this.strenght, true);
			CustomExplosion exp = new CustomExplosion(worldObj, null, (int)posX, (int)posY, (int)posZ, strenght, 64, true);
			//if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(worldObj, exp)) return;
			exp.doExplosionA();
            Iterator iterator = exp.affectedBlockPositions.iterator();
            while (iterator.hasNext()) {
                ChunkPosition chunkPosition = (ChunkPosition) iterator.next();
                int x = chunkPosition.chunkPosX;
                int y = chunkPosition.chunkPosY;
                int z = chunkPosition.chunkPosZ;
                worldObj.setBlockToAir(x, y, z);
                worldObj.markBlockForUpdate(x, y, z);
            }
            exp.doExplosionB(true);
            setDead();
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
