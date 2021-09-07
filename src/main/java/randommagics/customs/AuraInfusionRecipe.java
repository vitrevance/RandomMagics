package randommagics.customs;

import net.minecraft.item.ItemStack;

public class AuraInfusionRecipe {
	
	public ItemStack input, output;
	public ItemStack[] ingredients;
	public int vis;
	public String research;
	public int instability;
	
	public AuraInfusionRecipe(String research, int vis, int instability, ItemStack output, ItemStack input, ItemStack[] ingredients) {
		this.input = input;
		this.output = output;
		this.ingredients = ingredients;
		this.vis = vis;
		this.research = research;
		this.instability = instability;
	}
}