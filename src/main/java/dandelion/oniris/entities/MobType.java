package dandelion.oniris.entities;

import dandelion.oniris.entities.pathfinders.MoveTowardsTargetIfRange;
import dandelion.oniris.entities.pathfinders.PathfinderGoalMount;
import dandelion.oniris.entities.pathfinders.PathfinderGoalPet;
import net.minecraft.server.v1_16_R2.*;

import java.util.Arrays;
import java.util.List;

public enum MobType {
    HOSTILE(Arrays.asList(PathfinderGoalMeleeAttack.class, MoveTowardsTargetIfRange.class), Arrays.asList(PathfinderGoalHurtByTarget.class, PathfinderGoalNearestAttackableTarget.class)),
    PASSIVE(Arrays.asList(PathfinderGoalFloat.class, PathfinderGoalNearestAttackableTarget.class, PathfinderGoalMeleeAttack.class), null),
    INNOFENSIVE(Arrays.asList(PathfinderGoalFloat.class, PathfinderGoalNearestAttackableTarget.class, PathfinderGoalMeleeAttack.class), null),
    MOUNT(Arrays.asList(PathfinderGoalFloat.class, PathfinderGoalMount.class), null),
    PET(Arrays.asList(PathfinderGoalFloat.class, PathfinderGoalPet.class, PathfinderGoalLookAtPlayer.class), null),
    BOSS(Arrays.asList(PathfinderGoalFloat.class, PathfinderGoalPet.class, PathfinderGoalLookAtPlayer.class), null),
    DNPC(Arrays.asList(PathfinderGoalFloat.class, PathfinderGoalPet.class, PathfinderGoalLookAtPlayer.class), null);

    List<Class<? extends PathfinderGoal>> goalClasses;
    List<Class<? extends PathfinderGoal>> targetClasses;

    MobType(List<Class<? extends PathfinderGoal>> goalClasses, List<Class<? extends PathfinderGoal>> targetClasses) {
        this.goalClasses = goalClasses;
        this.targetClasses = targetClasses;
    }
}
