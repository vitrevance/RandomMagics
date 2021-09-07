package randommagics.items;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cpw.mods.fml.relauncher.ServerLaunchWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import randommagics.Config;
import randommagics.Init;
import randommagics.Main;
import randommagics.containers.InventoryFocusInfinity;
import randommagics.customs.CustomExtendedEntityProperties;
import randommagics.entities.EntityBlockProjectile;
import randommagics.entities.EntityEntityProjectile;
import randommagics.entities.EntityExpulosion;
import randommagics.entities.EntityPotionProjectile;
import randommagics.entities.EntityPowerBlast;
import randommagics.entities.EntityStealingOrb;
import randommagics.gui.GUIHandler;
import scala.math.Numeric;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.IWandable;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.utils.BlockUtils;

public class FocusInfinity extends ItemFocusBasic {
	
	static enum eDictModes {NONE, AER, FIRE, WATER, EARTH, ORDER, ENTROPY, INFINITY};
	
	public FocusInfinity() {
		setCreativeTab(Init.TabRandomMagics);
		setUnlocalizedName("fociInfinity");
	}
	
	public IIcon iconOrnament; //ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
	this.icon = ir.registerIcon("randommagics:infinityF"); //ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	this.iconOrnament = ir.registerIcon("randommagics:infinityOrn"); //ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	this.depthIcon = ir.registerIcon("randommagics:infinityIn"); //ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	}
	
	public IIcon depthIcon = null; //ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int par1, int renderPass)
	{
		return renderPass == 1 ? this.icon : this.iconOrnament; //ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½, ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½, ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½, ï¿½  ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	}
	
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true; //ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½, ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½, ï¿½ ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	}
	
	public IIcon getOrnament(ItemStack itemstack)
	{
		return this.iconOrnament; //ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½, ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ - ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	}
	
	public IIcon getFocusDepthLayerIcon(ItemStack itemstack)
	{
		return this.depthIcon; //ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ (ï¿½ï¿½ï¿½ ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½, ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ ï¿½ ï¿½), ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ - ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	}
	
	public int getFocusColor(ItemStack focusstack) {//ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½! ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½, ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½, ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ RGB
		int a=100; //ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½, ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
		int r=230; //ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ RGB
		int g=255; //ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ RGB
		int b=251; //ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ RGB
		return  a << 24 | r << 16 | g << 8 | b << 0;
	}
	
	public AspectList getVisCost(ItemStack focusstack) {//ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
		return new AspectList(); //ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ 100 ï¿½. ï¿½. 100 ï¿½ ï¿½ï¿½ï¿½ï¿½ = 1 ï¿½ ï¿½ï¿½ï¿½ï¿½
	}
	
	public int getActivationCooldown(ItemStack focusstack) {
		return 500; //ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½, 1000 - ï¿½ï¿½ï¿½ 1 ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	}
	
	public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack itemstack, int rank) //ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	{
		return null;
	 }
	
	public String getSortingHelper(ItemStack itemstack) {
	   return "Infinity"; //ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½, ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ - ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½!
	}
	
	public ItemStack onFocusRightClick(ItemStack wandstack, World world, EntityPlayer player, MovingObjectPosition mop) {//ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
		ItemWandCasting wand = (ItemWandCasting)wandstack.getItem();
		ItemStack itemstack = wand.getFocusItem(wandstack);
		//if(!world.isRemote)
		if (player.isSneaking()) {
			if (!itemstack.hasTagCompound()) {
				NBTTagCompound nbt = new NBTTagCompound();
				itemstack.setTagCompound(nbt);
			}
			short mode = itemstack.getTagCompound().getShort("Mode");
			//IInventory inventory = new InventoryFocusInfinity(itemstack);
			ItemStack[] inventory = getInventory(itemstack);
			mode++;
			boolean round = false;
			boolean full = true;
			for (int i = 0; i < 6; i++) {
				if(inventory[i] == null) {
					full = false;
					break;
				}
			}
			while(true) {
				if(mode > 0 && mode < 7 && inventory[mode - 1] != null)
					break;
				if(full && mode == 7)
					break;
				mode++;
				if ((!full && mode > 6) || (full && mode > 7)) {
					mode = 0;
					if(round) {
						mode = 0;
						break;
					}
					round = true;
				}
			}
			itemstack.getTagCompound().setShort("Mode", mode);
			if(world.isRemote)
				player.addChatMessage(new ChatComponentText("§5Focus Mode: " + eDictModes.values()[mode].toString()));
		}
		if (!player.isSneaking()) {
			if (!itemstack.hasTagCompound()) {
				NBTTagCompound nbt = new NBTTagCompound();
				itemstack.setTagCompound(nbt);
			}
			ItemStack[] inventory = getInventory(itemstack);
			short mode = itemstack.getTagCompound().getShort("Mode");
			if (mode == 7) {
				boolean full = true;
				for (int i = 0; i < 6; i++) {
					if(inventory[i] == null) {
						full = false;
						break;
					}
				}
				if (!full)
					return wandstack;
			}
			if(mode == 0)
				return wandstack;
			if (mode < 7 && inventory[mode - 1] == null)
				return wandstack;
			switch(mode) {
			case 1:{
				List entities = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(player.posX - 16, player.posY - 16, player.posZ - 16, player.posX + 16, player.posY + 16, player.posZ + 16));
				for (Object entity : entities) {
					if (world.isRemote) {
						if (!(entity instanceof EntityClientPlayerMP) && !(entity instanceof EntityPlayer))
							((EntityLiving)entity).motionY = 2;
					}
					else {
						if (!(entity instanceof EntityPlayerMP) && !(entity instanceof EntityPlayer))
						((EntityLiving)entity).motionY = 2;
					}
				}
				break;
			}
			case 2:{
				if (!world.isRemote)
					world.spawnEntityInWorld(new EntityEntityProjectile(world, player, new EntityExpulosion(world, 100, 128)));
				break;
			}
			case 3:{
				if (!world.isRemote)
					world.spawnEntityInWorld(new EntityPotionProjectile(world, player, new PotionEffect(Config.potionStuckInTimeId, 200)));
				break;
			}
			case 4:{
				if (mop == null || mop.typeOfHit != MovingObjectType.BLOCK)
					break;
				int x = mop.blockX;
				int y = mop.blockY;
				int z = mop.blockZ;
				int side = mop.sideHit;
				if(side == 0 || side == 1) {
					Block block = world.getBlock(x, y, z);
					if (!block.hasTileEntity()) {
						//block.dropBlockAsItem(world, (int)player.posX, (int)player.posY, (int)player.posZ, 1, 0);
						block.dropBlockAsItemWithChance(world, (int)player.posX, (int)player.posY, (int)player.posZ, world.getBlockMetadata(x, y, z), 1.0F, 50);
						world.setBlock(x, y, z, Blocks.air);
					}
				} else {
					if (side == 4 || side == 5) {
						for (int i = -1; i < 23; i++) {
							for (int j = -12; j < 12; j++) {
								Block block = world.getBlock(x, y + i, z + j);
								if (!block.hasTileEntity()) {
									//block.dropBlockAsItem(world, (int)player.posX, (int)player.posY, (int)player.posZ, 1, 0);
									block.dropBlockAsItemWithChance(world, (int)player.posX, (int)player.posY, (int)player.posZ, world.getBlockMetadata(x, y + i, z + j), 1.0F, 50);
									world.setBlock(x, y + i, z + j, Blocks.air);
								}
							}
						}
					}
					if (side == 2 || side == 3) {
						for (int i = -1; i < 23; i++) {
							for (int j = -12; j < 12; j++) {
								Block block = world.getBlock(x + j, y + i, z);
								if (!block.hasTileEntity()) {
									//block.dropBlockAsItem(world, (int)player.posX, (int)player.posY, (int)player.posZ, 1, 0);
									block.dropBlockAsItemWithChance(world, (int)player.posX, (int)player.posY, (int)player.posZ, world.getBlockMetadata(x + j, y + i, z), 1.0F, 50);
									world.setBlock(x + j, y + i, z, Blocks.air);
								}
							}
						}
					}
				}
				break;
			}
			case 5:{
				teleporter(world, player);
				break;
			}
			case 6:{
				if (!world.isRemote)
					world.spawnEntityInWorld(new EntityPowerBlast(world, player));
				break;
			}
			case 7:{
				List entities = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(player.posX - 512, player.posY - 512, player.posZ - 512, player.posX + 512, player.posY + 512, player.posZ + 512));
				boolean lucky = true;
				for (Object entity : entities) {
					if (!lucky) {
						if (!Config.focusinfinityKillOwner && entity instanceof EntityPlayer)
							if (((EntityPlayer)entity) == player)
								continue;
						if (!world.isRemote)
							((EntityLivingBase)entity).setHealth(0);
						CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(player);
						ex.madnessLvl++;
					}
					lucky = !lucky;
				}
				break;
			}
			}
		}
		player.swingItem();
        return wandstack;
	 }
	
	private static MovingObjectPosition mop;
	
	private void teleporter(World world, EntityPlayer player) {
		MovingObjectPosition mop = BlockUtils.getTargetBlock(world, (player.prevPosX + (player.posX - player.prevPosX)),
                (player.prevPosY + (player.posY - player.prevPosY) + 1.62 - player.yOffset),
                (player.prevPosZ + (player.posZ - player.prevPosZ)),
                (player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw)),
                (player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch)), false, 256.0);
		if (mop != null) {
			int y;
			for (y = 255; world.getBlock(mop.blockX, y, mop.blockZ) == Blocks.air; y--);
			Block block = world.getBlock(mop.blockX, y, mop.blockZ);
			//player.addChatMessage(new ChatComponentText(mop.blockX + " " + y + " " + mop.blockZ));
			player.setPositionAndUpdate(mop.blockX + 0.5, y + 1, mop.blockZ + 0.5);
			for (int i = -2; i <= 2; i++)
				for (int j = -2; j <= 2; j++) {
					for (y = 255; world.getBlock(mop.blockX + i, y, mop.blockZ + j) == Blocks.air; y--);
					world.spawnEntityInWorld(new EntityLightningBolt(world, mop.blockX + i, y, mop.blockZ + j));
				}
			player.addPotionEffect(new PotionEffect(Potion.resistance.id, 40, 200));
		}
	}
	/*
	private int[][] getHightDiffForFireUp(int x, int y, int z, World world) {
		int[][] map = new int[5][5];
		boolean flag = false;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				flag = false;
				for (int k = 0; !flag; k++) {
					if (world.getBlock(x, y + k, z) != null && world.getBlock(x, y + k + 1, z) != null) {
						Material material = world.getBlock(x, y + k, z).getMaterial();
						Material material2 = world.getBlock(x, y + k + 1, z).getMaterial();
						if (material != Material.air && material != Material.water && material != Material.lava && (material2 == Material.air || material2 == Material.water || material2 == Material.lava)) {
							flag = true;
							map[i][j] = k;
						}
					}
					else
						flag = true;
					if (!flag) {
						if (world.getBlock(x, y - k, z) != null && world.getBlock(x, y - k + 1, z) != null) {
							Material material = world.getBlock(x, y - k, z).getMaterial();
							Material material2 = world.getBlock(x, y - k + 1, z).getMaterial();
							if (material != Material.air && material != Material.water && material != Material.lava && (material2 == Material.air || material2 == Material.water || material2 == Material.lava)) {
								flag = true;
								map[i][j] = -k;
							}
						}
						else
							flag = true;
					}
				}
			}
		}
		return map;
	}
	
	private ArrayList<int[]> getMapForFireUp(int x, int y, int z, int[][] map) {
		boolean[][] resmap = new boolean[5][5];
		boolean flg = true;
		ArrayList<int[]> result = new ArrayList<int[]>();
		
		for (int i = 0; i < 5; i++) 
			for (int j = 0; j < 5; j++)
				resmap[i][j] = false;
		resmap[2][2] = true;
		while (flg) {
			flg = false;
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (resmap[i][j]) {
						if (i != 0 && (map[i-1][j] - map[i][j] > -2 && map[i-1][j] - map[i][j] < 2)) {
							if (!resmap[i-1][j]) {
								resmap[i-1][j] = true;
								flg = true;
							}
						}
						if (i != 4 && (map[i+1][j] - map[i][j] > -2 && map[i+1][j] - map[i][j] < 2)) {
							if (!resmap[i+1][j]) {
								resmap[i+1][j] = true;
								flg = true;
							}
						}
						if (j != 0 && (map[i][j-1] - map[i][j] > -2 && map[i][j-1] - map[i][j] < 2)) {
							if (!resmap[i][j-1]) {
								resmap[i][j-1] = true;
								flg = true;
							}
						}
						if (j != 4 && (map[i][j+1] - map[i][j] > -2 && map[i][j+1] - map[i][j] < 2)) {
							if (!resmap[i][j+1]) {
								resmap[i][j+1] = true;
								flg = true;
							}
						}
						
					}
				}
			}
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (resmap[i][j]) {
					int[] coord = new int[2];
					coord[0] = x - 2 + i;
					coord[1] = z - 2 + j;
					result.add(coord);
				}
			}
		}
		return result;
	}

	private void fireUp(World world, EntityPlayer player) {
		int x = (int)player.posX;
		int z = (int)player.posZ;
		int y;
		for(y = (int)player.posY; world.getBlock(x, y, z).getMaterial() == Material.air; y--);
		int[][] yMap = getHightDiffForFireUp(x, y, z, world);
		ArrayList<int[]> map = getMapForFireUp(x, y, z, yMap);
		for (int[] i : map) {
			if (world.getBlock(i[0], y + yMap[i[0] - x + 2][i[1] - z + 2] + 1, i[1]) == Blocks.air) {
				world.setBlock(i[0], (int)(y + yMap[i[0] - x + 2][i[1] - z + 2] + 1), i[1], Init.BlockFire);
			}
		}
	}
*/
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		if(!world.isRemote && player.isSneaking()) {
			player.openGui(Main.instance, 1, world, (int)player.posX, (int)player.posY, (int)player.posZ);
		}
        return stack;
    }
	
	public void setInventory(ItemStack item, ItemStack stackList[])
    {
        NBTTagList var2 = new NBTTagList();
        for(int var3 = 0; var3 < stackList.length; var3++)
            if(stackList[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                stackList[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        //item.setTagInfo("Inventory", var2);
        item.getTagCompound().setTag("Inventory", var2);
    }
	
	public ItemStack[] getInventory(ItemStack stack)
    {
        ItemStack stackList[] = new ItemStack[6];
        NBTTagCompound compound = stack.getTagCompound();
        if(stack.hasTagCompound())
        {
        	NBTTagList items = compound.getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);

    		for (int i = 0; i < items.tagCount(); ++i)
    		{
    			// 1.7.2+ change to items.getCompoundTagAt(i)
    			NBTTagCompound item = (NBTTagCompound) items.getCompoundTagAt(i);
    			int slot = item.getInteger("Slot");

    			// Just double-checking that the saved slot index is within our inventory array bounds
    			if (slot >= 0 && slot < stackList.length) {
    				stackList[slot] = ItemStack.loadItemStackFromNBT(item);
    			}
    		}

        }
        return stackList;
    }
	
	public void addInformation(ItemStack itemstack, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		if(itemstack.hasTagCompound()) {
			int mode = itemstack.stackTagCompound.getShort("Mode");
			list.add(1, String.valueOf("Mode: " + eDictModes.values()[mode].toString()));
		}
	}

}
