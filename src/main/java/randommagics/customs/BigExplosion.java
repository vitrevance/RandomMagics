package randommagics.customs;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import randommagics.entities.EntityBigExplosion;

public class BigExplosion extends Explosion {
	
	private World worldObj;
	public int x, y, z;
	public float force, radius, s1, s2;
	private int state;
	private int stateX;
	private int stateY;
	private int stateLvl;
	public int lastLvl;
	private final int maxBlocks;
	public boolean finished;
	
	public BigExplosion(World world, int x, int y, int z, float f, float radius, float s1, float s2) {
		super(world, null, x, y, z, f);
		this.worldObj = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.force = f;
		this.radius = radius;
		this.s1 = s1;
		this.s2 = s2;
		this.state = 0;
		this.stateX = 0;
		this.stateY = 0;
		this.stateLvl = 0;
		this.lastLvl = 0;
		this.finished = false;
		
		this.maxBlocks = 50000;
	}
	
	public BigExplosion(World world, NBTTagCompound nbt) {
		super(world, null, nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"), nbt.getFloat("force"));
		this.readFromNBT(nbt);
		this.worldObj = world;
		
		this.maxBlocks = 50000;
	}
	
	public void doExplosionServer() {
		if (this.lastLvl > this.radius) {
			this.finished = true;
			return;
		}
		if (this.lastLvl == 0) {
			this.worldObj.setBlock(this.x, this.y, this.z, Blocks.air, 0, 2);
			this.lastLvl++;
			this.state = 5;
		}
		else {
			int lvl = this.lastLvl;
			for (int i = 0; i < this.maxBlocks;) {
				ChunkPosition statePos = this.fromState();
				for (int j = 0; j < 6; j++) {
					ChunkPosition blockPos = this.toSide(statePos, j);
					int xx = blockPos.chunkPosX + this.x;
					int yy = blockPos.chunkPosY + this.y;
					int zz = blockPos.chunkPosZ + this.z;
					Block repl = worldObj.getBlock(xx, yy, zz);
					if (repl != Blocks.air) {
						if (repl.getBlockHardness(worldObj, xx, yy, zz) >= 0) {
							float st = (float)Math.sqrt(blockPos.chunkPosX * blockPos.chunkPosX + blockPos.chunkPosY * blockPos.chunkPosY + blockPos.chunkPosZ * blockPos.chunkPosZ) / this.radius;
							if (st <= this.s1) {
								this.worldObj.setBlock(xx, yy, zz, Blocks.air, 0, 3);
							}
							else {
								if (st <= this.s2) {
									float ts = Math.abs((st - s1) * (1 / (1 - s1)) - 1);
									if (this.worldObj.rand.nextFloat() <= ts && repl.getMaterial() != Material.lava) {
										if (this.worldObj.rand.nextFloat() < 0.7) {
											this.worldObj.setBlock(xx, yy, zz, Blocks.air, 0, 3);
										}
										else {
											this.worldObj.setBlock(xx, yy, zz, Blocks.fire, 0, 3);
										}
									}
								}
							}
						}
						
					}
					i++;
				}
				if (this.lastLvl / this.radius >= 1f) {
					this.finished = true;
					break;
				}
			}
			if (lvl != this.lastLvl) {
				AxisAlignedBB box = AxisAlignedBB.getBoundingBox(this.x - this.lastLvl, this.y - this.lastLvl, this.z - this.lastLvl, this.x + this.lastLvl, this.y + this.lastLvl, this.z + this.lastLvl);
				List list = this.worldObj.getEntitiesWithinAABB(Entity.class, box);
				Iterator<Entity> it = list.iterator();
				while(it.hasNext()) {
					Entity e = it.next();
					if (e instanceof EntityLiving) {
						((EntityLiving)e).attackEntityFrom(DamageSource.setExplosionSource(this), this.radius / this.lastLvl * this.force);
					}
					else if (e instanceof EntityLivingBase) {
						((EntityLivingBase)e).attackEntityFrom(DamageSource.setExplosionSource(this), this.radius / this.lastLvl * this.force);
					}
					else if (!(e instanceof EntityBigExplosion)) {
						e.setDead();
					}
				}
			}
		}
	}
	
	public void doExplosionClient() {
		if (this.lastLvl > this.radius) {
			this.finished = true;
			return;
		}
		if (this.lastLvl == 0) {
			this.lastLvl++;
			this.state = 5;
		}
		else {
			int lvl = this.lastLvl;
			for (int i = 0; i < this.maxBlocks;) {
				this.fromState();
				i += 6;
				if (this.lastLvl / this.radius >= 1f) {
					this.finished = true;
					break;
				}
			}
		}
	}
	
	public ChunkPosition toSide(ChunkPosition coordXY, int side) {
		int xx = coordXY.chunkPosX;
		int yy = coordXY.chunkPosY;
		int zz = coordXY.chunkPosZ;
		
		int tmp = 0;
		switch (side) {
		case 1:
			xx *= -1;
			zz *= -1;
			break;
		case 2:
			tmp = xx;
			xx = zz;
			zz = tmp;
			break;
		case 3:
			tmp = xx;
			xx = -zz;
			zz = -tmp;
			break;
		case 4:
			tmp = yy;
			yy = -zz;
			zz = tmp;
			break;
		case 5:
			tmp = yy;
			yy = zz;
			zz = -tmp;
			xx *= -1;
			break;
		}
		return new ChunkPosition(xx, yy, zz);
	}
	
	public ChunkPosition fromState() {
		int square = (this.lastLvl * 2 + 1 - 2) * (this.lastLvl * 2 + 1 - 2);
		int zz = this.lastLvl;
		int am = this.stateLvl * 8;
		if (this.stateLvl > this.lastLvl) {
			this.lastLvl++;
			this.stateLvl = 0;
			this.state = 5;
			this.stateX = 0;
			this.stateY = 0;
		}
		if (this.stateX == this.stateLvl && this.stateY == 0 && this.state == 4) {
			this.stateLvl++;
			this.stateX++;
			this.state = 0;
		}
		else {
			if (this.state == 0) {
				if (this.stateY == -this.stateLvl || this.stateY + this.y <= 0 || this.stateY + this.y >= 256) {
					this.state++;
					this.stateX--;
				}
				else {
					this.stateY--;
				}
			}
			else if (this.state == 1) {
				if (this.stateX == -this.stateLvl) {
					this.state++;
					this.stateY++;
				}
				else {
					this.stateX--;
				}
			}
			else if (this.state == 2) {
				if (this.stateY == this.stateLvl || this.stateY + this.y <= 0 || this.stateY + this.y >= 256) {
					this.state++;
					this.stateX++;
				}
				else {
					this.stateY++;
				}
			}
			else if (this.state == 3) {
				if (this.stateX == this.stateLvl) {
					this.state++;
					this.stateY--;
				}
				else {
					this.stateX++;
				}
			}
			else if (this.state == 4) {
				this.stateY--;
			}
			else if (this.state == 5) {
				this.state = 4;
			}
		}
		return new ChunkPosition(this.stateX, this.stateY, zz);
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("x", this.x);
		nbt.setInteger("y", this.y);
		nbt.setInteger("z", this.z);
		nbt.setFloat("force", this.force);
		nbt.setFloat("radius", this.radius);
		nbt.setFloat("s1", this.s1);
		nbt.setFloat("s2", this.s2);
		nbt.setInteger("state", this.state);
		nbt.setInteger("stateX", this.stateX);
		nbt.setInteger("stateY", this.stateY);
		nbt.setInteger("stateLvl", this.stateLvl);
		nbt.setInteger("lastLvl", this.lastLvl);
		nbt.setBoolean("finished", this.finished);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		this.x = nbt.getInteger("x");
		this.y = nbt.getInteger("y");
		this.z = nbt.getInteger("z");
		this.force = nbt.getFloat("force");
		this.radius = nbt.getFloat("radius");
		this.s1 = nbt.getFloat("s1");
		this.s2 = nbt.getFloat("s2");
		this.state = nbt.getInteger("state");
		this.stateX = nbt.getInteger("stateX");
		this.stateY = nbt.getInteger("stateY");
		this.stateLvl = nbt.getInteger("stateLvl");
		this.lastLvl = nbt.getInteger("lastlvl");
		this.finished = nbt.getBoolean("finished");
	}
	
	public void create() {
		this.worldObj.spawnEntityInWorld(new EntityBigExplosion(this.worldObj, this));
	}
}
