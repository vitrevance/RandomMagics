package randommagics.customs;

import java.io.IOException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.APPLEPackedPixels;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import randommagics.Config;
import randommagics.Init;
import randommagics.Main;
import randommagics.curses.Curse;
import randommagics.curses.CurseGhostForm;
import randommagics.curses.CurseOutOfTime;
import randommagics.curses.CurseRegistry;
import randommagics.entities.EntityPotionProjectile;
import randommagics.entities.EntityStealingOrb;
import randommagics.entities.mobs.EntityDemon;
import randommagics.entities.mobs.EntitySupremeDemon;
import randommagics.packets.PacketCustomExtendedPropertiesSync;
import randommagics.packets.RmNetworkRegistry;
import thaumcraft.common.lib.network.PacketHandler;

public class CustomExtendedEntityProperties implements IExtendedEntityProperties {
	
	public static String ID = "randommagicsextendedproperties";
	
	private NBTTagCompound nbt;
	
	private EntityPlayer player;
	
	public ArrayList<Curse> curses = new ArrayList<Curse>();
	
	public int demonLevel;
	
	public int madnessLvl;
	
	public int demonability;
	
	public int nextDemonLvlProgress;
	
	public boolean usesdemonability;
	
	public int prevDemonLvl;

	public CustomExtendedEntityProperties(EntityPlayer player) {
		this.player = player;
		nbt = new NBTTagCompound();
		madnessLvl = 0;
		demonLevel = 0;
		demonability = 0;
		prevDemonLvl = 0;
		nextDemonLvlProgress = 0;
		usesdemonability = false;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		if (nbt == null)
			nbt = new NBTTagCompound();
		compound.setTag(ID, nbt);
		nbt.setInteger("madnessLvl", madnessLvl);
		nbt.setInteger("demonLevel", demonLevel);
		nbt.setInteger("demonability", demonability);
		nbt.setInteger("prevdemonlvl", prevDemonLvl);
		nbt.setBoolean("usesdemonability", usesdemonability);
		nbt.setInteger("nextDemonLvlProgress", nextDemonLvlProgress);
		NBTTagCompound cursesnbt = new NBTTagCompound();
		Iterator<Curse> i = curses.iterator();
		int n = 0;
		while(i.hasNext()) {
			try {
				//byte[] b = PacketCustomExtendedPropertiesSync.toByteArray(i.next());
				//cursesnbt.setByteArray(String.valueOf(n), i.next().writeToNBT().toString().getBytes());
				NBTTagCompound temp = new NBTTagCompound();
				i.next().writeToNBT(temp);
				cursesnbt.setTag("curse" + n, temp);
				n++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		nbt.setTag("cursesnbt", cursesnbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		nbt = (NBTTagCompound) compound.getTag(ID);
		if (nbt != null) {
			madnessLvl = nbt.getInteger("madnessLvl");
			demonLevel = nbt.getInteger("demonLevel");
			demonability = nbt.getInteger("demonability");
			prevDemonLvl = nbt.getInteger("prevdemonlvl");
			usesdemonability = nbt.getBoolean("usesdemonability");
			nextDemonLvlProgress = nbt.getInteger("nextDemonLvlProgress");
			NBTTagCompound cursesnbt = nbt.getCompoundTag("cursesnbt");
			if (cursesnbt != null) {
				curses = new ArrayList<Curse>();
				for (int i = 0; cursesnbt.hasKey("curse" + i); i++) {
					try {
						//byte[] b = cursesnbt.getByteArray(String.valueOf(i));
						//curses.add((Curse)PacketCustomExtendedPropertiesSync.toObject(b));
						//curses.add(Curse.readFromNBT(cursesnbt));
						NBTTagCompound temp = cursesnbt.getCompoundTag("curse" + i);
						Class cu = CurseRegistry.registry.get(temp.getString("CurseID"));
						Curse c = (Curse)cu.newInstance();
						c.readFromNBT(temp);
						curses.add(c);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void init(Entity entity, World world) {
		this.nbt = new NBTTagCompound();
	}
	
	public static CustomExtendedEntityProperties get(EntityPlayer entity) {
		return (CustomExtendedEntityProperties)entity.getExtendedProperties(ID);
	}
	
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(ID, new CustomExtendedEntityProperties(player));
	}

	public static void sync(EntityPlayer player) {
		if (player != null) {
			CustomExtendedEntityProperties ex = get(player);
			ex.sendData();
		}
	}
	
	public void sendData() {
		if (player instanceof EntityPlayerMP)
			RmNetworkRegistry.NETWORK.sendTo(new PacketCustomExtendedPropertiesSync(this), (EntityPlayerMP)player);
	}

	public void addCurse(Curse curse) {
		Iterator<Curse> i = curses.iterator();
		while (i.hasNext()) {
			Curse c = i.next();
			if (c.getUniqueID() == curse.getUniqueID()) {
				if (c.isStackable()) {
					c.addLvl(curse.Lvl());
				}
				return;
			}
		}
		curses.add(curse);
	}
	
	public void revokeCurse(Curse curse) {
		Iterator<Curse> i = curses.iterator();
		while (i.hasNext()) {
			Curse c = i.next();
			if (c.getUniqueID() == curse.getUniqueID()) {
				c.onRevoke(player);
				c = null;
				i.remove();
				return;
			}
		}
	}

	public void removeCurse(Curse curse) {
		Iterator<Curse> i = curses.iterator();
		while (i.hasNext()) {
			if (i.next().getUniqueID() == curse.getUniqueID()) {
				i.remove();
				return;
			}
		}
	}
	
	public boolean cursedWith(String curseId) {
		Iterator<Curse> i = curses.iterator();
		while (i.hasNext()) {
			if (i.next().getUniqueID() == curseId) {
				return true;
			}
		}
		return false;
	}
	
	public void removeAllCurses() {
		curses.clear();
	}
	
	public void doCurses() {
		if (curses != null && !curses.isEmpty()) {
			ArrayList<Curse> temp = (ArrayList<Curse>)curses.clone();
			Iterator<Curse> i = temp.iterator();
			while (i.hasNext()) {
				Curse c = i.next();
				c.onUpdate(player);
			}
			temp = null;
		}
	}

	public void revokeAllCurses() {
		Iterator<Curse> i = curses.iterator();
		while (i.hasNext()) {
			i.next().onRevoke(player);
			i.remove();
		}
	}
	public Curse getCurse(String id) {
		for (Curse c : curses) {
			if (c.getUniqueID().contentEquals(id)) {
				return c;
			}
		}
		return null;
	}
	
	public void useDemonAbility() {
		if (demonLevel > demonability + 2 && usesdemonability) {
			switch (demonability) {
			case 0:
				player.motionX = player.getLookVec().xCoord * 5;
				player.motionZ = player.getLookVec().zCoord * 5;
				player.motionY = player.getLookVec().yCoord * 5;
				break;
			case 1:
				if (!player.worldObj.isRemote) {
					EntityDemon demon = new EntityDemon(player.worldObj, player);
					demon.setPosition(player.posX, player.posY + 1, player.posZ);
					player.worldObj.spawnEntityInWorld(demon);
				}
				break;
			case 2:
				if (cursedWith("ghostmode")) {
					CurseRegistry.revokeFromPlayer(player, new CurseGhostForm());
				}
				else {
					CurseRegistry.applyOnPlayer(player, player, new CurseGhostForm());
				}
				break;
			case 3:
				if (!player.worldObj.isRemote) {
					EntityStealingOrb projectile = new EntityStealingOrb(player.worldObj, player);
					player.worldObj.spawnEntityInWorld(projectile);
					player.worldObj.playSoundAtEntity(projectile, "thaumcraft:ice", 0.3F, 0.8F + player.worldObj.rand.nextFloat() * 0.1F);
				}
				break;
			case 4:
				List entities = player.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(player.posX - 8, player.posY - 8, player.posZ - 8, player.posX + 8, player.posY + 8, player.posZ + 8));
				for (Object entity : entities) {
					if (entity instanceof EntityPlayer) {
						if ((EntityPlayer)entity == player) {
							continue;
						}
					}
					Entity ent = (Entity)entity;
					ent.motionX += (ent.posX - player.posX);
					ent.motionY += (ent.posY - player.posY + 1);
					ent.motionZ += (ent.posZ - player.posZ);
				}
				if (!player.worldObj.isRemote) {
					player.worldObj.playSoundAtEntity(player, "thaumcraft:ice", 1.3F, 0.2F + player.worldObj.rand.nextFloat() * 0.1F);
				}
				break;
			case 5:
				if (!player.worldObj.isRemote) {
					EntityPotionProjectile projectile = new EntityPotionProjectile(player.worldObj, player, new PotionEffect(Config.potionStuckInTimeId, 200));
					player.worldObj.spawnEntityInWorld(projectile);
					player.worldObj.playSoundAtEntity(projectile, "thaumcraft:ice", 0.3F, 0.8F + player.worldObj.rand.nextFloat() * 0.1F);
				}
				break;
			case 6:
				if (cursedWith("outoftime")) {
					revokeCurse(new CurseOutOfTime());
				}
				else {
					CurseRegistry.applyOnPlayer(player, player, new CurseOutOfTime());
				}
				break;
			case 7:
				if (!player.worldObj.isRemote) {
					EntityPotionProjectile projectile = new EntityPotionProjectile(player.worldObj, player, new PotionEffect(Config.potionVoiceOfDeathId, 100, 500));
					player.worldObj.spawnEntityInWorld(projectile);
					player.worldObj.playSoundAtEntity(projectile, "thaumcraft:ice", 0.3F, 0.8F + player.worldObj.rand.nextFloat() * 0.1F);
				}
				break;
			}
		}
		if (this.demonLevel == 5) {
			this.nextDemonLvlProgress++;
			sync(player);
		}
	}

	public int nextDemonLevelRequres() {
		return (int)Math.pow(8, demonLevel);
	}
	
	public void nextDemonLevel() {
		demonLevel++;
		nextDemonLvlProgress = 0;
		if (!player.worldObj.isRemote) {
			player.addChatMessage(new ChatComponentText("Now your demon level is " + demonLevel));
		}
		sync(player);
	}

}
