package randommagics.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import randommagics.Init;

public class BlockRMOre extends BlockOre {
	
	private IIcon[] icons = new IIcon[2];
	
	public BlockRMOre() {
		this.setCreativeTab(Init.TabRandomMagics);
		this.setBlockName("RMOre");
		this.setHarvestLevel("pickaxe", 10, 0);
		this.setHarvestLevel("pickaxe", 3, 1);
		this.setHardness(30f);
	}
	
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
    }
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return this.icons[meta];
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		for (int i = 0; i < this.icons.length; i++)
			this.icons[i] = reg.registerIcon("randommagics:RMOre." + i);
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		if (metadata == 0)
			ret.add(new ItemStack(Init.ItemRMResource, 1, 25));
		if (metadata == 1)
			ret.add(new ItemStack(Init.ItemRMResource, 1, 26));
		return ret;
	}
}
