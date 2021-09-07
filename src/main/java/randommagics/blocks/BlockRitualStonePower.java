package randommagics.blocks;

import java.util.concurrent.TimeUnit;

import javax.xml.ws.Provider;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import randommagics.Init;
import randommagics.packets.PacketParticles;
import randommagics.packets.RmNetworkRegistry;
import thaumcraft.common.tiles.TilePedestal;
import thaumcraft.common.config.ConfigBlocks;

public class BlockRitualStonePower extends Block implements ITileEntityProvider {
	
	public BlockRitualStonePower()
	{
		super(Material.rock);
		this.setBlockName("ritualStonePower");
		this.setCreativeTab(Init.TabRandomMagics);
		this.setBlockTextureName("randommagics:RitualStonePower");
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(3.0F);
	}
	
	private boolean StructExists(World world, int x, int y, int z)
	{
		boolean ex = true;
		for (int addx = -1; addx < 2; addx++)
		{
			for (int addz = -1; addz < 2; addz++)
			{
				if (world.getBlock(x+addx, y-1, z+addz) != Init.RitualStone)
					ex = false;
			}
		}
		TileEntity te = world.getTileEntity(x, y+1, z);
		if (ex && te != null && (te instanceof TilePedestal))
		{
			ped = (TilePedestal)te;
            if(ped.getStackInSlot(0) != null && ped.getStackInSlot(0).isItemEnchanted())
                return true;
		}
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote)
		{
			ItemStack current_is = player.getCurrentEquippedItem();
			if (current_is != null && current_is.getItem() == Init.RitualCatalyst)
			{
				if (StructExists(world, x, y, z)) {
				//player.destroyCurrentEquippedItem();
				//player.setItemInUse(current_is, current_is.stackSize-1);
				short id = ped.getStackInSlot(0).getEnchantmentTagList().getCompoundTagAt(0).getShort("id");
				short lvl = ped.getStackInSlot(0).getEnchantmentTagList().getCompoundTagAt(0).getShort("lvl");
				if (lvl < 127)
				{
					//ped.getStackInSlot(0).setTagCompound(null);
					int i = ped.getStackInSlot(0).getEnchantmentTagList().tagCount() - 1;
					/*
					for (; i >= 0; i--) {
						ped.getStackInSlot(0).getEnchantmentTagList().removeTag(i);
					}
					*/
					ped.getStackInSlot(0).stackTagCompound.setTag("ench", null);
					ped.getStackInSlot(0).addEnchantment(Enchantment.enchantmentsList[id], lvl+1);
					player.setCurrentItemOrArmor(0, new ItemStack(current_is.getItem(), (current_is.stackSize)-1));
					RmNetworkRegistry.sendToAllAround(new PacketParticles(x, y + 1.6D, z, 0, 0.1D, 0, 0), world.provider.dimensionId, x, y, z, 16);
					RmNetworkRegistry.sendToAllAround(new PacketParticles(x + 0.5, y + 1.5D, z, 0, 0.1D, 0, 0), world.provider.dimensionId, x, y, z, 16);
					RmNetworkRegistry.sendToAllAround(new PacketParticles(x + 1, y + 1.6D, z, 0, 0.1D, 0, 0), world.provider.dimensionId, x, y, z, 16);
					RmNetworkRegistry.sendToAllAround(new PacketParticles(x + 1, y + 1.6D, z + 1, 0, 0.1D, 0, 0), world.provider.dimensionId, x, y, z, 16);
					RmNetworkRegistry.sendToAllAround(new PacketParticles(x, y + 1.5D, z + 0.5, 0, 0.1D, 0, 0), world.provider.dimensionId, x, y, z, 16);
					RmNetworkRegistry.sendToAllAround(new PacketParticles(x, y + 1.6D, z + 1, 0, 0.1D, 0, 0), world.provider.dimensionId, x, y, z, 16);
					RmNetworkRegistry.sendToAllAround(new PacketParticles(x + 0.5, y + 1.5D, z + 1, 0, 0.1D, 0, 0), world.provider.dimensionId, x, y, z, 16);
					RmNetworkRegistry.sendToAllAround(new PacketParticles(x + 1, y + 1.6D, z + 0.5, 0, 0.1D, 0, 0), world.provider.dimensionId, x, y, z, 16);
					return true;
				}
				}
				else {
					TileRitualStonePower te  = (TileRitualStonePower)world.getTileEntity(x, y, z);
					if (te.startRitual(player)) {
						player.setCurrentItemOrArmor(0, new ItemStack(current_is.getItem(), (current_is.stackSize)-1));
					}
				}
			}
		}
		return false;
	}
	private TilePedestal ped;
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileRitualStonePower();
	}
}
