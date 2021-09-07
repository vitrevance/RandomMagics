package randommagics.customs;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import randommagics.EnchantmentInit;
import randommagics.Init;
import randommagics.enchantments.EnchantmentAOEAugment;
import randommagics.enchantments.EnchantmentsHelper;

public class TrashVoiderBlacklistRecipe implements IRecipe {
	
	public TrashVoiderBlacklistRecipe() {
	}
	
	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		if (inv.getStackInSlot(0) != null) {
			if (inv.getStackInSlot(0).getItem() == Init.ItemPickaxeOfTheLordOfTheEarth) {
				if (!EnchantmentsHelper.hasEnchantment(inv.getStackInSlot(0), EnchantmentInit.TrashVoiderAugment.effectId)) {
					return false;
				}
				if (inv.getStackInSlot(1) != null) {
					boolean isClearing = inv.getStackInSlot(1).getItem() == Items.ender_pearl;
					for (int i = 2; i < inv.getSizeInventory() && isClearing; i++) {
						if (inv.getStackInSlot(i) != null) {
							isClearing = false;
						}
					}
					if (isClearing)
						return true;
				}
				for (int i = 1; i < inv.getSizeInventory(); i++) {
					ItemStack slot = inv.getStackInSlot(i);
					if (slot != null) {
						Block b = Block.getBlockFromItem(slot.getItem());
						if (b == Blocks.air) {
							return false;
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		if (inv.getStackInSlot(0).getItem() == Init.ItemPickaxeOfTheLordOfTheEarth) {
			ItemStack ret = inv.getStackInSlot(0).copy();
			NBTTagCompound nbt = ret.getTagCompound();
			int count = nbt.getInteger("blacklistSize");
			
			if (inv.getStackInSlot(1) != null && inv.getStackInSlot(1).getItem() == Items.ender_pearl) {
				count = 0;
			}
			else {
				//if (!EnchantmentsHelper.hasEnchantment(inv.getStackInSlot(0), EnchantmentInit.TrashVoiderAugment.effectId)) {
				//}
				for (int i = 1; i < inv.getSizeInventory(); i++) {
					ItemStack slot = inv.getStackInSlot(i);
					if (slot != null) {
						Block b = Block.getBlockFromItem(slot.getItem());
						if (b != Blocks.air) {
							nbt.setInteger("blacklistid_" + count, Block.getIdFromBlock(b));
							nbt.setInteger("blacklistmeta_" + count, slot.getItemDamage());
							count++;
						}
					}
				}
			}
			nbt.setInteger("blacklistSize", count);
			return ret;
		}
		return null;
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
