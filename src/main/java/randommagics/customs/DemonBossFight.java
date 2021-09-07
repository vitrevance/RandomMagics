package randommagics.customs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.WritableRectangle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import randommagics.Init;
import randommagics.customs.DemonBossFight.Minigame.Node;
import randommagics.gui.GuiMinigame;
import scala.annotation.meta.param;

public class DemonBossFight {
	
	private static DemonBossFight currentFight = null;
	
	private EntityPlayer player;
	private String playerName;
	
	private Minigame minigame;
	private long startTime;
	private boolean isWin;
	
	private DemonBossFight(EntityPlayer player) {
		this.player = player;
		this.playerName = player.getCommandSenderName();
		this.onBegin();
	}
	
	public void onUpdate(World world) {
		if (this.player == null) {
			EntityPlayer foundPlayer = RandomUtils.getEntityPlayerByName(this.playerName, world.isRemote);
			if (foundPlayer == null) {
				this.isWin = false;
				this.onFinish(world.isRemote);
			}
			else {
				this.player = foundPlayer;
			}
		}
		else {
			if (this.player.isDead) {
				this.isWin = false;
				this.onFinish(world.isRemote);
			}
		}
	}
	
	public void onBegin() {
		this.minigame = new Minigame();
		this.minigame.load(RandomUtils.readFile("minigames/normal.map"));
		
		this.startTime = System.currentTimeMillis();
		this.isWin = true;
	}
	
	public EntityPlayer getPlayer() {
		return this.player;
	}
	
	public Minigame getMinigame() {
		return this.minigame;
	}
	
	public long getStartTime() {
		return this.startTime;
	}
	
	public void setLoose() {
		this.isWin = false;
	}
	
	public void onFinish(boolean isRemote) {
		System.out.println("1 " + isRemote);
		if (isRemote || Minecraft.getMinecraft().isIntegratedServerRunning()) {
			System.out.println("2 " + isRemote);
			GuiMinigame.setDemonBossFight(null);
			if (Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(Sound.minigame_normal)) {
				Minecraft.getMinecraft().getSoundHandler().stopSound(Sound.minigame_normal);
			}
			if (!Minecraft.getMinecraft().isIntegratedServerRunning()) {
				currentFight = null;
			}
		}
		if (!isRemote) {
			System.out.println("3 " + isRemote);
			currentFight = null;
			if (this.isWin) {
				RandomUtils.spawnItemInWorldAt(this.player.worldObj, new ItemStack(Init.ItemDemonHeart), this.player);
			}
		}
	}
	
	public static DemonBossFight beginDemonBossFight(EntityPlayer player) {
		if (currentFight != null) {
			if (player.worldObj.isRemote) {
				GuiMinigame.setDemonBossFight(currentFight);
				return currentFight;
			}
			else {
				return null;
			}
		}
		currentFight = new DemonBossFight(player);
		if (player.worldObj.isRemote) {
			GuiMinigame.setDemonBossFight(currentFight);
		}
		return currentFight;
	}
	
	public static DemonBossFight getCurrentFight() {
		return currentFight;
	}
	
	public static class Minigame {
		
		private ArrayList<Node> map;
		private long approachRate;
		private long attackTime;
		private Player player;
		
		private Minigame() {
			this.map = new ArrayList<Node>();
			this.approachRate = 800;
			this.attackTime = 50;
			this.player = new Player();
		}
		
		public Player getPlayer() {
			return this.player;
		}
		
		public long getApproachRate() {
			return this.approachRate;
		}
		
		public long getAttackTime() {
			return this.attackTime;
		}
		
		public Node getNode(int i) {
			return this.map.get(i);
		}
		
		public int mapSize() {
			return this.map.size();
		}
		
		public List<Node> getNodes() {
			return this.map;
		}
		
		private void load(String text) {
			String[] nodes = text.split("\n");
			Random rand = new Random();
			float angle = 0;
			for (String node : nodes) {
				if (node.isEmpty()) {
					continue;
				}
				String[] params = node.split(",");
				int x = Integer.parseInt(params[0]);
				int y = Integer.parseInt(params[1]);
				long time = Long.parseLong(params[2]);
				Node nextNode = new Node(x, y, time, angle);
				angle += 0.15f;
				this.map.add(nextNode);
			}
		}
		
		public static class Node {
			public final long time;
			public final int x, y;
			public final float direction;
			
			private Node(int x, int y, long time, float dir) {
				this.x = x;
				this.y = y;
				this.time = time;
				this.direction = dir;
			}
		}
		
		public static class Player {
			public float x, y;
			public long prevTime;
			public long lastHit;
			
			private Player() {
				this.x = 0;
				this.y = 0;
				this.lastHit = 0;
			}
		}
		
	}
}