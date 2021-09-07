package randommagics.customs;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import scala.Array;

public class StructureHelper {
	
	private ArrayList<MemberInfo> struct = new ArrayList<MemberInfo>();
	private ArrayList<BlockInfo> reg = new ArrayList<BlockInfo>();
	
	private int p1x, p1y, p1z, p2x, p2y, p2z, relx, rely, relz, relID, curID;
	
	public StructureHelper() {
		this.setPos1(0, 0, 0);
		this.setPos2(0, 0, 0);
		this.setRel(0, 0, 0);
		this.relID = 0;
		this.curID = 0;
	}
	
	public StructureHelper(String str) {
		this.setPos1(0, 0, 0);
		this.setPos2(0, 0, 0);
		this.setRel(0, 0, 0);
		this.relID = 0;
		this.curID = 0;
		this.fromStr(str);
	}
	
	public void setPos1(int x, int y, int z) {
		this.p1x = x;
		this.p1y = y;
		this.p1z = z;
	}
	
	public void setPos2(int x, int y, int z) {
		this.p2x = x;
		this.p2y = y;
		this.p2z = z;
	}
	
	public StructureHelper setRel(int x, int y, int z) {
		this.relx = x;
		this.rely = y;
		this.relz = z;
		return this;
	}
	
	public void readStruct(World world, boolean mindAir) {
		for (int x = Math.min(this.p1x, this.p2x); x <= Math.max(this.p1x, this.p2x); x++) {
			for (int y = Math.min(this.p1y, this.p2y); y <= Math.max(this.p1y, this.p2y); y++) {
				for (int z = Math.min(this.p1z, this.p2z); z <= Math.max(this.p1z, this.p2z); z++) {
					Block block = world.getBlock(x, y, z);
					if (block == Blocks.air && !mindAir) {
						continue;
					}
					int meta = world.getBlockMetadata(x, y, z);
					BlockInfo blockInfo = null;
					for (int id = 0; id < this.reg.size(); id++) {
						BlockInfo it = this.reg.get(id);
						if (it.block == block) {
							if (!it.mindMeta || (it.mindMeta && it.meta == meta)) {
								blockInfo = it;
								this.curID = id;
								break;
							}
						}
					}
					if (blockInfo == null) {
						blockInfo = new BlockInfo(block, meta);
						this.reg.add(blockInfo);
						this.curID = this.relID;
						this.relID++;
					}
					this.struct.add(new MemberInfo(this.curID, x - this.relx, y - this.rely, z - this.relz));
				}
			}
		}
	}
	
	public boolean checkStruct(World world) {
		for (MemberInfo it : this.struct) {
			int x = it.x + this.relx;
			int y = it.y + this.rely;
			int z = it.z + this.relz;
			Block block = world.getBlock(x, y, z);
			BlockInfo blockInfo = this.reg.get(it.relID);
			
			if (block != blockInfo.block) {
				//System.out.println(block.getLocalizedName() + " " + blockInfo.block.getLocalizedName() + " " + x + " " + y + " " + z);
				return false;
			}
			if (blockInfo.mindMeta) {
				int meta = world.getBlockMetadata(x, y, z);
				if (meta != blockInfo.meta) {
					//System.out.print(block.getLocalizedName() + " " + blockInfo.block.getLocalizedName() + " ");
					//System.out.println(meta + " " + blockInfo.meta);
					return false;
				}
			}
		}
		return true;
	}
	
	public StructureHelper reg(BlockInfo blockInfo) {
		this.reg.add(blockInfo);
		return this;
	}
	
	public StructureHelper reg(Block b) {
		this.reg.add(new BlockInfo(b));
		return this;
	}
	
	public StructureHelper reg(Block b, int meta) {
		this.reg.add(new BlockInfo(b, meta));
		return this;
	}
	
	public void clearReg() {
		this.reg.clear();
	}
	
	public String getReg() {
		String ret = "";
		for (BlockInfo it : this.reg) {
			ret += it.block.getLocalizedName() + " " + it.meta + " " + it.mindMeta + '\n';
		}
		return ret;
	}
	
	public String toStr() {
		String ret = "";
		for (MemberInfo it : this.struct) {
			ret += it.toStr();
			ret += "|";
		}
		return ret;
	}
	
	public void fromStr(String str) {
		this.struct.clear();
		while (str.length() > 0) {
			int nexti = str.indexOf('|');
			MemberInfo mem = new MemberInfo();
			mem.fromStr(str.substring(0, nexti));
			this.struct.add(mem);
			str = str.substring(nexti + 1);
		}
	}
	
	public class MemberInfo {
		public int x, y, z, relID;
		public MemberInfo() {
			this.relID = 0;
			this.x = 0;
			this.y = 0;
			this.z = 0;
		}
		
		public MemberInfo(int relID, int xx, int yy, int zz) {
			this.relID = relID;
			this.x = xx;
			this.y = yy;
			this.z = zz;
		}
		
		public String toStr() {
			return this.relID + "/" + this.x + "/" + this.y + "/" + this.z;
		}
		
		public void fromStr(String str) {
			int nexti = str.indexOf('/');
			this.relID = Integer.parseInt(str.substring(0, nexti));
			str = str.substring(nexti + 1);
			
			nexti = str.indexOf('/');
			this.x = Integer.parseInt(str.substring(0, nexti));
			str = str.substring(nexti + 1);
			
			nexti = str.indexOf('/');
			this.y = Integer.parseInt(str.substring(0, nexti));
			str = str.substring(nexti + 1);
			
			this.z = Integer.parseInt(str);
		}
	}
	
	public class BlockInfo {
		public Block block;
		public int meta;
		public boolean mindMeta;
		
		public BlockInfo(Block b, int meta) {
			this.block = b;
			this.meta = meta;
			this.mindMeta = true;
		}
		
		public BlockInfo(Block b) {
			this.block = b;
			this.meta = 0;
			this.mindMeta = false;
		}
	}
}
