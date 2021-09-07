package randommagics.packets;

import org.apache.logging.log4j.core.jmx.Server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import randommagics.customs.CustomExtendedEntityProperties;
import randommagics.customs.RandomUtils;

public class PacketDemonChooseAbilityHandler implements IMessageHandler<PacketDemonChooseAbility, IMessage> {
	
	public PacketDemonChooseAbilityHandler() {
		
	}

	@Override
	public IMessage onMessage(PacketDemonChooseAbility message, MessageContext ctx) {
		if (ctx.side.isServer()) {
			EntityPlayer player = RandomUtils.getServerEntityPlayerByName(message.name);
			if (player == null)
				return null;
			
			CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(player);
			if (ex.demonLevel > message.ability + 2 && message.ability !=  ex.demonability) {
				ex.demonability = message.ability;
				ex.sync(player);
			}
		}
		return null;
	}

}
