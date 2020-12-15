package dandelion.oniris.entities.pathfinders;

import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Location;

import java.util.EnumSet;

public class MoveTowardsTargetIfRange extends PathfinderGoal  {
    private double speed;
    private float maxDistance;
    private EntityInsentient entity;
    private EntityLiving target;
    private Location spawnPoint;
    public MoveTowardsTargetIfRange(EntityCreature entity, double speed, float range, Location spawnPoint) {
        this.speed = speed;
        this.maxDistance = range;
        this.entity = entity;
        this.spawnPoint = spawnPoint;

        this.a(EnumSet.of(Type.MOVE));
    }

    public boolean a() {
        this.target = this.entity.getGoalTarget();
        if(spawnPoint == null) return false;
        if(this.target == null) {

            this.entity.getNavigation().a(spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ(), this.speed);
            System.out.println("Walk to spawn point");
            return false;
        }
        return true;
    }

    public void c() {
        this.entity.getNavigation().a(this.entity, this.speed);
    }

    public boolean b() {
        return !this.entity.getNavigation().m() && this.target.isAlive() && ((this.entity.h(spawnPoint.getX(), spawnPoint.getY(),spawnPoint.getZ()) < (this.maxDistance) * this.maxDistance) || (this.entity.h(target) < (20*20)));
    }

    public void d() {
        // runs when b() returns false
        this.entity.getNavigation().a(spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ(), this.speed);
        this.target = null;
    }
}
