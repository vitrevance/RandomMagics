package randommagics.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import randommagics.Init;

public class ItemRingOfSaturation extends Item implements IBauble {
	
	public ItemRingOfSaturation() {
		setTextureName("randommagics:RingOfSaturation");
		setUnlocalizedName("ItemRingOfSaturation");
		setCreativeTab(Init.TabRandomMagics);
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
	public void onWornTick(ItemStack stack, EntityLivingBase living) {
		if (living instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)living;
			player.getFoodStats().addStats(100, 40F);
			//player.getFoodStats().setFoodSaturationLevel(40);
		}
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.RING;
	}

	@Override
	public void onEquipped(ItemStack arg0, EntityLivingBase arg1) {
	}

	@Override
	public void onUnequipped(ItemStack arg0, EntityLivingBase arg1) {
	}
}
