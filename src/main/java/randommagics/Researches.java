package randommagics;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Consumer;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import randommagics.blocks.BlockDevice;
import randommagics.blocks.ItemDevice;
import randommagics.customs.AuraInfusionHelper;
import randommagics.customs.AuraInfusionRecipe;
import randommagics.customs.AuraInfusionResearchPage;
import randommagics.customs.CustomRitualResearchPage;
import randommagics.items.ItemWandRodPrim;
import randommagics.rituals.EnchantmentRitualReciepe;
import randommagics.rituals.PowerRitualReciepe;
import scala.collection.generic.BitOperations.Int;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.research.ResearchPage.PageType;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigAspects;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.config.ConfigResearch;
import thaumcraft.common.items.relics.ItemSanityChecker;

public class Researches {
	
	public static void Research()
	{
		
		ItemStack ritual_stone_block = new ItemStack(Init.RitualStone);
		ItemStack empty = new ItemStack(ConfigBlocks.blockHole, 1, 15);
	
	InfusionRecipe primrod = ThaumcraftApi.addInfusionCraftingRecipe("ROD_primordial", new ItemStack(Init.ItemWandRodPrimal), 25, new AspectList().add(Aspect.AIR, 1000).add(Aspect.ORDER, 1000).add(Aspect.ENTROPY, 1000).add(Aspect.FIRE, 1000).add(Aspect.EARTH, 1000).add(Aspect.WATER, 1000).add(Aspect.MAGIC, 2000).add(Aspect.ELDRITCH, 5000),
			new ItemStack(ConfigItems.itemWandRod, 1, 100), new ItemStack[] 
					{new ItemStack(ConfigItems.itemResource, 1, 16), 
					new ItemStack(ConfigItems.itemResource, 1, 14),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3), 
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3), 
					new ItemStack(ConfigItems.itemResource, 1, 11),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemResource, 1, 11),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemResource, 1, 14),
					new ItemStack(ConfigItems.itemResource, 1, 16),
					new ItemStack(ConfigItems.itemResource, 1, 14),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemResource, 1, 11),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemResource, 1, 11),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemResource, 1, 14)});
	
	new ResearchItem("ROD_primordial", "RANDOMMAGICS", 
			new AspectList().add(Aspect.AIR, 64).add(Aspect.ORDER, 64).add(Aspect.MAGIC, 64).add(Aspect.ENTROPY, 64).add(Aspect.EARTH, 64).add(Aspect.FIRE, 64).add(Aspect.WATER, 64).add(Aspect.DARKNESS, 64), 1, -2, 2,
			new ItemStack(Init.ItemWandRodPrimal))
	.setPages(new ResearchPage[] { new ResearchPage("research.ROD_primordial.text"),new ResearchPage(primrod)})
	.setParents(new String[] {"Starts"}).setConcealed()
	.registerResearchItem();
	
	InfusionRecipe blkholecap = ThaumcraftApi.addInfusionCraftingRecipe("CAP_blkhole", new ItemStack(Init.ItemBlkHoleCap), 25, new AspectList().add(Aspect.AIR, 1000).add(Aspect.ORDER, 1000).add(Aspect.ENTROPY, 1000).add(Aspect.FIRE, 1000).add(Aspect.EARTH, 1000).add(Aspect.WATER, 1000).add(Aspect.MAGIC, 2000).add(Aspect.AURA, 50),
			new ItemStack(ConfigItems.itemWandCap, 1, 7), new ItemStack[] 
					{new ItemStack(ConfigItems.itemFocusPortableHole), 
					new ItemStack(ConfigItems.itemResource, 1, 15), 
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigBlocks.blockEssentiaReservoir),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigBlocks.blockEssentiaReservoir),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemResource, 1, 15),
					new ItemStack(ConfigItems.itemFocusPortableHole),
					new ItemStack(ConfigItems.itemResource, 1, 15),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigBlocks.blockEssentiaReservoir),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigBlocks.blockEssentiaReservoir),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemResource, 1, 15)});
	
	new ResearchItem("CAP_blkhole", "RANDOMMAGICS", 
			new AspectList().add(Aspect.AIR, 64).add(Aspect.ORDER, 64).add(Aspect.MAGIC, 64).add(Aspect.ENTROPY, 64).add(Aspect.EARTH, 64).add(Aspect.FIRE, 64).add(Aspect.WATER, 64).add(Aspect.AURA, 64), 3, -3, 5,
			//new ResourceLocation("randommagics", "textures/items/wand_rod_primordial.png")
			new ItemStack(Init.ItemBlkHoleCap))
	.setPages(new ResearchPage[] { new ResearchPage("research.CAP_blkhole.text"),new ResearchPage(blkholecap)})
	.setParents(new String[] {"ROD_primordial", "CAP_void"}).setConcealed()
	.registerResearchItem();
	
	new ResearchItem("Starts", "RANDOMMAGICS", new AspectList().add(Aspect.AIR, 64).add(Aspect.ORDER, 64).add(Aspect.MAGIC, 64).add(Aspect.ENTROPY, 64).add(Aspect.EARTH, 64).add(Aspect.FIRE, 64).add(Aspect.WATER, 64).add(Aspect.DARKNESS, 64), 0, 0, 5,
			new ResourceLocation("randommagics", "book/R_Starts.png"))
	.setPages(new ResearchPage[] { new ResearchPage("research.Starts.text")})
	.setParents(new String[] {"PRIMPEARL"})
	.setSecondary()
	.registerResearchItem();
	
	InfusionRecipe ritualstone = ThaumcraftApi.addInfusionCraftingRecipe("ritualStone", new ItemStack(Init.RitualStone), 25, new AspectList().add(Aspect.ORDER, 100).add(Aspect.FIRE, 50).add(Aspect.EARTH, 1000).add(Aspect.MAGIC, 250).add(Aspect.AURA, 50).add(Aspect.MIND, 400),
			new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack[] 
					{new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 0), 
					new ItemStack(ConfigItems.itemResource, 1, 1), 
					new ItemStack(ConfigItems.itemResource, 1, 9),
					new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6),
					new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 0),
					new ItemStack(ConfigBlocks.blockCrystal, 1, 6),
					new ItemStack(ConfigItems.itemManaBean),
					new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6)});
	
	new ResearchItem("ritualStone", "RANDOMMAGICS", new AspectList().add(Aspect.ORDER, 8).add(Aspect.MAGIC, 12).add(Aspect.ENTROPY, 4).add(Aspect.EARTH, 24).add(Aspect.FIRE, 12).add(Aspect.DARKNESS, 6), 1, 3, 6,
			new ItemStack(Init.RitualStone))
	.setPages(new ResearchPage[] { new ResearchPage("research.ritualStone.text"), new ResearchPage(ritualstone)})
	.setParents(new String[] {"Starts", "OUTERREV"}).setConcealed()
	.registerResearchItem();
	
	InfusionRecipe ritualstonespirit = ThaumcraftApi.addInfusionCraftingRecipe("ritualStoneSpirit", new ItemStack(Init.RitualStoneSpirit), 25, new AspectList().add(Aspect.ORDER, 500).add(Aspect.SOUL, 100).add(Aspect.MAGIC, 250).add(Aspect.AURA, 50).add(Aspect.MIND, 100).add(Aspect.SENSES, 120),
			new ItemStack(Init.RitualStone), new ItemStack[] 
					{new ItemStack(ConfigItems.itemEldritchObject, 1, 3), 
					new ItemStack(ConfigItems.itemWispEssence), 
					new ItemStack(ConfigItems.itemWispEssence),
					new ItemStack(ConfigItems.itemZombieBrain),
					new ItemStack(ConfigItems.itemWispEssence), 
					new ItemStack(ConfigItems.itemWispEssence),
					new ItemStack(ConfigItems.itemManaBean),
					new ItemStack(ConfigItems.itemWispEssence), 
					new ItemStack(ConfigItems.itemWispEssence),
					new ItemStack(ConfigItems.itemResource, 1, 9),
					new ItemStack(ConfigItems.itemWispEssence), 
					new ItemStack(ConfigItems.itemWispEssence)});
	
	InfusionRecipe ritualstonepower = ThaumcraftApi.addInfusionCraftingRecipe("ritualStonePower", new ItemStack(Init.RitualStonePower), 25, new AspectList().add(Aspect.ORDER, 100).add(Aspect.MAGIC, 200).add(Aspect.AURA, 50).add(Aspect.ENERGY, 1500),
			new ItemStack(Init.RitualStone), new ItemStack[] 
					{new ItemStack(ConfigItems.itemEldritchObject, 1, 3), 
					new ItemStack(ConfigBlocks.blockCustomPlant, 1, 4), 
					new ItemStack(ConfigBlocks.blockCustomPlant, 1, 4),
					new ItemStack(Items.nether_star),
					new ItemStack(ConfigBlocks.blockCustomPlant, 1, 4),
					new ItemStack(ConfigBlocks.blockCustomPlant, 1, 4),
					new ItemStack(ConfigItems.itemResource, 1, 0),
					new ItemStack(ConfigBlocks.blockCustomPlant, 1, 4),
					new ItemStack(ConfigBlocks.blockCustomPlant, 1, 4),
					new ItemStack(ConfigItems.itemResource, 1, 15),
					new ItemStack(ConfigBlocks.blockCustomPlant, 1, 4),
					new ItemStack(ConfigBlocks.blockCustomPlant, 1, 4)});
	
	ItemStack a = new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 1);
	ItemStack s = new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 7);
	ItemStack r = new ItemStack(Init.RitualStone);
	ItemStack d = new ItemStack(ConfigBlocks.blockStoneDevice, 1, 9);
	ItemStack q = new ItemStack(Blocks.quartz_block, 1, 2);
	ItemStack n = null;
	
	new ResearchItem("ritualStonePower", "RANDOMMAGICS", new AspectList().add(Aspect.ORDER, 32).add(Aspect.MAGIC, 32).add(Aspect.ENERGY, 24), -1, 3, 3,
			new ItemStack(Init.RitualStonePower))
	.setPages(new ResearchPage[] { new ResearchPage("research.ritualStonePower.text"), new ResearchPage(ritualstonepower), new ResearchPage(Arrays.asList(new Object[] {
            (null), Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(3), Arrays.asList(new ItemStack[] {
                    null, null, null,
                    null, new ItemStack(ConfigBlocks.blockStoneDevice, 1, 1), null,
                    null, null, null,
                    null, null, null,
                    null, new ItemStack(Init.RitualStonePower), null,
                    null, null, null,
                    ritual_stone_block, ritual_stone_block, ritual_stone_block,
                    ritual_stone_block, ritual_stone_block, ritual_stone_block,
                    ritual_stone_block, ritual_stone_block, ritual_stone_block
                })
            })),/*
			new ResearchPage(Arrays.asList(new Object[] {
		            (null), Integer.valueOf(7), Integer.valueOf(3), Integer.valueOf(7), Arrays.asList(new ItemStack[] {
		            		n, n, n, d, n, n, n,
		                    n, d, n, n, n, d, n,
		                    n, n, n, n, n, n, n,
		                    d, n, n, n, n, n, d,
		                    n, n, n, n, n, n, n,
		                    n, d, n, n, n, d, n,
		                    n, n, n, d, n, n, n,
		                    
		                    n, n, n, r, n, n, n,
		                    n, r, n, n, n, r, n,
		                    n, n, n, n, n, n, n,
		                    r, n, n, n, n, n, r,
		                    n, n, n, n, n, n, n,
		                    n, r, n, n, n, r, n,
		                    n, n, n, r, n, n, n,
		                    
		                    n, n, n, q, n, n, n,
		                    n, q, a, s, a, q, n,
		                    n, a, r, s, r, a, n,
		                    q, s, s, new ItemStack(Init.RitualStonePower), s, s, q,
		                    n, a, r, s, r, a, n,
		                    n, q, a, s, a, q, n,
		                    n, n, n, q, n, n, n,
		                })
		            })),*/
			
			new ResearchPage(Arrays.asList(new Object[] {
		            (null), Integer.valueOf(7), Integer.valueOf(1), Integer.valueOf(7), Arrays.asList(new ItemStack[] {
		                    n, n, n, q, n, n, n,
		                    n, q, a, s, a, q, n,
		                    n, a, r, s, r, a, n,
		                    q, s, s, new ItemStack(Init.RitualStonePower), s, s, q,
		                    n, a, r, s, r, a, n,
		                    n, q, a, s, a, q, n,
		                    n, n, n, q, n, n, n,
		                })
		            })),
			new ResearchPage(Arrays.asList(new Object[] {
		            (null), Integer.valueOf(7), Integer.valueOf(1), Integer.valueOf(7), Arrays.asList(new ItemStack[] {
		                    n, n, n, r, n, n, n,
		                    n, r, n, n, n, r, n,
		                    n, n, n, n, n, n, n,
		                    r, n, n, n, n, n, r,
		                    n, n, n, n, n, n, n,
		                    n, r, n, n, n, r, n,
		                    n, n, n, r, n, n, n
		                })
		            })),
			new ResearchPage(Arrays.asList(new Object[] {
		            (null), Integer.valueOf(7), Integer.valueOf(1), Integer.valueOf(7), Arrays.asList(new ItemStack[] {
		            		n, n, n, d, n, n, n,
		                    n, d, n, n, n, d, n,
		                    n, n, n, n, n, n, n,
		                    d, n, n, n, n, n, d,
		                    n, n, n, n, n, n, n,
		                    n, d, n, n, n, d, n,
		                    n, n, n, d, n, n, n
		                })
		            }))
			
	})
	.setParents(new String[] {"ritualStone"}).setConcealed()
	.registerResearchItem();
	
	new ResearchItem("ritualStoneSpirit", "RANDOMMAGICS", new AspectList().add(Aspect.ORDER, 32).add(Aspect.MAGIC, 32).add(Aspect.SENSES, 24), -1, 2, 5,
			new ItemStack(Init.RitualStoneSpirit))
	.setPages(new ResearchPage[] { new ResearchPage("research.ritualStoneSpirit.text"), new ResearchPage(ritualstonespirit), new ResearchPage(Arrays.asList(new Object[] {
            (null), Integer.valueOf(5), Integer.valueOf(3), Integer.valueOf(5), Arrays.asList(new ItemStack[] {
                    null, null, null, null, null,
                    null, null, null, null, null,
                    null, null, new ItemStack(ConfigBlocks.blockJar, 1, 1), null, null,
                    null, null, null, null, null,
                    null, null, null, null, null,
                    null, null, empty, null, null,
                    null, new ItemStack(ConfigBlocks.blockCrystal, 1, 6), empty, new ItemStack(ConfigBlocks.blockCrystal, 1, 6), null,
                    empty, empty, new ItemStack(Init.RitualStoneSpirit), empty, empty,
                    null, new ItemStack(ConfigBlocks.blockCrystal, 1, 6), empty, new ItemStack(ConfigBlocks.blockCrystal, 1, 6), null,
                    null, null, empty, null, null,
                    empty, empty, ritual_stone_block, empty, empty,
                    empty, ritual_stone_block, new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 0), ritual_stone_block, empty,
                    ritual_stone_block, new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 0), ritual_stone_block, new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 0), ritual_stone_block,
                    empty, ritual_stone_block, new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 0), ritual_stone_block, empty,
                    empty, empty, ritual_stone_block, empty, empty
                })
            }))})
	.setParents(new String[] {"ritualStone"}).setConcealed()
	.registerResearchItem();
	
	ShapedArcaneRecipe ritualcatalyst = ThaumcraftApi.addArcaneCraftingRecipe("ritualCatalyst",new ItemStack(Init.RitualCatalyst), new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.ENTROPY, 50).add(Aspect.AIR, 50).add(Aspect.WATER, 50).add(Aspect.FIRE, 50), 
			 new Object[] { "ABC", "BSB", "CMA",
					 Character.valueOf('A'), new ItemStack(ConfigItems.itemResource, 1, 17),
					 Character.valueOf('B'), new ItemStack(ConfigItems.itemWispEssence),
					 Character.valueOf('C'), new ItemStack(Items.blaze_powder),
					 Character.valueOf('M'), new ItemStack(ConfigBlocks.blockCrystal, 1, 6),
					 Character.valueOf('S'), new ItemStack(ConfigItems.itemResource, 1, 14)});
	
	new ResearchItem("ritualCatalyst", "RANDOMMAGICS", new AspectList().add(Aspect.ORDER, 32).add(Aspect.MAGIC, 32).add(Aspect.AURA, 24), 1, 5, 3,
			new ItemStack(Init.RitualCatalyst))
	.setPages(new ResearchPage[] { new ResearchPage("research.ritualCatalyst.text"), new ResearchPage(ritualcatalyst)})
	.setParents(new String[] {"ritualStone"}).setConcealed()
	.registerResearchItem();
	
	InfusionRecipe BlockStableEldPortal = ThaumcraftApi.addInfusionCraftingRecipe("stableEldPortal", new ItemStack(Init.BlockStableEldPortal), 25, new AspectList().add(Aspect.ORDER, 100).add(Aspect.MAGIC, 200).add(Aspect.AURA, 500),
			new ItemStack(Init.RitualStone), new ItemStack[] 
					{new ItemStack(ConfigItems.itemEldritchObject, 1, 3), 
					new ItemStack(ConfigItems.itemEldritchObject, 1, 1),
					new ItemStack(Init.RitualCatalyst),
					new ItemStack(ConfigItems.itemFocusPortableHole),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 0),
					new ItemStack(ConfigItems.itemResource, 1, 17),
					new ItemStack(ConfigItems.itemBottleTaint),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 2)});
	
	new ResearchItem("stableEldPortal", "RANDOMMAGICS", new AspectList().add(Aspect.ORDER, 32).add(Aspect.MAGIC, 32).add(Aspect.DARKNESS, 24).add(Aspect.AURA, 16), -3, 2, 3,
			new ItemStack(Init.BlockStableEldPortal))
	.setPages(new ResearchPage[] { new ResearchPage("research.stableEldPortal.text"), new ResearchPage(BlockStableEldPortal), new ResearchPage(Arrays.asList(new Object[] {
            (null), Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(5), Arrays.asList(new ItemStack[] {
                    new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1),
                    new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(Init.RitualStone), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1),
                    new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(Init.RitualStone), new ItemStack(Init.BlockStableEldPortal), new ItemStack(Init.RitualStone), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1),
                    new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(Init.RitualStone), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1),
                    new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1),
                })
            }))})
	.setParents(new String[] {"ritualStoneSpirit"}).setConcealed()
	.registerResearchItem();
	
	
	
	/*ConfigResearch.recipes.put("ritualStoneSpirit", Arrays.asList(new Object[] {
            (new AspectList()), Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(5), Arrays.asList(new ItemStack[] {
                new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1),
                new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(Init.RitualStone), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1),
                new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(Init.RitualStone), new ItemStack(Init.BlockStableEldPortal), new ItemStack(Init.RitualStone), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1),
                new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(Init.RitualStone), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1),
                new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1),
            })
        }));*/
	
	ShapedArcaneRecipe primblend = ThaumcraftApi.addArcaneCraftingRecipe("PrimBlend",new ItemStack(Init.PrimBlend), new AspectList().add(Aspect.ORDER, 100).add(Aspect.EARTH, 75).add(Aspect.ENTROPY, 40), 
			 new Object[] { "KAK", "VPV", "SSS",
					 Character.valueOf('K'), new ItemStack(ConfigItems.itemResource, 1, 9),
					 Character.valueOf('A'), new ItemStack(ConfigItems.itemResource, 1, 6),
					 Character.valueOf('V'), new ItemStack(ConfigItems.itemNugget, 1, 7),
					 Character.valueOf('P'), new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					 Character.valueOf('S'), new ItemStack(ConfigItems.itemResource, 1, 14)});
	
	new ResearchItem("PrimBlend", "RANDOMMAGICS", new AspectList().add(Aspect.METAL, 32).add(Aspect.AIR, 32).add(Aspect.EARTH, 32).add(Aspect.FIRE, 32).add(Aspect.WATER, 32).add(Aspect.ORDER, 32).add(Aspect.ENTROPY, 32), 3, -1, 3,
		new ItemStack(Init.PrimBlend)).setPages(new ResearchPage[] {new ResearchPage("research.PrimBlend.text"), new ResearchPage(primblend), new ResearchPage(new ItemStack(Init.PrimBlend))})
	.setParents(new String[] {"Starts"}).setConcealed()
	.registerResearchItem();
	
	InfusionRecipe eldhelmet = ThaumcraftApi.addInfusionCraftingRecipe("EldArmor", new ItemStack(Init.EldHelmet), 25, new AspectList().add(Aspect.ORDER, 100).add(Aspect.MAGIC, 200).add(Aspect.ARMOR, 200).add(Aspect.ELDRITCH, 300),
			new ItemStack(ConfigItems.itemHelmetVoidRobe), new ItemStack[] 
					{new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemEldritchObject),
					new ItemStack(ConfigItems.itemAmuletRunic),
					new ItemStack(ConfigItems.itemAmuletVis, 1, 1),
					new ItemStack(ConfigItems.itemAmuletRunic),
					new ItemStack(ConfigItems.itemAmuletRunic),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemAmuletRunic),
					new ItemStack(ConfigItems.itemAmuletRunic),
					new ItemStack(ConfigItems.itemBottleTaint),
					new ItemStack(ConfigItems.itemAmuletRunic),
					new ItemStack(ConfigItems.itemEldritchObject)});
	
	InfusionRecipe eldchesplate = ThaumcraftApi.addInfusionCraftingRecipe("EldArmor", new ItemStack(Init.EldChestplate), 25, new AspectList().add(Aspect.ORDER, 100).add(Aspect.MAGIC, 200).add(Aspect.ARMOR, 200).add(Aspect.ELDRITCH, 300),
			new ItemStack(ConfigItems.itemChestVoidRobe), new ItemStack[] 
					{new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemAmuletRunic),
					new ItemStack(ConfigItems.itemHoverHarness),
					new ItemStack(Init.PrimIngot),
					new ItemStack(ConfigItems.itemResource, 1, 11),
					new ItemStack(ConfigItems.itemAmuletRunic),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemAmuletRunic),
					new ItemStack(ConfigItems.itemResource, 1, 15),
					new ItemStack(Init.PrimIngot),
					new ItemStack(ConfigItems.itemResource, 1, 11),
					new ItemStack(ConfigItems.itemAmuletRunic)});
	
	InfusionRecipe eldleggins = ThaumcraftApi.addInfusionCraftingRecipe("EldArmor", new ItemStack(Init.EldLeggins), 25, new AspectList().add(Aspect.ORDER, 100).add(Aspect.MAGIC, 200).add(Aspect.ARMOR, 200).add(Aspect.ELDRITCH, 300),
			new ItemStack(ConfigItems.itemLegsVoidRobe), new ItemStack[] 
					{new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigBlocks.blockCustomPlant, 1, 5),
					new ItemStack(Init.PrimIngot),
					new ItemStack(ConfigItems.itemResource, 1, 11),
					new ItemStack(ConfigBlocks.blockCustomPlant, 1, 4),
					new ItemStack(ConfigBlocks.blockTaint, 1, 2),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigItems.itemResource, 1, 14),
					new ItemStack(Init.PrimIngot),
					new ItemStack(ConfigItems.itemResource, 1, 11),
					new ItemStack(ConfigItems.itemBathSalts),
					new ItemStack(ConfigBlocks.blockEldritch, 1, 4)});
	
	InfusionRecipe eldboots = ThaumcraftApi.addInfusionCraftingRecipe("EldArmor", new ItemStack(Init.EldBoots), 25, new AspectList().add(Aspect.ORDER, 100).add(Aspect.MAGIC, 200).add(Aspect.ARMOR, 200).add(Aspect.ELDRITCH, 300),
			new ItemStack(ConfigItems.itemBootsVoid), new ItemStack[] 
					{new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigBlocks.blockMetalDevice, 1, 13),
					new ItemStack(Init.PrimIngot),
					new ItemStack(ConfigItems.itemResource, 1, 11),
					new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 0),
					new ItemStack(ConfigItems.itemFocusWarding),
					new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
					new ItemStack(ConfigBlocks.blockMetalDevice, 1, 13),
					new ItemStack(Init.PrimIngot),
					new ItemStack(ConfigItems.itemResource, 1, 11),
					new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 0),
					new ItemStack(ConfigItems.itemFocusExcavation)});
	
	new ResearchItem("EldArmor", "RANDOMMAGICS", new AspectList().add(Aspect.ARMOR, 16).add(Aspect.ELDRITCH, 24).add(Aspect.MAGIC, 32).add(Aspect.HEAL, 32).add(Aspect.EXCHANGE, 24), 5, -2, 3, new ItemStack(Init.EldChestplate))
		.setPages(new ResearchPage[] {new ResearchPage("research.EldArmor.text"), new ResearchPage(eldhelmet), new ResearchPage(eldchesplate), new ResearchPage(eldleggins), new ResearchPage(eldboots)})
		.setParents(new String[] {"PrimBlend"}).setConcealed()
		.registerResearchItem();
	
	InfusionRecipe focmindmutilation = ThaumcraftApi.addInfusionCraftingRecipe("fociMindMutilation", new ItemStack(Init.FocusMindMutilation), 7, new AspectList().add(Aspect.MAGIC, 64).add(Aspect.ENTROPY, 60).add(Aspect.ELDRITCH, 35),
			new ItemStack(ConfigItems.itemFocusTrade), new ItemStack[]
					{new ItemStack(ConfigBlocks.blockCrystal, 1, 5),
					new ItemStack(ConfigItems.itemBathSalts),
					new ItemStack(ConfigItems.itemBathSalts),
					new ItemStack(ConfigItems.itemBathSalts),
					});
	
	new ResearchItem("fociMindMutilation", "RANDOMMAGICS", new AspectList().add(Aspect.CRYSTAL, 16).add(Aspect.ELDRITCH, 24).add(Aspect.MAGIC, 32).add(Aspect.EXCHANGE, 24), 3, 1, 3, new ItemStack(Init.FocusMindMutilation))
	.setPages(new ResearchPage[] {new ResearchPage("research.fociMindMutilation.text"), new ResearchPage(focmindmutilation)})
	.setParents(new String[] {"Starts"}).setConcealed()
	.registerResearchItem();
	
	InfusionRecipe focstealing = ThaumcraftApi.addInfusionCraftingRecipe("fociStealing", new ItemStack(Init.FocusStealing), 7, new AspectList().add(Aspect.MAGIC, 60).add(Aspect.GREED, 150).add(Aspect.ELDRITCH, 50),
			new ItemStack(ConfigItems.itemFocusFire), new ItemStack[]
					{new ItemStack(Items.golden_apple),
					new ItemStack(Items.golden_apple),
					new ItemStack(Items.golden_apple),
					new ItemStack(Items.golden_apple),
					});
	
	new ResearchItem("fociStealing", "RANDOMMAGICS", new AspectList().add(Aspect.GREED, 24).add(Aspect.MAGIC, 32).add(Aspect.EXCHANGE, 24), 4, 1, 3, new ItemStack(Init.FocusStealing))
	.setPages(new ResearchPage[] {new ResearchPage("research.fociStealing.text"), new ResearchPage(focstealing)})
	.setParents(new String[] {"Starts"}).setConcealed()
	.registerResearchItem();
	
	InfusionRecipe fochailofarrows = ThaumcraftApi.addInfusionCraftingRecipe("fociHailOfArrows", new ItemStack(Init.FocusHailOfArrows), 7, new AspectList().add(Aspect.MAGIC, 60).add(Aspect.WEAPON, 150).add(Aspect.ENTROPY, 80).add(Aspect.AIR, 100),
			new ItemStack(ConfigItems.itemFocusFire), new ItemStack[]
					{new ItemStack(ConfigItems.itemSwordVoid),
					new ItemStack(Items.arrow),
					new ItemStack(ConfigItems.itemResource, 1, 15),
					new ItemStack(Items.arrow)
					});
	
	new ResearchItem("fociHailOfArrows", "RANDOMMAGICS", new AspectList().add(Aspect.WEAPON, 24).add(Aspect.MAGIC, 32).add(Aspect.AIR, 24), 5, 1, 3, new ItemStack(Init.FocusHailOfArrows))
	.setPages(new ResearchPage[] {new ResearchPage("research.fociHailOfArrows.text"), new ResearchPage(fochailofarrows)})
	.setParents(new String[] {"ELDRITCHMINOR"}).setConcealed()
	.registerResearchItem();
	
	InfusionRecipe xpholder = ThaumcraftApi.addInfusionCraftingRecipe("XpHolder", new ItemStack(Init.ItemXpHolder), 7, new AspectList().add(Aspect.MAGIC, 60).add(Aspect.ELDRITCH, 150).add(Aspect.AURA, 40),
			new ItemStack(ConfigBlocks.blockJar, 1, 1), new ItemStack[]
					{new ItemStack(ConfigItems.itemResource, 1, 12),
					new ItemStack(Items.experience_bottle),
					new ItemStack(Items.experience_bottle),
					new ItemStack(ConfigItems.itemResource, 1, 15),
					new ItemStack(Items.experience_bottle),
					new ItemStack(Items.experience_bottle)
					});
	
	new ResearchItem("XpHolder", "RANDOMMAGICS", new AspectList().add(Aspect.ELDRITCH, 24).add(Aspect.MAGIC, 32).add(Aspect.AURA, 24), 3, 3, 3, new ItemStack(Init.ItemXpHolder))
	.setPages(new ResearchPage[] {new ResearchPage("research.XpHolder.text"), new ResearchPage(xpholder)})
	.setParents(new String[] {"INFUSION"}).setConcealed()
	.registerResearchItem();
	
	InfusionRecipe fociaurabottler = ThaumcraftApi.addInfusionCraftingRecipe("fociAuraBottler", new ItemStack(Init.FocusAuraBottler), 7, new AspectList().add(Aspect.MAGIC, 60).add(Aspect.EXCHANGE, 60).add(Aspect.AURA, 10),
			new ItemStack(ConfigBlocks.blockJar), new ItemStack[]
					{new ItemStack(ConfigItems.itemResource, 1, 15),
					new ItemStack(Items.ghast_tear),
					new ItemStack(ConfigItems.itemResource, 1, 8),
					new ItemStack(ConfigItems.itemResource, 1, 15),
					new ItemStack(Items.emerald),
					new ItemStack(Items.glowstone_dust)
					});
	
	new ResearchItem("fociAuraBottler", "RANDOMMAGICS", new AspectList().add(Aspect.EXCHANGE, 12).add(Aspect.MAGIC, 15).add(Aspect.AURA, 20), 6, 1, 3, new ItemStack(Init.FocusAuraBottler))
	.setPages(new ResearchPage[] {new ResearchPage("research.fociAuraBottler.text"), new ResearchPage(fociaurabottler)})
	.setParents(new String[] {"ELDRITCHMINOR"}).setConcealed()
	.registerResearchItem();
	
	
	ItemStack infusionMatrixRecipeOut = new ItemStack(GameRegistry.findItem("randommagics", "Device"), 1, 0);
	infusionMatrixRecipeOut.setTagCompound(new NBTTagCompound());
	infusionMatrixRecipeOut.getTagCompound().setInteger("speedMod", 1);
	ShapedArcaneRecipe infusionmatrixextended = ThaumcraftApi.addArcaneCraftingRecipe("InfMatrixExtended", infusionMatrixRecipeOut, new AspectList().add(Aspect.ORDER, 100).add(Aspect.EARTH, 100).add(Aspect.ENTROPY, 100).add(Aspect.AIR, 100).add(Aspect.WATER, 100).add(Aspect.FIRE, 100), 
			 new Object[] { "ABA", "BCB", "ABA",
					 Character.valueOf('B'), new ItemStack(Init.RitualCatalyst),
					 Character.valueOf('A'), new ItemStack(ConfigBlocks.blockStoneDevice, 1, 2),
					 Character.valueOf('C'), new ItemStack(Init.RitualStone)});
	
	new ResearchItem("InfMatrixExtended", "RANDOMMAGICS", new AspectList().add(Aspect.EXCHANGE, 12).add(Aspect.MAGIC, 15).add(Aspect.AURA, 20).add(Aspect.MECHANISM, 15), 4, 3, 3, new ItemStack(GameRegistry.findItem("randommagics", "Device"), 1, 0))
	.setPages(new ResearchPage[] {new ResearchPage("research.InfMatrixExtended.text"), new ResearchPage(infusionmatrixextended), new ResearchPage(new ShapedRecipes(3, 3, new ItemStack[] 
			{
					null, new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6), null,
					new ItemStack(Init.BlockDevice, 1, 0), new ItemStack(ConfigItems.itemEldritchObject, 1, 3), new ItemStack(Init.BlockDevice, 1, 0),
					null, new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6), null
			}, new ItemStack(Init.BlockDevice, 1, 0)))})
	.setParents(new String[] {"ritualStone"}).setConcealed()
	.registerResearchItem();
	
	ShapedArcaneRecipe viscell = ThaumcraftApi.addArcaneCraftingRecipe("VisCell", new ItemStack(GameRegistry.findItem("randommagics", "Device"), 1, 1), new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 10).add(Aspect.ENTROPY, 5).add(Aspect.AIR, 60).add(Aspect.FIRE, 15), 
			 new Object[] { "AAA", "BCB", "AAA",
					 Character.valueOf('B'), new ItemStack(ConfigItems.itemWandRod, 1, 50),
					 Character.valueOf('A'), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
					 Character.valueOf('C'), new ItemStack(ConfigItems.itemShard, 1, 6)});
	
	new ResearchItem("VisCell", "RANDOMMAGICS", new AspectList().add(Aspect.MAGIC, 15).add(Aspect.METAL, 20).add(Aspect.MECHANISM, 15), 5, 3, 3, new ItemStack(GameRegistry.findItem("randommagics", "Device"), 1, 1))
	.setPages(new ResearchPage[] {new ResearchPage("research.VisCell.text"), new ResearchPage(viscell)})
	.setParents(new String[] {"VISCHARGERELAY"}).setConcealed()
	.registerResearchItem();
	
	InfusionRecipe inventoryaccess = ThaumcraftApi.addInfusionCraftingRecipe("InventoryAccess", new ItemStack(GameRegistry.findItem("randommagics", "Device"), 1, 2), 24, new AspectList().add(Aspect.MAGIC, 100).add(Aspect.EXCHANGE, 100).add(Aspect.MECHANISM, 100),
			new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4), new ItemStack[]
					{new ItemStack(ConfigItems.itemHandMirror),
					new ItemStack(Init.BlockMindWarped),
					new ItemStack(Init.BlockMindWarped),
					new ItemStack(ConfigItems.itemFocusPortableHole),
					new ItemStack(Init.BlockMindWarped),
					new ItemStack(Init.BlockMindWarped)
					});
	
	new ResearchItem("InventoryAccess", "RANDOMMAGICS", new AspectList().add(Aspect.MAGIC, 15).add(Aspect.EXCHANGE, 20).add(Aspect.MECHANISM, 15), 6, 3, 3, new ItemStack(GameRegistry.findItem("randommagics", "Device"), 1, 2))
	.setPages(new ResearchPage[] {new ResearchPage("research.InventoryAccess.text"), new ResearchPage(inventoryaccess)})
	.setParents(new String[] {"ritualStoneSpirit"}).setConcealed()
	.registerResearchItem();
	
	InfusionRecipe alchemyplant = ThaumcraftApi.addInfusionCraftingRecipe("AlchemyPlant", new ItemStack(GameRegistry.findItem("randommagics", "Device"), 1, 3), 24, new AspectList().add(Aspect.MAGIC, 100).add(Aspect.AURA, 100).add(Aspect.MECHANISM, 100).add(Aspect.VOID, 100).add(Aspect.WATER, 100),
			new ItemStack(ConfigBlocks.blockMetalDevice, 1, 3), new ItemStack[]
					{new ItemStack(ConfigBlocks.blockMetalDevice, 1,3),
						new ItemStack(Init.BlockMindWarped, 1),
						new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
						new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
						new ItemStack(Init.PrimIngot, 1),
						new ItemStack(ConfigBlocks.blockMetalDevice, 1,3),
						new ItemStack(Init.PrimIngot, 1),
						new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
						new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
						new ItemStack(Init.BlockMindWarped, 1),
						new ItemStack(ConfigBlocks.blockMetalDevice, 1,3),
						new ItemStack(Init.BlockMindWarped, 1),
						new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
						new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
						new ItemStack(Init.PrimIngot, 1),
						new ItemStack(ConfigBlocks.blockMetalDevice, 1,3),
						new ItemStack(Init.PrimIngot, 1),
						new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
						new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
						new ItemStack(Init.BlockMindWarped, 1)
					});
	
	new ResearchItem("AlchemyPlant", "RANDOMMAGICS", new AspectList().add(Aspect.MAGIC, 15).add(Aspect.AURA, 20).add(Aspect.MECHANISM, 15).add(Aspect.WATER, 10), 3, 4, 3, new ItemStack(GameRegistry.findItem("randommagics", "Device"), 1, 3))
	.setPages(new ResearchPage[] {new ResearchPage("research.AlchemyPlant.text"), new ResearchPage(alchemyplant)})
	.setParents(new String[] {"ritualStoneSpirit"}).setConcealed()
	.registerResearchItem();
	
	InfusionRecipe ringofsaturation = ThaumcraftApi.addInfusionCraftingRecipe("RingOfSaturation", new ItemStack(Init.ItemRingOfSaturation), 24, new AspectList().add(Aspect.MAGIC, 100).add(Aspect.AURA, 100).add(Aspect.FLESH, 100).add(Aspect.CROP, 100).add(Aspect.LIFE, 100),
			new ItemStack(ConfigItems.itemBaubleBlanks, 1, 1), new ItemStack[]
					{new ItemStack(Items.cake),
						new ItemStack(ConfigBlocks.blockCustomPlant, 1, 1),
						new ItemStack(ConfigItems.itemResource, 1, 14),
						new ItemStack(Init.PrimIngot),
					});
	
	new ResearchItem("RingOfSaturation", "RANDOMMAGICS", new AspectList().add(Aspect.MAGIC, 15).add(Aspect.AURA, 20).add(Aspect.FLESH, 15), 0, -3, 2, new ItemStack(Init.ItemRingOfSaturation))
	.setPages(new ResearchPage[] {new ResearchPage("research.RingOfSaturation.text"), new ResearchPage(ringofsaturation)})
	.setParents(new String[] {"Starts"}).setConcealed()
	.registerResearchItem();
	
	PowerRitualReciepe timeExpander = new PowerRitualReciepe(new ItemStack(ConfigItems.itemEldritchObject, 1, 3), 1500, new ItemStack(Init.BlockDevice, 1, 4), "TimeExpander").register();
	
	new ResearchItem("TimeExpander", "RANDOMMAGICS", new AspectList().add(Aspect.MAGIC, 15).add(Aspect.AURA, 20).add(Aspect.MECHANISM, 15).add(Aspect.MIND, 10), -1, 5, 2, new ItemStack(GameRegistry.findItem("randommagics", "Device"), 1, 4))
	.setPages(new ResearchPage[] {new ResearchPage("research.TimeExpander.text"), new CustomRitualResearchPage(timeExpander)})
	.setParents(new String[] {"ritualStonePower"}).setConcealed()
	.registerResearchItem();
	
	PowerRitualReciepe infStones[] = new PowerRitualReciepe[6];
	for (int i = 0; i < 6; i++)
		infStones[i] = new PowerRitualReciepe(new ItemStack(ConfigBlocks.blockCrystal, 64, i), 3000, new ItemStack(Init.ItemInfinityStone, 1, i)).register();
	
	new ResearchItem("InfinityStones", "RANDOMMAGICS", new AspectList().add(Aspect.MAGIC, 15).add(Aspect.AURA, 20).add(Aspect.AIR, 10).add(Aspect.FIRE, 10).add(Aspect.WATER, 10).add(Aspect.EARTH, 10).add(Aspect.ORDER, 10).add(Aspect.ENTROPY, 10), -2, 5, 2, new ItemStack(Init.ItemInfinityStone))
	.setPages(new ResearchPage[] {new ResearchPage("research.InfinityStones.text"), new CustomRitualResearchPage(infStones[0]), new CustomRitualResearchPage(infStones[1]), new CustomRitualResearchPage(infStones[2]), new CustomRitualResearchPage(infStones[3]), new CustomRitualResearchPage(infStones[4]), new CustomRitualResearchPage(infStones[5])})
	.setParents(new String[] {"ritualStonePower"}).setConcealed()
	.registerResearchItem();
	
	ShapedArcaneRecipe infinitypick = ThaumcraftApi.addArcaneCraftingRecipe("GreaterTools",new ItemStack(Init.ItemPickaxeOfTheLordOfTheEarth), new AspectList().add(Aspect.ORDER, 500).add(Aspect.EARTH, 500), 
			 new Object[] { "SSS", " R ", " R ",
					 'S', new ItemStack(Init.ItemInfinityStone, 1, 3),
					 'R', new ItemStack(Init.PrimIngot)});
	
	ShapedArcaneRecipe infinitysword = ThaumcraftApi.addArcaneCraftingRecipe("GreaterTools",new ItemStack(Init.ItemSwordOfInfiniteDestruction), new AspectList().add(Aspect.ENTROPY, 500).add(Aspect.EARTH, 500), 
			 new Object[] { " S ", " S ", " R ",
					 'S', new ItemStack(Init.ItemInfinityStone, 1, 5),
					 'R', new ItemStack(Init.ItemWandRodPrimal)});
	
	PowerRitualReciepe disintegration = new EnchantmentRitualReciepe(new ItemStack(Init.ItemInfinityStone, 1, 5), 30000, EnchantmentInit.Disintegration, 1, "GreaterTools").register();
	PowerRitualReciepe fortune = new EnchantmentRitualReciepe(new ItemStack(Items.nether_star, 32), 1000, Enchantment.fortune, 32, "GreaterTools").register();
	PowerRitualReciepe augTridim = new EnchantmentRitualReciepe(new ItemStack(Blocks.obsidian, 64), 512, EnchantmentInit.TriDimensionalAugment, 1, "GreaterTools").register();
	PowerRitualReciepe augDist = new EnchantmentRitualReciepe(new ItemStack(Items.diamond_pickaxe), 128, EnchantmentInit.DistanceAugment, 1, "GreaterTools").register();
	PowerRitualReciepe augAOE = new EnchantmentRitualReciepe(new ItemStack(ConfigItems.itemPrimalCrusher), 128, EnchantmentInit.AOEAugment, 1, "GreaterTools").register();
	PowerRitualReciepe augTrashVoider = new EnchantmentRitualReciepe(new ItemStack(ConfigItems.itemPickVoid), 128, EnchantmentInit.TrashVoiderAugment, 1, "GreaterTools").register();
	PowerRitualReciepe augCooldownreduct = new EnchantmentRitualReciepe(new ItemStack(Items.nether_star), 128, EnchantmentInit.CooldownReductionAugment, 1, "GreaterTools").register();
	PowerRitualReciepe augMatterCondenser = new EnchantmentRitualReciepe(new ItemStack(Items.ender_pearl, 16), 128, EnchantmentInit.MatterCondenser, 1, "GreaterTools").register();
	
	new ResearchItem("GreaterTools", "RANDOMMAGICS", new AspectList().add(Aspect.MAGIC, 15).add(Aspect.AURA, 20).add(Aspect.AIR, 10).add(Aspect.FIRE, 10).add(Aspect.WATER, 10).add(Aspect.EARTH, 10).add(Aspect.ORDER, 10).add(Aspect.ENTROPY, 10).add(Aspect.TOOL, 10), -1, 7, 2, new ItemStack(Init.ItemPickaxeOfTheLordOfTheEarth))
	.setPages(new ResearchPage[] {new ResearchPage("research.GreaterTools.text"), new ResearchPage(infinitypick), new ResearchPage(infinitysword), new CustomRitualResearchPage(fortune), new CustomRitualResearchPage(augTridim), new CustomRitualResearchPage(augDist), new CustomRitualResearchPage(augAOE), new CustomRitualResearchPage(augTrashVoider), new CustomRitualResearchPage(augCooldownreduct), new CustomRitualResearchPage(disintegration), new CustomRitualResearchPage(augMatterCondenser)})
	.setParents(new String[] {"InfinityStones"}).setConcealed()
	.registerResearchItem();
	
	ShapedArcaneRecipe ringrunicepic = ThaumcraftApi.addArcaneCraftingRecipe("ringRunicEpic",new ItemStack(Init.ItemInfniteRunicShieldingRing), new AspectList().add(Aspect.ORDER, 500).add(Aspect.EARTH, 500), 
			 new Object[] { "IOI", "WRW", "IWI",
					 'O', new ItemStack(Init.ItemInfinityStone, 1, 4),
					 'I', new ItemStack(Init.PrimIngot),
					 'W', new ItemStack(Init.ItemInfinityStone, 1, 2),
					 'R', new ItemStack(ConfigItems.itemRingRunic, 1, 1)});
	
	new ResearchItem("ringRunicEpic", "RANDOMMAGICS", new AspectList().add(Aspect.MAGIC, 15).add(Aspect.AURA, 20).add(Aspect.AIR, 10).add(Aspect.FIRE, 10).add(Aspect.WATER, 10).add(Aspect.EARTH, 10).add(Aspect.ORDER, 10).add(Aspect.ENTROPY, 10).add(Aspect.ARMOR, 10), -3, 7, 2, new ItemStack(Init.ItemInfniteRunicShieldingRing))
	.setPages(new ResearchPage[] {new ResearchPage("research.ringRunicEpic.text"), new ResearchPage(ringrunicepic)})
	.setParents(new String[] {"InfinityStones"}).setConcealed()
	.registerResearchItem();
	
	CrucibleRecipe lifeStealingPlant = ThaumcraftApi.addCrucibleRecipe("LifeStealingPlant", new ItemStack(Init.BlockLifeStealingPlant), new ItemStack(Blocks.deadbush), new AspectList().add(Aspect.GREED, 100).add(Aspect.HUNGER, 100).add(Aspect.LIFE, 100));
	new ResearchItem("ringRunicEpic", "RANDOMMAGICS", new AspectList().add(Aspect.MAGIC, 15).add(Aspect.AURA, 20).add(Aspect.AIR, 10).add(Aspect.FIRE, 10).add(Aspect.WATER, 10).add(Aspect.EARTH, 10).add(Aspect.ORDER, 10).add(Aspect.ENTROPY, 10).add(Aspect.ARMOR, 10), -3, 7, 2, new ItemStack(Init.ItemInfniteRunicShieldingRing))
	.setPages(new ResearchPage[] {new ResearchPage("blahblah ringRunicEpic"), new ResearchPage(ringrunicepic)})
	.setParents(new String[] {"InfinityStones"})
	.registerResearchItem();
	
	new ResearchItem("LifeStealingPlant", "RANDOMMAGICS", new AspectList().add(Aspect.MAGIC, 15).add(Aspect.LIFE, 20).add(Aspect.HUNGER, 10).add(Aspect.GREED, 10).add(Aspect.PLANT, 10).add(Aspect.POISON, 10).add(Aspect.ENTROPY, 10), -5, 7, 0, new ItemStack(Init.BlockLifeStealingPlant))
	.setPages(new ResearchPage[] {new ResearchPage("research.LifeStealingPlant.text"), new ResearchPage(lifeStealingPlant)})
	.setParents(new String[] {"InfinityStones"}).setConcealed()
	.registerResearchItem();
	
	ShapedArcaneRecipe lifeessenceextractor = ThaumcraftApi.addArcaneCraftingRecipe("LifeEssenceExtractor",new ItemStack(Init.BlockLifeEssenceExtractor), new AspectList().add(Aspect.ORDER, 50).add(Aspect.WATER, 50).add(Aspect.AIR, 100), 
			 new Object[] { "ATA", "AIM", "AAA",
					 'A', new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6),
					 'I', new ItemStack(Init.PrimIngot),
					 'M', new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 1),
					 'T', new ItemStack(ConfigBlocks.blockTube, 1, 3)});
	
	new ResearchItem("LifeEssenceExtractor", "RANDOMMAGICS", new AspectList().add(Aspect.MAGIC, 15).add(Aspect.LIFE, 20).add(Aspect.MECHANISM, 10), -5, 9, 0, new ItemStack(Init.BlockLifeEssenceExtractor))
	.setPages(new ResearchPage[] {new ResearchPage("research.LifeEssenceExtractor.text"), new ResearchPage(lifeessenceextractor)})
	.setParents(new String[] {"LifeStealingPlant"}).setConcealed()
	.registerResearchItem();
	
	ShapedArcaneRecipe lifeessenceJar = ThaumcraftApi.addArcaneCraftingRecipe("LifeEssenceJar",new ItemStack(Init.BlockLifeEssenceJar), new AspectList().add(Aspect.ORDER, 100).add(Aspect.WATER, 150), 
			 new Object[] { "GIG", "G G", "GGG",
					 'G', new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 2),
					 'I', new ItemStack(Init.PrimIngot)});
	
	new ResearchItem("LifeEssenceJar", "RANDOMMAGICS", new AspectList().add(Aspect.MAGIC, 15).add(Aspect.LIFE, 20).add(Aspect.CRYSTAL, 10).add(Aspect.WATER, 25), -7, 9, 0, new ItemStack(Init.BlockLifeEssenceJar))
	.setPages(new ResearchPage[] {new ResearchPage("research.LifeEssenceJar.text"), new ResearchPage(lifeessenceJar)})
	.setParents(new String[] {"LifeEssenceExtractor"}).setRound().setSecondary().setConcealed()
	.registerResearchItem();
	
	InfusionRecipe auraInfusionMatrix = ThaumcraftApi.addInfusionCraftingRecipe("auraInfusion", new ItemStack(GameRegistry.findItem("randommagics", "Device"), 1, 5), 25,
		new AspectList().add(Aspect.AURA, 1000).add(Aspect.MAGIC, 1000).add(Aspect.LIFE, 1000).add(Aspect.ENERGY, 1000).add(Aspect.TAINT, 500).add(Aspect.ELDRITCH, 1000), new ItemStack(Init.RitualStonePower),
		new ItemStack[] {
			new ItemStack(ConfigBlocks.blockStoneDevice, 1, 2),
			new ItemStack(ConfigItems.itemFocusPrimal),
			new ItemStack(Init.BlockMindWarped),
			new ItemStack(Init.BlockMindWarped),
			new ItemStack(Init.PrimIngot),
			new ItemStack(ConfigBlocks.blockStoneDevice, 1, 2),
			new ItemStack(Init.PrimIngot),
			new ItemStack(Init.ItemInfinityStone, 1, 0),
			new ItemStack(Init.ItemInfinityStone, 1, 1),
			new ItemStack(Init.ItemInfinityStone, 1, 2),
			new ItemStack(ConfigBlocks.blockStoneDevice, 1, 2),
			new ItemStack(Init.ItemInfinityStone, 1, 3),
			new ItemStack(Init.ItemInfinityStone, 1, 4),
			new ItemStack(Init.ItemInfinityStone, 1, 5),
			new ItemStack(Init.BlockLifeEssenceJar),
			new ItemStack(ConfigBlocks.blockStoneDevice, 1, 2),
			new ItemStack(ConfigItems.itemEldritchObject, 1, 0),
			new ItemStack(GameRegistry.findItem("randommagics", "Device"), 1, 4),
			new ItemStack(Init.BlockLifeEssenceExtractor),
			new ItemStack(Init.BlockLifeStealingPlant)
	});
	
	new ResearchItem("auraInfusion", "RANDOMMAGICS", new AspectList().add(Aspect.MAGIC, 15).add(Aspect.AURA, 20).add(Aspect.MECHANISM, 10).add(Aspect.HUNGER, 25), -7, 7, 3, new ItemStack(GameRegistry.findItem("randommagics", "Device"), 1, 5))
	.setPages(new ResearchPage[] {new ResearchPage("research.auraInfusion.text"), new ResearchPage(auraInfusionMatrix)})
	.setParents(new String[] {"LifeEssenceJar"}).setConcealed()
	.registerResearchItem();
	
	AuraInfusionRecipe warpCleaner = AuraInfusionHelper.addAuraInfusionrecipe("warpCleaner", 8, 0, new ItemStack(Init.ItemWarpCleaner), new ItemStack(Items.golden_apple, 1, 1),
			new ItemStack(ConfigItems.itemBathSalts),
			new ItemStack(ConfigItems.itemResource, 1, 9),
			new ItemStack(ConfigItems.itemZombieBrain),
			new ItemStack(ConfigBlocks.blockCustomPlant, 1, 1));
	
	new ResearchItem("warpCleaner", "RANDOMMAGICS", new AspectList().add(Aspect.HEAL, 15).add(Aspect.MIND, 20).add(Aspect.LIGHT, 10).add(Aspect.ELDRITCH, 25), -9, 7, 1, new ItemStack(Init.ItemWarpCleaner))
	.setPages(new ResearchPage[] {new ResearchPage("research.warpCleaner.text"), new AuraInfusionResearchPage(warpCleaner)})
	.setParents(new String[] {"auraInfusion"}).setConcealed()
	.registerResearchItem();
	
	AuraInfusionRecipe voidSlate = AuraInfusionHelper.addAuraInfusionrecipe("voidSlate", 5, 1, new ItemStack(Init.ItemRMResource, 1, 0), new ItemStack(ConfigItems.itemResource, 1, 16),
			new ItemStack(ConfigItems.itemResource, 1, 16),
			new ItemStack(Init.ItemRMResource, 1, 25),
			new ItemStack(Init.PrimIngot),
			new ItemStack(Init.ItemRMResource, 1, 26),
			new ItemStack(ConfigItems.itemResource, 1, 16),
			new ItemStack(Init.ItemRMResource, 1, 25),
			new ItemStack(Init.PrimIngot),
			new ItemStack(Init.ItemRMResource, 1,26));
	
	new ResearchItem("voidSlate", "RANDOMMAGICS", new AspectList().add(Aspect.METAL, 15).add(Aspect.VOID, 20), -10, 8, 1, new ItemStack(Init.ItemRMResource, 1, 0))
	.setPages(new ResearchPage[] {new ResearchPage("research.voidSlate.text"), new AuraInfusionResearchPage(voidSlate)})
	.setParents(new String[] {"auraInfusion"}).setConcealed()
	.registerResearchItem();
	
	InfusionRecipe thaumSlate = ThaumcraftApi.addInfusionCraftingRecipe("thaumSlate", new ItemStack(Init.ItemRMResource, 1, 1), 25,
			new AspectList().add(Aspect.EXCHANGE, 100), new ItemStack(Init.ItemRMResource, 1, 0),
			new ItemStack[] {
				new ItemStack(ConfigItems.itemResource, 1, 2),
				new ItemStack(ConfigItems.itemResource, 1, 2),
				new ItemStack(ConfigItems.itemResource, 1, 2),
				new ItemStack(ConfigItems.itemResource, 1, 2)
		});
	
	new ResearchItem("thaumSlate", "RANDOMMAGICS", new AspectList().add(Aspect.METAL, 15).add(Aspect.EXCHANGE, 20), -12, 8, 1, new ItemStack(Init.ItemRMResource, 1, 1))
	.setPages(new ResearchPage[] {new ResearchPage("research.thaumSlate.text"), new ResearchPage(thaumSlate)})
	.setParents(new String[] {"voidSlate"}).setSecondary().setConcealed()
	.registerResearchItem();
	
	InfusionRecipe essenceSlate = ThaumcraftApi.addInfusionCraftingRecipe("essenceSlate", new ItemStack(Init.ItemRMResource, 1, 2), 25,
			new AspectList().add(Aspect.EXCHANGE, 100), new ItemStack(Init.ItemRMResource, 1, 0),
			new ItemStack[] {
				new ItemStack(ConfigItems.itemWispEssence),
				new ItemStack(ConfigItems.itemWispEssence),
				new ItemStack(ConfigItems.itemWispEssence),
				new ItemStack(ConfigItems.itemWispEssence)
		});
	
	new ResearchItem("essenceSlate", "RANDOMMAGICS", new AspectList().add(Aspect.AURA, 15).add(Aspect.EXCHANGE, 20), -12, 9, 1, new ItemStack(Init.ItemRMResource, 1, 2))
	.setPages(new ResearchPage[] {new ResearchPage("research.essenceSlate.text"), new ResearchPage(essenceSlate)})
	.setParents(new String[] {"voidSlate"}).setSecondary().setConcealed()
	.registerResearchItem();
	
	InfusionRecipe crystalSlate = ThaumcraftApi.addInfusionCraftingRecipe("crystalSlate", new ItemStack(Init.ItemRMResource, 1, 3), 25,
			new AspectList().add(Aspect.EXCHANGE, 100), new ItemStack(Init.ItemRMResource, 1, 0),
			new ItemStack[] {
				new ItemStack(ConfigItems.itemShard, 1, 6),
				new ItemStack(ConfigItems.itemShard, 1, 0),
				new ItemStack(ConfigItems.itemShard, 1, 1),
				new ItemStack(ConfigItems.itemShard, 1, 2),
				new ItemStack(ConfigItems.itemShard, 1, 3),
				new ItemStack(ConfigItems.itemShard, 1, 4),
				new ItemStack(ConfigItems.itemShard, 1, 5)
		});
	
	new ResearchItem("crystalSlate", "RANDOMMAGICS", new AspectList().add(Aspect.CRYSTAL, 15).add(Aspect.EXCHANGE, 20), -12, 10, 1, new ItemStack(Init.ItemRMResource, 1, 3))
	.setPages(new ResearchPage[] {new ResearchPage("research.crystalSlate.text"), new ResearchPage(crystalSlate)})
	.setParents(new String[] {"voidSlate"}).setSecondary().setConcealed()
	.registerResearchItem();
	
	InfusionRecipe taintSlate = ThaumcraftApi.addInfusionCraftingRecipe("taintSlate", new ItemStack(Init.ItemRMResource, 1, 4), 25,
			new AspectList().add(Aspect.EXCHANGE, 100), new ItemStack(Init.ItemRMResource, 1, 0),
			new ItemStack[] {
				new ItemStack(ConfigItems.itemBottleTaint),
				new ItemStack(ConfigItems.itemBottleTaint),
				new ItemStack(ConfigItems.itemBottleTaint),
				new ItemStack(ConfigItems.itemBottleTaint)
		});
	
	new ResearchItem("taintSlate", "RANDOMMAGICS", new AspectList().add(Aspect.TAINT, 15).add(Aspect.EXCHANGE, 20), -12, 11, 1, new ItemStack(Init.ItemRMResource, 1, 4))
	.setPages(new ResearchPage[] {new ResearchPage("research.taintSlate.text"), new ResearchPage(taintSlate)})
	.setParents(new String[] {"voidSlate"}).setSecondary().setConcealed()
	.registerResearchItem();
	
	InfusionRecipe netherSlate = ThaumcraftApi.addInfusionCraftingRecipe("netherSlate", new ItemStack(Init.ItemRMResource, 1, 5), 25,
			new AspectList().add(Aspect.EXCHANGE, 100), new ItemStack(Init.ItemRMResource, 1, 0),
			new ItemStack[] {
				new ItemStack(Items.nether_star),
				new ItemStack(Items.nether_star),
				new ItemStack(Items.nether_star),
				new ItemStack(Items.nether_star)
		});
	
	new ResearchItem("netherSlate", "RANDOMMAGICS", new AspectList().add(Aspect.FIRE, 15).add(Aspect.EXCHANGE, 20), -11, 12, 1, new ItemStack(Init.ItemRMResource, 1, 5))
	.setPages(new ResearchPage[] {new ResearchPage("research.netherSlate.text"), new ResearchPage(netherSlate)})
	.setParents(new String[] {"voidSlate"}).setSecondary().setConcealed()
	.registerResearchItem();
	
	InfusionRecipe diamondSlate = ThaumcraftApi.addInfusionCraftingRecipe("diamondSlate", new ItemStack(Init.ItemRMResource, 1, 6), 25,
			new AspectList().add(Aspect.EXCHANGE, 100), new ItemStack(Init.ItemRMResource, 1, 0),
			new ItemStack[] {
				new ItemStack(Blocks.diamond_block),
				new ItemStack(Blocks.diamond_block),
				new ItemStack(Blocks.diamond_block),
				new ItemStack(Blocks.diamond_block),
				new ItemStack(Blocks.diamond_block),
				new ItemStack(Blocks.diamond_block),
				new ItemStack(Blocks.diamond_block),
				new ItemStack(Blocks.diamond_block),
				new ItemStack(Blocks.diamond_block),
				new ItemStack(Blocks.diamond_block),
				new ItemStack(Blocks.diamond_block),
				new ItemStack(Blocks.diamond_block),
				new ItemStack(Blocks.diamond_block),
				new ItemStack(Blocks.diamond_block),
				new ItemStack(Blocks.diamond_block),
				new ItemStack(Blocks.diamond_block)
		});
	
	new ResearchItem("diamondSlate", "RANDOMMAGICS", new AspectList().add(Aspect.EARTH, 15).add(Aspect.EXCHANGE, 20), -10, 12, 1, new ItemStack(Init.ItemRMResource, 1, 6))
	.setPages(new ResearchPage[] {new ResearchPage("research.diamondSlate.text"), new ResearchPage(diamondSlate)})
	.setParents(new String[] {"voidSlate"}).setSecondary().setConcealed()
	.registerResearchItem();
	
	InfusionRecipe soulSlate = ThaumcraftApi.addInfusionCraftingRecipe("soulSlate", new ItemStack(Init.ItemRMResource, 1, 7), 25,
			new AspectList().add(Aspect.EXCHANGE, 100), new ItemStack(Init.ItemRMResource, 1, 0),
			new ItemStack[] {
				new ItemStack(Items.skull),
				new ItemStack(Items.skull),
				new ItemStack(Items.skull),
				new ItemStack(Items.skull),
				new ItemStack(Items.skull),
				new ItemStack(Items.skull),
				new ItemStack(Items.skull),
				new ItemStack(Items.skull)
		});
	
	new ResearchItem("soulSlate", "RANDOMMAGICS", new AspectList().add(Aspect.SOUL, 15).add(Aspect.EXCHANGE, 20), -9, 12, 1, new ItemStack(Init.ItemRMResource, 1, 7))
	.setPages(new ResearchPage[] {new ResearchPage("research.soulSlate.text"), new ResearchPage(soulSlate)})
	.setParents(new String[] {"voidSlate"}).setSecondary().setConcealed()
	.registerResearchItem();
	
	InfusionRecipe ironSlate = ThaumcraftApi.addInfusionCraftingRecipe("ironSlate", new ItemStack(Init.ItemRMResource, 1, 8), 25,
			new AspectList().add(Aspect.EXCHANGE, 100), new ItemStack(Init.ItemRMResource, 1, 0),
			new ItemStack[] {
				new ItemStack(Blocks.iron_block),
				new ItemStack(Blocks.iron_block),
				new ItemStack(Blocks.iron_block),
				new ItemStack(Blocks.iron_block),
				new ItemStack(Blocks.iron_block),
				new ItemStack(Blocks.iron_block),
				new ItemStack(Blocks.iron_block),
				new ItemStack(Blocks.iron_block),
				new ItemStack(Blocks.iron_block),
				new ItemStack(Blocks.iron_block),
				new ItemStack(Blocks.iron_block),
				new ItemStack(Blocks.iron_block),
				new ItemStack(Blocks.iron_block),
				new ItemStack(Blocks.iron_block),
				new ItemStack(Blocks.iron_block),
				new ItemStack(Blocks.iron_block)
		});
	
	new ResearchItem("ironSlate", "RANDOMMAGICS", new AspectList().add(Aspect.METAL, 15).add(Aspect.EXCHANGE, 20), -8, 12, 1, new ItemStack(Init.ItemRMResource, 1, 8))
	.setPages(new ResearchPage[] {new ResearchPage("research.ironSlate.text"), new ResearchPage(ironSlate)})
	.setParents(new String[] {"voidSlate"}).setSecondary().setConcealed()
	.registerResearchItem();
	
	InfusionRecipe primSlate = ThaumcraftApi.addInfusionCraftingRecipe("primSlate", new ItemStack(Init.ItemRMResource, 1, 9), 25,
			new AspectList().add(Aspect.ELDRITCH, 150).add(Aspect.ARMOR, 100).add(Aspect.ENERGY, 200).add(Aspect.EXCHANGE, 250), new ItemStack(Init.ItemRMResource, 1, 0),
			new ItemStack[] {
				new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
				new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
				new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
				new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
				new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
				new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
				new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
				new ItemStack(ConfigItems.itemEldritchObject, 1, 3)
		});
	
	new ResearchItem("primSlate", "RANDOMMAGICS", new AspectList().add(Aspect.ENERGY, 32).add(Aspect.EXCHANGE, 20), -13, 13, 1, new ItemStack(Init.ItemRMResource, 1, 9))
	.setPages(new ResearchPage[] {new ResearchPage("research.primSlate.text"), new ResearchPage(primSlate)})
	.setParents(new String[] {"voidSlate", "ironSlate", "soulSlate", "diamondSlate", "netherSlate", "taintSlate", "crystalSlate", "essenceSlate", "thaumSlate"}).setSecondary().setConcealed()
	.registerResearchItem();
	
	ShapedArcaneRecipe alloySlate = ThaumcraftApi.addArcaneCraftingRecipe("slates",new ItemStack(Init.ItemRMResource, 1, 10), new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.ENTROPY, 50).add(Aspect.AIR, 50).add(Aspect.WATER, 50).add(Aspect.FIRE, 50), 
			 new Object[] { "ABA", "BCB", "ABA",
					 Character.valueOf('A'), new ItemStack(Init.ItemRMResource, 1, 6),
					 Character.valueOf('B'), new ItemStack(Init.ItemRMResource, 1, 8),
					 Character.valueOf('C'), new ItemStack(Init.ItemRMResource, 1, 1)});
	
	ShapedArcaneRecipe etherealSlate = ThaumcraftApi.addArcaneCraftingRecipe("slates",new ItemStack(Init.ItemRMResource, 1, 11), new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.ENTROPY, 50).add(Aspect.AIR, 50).add(Aspect.WATER, 50).add(Aspect.FIRE, 50), 
			 new Object[] { "ABA", "CCC", "DDD",
					 Character.valueOf('A'), new ItemStack(Init.ItemRMResource, 1, 5),
					 Character.valueOf('B'), new ItemStack(Init.ItemRMResource, 1, 7),
					 Character.valueOf('C'), new ItemStack(Init.ItemRMResource, 1, 2),
					 Character.valueOf('D'), new ItemStack(Init.ItemRMResource, 1, 3)
					 });
	
	ShapedArcaneRecipe reinforcedSlate = ThaumcraftApi.addArcaneCraftingRecipe("slates",new ItemStack(Init.ItemRMResource, 1, 12), new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.ENTROPY, 50).add(Aspect.AIR, 50).add(Aspect.WATER, 50).add(Aspect.FIRE, 50), 
			 new Object[] { "ABA", "BAB", "ABA",
					 Character.valueOf('A'), new ItemStack(Init.ItemRMResource, 1, 4),
					 Character.valueOf('B'), new ItemStack(Init.ItemRMResource, 1, 1)
					 });
	
	ShapedArcaneRecipe crystallicSlate = ThaumcraftApi.addArcaneCraftingRecipe("slates",new ItemStack(Init.ItemRMResource, 1, 13), new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.ENTROPY, 50).add(Aspect.AIR, 50).add(Aspect.WATER, 50).add(Aspect.FIRE, 50), 
			 new Object[] { "ABA", "BAB", "ABA",
					 Character.valueOf('A'), new ItemStack(Init.ItemRMResource, 1, 3),
					 Character.valueOf('B'), new ItemStack(Init.ItemRMResource, 1, 5)
					 });
	
	ShapedArcaneRecipe compoundSlate = ThaumcraftApi.addArcaneCraftingRecipe("slates",new ItemStack(Init.ItemRMResource, 1, 14), new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.ENTROPY, 50).add(Aspect.AIR, 50).add(Aspect.WATER, 50).add(Aspect.FIRE, 50), 
			 new Object[] { "AAA", "BBB", "CCC",
					 Character.valueOf('A'), new ItemStack(Init.ItemRMResource, 1, 13),
					 Character.valueOf('B'), new ItemStack(Init.ItemRMResource, 1, 11),
					 Character.valueOf('C'), new ItemStack(Init.ItemRMResource, 1, 12)
					 });
	
	ShapedArcaneRecipe coreSlate = ThaumcraftApi.addArcaneCraftingRecipe("slates",new ItemStack(Init.ItemRMResource, 1, 15), new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.ENTROPY, 50).add(Aspect.AIR, 50).add(Aspect.WATER, 50).add(Aspect.FIRE, 50), 
			 new Object[] { "ABA", "CDC", "ABA",
					 Character.valueOf('A'), new ItemStack(Init.ItemRMResource, 1, 10),
					 Character.valueOf('B'), new ItemStack(Init.ItemRMResource, 1, 11),
					 Character.valueOf('C'), new ItemStack(Init.ItemRMResource, 1, 14),
					 Character.valueOf('D'), new ItemStack(Init.ItemRMResource, 1, 9)
					 });
	
	ShapedArcaneRecipe calculationSlate = ThaumcraftApi.addArcaneCraftingRecipe("slates",new ItemStack(Init.ItemRMResource, 1, 16), new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.ENTROPY, 50).add(Aspect.AIR, 50).add(Aspect.WATER, 50).add(Aspect.FIRE, 50), 
			 new Object[] { "ABA", "CDC", "ACA",
					 Character.valueOf('A'), new ItemStack(Init.ItemRMResource, 1, 13),
					 Character.valueOf('B'), new ItemStack(Init.ItemRMResource, 1, 11),
					 Character.valueOf('C'), new ItemStack(Init.ItemRMResource, 1, 14),
					 Character.valueOf('D'), new ItemStack(Init.ItemRMResource, 1, 15)
					 });
	
	ShapedArcaneRecipe irrationalSlate = ThaumcraftApi.addArcaneCraftingRecipe("slates",new ItemStack(Init.ItemRMResource, 1, 17), new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.ENTROPY, 50).add(Aspect.AIR, 50).add(Aspect.WATER, 50).add(Aspect.FIRE, 50), 
			 new Object[] { "ABA", "BCB", "CDC",
					 Character.valueOf('A'), new ItemStack(Init.ItemRMResource, 1, 9),
					 Character.valueOf('B'), new ItemStack(Init.ItemRMResource, 1, 14),
					 Character.valueOf('C'), new ItemStack(Init.ItemRMResource, 1, 16),
					 Character.valueOf('D'), new ItemStack(Init.ItemRMResource, 1, 10)
					 });
	
	ShapedArcaneRecipe reinforcedAlloySlate = ThaumcraftApi.addArcaneCraftingRecipe("slates",new ItemStack(Init.ItemRMResource, 1, 18), new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.ENTROPY, 50).add(Aspect.AIR, 50).add(Aspect.WATER, 50).add(Aspect.FIRE, 50), 
			 new Object[] { "ABA", "BCB", "ABA",
					 Character.valueOf('A'), new ItemStack(Init.ItemRMResource, 1, 10),
					 Character.valueOf('B'), new ItemStack(Init.ItemRMResource, 1, 12),
					 Character.valueOf('C'), new ItemStack(Init.ItemRMResource, 1, 15)
					 });
	
	ShapedArcaneRecipe basicProcessingCore = ThaumcraftApi.addArcaneCraftingRecipe("slates",new ItemStack(Init.ItemRMResource, 1, 19), new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.ENTROPY, 50).add(Aspect.AIR, 50).add(Aspect.WATER, 50).add(Aspect.FIRE, 50), 
			 new Object[] { "ABA", "CDE", "AFA",
					 Character.valueOf('A'), new ItemStack(Init.ItemRMResource, 1, 18),
					 Character.valueOf('B'), new ItemStack(Init.ItemRMResource, 1, 17),
					 Character.valueOf('C'), new ItemStack(Init.ItemRMResource, 1, 11),
					 Character.valueOf('D'), new ItemStack(Init.ItemRMResource, 1, 15),
					 Character.valueOf('E'), new ItemStack(Init.ItemRMResource, 1, 16),
					 Character.valueOf('F'), new ItemStack(Init.ItemRMResource, 1, 13)
					 });
	
	ShapedArcaneRecipe ramController = ThaumcraftApi.addArcaneCraftingRecipe("slates",new ItemStack(Init.ItemRMResource, 1, 20), new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.ENTROPY, 50).add(Aspect.AIR, 50).add(Aspect.WATER, 50).add(Aspect.FIRE, 50), 
			 new Object[] { "ABC", "DED", "FFF",
					 Character.valueOf('A'), new ItemStack(Init.ItemRMResource, 1, 16),
					 Character.valueOf('B'), new ItemStack(Init.ItemRMResource, 1, 14),
					 Character.valueOf('C'), new ItemStack(Init.ItemRMResource, 1, 17),
					 Character.valueOf('D'), new ItemStack(Init.ItemRMResource, 1, 18),
					 Character.valueOf('E'), new ItemStack(Init.ItemRMResource, 1, 19),
					 Character.valueOf('F'), new ItemStack(Init.ItemRMResource, 1, 13)
					 });
	
	ShapedArcaneRecipe mfController = ThaumcraftApi.addArcaneCraftingRecipe("slates",new ItemStack(Init.ItemRMResource, 1, 21), new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.ENTROPY, 50).add(Aspect.AIR, 50).add(Aspect.WATER, 50).add(Aspect.FIRE, 50), 
			 new Object[] { "ABA", "CDC", "AEA",
					 Character.valueOf('A'), new ItemStack(Init.ItemRMResource, 1, 16),
					 Character.valueOf('B'), new ItemStack(Init.ItemRMResource, 1, 17),
					 Character.valueOf('C'), new ItemStack(Init.ItemRMResource, 1, 15),
					 Character.valueOf('D'), new ItemStack(Init.ItemRMResource, 1, 19),
					 Character.valueOf('E'), new ItemStack(Init.ItemRMResource, 1, 13)
					 });
	
	ShapedArcaneRecipe mfCapacitor = ThaumcraftApi.addArcaneCraftingRecipe("slates",new ItemStack(Init.ItemRMResource, 1, 22), new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.ENTROPY, 50).add(Aspect.AIR, 50).add(Aspect.WATER, 50).add(Aspect.FIRE, 50), 
			 new Object[] { "AAA", "BBB", "AAA",
					 Character.valueOf('A'), new ItemStack(Init.ItemRMResource, 1, 18),
					 Character.valueOf('B'), new ItemStack(Init.ItemRMResource, 1, 13)
					 });
	
	ShapedArcaneRecipe visFlowController = ThaumcraftApi.addArcaneCraftingRecipe("slates",new ItemStack(Init.ItemRMResource, 1, 23), new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.ENTROPY, 50).add(Aspect.AIR, 50).add(Aspect.WATER, 50).add(Aspect.FIRE, 50), 
			 new Object[] { "ABA", "CDC", "FEF",
					 Character.valueOf('A'), new ItemStack(Init.ItemRMResource, 1, 18),
					 Character.valueOf('B'), new ItemStack(Init.ItemRMResource, 1, 17),
					 Character.valueOf('C'), new ItemStack(Init.ItemRMResource, 1, 16),
					 Character.valueOf('D'), new ItemStack(Init.ItemRMResource, 1, 19),
					 Character.valueOf('E'), new ItemStack(Init.ItemRMResource, 1, 11),
					 Character.valueOf('F'), new ItemStack(Init.ItemRMResource, 1, 0)
					 });
	
	ShapedArcaneRecipe mfRerouter = ThaumcraftApi.addArcaneCraftingRecipe("slates",new ItemStack(Init.ItemRMResource, 1, 24), new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.ENTROPY, 50).add(Aspect.AIR, 50).add(Aspect.WATER, 50).add(Aspect.FIRE, 50), 
			 new Object[] { "ABA", "CDC", "EBE",
					 Character.valueOf('A'), new ItemStack(Init.ItemRMResource, 1, 22),
					 Character.valueOf('B'), new ItemStack(Init.ItemRMResource, 1, 21),
					 Character.valueOf('C'), new ItemStack(Init.ItemRMResource, 1, 20),
					 Character.valueOf('D'), new ItemStack(Init.ItemRMResource, 1, 23),
					 Character.valueOf('E'), new ItemStack(Init.ItemRMResource, 1, 19)
					 });
	
	new ResearchItem("slates", "RANDOMMAGICS", new AspectList().add(Aspect.CRAFT, 32).add(Aspect.MAGIC, 20), -10, 5, 1, new ItemStack(Init.ItemRMResource, 1, 15))
	.setPages(new ResearchPage[] {new ResearchPage("research.slates.text"), new ResearchPage(alloySlate), new ResearchPage(etherealSlate), new ResearchPage(reinforcedSlate),
			new ResearchPage(crystallicSlate), new ResearchPage(compoundSlate), new ResearchPage(coreSlate), new ResearchPage(calculationSlate),
			new ResearchPage(irrationalSlate), new ResearchPage(reinforcedAlloySlate), new ResearchPage(basicProcessingCore), new ResearchPage(ramController),
			new ResearchPage(mfController), new ResearchPage(mfCapacitor), new ResearchPage(visFlowController), new ResearchPage(mfRerouter)})
	.setParents(new String[] {"voidSlate"}).setConcealed()
	.registerResearchItem();
	
	AuraInfusionRecipe molecularChestplate = AuraInfusionHelper.addAuraInfusionrecipe("molecularArmor", 25, 8, new ItemStack(Init.OverlordChestplate), new ItemStack(Init.EldChestplate),
			new ItemStack(Init.ItemRMResource, 1, 19),
			new ItemStack(Init.ItemRMResource, 1, 17),
			new ItemStack(Init.ItemRMResource, 1, 16),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 22),
			new ItemStack(Init.ItemRMResource, 1, 24),
			new ItemStack(Init.ItemRMResource, 1, 20),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 16),
			new ItemStack(Init.ItemRMResource, 1, 9),
			new ItemStack(Init.ItemRMResource, 1, 11),
			new ItemStack(Init.ItemRMResource, 1, 9),
			new ItemStack(Init.ItemRMResource, 1, 16),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 21),
			new ItemStack(Init.ItemRMResource, 1, 24),
			new ItemStack(Init.ItemRMResource, 1, 23),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 16),
			new ItemStack(Init.ItemRMResource, 1, 17));
	
	AuraInfusionRecipe molecularHelmet = AuraInfusionHelper.addAuraInfusionrecipe("molecularArmor", 25, 8, new ItemStack(Init.OverlordHelmet), new ItemStack(Init.EldHelmet),
			new ItemStack(Init.ItemRMResource, 1, 19),
			new ItemStack(Init.ItemRMResource, 1, 23),
			new ItemStack(Init.ItemRMResource, 1, 16),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 22),
			new ItemStack(Init.ItemRMResource, 1, 24),
			new ItemStack(Init.ItemRMResource, 1, 22),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 20),
			new ItemStack(Init.ItemRMResource, 1, 21),
			new ItemStack(Init.ItemRMResource, 1, 20),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 22),
			new ItemStack(Init.ItemRMResource, 1, 24),
			new ItemStack(Init.ItemRMResource, 1, 22),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 16),
			new ItemStack(Init.ItemRMResource, 1, 17));
	
	AuraInfusionRecipe molecularBoots = AuraInfusionHelper.addAuraInfusionrecipe("molecularArmor", 25, 8, new ItemStack(Init.OverlordBoots), new ItemStack(Init.EldBoots),
			new ItemStack(Init.ItemRMResource, 1, 19),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 24),
			new ItemStack(Init.ItemRMResource, 1, 21),
			new ItemStack(Init.ItemRMResource, 1, 22),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 20),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 24),
			new ItemStack(Init.ItemRMResource, 1, 23),
			new ItemStack(Init.ItemRMResource, 1, 22),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 18));
	
	AuraInfusionRecipe molecularleggings = AuraInfusionHelper.addAuraInfusionrecipe("molecularArmor", 25, 8, new ItemStack(Init.OverlordLeggins), new ItemStack(Init.EldLeggins),
			new ItemStack(Init.ItemRMResource, 1, 19),
			new ItemStack(Init.ItemRMResource, 1, 24),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 22),
			new ItemStack(Init.ItemRMResource, 1, 21),
			new ItemStack(Init.ItemRMResource, 1, 22),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 23),
			new ItemStack(Init.ItemRMResource, 1, 21),
			new ItemStack(Init.ItemRMResource, 1, 23),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 22),
			new ItemStack(Init.ItemRMResource, 1, 21),
			new ItemStack(Init.ItemRMResource, 1, 22),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 18),
			new ItemStack(Init.ItemRMResource, 1, 20));
	
	new ResearchItem("molecularArmor", "RANDOMMAGICS", new AspectList().add(Aspect.ARMOR, 32).add(Aspect.MOTION, 20), -13, 5, 1, new ItemStack(Init.OverlordHelmet))
	.setPages(new ResearchPage[] {new ResearchPage("research.molecularArmor.text"), new AuraInfusionResearchPage(molecularHelmet), new AuraInfusionResearchPage(molecularChestplate), new AuraInfusionResearchPage(molecularleggings), new AuraInfusionResearchPage(molecularBoots)})
	.setParents(new String[] {"slates"}).setConcealed()
	.registerResearchItem();
	
	AuraInfusionRecipe soulBinder = AuraInfusionHelper.addAuraInfusionrecipe("curseRitual", 25, 2, new ItemStack(Init.ItemSoulBinder), new ItemStack(Items.ender_eye), new ItemStack(Init.ItemRMResource, 1, 25), new ItemStack(Init.PrimIngot), new ItemStack(ConfigItems.itemResource, 1, 17));
	ShapedArcaneRecipe ritualPylon = ThaumcraftApi.addArcaneCraftingRecipe("curseRitual", new ItemStack(Init.BlockRitualPylon), new AspectList().add(Aspect.ORDER, 500).add(Aspect.AIR, 650),
			"AAA", "ABA", "AAA", 'A', new ItemStack(Init.ItemRMResource, 1, 26), 'B', new ItemStack(Init.ItemRMResource, 1, 13));
	
	new ResearchItem("curseRitual", "RANDOMMAGICS", new AspectList().add(Aspect.MAN, 32).add(Aspect.MAGIC, 20), -6, 4, 1, new ItemStack(Init.ItemCursedScroll))
	.setPages(new ResearchPage[] {new ResearchPage("research.curseRitual.text"), new AuraInfusionResearchPage(soulBinder), new ResearchPage(ritualPylon), new ResearchPage(new ResourceLocation("randommagics", "textures/blocks/Device.png"), "Structure")})
	.setParents(new String[] {"ritualStoneSpirit", "slates"}).setConcealed()
	.registerResearchItem();
	
	ShapedArcaneRecipe fociunequaltrade = ThaumcraftApi.addArcaneCraftingRecipe("FociUnequalTrade", new ItemStack(Init.FocusUnequalTrade), new AspectList().add(Aspect.ORDER, 20).add(Aspect.EARTH, 10).add(Aspect.ENTROPY, 15).add(Aspect.AIR, 20), 
			 new Object[] { "ABA", "BCB", "ABA",
					 Character.valueOf('B'), new ItemStack(ConfigItems.itemShard, 1, 3),
					 Character.valueOf('A'), new ItemStack(ConfigItems.itemShard, 1, 6),
					 Character.valueOf('C'), new ItemStack(ConfigItems.itemFocusTrade)});
	
	new ResearchItem("FociUnequalTrade", "RANDOMMAGICS", new AspectList().add(Aspect.MAGIC, 15).add(Aspect.EXCHANGE, 15).add(Aspect.VOID, 15), 7, 1, 3, new ItemStack(Init.FocusUnequalTrade))
	.setPages(new ResearchPage[] {new ResearchPage("research.FociUnequalTrade.text"), new ResearchPage(fociunequaltrade)})
	.setParents(new String[] {"Starts"}).setConcealed()
	.registerResearchItem();
	
	AuraInfusionRecipe deathsentence = AuraInfusionHelper.addAuraInfusionrecipe("deathSentence", 20, 5, new ItemStack(Init.ItemDeathSentence), new ItemStack(Init.ItemSwordOfInfiniteDestruction),
			new ItemStack(Init.ItemRMResource, 1, 22),
			new ItemStack(Init.ItemRMResource, 1, 20),
			new ItemStack(Init.ItemRMResource, 1, 21),
			new ItemStack(Init.ItemRMResource, 1, 23),
			new ItemStack(Init.ItemRMResource, 1, 24));
	
	new ResearchItem("deathSentence", "RANDOMMAGICS", new AspectList().add(Aspect.WEAPON, 32).add(Aspect.DEATH, 20), -13, 3, 1, new ItemStack(Init.ItemDeathSentence))
	.setPages(new ResearchPage[] {new ResearchPage("research.deathSentence.text"), new AuraInfusionResearchPage(deathsentence)})
	.setParents(new String[] {"slates"}).setConcealed()
	.registerResearchItem();
	
	new ResearchItem("demonDevelopment", "RANDOMMAGICS", new AspectList().add(Aspect.MAN, 32).add(Aspect.GREED, 20), -7, -2, 1, new ItemStack(Init.ItemDemonHeart))
	.setPages(new ResearchPage[] {new ResearchPage("research.demonDevelopment.text")})
	.setSecondary()
	.setItemTriggers(new ItemStack(Init.ItemDemonHeart))
	.setHidden()
	//.setConcealed()
	.registerResearchItem();
	
	ShapedArcaneRecipe clawsOfExile = ThaumcraftApi.addArcaneCraftingRecipe("clawsOfExile", new ItemStack(Init.ItemClawsOfExile), new AspectList().add(Aspect.ORDER, 20).add(Aspect.FIRE, 10).add(Aspect.ENTROPY, 15), 
			 new Object[] { "ABA", "ABA", "CCC",
					 Character.valueOf('B'), new ItemStack(ConfigItems.itemShard, 1, 5),
					 Character.valueOf('A'), new ItemStack(ConfigItems.itemShard, 1, 6),
					 Character.valueOf('C'), new ItemStack(Init.ItemRMResource, 1, 26)});
	
	new ResearchItem("clawsOfExile", "RANDOMMAGICS", new AspectList().add(Aspect.WEAPON, 10).add(Aspect.MAN, 10), -9, -2, 1, new ItemStack(Init.ItemClawsOfExile))
	.setPages(new ResearchPage[] {new ResearchPage("research.clawsOfExile.text"), new ResearchPage(clawsOfExile)})
	.setParents(new String[] {"demonDevelopment"}).setConcealed()
	.registerResearchItem();
	
	}
	
}