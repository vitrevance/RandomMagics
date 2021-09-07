package randommagics;

import java.util.HashMap;

import net.minecraft.util.ResourceLocation;
import thaumcraft.api.research.ResearchCategories;

public class BookTab {
	
	public static final String RM = "Random Magics";

	 public static HashMap<String, Object> recipeList = new HashMap();
	 public static final ResourceLocation[] backgrounds = new ResourceLocation[]{
			 new ResourceLocation("randommagics", "book/bkground.png") /* �������������� ���� ��� ������� */};

	 public static void setup() {
	     ResearchCategories.registerCategory((String)"RANDOMMAGICS" /* ID ����� �������. ����������� ������ �������� �������!!! */, 
	    		 (ResourceLocation)new ResourceLocation("randommagics","book/icon.png") /* �������������� ������ ������� */, (ResourceLocation)backgrounds[0]);
	 }
}
