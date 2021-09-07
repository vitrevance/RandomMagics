package randommagics.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import randommagics.customs.DemonBossFight;
import randommagics.customs.RandomUtils;
import thaumcraft.api.damagesource.DamageSourceIndirectThaumcraftEntity;

public class PacketDemonBossFightEvent implements IMessage, IMessageHandler<PacketDemonBossFightEvent, IMessage> {
	
	public int eventType;
	public String playerName;
	
	public PacketDemonBossFightEvent() {
	}
	
	public PacketDemonBossFightEvent(String playerName, int type) {
		this.eventType = type;
		this.playerName = playerName;
	}
	
	@Override
	public IMessage onMessage(PacketDemonBossFightEvent message, MessageContext ctx) {
		if (ctx.side == Side.SERVER) {
			EntityPlayer player = RandomUtils.getServerEntityPlayerByName(message.playerName);
			if (message.eventType == 0) {
				if (player != null) {
					DamageSource source = DamageSource.causeMobDamage(player);
					source.damageType = "disintegration";
					player.attackEntityFrom(source, 110);
				}
			}
			if (message.eventType == 1) {
				if (DemonBossFight.getCurrentFight() != null) {
					DemonBossFight.getCurrentFight().onFinish(false);
				}
			}
			if (message.eventType == 2) {
				if (DemonBossFight.getCurrentFight() != null) {
					DemonBossFight.getCurrentFight().setLoose();
					DemonBossFight.getCurrentFight().onFinish(false);
				}
			}
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.playerName = ByteBufUtils.readUTF8String(buf);
		this.eventType = ByteBufUtils.readVarInt(buf, 1);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, playerName);
		ByteBufUtils.writeVarInt(buf, this.eventType, 1);
	}
	
	
}