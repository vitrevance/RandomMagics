package randommagics.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import randommagics.Config;

public class EnchantmentDisintegration extends Enchantment {

	public EnchantmentDisintegration() {
		super(Config.enchantmentDisintegrationId, 0, EnumEnchantmentType.weapon);
		setName("Disintegration");
	}
	
	@Override
	public boolean canApplyTogether(Enchantment p_77326_1_) {
		return true;
	}
	
	@Override
	public int getMinEnchantability(int p_77321_1_) {
		return -1;//Integer.MAX_VALUE - 1;
	}
	
	@Override
	public int getMaxEnchantability(int p_77317_1_) {
		return -1;
	}

}
