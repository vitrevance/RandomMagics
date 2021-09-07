package randommagics.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import randommagics.Init;

public class ItemRMOre extends ItemBlock {

	public ItemRMOre(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	public String getUnlocalizedName(ItemStack par1ItemStack){
        return (new StringBuilder()).append(super.getUnlocalizedName()).append(".").append(par1ItemStack.getItemDamage()).toString();
    }
	
	@Override
	public int getMetadata(int meta) {
		return meta;
	}

}
