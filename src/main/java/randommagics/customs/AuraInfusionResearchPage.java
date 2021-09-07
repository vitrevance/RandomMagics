package randommagics.customs;

import randommagics.Init;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchPage;

public class AuraInfusionResearchPage extends ResearchPage {
	
	public AuraInfusionResearchPage(AuraInfusionRecipe rec) {
		super("");
		this.type = type.INFUSION_CRAFTING;
		this.recipe = new InfusionRecipe(rec.research, rec.output, rec.instability, new AspectList().add(Init.diferium, rec.vis), rec.input, rec.ingredients);
	}
}
