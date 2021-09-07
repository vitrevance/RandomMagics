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
import thaumcraft.common.items.wands.ItemFocusPouch;

public class ContainerFocusInfinity extends Container
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
    ItemStack focus;

    public ContainerFocusInfinity(InventoryPlayer iinventory, World par2World, int par3, int par4, int par5)
    {
    	input = new InventoryFocusInfinity(iinventory.player.getCurrentEquippedItem());
    	focus = null;
        player = null;
        worldObj = par2World;
        posX = par3;
        posY = par4;
        posZ = par5;
        player = iinventory.player;
        focus = iinventory.getCurrentItem();
        blockSlot = iinventory.currentItem + 33;
        //inv = new InventoryFocusInfinity(iinventory);
    	
        //��������
        addSlotToContainer(new SlotModded(input, slotID++, 44 + 0 * 18, 17 + 1 * 18));
        addSlotToContainer(new SlotModded(input, slotID++, 44 + 1 * 18, 17 + 0 * 18));
        addSlotToContainer(new SlotModded(input, slotID++, 44 + 2 * 18, 17 + 0 * 18));
        addSlotToContainer(new SlotModded(input, slotID++, 44 + 3 * 18, 17 + 0 * 18));
        addSlotToContainer(new SlotModded(input, slotID++, 44 + 2 * 18, 17 + 2 * 18));
        addSlotToContainer(new SlotModded(input, slotID++, 44 + 4 * 18, 17 + 1 * 18));

        //���������
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                addSlotToContainer(new Slot(iinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        //������
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
            if(slot < 6)
            {
                if(!input.isItemValidForSlot(slot, stackInSlot) || !mergeItemStack(stackInSlot, 6, inventorySlots.size(), true))
                    return null;
            } else
            if(input.isItemValidForSlot(stackInSlot.getItemDamage(), stackInSlot) && input.getStackInSlot(stackInSlot.getItemDamage()) == null) {
            	input.setInventorySlotContents(stackInSlot.getItemDamage(), stackInSlot.copy());
            	stackInSlot.stackSize = 0;
            }
            else
                return null;
            if(stackInSlot.stackSize == 0)
                slotObject.putStack(null);
            else
                slotObject.onSlotChanged();
        }
        return stack;
    }
    
    public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer)
    {
        if(par1 == blockSlot)
            return null;
        else
            return super.slotClick(par1, par2, par3, par4EntityPlayer);
    }
    
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);
        if(!worldObj.isRemote)
        {
            ((FocusInfinity)focus.getItem()).setInventory(focus, ((InventoryFocusInfinity)input).items);
            if(player == null)
                return;
            if(player.getHeldItem() != null && player.getHeldItem().isItemEqual(focus))
                player.setCurrentItemOrArmor(0, focus);
            player.inventory.markDirty();
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }
}
