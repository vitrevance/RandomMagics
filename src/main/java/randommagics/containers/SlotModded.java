package randommagics.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import randommagics.items.ItemInfinityStone;

public class SlotModded extends Slot{

	public SlotModded(IInventory inventory, int par2, int p_i1824_3_, int p_i1824_4_) {
		super(inventory, par2, p_i1824_3_, p_i1824_4_);

	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
    {
		return stack != null && (stack.getItem() instanceof ItemInfinityStone) && stack.getItemDamage() == slotNumber;
    }

}
