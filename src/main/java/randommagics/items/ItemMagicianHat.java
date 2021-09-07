package randommagics.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import randommagics.Init;
import randommagics.Main;
import randommagics.gui.GUIHandler;

public class ItemMagicianHat extends ItemArmor {

	public ItemMagicianHat() {
		super(ArmorMaterial.CLOTH, 0, 0);
		this.setUnlocalizedName("ItemMagicianHat");
		this.setTextureName("randommagics:MagicianHat");
		this.setCreativeTab(Init.TabRandomMagics);
		this.setMaxDamage(0);
	}
	
	@Override
	public boolean isDamageable() {
		return false;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
	    return "randommagics:textures/armor/ArmorMagicianHat.png";
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player.isSneaking() && stack.getTagCompound() != null && stack.getTagCompound().getBoolean("active")) {
			if (!world.isRemote) {
				player.openGui(Main.instance, GUIHandler.GuiIDs.GUI_MAGICIAN_HAT.ordinal(), world, (int)player.posX, (int)player.posY, (int)player.posZ);
			}
			return stack;
		}
		return super.onItemRightClick(stack, world, player);
	}
	
	public void setInventory(ItemStack item, ItemStack stackList[]) {
        NBTTagList var2 = new NBTTagList();
        for(int var3 = 0; var3 < stackList.length; var3++)
            if(stackList[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                stackList[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        item.getTagCompound().setTag("Inventory", var2);
    }
}