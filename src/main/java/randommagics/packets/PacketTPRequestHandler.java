package randommagics.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import randommagics.customs.RandomUtils;
import randommagics.items.ItemEldritchArmor;
import randommagics.packets.PacketTPRequest.RequestType;

public class PacketTPRequestHandler implements IMessageHandler<PacketTPRequest, IMessage> {
	
	public PacketTPRequestHandler() {
	}

	@Override
	public IMessage onMessage(PacketTPRequest message, MessageContext ctx) {
		if (ctx.side == Side.SERVER) {
			if (message.reason == RequestType.NoReasons) {
				return null;
			}
			if (message.reason == RequestType.EldritchArmor) {
				//EntityPlayer player = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(message.player);
				EntityPlayer player = RandomUtils.getServerEntityPlayerByName(message.player);
				if (player == null)
					return null;
				for (int i = 0; i < 4; i++) {
					if (player.getCurrentArmor(i) == null)
						return null;
					if (!(player.getCurrentArmor(i).getItem() instanceof ItemEldritchArmor)) {
						return null;
					}
				}
				player.setPositionAndUpdate(message.x, message.y, message.z);
				return null;
			}
		}
		return null;
	}

}
