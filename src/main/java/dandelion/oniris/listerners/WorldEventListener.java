package dandelion.oniris.listerners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class WorldEventListener implements Listener {
    @EventHandler
    public void chunckUnload(ChunkUnloadEvent event) {
        /*
        for(Entity entity : event.getChunk().getEntities()) {
            if(entity instanceof LivingEntity) {
                if(((CraftEntity)entity).getHandle() instanceof DandelionEntity) {
                }
            }

        }*/
    }
}
