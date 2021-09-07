package randommagics.packets;

import java.nio.charset.Charset;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class PacketTPRequest implements IMessage {
	
	public static enum RequestType {
		NoReasons,
		EldritchArmor
	}
	
	RequestType reason;
	double x, y, z;
	int world;
	String player;
	
	public PacketTPRequest() {
		reason = RequestType.NoReasons;
	}
	
	public PacketTPRequest(RequestType type, String name, int worldId, double x, double y, double z) {
		this.reason = type;
		player = name;
		world = worldId;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		reason = RequestType.values()[buf.readInt()];
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		world = buf.readInt();
		byte[] b = new byte[buf.readInt()];
		buf.readBytes(b);
		player = new String(b, Charset.defaultCharset());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(reason.ordinal());
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeInt(world);
		buf.writeInt(player.length());
		buf.writeBytes(player.getBytes(Charset.defaultCharset()));
	}

}