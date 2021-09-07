package randommagics.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import randommagics.Init;

public class ItemLifeEssenceJar extends ItemBlock {

	public ItemLifeEssenceJar(Block block) {
		super(block);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		super.addInformation(stack, player, list, bool);
		if (stack.getItemDamage() == 0 && stack.hasTagCompound()) {
			list.add("Amount: " + stack.getTagCompound().getInteger("am"));
		}
	}
}