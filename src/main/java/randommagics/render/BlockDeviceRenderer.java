package randommagics.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.handler.traffic.GlobalTrafficShapingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.shader.TesselatorVertexState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraftforge.common.util.RotationHelper;
import randommagics.Init;
import randommagics.blocks.BlockCloud;
import randommagics.blocks.BlockDevice;
import randommagics.blocks.BlockItemDuplicator;
import randommagics.blocks.BlockLifeEssenceJar;
import randommagics.blocks.BlockLifeStealingPlant;
import randommagics.blocks.BlockRitualPylon;
import randommagics.blocks.TileAlchemyPlant;
import randommagics.blocks.TileAuraInfusionMatrix;
import randommagics.blocks.TileEssenceContainer;
import randommagics.blocks.TileInfusionMatrixExtended;
import randommagics.blocks.TileInventoryAccess;
import randommagics.blocks.TileItemDuplicator;
import randommagics.blocks.TileLifeEssenceJar;
import randommagics.blocks.TileLifeStealingPlant;
import randommagics.blocks.TileRitualPylon;
import randommagics.blocks.TileTimeExpander;
import randommagics.blocks.TileVisCell;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.NodeModifier;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.block.BlockRenderer;
import thaumcraft.client.renderers.tile.TileNodeRenderer;
import thaumcraft.codechicken.lib.vec.Rotation;
import thaumcraft.common.blocks.BlockJar;
import thaumcraft.common.config.ConfigBlocks;
@SideOnly(Side.CLIENT)
public class BlockDeviceRenderer extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {
	
	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("randommagics", "textures/models/TileInfusionMatrixExtended.obj")); //���� � ������.
	public static final IModelCustom modelTileVisCell = AdvancedModelLoader.loadModel(new ResourceLocation("randommagics", "textures/models/TileVisCell.obj"));
	public static final IModelCustom modelSimpleBlock = AdvancedModelLoader.loadModel(new ResourceLocation("randommagics", "textures/models/SimpleBlock.obj"));
	public static final IModelCustom modelTileAlchemyPlant = AdvancedModelLoader.loadModel(new ResourceLocation("randommagics", "textures/models/TileAlchemyPlant.obj"));
	public static final IModelCustom modelTileTimeExpander = AdvancedModelLoader.loadModel(new ResourceLocation("randommagics", "textures/models/TileTimeExpander.obj"));
	public static final IModelCustom modelTileLifeEssenceJar = AdvancedModelLoader.loadModel(new ResourceLocation("randommagics", "textures/models/TileLifeEssenceJar.obj"));
	public static final IModelCustom modelTileAuraInfusionMatrix = AdvancedModelLoader.loadModel(new ResourceLocation("randommagics", "textures/models/TileAuraInfusionMatrix.obj"));
	//public static final IModelCustom modelTileRitualPylon = AdvancedModelLoader.loadModel(new ResourceLocation("randommagics", "textures/models/TileRitualPylon.obj"));
	//public static final IModelCustom modelTileExplosion = AdvancedModelLoader.loadModel(new ResourceLocation("randommagics", "textures/models/TileExpulosion.obj"));
	/*
	public BlockDeviceRenderer() {
		
	}*/

	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		if (block instanceof BlockDevice) {
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			if (metadata == 0) {
				//GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
				TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileInfusionMatrixExtended(), 0.0D, 0.0D, 0.0D, 0.0F);
			} else if (metadata == 1 ) {
				//GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
				TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileVisCell(), 0.0D, 0.0D, 0.0D, 0.0F);
			} else if (metadata == 2) {
				//GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
				TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileInventoryAccess(), 0.0D, 0.0D, 0.0D, 0.0F);
			} else if (metadata == 3) {
				TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileAlchemyPlant(), 0.0D, 0.0D, 0.0D, 0.0F);
			} else if (metadata == 4) {
				TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileTimeExpander(), 0.0D, 0.0D, 0.0D, 0.0F);
			} else if (metadata == 5) {
				TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileAuraInfusionMatrix(), 0.0D, 0.0D, 0.0D, 0.0F);
			}
		}
		if (block instanceof BlockLifeStealingPlant) {
			TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileLifeStealingPlant(), 0.0D, 0.0D, 0.0D, 0.0F);
		}
		if (block instanceof BlockItemDuplicator) {
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileItemDuplicator(), 0.0D, 0.0D, 0.0D, 0.0F);
		}
		if (block instanceof BlockRitualPylon) {
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileRitualPylon(), 0.0D, 0.0D, 0.0D, 0.0F);
		}
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        return true;
	}
	
	public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }
	
	public int getRenderId()
    {
        return Init.RenderID;
    }

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z,
			float partialTicks) {
		if (tile instanceof TileInfusionMatrixExtended) {
			TileInfusionMatrixExtended te  = (TileInfusionMatrixExtended)tile;
			GL11.glPushMatrix();
    		GL11.glTranslated(x + 0.5, y + 0.5 + te.renderPos, z + 0.5);
    		GL11.glRotatef(te.renderAngl, 0.F, 1.F, 0.F);
    		
    		if (te.crafting)
    			bindTexture(new ResourceLocation("randommagics", "textures/models/TileInfusionMatrixExtendedG.png"));
    		else
    			bindTexture(new ResourceLocation("randommagics", "textures/models/TileInfusionMatrixExtended.png")); //�����������
    		//model.renderAll();
    		model.renderPart("_2");
    		GL11.glPopMatrix();
    		if (te.active) {
    			GL11.glPushMatrix();
    			GL11.glTranslated(x + 0.5, y + 0.5 + te.renderPos, z + 0.5);
    			GL11.glRotatef(-te.renderAngl, 0.F, 1.F, 0.F);
    			model.renderPart("_1");
    			GL11.glPopMatrix();
    		}
		}
		if (tile instanceof TileVisCell) {
			TileVisCell te = (TileVisCell)tile;
			GL11.glPushMatrix();
    		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
    		bindTexture(new ResourceLocation("randommagics", "textures/models/TileVisCell.png"));
    		modelTileVisCell.renderPart("_2");
    		GL11.glRotatef(te.renderAngl, 0.F, 1.F, 0.F);
    		modelTileVisCell.renderPart("_1");
    		GL11.glPopMatrix();
		}
		if (tile instanceof TileInventoryAccess) {
			GL11.glPushMatrix();
    		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
    		bindTexture(new ResourceLocation("randommagics", "textures/models/TileInventoryAccess.png"));
    		modelSimpleBlock.renderPart("_1");
    		GL11.glPopMatrix();
		}
		if (tile instanceof TileAlchemyPlant) {
			TileAlchemyPlant te = (TileAlchemyPlant)tile;
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);
    		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
    		GL11.glRotatef(te.side * 90, 0.F, 1.F, 0.F);
    		bindTexture(new ResourceLocation("randommagics", "textures/models/TileAlchemyPlant.png"));
    		modelTileAlchemyPlant.renderAll();
    		GL11.glEnable(GL11.GL_CULL_FACE);
    		GL11.glPopMatrix();
		}
		if (tile instanceof TileTimeExpander) {
			TileTimeExpander te = (TileTimeExpander)tile;
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);
    		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
    		bindTexture(new ResourceLocation("randommagics", "textures/models/TileTimeExpander.png"));
    		modelTileTimeExpander.renderAll();
    		GL11.glEnable(GL11.GL_CULL_FACE);
    		GL11.glPopMatrix();
    		if (te.isActive)
    			TileNodeRenderer.renderNode(Minecraft.getMinecraft().renderViewEntity, 100, true, false, 1.6F, tile.xCoord, tile.yCoord, tile.zCoord, partialTicks, new AspectList().add(Aspect.MAGIC, 30), NodeType.TAINTED, NodeModifier.BRIGHT);
		}
		if (tile instanceof TileLifeEssenceJar) {
			TileLifeEssenceJar te = (TileLifeEssenceJar)tile;
			float scl = (float)te.getEssence() / (float)te.getCapacity();
			bindTexture(new ResourceLocation("thaumcraft", "textures/blocks/amberblock.png"));
			if (scl > 0) {
				GL11.glPushMatrix();
				
				GL11.glTranslated(x + 0.5, y + scl / 2f, z + 0.5);
				GL11.glScalef(1f, scl, 1f);
				//GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glColor4f(1f, 0.1f, 0.1f, 1f);
				modelTileLifeEssenceJar.renderPart("_3");
				//GL11.glEnable(GL11.GL_CULL_FACE);
				
				GL11.glPopMatrix();
			}
			
			GL11.glPushMatrix();
			
			
			GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
			
			
			bindTexture(new ResourceLocation("randommagics", "textures/blocks/TileLifeEssenceJar_side.png"));
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1f, 1f, 1f, 0.8f);
			modelTileLifeEssenceJar.renderPart("_1");
			GL11.glDisable(GL11.GL_BLEND);
			
			bindTexture(new ResourceLocation("randommagics", "textures/blocks/TileLifeEssenceJar_top.png"));
			modelTileLifeEssenceJar.renderPart("_2");
			
			GL11.glPopMatrix();
			
			
		}
		if (tile instanceof TileAuraInfusionMatrix) {
			TileAuraInfusionMatrix te  = (TileAuraInfusionMatrix)tile;
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
    		//GL11.glTranslated(x + 0.5, y + 0.5 + te.renderPos, z + 0.5);
    		//GL11.glRotatef(te.renderAngl, 0.F, 1.F, 0.F);
			GL11.glRotatef(te.rotx, 1, 0, 0);
			GL11.glRotatef(te.roty, 0, 1, 0);
			GL11.glRotatef(te.rotz, 0, 0, 1);
			
			if (te.crafting)
				bindTexture(new ResourceLocation("randommagics", "textures/models/TileAuraInfusionMatrixG.png"));
			else
				bindTexture(new ResourceLocation("randommagics", "textures/models/TileAuraInfusionMatrix.png"));
    		//model.renderAll();
    		modelTileAuraInfusionMatrix.renderAll();
    		GL11.glPopMatrix();
		}
		if (tile instanceof TileItemDuplicator) {
			TileItemDuplicator te = (TileItemDuplicator)tile;
			
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
			bindTexture(new ResourceLocation("randommagics", "textures/models/TileItemDuplicator.png"));
    		modelSimpleBlock.renderAll();
    		GL11.glPopMatrix();
    		
    		float ticks = (float)Minecraft.getMinecraft().renderViewEntity.ticksExisted + partialTicks;
    		GL11.glPushMatrix();
    		GL11.glTranslated(x + 0.5, y + 1.15, z + 0.5);
    		if (te.slots[0] != null) {
    			RenderUtils.renderItemStack(te.slots[0], partialTicks);
    		}
    		GL11.glPopMatrix();
		}
		if (tile instanceof TileRitualPylon) {
			TileRitualPylon te = (TileRitualPylon)tile;
			
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y + 1.0, z + 0.5);
			GL11.glRotatef(te.rotation, 0, 1, 0);
			GL11.glScalef(2.1f, 2.8f, 2.1f);
			bindTexture(new ResourceLocation("randommagics", "textures/blocks/RitualPylon.png"));
    		modelTileVisCell.renderPart("_1");
    		GL11.glPopMatrix();
		}
	}

}
