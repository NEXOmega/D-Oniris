package dandelion.oniris.entities;

import dandelion.oniris.Oniris;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class PetManager {
    HashMap<Player, LivingEntity> pets = new HashMap<>();
    Oniris oniris;

    public PetManager(Oniris oniris) {
        this.oniris = oniris;
    }

    public void init() {
        new BukkitRunnable() {
            public void run() {
                pets.forEach((p, e) -> {
                    if(e.isDead())
                        cancel();
                    else {
                        double d = p.getLocation().distance(e.getLocation());

                        if (d >= 4.0D) {
                            try {
                                /*Object craftLivingEntity = NMSManager.getInstance().getCraftClass("entity.CraftLivingEntity").cast(cow);
                                Method getHandleMethod = craftLivingEntity.getClass().getMethod("getHandle", new Class<?>[0]);

                                Object entityCow = getHandleMethod.invoke(craftLivingEntity, new Object[0]);
                                Object entityInsentient = NMSManager.getInstance().getNMSClass("EntityInsentient").cast(entityCow);

                                Method getNavigationMethodCow = entityInsentient.getClass().getMethod("getNavigation", new Class<?>[0]);
                                Object entityNavigation =  getNavigationMethodCow.invoke(entityInsentient, new Object[0]);
                                Method aMethod = entityNavigation.getClass().getMethod("a", new Class<?>[] { double.class, double.class, double.class, double.class});
                                aMethod.invoke(entityNavigation, new Object[] { p.getLocation().getX() + 1.5D, p.getLocation().getY(), p.getLocation().getZ() + 1.5D, 2.0D });*/
                            } catch (Exception exp) {
                                exp.printStackTrace();
                            }
                        }

                        if (d > 20.0D) {
                            e.teleport(p.getLocation());
                        }
                    }

                });
            }
        }.runTaskTimer(oniris, 10L, 10L);
    }

    public void addPet(Player p, LivingEntity entity) {
        if(pets.containsKey(p.getDisplayName())) {
            pets.get(p).remove();
        }

        this.pets.put(p, entity);
    }
}
