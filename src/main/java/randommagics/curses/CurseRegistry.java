package randommagics.curses;

import java.util.HashMap;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.omg.CORBA.ExceptionList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import randommagics.customs.CustomExtendedEntityProperties;
import scala.util.Random;
import scala.util.control.Exception;

public class CurseRegistry {
	
	public static final HashMap<String, Class<? extends Curse>> registry = new HashMap<String, Class<? extends Curse>>();
	
	public static void register() {
		registerCurse("shortlife", CurseShortLife.class);
		registerCurse("ghostmode", CurseGhostForm.class);
		registerCurse("outoftime", CurseOutOfTime.class);
		registerCurse("badluck", CurseBadLuck.class);
		registerCurse("undead", CurseUndead.class);
		registerCurse("holeypockets", CurseHoleyPockets.class);
		registerCurse(new CurseRevoker().getUniqueID(), CurseRevoker.class);
		registerCurse(new CurseEndersteve().getUniqueID(), CurseEndersteve.class);
		registerCurse(new CurseAnnoyedSteve().getUniqueID(), CurseAnnoyedSteve.class);
		registerCurse(new CurseJumping().getUniqueID(), CurseJumping.class);
		registerCurse(new CurseMadnessClearer().getUniqueID(), CurseMadnessClearer.class);
	}
	
	public static final void registerCurse(String id, Class<? extends Curse> cl) {
		if (!registry.containsKey(id)) {
			registry.put(id, cl);
		}
	}
	
	public static void applyOnPlayer(EntityPlayer player, EntityPlayer caster, Curse curse) {
		if (player != null && curse != null) {
			CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(player);
			ex.addCurse(curse);
			curse.onApply(player, caster);
		}
	}
	
	public static void applyOnPlayerSided(EntityPlayer player, EntityPlayer caster, Curse curse, boolean isRemote) {
		if (player != null && curse != null) {
			if (!isRemote) {
				CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(player);
				ex.addCurse(curse);
				ex.sync(player);
				curse.onApply(player, caster);
			}
			else {
				curse.onApply(player, caster);
			}
		}
	}
	
	public static void revokeFromPlayer(EntityPlayer player, Curse curse) {
		if (player != null && curse != null) {
			CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(player);
			ex.revokeCurse(curse);
			curse = null;
		}
	}
	
	/**
	 * Removes without calling onRevoke() method.
	 */
	public static void removeAllFromPlayer(EntityPlayer player) {
		if (player != null) {
			CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(player);
			ex.removeAllCurses();
		}
	}
	
	public static void revokeAllFromPlayer(EntityPlayer player) {
		if (player != null) {
			CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(player);
			ex.revokeAllCurses();
		}
	}
	
	public static boolean isPlayerCursed(EntityPlayer player, String curseID) {
		CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(player);
		return ex.cursedWith(curseID);
	}
	
	public static Curse getCurseByID(String id) {
		if (registry.containsKey(id)) {
			try {
				return registry.get(id).newInstance();
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
		}
		return null;
	}
	
	public static String getRandomCurseID() {
		Random rand = new Random();
		return registry.keySet().toArray(new String[0])[rand.nextInt(registry.size())];
	}
}
