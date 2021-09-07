package randommagics.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import randommagics.Init;
import randommagics.entities.EntityStealingOrb;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigEntities;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.tiles.TileJar;
import thaumcraft.common.tiles.TileJarNode;

public class FocusAuraBottler extends ItemFocusBasic{
	
	public Object node;
	
	public FocusAuraBottler() {
		this.setCreativeTab(Init.TabRandomMagics);
		this.setUnlocalizedName("fociaurabottler");
	}
	
	public IIcon iconOrnament; //�������� ������ ������������

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
	this.icon = ir.registerIcon("randommagics:aurabottlerF"); //�������� ������������
	this.iconOrnament = ir.registerIcon("randommagics:aurabottlerOrn"); //�������� ���������
	this.depthIcon = ir.registerIcon("randommagics:aurabottlerIn"); //�������� ������ ������������
	}
	
	public IIcon depthIcon = null; //�������� ������ ������������
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int par1, int renderPass)
	{
		return renderPass == 1 ? this.icon : this.iconOrnament; //��������� ������� ������� ��������� ����� �� �����, ������������� �� ����, ����� ��� ���������, �  ��� ����� ������
	}
	
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true; //��������� ���������� ��� �������, ����� ��� ���������, � ��� ����� ������
	}
	
	public IIcon getOrnament(ItemStack itemstack)
	{
		return this.iconOrnament; //��������� �������� ���������, ���� �� ����� - ��������
	}
	
	public IIcon getFocusDepthLayerIcon(ItemStack itemstack)
	{
		return this.depthIcon; //��������� ��������� �������� (��� � �������� ���������, ����������� � � �), �� ����� - ��������
	}
	
	public int getFocusColor(ItemStack focusstack) {//�����������! ��� ���� ������� �������� ������������, ������ �������, ������������ ����� ����� �������� ����� RGB
		int a=100; //������������, ���������� ������
		int r=230; //������� ������ RGB
		int g=255; //������� ������ RGB
		int b=251; //����� ������ RGB
		return  a << 24 | r << 16 | g << 8 | b << 0;
	}
	
	public AspectList getVisCost(ItemStack focusstack) {//����� ������� ����� �����������
		return new AspectList().add(Aspect.ORDER, 10000).add(Aspect.AIR, 10000).add(Aspect.ENTROPY, 10000); //���� ������� ������� ��� 100 �. �. 100 � ���� = 1 � ����
	}
	
	public int getActivationCooldown(ItemStack focusstack) {
		return 10000; //�������� ���������, 1000 - ��� 1 �������
	}
	
	public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack itemstack, int rank) //�������� ������������
	{
		switch (rank)
		{
			case 1: 
				return new FocusUpgradeType[] { FocusUpgradeType.frugal /* ��� �� ���������� ������*/};
			case 2: 
				return new FocusUpgradeType[] { FocusUpgradeType.frugal};
			case 3: 
				return new FocusUpgradeType[] { FocusUpgradeType.frugal};
			case 4: 
				return new FocusUpgradeType[] { FocusUpgradeType.frugal};
			case 5: 
				return new FocusUpgradeType[] { FocusUpgradeType.frugal};
		}
		return null;
	 }
	
	public String getSortingHelper(ItemStack itemstack) {
	   return "Aura Bottler"; //��� ������������ ������ ����, ���� �� ������� - �� ����� ������������ � ���� �������������!
	}
	
	public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer player, MovingObjectPosition mop) {//��������� ������ �������
		
		ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();
        EntityStealingOrb shard = new EntityStealingOrb(world, player);
        if(!world.isRemote && mop != null && world.getBlock(mop.blockX, mop.blockY + 1, mop.blockZ) == ConfigBlocks.blockAiry && wand.consumeAllVis(itemstack, player, getVisCost(itemstack), true, false))
        {

        	int x = mop.blockX;
            int y = mop.blockY + 1;
            int z = mop.blockZ;
            INode tile = (INode)world.getTileEntity(x, y, z);
            TileJarNode node = new TileJarNode();
            node.setAspects(tile.getAspects());
            node.setNodeType(tile.getNodeType());
            node.setNodeModifier(tile.getNodeModifier());
            world.setBlock(x, y, z, ConfigBlocks.blockJar, 2, 3);
            world.setTileEntity(x, y, z, node);
        }
        player.swingItem();
        return itemstack;
	 }

}
