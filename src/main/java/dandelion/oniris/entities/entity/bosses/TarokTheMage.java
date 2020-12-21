package dandelion.oniris.entities.entity.bosses;

import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.entities.MobType;
import dandelion.oniris.entities.entity.DandelionEntity;
import net.minecraft.server.v1_16_R2.*;

public class TarokTheMage extends DandelionEntity {
    public TarokTheMage(EntityTypes<? extends EntityCreature> entitytypes, World world, String uuid, DandelionMob dandelionMob, MobType type) {
        super(entitytypes, world, uuid, dandelionMob, type);
    }

    @Override
    protected void initPathfinder() {
        this.goalSelector.a(1, new TarokPathfinder());

        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget<EntityHuman>(this, EntityHuman.class, true));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, EntityHuman.class));
    }

    class TarokPathfinder extends PathfinderGoal {

        @Override
        public boolean a() {
            return false;
        }
    }
}
