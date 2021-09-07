package randommagics.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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

public class FocusStealing extends ItemFocusBasic {
	
	public FocusStealing() {
		setCreativeTab(Init.TabRandomMagics);
		setUnlocalizedName("fociStealing");
	}
	
	public IIcon iconOrnament; //�������� ������ ������������

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
	this.icon = ir.registerIcon("randommagics:stealingF"); //�������� ������������
	this.iconOrnament = ir.registerIcon("randommagics:stealingOrn"); //�������� ���������
	this.depthIcon = ir.registerIcon("randommagics:stealingIn"); //�������� ������ ������������
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
		return new AspectList().add(Aspect.ORDER, 500).add(Aspect.AIR, 500); //���� ������� ������� ��� 100 �. �. 100 � ���� = 1 � ����
	}
	
	public int getActivationCooldown(ItemStack focusstack) {
		return 3000; //�������� ���������, 1000 - ��� 1 �������
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
	   return "Stealing"; //��� ������������ ������ ����, ���� �� ������� - �� ����� ������������ � ���� �������������!
	}
	
	public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer player, MovingObjectPosition mop) {//��������� ������ �������
		
		ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();
        EntityStealingOrb shard = new EntityStealingOrb(world, player);
        if (wand.consumeAllVis(itemstack, player, getVisCost(itemstack), true, false))
        {
        	if (!world.isRemote) {
        		world.spawnEntityInWorld(shard);
            	world.playSoundAtEntity(shard, "thaumcraft:ice", 0.3F, 0.8F + world.rand.nextFloat() * 0.1F);
        	}
        }
        player.swingItem();
        return itemstack;
	 }

}
