package dandelion.oniris.entities.entity;

import dandelion.oniris.Oniris;
import dandelion.oniris.entities.DandelionMob;
import net.minecraft.server.v1_16_R2.EntityCow;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.World;

public class CustomCow extends EntityCow {
    DandelionMob dandelionMob;
    public CustomCow(World world, DandelionMob dandelionMob) {
        super(EntityTypes.COW, world);
        this.dandelionMob = dandelionMob;
    }

    @Override
    protected void initPathfinder() {
    }

    @Override
    public void die() {
        super.die();
        System.out.println("AAAAAAAAAAAAAAAAAA");
        Oniris.getInstance().getEntityManager().respawn(dandelionMob, false);
    }
}
