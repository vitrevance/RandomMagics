package randommagics.items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockSand;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import randommagics.Config;
import randommagics.EnchantmentInit;
import randommagics.Init;
import randommagics.customs.RandomUtils;
import randommagics.enchantments.EnchantmentsHelper;
import thaumcraft.common.lib.utils.BlockUtils;

public class ItemPickaxeOfTheLordOfTheEarth extends ItemTool {
	
	private Set<String> toolClasses = new HashSet<String>();
	private static final int maxcd = 800;
	private int side;

	public ItemPickaxeOfTheLordOfTheEarth() {
		super(-556, Init.INFINITE_STONE, new HashSet<Block>());
		setUnlocalizedName("ItemPickaxeOfTheLordOfTheEarth");
		setTextureName("randommagics:PickaxeOfTheLordOfTheEarth");
		setCreativeTab(Init.TabRandomMagics);
		toolClasses.add("pickaxe");
		toolClasses.add("shovel");
	}
	
	@Override
	public boolean canHarvestBlock(Block block, ItemStack stack) {
		return true;
	}
	
	@Override
	public Set<String> getToolClasses(ItemStack stack) {
		return toolClasses;
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
		int aoe = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.AOEAugment.effectId, itemstack);
		if (aoe > 0) {
			MovingObjectPosition movingobjectposition = BlockUtils.getTargetBlock(player.worldObj, player, true);
	        if(movingobjectposition != null && movingobjectposition.typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.BLOCK)
	            side = movingobjectposition.sideHit;
		}
		else {
			boolean trash = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.TrashVoiderAugment.effectId, itemstack) > 0;
			Block cur = player.worldObj.getBlock(X, Y, Z);
			if (cur instanceof BlockFalling && !player.isSneaking()) {
				for (int i = 1; i < 6; i++) {
					Block b = player.worldObj.getBlock(X, Y + i, Z);
					if (b == cur) {
						if (trash && (b == Blocks.stone || b == Blocks.gravel || b == Blocks.dirt))
	                    	BlockUtils.removeBlock(player.worldObj, X, Y + i, Z, player);
						else
							BlockUtils.harvestBlock(player.worldObj, player, X, Y + i, Z, true, 2);
					}
					else {
						break;
					}
				}
			}
		}
		return super.onBlockStartBreak(itemstack, X, Y, Z, player);
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block bi, int x, int y, int z, EntityLivingBase ent)
    {
        if(ent.isSneaking())
            return super.onBlockDestroyed(stack, world, bi, x, y, z, ent);
        int aoe = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.AOEAugment.effectId, stack);
        
        if(!ent.worldObj.isRemote)
        {
        	boolean trash = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.TrashVoiderAugment.effectId, stack) > 0;
            int md = world.getBlockMetadata(x, y, z);
            ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
            
            for(int aa = -aoe; aa <= aoe; aa++)
            {
                for(int bb = (side <= 1 ? -aoe : -1); bb <= aoe + (side <= 1 ? 0 : (aoe - 1)); bb++)
                {
                    int xx = 0;
                    int yy = 0;
                    int zz = 0;
                    if(side <= 1)
                    {
                        xx = aa;
                        zz = bb;
                    } else
                    if(side <= 3)
                    {
                        xx = aa;
                        yy = bb;
                    } else
                    {
                        zz = aa;
                        yy = bb;
                    }
                    if((ent instanceof EntityPlayer) && !world.canMineBlock((EntityPlayer)ent, x + xx, y + yy, z + zz))
                        continue;
                    Block bl = world.getBlock(x + xx, y + yy, z + zz);
                    md = world.getBlockMetadata(x + xx, y + yy, z + zz);
                    boolean isNotTrash = true;
                    if (trash) {
                    	NBTTagCompound nbt = stack.getTagCompound();
                    	int count = nbt.getInteger("blacklistSize");
                    	for (int i = 0; i < count; i++) {
                    		int blid = nbt.getInteger("blacklistid_" + i);
                    		int blmeta = nbt.getInteger("blacklistmeta_" + i);
                    		if (Block.getIdFromBlock(bl) == blid && md == blmeta) {
                    			BlockUtils.removeBlock(world, x + xx, y + yy, z + zz, (EntityPlayer)ent);
                    			isNotTrash = false;
                    			break;
                    		}
                    	}
                    }
                    if(isNotTrash && bl.getBlockHardness(world, x + xx, y + yy, z + zz) >= 0.0F)
                    {
                    	if (!EnchantmentsHelper.hasEnchantment(stack, EnchantmentInit.MatterCondenser.effectId)) {
                    		RandomUtils.harvestBlock(world, (EntityPlayer)ent, x + xx, y + yy, z + zz, true, 2);
                    	}
                    	else {
	                    	ArrayList<ItemStack> digged = RandomUtils.harvestBlockVirtual(world, (EntityPlayer)ent, x + xx, y + yy, z + zz);
	                    	for (ItemStack st1 : digged) {
	                    		boolean add = true;
	                    		for (ItemStack st2 : drops) {
	                    			if (st2.getItem() == st1.getItem() && st1.getItemDamage() == st2.getItemDamage()) {
	                    				if (ItemStack.areItemStackTagsEqual(st1, st2)) {
	                    					st2.stackSize += st1.stackSize;
	                    					add = false;
	                    				}
	                    			}
	                    		}
	                    		if (add) {
	                    			drops.add(st1);
	                    		}
	                    	}
                    	}
                    }
                }
            }
            if (drops.size() > 0) {
	            ItemStack matter = new ItemStack(Init.ItemCondensedMatter);
	    		ItemCondensedMatter.loadArray(matter, drops);
	    		EntityItem e = new EntityItem(world, ent.posX, ent.posY, ent.posZ);
	    		e.setEntityItemStack(matter);
	    		world.spawnEntityInWorld(e);
            }
        }
        
        List<EntityXPOrb> xpOrbs = world.getEntitiesWithinAABB(EntityXPOrb.class,
        		AxisAlignedBB.getBoundingBox(ent.posX, ent.posY, ent.posZ, ent.posX + 1, ent.posY + 1, ent.posZ + 1).expand(aoe, aoe, aoe));
        for (EntityXPOrb it : xpOrbs) {
        	it.setPosition(ent.posX, ent.posY, ent.posZ);
        }
        
        return true;
    }
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x,
			int y, int z, int d, float e, float f, float g) {
		if (world.getBlock(x, y, z) == Blocks.stone && stack.getTagCompound().getInteger("coolDown") == 0) {
			stack.getTagCompound().setInteger("coolDown", maxcd / (1 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.CooldownReductionAugment.effectId, stack)));
			breakB(world, x, y, z, 0, 32 + EnchantmentHelper.getEnchantmentLevel(Config.EnchantmentDistanceAugmentId, stack), EnchantmentsHelper.hasEnchantment(stack, Config.enchantmentTriDimensionalAugmentId));
		}
		return false;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity ent, int p_77663_4_,
			boolean p_77663_5_) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		int cd = stack.getTagCompound().getInteger("coolDown");
		if (cd > 0) {
			stack.getTagCompound().setInteger("coolDown", cd - 1);
		}
		super.onUpdate(stack, world, ent, p_77663_4_, p_77663_5_);
	}
	
	private void breakB(World world, int x, int y, int z, int i, int max, boolean go3D) {
		if (i >= max)
			return;
		if (world.getBlock(x - 1, y, z) == Blocks.stone) {
			world.setBlockToAir(x - 1, y, z);
			world.markBlockForUpdate(x - 1, y, z);
			breakB(world, x - 1, y, z, i + 1, max, go3D);
		}
		if (world.getBlock(x + 1, y, z) == Blocks.stone) {
			world.setBlockToAir(x + 1, y, z);
			world.markBlockForUpdate(x + 1, y, z);
			breakB(world, x + 1, y, z, i + 1, max, go3D);
		}
		if (world.getBlock(x, y, z - 1) == Blocks.stone) {
			world.setBlockToAir(x, y, z - 1);
			world.markBlockForUpdate(x, y, z - 1);
			breakB(world, x, y, z - 1, i + 1, max, go3D);
		}
		if (world.getBlock(x, y, z + 1) == Blocks.stone) {
			world.setBlockToAir(x, y, z + 1);
			world.markBlockForUpdate(x, y, z + 1);
			breakB(world, x, y, z+ 1, i + 1, max, go3D);
		}
		if (go3D) {
			if (world.getBlock(x, y + 1, z) == Blocks.stone) {
				world.setBlockToAir(x, y + 1, z);
				world.markBlockForUpdate(x, y + 1, z);
				breakB(world, x, y + 1, z, i + 1, max, go3D);
			}
			if (world.getBlock(x, y - 1, z) == Blocks.stone) {
				world.setBlockToAir(x, y - 1, z);
				world.markBlockForUpdate(x, y - 1, z);
				breakB(world, x, y - 1, z, i + 1, max, go3D);
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b) {
		super.addInformation(stack, player, list, b);
		if (stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			int cd = nbt.getInteger("coolDown");
			list.add("Cooldown: " + (cd == 0 ? "ready" : cd));
			int count = nbt.getInteger("blacklistSize");
			for (int i = 0; i < count; i++) {
				ItemStack tmp = new ItemStack(Block.getBlockById(nbt.getInteger("blacklistid_" + i)), 1, nbt.getInteger("blacklistmeta_" + i));
				list.add(tmp.getDisplayName());
				//list.add("ii " + i);
			}
		}
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		return efficiencyOnProperMaterial;
	}
}
