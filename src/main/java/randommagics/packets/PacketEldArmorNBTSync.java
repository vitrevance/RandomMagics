package randommagics.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import randommagics.items.ItemEldritchArmor;

public class PacketEldArmorNBTSync implements IMessage, IMessageHandler<PacketEldArmorNBTSync, IMessage> {
	
	public NBTTagCompound tag;
	public String player;
	
	public PacketEldArmorNBTSync() {
	}
	
	public PacketEldArmorNBTSync(NBTTagCompound nbt, String name) {
		player = name;
		tag = nbt;
	}
	
	@Override
	public IMessage onMessage(PacketEldArmorNBTSync message, MessageContext ctx) {
		if (ctx.side == Side.SERVER) {
			PacketEldArmorNBTSync mes = (PacketEldArmorNBTSync)message;
			EntityPlayer player = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(mes.player);
			if (player != null) {
				for (int i = 0; i < 4; i++) {
					if (player.getCurrentArmor(i) == null)
						return null;
					if (!(player.getCurrentArmor(i).getItem() instanceof ItemEldritchArmor)) {
						return null;
					}
				}
				player.getCurrentArmor(0).setTagCompound(mes.tag);
			}
		}
		return null;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, tag);
		ByteBufUtils.writeUTF8String(buf, player);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		tag = ByteBufUtils.readTag(buf);
		player = ByteBufUtils.readUTF8String(buf);
	}
}
