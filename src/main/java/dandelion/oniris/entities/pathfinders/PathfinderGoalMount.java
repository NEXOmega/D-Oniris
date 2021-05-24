package dandelion.oniris.entities.pathfinders;

import net.minecraft.server.v1_16_R2.Entity;
import net.minecraft.server.v1_16_R2.EntityInsentient;
import net.minecraft.server.v1_16_R2.EntityLiving;
import net.minecraft.server.v1_16_R2.PathfinderGoal;

import java.util.EnumSet;

public class PathfinderGoalMount extends PathfinderGoal {
    private final EntityInsentient mount;
    private EntityLiving rider;

    public PathfinderGoalMount(EntityInsentient entity) {
        this.mount = entity;
        this.a(EnumSet.of(Type.MOVE));
    }

    @Override
    public boolean a() {
        rider = this.mount.getGoalTarget();
        if(mount.passengers.isEmpty())
            return false;
        return true;
    }

    public void c() {
        /*double x,y,z;
        double pitch = ((rider.pitch + 90) * Math.PI) / 180;
        double yaw  = ((rider.yaw + 90)  * Math.PI) / 180;

        x = Math.sin(pitch) * Math.cos(yaw);
        y = Math.sin(pitch) * Math.sin(yaw);
        z = Math.cos(pitch);*/
        this.mount.die();
        this.mount.getNavigation().a(mount.locX()+10, mount.locY()+1, mount.locZ()+ 10, 2);
    }

    public boolean b() {
        //runs ater c
        //runs every ticks as long as its true (repeats c)
        return !this.mount.getNavigation().m();
    }
}
