package randommagics.gui;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import randommagics.KeyHandler;
import randommagics.dimensions.CustomTeleporter;
import randommagics.packets.PacketEldArmorNBTSync;
import randommagics.packets.PacketTPRequest;
import randommagics.packets.PacketTPRequest.RequestType;
import randommagics.packets.RmNetworkRegistry;
import randommagics.render.RenderUtils;

public class GuiEldArmor extends GuiScreen{
	
	private int guiHeight = 177; //147
	private int guiWidth = 181;

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		int guiX = (width - guiWidth) / 2;
		int guiY = (height - guiHeight) / 2;
	    drawDefaultBackground();
	    GL11.glColor4f(1, 1, 1, 1);
	    mc.renderEngine.bindTexture(new ResourceLocation("randommagics", "textures/gui/EldArmorGuiBkground.png"));
	    this.drawTexturedModalRect(guiX, guiY, 0, 0, guiWidth, guiHeight);
	    super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public boolean doesGuiPauseGame() {
	    return false;
	}
	
	@Override
	public void initGui() {
		
		super.initGui();
		int step = 0;
		this.buttonList.add(new GuiButton(1, (width - guiWidth) / 2 + 10, (height - guiHeight) / 2 + 10 + 20 * step++, 150, 20, "Toggle Night Vision"));
		this.buttonList.add(new GuiButton(2, (width - guiWidth) / 2 + 10, (height - guiHeight) / 2 + 10 + 20 * step++, 150, 20, "Toggle Speed Boost"));
		this.buttonList.add(new GuiButton(3, (width - guiWidth) / 2 + 10, (height - guiHeight) / 2 + 10 + 20 * step++, 150, 20, "Set TP Point"));
		this.buttonList.add(new GuiButton(4, (width - guiWidth) / 2 + 10, (height - guiHeight) / 2 + 10 + 20 * step++, 150, 20, "TP To Point"));
		this.buttonList.add(new GuiButton(5, (width - guiWidth) / 2 + 10, (height - guiHeight) / 2 + 10 + 20 * step++, 150, 20, "Toggle Auto Step"));
	}
	
	@Override
	protected void keyTyped(char c, int num)
	{
	    if(num == Keyboard.KEY_ESCAPE || num == Keyboard.KEY_E)
	    {
	        Minecraft.getMinecraft().thePlayer.closeScreen();
	    }
	}
	
	String NVision = "nightvision";
	
	protected void actionPerformed(GuiButton button) {
		ItemStack stack = Minecraft.getMinecraft().thePlayer.getCurrentArmor(0);
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		NBTTagCompound nbt = stack.getTagCompound();
		switch(button.id) {
			case 1:
					if(!nbt.hasKey(NVision) || !nbt.getBoolean(NVision)) {
						nbt.setBoolean(NVision, true);
						//Minecraft.getMinecraft().thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 50, 1, false));
					} else {
						nbt.setBoolean(NVision, false);
						//Minecraft.getMinecraft().thePlayer.removePotionEffect(Potion.nightVision.id);
					}
				break;
			case 2:
				if (nbt.hasKey("speedboost"))
					nbt.setBoolean("speedboost", !nbt.getBoolean("speedboost"));
				else
					nbt.setBoolean("speedboost", false);
				break;
			case 3:
				String s = player.getPosition(1).toString();
				s = s.substring(1, s.length() - 1);
				s += ", " + player.dimension;
				stack.stackTagCompound.setString("point", s);
				break;
			case 4:
				if (stack.stackTagCompound.hasKey("point")) {
					String point = stack.stackTagCompound.getString("point");
					//point = point.substring(1, point.length()-1);
					point = point + ",";
					double[] ar = new double[4];
					int n = 0;
					int li = 0;
					for (int i = 0; i < point.length(); i++) {
						if (point.charAt(i) == ',') {
							ar[n] = Double.parseDouble(point.substring(li, i));
							n++;
							li = i + 2;
						}
					}
					if(player.dimension == (int)ar[3]) {
						player.closeScreen();
						//player.setPosition(ar[0], ar[1], ar[2]);
						RmNetworkRegistry.NETWORK.sendToServer(new PacketTPRequest(RequestType.EldritchArmor, player.getCommandSenderName(), player.dimension, ar[0], ar[1], ar[2]));
					}
				}
				break;
			case 5:
				stack.getTagCompound().setBoolean("autostep", !stack.getTagCompound().getBoolean("autostep"));
		}
		RmNetworkRegistry.NETWORK.sendToServer(new PacketEldArmorNBTSync(nbt, player.getCommandSenderName()));
	}
	
}
