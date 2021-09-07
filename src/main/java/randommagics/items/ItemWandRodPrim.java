package randommagics.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import randommagics.Init;

public class ItemWandRodPrim extends Item{

	public ItemWandRodPrim()
    {
		super();
		
		this.setTextureName("randommagics:wand_rod_primordial");
		this.setCreativeTab(Init.TabRandomMagics);
		this.setUnlocalizedName("ItemWandRodPrimal");
    }
}
