package randommagics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import randommagics.curses.CurseRegistry;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;

public class TabRandomMagics extends CreativeTabs{

	public static TabRandomMagics INSTANCE;
	
	public ArrayList<ItemStack> creativeTabQueue = new ArrayList<ItemStack>();
	
	ArrayList list = new ArrayList();
	
	public TabRandomMagics(int id, String lable) {
		super(id, lable);
		//addStuff();
	}

	@Override
	public Item getTabIconItem() {
		return Init.ItemWandRodPrimal;
	}
	
	public void displayAllReleventItems (List l) {
		l.addAll(list);
		super.displayAllReleventItems(l);
	}
	
	public void addStuff() {
		if (list == null)
			return;
		
		ItemStack wand = new ItemStack(ConfigItems.itemWandCasting);
		ItemWandCasting wandCasting = (ItemWandCasting) wand.getItem();

		wandCasting.setCap(wand, Init.WAND_CAP_BLKHOLE);
		wandCasting.setRod(wand, Init.WAND_ROD_PRIMAL);
		wandCasting.storeAllVis(wand, new AspectList().add(Aspect.AIR, 1000000).add(Aspect.WATER, 1000000).add(Aspect.EARTH, 1000000).add(Aspect.ORDER, 1000000).add(Aspect.ENTROPY, 1000000).add(Aspect.FIRE, 1000000));
		GameRegistry.registerCustomItemStack("Wan", wand);
		list.add(wand);
		
		ItemStack filleldifeessencejar = new ItemStack(Init.BlockLifeEssenceJar, 1, 0);
		filleldifeessencejar.setTagCompound(new NBTTagCompound());
		filleldifeessencejar.getTagCompound().setInteger("am", 64);
		filleldifeessencejar.getTagCompound().setInteger("cap", 64);
		GameRegistry.registerCustomItemStack("FilledLifeEssenceJar", filleldifeessencejar);
		list.add(filleldifeessencejar);
		
		for (String c : CurseRegistry.registry.keySet()) {
			ItemStack scrolls = new ItemStack(Init.ItemCursedScroll);
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("curse", c);
			scrolls.setTagCompound(nbt);
			GameRegistry.registerCustomItemStack("CursedScroll_" + c, scrolls);
			list.add(scrolls);
		}
			
	}

}
