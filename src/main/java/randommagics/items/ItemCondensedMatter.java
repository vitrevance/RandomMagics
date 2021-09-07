package randommagics.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import randommagics.Init;

public class ItemCondensedMatter extends Item {

	public ItemCondensedMatter() {
		super();
		this.setTextureName("randommagics:CondensedMaterial");
		this.setUnlocalizedName("ItemCondensedMaterial");
		this.setMaxStackSize(1);
	}
	
	public static void loadArray(ItemStack stack, ArrayList<ItemStack> list) {
		NBTTagCompound nbt = new NBTTagCompound();
		int n = 0;
		for (ItemStack i : list) {
			NBTTagCompound tmp = new NBTTagCompound();
			i.writeToNBT(tmp);
			tmp.setInteger("myCount", i.stackSize);
			nbt.setTag("" + n, tmp);
			n++;
		}
		nbt.setInteger("index", n - 1);
		stack.setTagCompound(nbt);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		int i = stack.getTagCompound().getInteger("index");
		if (i < 0) {
			stack.stackSize = 0;
			return stack;
		}
		EntityItem e = new EntityItem(world, player.posX, player.posY, player.posZ);
		ItemStack con = ItemStack.loadItemStackFromNBT(stack.getTagCompound().getCompoundTag("" + i));
		con.stackSize = stack.getTagCompound().getCompoundTag("" + i).getInteger("myCount");
		stack.getTagCompound().setInteger("index", i - 1);
		e.setEntityItemStack(con);
		if (!world.isRemote)
			world.spawnEntityInWorld(e);
		return stack;
	}
}
