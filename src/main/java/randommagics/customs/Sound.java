package randommagics.customs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.ResourceLocation;

public class Sound implements ISound {
	
	public static Sound heartbeat = new Sound(new ResourceLocation("randommagics", "ambient.heartbeat"), true).setVolume(25).setPitch(1);
	public static Sound minigame_normal = new Sound(new ResourceLocation("randommagics", "minigame.normal"), false).setVolume(0.4f).setPitch(1);
	public static Sound minigame_hit = new Sound(new ResourceLocation("randommagics", "spell.wind"), false).setVolume(0.5f).setPitch(1);
	
	private ResourceLocation source;
	private boolean repeat;
	private float volume;
	private float pitch;
	
	public Sound() {
		this.source = null;
		this.repeat = false;
		this.volume = 1;
		this.pitch = 1;
	}
	
	public Sound(ResourceLocation source, boolean canRepeat) {
		this.source = source;
		this.repeat = canRepeat;
		this.volume = 1;
		this.pitch = 1;
	}
	
	public Sound setVolume(float value) {
		this.volume = value;
		return this;
	}
	
	public Sound setPitch(float value) {
		this.pitch = value;
		return this;
	}

	@Override
	public ResourceLocation getPositionedSoundLocation() {
		return this.source;
	}

	@Override
	public boolean canRepeat() {
		return this.repeat;
	}

	@Override
	public int getRepeatDelay() {
		return 0;
	}

	@Override
	public float getVolume() {
		return this.volume;
	}

	@Override
	public float getPitch() {
		return this.pitch;
	}

	@Override
	public float getXPosF() {
		return 0;
	}

	@Override
	public float getYPosF() {
		return 0;
	}

	@Override
	public float getZPosF() {
		return 0;
	}

	@Override
	public AttenuationType getAttenuationType() {
		return AttenuationType.NONE;
	}
}
