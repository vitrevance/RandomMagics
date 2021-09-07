package randommagics.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import randommagics.Init;
import randommagics.curses.CurseRegistry;
import randommagics.curses.CurseShortLife;

public class ItemCloudCharm extends Item {
	
	public ItemCloudCharm() {
		setCreativeTab(Init.TabRandomMagics);
		setUnlocalizedName("ItemCloudCharm");
		setTextureName("randommagics:CloudCharm");
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int p_77648_4_,
			int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		if (stack.getTagCompound() == null) {
			NBTTagCompound nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		stack.getTagCompound().setBoolean("active", !(stack.getTagCompound().getBoolean("active")));
		return true;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_,
			boolean p_77663_5_) {
		super.onUpdate(stack, world, entity, p_77663_4_, p_77663_5_);
		if (entity != null)
		if (stack.getTagCompound() != null && stack.getTagCompound().getBoolean("active")) {
			if (entity instanceof EntityPlayer) {
				//if (entity.ticksExisted % 20 == 0) {
					EntityPlayer player = (EntityPlayer)entity;
					//CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(player);
					//System.out.println("message: " + ex.cloud);
					for (int i = 0; i < 5; i++)
						for (int j = 0; j < 5; j++)
							if (player.worldObj.blockExists((int)player.posX + i - 2, 254, (int)player.posZ + j - 2) && player.worldObj.isAirBlock((int)player.posX + i - 2, 254, (int)player.posZ + j - 2))
								player.worldObj.setBlock((int)player.posX + i - 2, 254, (int)player.posZ + j - 2, Init.BlockCloud);
				//}
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
		if (stack.hasTagCompound()) {
			list.add(1, (stack.getTagCompound().getBoolean("active") ? "Active" : "Inactive"));
		}
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		if (stack.hasTagCompound())
			return stack.getTagCompound().getBoolean("active");
		return false;
	}
	
	@Override
	public int getItemEnchantability() {
		return 0;
	}
}
