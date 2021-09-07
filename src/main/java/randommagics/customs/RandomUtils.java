package randommagics.customs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ForgeEventFactory;
import scala.collection.parallel.ParIterableLike.Min;
import thaumcraft.common.entities.EntityFollowingItem;
import thaumcraft.common.lib.utils.EntityUtils;
import thaumcraft.common.lib.utils.Utils;

public class RandomUtils {
	
	public static EntityPlayer getServerEntityPlayerByName(String name) {
		EntityPlayer player = null;
		for (int i = 0; i < MinecraftServer.getServer().worldServers.length && player == null; i++) {
			player = MinecraftServer.getServer().worldServers[i].getPlayerEntityByName(name);
		}
		return player;
	}
	
	public static EntityPlayer getEntityPlayerByName(String name, boolean remote) {
		EntityPlayer player = null;
		if (remote) {
			player = Minecraft.getMinecraft().theWorld.getPlayerEntityByName(name);
		}
		else {
			for (int i = 0; i < MinecraftServer.getServer().worldServers.length && player == null; i++) {
				player = MinecraftServer.getServer().worldServers[i].getPlayerEntityByName(name);
			}
		}
		return player;
	}
	
	public static List<EntityPlayer> getServerPlayers() {
		List<EntityPlayer> ret = new ArrayList<EntityPlayer>();
		for (int i = 0; i < MinecraftServer.getServer().worldServers.length; i++) {
			ret.addAll(MinecraftServer.getServer().worldServers[i].playerEntities);
		}
		return ret;
	}
	
	public static int getWorldTopBlock(World world, int x, int z) {
		int y = 255;
		for (; world.getBlock(x, y, z).getMaterial() == Material.air && y > 0; y--);
		return y;
	}
	
	public static void LookAt(double px, double py, double pz , EntityLivingBase me) {
	    double dirx = me.getPosition(0f).xCoord - px;
	    double diry = me.getPosition(0f).yCoord - py;
	    double dirz = me.getPosition(0f).zCoord - pz;

	    double len = Math.sqrt(dirx*dirx + diry*diry + dirz*dirz);

	    dirx /= len;
	    diry /= len;
	    dirz /= len;

	    double pitch = Math.asin(diry);
	    double yaw = Math.atan2(dirz, dirx);

	    //to degree
	    pitch = pitch * 180.0 / Math.PI;
	    yaw = yaw * 180.0 / Math.PI;

	    yaw += 90f;
	    me.rotationPitch = (float)pitch;
	    me.rotationYaw = (float)yaw;
	}
	
	public static boolean harvestBlock(World world, EntityPlayer player, int x, int y, int z)
    {
        return harvestBlock(world, player, x, y, z, false, 0);
    }

    public static boolean harvestBlock(World world, EntityPlayer player, int x, int y, int z, boolean followItem, int color)
    {
        Block block = world.getBlock(x, y, z);
        int i1 = world.getBlockMetadata(x, y, z);
        if(block.getBlockHardness(world, x, y, z) < 0.0F)
            return false;
        world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (i1 << 12));
        boolean flag = false;
        if(player.capabilities.isCreativeMode)
        {
            flag = removeBlock(world, x, y, z, player);
        } else
        {
            boolean flag1 = false;
            if(block != null)
                flag1 = block.canHarvestBlock(player, i1);
            flag = removeBlock(world, x, y, z, player);
            if(flag && flag1)
            {
                block.harvestBlock(world, player, x, y, z, i1);
                if(followItem)
                {
                    ArrayList entities = EntityUtils.getEntitiesInRange(world, (double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, player, EntityItem.class, 2D);
                    if(entities != null && entities.size() > 0)
                    {
                        Iterator i$ = entities.iterator();
                        do
                        {
                            if(!i$.hasNext())
                                break;
                            Entity e = (Entity)i$.next();
                            if(!e.isDead && (e instanceof EntityItem) && e.ticksExisted == 0 && !(e instanceof EntityFollowingItem))
                            {
                                EntityFollowingItem fi = new EntityFollowingItem(world, e.posX, e.posY, e.posZ, ((EntityItem)e).getEntityItem().copy(), player, color);
                                fi.motionX = e.motionX;
                                fi.motionY = e.motionY;
                                fi.motionZ = e.motionZ;
                                world.spawnEntityInWorld(fi);
                                e.setDead();
                            }
                        } while(true);
                    }
                }
            }
        }
        return true;
    }
    
    public static ArrayList<ItemStack> harvestBlockVirtual(World world, EntityPlayer player, int x, int y, int z) {
    	ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
    	Block block = world.getBlock(x, y, z);
        int i1 = world.getBlockMetadata(x, y, z);
        if(block.getBlockHardness(world, x, y, z) < 0.0F)
            return ret;
        world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (i1 << 12));
        boolean flag = false;
        if(player.capabilities.isCreativeMode)
        {
            flag = removeBlock(world, x, y, z, player);
        } else
        {
            boolean flag1 = false;
            if(block != null)
                flag1 = block.canHarvestBlock(player, i1);
            flag = removeBlock(world, x, y, z, player);
            if(flag && flag1)
            {
                //block.harvestBlock(world, player, x, y, z, i1);
            	removeBlock(world, x, y, z, player);
                
                
                player.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(block)], 1);
                player.addExhaustion(0.025F);

                if (block.canSilkHarvest(world, player, x, y, z, i1) && EnchantmentHelper.getSilkTouchModifier(player))
                {
                    ArrayList<ItemStack> items = new ArrayList<ItemStack>();
                    ItemStack itemstack;// = block.createStackedBlock(i1);
                    int j = 0;
                    Item item = Item.getItemFromBlock(block);

                    if (item != null && item.getHasSubtypes())
                    {
                        j = i1;
                    }

                    itemstack = new ItemStack(item, 1, j);

                    if (itemstack != null)
                    {
                        items.add(itemstack);
                    }

                    ForgeEventFactory.fireBlockHarvesting(items, world, block, x, y, z, i1, 0, 1.0f, true, player);
                    ret.addAll(items);
                }
                else
                {
                	int fortune = EnchantmentHelper.getFortuneModifier(player);
                	if (!world.isRemote && !world.restoringBlockSnapshots) // do not drop items while restoring blockstates, prevents item dupe
                    {
                        ArrayList<ItemStack> items = block.getDrops(world, x, y, z, i1, fortune);
                        float chance = ForgeEventFactory.fireBlockHarvesting(items, world, block, x, y, z, i1, fortune, 1.0F, false, player);

                        for (ItemStack item : items)
                        {
                            if (world.rand.nextFloat() <= chance)
                            {
                                ret.add(item);
                            }
                        }
                    }
                	/*
                    harvesters.set(player);
                    
                    block.dropBlockAsItem(world, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_, i1);
                    harvesters.set(null);
                    */
                }
                /*
                ArrayList entities = EntityUtils.getEntitiesInRange(world, (double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, player, EntityItem.class, 2D);
                if(entities != null && entities.size() > 0)
                {
                    Iterator i$ = entities.iterator();
                    do
                    {
                        if(!i$.hasNext())
                            break;
                        Entity e = (Entity)i$.next();
                        if(!e.isDead && (e instanceof EntityItem) && e.ticksExisted == 0 && !(e instanceof EntityFollowingItem))
                        {
                            //EntityFollowingItem fi = new EntityFollowingItem(world, e.posX, e.posY, e.posZ, ((EntityItem)e).getEntityItem().copy(), player, color);
                            //fi.motionX = e.motionX;
                            //fi.motionY = e.motionY;
                            //fi.motionZ = e.motionZ;
                            //world.spawnEntityInWorld(fi);
                        	ret.add(((EntityItem)e).getEntityItem().copy());
                            e.setDead();
                        }
                    } while(true);
                }
                */
            }
        }
        return ret;
    }
    
    public static void destroyBlockPartially(World world, int par1, int par2, int par3, int par4, int par5)
    {
        Iterator iterator = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();
            if(entityplayermp != null && entityplayermp.worldObj == MinecraftServer.getServer().getEntityWorld() && entityplayermp.getEntityId() != par1)
            {
                double d0 = (double)par2 - entityplayermp.posX;
                double d1 = (double)par3 - entityplayermp.posY;
                double d2 = (double)par4 - entityplayermp.posZ;
                if(d0 * d0 + d1 * d1 + d2 * d2 < 1024D)
                    entityplayermp.playerNetServerHandler.sendPacket(new S25PacketBlockBreakAnim(par1, par2, par3, par4, par5));
            }
        } while(true);
    }

    public static boolean removeBlock(World world, int par1, int par2, int par3, EntityPlayer player)
    {
        Block block = world.getBlock(par1, par2, par3);
        int l = world.getBlockMetadata(par1, par2, par3);
        if(block != null)
            block.onBlockHarvested(world, par1, par2, par3, l, player);
        boolean flag = block != null && block.removedByPlayer(world, player, par1, par2, par3, true);
        if(block != null && flag)
            block.onBlockDestroyedByPlayer(world, par1, par2, par3, l);
        return flag;
    }

    public static void findBlocks(World world, int x, int y, int z, Block block)
    {
        int count = 0;
        for(int xx = -2; xx <= 2; xx++)
        {
            for(int yy = 2; yy >= -2; yy--)
            {
                for(int zz = -2; zz <= 2; zz++)
                {
                    if(Math.abs((lastx + xx) - x) > 24)
                        return;
                    if(Math.abs((lasty + yy) - y) > 48)
                        return;
                    if(Math.abs((lastz + zz) - z) > 24)
                        return;
                    if(world.getBlock(lastx + xx, lasty + yy, lastz + zz) != block || !Utils.isWoodLog(world, lastx + xx, lasty + yy, lastz + zz) || block.getBlockHardness(world, lastx + xx, lasty + yy, lastz + zz) < 0.0F)
                        continue;
                    double xd = (lastx + xx) - x;
                    double yd = (lasty + yy) - y;
                    double zd = (lastz + zz) - z;
                    double d = xd * xd + yd * yd + zd * zd;
                    if(d > lastdistance)
                    {
                        lastdistance = d;
                        lastx += xx;
                        lasty += yy;
                        lastz += zz;
                        findBlocks(world, x, y, z, block);
                        return;
                    }
                }

            }

        }

    }

    public static boolean breakFurthestBlock(World world, int x, int y, int z, Block block, EntityPlayer player)
    {
        return breakFurthestBlock(world, x, y, z, block, player, false, 0);
    }

    public static boolean breakFurthestBlock(World world, int x, int y, int z, Block block, EntityPlayer player, boolean followitem, int color)
    {
        lastx = x;
        lasty = y;
        lastz = z;
        lastdistance = 0.0D;
        findBlocks(world, x, y, z, block);
        boolean worked = harvestBlock(world, player, lastx, lasty, lastz, followitem, color);
        world.markBlockForUpdate(x, y, z);
        if(worked)
        {
            world.markBlockForUpdate(lastx, lasty, lastz);
            for(int xx = -3; xx <= 3; xx++)
            {
                for(int yy = -3; yy <= 3; yy++)
                {
                    for(int zz = -3; zz <= 3; zz++)
                        world.scheduleBlockUpdate(lastx + xx, lasty + yy, lastz + zz, world.getBlock(lastx + xx, lasty + yy, lastz + zz), 150 + world.rand.nextInt(150));

                }

            }

        }
        return worked;
    }

    public static MovingObjectPosition getTargetBlock(World world, double x, double y, double z, float yaw, 
            float pitch, boolean par3, double range)
    {
        Vec3 var13 = Vec3.createVectorHelper(x, y, z);
        float var14 = MathHelper.cos(-yaw * 0.01745329F - 3.141593F);
        float var15 = MathHelper.sin(-yaw * 0.01745329F - 3.141593F);
        float var16 = -MathHelper.cos(-pitch * 0.01745329F);
        float var17 = MathHelper.sin(-pitch * 0.01745329F);
        float var18 = var15 * var16;
        float var20 = var14 * var16;
        double var21 = range;
        Vec3 var23 = var13.addVector((double)var18 * var21, (double)var17 * var21, (double)var20 * var21);
        return world.func_147447_a(var13, var23, par3, !par3, false);
    }

    public static MovingObjectPosition getTargetBlock(World world, Entity entity, boolean par3)
    {
        float var4 = 1.0F;
        float var5 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * var4;
        float var6 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * var4;
        double var7 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)var4;
        double var9 = (entity.prevPosY + (entity.posY - entity.prevPosY) * (double)var4 + 1.6200000000000001D) - (double)entity.yOffset;
        double var11 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)var4;
        Vec3 var13 = Vec3.createVectorHelper(var7, var9, var11);
        float var14 = MathHelper.cos(-var6 * 0.01745329F - 3.141593F);
        float var15 = MathHelper.sin(-var6 * 0.01745329F - 3.141593F);
        float var16 = -MathHelper.cos(-var5 * 0.01745329F);
        float var17 = MathHelper.sin(-var5 * 0.01745329F);
        float var18 = var15 * var16;
        float var20 = var14 * var16;
        double var21 = 10D;
        Vec3 var23 = var13.addVector((double)var18 * var21, (double)var17 * var21, (double)var20 * var21);
        return world.func_147447_a(var13, var23, par3, !par3, false);
    }

    public static boolean isBlockAdjacentToAtleast(IBlockAccess world, int x, int y, int z, Block id, int md, int amount)
    {
        return isBlockAdjacentToAtleast(world, x, y, z, id, md, amount, 1);
    }

    public static boolean isBlockAdjacentToAtleast(IBlockAccess world, int x, int y, int z, Block id, int md, int amount, int range)
    {
        int count = 0;
        for(int xx = -range; xx <= range; xx++)
        {
            for(int yy = -range; yy <= range; yy++)
            {
                for(int zz = -range; zz <= range; zz++)
                {
                    if(xx == 0 && yy == 0 && zz == 0)
                        continue;
                    if(world.getBlock(x + xx, y + yy, z + zz) == id && (md == 32767 || world.getBlockMetadata(x + xx, y + yy, z + zz) == md))
                        count++;
                    if(count >= amount)
                        return true;
                }

            }

        }

        return count >= amount;
    }

    public static List getContentsOfBlock(World world, int x, int y, int z)
    {
        List list = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x, y, z, (double)x + 1.0D, (double)y + 1.0D, (double)z + 1.0D));
        return list;
    }

    public static int countExposedSides(World world, int x, int y, int z)
    {
        int count = 0;
        ForgeDirection arr$[] = ForgeDirection.VALID_DIRECTIONS;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            ForgeDirection dir = arr$[i$];
            if(world.isAirBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ))
                count++;
        }

        return count;
    }

    public static boolean isBlockExposed(World world, int x, int y, int z)
    {
        return !world.getBlock(x, y, z + 1).isOpaqueCube() || !world.getBlock(x, y, z - 1).isOpaqueCube() || !world.getBlock(x + 1, y, z).isOpaqueCube() || !world.getBlock(x - 1, y, z).isOpaqueCube() || !world.getBlock(x, y + 1, z).isOpaqueCube() || !world.getBlock(x, y - 1, z).isOpaqueCube();
    }

    public static boolean isAdjacentToSolidBlock(World world, int x, int y, int z)
    {
        for(int a = 0; a < 6; a++)
        {
            ForgeDirection d = ForgeDirection.getOrientation(a);
            if(world.isSideSolid(x + d.offsetX, y + d.offsetY, z + d.offsetZ, d.getOpposite()))
                return true;
        }

        return false;
    }

    public static boolean isBlockTouching(IBlockAccess world, int x, int y, int z, Block id)
    {
        return world.getBlock(x, y, z + 1) == id || world.getBlock(x, y, z - 1) == id || world.getBlock(x + 1, y, z) == id || world.getBlock(x - 1, y, z) == id || world.getBlock(x, y + 1, z) == id || world.getBlock(x, y - 1, z) == id;
    }

    public static boolean isBlockTouching(IBlockAccess world, int x, int y, int z, Block id, int md)
    {
        return world.getBlock(x, y, z + 1) == id && world.getBlockMetadata(x, y, z + 1) == md || world.getBlock(x, y, z - 1) == id && world.getBlockMetadata(x, y, z - 1) == md || world.getBlock(x + 1, y, z) == id && world.getBlockMetadata(x + 1, y, z) == md || world.getBlock(x - 1, y, z) == id && world.getBlockMetadata(x - 1, y, z) == md || world.getBlock(x, y + 1, z) == id && world.getBlockMetadata(x, y + 1, z) == md || world.getBlock(x, y - 1, z) == id && world.getBlockMetadata(x, y - 1, z) == md;
    }

    public static boolean isBlockTouchingOnSide(IBlockAccess world, int x, int y, int z, Block id, int md, int side)
    {
        if(side > 3 && world.getBlock(x, y, z + 1) == id && world.getBlockMetadata(x, y, z + 1) == md || side > 3 && world.getBlock(x, y, z - 1) == id && world.getBlockMetadata(x, y, z - 1) == md || side > 1 && side < 4 && world.getBlock(x + 1, y, z) == id && world.getBlockMetadata(x + 1, y, z) == md || side > 1 && side < 4 && world.getBlock(x - 1, y, z) == id && world.getBlockMetadata(x - 1, y, z) == md || side > 1 && world.getBlock(x, y + 1, z) == id && world.getBlockMetadata(x, y + 1, z) == md || side > 1 && world.getBlock(x, y - 1, z) == id && world.getBlockMetadata(x, y - 1, z) == md)
            return true;
        if(side > 3 && world.getBlock(x, y + 1, z + 1) == id && world.getBlockMetadata(x, y + 1, z + 1) == md || side > 3 && world.getBlock(x, y + 1, z - 1) == id && world.getBlockMetadata(x, y + 1, z - 1) == md || side > 1 && side < 4 && world.getBlock(x + 1, y + 1, z) == id && world.getBlockMetadata(x + 1, y + 1, z) == md || side > 1 && side < 4 && world.getBlock(x - 1, y + 1, z) == id && world.getBlockMetadata(x - 1, y + 1, z) == md)
            return true;
        if(side > 3 && world.getBlock(x, y - 1, z + 1) == id && world.getBlockMetadata(x, y - 1, z + 1) == md || side > 3 && world.getBlock(x, y - 1, z - 1) == id && world.getBlockMetadata(x, y - 1, z - 1) == md || side > 1 && side < 4 && world.getBlock(x + 1, y - 1, z) == id && world.getBlockMetadata(x + 1, y - 1, z) == md || side > 1 && side < 4 && world.getBlock(x - 1, y - 1, z) == id && world.getBlockMetadata(x - 1, y - 1, z) == md)
            return true;
        switch(side)
        {
        default:
            break;

        case 0: // '\0'
            if(world.getBlock(x, y - 1, z) == id && world.getBlockMetadata(x, y - 1, z) == md)
                return true;
            break;

        case 1: // '\001'
            if(world.getBlock(x, y + 1, z) == id && world.getBlockMetadata(x, y + 1, z) == md)
                return true;
            break;
        }
        return false;
    }
    
    public static void spawnEntityAt(World world, Entity e, float x, float y, float z) {
    	if (world.isRemote)
    		return;
    	e.setPosition(x, y, z);
    	world.spawnEntityInWorld(e);
    }
    
    public static float lerp(float a, float b, float delta) {
    	return a + delta * (b - a);
    }
    
    public static void spawnItemInWorld(World world, ItemStack stack, double x, double y, double z) {
    	world.spawnEntityInWorld(new EntityItem(world, x, y, z, stack));
    }
    
    public static void spawnItemInWorldAt(World world, ItemStack stack, Entity e) {
    	world.spawnEntityInWorld(new EntityItem(world, e.posX, e.posY, e.posZ, stack));
    }
    
    public static boolean isUnderOpenSky(World world, int x, int y, int z) {
    	for (int yy = y; yy < 256; yy++) {
    		if (world.getBlock(x, yy, z) != Blocks.air) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public static ArrayList<String> getAllPlayersNames() {
    	ArrayList<String> names = new ArrayList<String>();
    	EntityPlayer player = null;
		for (int i = 0; i < MinecraftServer.getServer().worldServers.length && player == null; i++) {
			for (EntityPlayerMP pl : (List<EntityPlayerMP>)MinecraftServer.getServer().worldServers[i].playerEntities) {
				names.add(pl.getCommandSenderName());
			}
		}
		return names;
    }
    
    /**
     * Take a remainder
     * @param a
     * @param b
     * @return a % b, but for double. Formula = (a - (((int)(a / b)) * b));
     */
    public static double remainder(double a, double b) {
    	return a - (((int)(a / b)) * b);
    }
    
    public static float clampf(float x, float a, float b) {
    	return Math.max(a, Math.min(x, b));
    }
    
    public static String translateCurse(String id) {
    	return StatCollector.translateToLocal("curse." + id);
    }
    
    public static String readFile(String path){
        try {
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("randommagics", path)).getInputStream(), "UTF-8"));
            String str;
            while ((str = reader.readLine()) != null)
                builder.append(str).append("\n");

            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    static HashMap blockEventCache = new HashMap();
    static int lastx = 0;
    static int lasty = 0;
    static int lastz = 0;
    static double lastdistance = 0.0D;
}
