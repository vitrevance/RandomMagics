package randommagics.items;

import net.minecraft.item.Item;
import randommagics.Init;

public class ItemBlkHoleCap extends Item {
	
	public ItemBlkHoleCap()
    {
		super();
		
		this.setTextureName("randommagics:wand_cap_blkhole");
		this.setCreativeTab(Init.TabRandomMagics);
		this.setUnlocalizedName("ItemBlkHoleCap");
    }
}
