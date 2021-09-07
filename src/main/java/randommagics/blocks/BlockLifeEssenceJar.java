package randommagics.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import randommagics.Init;

public class BlockLifeEssenceJar extends Block implements ITileEntityProvider {
	
	public BlockLifeEssenceJar() {
		super(Material.glass);
		setStepSound(soundTypeGlass);
		setCreativeTab(Init.TabRandomMagics);
		setHardness(0.5F);
		setBlockName("LifeEssenceJar");
		setBlockTextureName("randommagics:TileLifeEssenceJar_side");
		setBlockBounds(0.2f, 0.0f, 0.2f, 0.8f, 0.8f, 0.8f);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y,
			int z) {
		return AxisAlignedBB.getBoundingBox(x + 0.2D, (double)y, z + 0.2D, x + 0.8D, y + 0.8D, z + 0.8D);
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int x, int y,
			int z) {
		return AxisAlignedBB.getBoundingBox(x + 0.2D, (double)y, z + 0.2D, x + 0.8D, y + 0.8D, z + 0.8D);
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		ItemStack ret = new ItemStack(this);
		if (world.getTileEntity(x, y, z) instanceof TileEssenceContainer) {
			TileEssenceContainer te = (TileEssenceContainer)world.getTileEntity(x, y, z);
			ret.setTagCompound(new NBTTagCompound());
			ret.getTagCompound().setInteger("cap", te.getCapacity());
			ret.getTagCompound().setInteger("am", te.getEssence());
		}
		return ret;
	}
	
	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
		return willHarvest || super.removedByPlayer(world, player, x, y, z, willHarvest);
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer e, int x, int y,
			int z, int meta) {
		super.harvestBlock(world, e, x, y, z, meta);
		world.setBlockToAir(x, y, z);
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		if (stack.hasTagCompound() && world.getTileEntity(x, y, z) instanceof TileEssenceContainer) {
			TileEssenceContainer te = (TileEssenceContainer)world.getTileEntity(x, y, z);
			te.readCustomNBT(stack.getTagCompound());
		}
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ItemStack ret = new ItemStack(this);
		ret.setTagCompound(new NBTTagCompound());
		TileEssenceContainer tile = (TileEssenceContainer)world.getTileEntity(x, y, z);
		if (tile != null)
			tile.writeCustomNBT(ret.getTagCompound());
		ArrayList<ItemStack> rett = new ArrayList<ItemStack>();
		rett.add(ret);
		return rett;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileLifeEssenceJar();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return Init.RenderID;
	}
}