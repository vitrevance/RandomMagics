package randommagics.items;

import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import randommagics.Init;

public class ItemXpHolder extends Item {
	
	private IIcon[] icons = new IIcon[2];
	//private String texturename = "randommagics:XpHolder";
	
	public ItemXpHolder() {
		super();
		
		this.setTextureName("randommagics:XpHolder_0");
		this.setCreativeTab(Init.TabRandomMagics);
		this.setUnlocalizedName("ItemXpHolder");
		this.setMaxStackSize(1);
	}
	
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
		if(!world.isRemote) {
			if(player.isSneaking()) {
				int xp;
				if(itemstack.hasTagCompound())
					xp = itemstack.getTagCompound().getInteger("xp");
				else
					xp = 0;
				List ents = getXPOrbs(player.posX, player.posY, player.posZ, player.getEntityWorld());
				for(int i = 0; i < ents.size(); i++) {
					EntityXPOrb orb = (EntityXPOrb)ents.get(i);
					orb.setDead();
					xp += 1;
				}
				if(!itemstack.hasTagCompound()) {
					NBTTagCompound nbt = new NBTTagCompound();
					itemstack.setTagCompound(nbt);
				}
				itemstack.getTagCompound().setInteger("xp", xp);
			}
			else {
			if(!itemstack.hasTagCompound()) {
				NBTTagCompound nbt = new NBTTagCompound();
				itemstack.setTagCompound(nbt);
			}
			int xp = itemstack.stackTagCompound.getInteger("xp");
			if(xp == 0) {
				xp = player.experienceTotal;
				player.addExperienceLevel(-(player.experienceLevel + 1));
			}
			else {
				player.addExperience(xp);
				xp = 0;
			}
			itemstack.stackTagCompound.setInteger("xp", xp);
			}
		}
        return itemstack;
    }
	
	public void addInformation(ItemStack itemstack, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		if(itemstack.hasTagCompound()) {
			int xp = itemstack.stackTagCompound.getInteger("xp");
			list.add(1, String.valueOf(xp));
		}
	}
	
	public void registerIcons(IIconRegister reg) {
	    for (int i = 0; i < 2; i ++) {
	        this.icons[i] = reg.registerIcon("randommagics:XpHolder" + "_" + i);
	    }
	}
	
	public IIcon getIcon(ItemStack stack, int renderPass)
    {
		if(stack.hasTagCompound() && stack.getTagCompound().getInteger("xp") > 0)
			return icons[1];
		else
			return icons[0];
        //return getIcon(stack, renderPass);
    }
	
	public IIcon getIconIndex(ItemStack stack)
    {
		if(stack.hasTagCompound() && stack.getTagCompound().getInteger("xp") > 0)
			return icons[1];
		else
			return icons[0];
    }
	
	public List getXPOrbs(double x, double y, double z, World worldObj) {
		double cdist = 1.7976931348623158E+308D;
		List ents = worldObj.getEntitiesWithinAABB(net.minecraft.entity.item.EntityXPOrb.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1).expand(256D, 127D, 256D));
		for(int i = 0; i < ents.size(); i++) {
			double ex = ((EntityXPOrb)ents.get(i)).posX;
			double ey = ((EntityXPOrb)ents.get(i)).posY;
			double ez = ((EntityXPOrb)ents.get(i)).posZ;
			if(getDistanceTo(ex, ey, ez, x, y, z) > cdist) {
				ents.remove(i);
			}
		}
		return ents;
	}
	
	public double getDistanceTo(double par1, double par3, double par5, double x, double y, double z)
    {
        double var7 = ((double)x + 0.5D) - par1;
        double var9 = ((double)y + 0.5D) - par3;
        double var11 = ((double)z + 0.5D) - par5;
        return var7 * var7 + var9 * var9 + var11 * var11;
    }
}
