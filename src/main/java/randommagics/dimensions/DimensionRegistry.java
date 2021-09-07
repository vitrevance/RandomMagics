package randommagics.dimensions;

import net.minecraftforge.common.DimensionManager;

public class DimensionRegistry {
	
	public static void mainRegistry() {
		registerDimension();
	}
	
	public static final int dimensionId = -47;
	
	public static void registerDimension() {
		DimensionManager.registerProviderType(dimensionId, WorldProviderRM.class, true);
		DimensionManager.registerDimension(dimensionId, dimensionId);
	}
}
