package randommagics.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import randommagics.Init;

public class BlockItemDuplicator extends Block implements ITileEntityProvider {
	
	private IIcon icons[] = new IIcon[3];
	
	public BlockItemDuplicator() {
		super(Material.rock);
		setBlockName("ItemDuplicator");
		setCreativeTab(Init.TabRandomMagics);
		setHardness(2.5F);
		setBlockTextureName("randommagics:LifeEssenceExtractor_side");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileItemDuplicator();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int meta, float p1, float p2, float p3) {
		if (player.getCurrentEquippedItem() != null) {
			((TileItemDuplicator)world.getTileEntity(x, y, z)).slots[0] = player.getCurrentEquippedItem().copy();
		}
		else {
			((TileItemDuplicator)world.getTileEntity(x, y, z)).slots[0] = null;
		}
		return true;
	}
	
	public int getRenderType()
    {
        return Init.RenderID;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }
	
}