package randommagics.customs;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import randommagics.blocks.ItemDevice;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;

public class ExtendedInfusionMatrixRecipe implements IRecipe {
	
	public ExtendedInfusionMatrixRecipe() {
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		if (inv.getStackInSlot(0) == null && inv.getStackInSlot(2) == null && inv.getStackInSlot(6) == null && inv.getStackInSlot(8) == null) {
			ItemStack temp = new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6);
			if (inv.getStackInSlot(1) != null && inv.getStackInSlot(1).getItem() == temp.getItem() && inv.getStackInSlot(1).getItemDamage() == temp.getItemDamage()) {
				if (inv.getStackInSlot(7) != null && inv.getStackInSlot(7).getItem() == temp.getItem() && inv.getStackInSlot(7).getItemDamage() == temp.getItemDamage()) {
					if (inv.getStackInSlot(4) != null && inv.getStackInSlot(4).getItem() == ConfigItems.itemEldritchObject && inv.getStackInSlot(4).getItemDamage() == 3) {
						if (inv.getStackInSlot(3) != null && inv.getStackInSlot(3).getItem() instanceof ItemDevice && inv.getStackInSlot(3).getItemDamage() == 0) {
							if (inv.getStackInSlot(5) != null && inv.getStackInSlot(3).getItem() instanceof ItemDevice && inv.getStackInSlot(5).getItemDamage() == 0) {
								if (inv.getStackInSlot(3).hasTagCompound() && inv.getStackInSlot(5).hasTagCompound()) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack ret = new ItemStack(GameRegistry.findItem("randommagics", "Device"), 1, 0);
		int a = inv.getStackInSlot(3).getTagCompound().getInteger("speedMod");
		int b = inv.getStackInSlot(5).getTagCompound().getInteger("speedMod");
		ret.setTagCompound(new NBTTagCompound());
		ret.getTagCompound().setInteger("speedMod", a + b);
		return ret;
	}

	@Override
	public int getRecipeSize() {
		return 0;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

}
