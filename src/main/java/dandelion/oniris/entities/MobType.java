package dandelion.oniris.entities;

import dandelion.oniris.entities.pathfinders.PathfinderGoalMount;
import dandelion.oniris.entities.pathfinders.PathfinderGoalPet;
import net.minecraft.server.v1_16_R2.*;

import java.util.Arrays;
import java.util.List;

public enum MobType {
    HOSTILE(Arrays.asList(PathfinderGoalFloat.class, PathfinderGoalNearestAttackableTarget.class, PathfinderGoalMeleeAttack.class)),
    PASSIVE(Arrays.asList(PathfinderGoalFloat.class, PathfinderGoalNearestAttackableTarget.class, PathfinderGoalMeleeAttack.class)),
    INNOFENSIVE(Arrays.asList(PathfinderGoalFloat.class, PathfinderGoalNearestAttackableTarget.class, PathfinderGoalMeleeAttack.class)),
    MOUNT(Arrays.asList(PathfinderGoalFloat.class, PathfinderGoalMount.class)),
    PET(Arrays.asList(PathfinderGoalFloat.class, PathfinderGoalPet.class, PathfinderGoalLookAtPlayer.class)),
    BOSS(Arrays.asList(PathfinderGoalFloat.class, PathfinderGoalPet.class, PathfinderGoalLookAtPlayer.class)),
    DNPC(Arrays.asList(PathfinderGoalFloat.class, PathfinderGoalPet.class, PathfinderGoalLookAtPlayer.class));

    List<Class<? extends PathfinderGoal>> pathsfinderClasses;

    MobType(List<Class<? extends PathfinderGoal>> pathsfinderClasses) {
        this.pathsfinderClasses = pathsfinderClasses;
    }
}
