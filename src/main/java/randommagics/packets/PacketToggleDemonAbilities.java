package randommagics.packets;

import java.util.UUID;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import randommagics.customs.CustomExtendedEntityProperties;
import randommagics.customs.RandomUtils;

public class PacketToggleDemonAbilities implements IMessage, IMessageHandler<PacketToggleDemonAbilities, IMessage> {
	
	public String player;
	
	//public UUID player;
	
	public PacketToggleDemonAbilities() {
	}
	
	public PacketToggleDemonAbilities(String name) {
		player = name;
	}
	
	/*
	public PacketToggleDemonAbilities(UUID name) {
		player = name;
	}
	*/
	@Override
	public IMessage onMessage(PacketToggleDemonAbilities message, MessageContext ctx) {
		if (ctx.side == Side.SERVER) {
			EntityPlayer player = RandomUtils.getServerEntityPlayerByName(message.player);
			if (player != null) {
				CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(player);
				ex.usesdemonability = !ex.usesdemonability;
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
