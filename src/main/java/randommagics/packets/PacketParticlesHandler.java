package randommagics.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;

public class PacketParticlesHandler implements IMessageHandler<PacketParticles, IMessage> {
	
	public PacketParticlesHandler() {
	}
	
	@Override
	public IMessage onMessage(PacketParticles m, MessageContext ctx) {
		if (ctx.side.isClient()) {
			Minecraft.getMinecraft().theWorld.spawnParticle("smoke", m.x, m.y, m.z, m.velX, m.velY, m.velZ);
		}
		return null;
	}

}
