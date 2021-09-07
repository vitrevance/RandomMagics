package randommagics.blocks;

import java.net.NetworkInterface;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.visnet.VisNetHandler;
import thaumcraft.api.wands.IWandable;
import thaumcraft.common.CommonProxy;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.network.PacketHandler;

public class TileVisCell extends TileThaumcraft implements IWandable, IAspectContainer {
	
	public int maxVis;
	public AspectList visStorage;// = new AspectList().add(Aspect.AIR, 0).add(Aspect.FIRE, 0).add(Aspect.WATER, 0).add(Aspect.EARTH, 0).add(Aspect.ORDER, 0).add(Aspect.ENTROPY, 0);
	public float renderAngl = 0F;
	
	public TileVisCell() {
		maxVis = 500000;
		visStorage = new AspectList().add(Aspect.AIR, 0).add(Aspect.FIRE, 0).add(Aspect.WATER, 0).add(Aspect.EARTH, 0).add(Aspect.ORDER, 0).add(Aspect.ENTROPY, 0);
	}
	
	public AxisAlignedBB getRenderBoundingBox()
    {
        return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 1, zCoord - 1, xCoord + 1, yCoord + 1, zCoord + 1);
    }
	
	public boolean canUpdate()
    {
        return true;
    }
	
	public void updateEntity() {
        super.updateEntity();
        for (int i = 0; i < visStorage.aspects.size() && visStorage.visSize() < maxVis * 6; i++) {
    		int vis = VisNetHandler.drainVis(worldObj, this.xCoord, this.yCoord, this.zCoord, visStorage.getAspects()[i], 50);
    		visStorage.add(visStorage.getAspects()[i], vis);
    		if (visStorage.getAmount(visStorage.getAspects()[i]) > maxVis) {
    			visStorage.aspects.put(visStorage.getAspects()[i], maxVis);
    		}
    	}
        if (!worldObj.isRemote) {
        	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        	markDirty();
        } else {
        	renderAngl += 0.5F;
        	if (renderAngl >= 360F)
        		renderAngl = 0;
        }
    }
	
	@Override
	public void readCustomNBT(NBTTagCompound nbttagcompound) {
		visStorage.readFromNBT(nbttagcompound, "tileviscell");
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound nbttagcompound) {
		visStorage.aspects.putIfAbsent(Aspect.AIR, 0);
        visStorage.aspects.putIfAbsent(Aspect.FIRE, 0);
        visStorage.aspects.putIfAbsent(Aspect.WATER, 0);
        visStorage.aspects.putIfAbsent(Aspect.EARTH, 0);
        visStorage.aspects.putIfAbsent(Aspect.ORDER, 0);
        visStorage.aspects.putIfAbsent(Aspect.ENTROPY, 0);
        visStorage.writeToNBT(nbttagcompound, "tileviscell");
	}
	
	/*
	@Override
	public void readFromNBT(NBTTagCompound nbtCompound) {
		super.readFromNBT(nbtCompound);
		visStorage.readFromNBT(nbtCompound);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbtCompound) {
        super.writeToNBT(nbtCompound);
        visStorage.aspects.putIfAbsent(Aspect.AIR, 0);
        visStorage.aspects.putIfAbsent(Aspect.FIRE, 0);
        visStorage.aspects.putIfAbsent(Aspect.WATER, 0);
        visStorage.aspects.putIfAbsent(Aspect.EARTH, 0);
        visStorage.aspects.putIfAbsent(Aspect.ORDER, 0);
        visStorage.aspects.putIfAbsent(Aspect.ENTROPY, 0);
        visStorage.writeToNBT(nbtCompound);
	}
	
	@Override
    public S35PacketUpdateTileEntity getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        this.writeToNBT(tagCompound);
        S35PacketUpdateTileEntity pack = new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tagCompound);
        return pack;
    }
	
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
    }
*/
	@Override
	public AspectList getAspects() {
		AspectList list = new AspectList();
		for (int i = 0; i < 6; i++) {
			list.add(visStorage.getAspects()[i], visStorage.getAmount(visStorage.getAspects()[i]) / 100);
		}
		return list;
	}

	@Override
	public void setAspects(AspectList aspects) {
	}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return false;
	}

	@Override
	public int addToContainer(Aspect tag, int amount) {
		return 0;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		return false;
	}

	@Override
	public boolean takeFromContainer(AspectList ot) {
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount) {
		return false;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		return false;
	}

	@Override
	public int containerContains(Aspect tag) {
		return 0;
	}

	@Override
	public ItemStack onWandRightClick(World world, ItemStack wandstack, EntityPlayer player) {
		if (player.isSneaking()) {
			return null;
		}
		ItemWandCasting wand = (ItemWandCasting)wandstack.getItem();
		if (world.isRemote && visStorage.visSize() > 0)
			player.playSound("random.fizz", 1F, 1F);
		for (int i = 0; i < 6; i++) {
			if (wand.getMaxVis(wandstack) - wand.getVis(wandstack, visStorage.getAspects()[i]) >= visStorage.getAmount(visStorage.getAspects()[i])) {
				wand.addRealVis(wandstack, visStorage.getAspects()[i], visStorage.getAmount(visStorage.getAspects()[i]), true);
				visStorage.reduce(visStorage.getAspects()[i], visStorage.getAmount(visStorage.getAspects()[i]));
			}else {
				int dif = wand.getMaxVis(wandstack) - wand.getVis(wandstack, visStorage.getAspects()[i]);
				wand.addRealVis(wandstack, visStorage.getAspects()[i], dif, true);
				visStorage.reduce(visStorage.getAspects()[i], dif);
			}
		}
		player.swingItem();
		this.updateEntity();
		return wandstack;
	}

	@Override
	public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {
	}

	@Override
	public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {
	}

	@Override
	public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side,
			int md) {
		return 0;
	}

}
