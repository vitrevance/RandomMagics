package randommagics.blocks;

import net.minecraft.tileentity.TileEntity;
import randommagics.Config;
import thaumcraft.api.TileThaumcraft;

public class TileTimeExpander extends TileThaumcraft {
	
	public boolean isActive;
	public int blockCount = 0;
	
	public TileTimeExpander() {
		isActive = false;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		TileEntity ent = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
		if (ent != null && ent.canUpdate()) {
			isActive = true;
			if (ent instanceof TileTimeExpander) {
				if (!((TileTimeExpander)ent).isActive) {
					isActive = false;
				}
				this.blockCount = ((TileTimeExpander)ent).blockCount + 1;
			}
			else {
				this.blockCount = 1;
			}
			
			if (isActive) {
				isActive = this.blockCount <= Config.maxTimeExpanderStack;
			}
			
			if (isActive)
			for (int i = 0; i < 20 && ent.canUpdate(); i++) {
				ent.updateEntity();
			}
		}
		else {
			isActive = false;
		}
	}
}