package randommagics.rituals;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class EnchantmentRitualReciepe extends PowerRitualReciepe {
	
	public Enchantment enchant;
	public int lvl;
	
	public EnchantmentRitualReciepe(ItemStack input, int visamount, Enchantment result, int level, String... researhes) {
		super(input, visamount, null, researhes);
		this.enchant = result;
		type = Type.enchantment;
		this.lvl = level;
	}
}