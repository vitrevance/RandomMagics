package randommagics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.util.concurrent.BlockingOperationException;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.IWandRodOnUpdate;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.blocks.BlockCandle;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.equipment.ItemElementalPickaxe;
import thaumcraft.common.items.wands.ItemWandCasting;
import randommagics.blocks.*;
import randommagics.curses.CurseRegistry;
import randommagics.customs.AuraInfusionHelper;
import randommagics.customs.StructureHelper;
import randommagics.dimensions.BiomeRegistry;
import randommagics.dimensions.DimensionRegistry;
import randommagics.dimensions.OreGenerator;
import randommagics.gui.GUIHandler;
import randommagics.gui.GuiDemonAbilityMenu;
import randommagics.gui.GuiMinigame;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import randommagics.KeyHandler;
import randommagics.items.*;
import randommagics.render.BlockDeviceRenderer;
import randommagics.render.ItemSpecialRenderer;
import randommagics.render.ModShaders;
import randommagics.rituals.EnchantmentRitualReciepe;
import randommagics.rituals.PowerRitualReciepe;
import randommagics.rituals.RitualHelper;
import randommagics.effects.*;
import randommagics.enchantments.EnchantmentDisintegration;

public class Init{
	
	public static ArmorMaterial ELDRITCH_ARMOR = EnumHelper.addArmorMaterial("randommagics:ELDRITCH_ARMOR", 250, new int[] {4, 8, 6, 4}, 30);
	public static ToolMaterial INFINITE_STONE = EnumHelper.addToolMaterial("randommagics:INFINITE_STONE", 3, 0, 100, 596, 30);
	public static Aspect diferium = new Aspect("diferium", 15101695, new Aspect[] {Aspect.AURA, Aspect.MAGIC}, new ResourceLocation("randommagics", "textures/gui/diferium.png"), 1);
	
	public static StructureHelper StructureSpiritRitual;
	
	public static BlockRitualStone RitualStone;
	public static BlockRitualStoneSpirit RitualStoneSpirit;
	public static BlockRitualStonePower RitualStonePower;
	public static Block BlockMindWarped;
	public static Block BlockStableEldPortal;
	public static Block BlockExpulosion;
	public static Block BlockCloud;
	public static Block BlockLifeStealingPlant;
	public static Block BlockStupidAnvil;
	public static Block BlockLifeEssenceExtractor;
	public static Block BlockLifeEssenceJar;
	public static Block BlockItemDuplicator;
	public static Block BlockRMOre;
	public static Block BlockRitualPylon;
	public static Block BlockDevice;
	
	public static Item EldHelmet;
	public static Item EldChestplate;
	public static Item EldLeggins;
	public static Item EldBoots;
	public static Item ItemWandRodPrimal;
	public static Item ItemBlkHoleCap;
	public static Item RitualCatalyst;
	public static ItemFocusBasic FocusMindMutilation;
	public static ItemFocusBasic FocusStealing;
	public static ItemFocusBasic FocusHailOfArrows;
	public static ItemFocusBasic FocusAuraBottler;
	public static ItemFocusBasic FocusInfinity;
	public static Item PrimBlend = new Item().setUnlocalizedName("PrimBlend").setTextureName("randommagics:PrimBlend");
	public static Item PrimIngot = new Item().setUnlocalizedName("PrimIngot").setTextureName("randommagics:PrimIngot");
	public static Item ItemXpHolder;
	public static Item ItemInfinityStone;
	public static Item ItemCloudCharm;
	public static Item ItemInfniteRunicShieldingRing;
	public static Item ItemRingOfSaturation;
	public static Item ItemDemonHeart;
	public static Item ItemPickaxeOfTheLordOfTheEarth;
	public static Item ItemSwordOfInfiniteDestruction;
	public static Item ItemLifeEssenceJar;
	public static Item ItemWarpCleaner;
	public static Item ItemRMResource;
	public static Item OverlordHelmet;
	public static Item OverlordChestplate;
	public static Item OverlordLeggins;
	public static Item OverlordBoots;
	public static Item ItemSoulBinder;
	public static Item ItemCursedScroll;
	public static Item ItemInfoPaper;
	public static Item ItemCondensedMatter;
	public static Item ItemDeathSentence;
	public static Item FocusUnequalTrade;
	public static Item ItemMagicianHat;
	public static Item ItemClawsOfExile;
	public static Item ItemSuniaster;
	
	public static Potion EffectStuckInTime;
	public static Potion EffectVoiceOfDeath;
	
	public static int RenderID;
	
	public static void init()
	{
		
		CurseRegistry.register();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GUIHandler());
		
		ItemWandRodPrimal = new ItemWandRodPrim();
        GameRegistry.registerItem(ItemWandRodPrimal, "ItemWandRodPrimal");
        WAND_ROD_PRIMAL = new WandRod("primordial", 10000, new ItemStack(ItemWandRodPrimal, 1, 0), 100, new WandRodPrimordialOnUpdate() , new ResourceLocation("randommagics", "textures/models/wand_rod_primordial.png"));
        ItemBlkHoleCap = new ItemBlkHoleCap();
        GameRegistry.registerItem(ItemBlkHoleCap, "ItemBlkHoleCap");
        WAND_CAP_BLKHOLE = new WandCap("blkhole", 0.001f, new ItemStack(ItemBlkHoleCap,1,0),9);
        WAND_CAP_BLKHOLE.setTexture(new ResourceLocation("randommagics:textures/models/wand_cap_blkhole.png"));
        RitualStone = new BlockRitualStone();
        GameRegistry.registerBlock(RitualStone, "ritualStone");
        RitualStoneSpirit = new BlockRitualStoneSpirit();
        GameRegistry.registerBlock(RitualStoneSpirit, "ritualStoneSpirit");
        RitualCatalyst = new Item().setUnlocalizedName("ritualCatalyst").setCreativeTab(TabRandomMagics).setTextureName("randommagics:ritualCatalyst");
        GameRegistry.registerItem(RitualCatalyst, "ritualCatalyst");
        RitualStonePower = new BlockRitualStonePower();
        GameRegistry.registerBlock(RitualStonePower, "ritualStonePower");
        GameRegistry.registerTileEntity(TileRitualStonePower.class, "TileRitualStonePower");
        BlockMindWarped = new BlockMindWarped();
        GameRegistry.registerBlock(BlockMindWarped, "BlockMindWarped");
        FocusMindMutilation = new randommagics.items.FocusMindMutilation();
        GameRegistry.registerItem(FocusMindMutilation, "fociMindMut");
        FocusStealing = new randommagics.items.FocusStealing();
        GameRegistry.registerItem(FocusStealing, "fociStealing");
        FocusHailOfArrows = new randommagics.items.FocusHailOfArrows();
        GameRegistry.registerItem(FocusHailOfArrows, "fociHailOfArrows");
        FocusAuraBottler = new randommagics.items.FocusAuraBottler();
        GameRegistry.registerItem(FocusAuraBottler, "fociAuraBottler");
        FocusInfinity = new randommagics.items.FocusInfinity();
        GameRegistry.registerItem(FocusInfinity, "fociInfinity");
        BlockStableEldPortal = new BlockStableEldPortal();
        GameRegistry.registerBlock(BlockStableEldPortal, "ritualStoneSpawn");
        ItemXpHolder = new ItemXpHolder();
        GameRegistry.registerItem(ItemXpHolder, "ItemXpHolder");
        ItemInfinityStone = new ItemInfinityStone();
        GameRegistry.registerItem(ItemInfinityStone, ItemInfinityStone.getUnlocalizedName());
        ItemCloudCharm = new ItemCloudCharm();
        GameRegistry.registerItem(ItemCloudCharm, "ItemCloudCharm");
        ItemInfniteRunicShieldingRing = new ItemInfniteRunicShieldingRing();
        GameRegistry.registerItem(ItemInfniteRunicShieldingRing, "ItemInfniteRunicShieldingRing");
        ItemRingOfSaturation = new randommagics.items.ItemRingOfSaturation();
        GameRegistry.registerItem(ItemRingOfSaturation, "ItemRingOfSaturation");
        ItemDemonHeart  = new ItemDemonHeart();
        GameRegistry.registerItem(ItemDemonHeart, "ItemDemonHeart");
        ItemPickaxeOfTheLordOfTheEarth = new ItemPickaxeOfTheLordOfTheEarth();
        GameRegistry.registerItem(ItemPickaxeOfTheLordOfTheEarth, "ItemPickaxeOfTheLordOfTheEarth");
        ItemSwordOfInfiniteDestruction = new ItemSwordOfInfiniteDestruction();
        GameRegistry.registerItem(ItemSwordOfInfiniteDestruction, "ItemSwordOfInfiniteDestruction");
        ItemWarpCleaner = new ItemWarpCleaner();
        GameRegistry.registerItem(ItemWarpCleaner, "ItemWarpCleaner");
        ItemRMResource = new ItemRMResource();
        GameRegistry.registerItem(ItemRMResource, "ItemRMResource");
        ItemSoulBinder = new ItemSoulBinder();
        GameRegistry.registerItem(ItemSoulBinder, "ItemSoulBinder");
        ItemCursedScroll = new ItemCursedScroll();
        GameRegistry.registerItem(ItemCursedScroll, "ItemCursedScroll");
        ItemInfoPaper = new ItemInfoPaper();
        GameRegistry.registerItem(ItemInfoPaper, "ItemInfoPaper");
        ItemCondensedMatter = new ItemCondensedMatter();
        GameRegistry.registerItem(ItemCondensedMatter, "ItemCondensedMatter");
        ItemDeathSentence = new ItemDeathSentence();
        GameRegistry.registerItem(ItemDeathSentence, "ItemDeathSentence");
        FocusUnequalTrade = new FocusUnequalTrade();
        GameRegistry.registerItem(FocusUnequalTrade, FocusUnequalTrade.getUnlocalizedName());
        ItemMagicianHat = new ItemMagicianHat();
        GameRegistry.registerItem(ItemMagicianHat, ItemMagicianHat.getUnlocalizedName());
        ItemClawsOfExile = new randommagics.items.ItemClawsOfExile();
        GameRegistry.registerItem(ItemClawsOfExile, ItemClawsOfExile.getUnlocalizedName());
        ItemSuniaster = new ItemSuniaster();
        GameRegistry.registerItem(ItemSuniaster, ItemSuniaster.getUnlocalizedName());
        
        BlockStupidAnvil = new BlockStupidAnvil();
        GameRegistry.registerBlock(BlockStupidAnvil, "BlockStupidAnvil");
        BlockLifeEssenceExtractor = new BlockLifeEssenceExtractor();
        GameRegistry.registerBlock(BlockLifeEssenceExtractor, "BlockLifeEssenceExtractor");
        GameRegistry.registerTileEntity(TileLifeEssenceExtractor.class, "TileLifeEssenceExtractor");
        GameRegistry.registerTileEntity(TileEssenceContainer.class, "TileEssenceContainer");
        BlockLifeEssenceJar = new BlockLifeEssenceJar();
        ItemLifeEssenceJar = new ItemLifeEssenceJar(BlockLifeEssenceJar);
        GameRegistry.registerBlock(BlockLifeEssenceJar, ItemLifeEssenceJar.class, "LifeEssenceJar");
        GameRegistry.registerTileEntity(TileLifeEssenceJar.class, "TileLifeEssenceJar");
        
        BlockCloud = new randommagics.blocks.BlockCloud();
        GameRegistry.registerBlock(BlockCloud, "Cloud");
        GameRegistry.registerTileEntity(TileCloud.class, "TileCloud");
        BlockLifeStealingPlant = new randommagics.blocks.BlockLifeStealingPlant();
        GameRegistry.registerBlock(BlockLifeStealingPlant, "LifeStealingPlant");
        GameRegistry.registerTileEntity(TileLifeStealingPlant.class, "TileLifeStealingPlant");
        BlockItemDuplicator =  new BlockItemDuplicator();
        GameRegistry.registerBlock(BlockItemDuplicator, "ItemDuplicator");
        GameRegistry.registerTileEntity(TileItemDuplicator.class, "TileItemDuplicator");
        BlockRMOre = new BlockRMOre();
        GameRegistry.registerBlock(BlockRMOre, ItemRMOre.class, "RMOre");
        BlockRitualPylon = new BlockRitualPylon();
        GameRegistry.registerTileEntity(TileRitualPylon.class, "TileRitualPylon");
        GameRegistry.registerBlock(BlockRitualPylon, "RitualPylon");
        
        GameRegistry.registerTileEntity(TileInfusionMatrixExtended.class, "TileInfusionMatrixExtended");
        GameRegistry.registerTileEntity(TileVisCell.class, "TileVisCell");
        GameRegistry.registerTileEntity(TileInventoryAccess.class, "TileInventoryAccess");
        GameRegistry.registerTileEntity(TileAlchemyPlant.class, "TileAlchemyPlant");
        GameRegistry.registerTileEntity(TileTimeExpander.class, "TileTimeExpander");
        GameRegistry.registerTileEntity(TileAuraInfusionMatrix.class, "TileAuraInfusionMatrix");
        GameRegistry.registerTileEntity(TileReactor.class, "TileReactor");
        BlockDevice = new BlockDevice();
        GameRegistry.registerBlock(BlockDevice, ItemDevice.class, "Device");
        
        
        GameRegistry.registerItem(EldHelmet = new ItemEldritchArmor("EldHelmet", ELDRITCH_ARMOR, "EldHelmet", 0), "EldHelmet");
        GameRegistry.registerItem(EldChestplate = new ItemEldritchArmor("EldChestplate", ELDRITCH_ARMOR, "EldChestplate", 1), "EldChestplate");
        GameRegistry.registerItem(EldLeggins = new ItemEldritchArmor("EldLeggins", ELDRITCH_ARMOR, "EldLeggins", 2), "EldLeggins");
        GameRegistry.registerItem(EldBoots = new ItemEldritchArmor("EldBoots", ELDRITCH_ARMOR, "EldBoots", 3), "EldBoots");
        GameRegistry.registerItem(OverlordHelmet = new ItemOverlordArmor("OverlordHelmet", ELDRITCH_ARMOR, "OverlordHelmet", 0), "OverlordHelmet");
        GameRegistry.registerItem(OverlordChestplate = new ItemOverlordArmor("OverlordChestplate", ELDRITCH_ARMOR, "OverlordChestplate", 1), "OverlordChestplate");
        GameRegistry.registerItem(OverlordLeggins = new ItemOverlordArmor("OverlordLeggins", ELDRITCH_ARMOR, "OverlordLeggins", 2), "OverlordLeggins");
        GameRegistry.registerItem(OverlordBoots = new ItemOverlordArmor("OverlordBoots", ELDRITCH_ARMOR, "OverlordBoots", 3), "OverlordBoots");
        
        
        GameRegistry.registerItem(PrimBlend.setCreativeTab(TabRandomMagics), "PrimBlend");
        GameRegistry.registerItem(PrimIngot.setCreativeTab(TabRandomMagics), "PrimIngot");
        
        EffectStuckInTime = new EffectStuckInTime(Config.potionStuckInTimeId, true, 0);
        EffectVoiceOfDeath = new EffectVoiceOfDeath(Config.potionVoiceOfDeathId);
        
        
        BiomeRegistry.mainRegistry();
        DimensionRegistry.mainRegistry();
        GameRegistry.registerWorldGenerator(new OreGenerator(), OreGenerator.ID);
        EventHandler eventHandler = new EventHandler();
        MinecraftForge.EVENT_BUS.register(eventHandler);
        FMLCommonHandler.instance().bus().register(eventHandler);
        MinecraftForge.EVENT_BUS.register(new EnchantmentEvents());
        
        RitualHelper.registerPowerReciepe(new PowerRitualReciepe(new ItemStack(Blocks.gold_block, 64, 0), 1000, new ItemStack(FocusInfinity, 1, 0)));
        
        Recipes.Init();
        
        if (FMLCommonHandler.instance().getSide().isClient()) {
        	FMLCommonHandler.instance().bus().register(new KeyHandler());
        	MinecraftForge.EVENT_BUS.register(new GuiDemonAbilityMenu());
        	GuiMinigame guiMinigame = new GuiMinigame();
        	MinecraftForge.EVENT_BUS.register(guiMinigame);
        	FMLCommonHandler.instance().bus().register(guiMinigame);
        	/*
        	BlockDeviceRenderer render1 = new BlockDeviceRenderer();
            
            ClientRegistry.bindTileEntitySpecialRenderer(TileInfusionMatrixExtended.class , render1);
            ClientRegistry.bindTileEntitySpecialRenderer(TileVisCell.class , render1);
            ClientRegistry.bindTileEntitySpecialRenderer(TileInventoryAccess.class , render1);
            ClientRegistry.bindTileEntitySpecialRenderer(TileAlchemyPlant.class , render1);
            ClientRegistry.bindTileEntitySpecialRenderer(TileExpulosion.class, render1);
            */
            
        	//FMLCommonHandler.instance().bus().register(new EventHandler());
        }
        
        StructureSpiritRitual = new StructureHelper("0/-5/0/0|0/-5/1/0|0/-5/2/0|1/-5/3/0|2/-5/4/0|0/-4/0/-4|0/-4/0/4|0/-4/1/-4|0/-4/1/4|0/-4/2/-4|0/-4/2/4|1/-4/3/-4|1/-4/3/4|2/-4/4/-4|2/-4/4/4|3/-2/-1/-1|3/-2/-1/0|3/-2/-1/1|3/-1/-1/-2|3/-1/-1/-1|3/-1/-1/0|3/-1/-1/1|3/-1/-1/2|3/0/-1/-2|3/0/-1/-1|3/0/-1/0|3/0/-1/1|3/0/-1/2|0/0/0/-5|4/0/0/0|0/0/0/5|0/0/1/-5|0/0/1/5|0/0/2/-5|0/0/2/5|1/0/3/-5|1/0/3/5|2/0/4/-5|2/0/4/5|3/1/-1/-2|3/1/-1/-1|3/1/-1/0|3/1/-1/1|3/1/-1/2|3/2/-1/-1|3/2/-1/0|3/2/-1/1|0/4/0/-4|0/4/0/4|0/4/1/-4|0/4/1/4|0/4/2/-4|0/4/2/4|1/4/3/-4|1/4/3/4|2/4/4/-4|2/4/4/4|0/5/0/0|0/5/1/0|0/5/2/0|1/5/3/0|2/5/4/0|")
    			.reg(ConfigBlocks.blockCosmeticSolid, 0)
    			.reg(Init.BlockRitualPylon, 0)
    			.reg(Init.BlockRitualPylon, 1)
    			.reg(Init.BlockMindWarped)
    			.reg(Init.RitualStoneSpirit);
        
        ((TabRandomMagics)TabRandomMagics).addStuff();
        ThaumcraftApi.registerObjectTag(new ItemStack(ItemDemonHeart), new AspectList().add(Aspect.FLESH, 10).add(Aspect.FIRE, 300).add(Aspect.TAINT, 256));
        
        //FMLCommonHandler.instance().bus().register(new EnchantmentHandler());
        
	}
	public static WandRod WAND_ROD_PRIMAL = new WandRod("primordial", 10000, new ItemStack(ItemWandRodPrimal, 1, 0), 100, new WandRodPrimordialOnUpdate() , new ResourceLocation("randommagics", "textures/models/wand_rod_primordial.png"));
	public static WandCap WAND_CAP_BLKHOLE = new WandCap("blkhole", 0.001f, new ItemStack(ItemBlkHoleCap,1,0),9);
	
	public static CreativeTabs TabRandomMagics = new TabRandomMagics(CreativeTabs.getNextID(), "RandomMagics");
	//public static CreativeTabs TabEssentials = new TabRandomMagics(CreativeTabs.getNextID(), "EssentialPlants");
}
