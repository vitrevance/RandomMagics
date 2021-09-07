package randommagics.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import randommagics.Init;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.monster.EntityBrainyZombie;
import thaumcraft.common.entities.monster.boss.EntityCultistPortal;

public class BlockStableEldPortal extends Block{

	public BlockStableEldPortal() {
		super(Material.rock);
		this.setCreativeTab(Init.TabRandomMagics);
		this.setBlockName("stableEldPortal");
		this.setBlockTextureName("randommagics:StableEldPortal");
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(3.0F);
	}
	
	private boolean StructExists(World world, int x, int y, int z) {
		boolean tiles = true;
		for(int i = 2; i >= -2; i--)
			for(int j = 2; j >= -2; j--) {
				if(world.getBlock(x+i, y, z+j) != ConfigBlocks.blockCosmeticSolid || world.getBlockMetadata(x+i, y, z+j) != 1)
					if(!((i == 1 && j == 0) || (i == -1 && j == 0) || (i == 0 && j == 1) || (i == 0 && j == -1) || (i == 0 && j == 0)))
						tiles = false;
				
			}
		if(tiles && world.getBlock(x+1, y, z) == Init.RitualStone && world.getBlock(x-1, y, z) == Init.RitualStone && world.getBlock(x, y, z+1) == Init.RitualStone && world.getBlock(x, y, z-1) == Init.RitualStone)
			return true;
		return false;
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			ItemStack current = player.getCurrentEquippedItem();
			if(StructExists(world, x, y, z) && player.getCurrentEquippedItem() != null && current.getItem() == ConfigItems.itemEldritchObject && current.getItemDamage() == 0) {
				Entity boss = new EntityCultistPortal(world);
				boss.setPosition(x, y+1, z);
				world.spawnEntityInWorld(boss);
				player.destroyCurrentEquippedItem();
			}
		}
		return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
	}
}
