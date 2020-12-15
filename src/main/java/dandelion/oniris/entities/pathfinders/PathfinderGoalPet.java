package dandelion.oniris.entities.pathfinders;

import net.minecraft.server.v1_16_R2.*;

import java.util.EnumSet;

public class PathfinderGoalPet extends PathfinderGoal {
    private final EntityInsentient a;//Pet
    private EntityLiving b;

    private final double f; // pet spedd;
    private final float g;// distance between owner & pet to teleport

    private double c,d,e;//x,y,z

    public PathfinderGoalPet(EntityInsentient pet, double speed, float distance) {
        this.a = pet;

        this.f = speed;
        this.g = distance;

        this.a(EnumSet.of(Type.MOVE));
    }



    @Override
    public boolean a() {
        //will start the pathfinding goal if it is true
        //runs every tick
        this.b = this.a.getGoalTarget();
        System.out.println(this.b.getDisplayName());
        if(this.b == null)
            return false;
        else if(this.a.getRidingPassenger() != null)
            return false;
        else if(this.a.getDisplayName() == null)
            return false;
        else if(!(this.a.getDisplayName().toString().contains(this.b.getName())))
            return false;
        else if(this.b.h(this.a) > (double) (this.g * this.g)) {
            //teleport to owner
            a.setPosition(this.b.locX(), this.b.locY(), this.b.locZ());
            return false;
        }
        else {
            //follow the owner
            Vec3D vec = RandomPositionGenerator.a((EntityCreature)this.a, 16,7,this.b.getPositionVector());

            // in air
            if(vec == null)
                return false;

            this.c = this.b.locX();
            this.d = this.b.locY();
            this.e = this.b.locZ();
            return true; // runs c()
        }
    }

    public void c() {
        //runner
        this.a.getNavigation().a(this.c, this.d, this.e, this.f);
    }

    public boolean b() {
        //runs ater c
        //runs every ticks as long as its true (repeats c)
        return !this.a.getNavigation().m() && this.b.h(this.a) < (double) (this.g * this.g);
    }

    public void d() {
        // runs when b() returns false
        this.b = null;
    }
}
