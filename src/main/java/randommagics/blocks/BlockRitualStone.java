package randommagics.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import randommagics.Init;

public class BlockRitualStone extends Block{
	
	public BlockRitualStone()
	{
		super(Material.rock);
		this.setBlockName("ritualStone");
		this.setCreativeTab(Init.TabRandomMagics);
		this.setBlockTextureName("randommagics:RitualStone");
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(3.0F);
	}
}

