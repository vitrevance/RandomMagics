package randommagics.curses;

import net.minecraft.entity.player.EntityPlayer;
import randommagics.customs.CustomExtendedEntityProperties;

public class CurseRevoker extends Curse {
	
	public CurseRevoker() {
		super("revoker");
		
		this.isInfinite = false;
		this.revokeAfterDeath = true;
		this.timeInTicks = 5;
		this.stackable = false;
	}
	
	@Override
	public void onApply(EntityPlayer player, EntityPlayer caster) {
		CurseRegistry.revokeFromPlayer(player, this);
		CustomExtendedEntityProperties ex = CustomExtendedEntityProperties.get(player);
		if (ex.curses.size() > 0) {
			CurseRegistry.revokeFromPlayer(player, CurseRegistry.getCurseByID(ex.curses.get((ex.curses.size() * ex.curses.size() * 1365) % ex.curses.size()).getUniqueID()));
		}
	}
}
