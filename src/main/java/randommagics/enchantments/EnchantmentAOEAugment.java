package randommagics.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import randommagics.Config;
import randommagics.Init;

public class EnchantmentAOEAugment extends Enchantment {
	
	public EnchantmentAOEAugment() {
		super(Config.EnchantmentAOEAugmentId, 0, EnumEnchantmentType.digger);
		setName("AOEAugment");
	}
	
	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() == Init.ItemPickaxeOfTheLordOfTheEarth;
	}
	
	@Override
	public int getMaxLevel() {
		return 10;
	}
}
