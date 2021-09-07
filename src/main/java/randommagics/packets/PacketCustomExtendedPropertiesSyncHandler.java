package randommagics.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import randommagics.customs.CustomExtendedEntityProperties;

public class PacketCustomExtendedPropertiesSyncHandler implements IMessageHandler<PacketCustomExtendedPropertiesSync, IMessage> {
	
	public PacketCustomExtendedPropertiesSyncHandler() {
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IMessage onMessage(PacketCustomExtendedPropertiesSync message, MessageContext ctx) {
		if (ctx.side.isClient()) {
			CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(net.minecraft.client.Minecraft.getMinecraft().thePlayer);
			/*
			ex.madnessLvl = message.madnessLvl;
			ex.curses = message.curses;*/
			ex.loadNBTData(message.nbt);
		}
		return null;
	}
/*
	@Override
	public IMessage onMessage(IMessage message, MessageContext ctx) {
		return onMessage((PacketCustomExtendedPropertiesSync)message, ctx);
	}*/
	
	

}
