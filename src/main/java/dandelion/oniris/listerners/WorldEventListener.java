package dandelion.oniris.listerners;

import dandelion.oniris.Oniris;
import dandelion.oniris.entities.entity.DandelionEntity;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class WorldEventListener implements Listener {
    Oniris oniris;

    public WorldEventListener(Oniris oniris) {
        this.oniris = oniris;
    }

    @EventHandler
    public void chunckUnload(ChunkUnloadEvent event) {
        /*for(Entity entity : event.getChunk().getEntities()) {
            if(entity instanceof LivingEntity) {
                if(((CraftEntity)entity).getHandle() instanceof DandelionEntity) {
                    oniris.getEntityManager().unloadEntity(entity);
                }
            }
        }*/
    }
}
