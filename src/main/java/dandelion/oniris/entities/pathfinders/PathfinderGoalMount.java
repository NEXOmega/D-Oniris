package dandelion.oniris.entities.pathfinders;

import net.minecraft.server.v1_16_R2.Entity;
import net.minecraft.server.v1_16_R2.EntityInsentient;
import net.minecraft.server.v1_16_R2.PathfinderGoal;

import java.util.EnumSet;

public class PathfinderGoalMount extends PathfinderGoal {
    private final EntityInsentient entity;

    public PathfinderGoalMount(EntityInsentient entity) {
        this.entity = entity;
        this.a(EnumSet.of(Type.MOVE));
    }

    @Override
    public boolean a() {
        if(entity.passengers.isEmpty())
            return false;
        return true;
    }

    public void c() {
        double x,y,z;
        Entity controller = this.entity.getPassengers().get(0);
        double pitch = ((controller.pitch + 90) * Math.PI) / 180;
        double yaw  = ((controller.yaw + 90)  * Math.PI) / 180;

        x = Math.sin(pitch) * Math.cos(yaw);
        y = Math.sin(pitch) * Math.sin(yaw);
        z = Math.cos(pitch);

        this.entity.getNavigation().a(entity.locX()+Math.round(x),entity.locY()+Math.round(x),entity.locZ()+ Math.round(x), 3);

        System.out.println("x:"+x+ " y:"+y+" z:"+z);
        System.out.println("loc x:"+entity.locX()+x+ " loc y:"+entity.locY()+ y+" loc z:"+ entity.locZ()+z);
    }

    public boolean b() {
        //runs ater c
        //runs every ticks as long as its true (repeats c)
        return !this.entity.getNavigation().m();
    }
}
