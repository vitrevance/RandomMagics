package randommagics.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import randommagics.Init;
import randommagics.customs.CustomExtendedEntityProperties;
import thaumcraft.api.ThaumcraftApiHelper;

public class ItemDemonHeart extends ItemFood {

	public ItemDemonHeart() {
		super(0, -10F, false);
		setCreativeTab(Init.TabRandomMagics);
		setUnlocalizedName("ItemDemonHeart");
		setTextureName("randommagics:DemonHeart");
		setAlwaysEdible();
		setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(player);
		if (ex.demonLevel == 0) {
			ex.demonLevel = 1;
			ex.sync(player);
			if (world.isRemote)
				player.addChatMessage(new ChatComponentText("Now your demon level is 1"));
		}
		else {
			player.attackEntityFrom(DamageSource.starve, 100F);
		}
		return super.onEaten(stack, world, player);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 1024;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int p,
			boolean b) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
			if (player.isUsingItem() && player.getCurrentEquippedItem() == stack) {
				player.addPotionEffect(new PotionEffect(Potion.blindness.id, 10, 40));
				player.addPotionEffect(new PotionEffect(Potion.poison.id, 1, 40));
				player.addPotionEffect(new PotionEffect(Potion.weakness.id, 50, 40));
				player.addPotionEffect(new PotionEffect(Potion.hunger.id, 1, 40));
			}
		}
		super.onUpdate(stack, world, entity, p, b);
	}
}
