package randommagics.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import randommagics.Init;
import randommagics.entities.EntityStealingOrb;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.entities.projectile.EntityPrimalOrb;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.items.wands.WandManager;

public class FocusHailOfArrows extends ItemFocusBasic {
	
	public FocusHailOfArrows() {
		setCreativeTab(Init.TabRandomMagics);
		setUnlocalizedName("fociHailOfArrows");
	}
	
	public IIcon iconOrnament; //�������� ������ ������������

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
	this.icon = ir.registerIcon("randommagics:hailofarrowsF"); //�������� ������������
	this.iconOrnament = ir.registerIcon("randommagics:hailofarrowsOrn"); //�������� ���������
	this.depthIcon = ir.registerIcon("randommagics:hailofarrowsIn"); //�������� ������ ������������
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
		int g=80; //������� ������ RGB
		int b=100; //����� ������ RGB
		return  a << 24 | r << 16 | g << 8 | b << 0;
	}
	
	public AspectList getVisCost(ItemStack focusstack) {//����� ������� ����� �����������
		return new AspectList().add(Aspect.ENTROPY, 7).add(Aspect.AIR, 5); //���� ������� ������� ��� 100 �. �. 100 � ���� = 1 � ����
	}
	
	public int getActivationCooldown(ItemStack focusstack) {
		return 0; //�������� ���������, 1000 - ��� 1 �������
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
	
	public boolean isVisCostPerTick(ItemStack focusstack) {
		return true;
	}
	
	public String getSortingHelper(ItemStack itemstack) {
	   return "Hail Of Arrows"; //��� ������������ ������ ����, ���� �� ������� - �� ����� ������������ � ���� �������������!
	}
	
	public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer player, MovingObjectPosition mop) {//��������� ������ �������
		player.setItemInUse(itemstack, 0x7fffffff);
        WandManager.setCooldown(player, -1);
        return itemstack;
	 }
	public void onUsingFocusTick(ItemStack itemstack, EntityPlayer player,int count) {
		ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();
		World world = player.getEntityWorld();
		EntityArrow arrow = new EntityArrow(world, player, 3.5F);
		arrow.canBePickedUp = 0;
		arrow.noClip = true;
		if(!wand.consumeAllVis(itemstack, player, getVisCost(itemstack), false, false))
        {
            player.stopUsingItem();
            return;
        }
		
        if(!world.isRemote && wand.consumeAllVis(itemstack, player, getVisCost(itemstack), true, false))
        {
        	world.spawnEntityInWorld(arrow);
        	arrow.ticksExisted = 1000;
        }
	}
}
