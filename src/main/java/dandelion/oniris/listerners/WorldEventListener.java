package dandelion.oniris.listerners;

import dandelion.oniris.entities.entity.DandelionEntity;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class WorldEventListener implements Listener {
    @EventHandler
    public void chunckUnload(ChunkUnloadEvent event) {
        for(Entity entity : event.getChunk().getEntities()) {
            if(entity instanceof LivingEntity) {
                System.out.println(entity.spigot().getClass());
                System.out.println(((CraftEntity)entity).getHandle().getClass());
                if(((CraftEntity)entity).getHandle() instanceof DandelionEntity) {
                    System.out.println("HolaMuchos");
                }
            }

        }
    }
}
