package randommagics.items;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import randommagics.Init;
import randommagics.curses.CurseShortLife;
import randommagics.customs.CustomExtendedEntityProperties;

public class ItemClawsOfExile extends ItemSword {
	
	public ItemClawsOfExile() {
		super(ToolMaterial.IRON);
		this.setCreativeTab(Init.TabRandomMagics);
		this.setUnlocalizedName("ItemClawsOfExile");
		this.setTextureName("randommagics:ClawsOfExile");
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int p_77648_4_,
			int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(player);
		if (ex != null && ex.demonLevel > 0 && !world.isRemote) {
			ex.demonLevel = -1;
			ex.madnessLvl += 10;
			ex.nextDemonLevel();
			stack.stackSize = 0;
			player.attackEntityFrom(DamageSource.causePlayerDamage(player), 5);
			world.playSoundAtEntity(player, "mob.enderdragon.end", 1, 1);
			player.addPotionEffect(new PotionEffect(Potion.blindness.id, 120, 1));
			return true;
		}
		return false;
	}
}