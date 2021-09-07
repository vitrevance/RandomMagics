package randommagics.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import randommagics.Init;

public class BlockRitualPylon extends Block implements ITileEntityProvider {
	
	public BlockRitualPylon() {
		super(Material.glass);
		this.setCreativeTab(Init.TabRandomMagics);
		this.setHardness(3f);
		this.setBlockName("RitualPylon");
		this.setBlockTextureName("randommagics:RitualPylon");
		//this.setBlockBounds(0.1f, 0f, 0.1f, 0.1f, 2f, 0.1f);
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 0) {
			world.setBlock(x, y + 1, z, this, meta + 1, 3);
		}
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return world.getBlock(x, y, z).isReplaceable(world, x, y, z) && world.getBlock(x, y + 1, z).isReplaceable(world, x, y + 1, z);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block,
			int meta) {
		// TODO Auto-generated method stub
		super.breakBlock(world, x, y, z, block, meta);
		if (meta == 0) {
			world.setBlockToAir(x, y + 1, z);
		}
		else if (meta == 1) {
			world.setBlockToAir(x, y - 1, z);
		}
	}
	/*
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y,
			int z) {
		if (world.getBlockMetadata(x, y, z) == 0)
			return AxisAlignedBB.getBoundingBox(x + 0.1D, (double)y, z + 0.1D, x + 0.9D, y + 2D, z + 0.9D);
		else
			return AxisAlignedBB.getBoundingBox(x + 0.1D, y + 1D, z + 0.1D, x + 0.9D, y - 1D, z + 0.9D);
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y,
			int z) {
		return this.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	*/
	
	@Override
	public int getLightValue() {
		return 15;
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

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if (meta == 0)
			return new TileRitualPylon();
		return null;
	}
}
