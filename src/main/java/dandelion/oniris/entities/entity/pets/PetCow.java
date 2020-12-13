package dandelion.oniris.entities.entity.pets;

import dandelion.oniris.entities.pathfinders.PathfinderGoalMount;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;

public class PetCow  extends EntityCow {
    public PetCow(World world, Player player) {
        super(EntityTypes.COW, world);

        this.setPosition(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
        this.setInvulnerable(true);

        this.setGoalTarget(((CraftPlayer)player).getHandle(), EntityTargetEvent.TargetReason.CUSTOM, true);
    }
    

    @Override
    protected void initPathfinder() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        //this.goalSelector.a(1, new PathfinderGoalPet(this, 1.5,20));
        this.goalSelector.a(1, new PathfinderGoalMount(this));
        this.goalSelector.a(2, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
    }
}