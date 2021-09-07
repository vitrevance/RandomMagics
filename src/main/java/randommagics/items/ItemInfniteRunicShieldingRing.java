package randommagics.items;

import java.util.List;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import randommagics.Init;
import thaumcraft.api.IRunicArmor;
import thaumcraft.api.ItemRunic;
import thaumcraft.common.Thaumcraft;

public class ItemInfniteRunicShieldingRing extends ItemRunic implements IBauble {
	
	public ItemInfniteRunicShieldingRing() {
		super(1000000);
		setCreativeTab(Init.TabRandomMagics);
		setUnlocalizedName("ItemInfniteRunicShieldingRing");
		setTextureName("randommagics:InfniteRunicShieldingRing");
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.RING;
	}

	@Override
	public boolean canEquip(ItemStack arg0, EntityLivingBase arg1) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack arg0, EntityLivingBase arg1) {
		return true;
	}

	@Override
	public void onEquipped(ItemStack arg0, EntityLivingBase arg1) {
		Thaumcraft.instance.runicEventHandler.isDirty = true;
	}

	@Override
	public void onUnequipped(ItemStack arg0, EntityLivingBase arg1) {
		Thaumcraft.instance.runicEventHandler.isDirty = true;
	}

	@Override
	public void onWornTick(ItemStack arg0, EntityLivingBase arg1) {
	}
}