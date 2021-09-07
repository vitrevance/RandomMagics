package randommagics.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import randommagics.Config;
import randommagics.Init;

public class EnchantmentCooldownReductionAugment extends Enchantment {

	public EnchantmentCooldownReductionAugment() {
		super(Config.EnchantmentCooldownReductionAugmentId, 0, EnumEnchantmentType.digger);
		setName("CooldownReductionAugment");
	}
	
	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() == Init.ItemPickaxeOfTheLordOfTheEarth;
	}
	
	@Override
	public int getMaxLevel() {
		return 127;
	}
}
