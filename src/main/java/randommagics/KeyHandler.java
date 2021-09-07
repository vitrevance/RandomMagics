package randommagics;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import randommagics.packets.PacketToggleDemonAbilities;
import randommagics.packets.RmNetworkRegistry;

public class KeyHandler {
	
	private static final String desc = "Eldritch Armor GUI";
	private static final int keyValues = Keyboard.KEY_P;
	public static KeyBinding keyP;
	public static KeyBinding keyDemonAbilityMenu;
	public static KeyBinding keyActivateDemonAbilities;
	
	public KeyHandler() {
		keyP = new KeyBinding(desc, keyValues, "Random Magics");
		ClientRegistry.registerKeyBinding(keyP);
		keyDemonAbilityMenu = new KeyBinding("Demon Ability GUI", Keyboard.KEY_F, "Random Magics");
		ClientRegistry.registerKeyBinding(keyDemonAbilityMenu);
		keyActivateDemonAbilities = new KeyBinding("Toggle Demon Ability", Keyboard.KEY_R, "Random Magics");
		ClientRegistry.registerKeyBinding(keyActivateDemonAbilities);
		
	}
	
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (Minecraft.getMinecraft().thePlayer.isPotionActive(Config.potionStuckInTimeId)) {
			KeyBinding.unPressAllKeys();
		}
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		World world = Minecraft.getMinecraft().theWorld;
		if(!FMLClientHandler.instance().isGUIOpen(GuiChat.class) && keyP.isPressed()) {
			if(player.getCurrentArmor(0) != null
					&& player.getCurrentArmor(1) != null
					&& player.getCurrentArmor(2) != null
					&& player.getCurrentArmor(3) != null
					&& player.getCurrentArmor(3).getItem() == Init.EldHelmet
					&& player.getCurrentArmor(2).getItem() == Init.EldChestplate
					&& player.getCurrentArmor(1).getItem() == Init.EldLeggins
					&& player.getCurrentArmor(0).getItem() == Init.EldBoots)
			player.openGui(Main.instance, 0, world, (int)player.posX, (int)player.posY, (int)player.posZ);
		}
		if(!FMLClientHandler.instance().isGUIOpen(GuiChat.class)) {
			if (keyActivateDemonAbilities.isPressed()) {
				RmNetworkRegistry.NETWORK.sendToServer(new PacketToggleDemonAbilities(player.getCommandSenderName()));
				//RmNetworkRegistry.NETWORK.sendToServer(new PacketToggleDemonAbilities(player.getPersistentID()));
			}
		}
	}
}
