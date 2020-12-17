package dandelion.oniris.entities;

import dandelion.oniris.Oniris;
import dandelion.oniris.utils.DTicks;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;

public class EntityManager {
    Oniris oniris;

    HashMap<String, DandelionMob> mobMap;
    HashMap<String, Location> mobSpawn;

    public EntityManager(Oniris oniris) {
        this.oniris = oniris;
        mobMap = new HashMap<>();
        mobSpawn = new HashMap<>();
    }

    public void respawn(String mob_name, String uuid, boolean spawned) {
        System.out.println("Respawn ? " + mob_name);
        if (!mobMap.containsKey(mob_name))
            return;
        DandelionMob mob = mobMap.get(mob_name);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Oniris.getInstance(), () ->{
            mob.spawnAsMob(mobSpawn.get(uuid), uuid);
            System.out.println("Set cooldown : "+mob.getMob_name());
        },(spawned  ? DTicks.seconds(mob.getRespawnTime()) : DTicks.seconds(1)));
    }

    public void addEntity(String mob_name, DandelionMob mob) {
        mobMap.put(mob_name, mob);
    }

    public void addEntityLocation(String uuid, String mobName, Location location) {
        this.mobSpawn.put(uuid, location);
        this.mobMap.get(mobName).getMobLocations().put(uuid, location);
    }

    public HashMap<String, DandelionMob> getMobMap() {
        return mobMap;
    }

    public HashMap<String, Location> getMobSpawn() {
        return mobSpawn;
    }
}
