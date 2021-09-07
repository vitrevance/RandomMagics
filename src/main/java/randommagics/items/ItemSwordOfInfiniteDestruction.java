package randommagics.items;

import java.util.List;

import com.google.common.collect.Multimap;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import randommagics.Init;

public class ItemSwordOfInfiniteDestruction extends ItemSword {

	public ItemSwordOfInfiniteDestruction() {
		super(Init.INFINITE_STONE);
		setUnlocalizedName("ItemSwordOfInfiniteDestruction");
		setCreativeTab(Init.TabRandomMagics);
		setTextureName("randommagics:SwordOfInfiniteDestruction");
	}
	
	@Override
	public boolean isDamageable() {
		return false;
	}
}
