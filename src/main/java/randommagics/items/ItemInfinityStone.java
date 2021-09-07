package randommagics.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import randommagics.Init;

public class ItemInfinityStone extends Item {
	
	public IIcon icons[] = new IIcon[6];
	
	public ItemInfinityStone() {
		super();
		this.setUnlocalizedName("ItemInfinityStone");
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.iconString = "ItemInfinityStone";
		this.setCreativeTab(Init.TabRandomMagics);
	}
	
	public EnumRarity getRarity(ItemStack p_77613_1_)
    {
        return EnumRarity.epic;
    }
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
		for (int i = 0; i < 6; i++)
			this.icons[i] = iconRegister.registerIcon("RANDOMMAGICS:" + this.getIconString() + "." + String.valueOf(i));
    }
	
	public IIcon getIconFromDamage(int damage)
    {
        return this.icons[damage];
    }
	
	public boolean showDurabilityBar(ItemStack stack)
    {
        return false;
    }
	
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return false;
    }
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
		if(entity instanceof EntityLivingBase && entity.ticksExisted % 400 == 0) {
			entity.attackEntityFrom(DamageSource.magic, 1);
		}
	}
	
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for(int a = 0; a < 6; a++)
        	par3List.add(new ItemStack(this, 1, a));

    }

    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return (new StringBuilder()).append(super.getUnlocalizedName()).append(".").append(par1ItemStack.getItemDamage()).toString();
    }
}
