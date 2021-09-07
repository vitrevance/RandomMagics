package randommagics;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import randommagics.customs.ExtendedInfusionMatrixRecipe;
import randommagics.customs.TrashVoiderBlacklistRecipe;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;

public class Recipes {

	public static void Init() {
		
		GameRegistry.addSmelting(new ItemStack(Init.PrimBlend), new ItemStack(Init.PrimIngot), 1.5F);
		GameRegistry.addRecipe(new ExtendedInfusionMatrixRecipe());
		GameRegistry.addRecipe(new TrashVoiderBlacklistRecipe());
		GameRegistry.addShapedRecipe(new ItemStack(Init.BlockStupidAnvil), new Object[] {
				"DAD", "DBD", "TDT",
				'D', new ItemStack(Blocks.dirt),
				'A', new ItemStack(Blocks.anvil, 1, 2),
				'B', new ItemStack(Blocks.diamond_block),
				'T', new ItemStack(Init.ItemRMResource, 1, 25)
		});
		GameRegistry.addShapelessRecipe(new ItemStack(Init.ItemInfoPaper), new Object[] {new ItemStack(Items.paper), new ItemStack(ConfigItems.itemResource, 1, 14)});
	}
}