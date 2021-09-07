package randommagics.rituals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.item.ItemStack;

public class RitualHelper {
	
	//private static HashMap<ItemStack, PowerRitualReciepe> powerreciepes = new HashMap<ItemStack, PowerRitualReciepe>();
	private static ArrayList<PowerRitualReciepe> powerreciepes = new ArrayList<PowerRitualReciepe>();
	
	public static void registerPowerReciepe(PowerRitualReciepe reciepe) {
		powerreciepes.add(reciepe);
	}
	
	public static PowerRitualReciepe findPowerReciepe(ItemStack stack) {
		Iterator<PowerRitualReciepe> i = powerreciepes.iterator();
		while (i.hasNext()) {
			PowerRitualReciepe p = i.next();
			if (ItemStack.areItemStacksEqual(stack, p.input)) {
				return p;
			}
		}
		return null;
	}
}
