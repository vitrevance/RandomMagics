package randommagics;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import randommagics.blocks.ItemLifeEssenceJar;
import randommagics.blocks.TileAlchemyPlant;
import randommagics.blocks.TileAuraInfusionMatrix;
import randommagics.blocks.TileCloud;
import randommagics.blocks.TileEssenceContainer;
import randommagics.blocks.TileInfusionMatrixExtended;
import randommagics.blocks.TileInventoryAccess;
import randommagics.blocks.TileItemDuplicator;
import randommagics.blocks.TileLifeEssenceJar;
import randommagics.blocks.TileLifeStealingPlant;
import randommagics.blocks.TileRitualPylon;
import randommagics.blocks.TileTimeExpander;
import randommagics.blocks.TileVisCell;
import randommagics.entities.EntityBigExplosion;
import randommagics.entities.EntityExpulosion;
import randommagics.entities.mobs.EntitySupremeDemon;
import randommagics.render.BlockDeviceRenderer;
import randommagics.render.EntitySpecialRenderer;
import randommagics.render.ItemDeathSentenceRenderer;
import randommagics.render.ItemSpecialRenderer;
import randommagics.entities.mobs.EntityDemon;

public class RenderInit {
	
	public static void init() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileInfusionMatrixExtended.class , new BlockDeviceRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileVisCell.class , new BlockDeviceRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileInventoryAccess.class , new BlockDeviceRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAlchemyPlant.class , new BlockDeviceRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTimeExpander.class , new BlockDeviceRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCloud.class, new BlockDeviceRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileLifeEssenceJar.class, new BlockDeviceRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAuraInfusionMatrix.class, new BlockDeviceRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileItemDuplicator.class, new BlockDeviceRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileRitualPylon.class, new BlockDeviceRenderer());
    	
        RenderingRegistry.registerBlockHandler(Init.RenderID = RenderingRegistry.getNextAvailableRenderId(), new BlockDeviceRenderer());
        
        RenderingRegistry.registerEntityRenderingHandler(EntityExpulosion.class, new EntitySpecialRenderer());
        RenderingRegistry.registerEntityRenderingHandler(EntitySupremeDemon.class, new EntitySpecialRenderer());
        RenderingRegistry.registerEntityRenderingHandler(EntityDemon.class, new EntitySpecialRenderer());
        RenderingRegistry.registerEntityRenderingHandler(EntityBigExplosion.class, new EntitySpecialRenderer());
        
        MinecraftForgeClient.registerItemRenderer(GameRegistry.findItem("randommagics", "LifeEssenceJar"), new ItemSpecialRenderer());
        MinecraftForgeClient.registerItemRenderer(Init.ItemDeathSentence, new ItemDeathSentenceRenderer());
	}
}