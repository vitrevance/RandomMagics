package randommagics.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import randommagics.Init;
import randommagics.Main;
import randommagics.curses.Curse;
import randommagics.curses.CurseRegistry;
import randommagics.customs.RandomUtils;
import randommagics.customs.StructureHelper;
import randommagics.dimensions.CustomTeleporter;
import randommagics.dimensions.DimensionRegistry;
import scala.util.Random;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;

public class BlockRitualStoneSpirit extends Block {

	public BlockRitualStoneSpirit()
	{
		super(Material.rock);
		this.setBlockName("ritualStoneSpirit");
		this.setCreativeTab(Init.TabRandomMagics);
		this.setBlockTextureName("randommagics:RitualStoneSpirit");
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(3.0F);
	}
	
	private boolean StructExists(World world, int x, int y, int z)
	{
		if ((world.getBlock(x, y+1, z) == ConfigBlocks.blockJar && world.getTileEntity(x, y+1, z).getBlockMetadata() == 1)
			&& (world.getBlock(x, y-1, z) == Init.RitualStone)
				&& (world.getBlock(x+1, y-1, z) == ConfigBlocks.blockCosmeticSolid)
				&& (world.getBlockMetadata(x+1, y-1, z) == 0)
				&& (world.getBlock(x-1, y-1, z) == ConfigBlocks.blockCosmeticSolid)
				&& (world.getBlockMetadata(x-1, y-1, z) == 0)
				&& (world.getBlock(x, y-1, z+1) == ConfigBlocks.blockCosmeticSolid)
				&& (world.getBlockMetadata(x, y-1, z+1) == 0)
				&& (world.getBlock(x, y-1, z-1) == ConfigBlocks.blockCosmeticSolid)
				&& (world.getBlockMetadata(x, y-1, z-1) == 0)
					&& (world.getBlock(x-2, y-1, z) == Init.RitualStone)
						&& (world.getBlock(x+2, y-1, z) == Init.RitualStone)
							&& (world.getBlock(x, y-1, z-2) == Init.RitualStone)
								&& (world.getBlock(x, y-1, z+2) == Init.RitualStone)
									&& (world.getBlock(x-1, y-1, z-1) == Init.RitualStone)
										&& (world.getBlock(x+1, y-1, z+1) == Init.RitualStone)
											&& (world.getBlock(x-1, y-1, z+1) == Init.RitualStone)
												&& (world.getBlock(x+1, y-1, z-1) == Init.RitualStone)
													&& (world.getBlock(x+1, y, z+1) == ConfigBlocks.blockCrystal)
													&& (world.getTileEntity(x+1, y, z+1).getBlockMetadata() == 6)
													&& (world.getBlock(x+1, y, z-1) == ConfigBlocks.blockCrystal)
													&& (world.getTileEntity(x+1, y, z-1).getBlockMetadata() == 6)
													&& (world.getBlock(x-1, y, z+1) == ConfigBlocks.blockCrystal)
													&& (world.getTileEntity(x-1, y, z+1).getBlockMetadata() == 6)
													&& (world.getBlock(x-1, y, z-1) == ConfigBlocks.blockCrystal)
													&& (world.getTileEntity(x-1, y, z-1).getBlockMetadata() == 6))
													return true;
		else
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote)
		{
			Block struct = world.getBlock(x, y+1, z);
			TileEntity brjar = world.getTileEntity(x, y+1, z);
			ItemStack current_is = player.getCurrentEquippedItem();
			if (current_is != null && current_is.getItem() == Init.RitualCatalyst && StructExists(world, x, y, z))
			{
				player.setCurrentItemOrArmor(0, new ItemStack(current_is.getItem(), (current_is.stackSize)-1));
				EntityPlayerMP thePlayer = (EntityPlayerMP)player;
				world.playSoundAtEntity(thePlayer, "thaumcraft:ice", 0.3F, 0.8F + world.rand.nextFloat() * 0.1F);
				CustomTeleporter.teleportToDimension(thePlayer, player.worldObj.provider.dimensionId == DimensionRegistry.dimensionId ? 0 : DimensionRegistry.dimensionId, 0, 0, 0);
			
			}
		}
		ItemStack current_is = player.getCurrentEquippedItem();
		if (current_is != null && current_is.getItem() == Init.ItemSoulBinder && current_is.hasTagCompound() && ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), "curseRitual")) {
			if (!current_is.getTagCompound().getString("bind").isEmpty()) {
				Init.StructureSpiritRitual.setRel(x, y, z);
				if (Init.StructureSpiritRitual.checkStruct(world)) {
					List scroll = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 2, z + 1));
					if (scroll.size() == 1) {
						EntityItem scei = (EntityItem)scroll.get(0);
						if (scei.getEntityItem().getItem() == Init.ItemCursedScroll && scei.getEntityItem().hasTagCompound() && !scei.getEntityItem().getTagCompound().getString("curse").isEmpty()) {
							String curseTarget = current_is.getTagCompound().getString("bind");
							List ingots = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x - 4, y, z - 4, x + 4, y + 2, z + 4));
							int donation = 0;
							for (EntityItem ing : (List<EntityItem>)ingots) {
								if (ing.getEntityItem().getItem() == Init.ItemRMResource && ing.getEntityItem().getItemDamage() == 26) {
									int sts = ing.getEntityItem().stackSize;
									if (sts > 8 - donation) {
										if (!world.isRemote)
											ing.getEntityItem().stackSize = sts - (8 - donation);
										donation = 8;
										break;
									}
									else {
										donation += sts;
										if (!world.isRemote)
											ing.setDead();
									}
								}
								if (donation >= 8) {
									break;
								}
							}
							if (donation >= 8) {
								EntityPlayer target = RandomUtils.getEntityPlayerByName(curseTarget, player.worldObj.isRemote);
								Curse curse = CurseRegistry.getCurseByID(scei.getEntityItem().getTagCompound().getString("curse"));
								if (curse != null && target != null) {
									CurseRegistry.applyOnPlayerSided(target, player, curse, world.isRemote);
									player.swingItem();
									world.playSoundEffect(x, y, z, "thaumcraft:craftfail", 1.5F, 0.4F);
									scei.setDead();
									if (!world.isRemote) {
										Random rand = new Random();
										if (rand.nextFloat() < 0.2f) {
											player.setCurrentItemOrArmor(0, null);
											world.playSoundAtEntity(player, "random.break", 1.0F, 0.6F);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
	}
}
