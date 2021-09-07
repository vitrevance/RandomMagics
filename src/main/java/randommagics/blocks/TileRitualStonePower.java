package randommagics.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import randommagics.EnchantmentInit;
import randommagics.Init;
import randommagics.enchantments.EnchantmentDisintegration;
import randommagics.rituals.EnchantmentRitualReciepe;
import randommagics.rituals.PowerRitualReciepe;
import randommagics.rituals.PowerRitualReciepe.Type;
import randommagics.rituals.RitualHelper;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.nodes.NodeModifier;
import thaumcraft.client.lib.TCFontRenderer;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXInfusionSource;

public class TileRitualStonePower extends TileThaumcraft implements IAspectContainer {
	
	public boolean active;
	private ItemStack stack;
	private int visamount;
	private int visreciepe;
	private TileEntity[] nodes;
	private int timer;
	private static final int cycleTime = 10;
	private PowerRitualReciepe reciepe;
	private AspectList containAspects;
	
	public TileRitualStonePower() {
		active = false;
		visreciepe = -1;
		visamount = 0;
		stack = null;
		timer = cycleTime;
		containAspects = new AspectList();
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound nbt) {
		nbt.setBoolean("active", active);
		nbt.setInteger("visa", visamount);
		nbt.setInteger("visr", visreciepe);
		nbt.setInteger("t", timer);
		if (stack != null) {
			NBTTagCompound st = new NBTTagCompound();
			stack.writeToNBT(st);
			nbt.setTag("stack", st);
		}
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound nbt) {
		active = nbt.getBoolean("active");
		visamount = nbt.getInteger("visa");
		visreciepe = nbt.getInteger("visr");
		timer = nbt.getInteger("t");
		if (nbt.getCompoundTag("stack") != null) {
			stack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("stack"));
		}
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (active && stack != null) {
			if (containAspects.size() > 1)
				containAspects.aspects.clear();
			containAspects.aspects.put(Aspect.AURA, visreciepe - visamount);
			timer--;
			if (checkSurroundings()) {
				
				if (visamount == visreciepe) {
					 worldObj.playSoundEffect(xCoord, yCoord, zCoord, "thaumcraft:rumble", 5F, 1.0F);
					 if (!worldObj.isRemote)
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, stack.copy()));
					active = false;
					visamount = 0;
					visreciepe = -1;
					timer = cycleTime;
					return;
				}
				for (int i = 0; i < 8; i++) {
					if (nodes[i] != null) {
						//Thaumcraft.proxy.beam(worldObj, nodes[i].xCoord, nodes[i].yCoord, nodes[i].zCoord, xCoord, yCoord, zCoord, 1, 255, false, 5, 0);
						if (worldObj.isRemote)
							Thaumcraft.proxy.drawInfusionParticles4(worldObj, nodes[i].xCoord + 0.5, nodes[i].yCoord + 1.1, nodes[i].zCoord + 0.5, xCoord, yCoord, zCoord);
						
						//effects for nodes and stuff here )
						
						if (timer == 0) {
							
							INode te = (INode)nodes[i];
							if (te.getAspects() == null || te.getAspects().size() == 0) {
								//worldObj.destroyBlockInWorldPartially(0, nodes[i].xCoord, nodes[i].yCoord, nodes[i].zCoord, 100);
								worldObj.setBlockToAir(nodes[i].xCoord, nodes[i].yCoord, nodes[i].zCoord);
								continue;
							}
							AspectList asp = te.getAspects();
							if (asp.getAmount(asp.getAspects()[0]) == 0) {
								asp.remove(asp.getAspects()[0]);
							}
							if (visamount < visreciepe && asp.size() > 0 && te.takeFromContainer(asp.getAspects()[0], 1)) {
								visamount++;
							}
							worldObj.markBlockForUpdate(nodes[i].xCoord, nodes[i].yCoord, nodes[i].zCoord);
						}
					}
				}
			}
			if (timer == 0) {
				timer = cycleTime;
			}
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			markDirty();
		}
		if (active && stack == null) {
			if (reciepe != null)
			if (reciepe.type == Type.enchantment) {
				List l = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 2, zCoord + 1));
				if (l.size() == 1) {
					EntityItem item = (EntityItem)l.get(0);
					stack = item.getEntityItem();
					if (stack.stackSize == 1) {
						if (stack != null) {
							EnchantmentRitualReciepe rep = (EnchantmentRitualReciepe)reciepe;
							if (rep.enchant != null && rep.enchant.canApply(stack)) {
								item.setDead();
								this.stack.addEnchantment(rep.enchant, rep.lvl);
								if (rep.enchant instanceof EnchantmentDisintegration) {
									this.stack.getTagCompound().setBoolean("honest", true);
								}
							}
							else {
								stack = null;
							}
						}
					}
				}
			}
		}
		if (!active && containAspects.size() > 0) {
			containAspects.remove(Aspect.AURA);
		}
	}
	
	public boolean startRitual(EntityPlayer player) {
		if (active)
			return false;
		if (ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), "ritualStonePower") && checkSurroundings()) {
			List l = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 2, zCoord + 1));
			if (l.size() == 1) {
				EntityItem item = (EntityItem)l.get(0);
				stack = item.getEntityItem();
				if (stack != null) {
					reciepe = RitualHelper.findPowerReciepe(stack);
					if (reciepe == null) {
						return false;
					}
					if (reciepe.research != null && reciepe.research.length > 0) {
						for (String i : reciepe.research) {
							if (!ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), i)) {
								return false;
							}
						}
					}
					visreciepe = reciepe.vis;
					if (reciepe.type == Type.basic)
						this.stack = reciepe.result.copy();
					if (reciepe.type == Type.enchantment) {
						this.stack = null;
					}
					/*
					if (rec.type == Type.enchantment) {
						EnchantmentRitualReciepe rep = (EnchantmentRitualReciepe)rec;
						this.stack = rec.input.copy();
						this.stack.addEnchantment(rep.enchant, rep.lvl);
						if (rep.enchant instanceof EnchantmentDisintegration) {
							this.stack.getTagCompound().setBoolean("honest", true);
						}
					}
					*/
					visamount = 0;
					item.setDead();
					active = true;
					worldObj.playSoundEffect(xCoord, yCoord, zCoord, "thaumcraft:craftfail", 5F, 1.0F);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					markDirty();
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkSurroundings() {
		for (int i = -2; i < 2; i++) {
			if (i != 0 && (worldObj.getBlock(xCoord + i, yCoord, zCoord) != ConfigBlocks.blockCosmeticSolid || worldObj.getBlockMetadata(xCoord + i, yCoord, zCoord) != 7)) {
				return false;
			}
		}
		for (int i = -2; i < 2; i++) {
			if (i != 0 && (worldObj.getBlock(xCoord, yCoord, zCoord + i) != ConfigBlocks.blockCosmeticSolid || worldObj.getBlockMetadata(xCoord, yCoord, zCoord + i) != 7)) {
				return false;
			}
		}
		if (worldObj.getBlock(xCoord + 1, yCoord, zCoord + 1) != Init.RitualStone || worldObj.getBlock(xCoord + 1, yCoord, zCoord - 1) != Init.RitualStone || worldObj.getBlock(xCoord - 1, yCoord, zCoord + 1) != Init.RitualStone || worldObj.getBlock(xCoord - 1, yCoord, zCoord - 1) != Init.RitualStone) {
			return false;
		}
		if (worldObj.getBlock(xCoord + 2, yCoord, zCoord + 2) != Blocks.quartz_block || worldObj.getBlockMetadata(xCoord + 2, yCoord, zCoord + 2) != 2 || worldObj.getBlock(xCoord - 2, yCoord, zCoord + 2) != Blocks.quartz_block || worldObj.getBlockMetadata(xCoord - 2, yCoord, zCoord + 2) != 2 || worldObj.getBlock(xCoord + 2, yCoord, zCoord - 2) != Blocks.quartz_block || worldObj.getBlockMetadata(xCoord + 2, yCoord, zCoord - 2) != 2 || worldObj.getBlock(xCoord - 2, yCoord, zCoord - 2) != Blocks.quartz_block || worldObj.getBlockMetadata(xCoord - 2, yCoord, zCoord - 2) != 2) {
			return false;
		}
		if (worldObj.getBlock(xCoord + 3, yCoord, zCoord) != Blocks.quartz_block || worldObj.getBlockMetadata(xCoord + 3, yCoord, zCoord) != 2 || worldObj.getBlock(xCoord - 3, yCoord, zCoord) != Blocks.quartz_block || worldObj.getBlockMetadata(xCoord - 3, yCoord, zCoord) != 2 || worldObj.getBlock(xCoord, yCoord, zCoord + 3) != Blocks.quartz_block || worldObj.getBlockMetadata(xCoord, yCoord, zCoord + 3) != 2 || worldObj.getBlock(xCoord, yCoord, zCoord - 3) != Blocks.quartz_block || worldObj.getBlockMetadata(xCoord, yCoord, zCoord - 3) != 2) {
			return false;
		}
		Block a = ConfigBlocks.blockCosmeticOpaque;
		if (!eq(1, 0, -2, a, 1) || !eq(2, 0, -1, a, 1) || !eq(2, 0, 1, a, 1) || !eq(1, 0, 2, a, 1) || !eq(-1, 0, 2, a, 1) || !eq(-2, 0, 1, a, 1) || !eq(-2, 0, -1, a, 1) || !eq(-1, 0, -2 ,a, 1)) {
			return false;
		}
		Block r = Init.RitualStone;
		if (!eq(2, 1, 2, r, 0) || !eq(-2, 1, 2, r, 0) || !eq(2, 1, -2, r, 0) || !eq(-2, 1,-2, r, 0)) {
			return false;
		}
		if (!eq(3, 1, 0, r, 0) || !eq(-3, 1, 0, r, 0) || !eq(0, 1, 3, r, 0) || !eq(0, 1, -3, r, 0)) {
			return false;
		}
		Block d = ConfigBlocks.blockStoneDevice;
		if (!eq(2, 2, 2, d, 9) || !eq(-2, 2, 2, d, 9) || !eq(2, 2, -2, d, 9) || !eq(-2, 2, -2, d, 9)) {
			return false;
		}
		if (!eq(3, 2, 0, d, 9) || !eq(-3, 2, 0, d, 9) || !eq(0, 2, 3, d, 9) || !eq(0, 2, -3, d, 9)) {
			return false;
		}
		nodes = new TileEntity[8];
		int x, z;
		x = 2;
		z = 2;
		nodes[0] = (worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) != null && worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) instanceof INode) ? worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) : null;
		x = -2;
		z = 2;
		nodes[1] = (worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) != null && worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) instanceof INode) ? worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) : null;
		x = 2;
		z = -2;
		nodes[2] = (worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) != null && worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) instanceof INode) ? worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) : null;
		x = -2;
		z = -2;
		nodes[3] = (worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) != null && worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) instanceof INode) ? worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) : null;
		x = 3;
		z = 0;
		nodes[4] = (worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) != null && worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) instanceof INode) ? worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) : null;
		x = -3;
		nodes[5] = (worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) != null && worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) instanceof INode) ? worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) : null;
		x = 0;
		z = 3;
		nodes[6] = (worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) != null && worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) instanceof INode) ? worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) : null;
		z = -3;
		nodes[7] = (worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) != null && worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) instanceof INode) ? worldObj.getTileEntity(xCoord + x, yCoord + 3, zCoord + z) : null;
		return true;
	}
	
	private boolean eq(int x, int y, int z, Block b, int meta) {
		if (worldObj.getBlock(xCoord + x, yCoord + y, zCoord + z) != b || worldObj.getBlockMetadata(xCoord + x, yCoord + y, zCoord + z) != meta)
			return false;
		return true;
	}

	@Override
	public AspectList getAspects() {
		return containAspects;
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
	
}
