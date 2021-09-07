package randommagics.packets;

import java.util.HashMap;
import java.util.Set;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import randommagics.EventHandler;
import randommagics.customs.CustomExtendedEntityProperties;
import randommagics.customs.RandomUtils;

public class PacketSyncOverlordArmorAnim implements IMessage, IMessageHandler<PacketSyncOverlordArmorAnim, IMessage> {
	
	public String key;
	public int val;
	
	public PacketSyncOverlordArmorAnim() {
	}
	
	public PacketSyncOverlordArmorAnim(String k, int v) {
		key = k;
		val = v;
	}
	
	@Override
	public IMessage onMessage(PacketSyncOverlordArmorAnim message, MessageContext ctx) {
		if (ctx.side == Side.CLIENT) {
			EventHandler.dodgeAnim.put(message.key, message.val);
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		key = ByteBufUtils.readUTF8String(buf);
		val = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, key);
		buf.writeInt(val);
	}

}
