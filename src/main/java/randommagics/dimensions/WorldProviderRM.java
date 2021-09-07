package randommagics.dimensions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.ChunkProviderHell;
import thaumcraft.common.lib.world.dim.ChunkProviderOuter;

public class WorldProviderRM extends WorldProvider {
	
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerR.WorldChunkManagerCustom(1, WorldType.DEFAULT);
		//this.hasNoSky = true;
		this.isHellWorld = true;
		//this.isSkyColored();
		//this.isDaytime();
		this.dimensionId = DimensionRegistry.dimensionId;
	}
	
	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float par1, float par2) {
		return Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
	}
	
	public boolean isDaytime() {
		return true;
	}
	
	public boolean isSkyColored() {
		return true;
	}
	
	public boolean isSurfaceWorld() {
		return false;
	}
	
	public boolean canRespawnHere() {
		return false;
	}
	
	public IChunkProvider createChunkGeneration() {
		return new ChunkProviderModded(this.worldObj, this.worldObj.getSeed());
		//return new ChunkProviderHell(worldObj, getSeed());
	}

	@Override
	public String getDimensionName() {
		return "Brain";
	}
}
