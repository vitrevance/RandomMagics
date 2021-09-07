package randommagics.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import randommagics.Init;

public class BlockCloud extends Block implements ITileEntityProvider{

	public BlockCloud() {
		super(Material.coral);
		//setCreativeTab(Init.TabRandomMagics);
		setBlockName("Cloud");
		setBlockUnbreakable();
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileCloud();
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return true;
	}
	
	@Override
	public int getRenderType() {
		return Init.RenderID;
	}
	
}
