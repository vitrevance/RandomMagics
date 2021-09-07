package randommagics.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import randommagics.Init;

public class BlockLifeStealingPlant extends Block implements ITileEntityProvider {
	
	public BlockLifeStealingPlant() {
		super(Material.leaves);
		setBlockName("LifeStealingPlant");
		setCreativeTab(Init.TabRandomMagics);
		setHardness(0.2F);
		stepSound = soundTypeGrass;
		setBlockTextureName("randommagics:LifeStealingPlant");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileLifeStealingPlant();
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return 6;//Init.RenderID;
	}
	
	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
		return false;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z,
			Entity entity) {
		if (entity instanceof EntityLivingBase && entity.ticksExisted % 20 == 0) {
			((TileLifeStealingPlant)world.getTileEntity(x, y, z)).stealHealth((EntityLivingBase)entity);
			world.playSoundAtEntity(entity, "random.fizz", 0.5F, 5F);
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_,
			int p_149668_4_) {
		return null;
	}
	
	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return true;
	}
	
	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return 250;
	}
	
	@Override
	public boolean isFoliage(IBlockAccess world, int x, int y, int z) {
		return false;
	}
}
