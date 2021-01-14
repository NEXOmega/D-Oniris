package dandelion.oniris.entities;

import com.mongodb.Block;
import dandelion.oniris.Oniris;
import dandelion.oniris.database.ConvertToDoc;
import dandelion.oniris.entities.entity.DandelionEntity;
import dandelion.oniris.utils.DTicks;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

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
        if (!mobMap.containsKey(mob_name))
            return;
        DandelionMob mob = mobMap.get(mob_name);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Oniris.getInstance(), () ->{
            mob.spawnAsMob(mobSpawn.get(uuid), uuid);
        },(spawned  ? DTicks.seconds(mob.getRespawnTime()) : DTicks.seconds(1)));
    }

    public void loadEntities() {
        oniris.getConnectToDB().getEntityCollecion().find().forEach((Block<? super Document>) e -> {
            DandelionMob mob = ConvertToDoc.docToEntity(e);
            oniris.getEntityManager().addEntity(mob.getMob_name(), mob);
            mob.getMobLocations().forEach((uuid, loc) -> {
                oniris.getEntityManager().addEntityLocation(uuid, mob.getMob_name(), loc);
                mob.spawnAsMob(loc, uuid);
            });
        });
    }

    public void saveEntities() {
        for(World world : Bukkit.getServer().getWorlds()) {
            for(Entity entity : world.getEntities()) {
                if(((CraftEntity)entity).getHandle() instanceof DandelionEntity) {
                    ((DandelionEntity)((CraftEntity)entity).getHandle()).setRespawn(false);
                    oniris.getConnectToDB().addEntity(((DandelionEntity)((CraftEntity)entity).getHandle()).getMob());
                    entity.remove();
                }
            }
        }
    }

    public void resetEntities() {
        for(World world : Bukkit.getServer().getWorlds()) {
            for(Entity entity : world.getEntities()) {
                if(((CraftEntity)entity).getHandle() instanceof DandelionEntity) {
                    ((DandelionEntity)((CraftEntity)entity).getHandle()).setRespawn(false);
                    ((DandelionEntity)((CraftEntity)entity).getHandle()).setDrop(false);
                    entity.remove();
                }
            }
        }
        oniris.getEntityManager().getMobMap().clear();
        oniris.getEntityManager().getMobSpawn().clear();
    }

    public void unloadEntity(Entity entity) {
        ((DandelionEntity)((CraftEntity)entity).getHandle()).setRespawn(false);
        ((DandelionEntity)((CraftEntity)entity).getHandle()).setDrop(false);
        entity.remove();
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
