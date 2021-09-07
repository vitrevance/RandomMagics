package randommagics.items;

import java.util.List;

import baubles.common.network.PacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import randommagics.Init;
import randommagics.customs.RandomUtils;
import randommagics.packets.RmNetworkRegistry;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.items.wands.foci.ItemFocusTrade;
import thaumcraft.common.lib.network.fx.PacketFXBlockSparkle;

public class FocusUnequalTrade extends ItemFocusBasic {
	
	//public IIcon iconOrnament;
	//public IIcon depthIcon;
	
	public FocusUnequalTrade() {
		setCreativeTab(Init.TabRandomMagics);
		setUnlocalizedName("fociUnequalTrade");
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		this.icon = ir.registerIcon("randommagics:unequaltradeF");
		//this.iconOrnament = ir.registerIcon("randommagics:mindmutOrn");
		//this.depthIcon = ir.registerIcon("randommagics:mindmutIn");
	}
	
	public int getFocusColor(ItemStack focusstack) {
		int a=100;
		int r=209;
		int g=253;
		int b=253;
		return  a << 24 | r << 16 | g << 8 | b << 0;
	}
	
	@Override
	public String getSortingHelper(ItemStack focusstack) {
		return "Unequal Trade";
	}
	
	@Override
	public int getActivationCooldown(ItemStack focusstack) {
		return 5;
	}
	
	@Override
	public AspectList getVisCost(ItemStack focusstack) {
		return new AspectList().add(Aspect.ENTROPY, 20).add(Aspect.AIR, 10).add(Aspect.EARTH, 15);
	}
	
	@Override
	public ItemStack onFocusRightClick(ItemStack wandstack, World world, EntityPlayer player,
			MovingObjectPosition movingobjectposition) {
		player.swingItem();
		onPerform(wandstack, world, player, movingobjectposition);
		return wandstack;
	}
	
	public ItemStack onPerform(ItemStack wandstack, World world, EntityPlayer player,
			MovingObjectPosition movingobjectposition) {
		if (movingobjectposition != null && movingobjectposition.typeOfHit == MovingObjectType.BLOCK) {
			int x = movingobjectposition.blockX;
			int y = movingobjectposition.blockY;
			int z = movingobjectposition.blockZ;
			Block block = world.getBlock(x, y, z);
			if (block.getBlockHardness(world, x, y, z) >= 0F) {
				int meta = world.getBlockMetadata(x, y, z);
				ItemWandCasting wand = (ItemWandCasting)wandstack.getItem();
				wand.consumeAllVis(wandstack, player, getVisCost(wandstack), true, false);
				if (!world.isRemote) {
					block.dropBlockAsItem(world, x, y, z, meta, 0);
					world.setBlockToAir(x, y, z);
					world.playSoundAtEntity(player, "thaumcraft:wand", 0.25f, 1);
					
				}
				PacketFXBlockSparkle fx = new PacketFXBlockSparkle(x, y, z, this.getFocusColor(wandstack));
				PacketHandler.INSTANCE.sendToAllAround(fx, new TargetPoint(world.provider.dimensionId, x, y, z, 32));
				List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1));
				for (EntityItem it : entities) {
					it.setPosition(player.posX, player.posY, player.posZ);
					it.delayBeforeCanPickup = 0;
				}
			}
		}
		return wandstack;
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		MovingObjectPosition mop = RandomUtils.getTargetBlock(entityLiving.worldObj, entityLiving, false);
		onPerform(stack, entityLiving.worldObj, (EntityPlayer)entityLiving, mop);
		return super.onEntitySwing(entityLiving, stack);
	}
}
