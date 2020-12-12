package dandelion.oniris.entities.entity;

import net.minecraft.server.v1_16_R2.*;
import org.bukkit.entity.Player;

public class CustomCow extends EntityCow {
    public CustomCow(World world, Player player) {
        super(EntityTypes.COW, world);

    }

    @Override
    protected void initPathfinder() {
    }
}
