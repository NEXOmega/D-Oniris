package dandelion.oniris.entities;

import dandelion.oniris.Oniris;
import dandelion.oniris.utils.DTicks;
import org.bukkit.Bukkit;

import java.util.HashMap;

public class EntityManager {
    Oniris oniris;

    HashMap<String, DandelionMob> mobMap;

    public EntityManager(Oniris oniris) {
        this.oniris = oniris;
        mobMap = new HashMap<>();
    }

    public void respawn(DandelionMob mob, boolean spawned) {
        if (mob == null)
            return;

        Bukkit.getScheduler().scheduleSyncDelayedTask(Oniris.getInstance(), () ->{
            mob.spawnAsMob(mob.getSpawnPoint());
        },(spawned  ? DTicks.seconds(mob.getRespawnTime()) : DTicks.seconds(1)));
    }

    public void addEntity(String uuid, DandelionMob mob) {
        mobMap.put(uuid, mob);
    }

    public HashMap<String, DandelionMob> getMobMap() {
        return mobMap;
    }
}
