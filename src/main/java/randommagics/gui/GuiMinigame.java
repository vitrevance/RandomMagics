package randommagics.gui;

import java.util.ArrayList;
import java.util.Random;

import javax.vecmath.Point2f;
import javax.vecmath.Vector3f;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import randommagics.customs.DemonBossFight;
import randommagics.customs.DemonBossFight.Minigame;
import randommagics.customs.DemonBossFight.Minigame.Node;
import randommagics.customs.DemonBossFight.Minigame.Player;
import randommagics.customs.RandomUtils;
import randommagics.customs.Sound;
import randommagics.packets.PacketDemonBossFightEvent;
import randommagics.packets.RmNetworkRegistry;
import randommagics.render.RenderUtils;

public class GuiMinigame extends Gui {
	
	private static DemonBossFight demonBossFight = null;
	private static ResourceLocation mapTexture = new ResourceLocation("randommagics", "textures/gui/diferium.png");
	
	private int playerSize = 8;
	private static boolean wasPaused = false;
	
	public GuiMinigame() {
		super();
	}
	
	public static void setDemonBossFight(DemonBossFight fight) {
		demonBossFight = fight;
		wasPaused = false;
	}
	
	@SubscribeEvent
	public void onRenderGameOverlayEventPost(RenderGameOverlayEvent.Post event) {
		if (demonBossFight != null 
			&& demonBossFight.getPlayer().getCommandSenderName()
			.contentEquals(Minecraft.getMinecraft().thePlayer.getCommandSenderName())
			&& event.type == ElementType.TEXT) {
			
			if (Minecraft.getMinecraft().isGamePaused()) {
				wasPaused = true;
				return;
			}
			else {
				if (wasPaused) {
					demonBossFight.onFinish(true);
					RmNetworkRegistry.NETWORK.sendToServer(new PacketDemonBossFightEvent(Minecraft.getMinecraft().thePlayer.getCommandSenderName(), 2));
					return;					
				}
			}
			
			GL11.glPushMatrix();
			int width = event.resolution.getScaledWidth();
			int height = event.resolution.getScaledHeight();
			
			int fieldSize = Math.min(width, height);
			int centerX = width / 2;
			int centerY = height / 2;
			int topLeftCornerX = centerX - fieldSize / 2;
			int topLeftCornerY = centerY - fieldSize / 2;
			int bottomRightCornerX = topLeftCornerX + fieldSize;
			int bottomRightCornerY = topLeftCornerY + fieldSize;
			
			playerSize = fieldSize / 50;
			
			int colorBlack = 255 << 24;
			int colorWhite = (255 << 24) + (255 << 16) + (255 << 8) + 255;
			int colorField = (255 << 24) + (21 << 16) + (21 << 8) + 41;
			int colorRed = (255 << 24) + (255 << 16);
			int colorBlue = (255 << 24) + (0 << 16) + (255 << 8) + 255;
			
			drawRect(0, 0, width, height, colorBlack);
			drawRect(topLeftCornerX, topLeftCornerY, bottomRightCornerX, bottomRightCornerY, colorField);
			
			long time = System.currentTimeMillis() - demonBossFight.getStartTime();
			
			//PLAY MUSIC
			if (time < 1000) {
				if (!Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(Sound.minigame_normal)) {
					Minecraft.getMinecraft().getSoundHandler().playSound(Sound.minigame_normal);
				}
			}
			
			//NOTES
			ArrayList<Attacker> list = getActiveAttackers(time);
			
			long apprRate = this.demonBossFight.getMinigame().getApproachRate();
			Player pl = this.demonBossFight.getMinigame().getPlayer();
			boolean isPlayerAttacked = false;
			
			for (Attacker it : list) {
				GL11.glPushMatrix();
				
				float nx = (float)it.x / 512f * fieldSize + topLeftCornerX;
				float ny = (float)it.y / 384f * fieldSize + topLeftCornerY;
				
				GL11.glTranslatef(nx, ny, 0);
				GL11.glRotated(it.angle * 180 / Math.PI, 0, 0, 1);
				
				int colorFill = (100 << 24) + ((int)(((1f - ((float)-it.charge / apprRate))) * 255) << 16);
				if (it.charge >= 0) {
					colorFill = colorRed;
					isPlayerAttacked = isPlayerAttacked || checkCollision(pl.x + centerX, pl.y + centerY, nx, ny, it.angle);
				}
				drawRect(-width, -playerSize, width, playerSize, colorFill);
				
				GL11.glPopMatrix();
			}
			
			//PLAYER CONTROLLS
			boolean isUp = Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode());
			boolean isDown = Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode());
			boolean isLeft = Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode());
			boolean isRight = Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode());
			
			float velocityX = (isRight ? 1 : 0) + (isLeft ? -1 : 0);
			float velocityY = (isUp ? -1 : 0) + (isDown ? 1 : 0);
			
			pl.x += velocityX * (time - pl.prevTime) / 50f * playerSize;
			pl.y += velocityY * (time - pl.prevTime) / 50f * playerSize;
			
			pl.x = RandomUtils.clampf(pl.x, -fieldSize / 2 + playerSize, fieldSize / 2 - playerSize);
			pl.y = RandomUtils.clampf(pl.y, -fieldSize / 2 + playerSize, fieldSize / 2 - playerSize);
			
			pl.prevTime = time;
			
			GL11.glPushMatrix();
			
			GL11.glTranslatef(pl.x + centerX, pl.y + centerY, 0);
			if (isPlayerAttacked) {
				drawRect(-playerSize, -playerSize, playerSize, playerSize, colorRed);
			}
			else {
				drawRect(-playerSize, -playerSize, playerSize, playerSize, colorBlue);
			}
			
			GL11.glPopMatrix();
			
			GL11.glPopMatrix();
			
			Minigame minigame = this.demonBossFight.getMinigame();
			
			if (isPlayerAttacked && pl.lastHit + 100 < time) {
				pl.lastHit = time;
				RmNetworkRegistry.NETWORK.sendToServer(new PacketDemonBossFightEvent(Minecraft.getMinecraft().thePlayer.getCommandSenderName(), 0));
				if (!Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(Sound.minigame_hit)) {
					Minecraft.getMinecraft().getSoundHandler().playSound(Sound.minigame_hit);
				}
			}
			
			if (minigame.getNode(minigame.mapSize() - 1).time + minigame.getAttackTime() + 5000 < time) {
				if (demonBossFight != null) {
					demonBossFight.onFinish(true);
				}
				RmNetworkRegistry.NETWORK.sendToServer(new PacketDemonBossFightEvent(Minecraft.getMinecraft().thePlayer.getCommandSenderName(), 1));
			}
		}
	}
	
	private ArrayList<Attacker> getActiveAttackers(long timePassed) {
		ArrayList<Attacker> list = new ArrayList<GuiMinigame.Attacker>();
		Minigame minigame = this.demonBossFight.getMinigame();
		for (Node node : minigame.getNodes()) {
			if (node.time + minigame.getAttackTime() >= timePassed) {
				if (node.time <= timePassed + minigame.getApproachRate()) {
					Attacker at = new Attacker(node.x, node.y, node.direction, (int)(timePassed - node.time));
					list.add(at);
				}
			}
		}
		
		return list;
	}
	
	private boolean checkCollision(float x, float y, float ax, float ay, float angle) {
		float dx = (float)Math.cos(angle);
		float dy = (float)Math.sin(angle);
		
		float mx = x - ax;
		float my = y - ay;
		float dt = mx * dx + my * dy;
		x -= ax + dx * dt;
		y -= ay + dy * dt;
		float dist = (float)Math.sqrt(x*x + y*y);
		return dist < playerSize * 2;
	}
	
	private static class Attacker {
		public final int x, y;
		public final float angle;
		public final int charge;
		
		private Attacker(int x, int y, float angle, int charge) {
			this.x = x;
			this.y = y;
			this.angle = angle;
			this.charge = charge;
		}
	}
}
