package randommagics.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import randommagics.Config;
import randommagics.Init;

public class EnchantmentTriDimensionalAugment extends Enchantment {

	public EnchantmentTriDimensionalAugment() {
		super(Config.enchantmentTriDimensionalAugmentId, 0, EnumEnchantmentType.digger);
		setName("TriDimensionalAugment");
	}
	
	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() == Init.ItemPickaxeOfTheLordOfTheEarth;
	}
}
