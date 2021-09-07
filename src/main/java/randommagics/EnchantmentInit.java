package randommagics;

import org.lwjgl.Sys;

import net.minecraft.enchantment.Enchantment;
import randommagics.enchantments.EnchantmentAOEAugment;
import randommagics.enchantments.EnchantmentCooldownReductionAugment;
import randommagics.enchantments.EnchantmentDisintegration;
import randommagics.enchantments.EnchantmentDistanceAugment;
import randommagics.enchantments.EnchantmentTrashVoiderAugment;
import randommagics.enchantments.EnchantmentTriDimensionalAugment;
import randommagics.enchantments.EnchantmentMatterCondenser;

public class EnchantmentInit {
	
	public static Enchantment Disintegration;
	public static Enchantment TriDimensionalAugment;
	public static Enchantment DistanceAugment;
	public static Enchantment CooldownReductionAugment;
	public static Enchantment AOEAugment;
	public static Enchantment TrashVoiderAugment;
	public static Enchantment MatterCondenser;
	
	public static void init() {
		if (Config.enchantmentDisintegrationId != -1)
			Disintegration = new EnchantmentDisintegration();
		TriDimensionalAugment = new EnchantmentTriDimensionalAugment();
		DistanceAugment = new EnchantmentDistanceAugment();
		CooldownReductionAugment = new EnchantmentCooldownReductionAugment();
		AOEAugment = new EnchantmentAOEAugment();
		TrashVoiderAugment = new EnchantmentTrashVoiderAugment();
		MatterCondenser = new EnchantmentMatterCondenser();
	}
}
