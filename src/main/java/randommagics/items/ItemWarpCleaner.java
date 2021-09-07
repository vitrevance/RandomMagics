package randommagics.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import randommagics.Init;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.common.Thaumcraft;

public class ItemWarpCleaner extends ItemFood {
	
	public ItemWarpCleaner() {
		super(0, -2, false);
		this.setCreativeTab(Init.TabRandomMagics);
		this.setUnlocalizedName("ItemWarpCleaner");
		this.setTextureName("randommagics:WarpCleaner");
		this.setAlwaysEdible();
		this.setMaxStackSize(16);
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		ThaumcraftApiHelper.addStickyWarpToPlayer(player, -10);
		ThaumcraftApiHelper.addWarpToPlayer(player, -5, false);
		ThaumcraftApiHelper.addWarpToPlayer(player, 5, true);
		return super.onEaten(stack, world, player);
	}

}