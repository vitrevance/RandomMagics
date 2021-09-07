package randommagics.rituals;

import net.minecraft.item.ItemStack;

public class PowerRitualReciepe {
	
	public ItemStack input;
	public ItemStack result;
	public int vis;
	public String[] research;
	public enum Type {
		basic,
		enchantment
	}
	public Type type;
	
	public PowerRitualReciepe(ItemStack input, int visamount, ItemStack result, String... researhes) {
		vis = visamount;
		this.input = input;
		this.result = result;
		this.research = researhes;
		type = Type.basic;
	}
	
	public PowerRitualReciepe register() {
		RitualHelper.registerPowerReciepe(this);
		return this;
	}
}
