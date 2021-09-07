package randommagics.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import randommagics.Init;

public class ItemSoulBinder extends Item {
	
	private IIcon[] icons = new IIcon[2];
	
	public ItemSoulBinder() {
		super();
		this.setCreativeTab(Init.TabRandomMagics);
		this.setMaxStackSize(1);
		this.setUnlocalizedName("ItemSoulBinder");
		this.setTextureName("randommagics:SoulBinder_0");
	}
	
	public void registerIcons(IIconRegister reg) {
	    this.icons[0] = reg.registerIcon("randommagics:SoulBinder_0");
	    this.icons[1] = reg.registerIcon("randommagics:SoulBinder_1");
	}
	
	public IIcon getIcon(ItemStack stack, int renderPass) {
		if(stack.hasTagCompound())
			return icons[1];
		else
			return icons[0];
    }
	
	public IIcon getIconIndex(ItemStack stack) {
		if(stack.hasTagCompound())
			return icons[1];
		else
			return icons[0];
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player.isSneaking() && !stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setString("bind", player.getCommandSenderName());
			player.swingItem();
		}
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean f) {
		if (stack.hasTagCompound()) {
			if (stack.getTagCompound().getString("bind").length() > 0) {
				list.add("Bound to " + stack.getTagCompound().getString("bind"));
			}
		}
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if (entity instanceof EntityPlayer && !stack.hasTagCompound() && player.worldObj.rand.nextFloat() < 0.005f) {
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setString("bind", ((EntityPlayer)entity).getCommandSenderName());
		}
		return false;
	}
}
