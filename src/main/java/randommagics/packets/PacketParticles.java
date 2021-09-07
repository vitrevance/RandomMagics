package randommagics.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import thaumcraft.client.fx.ParticleEngine;

public class PacketParticles implements IMessage {
	
	public double x, y, z, velX, velY, velZ;
	public int type;
	
	public PacketParticles() {
	}
	
	public PacketParticles(double x, double y, double z, double velX, double velY, double velZ, int name) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.velX = velX;
		this.velY = velY;
		this.velZ = velZ;
		this.type = name;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		velX = buf.readDouble();
		velY = buf.readDouble();
		velZ = buf.readDouble();
		type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeDouble(velX);
		buf.writeDouble(velY);
		buf.writeDouble(velZ);
		buf.writeInt(type);
	}

}
