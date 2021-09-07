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
import randommagics.blocks.BlockMindWarped;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;

public class FocusMindMutilation extends ItemFocusBasic /* ������������ �� Thaumcraft Focus */{
public FocusMindMutilation() {
	setCreativeTab(Init.TabRandomMagics); //��������� � �������
	setUnlocalizedName("fociMindMut");
}

public IIcon iconOrnament; //�������� ������ ������������

@SideOnly(Side.CLIENT)
public void registerIcons(IIconRegister ir) {
this.icon = ir.registerIcon("randommagics:mindmutF"); //�������� ������������
this.iconOrnament = ir.registerIcon("randommagics:mindmutOrn"); //�������� ���������
this.depthIcon = ir.registerIcon("randommagics:mindmutIn"); //�������� ������ ������������
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
public int getFocusColor(ItemStack focusstack) //�����������! ��� ���� ������� �������� ������������, ������ �������, ������������ ����� ����� �������� ����� RGB
 {
int a=255; //������������, ���������� ������
int r=80; //������� ������ RGB
int g=0; //������� ������ RGB
int b=115; //����� ������ RGB
   return  a << 24 | r << 16 | g << 8 | b << 0;
 }
public AspectList getVisCost(ItemStack focusstack) //����� ������� ����� �����������
 {
return new AspectList().add(Aspect.ORDER, 10000).add(Aspect.ENTROPY, 20000); //���� ������� ������� ��� 100, �� ���� � ���� ������������ �� ��������� 20 ����, 20 ����, 1 ��������, ����� ���� ������� ����� � ����� thaumcraft/api/aspects/Aspect.java ��� ����� ctrl+f ������� �������� ������� �� ���� � �������� �������� ������ ����, ��� �� ���������, ��� �������� Sano ������ ���� HEAL
 }
 
public int getActivationCooldown(ItemStack focusstack)
 {
   return 3000; //�������� ���������, 1000 - ��� 1 �������
 }

public ItemStack onFocusRightClick(ItemStack wandstack, World world, EntityPlayer player, MovingObjectPosition mop) //��������� ������ �������
 {
ItemWandCasting wand = (ItemWandCasting)wandstack.getItem();
if (player == null) {
     return wandstack; //�������� �� ������� �����, ������ ���������, ����� ��� � ���������� �������� �����, ������ �����������!
}
	if(!world.isRemote && mop != null && mop.typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.BLOCK) {
		if(world.getBlock(mop.blockX, mop.blockY, mop.blockZ) != null && world.getBlock(mop.blockX, mop.blockY, mop.blockZ) == Init.BlockMindWarped) {
			if (wand.consumeAllVis(wandstack, player, getVisCost(wandstack), true, false)) {
				world.setBlockToAir(mop.blockX, mop.blockY, mop.blockZ);
				player.entityDropItem(new ItemStack(Init.BlockMindWarped, 1, 0), 1);
			}
		}
   }
else return wandstack;
return wandstack;
 
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
public String getSortingHelper(ItemStack itemstack)
 {
   return "Mind Mutilation"; //��� ������������ ������ ����, ���� �� ������� - �� ����� ������������ � ���� �������������!
 }
}