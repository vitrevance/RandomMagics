package randommagics.blocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import randommagics.Init;
import randommagics.customs.AuraInfusionHelper;
import randommagics.customs.AuraInfusionRecipe;
import randommagics.customs.ILifeContainer;
import randommagics.customs.RandomUtils;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.wands.IWandable;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.entities.monster.EntityTaintCreeper;
import thaumcraft.common.entities.monster.EntityTaintSpore;
import thaumcraft.common.entities.monster.EntityTaintSwarm;
import thaumcraft.common.entities.monster.EntityTaintacle;
import thaumcraft.common.items.relics.ItemSanityChecker;
import thaumcraft.common.tiles.TilePedestal;

public class TileAuraInfusionMatrix extends TileThaumcraft implements IWandable, IAspectContainer {
	
	private ArrayList<TilePedestal> pedestals;
	private ArrayList<TileEntity> nodes;
	private ArrayList<TileEntity> essenceSources;
	private TilePedestal centralPed;
	public boolean active;
	public boolean crafting;
	private int recipeVis;
	private int timer;
	private final int maxTimer = 40;
	private AuraInfusionRecipe recipe;
	private int itemIndex;
	private int partInstability;
	private boolean recheckSurroundings;
	
	public float rotx = 0f;
	public float roty = 0f;
	public float rotz = 0f;
	private Vector3 animationTarget;
	private Vector3 animationState;
	private float animationdelta = 1.1f;
	
	public String playerPlacedName;
	
	
	public TileAuraInfusionMatrix() {
		this.active = false;
		this.crafting = false;
		this.pedestals = new ArrayList<TilePedestal>();
		this.nodes = new ArrayList<TileEntity>();
		this.essenceSources = new ArrayList<TileEntity>();
		this.timer = maxTimer;
		this.itemIndex = 0;
		this.recipe = null;
		this.partInstability = 0;
		this.recheckSurroundings = false;
		
		this.animationTarget = new Vector3(0, 0, 0);
		
		this.playerPlacedName = "";
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (this.active) {
			this.active = checkStruct();
			Random r = worldObj.rand;
			if (this.animationdelta >= 1f) {
				this.animationState = this.animationTarget.copy();
				this.animationTarget.x = r.nextDouble() * 360d;
				this.animationTarget.y = r.nextDouble() * 360d;
				this.animationTarget.z = r.nextDouble() * 360d;
				this.animationdelta = 0f;
			}
			this.rotx = RandomUtils.lerp((float)this.animationState.x, (float)this.animationTarget.x, this.animationdelta);
			this.roty = RandomUtils.lerp((float)this.animationState.y, (float)this.animationTarget.y, this.animationdelta);
			this.rotz = RandomUtils.lerp((float)this.animationState.z, (float)this.animationTarget.z, this.animationdelta);
			this.animationdelta += 0.001;
			
			if (this.crafting) {
				checkSurroundings();
				
				if (this.centralPed.getStackInSlot(0) == null) {
					this.crafting = false;
					this.recipe = null;
					worldObj.playSoundEffect(xCoord, yCoord, zCoord, "thaumcraft:craftfail", 1.0F, 0.6F);
					markDirty();
					return;
				}
			}
		}
		else {
			if (!this.animationTarget.isZero()) {
				this.animationTarget.multiply(0);
				this.animationState.x = this.rotx;
				this.animationState.y = this.roty;
				this.animationState.z = this.rotz;
				this.animationdelta = 0f;
			}
			if (this.animationdelta < 1f) {
				this.rotx = RandomUtils.lerp((float)this.animationState.x, (float)this.animationTarget.x, this.animationdelta);
				this.roty = RandomUtils.lerp((float)this.animationState.y, (float)this.animationTarget.y, this.animationdelta);
				this.rotz = RandomUtils.lerp((float)this.animationState.z, (float)this.animationTarget.z, this.animationdelta);
				this.animationdelta += 0.05;
			}
		}
		
		if (this.recheckSurroundings) {
			this.recheckSurroundings = false;
			boolean check = checkStruct();
			if (this.active) {
				this.active = check;
				checkSurroundings();
			}
			if (this.crafting) {
				this.crafting = check;
			}
		}
		if (this.active && this.crafting && this.recipe != null) {
			if (worldObj.isRemote && this.recipeVis > 0)
				for (TileEntity node : this.nodes)
					//Thaumcraft.proxy.drawInfusionParticles4(worldObj, node.xCoord + 0.5, node.yCoord + 1.1, node.zCoord + 0.5, xCoord, yCoord, zCoord);
					Thaumcraft.proxy.drawInfusionParticles3 (worldObj, node.xCoord + 0.5, node.yCoord + 1.1, node.zCoord + 0.5, xCoord, yCoord, zCoord);
			//Thaumcraft.proxy.essentiaTrailFx(worldObj, x, y, z, x2, y2, z2, count, color, scale);
			
			for (TileEntity node : this.nodes) {
				if (worldObj.getTileEntity(node.xCoord, node.yCoord - 1, node.zCoord) instanceof TileTimeExpander)
					this.partInstability++;
			}
			
			int totalInstability = this.partInstability + this.recipe.instability;
			for (TileEntity te : this.essenceSources) {
				ILifeContainer jar = (ILifeContainer)te;
				if (jar.getEssence() > 0) {
					if (worldObj.isRemote)
						Thaumcraft.proxy.essentiaTrailFx(worldObj, te.xCoord, te.yCoord, te.zCoord, xCoord, yCoord, zCoord, 1, 16714270, 1);
					if (this.timer % (this.maxTimer / 4) == 0) {
						totalInstability -= jar.drain(totalInstability);
						if (totalInstability <= 0)
							break;
					}
					else {
						break;
					}
				}
			}
			if (totalInstability > 0) {
				if (this.timer % (this.maxTimer / 4) == 0) {
					this.partInstability += totalInstability;
					Random r = worldObj.rand;
					if (worldObj.isRemote)
						Thaumcraft.proxy.arcLightning(worldObj, xCoord, yCoord, zCoord, xCoord + r.nextInt(14) - 7, yCoord + r.nextInt(7) - 2, zCoord + r.nextInt(14) - 7, 0.9f, 0.8f, 1f, 1f);
					if (totalInstability > 1) {
						List ents = worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(-11, -5, -11, 11, 5, 11));
						for (Object e : ents) {
							((EntityLiving)e).attackEntityFrom(DamageSource.magic, totalInstability);
						}
						
						if (totalInstability > 4) {
							RandomUtils.spawnEntityAt(worldObj, new EntityTaintSwarm(worldObj), xCoord + r.nextInt(14) - 7, yCoord + r.nextInt(7) - 2, zCoord + r.nextInt(14) - 7);
							if (r.nextBoolean()) {
								RandomUtils.spawnEntityAt(worldObj, new EntityTaintacle(worldObj), xCoord + r.nextInt(14) - 7, yCoord + r.nextInt(7) - 2, zCoord + r.nextInt(14) - 7);
							}
							if (r.nextBoolean() && r.nextBoolean()) {
								RandomUtils.spawnEntityAt(worldObj, new EntityTaintCreeper(worldObj), xCoord + r.nextInt(14) - 7, yCoord + r.nextInt(7) - 2, zCoord + r.nextInt(14) - 7);
							}
							
							if (totalInstability > 1000) {
								worldObj.createExplosion(null, xCoord + r.nextInt(14) - 7, yCoord + r.nextInt(7) - 2, zCoord + r.nextInt(14) - 7, 2, true);
								
								if (totalInstability > 10000) {
									worldObj.createExplosion(null, xCoord, yCoord, zCoord, 20, true);
								}
							}
						}
					}
				}
			}
			
			this.timer++;
			if (this.timer % 65 == 0)
				this.worldObj.playSound(xCoord, yCoord, zCoord, "thaumcraft:infuser", 0.5F, 1.0F, false);
			if (this.timer % this.maxTimer == 0 ) {
				if (this.recipeVis == 0) {
					if (this.itemIndex < this.recipe.ingredients.length) {
						boolean itemFound = false;
						for (TilePedestal ped : this.pedestals) {
							if (ItemStack.areItemStacksEqual(ped.getStackInSlot(0), this.recipe.ingredients[this.itemIndex])) {
								ped.setInventorySlotContentsFromInfusion(0, null);
								ped.markDirty();
								worldObj.markBlockForUpdate(ped.xCoord, ped.yCoord, ped.zCoord);
								this.itemIndex++;
								itemFound = true;
								break;
							}
						}
						if (!itemFound)
							this.partInstability++;
					}
					else {
						this.centralPed.setInventorySlotContentsFromInfusion(0, this.recipe.output.copy());
						this.crafting = false;
						this.partInstability = 0;
						this.itemIndex = 0;
						this.recipe = null;
					}
				}
				else {
					int prevVis = this.recipeVis;
					for (TileEntity nodeTe : this.nodes) {
						if (nodeTe == null || !(nodeTe instanceof INode)) {
							break;
						}
						
						INode te = (INode)nodeTe;
						if (te.getAspects() == null || te.getAspects().size() == 0) {
							worldObj.setBlockToAir(nodeTe.xCoord, nodeTe.yCoord, nodeTe.zCoord);
							worldObj.markBlockForUpdate(nodeTe.xCoord, nodeTe.yCoord, nodeTe.zCoord);
							continue;
						}
						AspectList asp = te.getAspects();
						if (asp.getAmount(asp.getAspects()[0]) == 0) {
							asp.remove(asp.getAspects()[0]);
						}
						if (this.recipeVis > 0 && asp.size() > 0 && te.takeFromContainer(asp.getAspects()[0], 1)) {
							this.recipeVis--;
						}
						nodeTe.markDirty();
						worldObj.markBlockForUpdate(nodeTe.xCoord, nodeTe.yCoord, nodeTe.zCoord);
						markDirty();
					}
					
					if (prevVis == this.recipeVis && this.recipeVis > 0) {
						this.partInstability++;
					}
				}
			}
			markDirty();
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		else {
			this.timer = 0;
		}
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound nbt) {
		nbt.setBoolean("active", this.active);
		nbt.setBoolean("crafting", this.crafting);
		nbt.setInteger("recipeVis", this.recipeVis);
		nbt.setInteger("timer", this.timer);
		nbt.setInteger("itemIndex", this.itemIndex);
		nbt.setInteger("partInstability", this.partInstability);
		nbt.setString("playerPlacedName", this.playerPlacedName);
		
		if (this.recipe != null) {
			NBTTagCompound inp = new NBTTagCompound();
			this.recipe.input.writeToNBT(inp);
			nbt.setTag("input", inp);
			if(this.recipe.ingredients.length > 0)
	        {
	            NBTTagList nbttaglist = new NBTTagList();
	            int count = 0;
	            for (int i = 0; i < this.recipe.ingredients.length; i++) {
	                ItemStack stack = this.recipe.ingredients[i];
	                if(stack != null)
	                {
	                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
	                    nbttagcompound1.setByte("item", (byte)count);
	                    stack.writeToNBT(nbttagcompound1);
	                    nbttaglist.appendTag(nbttagcompound1);
	                    count++;
	                }
	            }
	            nbt.setTag("recipein", nbttaglist);
	        }
		}
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound nbt) {
		this.active = nbt.getBoolean("active");
		this.crafting = nbt.getBoolean("crafting");
		this.recipeVis = nbt.getInteger("recipeVis");
		this.timer = nbt.getInteger("timer");
		this.itemIndex = nbt.getInteger("itemIndex");
		this.partInstability = nbt.getInteger("partInstability");
		this.playerPlacedName = nbt.getString("playerPlacedName");
		
		ItemStack input = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("input"));
		if (input != null) {
			NBTTagList nbttaglist = nbt.getTagList("recipein", 10);
	        ArrayList<ItemStack> recipeIngredients = new ArrayList<ItemStack>();
	        for(int i = 0; i < nbttaglist.tagCount(); i++)
	        {
	            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
	            byte b0 = nbttagcompound1.getByte("item");
	            recipeIngredients.add(ItemStack.loadItemStackFromNBT(nbttagcompound1));
	        }
	        ItemStack[] ingredients = recipeIngredients.toArray(new ItemStack[recipeIngredients.size()]);
	        
	        this.recipe = AuraInfusionHelper.findAuraInfusionRecipe(input, ingredients);
		}
		this.recheckSurroundings = true;
	}
	
	public void onRedstoneSignal(World world, int x, int y, int z, boolean signal) {
    	if (!world.isRemote && active && !crafting && signal) {
    		if (playerPlacedName != null && playerPlacedName.length() > 0) {
    			EntityPlayer player = RandomUtils.getEntityPlayerByName(playerPlacedName, world.isRemote);
    			if (player != null) {
    				tryStartInfusion(player);
    			}
    		}
    	}
    }
	
	private void tryStartInfusion(EntityPlayer player) {
		if (this.active && !this.crafting) {
			if (checkStruct()) {
				checkSurroundings();
				ItemStack input = centralPed.getStackInSlot(0);
				if (input == null)
					return;
				ArrayList<ItemStack> ingredients = new ArrayList<ItemStack>();
				for (TilePedestal ped : this.pedestals) {
					ItemStack item = ped.getStackInSlot(0);
					if (item != null) {
						ingredients.add(item);
					}
				}
				this.recipe = AuraInfusionHelper.findAuraInfusionRecipe(input, ingredients.toArray(new ItemStack[ingredients.size()]));
				if (this.recipe != null)
					if (this.recipe.research.length() == 0 || ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), this.recipe.research)) {
						this.recipeVis = this.recipe.vis;
						this.crafting = true;
						if (worldObj.isRemote)
							worldObj.playSound(xCoord, yCoord, zCoord, "thaumcraft:infuserstart", 0.5F, 1.0F, false);
					}
			}
		}
	}
	
	private boolean checkStruct() {
		if (this.worldObj.getBlock(xCoord + 1, yCoord - 1, zCoord + 1) != Init.RitualStone)
			return false;
		if (this.worldObj.getBlock(xCoord + 1, yCoord - 1, zCoord - 1) != Init.RitualStone)
			return false;
		if (this.worldObj.getBlock(xCoord - 1, yCoord - 1, zCoord + 1) != Init.RitualStone)
			return false;
		if (this.worldObj.getBlock(xCoord - 1, yCoord - 1, zCoord - 1) != Init.RitualStone)
			return false;
		if (this.worldObj.getBlock(xCoord + 1, yCoord - 2, zCoord + 1) != Init.RitualStone)
			return false;
		if (this.worldObj.getBlock(xCoord + 1, yCoord - 2, zCoord - 1) != Init.RitualStone)
			return false;
		if (this.worldObj.getBlock(xCoord - 1, yCoord - 2, zCoord + 1) != Init.RitualStone)
			return false;
		if (this.worldObj.getBlock(xCoord - 1, yCoord - 2, zCoord - 1) != Init.RitualStone)
			return false;
		if (this.worldObj.getBlock(xCoord, yCoord - 3, zCoord) != Init.RitualStonePower)
			return false;
		if (!(this.worldObj.getTileEntity(xCoord, yCoord - 2, zCoord) instanceof TilePedestal))
			return false;
		if (this.worldObj.getBlock(xCoord, yCoord - 1, zCoord).getMaterial() != Material.air)
			return false;
		return true;
	}
	
	private void checkSurroundings() {
		this.nodes.clear();
		this.pedestals.clear();
		this.essenceSources.clear();
		
		for (int xx = -11; xx < 12; xx++) {
			for (int zz = -11; zz < 12; zz++) {
				if (xx >= -1 && xx <= 1 && zz >= -1 && zz <= 1)
					continue;
				
				TileEntity e = worldObj.getTileEntity(xCoord + xx, yCoord - 2, zCoord + zz);
				if (e instanceof TilePedestal) {
					this.pedestals.add((TilePedestal)e);
				} else if (e instanceof INode) {
						this.nodes.add(e);
				} else if (e instanceof ILifeContainer) {
						this.essenceSources.add(e);
				}
				for (int yy = -1; yy < 3; yy++) {
					TileEntity ee = worldObj.getTileEntity(xCoord + xx, yCoord + yy, zCoord + zz);
					if (ee instanceof INode) {
						this.nodes.add(ee);
					} else if (ee instanceof ILifeContainer) {
						this.essenceSources.add(ee);
					}
				}
			}
		}
		this.centralPed = (TilePedestal)worldObj.getTileEntity(xCoord, yCoord - 2, zCoord);
	}

	@Override
	public AspectList getAspects() {
		if (this.crafting)
			return new AspectList().add(Init.diferium, this.recipeVis);
		return null;
	}

	@Override
	public void setAspects(AspectList aspects) {
	}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return false;
	}

	@Override
	public int addToContainer(Aspect tag, int amount) {
		return 0;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		return false;
	}

	@Override
	public boolean takeFromContainer(AspectList ot) {
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount) {
		return false;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		return false;
	}

	@Override
	public int containerContains(Aspect tag) {
		return 0;
	}

	@Override
	public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side,
			int md) {
		if (ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), "auraInfusion")) {
			if (this.active) {
				tryStartInfusion(player);
			}
			else {
				this.active = checkStruct();
			}
		}
		return 0;
	}

	@Override
	public ItemStack onWandRightClick(World world, ItemStack wandstack, EntityPlayer player) {
		player.swingItem();
		return wandstack;
	}

	@Override
	public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {
	}

	@Override
	public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {
	}

}
