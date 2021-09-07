package randommagics.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import randommagics.Init;
import randommagics.Main;
import randommagics.gui.GUIHandler;

public class BlockStupidAnvil extends Block {
	
	public BlockStupidAnvil() {
		super(Material.iron);
		setBlockName("StupidAnvil");
		setCreativeTab(Init.TabRandomMagics);
		setBlockTextureName("randommagics:StupidAnvil");
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		player.openGui(Main.instance, GUIHandler.GuiIDs.GUI_STUPID_ANVIL.ordinal(), world, x, y, z);
		return true;
	}
}
