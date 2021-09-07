package randommagics.packets;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import randommagics.Init;

public class RmNetworkRegistry {
	
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("randommagicsNetwork");
	
	public static void registerMessages() {
		NETWORK.registerMessage(PacketCustomExtendedPropertiesSyncHandler.class, PacketCustomExtendedPropertiesSync.class, 1, Side.CLIENT);
		NETWORK.registerMessage(PacketParticlesHandler.class, PacketParticles.class, 2, Side.CLIENT);
		NETWORK.registerMessage(PacketDemonChooseAbilityHandler.class, PacketDemonChooseAbility.class, 3, Side.SERVER);
		NETWORK.registerMessage(PacketTPRequestHandler.class, PacketTPRequest.class, 4, Side.SERVER);
		NETWORK.registerMessage(PacketEldArmorNBTSync.class, PacketEldArmorNBTSync.class, 5, Side.SERVER);
		NETWORK.registerMessage(PacketToggleDemonAbilities.class, PacketToggleDemonAbilities.class, 6, Side.SERVER);
		NETWORK.registerMessage(PacketUseDemonAbility.class, PacketUseDemonAbility.class, 7, Side.SERVER);
		NETWORK.registerMessage(PacketSyncOverlordArmorAnim.class, PacketSyncOverlordArmorAnim.class, 8, Side.CLIENT);
		NETWORK.registerMessage(PacketBeginDemonBossFight.class, PacketBeginDemonBossFight.class, 9, Side.CLIENT);
		NETWORK.registerMessage(PacketDemonBossFightEvent.class, PacketDemonBossFightEvent.class, 10, Side.SERVER);
	}
	
	public static void sendToAllAround(IMessage message, int dim, double x, double y, double z, double range) {
		NETWORK.sendToAllAround(message, new NetworkRegistry.TargetPoint(dim, x, y, z, range));
	}
}
