package randommagics.blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import randommagics.customs.RandomUtils;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.crafting.IInfusionStabiliser;
import thaumcraft.api.crafting.InfusionEnchantmentRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.wands.IWandable;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.container.InventoryFake;
import thaumcraft.common.lib.crafting.InfusionRunicAugmentRecipe;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.lib.events.EssentiaHandler;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXBlockZap;
import thaumcraft.common.lib.network.fx.PacketFXInfusionSource;
import thaumcraft.common.lib.utils.InventoryUtils;
import thaumcraft.common.tiles.TileInfusionMatrix.SourceFX;
import thaumcraft.common.tiles.TileInfusionPillar;
import thaumcraft.common.tiles.TilePedestal;

public class TileInfusionMatrixExtended extends TileThaumcraft
    implements IWandable, IAspectContainer
{
	
	public int speedMod;
	public String playerPlacedName;

    public TileInfusionMatrixExtended()
    {
        pedestals = new ArrayList();
        dangerCount = 0;
        active = false;
        crafting = false;
        checkSurroundings = true;
        recipeEssentia = new AspectList();
        recipeIngredients = null;
        recipeOutput = null;
        recipePlayer = null;
        recipeOutputLabel = null;
        recipeInput = null;
        recipeXP = 0;
        recipeType = 0;
        sourceFX = new HashMap();
        count = 0;
        craftCount = 0;
        countDelay = 1;
        ingredients = new ArrayList();
        itemCount = 0;
        
        speedMod = 1;
        playerPlacedName = "";
    }

    public AxisAlignedBB getRenderBoundingBox()
    {
        return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 1, zCoord - 1, xCoord + 1, yCoord + 1, zCoord + 1);
    }

    public void readCustomNBT(NBTTagCompound nbtCompound)
    {
        active = nbtCompound.getBoolean("active");
        crafting = nbtCompound.getBoolean("crafting");
        recipeEssentia.readFromNBT(nbtCompound);
        
        speedMod = nbtCompound.getInteger("speedmod");
        playerPlacedName = nbtCompound.getString("playerPlacedName");
    }

    public void writeCustomNBT(NBTTagCompound nbtCompound)
    {
        nbtCompound.setBoolean("active", active);
        nbtCompound.setBoolean("crafting", crafting);
        recipeEssentia.writeToNBT(nbtCompound);
        
        nbtCompound.setInteger("speedmod", speedMod);
        nbtCompound.setString("playerPlacedName", playerPlacedName);
    }

    public void readFromNBT(NBTTagCompound nbtCompound)
    {
        super.readFromNBT(nbtCompound);
        NBTTagList nbttaglist = nbtCompound.getTagList("recipein", 10);
        recipeIngredients = new ArrayList();
        for(int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("item");
            recipeIngredients.add(ItemStack.loadItemStackFromNBT(nbttagcompound1));
        }

        String rot = nbtCompound.getString("rotype");
        if(rot != null && rot.equals("@"))
            recipeOutput = ItemStack.loadItemStackFromNBT(nbtCompound.getCompoundTag("recipeout"));
        else
        if(rot != null)
        {
            recipeOutputLabel = rot;
            recipeOutput = nbtCompound.getTag("recipeout");
        }
        recipeInput = ItemStack.loadItemStackFromNBT(nbtCompound.getCompoundTag("recipeinput"));
        recipeType = nbtCompound.getInteger("recipetype");
        recipeXP = nbtCompound.getInteger("recipexp");
        recipePlayer = nbtCompound.getString("recipeplayer");
        if(recipePlayer.isEmpty())
            recipePlayer = null;
    }

    public void writeToNBT(NBTTagCompound nbtCompound)
    {
        super.writeToNBT(nbtCompound);
        if(recipeIngredients != null && recipeIngredients.size() > 0)
        {
            NBTTagList nbttaglist = new NBTTagList();
            int count = 0;
            Iterator i$ = recipeIngredients.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                ItemStack stack = (ItemStack)i$.next();
                if(stack != null)
                {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("item", (byte)count);
                    stack.writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                    count++;
                }
            } while(true);
            nbtCompound.setTag("recipein", nbttaglist);
        }
        if(recipeOutput != null && (recipeOutput instanceof ItemStack))
            nbtCompound.setString("rotype", "@");
        if(recipeOutput != null && (recipeOutput instanceof NBTBase))
            nbtCompound.setString("rotype", recipeOutputLabel);
        if(recipeOutput != null && (recipeOutput instanceof ItemStack))
            nbtCompound.setTag("recipeout", ((ItemStack)recipeOutput).writeToNBT(new NBTTagCompound()));
        if(recipeOutput != null && (recipeOutput instanceof NBTBase))
            nbtCompound.setTag("recipeout", (NBTBase)recipeOutput);
        if(recipeInput != null)
            nbtCompound.setTag("recipeinput", recipeInput.writeToNBT(new NBTTagCompound()));
        nbtCompound.setInteger("recipetype", recipeType);
        nbtCompound.setInteger("recipexp", recipeXP);
        if(recipePlayer == null)
            nbtCompound.setString("recipeplayer", "");
        else
            nbtCompound.setString("recipeplayer", recipePlayer);
    }

    public boolean canUpdate()
    {
        return true;
    }

    public void updateEntity()
    {
        super.updateEntity();
        count++;
        if(checkSurroundings)
        {
            checkSurroundings = false;
            getSurroundings();
        }
        if(worldObj.isRemote)
        {
            doEffects();
            if (active) {
    			renderAngl += 0.4;
    			if (renderAngl >= 360.F)
    				renderAngl -= 360F;
    			if (renderPos > 0.1F || renderPos <= -0.1F)
    				posAdd *= -1;
    			renderPos += posAdd;
    		}
        } else
        {
            if(count % (crafting ? 20 : 100) == 0 && !validLocation())
            {
                active = false;
                markDirty();
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                return;
            }
            if(active && crafting && count % countDelay == 0)
            {
            	
            	craftCycle();
                markDirty();
            }
        }
    }
    
    private int getCraftCycles() {
    	return (1 + this.speedMod) * this.speedMod / 2;
    }

    public boolean validLocation()
    {
        TileEntity te = null;
        te = worldObj.getTileEntity(xCoord, yCoord - 2, zCoord);
        if(te == null || !(te instanceof TilePedestal))
            return false;
        te = worldObj.getTileEntity(xCoord + 1, yCoord - 2, zCoord + 1);
        if(te == null || !(te instanceof TileInfusionPillar))
            return false;
        te = worldObj.getTileEntity(xCoord + 1, yCoord - 2, zCoord - 1);
        if(te == null || !(te instanceof TileInfusionPillar))
            return false;
        te = worldObj.getTileEntity(xCoord - 1, yCoord - 2, zCoord - 1);
        if(te == null || !(te instanceof TileInfusionPillar))
            return false;
        te = worldObj.getTileEntity(xCoord - 1, yCoord - 2, zCoord + 1);
        return te != null && (te instanceof TileInfusionPillar);
    }

    public void craftingStart(EntityPlayer player)
    {
        if(!validLocation())
        {
            active = false;
            markDirty();
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            return;
        }
        getSurroundings();
        TileEntity te = null;
        recipeInput = null;
        te = worldObj.getTileEntity(xCoord, yCoord - 2, zCoord);
        if(te != null && (te instanceof TilePedestal))
        {
            TilePedestal ped = (TilePedestal)te;
            if(ped.getStackInSlot(0) != null)
                recipeInput = ped.getStackInSlot(0).copy();
        }
        if(recipeInput == null)
            return;
        ArrayList components = new ArrayList();
        Iterator i$ = pedestals.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            ChunkCoordinates cc = (ChunkCoordinates)i$.next();
            te = worldObj.getTileEntity(cc.posX, cc.posY, cc.posZ);
            if(te != null && (te instanceof TilePedestal))
            {
                TilePedestal ped = (TilePedestal)te;
                if(ped.getStackInSlot(0) != null)
                    components.add(ped.getStackInSlot(0).copy());
            }
        } while(true);
        if(components.size() == 0)
            return;
        InfusionRecipe recipe = ThaumcraftCraftingManager.findMatchingInfusionRecipe(components, recipeInput, player);
        if(recipe != null)
        {
            recipeType = 0;
            recipeIngredients = new ArrayList();
            if(recipe instanceof InfusionRunicAugmentRecipe)
            {
                ItemStack arr$[] = ((InfusionRunicAugmentRecipe)recipe).getComponents(recipeInput);
                int len$ = arr$.length;
                for(int i$1 = 0; i$1 < len$; i$1++)
                {
                    ItemStack ing = arr$[i$1];
                    recipeIngredients.add(ing.copy());
                }

            } else
            {
                ItemStack arr$[] = recipe.getComponents();
                int len$ = arr$.length;
                for(int i$1 = 0; i$1 < len$; i$1++)
                {
                    ItemStack ing = arr$[i$1];
                    recipeIngredients.add(ing.copy());
                }

            }
            if(recipe.getRecipeOutput(recipeInput) instanceof Object[])
            {
                Object obj[] = (Object[])(Object[])recipe.getRecipeOutput(recipeInput);
                recipeOutputLabel = (String)obj[0];
                recipeOutput = (NBTBase)obj[1];
            } else
            {
                recipeOutput = recipe.getRecipeOutput(recipeInput);
            }
            recipeEssentia = recipe.getAspects(recipeInput).copy();
            recipePlayer = player.getCommandSenderName();
            crafting = true;
            worldObj.playSoundEffect(xCoord, yCoord, zCoord, "thaumcraft:craftstart", 0.5F, 1.0F);
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            markDirty();
            return;
        }
        InfusionEnchantmentRecipe recipe2 = ThaumcraftCraftingManager.findMatchingInfusionEnchantmentRecipe(components, recipeInput, player);
        if(recipe2 != null)
        {
            recipeType = 1;
            recipeIngredients = new ArrayList();
            ItemStack arr$[] = recipe2.components;
            int len$ = arr$.length;
            for(int i$1 = 0; i$1 < len$; i$1++)
            {
                ItemStack ing = arr$[i$1];
                recipeIngredients.add(ing.copy());
            }

            recipeOutput = recipe2.getEnchantment();
            //recipeInstability = recipe2.calcInstability(recipeInput);
            AspectList esscost = recipe2.aspects.copy();
            float essmod = recipe2.getEssentiaMod(recipeInput);
            Aspect arr$1[] = esscost.getAspects();
            int len$1 = arr$1.length;
            for(int i$1 = 0; i$1 < len$1; i$1++)
            {
                Aspect as = arr$1[i$1];
                esscost.add(as, (int)((float)esscost.getAmount(as) * essmod));
            }

            recipeEssentia = esscost;
            recipeXP = recipe2.calcXP(recipeInput);
            //instability = symmetry + recipeInstability;
            crafting = true;
            worldObj.playSoundEffect(xCoord, yCoord, zCoord, "thaumcraft:craftstart", 0.5F, 1.0F);
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            markDirty();
            return;
        } else
        {
            return;
        }
    }

    public void craftCycle()
    {
        boolean valid = false;
        TileEntity te = worldObj.getTileEntity(xCoord, yCoord - 2, zCoord);
        if(te != null && (te instanceof TilePedestal))
        {
            TilePedestal ped = (TilePedestal)te;
            if(ped.getStackInSlot(0) != null)
            {
                ItemStack i2 = ped.getStackInSlot(0).copy();
                if(recipeInput.getItemDamage() == 32767)
                    i2.setItemDamage(32767);
                if(InventoryUtils.areItemStacksEqualForCrafting(i2, recipeInput, true, true, false))
                    valid = true;
            }
        }
        
        if(!valid)
        {
            crafting = false;
            recipeEssentia = new AspectList();
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            worldObj.playSoundEffect(xCoord, yCoord, zCoord, "thaumcraft:craftfail", 1.0F, 0.6F);
            markDirty();
            return;
        }
        if(recipeType == 1 && recipeXP > 0)
        {
            List targets = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(10D, 10D, 10D));
            if(targets != null && targets.size() > 0)
            {
                for(Iterator i$ = targets.iterator(); i$.hasNext();)
                {
                    EntityPlayer target = (EntityPlayer)i$.next();
                    if(target.experienceLevel > 0)
                    {
                        target.addExperienceLevel(-1);
                        recipeXP--;
                        target.attackEntityFrom(DamageSource.magic, worldObj.rand.nextInt(2));
                        PacketHandler.INSTANCE.sendToAllAround(new PacketFXInfusionSource(xCoord, yCoord, zCoord, (byte)0, (byte)0, (byte)0, target.getEntityId()), new cpw.mods.fml.common.network.NetworkRegistry.TargetPoint(getWorldObj().provider.dimensionId, xCoord, yCoord, zCoord, 32D));
                        worldObj.playSoundAtEntity(target, "random.fizz", 1.0F, 2.0F + worldObj.rand.nextFloat() * 0.4F);
                        countDelay = 2;
                        return;
                    }
                }

                Aspect ingEss[] = recipeEssentia.getAspects();
                if(ingEss != null && ingEss.length > 0 && worldObj.rand.nextInt(3) == 0)
                {
                    Aspect as = ingEss[worldObj.rand.nextInt(ingEss.length)];
                    recipeEssentia.add(as, 10);
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                    markDirty();
                }
            }
            return;
        }
        if(recipeType == 1 && recipeXP == 0)
            countDelay = 1;
        
        if(recipeEssentia.visSize() > 0)
        {
        	for (int i = 0; i < getCraftCycles() && recipeEssentia.visSize() > 0; i++) {
	            Aspect arr$[] = recipeEssentia.getAspects();
	            int len$ = arr$.length;
	            for(int i$ = 0; i$ < len$; i$++)
	            {
	                Aspect aspect = arr$[i$];
	                if(recipeEssentia.getAmount(aspect) <= 0)
	                    continue;
	                if(EssentiaHandler.drainEssentia(this, aspect, ForgeDirection.UNKNOWN, 12))
	                {
	                    recipeEssentia.reduce(aspect, 1);
	                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	                    markDirty();
	                    break;
	                }
	                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	                markDirty();
	            }
        	}
            checkSurroundings = true;
            return;
        }
        if(recipeIngredients.size() > 0)
        {
            for(int a = 0; a < recipeIngredients.size(); a++)
            {
                for(Iterator i$ = pedestals.iterator(); i$.hasNext();)
                {
                    ChunkCoordinates cc = (ChunkCoordinates)i$.next();
                    te = worldObj.getTileEntity(cc.posX, cc.posY, cc.posZ);
                    if(te != null && (te instanceof TilePedestal) && ((TilePedestal)te).getStackInSlot(0) != null && InfusionRecipe.areItemStacksEqual(((TilePedestal)te).getStackInSlot(0), (ItemStack)recipeIngredients.get(a), true))
                    {
                        if(itemCount == 0)
                        {
                            itemCount = 5;
                            PacketHandler.INSTANCE.sendToAllAround(new PacketFXInfusionSource(xCoord, yCoord, zCoord, (byte)(xCoord - cc.posX), (byte)(yCoord - cc.posY), (byte)(zCoord - cc.posZ), 0), new cpw.mods.fml.common.network.NetworkRegistry.TargetPoint(getWorldObj().provider.dimensionId, xCoord, yCoord, zCoord, 32D));
                        } else
                        if(itemCount-- <= 1)
                        {
                            ItemStack is = ((TilePedestal)te).getStackInSlot(0).getItem().getContainerItem(((TilePedestal)te).getStackInSlot(0));
                            ((TilePedestal)te).setInventorySlotContents(0, is != null ? is.copy() : null);
                            recipeIngredients.remove(a);
                        }
                        return;
                    }
                }

                Aspect ingEss[] = recipeEssentia.getAspects();
                if(ingEss == null || ingEss.length <= 0 || worldObj.rand.nextInt(1 + a) != 0)
                    continue;
                Aspect as = ingEss[worldObj.rand.nextInt(ingEss.length)];
                recipeEssentia.add(as, 5);
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                markDirty();
            }

            return;
        } else
        {
            crafting = false;
            craftingFinish(recipeOutput, recipeOutputLabel);
            recipeOutput = null;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            markDirty();
            return;
        }
    }

    private void inEvEjectItem(int type)
    {
        for(int q = 0; q < 50 && pedestals.size() > 0; q++)
        {
            ChunkCoordinates cc = (ChunkCoordinates)pedestals.get(worldObj.rand.nextInt(pedestals.size()));
            TileEntity te = worldObj.getTileEntity(cc.posX, cc.posY, cc.posZ);
            if(te != null && (te instanceof TilePedestal) && ((TilePedestal)te).getStackInSlot(0) != null)
            {
                if(type < 3 || type == 5)
                    InventoryUtils.dropItems(worldObj, cc.posX, cc.posY, cc.posZ);
                else
                    ((TilePedestal)te).setInventorySlotContents(0, null);
                if(type == 1 || type == 3)
                {
                    worldObj.setBlock(cc.posX, cc.posY + 1, cc.posZ, ConfigBlocks.blockFluxGoo, 7, 3);
                    worldObj.playSoundEffect(cc.posX, cc.posY, cc.posZ, "game.neutral.swim", 0.3F, 1.0F);
                } else
                if(type == 2 || type == 4)
                {
                    worldObj.setBlock(cc.posX, cc.posY + 1, cc.posZ, ConfigBlocks.blockFluxGas, 7, 3);
                    worldObj.playSoundEffect(cc.posX, cc.posY, cc.posZ, "random.fizz", 0.3F, 1.0F);
                } else
                if(type == 5)
                    worldObj.createExplosion(null, (float)cc.posX + 0.5F, (float)cc.posY + 0.5F, (float)cc.posZ + 0.5F, 1.0F, false);
                worldObj.addBlockEvent(cc.posX, cc.posY, cc.posZ, ConfigBlocks.blockStoneDevice, 11, 0);
                PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockZap((float)xCoord + 0.5F, (float)yCoord + 0.5F, (float)zCoord + 0.5F, (float)cc.posX + 0.5F, (float)cc.posY + 1.5F, (float)cc.posZ + 0.5F), new cpw.mods.fml.common.network.NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32D));
                return;
            }
        }

    }

    public void craftingFinish(Object out, String label)
    {
        TileEntity te = worldObj.getTileEntity(xCoord, yCoord - 2, zCoord);
        if(te != null && (te instanceof TilePedestal))
        {
            if(out instanceof ItemStack)
                ((TilePedestal)te).setInventorySlotContentsFromInfusion(0, ((ItemStack)out).copy());
            else
            if(out instanceof NBTBase)
            {
                ItemStack temp = ((TilePedestal)te).getStackInSlot(0);
                NBTBase tag = (NBTBase)out;
                temp.setTagInfo(label, tag);
                worldObj.markBlockForUpdate(xCoord, yCoord - 2, zCoord);
                te.markDirty();
            } else
            if(out instanceof Enchantment)
            {
                ItemStack temp = ((TilePedestal)te).getStackInSlot(0);
                Map enchantments = EnchantmentHelper.getEnchantments(temp);
                enchantments.put(Integer.valueOf(((Enchantment)out).effectId), Integer.valueOf(EnchantmentHelper.getEnchantmentLevel(((Enchantment)out).effectId, temp) + 1));
                EnchantmentHelper.setEnchantments(enchantments, temp);
                worldObj.markBlockForUpdate(xCoord, yCoord - 2, zCoord);
                te.markDirty();
            }
            if(recipePlayer != null)
            {
                EntityPlayer p = worldObj.getPlayerEntityByName(recipePlayer);
                if(p != null)
                    FMLCommonHandler.instance().firePlayerCraftingEvent(p, ((TilePedestal)te).getStackInSlot(0), new InventoryFake(recipeIngredients));
            }
            recipeEssentia = new AspectList();
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            markDirty();
            worldObj.addBlockEvent(xCoord, yCoord - 2, zCoord, ConfigBlocks.blockStoneDevice, 12, 0);
        }
    }

    private void getSurroundings()
    {
        ArrayList stuff = new ArrayList();
        pedestals.clear();
        try
        {
            for(int xx = -12; xx <= 12; xx++)
            {
                for(int zz = -12; zz <= 12; zz++)
                {
                    boolean skip = false;
                    for(int yy = -5; yy <= 10; yy++)
                    {
                        if(xx == 0 && zz == 0)
                            continue;
                        int x = xCoord + xx;
                        int y = yCoord - yy;
                        int z = zCoord + zz;
                        TileEntity te = worldObj.getTileEntity(x, y, z);
                        if(!skip && yy > 0 && Math.abs(xx) <= 8 && Math.abs(zz) <= 8 && te != null && (te instanceof TilePedestal))
                        {
                            pedestals.add(new ChunkCoordinates(x, y, z));
                            skip = true;
                            continue;
                        }
                    }

                }
            }
        }
        catch(Exception e) { }
    }

    public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, 
            int md)
    {
        if(!world.isRemote && active && !crafting)
        {
            craftingStart(player);
            return 0;
        }
        if(!world.isRemote && !active && validLocation())
        {
            active = true;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            markDirty();
            return 0;
        } else
        {
            return -1;
        }
    }
    
    public void onRedstoneSignal(World world, int x, int y, int z, boolean signal) {
    	if (!world.isRemote && active && !crafting && speedMod >= 10 && signal) {
    		if (playerPlacedName != null && playerPlacedName.length() > 0) {
    			EntityPlayer player = RandomUtils.getEntityPlayerByName(playerPlacedName, world.isRemote);
    			if (player != null) {
    				craftingStart(player);
    			}
    		}
    	}
    }

    public ItemStack onWandRightClick(World world, ItemStack wandstack, EntityPlayer player)
    {
    	player.swingItem();
        return wandstack;
    }

    public void onUsingWandTick(ItemStack itemstack, EntityPlayer entityplayer, int i)
    {
    }

    public void onWandStoppedUsing(ItemStack itemstack, World world1, EntityPlayer entityplayer, int i)
    {
    }

    private void doEffects()
    {
        if(crafting)
        {
            if(craftCount == 0)
                worldObj.playSound(xCoord, yCoord, zCoord, "thaumcraft:infuserstart", 0.5F, 1.0F, false);
            else
            if(craftCount % 65 == 0)
                worldObj.playSound(xCoord, yCoord, zCoord, "thaumcraft:infuser", 0.5F, 1.0F, false);
            craftCount++;
            Thaumcraft.proxy.blockRunes(worldObj, xCoord, yCoord - 2, zCoord, 0.5F + worldObj.rand.nextFloat() * 0.2F, 0.1F, 0.7F + worldObj.rand.nextFloat() * 0.3F, 25, -0.03F);
        } else
        if(craftCount > 0)
        {
            craftCount -= 2;
            if(craftCount < 0)
                craftCount = 0;
            if(craftCount > 50)
                craftCount = 50;
        }
        if(active && startUp != 1.0F)
        {
            if(startUp < 1.0F)
                startUp = startUp + Math.max(startUp / 10F, 0.001F);
            if((double)startUp > 0.999D)
                startUp = 1.0F;
        }
        if(!active && startUp > 0.0F)
        {
            if(startUp > 0.0F)
                startUp = startUp - startUp / 10F;
            if((double)startUp < 0.001D)
                startUp = 0.0F;
        }
        String arr$[] = (String[])sourceFX.keySet().toArray(new String[0]);
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String fxk = arr$[i$];
            SourceFX fx = (SourceFX)sourceFX.get(fxk);
            if(fx.ticks <= 0)
            {
                sourceFX.remove(fxk);
                continue;
            }
            if(fx.loc.posX == xCoord && fx.loc.posY == yCoord && fx.loc.posZ == zCoord)
            {
                Entity player = worldObj.getEntityByID(fx.color);
                if(player != null)
                {
                    for(int a = 0; a < Thaumcraft.proxy.particleCount(2); a++)
                        Thaumcraft.proxy.drawInfusionParticles4(worldObj, player.posX + (double)((worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * player.width), player.boundingBox.minY + (double)(worldObj.rand.nextFloat() * player.height), player.posZ + (double)((worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * player.width), xCoord, yCoord - 1, zCoord);

                }
            } else
            {
                TileEntity tile = worldObj.getTileEntity(fx.loc.posX, fx.loc.posY, fx.loc.posZ);
                if(tile instanceof TilePedestal)
                {
                    ItemStack is = ((TilePedestal)tile).getStackInSlot(0);
                    if(is != null)
                        if(worldObj.rand.nextInt(3) == 0)
                        {
                            Thaumcraft.proxy.drawInfusionParticles3(worldObj, (float)fx.loc.posX + worldObj.rand.nextFloat(), (float)fx.loc.posY + worldObj.rand.nextFloat() + 1.0F, (float)fx.loc.posZ + worldObj.rand.nextFloat(), xCoord, yCoord - 1, zCoord);
                        } else
                        {
                            Item bi = is.getItem();
                            int md = is.getItemDamage();
                            if(is.getItemSpriteNumber() == 0 && (bi instanceof ItemBlock))
                            {
                                for(int a = 0; a < Thaumcraft.proxy.particleCount(2); a++)
                                    Thaumcraft.proxy.drawInfusionParticles2(worldObj, (float)fx.loc.posX + worldObj.rand.nextFloat(), (float)fx.loc.posY + worldObj.rand.nextFloat() + 1.0F, (float)fx.loc.posZ + worldObj.rand.nextFloat(), xCoord, yCoord - 1, zCoord, Block.getBlockFromItem(bi), md);

                            } else
                            {
                                for(int a = 0; a < Thaumcraft.proxy.particleCount(2); a++)
                                    Thaumcraft.proxy.drawInfusionParticles1(worldObj, (float)fx.loc.posX + 0.4F + worldObj.rand.nextFloat() * 0.2F, (float)fx.loc.posY + 1.23F + worldObj.rand.nextFloat() * 0.2F, (float)fx.loc.posZ + 0.4F + worldObj.rand.nextFloat() * 0.2F, xCoord, yCoord - 1, zCoord, bi, md);

                            }
                        }
                } else
                {
                    fx.ticks = 0;
                }
            }
            fx.ticks--;
            sourceFX.put(fxk, fx);
        }
    }

    public AspectList getAspects()
    {
        return recipeEssentia;
    }

    public void setAspects(AspectList aspectlist)
    {
    }

    public int addToContainer(Aspect tag, int amount)
    {
        return 0;
    }

    public boolean takeFromContainer(Aspect tag, int amount)
    {
        return false;
    }

    public boolean takeFromContainer(AspectList ot)
    {
        return false;
    }

    public boolean doesContainerContainAmount(Aspect tag, int amount)
    {
        return false;
    }

    public boolean doesContainerContain(AspectList ot)
    {
        return false;
    }

    public int containerContains(Aspect tag)
    {
        return 0;
    }

    public boolean doesContainerAccept(Aspect tag)
    {
        return true;
    }

    private ArrayList pedestals;
    private int dangerCount;
    public boolean active;
    public boolean crafting;
    public boolean checkSurroundings;
    private AspectList recipeEssentia;
    private ArrayList recipeIngredients;
    private Object recipeOutput;
    private String recipePlayer;
    private String recipeOutputLabel;
    private ItemStack recipeInput;
    private int recipeXP;
    private int recipeType;
    public HashMap sourceFX;
    public int count;
    public int craftCount;
    public float startUp;
    private int countDelay;
    ArrayList ingredients;
    int itemCount;
    public float renderPos = 0.F;
    public float renderAngl = 0.F;
    public float posAdd = 0.003F;
}
