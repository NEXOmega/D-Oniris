package dandelion.oniris.entities;

import dandelion.oniris.Oniris;
import dandelion.oniris.utils.DTicks;
import org.bukkit.Bukkit;

public class EntityManager {
    Oniris oniris;

    public EntityManager(Oniris oniris) {
        this.oniris = oniris;
    }

    public void respawn(DandelionMob mob, boolean spawned) {
        if (mob == null)
            return;

        Bukkit.getScheduler().scheduleSyncDelayedTask(Oniris.getInstance(), mob::spawn,(spawned  ? DTicks.seconds(mob.getRespawnTime()) : DTicks.seconds(1)));
    }
}
