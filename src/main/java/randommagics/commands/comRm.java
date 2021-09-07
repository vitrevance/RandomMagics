package randommagics.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import randommagics.Main;
import randommagics.curses.Curse;
import randommagics.curses.CurseRegistry;
import randommagics.customs.BigExplosion;
import randommagics.customs.CustomExtendedEntityProperties;
import randommagics.customs.DemonBossFight;
import randommagics.customs.RandomUtils;
import randommagics.customs.StructureHelper;
import randommagics.dimensions.CustomTeleporter;
import randommagics.entities.mobs.EntitySupremeDemon;
import randommagics.gui.GUIHandler;
import randommagics.packets.PacketBeginDemonBossFight;
import randommagics.packets.RmNetworkRegistry;
import tv.twitch.chat.ChatMessage;

public class comRm extends CommandBase {
	
	String[] param1lvl = {"madness", "demonlevel", "make_enchantment_honest", "curse", "tp", "explode", "begindemonbossfight", "repeat"};
	
	private static StructureHelper structureHelper = new StructureHelper();

	@Override
	public String getCommandName() {
		return "rm";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/rm <command> <parameters> or /rm <player> <command> <parameters>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] stin) {
		EntityPlayer player = null;
		String[] st = null;
		if (stin.length > 0) {
			player = RandomUtils.getServerEntityPlayerByName(stin[0]);
			if (player != null) {
				st = Arrays.copyOfRange(stin, 1, stin.length);
			}
			else if (sender instanceof EntityPlayer) {
				player = (EntityPlayer)sender;
				st = stin;
			}
			if (player != null && st != null) {
				if (st.length == 0) {
					throw new WrongUsageException(this.getCommandUsage(sender));
				}
				CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(player);
				if (st[0].contentEquals(param1lvl[0])) {
					if (st.length < 2)
						throw new WrongUsageException(this.getCommandUsage(sender));
					
					int val = Integer.parseInt(st[1]);
					if (val < 0)
						val = 0;
					ex.madnessLvl = val;
					ex.sync(player);
					player.addChatMessage(new ChatComponentText("Now your madness level is " + val));
					return;
				}
				if (st[0].contentEquals(param1lvl[1])) {
					if (st.length < 2)
						throw new WrongUsageException(this.getCommandUsage(sender));
					int val = Integer.parseInt(st[1]);
					if (val > 10)
						val = 10;
					if (val < 0)
						val = 0;
					ex.demonLevel = val;
					ex.nextDemonLvlProgress = 0;
					ex.sync(player);
					player.addChatMessage(new ChatComponentText("Now your demon level is " + val));
					return;
				}
				if (st[0].contentEquals(param1lvl[2])) {
					if (st.length < 2)
						throw new WrongUsageException(this.getCommandUsage(sender));
					boolean param = Boolean.parseBoolean(st[1]);
					if (player.getCurrentEquippedItem() != null) {
						if (!player.getCurrentEquippedItem().hasTagCompound())
							player.getCurrentEquippedItem().setTagCompound(new NBTTagCompound());
						player.getCurrentEquippedItem().getTagCompound().setBoolean("honest", param);
						player.addChatMessage(new ChatComponentText("Now your enchantment is " + param));
						return;
					}
				}
				if (st[0].contentEquals(param1lvl[3])) {
					if (st.length < 2)
						throw new WrongUsageException(this.getCommandUsage(sender));
					if (st[1].contentEquals("apply")) {
						if (st.length < 3) {
							throw new WrongUsageException(this.getCommandUsage(sender));
						}
						Curse c = CurseRegistry.getCurseByID(st[2]);
						if (c != null) {
							c.setCommandApplied();
							ex.addCurse(c);
							ex.sync(player);
							player.addChatMessage(new ChatComponentText("Curse <" + c.getUniqueID() + "> applied!"));
						}
						else {
							player.addChatMessage(new ChatComponentText("Curse <" + st[2] + "> not found!"));
						}
						return;
					}
					if (st[1].contentEquals("revoke")) {
						if (st.length < 3)
							throw new WrongUsageException(this.getCommandUsage(sender));
						if (st[2].contentEquals("all")) {
							for (int i = 0; i < ex.curses.size(); i++) {
								ex.curses.get(i).setCommandRevoke();
							}
							ex.sync(player);
							player.addChatMessage(new ChatComponentText("All curses revoked!"));
							return;
						}
						else {
							Curse c = ex.getCurse(st[2]);
							if (c != null) {
								c.setCommandRevoke();
								ex.sync(player);
								player.addChatMessage(new ChatComponentText("Curse <" + c.getUniqueID() + "> revoked!"));
							}
							else {
								player.addChatMessage(new ChatComponentText("Curse <" + st[2] + "> not found!"));
							}
							return;
						}
					}
					if (st[1].contentEquals("list")) {
						if (ex.curses.size() > 0) {
							for (Curse c : ex.curses) {
								player.addChatMessage(new ChatComponentText(c.getUniqueID() + "  " + c.Lvl()));
							}
						}
						else {
							player.addChatMessage(new ChatComponentText("No curses found!"));
						}
						return;
					}
				}
				if (st[0].contentEquals("tp")) {
					if (st.length == 5) {
						try {
							CustomTeleporter.teleportToDimension(player, Integer.parseInt(st[1]), Double.parseDouble(st[2]), Double.parseDouble(st[3]), Double.parseDouble(st[4]));
						}
						catch(NumberFormatException e) {
							throw new WrongUsageException(this.getCommandUsage(sender));
						}
					}
					else {
						throw new WrongUsageException(this.getCommandUsage(sender));
					}
					return;
				}
				if (st[0].contentEquals("explode")) {
					if (st.length < 2) {
						throw new WrongUsageException(this.getCommandUsage(sender));
					}
					float r = 0;
					try {
						r = Float.parseFloat(st[1]);
					}
					catch(NumberFormatException e) {
						throw new WrongUsageException(this.getCommandUsage(sender));
					}
					new BigExplosion(player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ, r, r, 0.7f, 1.f).create();
					return;
				}
				if (st[0].contentEquals("pos1")) {
					if (st.length < 4) {
						structureHelper.setPos1((int)player.posX, (int)player.posY, (int)player.posZ);
						System.out.println((int)player.posX + " " + (int)player.posY + " " + (int)player.posZ);
					}
					else {
						int x = Integer.parseInt(st[1]);
						int y = Integer.parseInt(st[2]);
						int z = Integer.parseInt(st[3]);
						structureHelper.setPos1(x, y, z);
						System.out.println(x + " " + y + " " + z);
					}
					return;
				}
				if (st[0].contentEquals("pos2")) {
					if (st.length < 4) {
						structureHelper.setPos2((int)player.posX, (int)player.posY, (int)player.posZ);
						System.out.println((int)player.posX + " " + (int)player.posY + " " + (int)player.posZ);
					}
					else {
						int x = Integer.parseInt(st[1]);
						int y = Integer.parseInt(st[2]);
						int z = Integer.parseInt(st[3]);
						structureHelper.setPos2(x, y, z);
						System.out.println(x + " " + y + " " + z);
					}
					return;
				}
				if (st[0].contentEquals("posRel")) {
					if (st.length < 4) {
						structureHelper.setRel((int)player.posX, (int)player.posY, (int)player.posZ);
						System.out.println((int)player.posX + " " + (int)player.posY + " " + (int)player.posZ);
					}
					else {
						int x = Integer.parseInt(st[1]);
						int y = Integer.parseInt(st[2]);
						int z = Integer.parseInt(st[3]);
						structureHelper.setRel(x, y, z);
						System.out.println(x + " " + y + " " + z);
					}
					return;
				}
				if (st[0].contentEquals("read")) {
					structureHelper.readStruct(player.worldObj, false);
					System.out.println(structureHelper.toStr().substring(0, 20));
					return;
				}
				if (st[0].contentEquals("str")) {
					System.out.println("Struct: " + structureHelper.toStr().length());
					try {
						FileWriter file = new FileWriter("../../../outputLOL.txt");
						file.write(structureHelper.toStr());
						file.flush();
						file.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					System.out.println(structureHelper.getReg());
					return;
				}
				if (st[0].contentEquals("reset")) {
					structureHelper = new StructureHelper();
					return;
				}
				if (st[0].contentEquals("begindemonbossfight")) {
					//player.worldObj.spawnEntityInWorld(new EntitySupremeDemon(player.worldObj, player));
					DemonBossFight.beginDemonBossFight(player);
					RmNetworkRegistry.NETWORK.sendToAll(new PacketBeginDemonBossFight(player.getCommandSenderName()));
					return;
				}
				if (st[0].contentEquals("test")) {
					System.out.println("Testing... " + player.worldObj.isRemote);
					Chunk chunk = player.worldObj.getChunkFromBlockCoords((int)player.posX, (int)player.posZ);
					ExtendedBlockStorage[] blocks = new ExtendedBlockStorage[16];
					for (int i = 0; i < 16; i++) {
						ExtendedBlockStorage ebs = new ExtendedBlockStorage(0, true);
						blocks[i] = ebs;
					}
					chunk.setStorageArrays(blocks);
					chunk.setChunkModified();
					return;
				}
				if (st[0].contentEquals("repeat") && st.length > 2) {
					int numToRepeat = Integer.parseInt(st[1]);
					String command = "";
					for (int i = 2; i < st.length; i++) {
						command += st[i] + " ";
					}
					for (int i = 0; i < numToRepeat; i++) {
						MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();;
						ICommandManager icommandmanager = server.getCommandManager();
						icommandmanager.executeCommand(player, command);
					}
					return;
				}
					
			}
			throw new WrongUsageException(this.getCommandUsage(sender));
		}
	}
	
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] com) {
		ArrayList<String> names = RandomUtils.getAllPlayersNames();
		if (com.length > 1) {
			for (String name : names) {
				if (name.contentEquals(com[0])) {
					List l = new ArrayList<String>();
					for (int i = 0; i < param1lvl.length; i++) {
						if (param1lvl[i].startsWith(com[1]))
							l.add(param1lvl[i]);
					}
					return l;
				}
			}
			return null;
		}
		List l = new ArrayList<String>();
		for (int i = 0; i < param1lvl.length; i++) {
			if (param1lvl[i].startsWith(com[0]))
				l.add(param1lvl[i]);
		}
		for (String name : names) {
			if (name.startsWith(com[0]) || name.toLowerCase().startsWith(com[0])) {
				l.add(name);
			}
		}
		return l;
	}
	
	@Override
	public List getCommandAliases() {
		List l = new ArrayList<String>();
		l.add("randommagics");
		return l;
	}
	
	@Override
    public boolean canCommandSenderUseCommand(ICommandSender commandSender) {
        //“олько опам или если в мире активны читы.
		return commandSender instanceof EntityPlayer ? MinecraftServer.getServer().getConfigurationManager().func_152596_g(((EntityPlayer) commandSender).getGameProfile()) : false;      
    }

}
