package randommagics.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import randommagics.Init;
import randommagics.curses.CurseRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;

public class ItemCursedScroll extends Item {
	
	public ItemCursedScroll() {
		super();
		
		this.setTextureName("randommagics:CursedScroll");
		this.setCreativeTab(Init.TabRandomMagics);
		this.setUnlocalizedName("ItemCursedScroll");
		this.setMaxStackSize(1);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World p_77663_2_, Entity p_77663_3_, int p_77663_4_,
			boolean p_77663_5_) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setString("curse", CurseRegistry.getRandomCurseID());
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List l, boolean p_77624_4_) {
		if (stack.hasTagCompound()) {
			if (!stack.getTagCompound().getString("curse").isEmpty()) {
				l.add(StatCollector.translateToLocal("curse." + stack.getTagCompound().getString("curse")));
			}
		}
	}
}
