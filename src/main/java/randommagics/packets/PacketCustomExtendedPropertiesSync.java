package randommagics.packets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import randommagics.curses.Curse;
import randommagics.customs.CustomExtendedEntityProperties;

public class PacketCustomExtendedPropertiesSync implements IMessage {
	
	public int madnessLvl;
	public ArrayList<Curse> curses;
	public int cursessize;
	NBTTagCompound nbt = new NBTTagCompound();
	
	public PacketCustomExtendedPropertiesSync(CustomExtendedEntityProperties value) {
		madnessLvl = value.madnessLvl;
		curses = value.curses;
		cursessize = curses.size();
		value.saveNBTData(nbt);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		/*
		curses = new ArrayList<Curse>();
		cursessize = buf.readInt();
		for (int i = 0; i < cursessize; i++) {
			try {
				int len = buf.readInt();
				byte[] b = new byte[len];
				buf.readBytes(b);
				curses.add((Curse)toObject(b));
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		madnessLvl = buf.readInt();*/
		/*
		int len = buf.readInt();
		ByteBuf b = ByteBufUtils.;
		buf.readBytes(b);
		ByteBufInputStream in = new ByteBufInputStream(ByteBufUtil.);
		try {
			nbt = CompressedStreamTools.readCompressed(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}
		nbt = ByteBufUtils.readTag(buf);
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		/*
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			CompressedStreamTools.writeCompressed(nbt, outputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buf.writeInt(bos.toByteArray().length);
		buf.writeBytes(bos.toByteArray());
		
		buf.writeInt(cursessize);
		Iterator<Curse> i = curses.iterator();
		while(i.hasNext()) {
			try {
				byte[] b = toByteArray(i.next());
				buf.writeInt(b.length);
				buf.writeBytes(b);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		buf.writeInt(madnessLvl);*/
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}
		ByteBufUtils.writeTag(buf, nbt);
		
	}
	
	public PacketCustomExtendedPropertiesSync() {
	}
	
	public static byte[] toByteArray(Object obj) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
        return bytes;
    }

    public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (ois != null) {
                ois.close();
            }
        }
        return obj;
    }
}
