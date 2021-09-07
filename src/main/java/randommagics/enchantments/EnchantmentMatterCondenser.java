package randommagics.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import randommagics.Config;
import randommagics.Init;

public class EnchantmentMatterCondenser extends Enchantment {

	public EnchantmentMatterCondenser() {
		super(Config.MatterCondenserAugmentId, 0, EnumEnchantmentType.digger);
		this.setName("MatterCondenserAugment");
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
