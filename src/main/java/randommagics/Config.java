package randommagics;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config {
	
	public static Configuration config;
	
	public static int potionStuckInTimeId;
	public static int potionVoiceOfDeathId;
	public static int enchantmentDisintegrationId;
	public static boolean focusinfinityKillOwner;
	public static int enchantmentTriDimensionalAugmentId;
	public static int EnchantmentDistanceAugmentId;
	public static int EnchantmentCooldownReductionAugmentId;
	public static int EnchantmentAOEAugmentId;
	public static int TrashVoiderAugmentId;
	public static int MatterCondenserAugmentId;
	public static int maxTimeExpanderStack;
	
	public static void config(FMLPreInitializationEvent event) {
		try {
			config = new Configuration(event.getSuggestedConfigurationFile());
			config.load();
			//OTHER
			focusinfinityKillOwner = config.getBoolean("focus_infinity_KillOwner", "Other", true, "Default true");
			maxTimeExpanderStack = config.getInt("time_expander_max_stack", "Other", 4, 0, 5, "Default 4. If 0 - disabled");
			
			//POTIONS
			potionStuckInTimeId = config.getInt("stuck_in_time", "Potions", 25, 0, 255, "Default 25");
			potionVoiceOfDeathId = config.getInt("voice_of_death", "Potions", 26, 0, 255, "Default 26");
			
			//ENCHANTMENTS
			enchantmentDisintegrationId = config.getInt("Disintegration", "Enchantments", 100, -1, 255, "Set -1 to disable. Default 100");
			enchantmentTriDimensionalAugmentId = config.getInt("TriDimensionalAugment", "Enchantments", 101, 1, 255, "Default 101");
			EnchantmentDistanceAugmentId = config.getInt("DistanceAugment", "Enchantments", 102, 1, 255, "Default 102");
			EnchantmentCooldownReductionAugmentId = config.getInt("CooldownReductionAugment", "Enchantments", 103, 1, 255, "Default 103");
			EnchantmentAOEAugmentId = config.getInt("AOEAugment", "Enchantments", 104, 1, 255, "Default 104");
			TrashVoiderAugmentId = config.getInt("TrashVoiderAugment", "Enchantments", 105, 1, 255, "Default 105");
			MatterCondenserAugmentId = config.getInt("MatterCondenserAugment", "Enchantments", 106, 1, 255, "Default 106");
			
		}
		catch (Exception e) {
		}
		finally {
			if (config.hasChanged())
				config.save();
		}
	}

}