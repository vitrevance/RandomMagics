package randommagics.curses;

import java.io.Serializable;
import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import randommagics.customs.CustomExtendedEntityProperties;

public class Curse {
	
	protected boolean isInfinite;
	/**
	 * Is curse stackable. If True, level of curse will be increased by 1 next time, it is applied.
	 */
	protected boolean stackable;
	/**
	 * The level of curse. Incremented every time stackable curse applied again.
	 */
	protected int level;
	protected boolean revokeAfterDeath;
	/**
	 * Time, remaining before curse revokes. Used only if not infinite.
	 */
	protected int timeInTicks;

	private String uniqueID;
	private short isCommandApplied = 0;
	
	private Curse() {
		level = 1;
		isInfinite = false;
		revokeAfterDeath = true;
		timeInTicks = 1;
		stackable = false;
		
		this.uniqueID = "base";
	}
	
	public Curse(String id) {
		level = 1;
		isInfinite = false;
		revokeAfterDeath = true;
		timeInTicks = 1;
		stackable = false;
		
		this.uniqueID = id;
	}
	
	public final void onUpdate(EntityPlayer player) {
		if (this.isCommandApplied == 1) {
			this.onApply(player, player);
			this.isCommandApplied = 2;
		}
		doEffects(player);
		if (!isInfinite)
			timeInTicks--;
		if (revokeAfterDeath && player != null && player.isDead) {
			CurseRegistry.revokeFromPlayer(player, this);
			return;
		}
		if (!isInfinite && timeInTicks <= 0) {
			CurseRegistry.revokeFromPlayer(player, this);
		}
		if (this.isCommandApplied == 3) {
			CurseRegistry.revokeFromPlayer(player, this);
		}
	}
	
	public boolean isInfinte() {
		return this.isInfinite;
	}
	
	public boolean isStackable() {
		return this.stackable;
	}
	
	/**
	 * 
	 * @param amount Value > 0 to increase and Value < 0 to decrease level.
	 * @return True if level has been changed successfully.
	 */
	public boolean addLvl(int amount) {
		if (this.stackable && this.level + amount > 0) {
			this.level += amount;
			return true;
		}
		return false;
	}
	
	/**
	 * Level based functional should be implemented somewhere else.
	 * @return Current level of curse.
	 */
	public int Lvl() {
		return this.stackable ? this.level : 0;
	}
	
	public boolean doeasRevokeOnDeath() {
		return revokeAfterDeath;
	}
	
	public final void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("CurseID", getUniqueID());
		
		nbt.setBoolean("isinfinte", isInfinite);
		nbt.setBoolean("stackable", stackable);
		nbt.setInteger("level", level);
		nbt.setInteger("timeinticks", timeInTicks);
		nbt.setBoolean("revokeondeath", revokeAfterDeath);
		nbt.setShort("isCommand", isCommandApplied);
		
		NBTTagCompound custom = new NBTTagCompound();
		writeCustomToNBT(custom);
		nbt.setTag("custom", custom);
	}
	
	/**
	 * Return curse's ID here. Must return same value as when registering.
	 */
	public final String getUniqueID() {
		return this.uniqueID;
	}

	public final void readFromNBT(NBTTagCompound nbt) {
		this.isInfinite = nbt.getBoolean("isinfinte");
		this.level = nbt.getInteger("level");
		this.stackable = nbt.getBoolean("stackable");
		this.revokeAfterDeath = nbt.getBoolean("revokeondeath");
		this.timeInTicks = nbt.getInteger("timeinticks");
		this.isCommandApplied = nbt.getShort("isCommand");
		
		NBTTagCompound custom = nbt.getCompoundTag("custom");
		readCustomFromNBT(custom);
	}
	
	public final void setCommandApplied() {
		if (this.isCommandApplied == 0)
			this.isCommandApplied = 1;
	}
	
	public final void setCommandRevoke() {
		if (this.isCommandApplied == 0 || this.isCommandApplied == 2) {
			this.isCommandApplied = 3;
		}
	}
	
	/**
	 * Called when someone curses the player.
	 * @param player The cursed player.
	 * @param caster The one, who cursed the player.
	 */
	public void onApply(EntityPlayer player, EntityPlayer caster) {
	}
	
	/**
	 * Called when curse is removed.
	 * @param player The cursed player.
	 */
	public void onRevoke(EntityPlayer player) {
	}
	
	/**
	 * Called every tick while curse is active.
	 * @param player
	 */
	public void doEffects(EntityPlayer player) {
	}
	/**
	 * All super variables have already been read.
	 */
	public void readCustomFromNBT(NBTTagCompound nbt) {
	}
	
	/**
	 * All super variables are already written.
	 */
	public void writeCustomToNBT(NBTTagCompound nbt) {
	}
}
