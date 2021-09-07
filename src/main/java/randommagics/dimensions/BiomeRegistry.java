package randommagics.dimensions;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class BiomeRegistry {
	
	public static void mainRegistry() {
		initializeBiome();
		registerBiome();
	}
	
	public static BiomeGenBase biomeBrain;
	
	public static void initializeBiome() {
		biomeBrain = new BiomeGenBrain(110).setBiomeName("Brain");
	}
	
	public static void registerBiome() {
		BiomeDictionary.registerBiomeType(biomeBrain, Type.END);
		//BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(biomeBrain, 100000000));
		//BiomeManager.addSpawnBiome(biomeBrain);
	}
}
