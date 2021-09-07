package randommagics.enchantments;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import randommagics.Config;

public class EnchantmentsHelper {
	
	public static boolean hasEnchantment(ItemStack stack, int id) {
		return EnchantmentHelper.getEnchantmentLevel(id, stack) > 0;
	}
}
