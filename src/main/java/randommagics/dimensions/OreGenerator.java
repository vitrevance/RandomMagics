package randommagics.dimensions;

import java.util.Collection;
import java.util.Random;

import akka.util.Collections;
import cpw.mods.fml.client.IModGuiFactory.RuntimeOptionGuiHandler;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import randommagics.Init;
import scala.collection.immutable.HashSet;

public class OreGenerator implements IWorldGenerator {
	
	public static final int ID = 1352;
	
	public OreGenerator() {
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		if (world.provider.dimensionId == 0) {
			this.runGenerator(Init.BlockRMOre, 0, 1, random.nextInt() % 5, 0, 16, new Block[] {Blocks.stone}, world, random, chunkX, chunkZ);
			this.runGenerator(Init.BlockRMOre, 1, 1, 8, 8, 24, new Block[] {Blocks.stone}, world, random, chunkX, chunkZ);
		}
	}
	
	private void runGenerator(Block blockToGen, int blockMeta, int blockAmount,  int chancesToSpawn, int minHeight, int maxHeight, Block[] blocksToReplace, World world, Random rand, int chunk_X, int chunk_Z) {

        if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
        	return;
        
        for (Block blockToReplace : blocksToReplace) {
	        int heightdiff = maxHeight - minHeight + 1;
	
	        for (int i = 0; i < chancesToSpawn / blocksToReplace.length; i++) {
	
	            int x = chunk_X * 16 +rand.nextInt(16);
	            int y = minHeight + rand.nextInt(heightdiff);
	            int z = chunk_Z * 16 + rand.nextInt(16);
	            
	            if (world.getBlock(x, y, z) == blockToReplace) {
	            	generate(blockToGen, blockMeta, blockAmount, world, rand, x, y, z);
	            }
	        }
        }
    }
	
	private void generate(Block block, int meta, int size, World world, Random rand, int x, int y, int z) {
		if (size <= 0)
			return;
		if (world.getBlock(x, y, z) == block && world.getBlockMetadata(x, y, z) == meta) {
			generate(block, meta, size, world, rand, x + rand.nextInt(3) - 1, y + rand.nextInt(3) - 1, z + rand.nextInt(3) - 1);
		}
		else {
			world.setBlock(x, y, z, block, meta, 2);
			if (size - 1 > 0)
				generate(block, meta, size - 1, world, rand, x + rand.nextInt(3) - 1, y + rand.nextInt(3) - 1, z + rand.nextInt(3) - 1);
		}
	}
}
