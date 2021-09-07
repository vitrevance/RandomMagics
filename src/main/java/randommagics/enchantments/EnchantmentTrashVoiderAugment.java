package randommagics.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import randommagics.Config;
import randommagics.Init;

public class EnchantmentTrashVoiderAugment extends Enchantment {
	
	public EnchantmentTrashVoiderAugment() {
		super(Config.TrashVoiderAugmentId, 0, EnumEnchantmentType.digger);
		setName("TrashVoiderAugment");
	}
	
	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() == Init.ItemPickaxeOfTheLordOfTheEarth;
	}
	
	@Override
	public int getMaxLevel() {
		return 1;
	}
}
