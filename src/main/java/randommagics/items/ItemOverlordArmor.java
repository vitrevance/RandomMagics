package randommagics.items;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import ibxm.Player;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.world.World;
import randommagics.Init;
import randommagics.customs.RandomUtils;
import thaumcraft.api.IGoggles;
import thaumcraft.api.nodes.IRevealer;

public class ItemOverlordArmor extends ItemArmor implements IRevealer, IGoggles {
	
	private static final UUID ID_armorknockback = UUID.fromString("6a8b292a-b36d-11e9-a2a3-2a2ae2dbcce4");
	private static final AttributeModifier armorknockback = new AttributeModifier(ID_armorknockback, "armorknockback", 2D, 0);

	public ItemOverlordArmor(String unlocalizedName, ArmorMaterial material, String textureName, int type) {
		super(material, 0, type);
	    this.setUnlocalizedName(unlocalizedName);
	    this.setCreativeTab(Init.TabRandomMagics);
	    this.setTextureName("randommagics:" + textureName);
	    this.setMaxDamage(0);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		this.setDamage(itemStack, 0);
		if (fullSet(player)) {
			if (itemStack.getItem() == Init.OverlordHelmet) {
				if (player.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getModifier(ID_armorknockback) == null) {
					player.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(armorknockback);
				}
				player.capabilities.allowFlying = true;
				if (player.isSprinting()) {
					if (world.isRemote)
						player.capabilities.setFlySpeed(1f);
				}
				else {
					if (world.isRemote)
						player.capabilities.setFlySpeed(0.1f);
				}
				player.extinguish();
				
				if (player.ticksExisted % 15 == 0) {
					player.heal(1);
				}
				
			}
		}
		else {
			if (player.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getModifier(ID_armorknockback) != null) {
				player.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).removeModifier(armorknockback);
				if (!player.capabilities.isCreativeMode) {
					player.capabilities.allowFlying = false;
					player.capabilities.isFlying = false;
				}
				if (world.isRemote)
					player.capabilities.setFlySpeed(0.05f);
			}
		}
		
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player.isSneaking() && stack.hasTagCompound()) {
			if (stack.getTagCompound().getString("owner").length() > 0) {
				if (stack.getTagCompound().getString("owner").compareTo(player.getCommandSenderName()) == 0) {
					stack.getTagCompound().setString("owner", "");
					stack.getTagCompound().setInteger("bindtime", 120);
				}
			}
		}
		return stack;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par1,
			boolean par2) {
		
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		if (stack.getTagCompound().getInteger("bindtime") > 0) {
			stack.getTagCompound().setInteger("bindtime", stack.getTagCompound().getInteger("bindtime") - 1);
		}
		
		if (stack.getTagCompound().getBoolean("cheated")) {
			if (entity instanceof EntityPlayer) {
				EntityPlayer p = (EntityPlayer)entity;
				for (int i = 0; i < p.inventory.getSizeInventory(); i++) {
					if (p.inventory.getStackInSlot(i) == stack)
						p.inventory.setInventorySlotContents(i, null);
				}
			}
			stack.stackSize = 0;
			return;
		}
		
		if (entity instanceof EntityPlayer) {
			if (stack.getTagCompound().getString("owner").length() == 0) {
				if (stack.getTagCompound().getInteger("bindtime") == 0) {
					stack.getTagCompound().setString("owner", ((EntityPlayer)entity).getCommandSenderName());
				}
			}
			else {
				if (!((EntityPlayer)entity).getCommandSenderName().contentEquals(stack.getTagCompound().getString("owner"))) {
					if (!world.isRemote) {
						entity.entityDropItem(stack.copy(), 0);
					}
					stack.getTagCompound().setBoolean("cheated", true);
					stack.setStackDisplayName("???");
				}
			}
		}
		else {
			if (stack.getTagCompound().getString("owner").length() > 0) {
				EntityPlayer player = RandomUtils.getEntityPlayerByName(stack.getTagCompound().getString("owner"), world.isRemote);
				if (player != null) {
					if (!world.isRemote)
						entity.entityDropItem(stack.copy(), 0);
					stack.getTagCompound().setBoolean("cheated", true);
				}
			}
		}
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		ItemStack stack = entityItem.getEntityItem().copy();
		if (stack.getTagCompound() != null) {
			if (stack.getTagCompound().getString("owner").length() > 0) {
				EntityPlayer player = RandomUtils.getEntityPlayerByName(stack.getTagCompound().getString("owner"), entityItem.worldObj.isRemote);
				if (player != null) {
					int type = ((ItemArmor)stack.getItem()).armorType;
					if (player.getCurrentArmor(3 - type) == null && player.isEntityAlive()) {
						player.setCurrentItemOrArmor(4 - type, stack);
						entityItem.setDead();
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean fullSet(EntityPlayer player) {
		if (player.getCurrentArmor(0) != null && player.getCurrentArmor(1) != null && player.getCurrentArmor(2) != null && player.getCurrentArmor(3) != null)
			if (player.getCurrentArmor(3).getItem() == Init.OverlordHelmet && player.getCurrentArmor(2).getItem() == Init.OverlordChestplate && player.getCurrentArmor(1).getItem() == Init.OverlordLeggins && player.getCurrentArmor(0).getItem() == Init.OverlordBoots)
				return true;
		return false;
	}

	@Override
	public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
	
	public EnumRarity getRarity(ItemStack itemstuck) {
		return EnumRarity.epic;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List l, boolean b) {
		super.addInformation(stack, player, l, b);
		if (stack.hasTagCompound()) {
			if (!stack.getTagCompound().getBoolean("cheated")) {
				if (stack.getTagCompound().getString("owner").length() > 0)
					l.add("Bound to " + stack.getTagCompound().getString("owner"));
				else
					l.add("Unbound - " + stack.getTagCompound().getInteger("bindtime") / 20);
			}
		}
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
	    return "randommagics:textures/armor/OverlordArmor_" + (this.armorType == 2 ? "2" : "1") + ".png";
	}

}
