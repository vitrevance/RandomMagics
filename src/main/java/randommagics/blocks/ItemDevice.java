package randommagics.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemDevice extends ItemBlock {

	public ItemDevice(Block block) {
		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	public int getMetadata(int par1)
    {
        return par1;
    }
	
	public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return (new StringBuilder()).append(super.getUnlocalizedName()).append(".").append(par1ItemStack.getItemDamage()).toString();
    }
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		super.addInformation(stack, player, list, bool);
		if (stack.getItemDamage() == 0 && stack.hasTagCompound()) {
			list.add("Speed mod: " + stack.getTagCompound().getInteger("speedMod"));
		}
	}
}
