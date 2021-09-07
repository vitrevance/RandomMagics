package randommagics;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagInt;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.IWandRodOnUpdate;
import thaumcraft.common.items.wands.ItemWandCasting;

public class WandRodPrimordialOnUpdate implements IWandRodOnUpdate {

	   Aspect aspect;
	   ArrayList primals;


	   public WandRodPrimordialOnUpdate(Aspect aspect) {
	      this.aspect = aspect;
	   }

	   public WandRodPrimordialOnUpdate() {
	      this.aspect = null;
	      this.primals = Aspect.getPrimalAspects();
	   }

	   public void onUpdate(ItemStack itemstack, EntityPlayer player) {
	      if(this.aspect != null) {
	         if(player.ticksExisted % 200 == 0 && ((ItemWandCasting)itemstack.getItem()).getVis(itemstack, this.aspect) < ((ItemWandCasting)itemstack.getItem()).getMaxVis(itemstack)) {
	            ((ItemWandCasting)itemstack.getItem()).addVis(itemstack, this.aspect, 1, true);
	         }
	      } else if(player.ticksExisted % 50 == 0) {
	         ArrayList q = new ArrayList();
	         Iterator i$ = this.primals.iterator();

	         while(i$.hasNext()) {
	            Aspect as = (Aspect)i$.next();
	            if(((ItemWandCasting)itemstack.getItem()).getVis(itemstack, as) < ((ItemWandCasting)itemstack.getItem()).getMaxVis(itemstack)) {
	               q.add(as);
	            }
	         }

	         if(q.size() > 0) {
	            ((ItemWandCasting)itemstack.getItem()).addVis(itemstack, (Aspect)q.get(player.worldObj.rand.nextInt(q.size())), 1, true);
	         }
	      }

	   }
	}

