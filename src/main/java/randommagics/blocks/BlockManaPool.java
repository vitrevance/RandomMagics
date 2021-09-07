package randommagics.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import randommagics.Init;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.wand.IWandHUD;

public class BlockManaPool extends Block implements ITileEntityProvider {

	public BlockManaPool() {
		super(Material.iron);
		this.setHardness(4);
		this.setCreativeTab(Init.TabRandomMagics);
		this.setBlockName("ManaPool");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileManaPool();
	}
	
}