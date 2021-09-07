package randommagics.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import randommagics.customs.DemonBossFight;
import randommagics.customs.RandomUtils;

public class PacketBeginDemonBossFight implements IMessage, IMessageHandler<PacketBeginDemonBossFight, IMessage> {
	
	private String playerName;
	
	public PacketBeginDemonBossFight() {
	}
	
	public PacketBeginDemonBossFight(String name) {
		this.playerName = name;
	}
	
	@Override
	public IMessage onMessage(PacketBeginDemonBossFight message, MessageContext ctx) {
		if (ctx.side == Side.CLIENT) {
			EntityPlayer player = RandomUtils.getEntityPlayerByName(message.playerName, true);
			if (player != null) {
				DemonBossFight dbf = DemonBossFight.beginDemonBossFight(player);
			}
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		playerName = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, playerName);
	}
	
	
}