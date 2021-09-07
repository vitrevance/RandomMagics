package randommagics.customs;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.item.ItemStack;

public class AuraInfusionHelper {
	
	private static ArrayList<AuraInfusionRecipe> recipes = new ArrayList<AuraInfusionRecipe>();
	
	public static AuraInfusionRecipe addAuraInfusionrecipe(String research, int vis, int instabiliy, ItemStack output, ItemStack input, ItemStack ... ingredients) {
		if (input == null || output == null || findAuraInfusionRecipe(input, ingredients) != null)
			return null;
		AuraInfusionRecipe rec = new AuraInfusionRecipe(research, vis, instabiliy, output, input, ingredients);
		recipes.add(rec);
		return rec;
	}
	
	public static AuraInfusionRecipe findAuraInfusionRecipe(ItemStack input, ItemStack[] ingredients) {
		boolean is = false;
		for (AuraInfusionRecipe rec : recipes) {
			if (ItemStack.areItemStacksEqual(input, rec.input) && rec.ingredients.length == ingredients.length) {
				ItemStack[] ii = rec.ingredients.clone();
				for (int i = 0; i < ingredients.length; i++) {
					is = false;
					for (int j = 0; j < ingredients.length; j++) {
						if (ItemStack.areItemStacksEqual(ingredients[i], ii[j])) {
							ii[j] = null;
							is = true;
							break;
						}
					}
				}
				if (is)
					return rec;
			}
		}
		return null;
	}
}