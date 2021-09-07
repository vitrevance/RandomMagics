package randommagics.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import randommagics.customs.CustomExtendedEntityProperties;
import randommagics.customs.RandomUtils;

public class PacketUseDemonAbility implements IMessage, IMessageHandler<PacketUseDemonAbility, IMessage> {
	
	public String player;
	
	public PacketUseDemonAbility() {
		
	}
	
	public PacketUseDemonAbility(String name) {
		player = name;
	}
	
	@Override
	public IMessage onMessage(PacketUseDemonAbility message, MessageContext ctx) {
		if (ctx.side == Side.SERVER) {
			//EntityPlayer player = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(message.player);
			EntityPlayer player = RandomUtils.getServerEntityPlayerByName(message.player);
			if (player != null) {
				CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(player);
				ex.useDemonAbility();
				ex.sync(player);
			}
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		player = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, player);
	}

}
