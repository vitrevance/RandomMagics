package randommagics.dimensions;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import randommagics.Init;
import thaumcraft.common.config.ConfigBlocks;

public class BiomeGenBrain extends BiomeGenBase {

	public BiomeGenBrain(int i) {
		super(i);
		this.enableRain = false;
		this.heightVariation = 0.0F;
		spawnableCreatureList.clear();
		spawnableMonsterList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		topBlock = Init.BlockMindWarped;
		fillerBlock = Init.BlockMindWarped;
		flowers.clear();
		this.setHeight(new BiomeGenBase.Height(0.0F, 0.0F));
		//theBiomeDecorator = new BiomeDecoratorCustom();
	}
	
	@Override
	public void genTerrainBlocks(World world, Random rand, Block[] blocks, byte[] b1, int par1, int par2, double par3) {
		/*
		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i] != null && blocks[i] != Blocks.air)
				blocks[i] = Init.BlockMindWarped;
		}
		super.genTerrainBlocks(p_150573_1_, p_150573_2_, blocks, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
		*/
		boolean flag = true;
        Block block = this.topBlock;
        byte b0 = (byte)(this.field_150604_aj & 255);
        Block block1 = this.fillerBlock;
        int l = (int)(par3 / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int i1 = par1 & 15;
        int j1 = par2 & 15;
        int k1 = blocks.length / 256;

        for (int l1 = 255; l1 >= 0; --l1)
        {
            int i2 = (j1 * 16 + i1) * k1 + l1;

            if (l1 <= 0 + rand.nextInt(5))
            {
                blocks[i2] = Blocks.bedrock;
            }
            else
            {
                Block block2 = blocks[i2];

                if (block2 != null && block2.getMaterial() != Material.air)
                {
                            block = this.topBlock;
                            b0 = (byte)(this.field_150604_aj & 255);
                            block1 = this.fillerBlock;
                            
                            if (block2.getMaterial() == Material.water)
                            {
                            	block = ConfigBlocks.blockFluidDeath;
                            	b0 = 0;
                            }

                            if (l1 >= 62)
                            {
                                blocks[i2] = block;
                                b1[i2] = b0;
                            }
                            else
                            {
                                blocks[i2] = block1;
                            }
                }
            }
        }
	}
	
	@Override
	public void decorate(World p_76728_1_, Random p_76728_2_, int p_76728_3_, int p_76728_4_) {
		//theBiomeDecorator.generateLakes = false;
		//theBiomeDecorator.decorateChunk(p_76728_1_, p_76728_2_, this, p_76728_3_, p_76728_4_);
	}

}
