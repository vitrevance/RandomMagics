package randommagics.customs;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import randommagics.rituals.EnchantmentRitualReciepe;
import randommagics.rituals.PowerRitualReciepe;
import randommagics.rituals.PowerRitualReciepe.Type;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.lib.enchantment.EnchantmentRepair;

public class CustomRitualResearchPage extends ResearchPage {
	
	//public static ResourceLocation powerBk = new ResourceLocation("");
	
	public CustomRitualResearchPage(PowerRitualReciepe rec) {
		super("");
		this.type = PageType.TEXT;
		if (rec.type == Type.basic)
			this.text = "Power Ritual Recipe\nInfuse " + rec.input.getDisplayName() + " x " + rec.input.stackSize + " with " + rec.vis + " aura to create " + rec.result.getDisplayName() + " x " + rec.result.stackSize;
		if (rec.type == Type.enchantment) {
			EnchantmentRitualReciepe r = (EnchantmentRitualReciepe)rec;
			this.text = "Power Ritual Recipe\nInfuse item with " + r.input.getDisplayName() + " x " + r.input.stackSize + " and " + r.vis + " aura to apply " + StatCollector.translateToLocal(r.enchant.getName()) + " " + r.lvl + " lvl";
		}
	}
}
