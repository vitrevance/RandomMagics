package randommagics.dimensions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class CustomTeleporter extends Teleporter {
    public boolean makePortal(Entity p_85188_1_)
    {
        return true;
    }
    @Override
    public void placeInPortal(Entity p_77185_1_, double p_77185_2_, double p_77185_4_, double p_77185_6_, float p_77185_8_)
    {
        int i = MathHelper.floor_double(p_77185_1_.posX);
        int j = MathHelper.floor_double(p_77185_1_.posY) - 1;
        int k = MathHelper.floor_double(p_77185_1_.posZ);
        p_77185_1_.setLocationAndAngles((double)i, (double)j, (double)k, p_77185_1_.rotationYaw, 0.0F);
        p_77185_1_.motionX = p_77185_1_.motionY = p_77185_1_.motionZ = 0.0D;
    }

    public CustomTeleporter(WorldServer world, double x, double y, double z) {
        super(world);
        this.worldServer = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private final WorldServer worldServer;
    private double x;
    private double y;
    private double z;


    public static void teleportToDimension(EntityPlayer player, int dimension, double x, double y, double z) {
        int oldDimension = player.worldObj.provider.dimensionId;
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
        MinecraftServer server = MinecraftServer.getServer();
        WorldServer worldServer = server.worldServerForDimension(dimension);
        //player.addExperienceLevel(0);

        if (worldServer == null){ //Dimension doesn't exist
            throw new IllegalArgumentException("Dimension: "+dimension+" doesn't exist!");
        }
        
        for (; !worldServer.isAirBlock((int) x, (int) y, (int) z); y++)
        {
        }
        server.getConfigurationManager().transferPlayerToDimension(entityPlayerMP, dimension, new CustomTeleporter(worldServer, x, y, z));
        player.setPositionAndUpdate(x, y, z);
        if (oldDimension == 1) {
            // For some reason teleporting out of the end does weird things. Compensate for that
            player.setPositionAndUpdate(x, y, z);
            worldServer.spawnEntityInWorld(player);
            worldServer.updateEntityWithOptionalForce(player, false);
        }
    }

}
