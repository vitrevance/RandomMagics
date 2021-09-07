package randommagics.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import randommagics.Init;

public class BlockLifeEssenceExtractor extends Block implements ITileEntityProvider {
	
	private IIcon icons[] = new IIcon[3];
	
	public BlockLifeEssenceExtractor() {
		super(Material.rock);
		setBlockName("LifeEssenceExtractor");
		setCreativeTab(Init.TabRandomMagics);
		setHardness(2.5F);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileLifeEssenceExtractor();
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if (side == meta)
			return icons[0];
		if (side == 1)
			return icons[2];
		return icons[1];
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icons[0] = reg.registerIcon("randommagics:LifeEssenceExtractor_work");
		icons[1] = reg.registerIcon("randommagics:LifeEssenceExtractor_side");
		icons[2] = reg.registerIcon("randommagics:LifeEssenceExtractor_top");
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entity, ItemStack stack) {
		int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		byte b0 = 2;
		if (l == 0)
        {
            b0 = 3;
        }

        if (l == 1)
        {
            b0 = 4;
        }

        if (l == 2)
        {
            b0 = 2;
        }

        if (l == 3)
        {
            b0 = 5;
        }
        world.setBlockMetadataWithNotify(x, y, z, b0, 0);
        ((TileLifeEssenceExtractor)world.getTileEntity(x, y, z)).setSide(b0);
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
	}
	
}