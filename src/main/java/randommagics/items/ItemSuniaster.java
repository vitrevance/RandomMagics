package randommagics.items;

import net.minecraft.item.Item;
import randommagics.Init;

public class ItemSuniaster extends Item {
	
	public ItemSuniaster() {
		super();
		this.setCreativeTab(Init.TabRandomMagics);
		this.setUnlocalizedName("ItemSuniaster");
		this.setTextureName("randommagics:Suniaster");
	}
}