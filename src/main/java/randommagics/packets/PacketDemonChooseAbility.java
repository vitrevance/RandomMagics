package randommagics.packets;

import java.nio.charset.Charset;
import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class PacketDemonChooseAbility implements IMessage {
	
	public int ability;
	public String name;
	//public UUID name;
	
	public PacketDemonChooseAbility() {
	}
	
	public PacketDemonChooseAbility(String playerName, int ability) {
		this.name = playerName;
		this.ability = ability;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		ability = buf.readInt();
		byte[] b = new byte[buf.readInt()];
		buf.readBytes(b);
		//name = UUID.nameUUIDFromBytes(b);
		name = new String(b, Charset.defaultCharset());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		//System.out.println("name p " + name.toString());
		buf.writeInt(ability);
		buf.writeInt(name.length());
		buf.writeBytes(name.getBytes(Charset.defaultCharset()));
	}

}
