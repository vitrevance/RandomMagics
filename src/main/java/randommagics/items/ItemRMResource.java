package randommagics.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import randommagics.Init;

public class ItemRMResource extends Item {
	
	public IIcon icons[] = new IIcon[27];
	
	public ItemRMResource() {
		super();
		this.setUnlocalizedName("ItemRMResource");
		this.setHasSubtypes(true);
		this.iconString = "RMResource";
		this.setCreativeTab(Init.TabRandomMagics);
	}
	
	public EnumRarity getRarity(ItemStack p_77613_1_) {
        return EnumRarity.uncommon;
    }
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
		for (int i = 0; i < this.icons.length; i++)
			this.icons[i] = iconRegister.registerIcon("RANDOMMAGICS:" + this.getIconString() + "." + String.valueOf(i));
    }
	
	public IIcon getIconFromDamage(int damage) {
        return this.icons[damage];
    }
	
	public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }
	
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
	
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        for(int a = 0; a < this.icons.length; a++)
        	par3List.add(new ItemStack(this, 1, a));

    }

    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return (new StringBuilder()).append(super.getUnlocalizedName()).append(".").append(par1ItemStack.getItemDamage()).toString();
    }
}
