package randommagics.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import randommagics.items.FocusInfinity;
import randommagics.items.ItemInfinityStone;
import randommagics.items.ItemMagicianHat;
import thaumcraft.common.items.wands.ItemFocusPouch;

public class ContainerMagicianHat extends Container
{
    private InventoryFocusInfinity inv;

    private int slotID = 0;
    
    private World worldObj;
    private int posX;
    private int posY;
    private int posZ;
    private int blockSlot;
    public IInventory input;
    EntityPlayer player;
    ItemStack hat;

    public ContainerMagicianHat(InventoryPlayer iinventory, World par2World, int par3, int par4, int par5)
    {
    	input = new InventoryMagicianHat(iinventory.player.getCurrentEquippedItem());
    	hat = null;
        player = null;
        worldObj = par2World;
        posX = par3;
        posY = par4;
        posZ = par5;
        player = iinventory.player;
        hat = iinventory.getCurrentItem();
        blockSlot = iinventory.currentItem + 27 + input.getSizeInventory();
        
        addSlotToContainer(new Slot(input, slotID++, 44 + 2 * 18, 17 + 1 * 18));
        
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                addSlotToContainer(new Slot(iinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++)
        {
            addSlotToContainer(new Slot(iinventory, i, 8 + i * 18, 142));
        }
    }
    
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot)
    {
        if(slot == blockSlot)
            return null;
        ItemStack stack = null;
        Slot slotObject = (Slot)inventorySlots.get(slot);
        if(slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();
            if(slot < 1)
            {
                if(!input.isItemValidForSlot(slot, stackInSlot) || !mergeItemStack(stackInSlot.copy(), 1, inventorySlots.size(), true))
                    return null;
            }
            else {
            	boolean flag = true;
            	for (int i = 0; i < input.getSizeInventory(); i++) {
            		if (input.isItemValidForSlot(i, stackInSlot) && input.getStackInSlot(i) == null) {
            			input.setInventorySlotContents(i, stackInSlot.copy());
    	            	stackInSlot.stackSize = 0;
    	            	flag = false;
    	            	break;
            		}
            	}
            	if (flag) {
            		return null;
            	}
            }
            if(stackInSlot.stackSize == 0)
                slotObject.putStack(null);
            else
                slotObject.onSlotChanged();
        }
        return stack;
    }
    
    @Override
    protected void retrySlotClick(int p_75133_1_, int p_75133_2_, boolean p_75133_3_, EntityPlayer p_75133_4_) {
    }
    
    public ItemStack slotClick(int slot, int button, int mode, EntityPlayer par4EntityPlayer)
    {
        if(slot == blockSlot)
            return null;
        else {
        	if (button == 2) {
        		input.setInventorySlotContents(slot, null);
        		return null;
        	}
            return super.slotClick(slot, button, mode, par4EntityPlayer);
        }
    }
    
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);
        if(!worldObj.isRemote)
        {
            ((ItemMagicianHat)hat.getItem()).setInventory(hat, ((InventoryMagicianHat)input).items);
            if(player == null)
                return;
            if(player.getHeldItem() != null && player.getHeldItem().isItemEqual(hat))
                player.setCurrentItemOrArmor(0, hat);
            player.inventory.markDirty();
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }
}
