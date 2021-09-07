package randommagics;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import randommagics.entities.EntityBigExplosion;
import randommagics.entities.EntityBlockProjectile;
import randommagics.entities.EntityEntityProjectile;
import randommagics.entities.EntityExpulosion;
import randommagics.entities.EntityPotionProjectile;
import randommagics.entities.EntityPowerBlast;
import randommagics.entities.EntityStealingOrb;
import randommagics.entities.mobs.EntityDemon;
import randommagics.entities.mobs.EntitySupremeDemon;
import randommagics.render.EntitySpecialRenderer;

public class EntityInit {
	
	public static void init() {
		EntityRegistry.registerModEntity(EntityExpulosion.class, "EntityExpulosion", 0, Main.instance, 255, 20, false);
		EntityRegistry.registerModEntity(EntityEntityProjectile.class, "EntityEntityProjectile", 1, Main.instance, 100, 20, true);
		EntityRegistry.registerModEntity(EntityBlockProjectile.class, "EntityBlockProjectile", 2, Main.instance, 100, 20, true);
		EntityRegistry.registerModEntity(EntityPotionProjectile.class, "EntityPotionProjectile", 3, Main.instance, 100, 20, true);
		EntityRegistry.registerModEntity(EntityPowerBlast.class, "EntityPowerBlast", 4, Main.instance, 100, 20, true);
		EntityRegistry.registerModEntity(EntityStealingOrb.class, "EntityStealingOrb", 5, Main.instance, 100, 20, true);
		EntityRegistry.registerModEntity(EntitySupremeDemon.class, "EntitySupremeDemon", 6, Main.instance, 100, 20, true);
		EntityRegistry.registerModEntity(EntityDemon.class, "EntityDemon", 7, Main.instance, 100, 20, true);
		EntityRegistry.registerModEntity(EntityBigExplosion.class, "EntityBigExplosion", 8, Main.instance, 255, 20, false);
	}
}
