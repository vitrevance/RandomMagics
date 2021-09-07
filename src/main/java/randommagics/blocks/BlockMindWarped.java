package randommagics.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import randommagics.Init;

public class BlockMindWarped extends Block{
	
	public BlockMindWarped() {
		super(Material.cloth);
		this.setBlockName("BlockMindWarped");
		this.setCreativeTab(Init.TabRandomMagics);
		this.setBlockTextureName("randommagics:MindWarped");
		this.setBlockUnbreakable();
		this.setResistance(100000);
		//this.setHardness(3.0F);
	}
}
