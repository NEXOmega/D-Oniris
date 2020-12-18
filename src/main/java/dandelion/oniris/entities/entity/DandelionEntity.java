package dandelion.oniris.entities.entity;

import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.entities.MobType;

public interface DandelionEntity {
    void setRespawn(boolean respawn);
    DandelionMob getMob();
    MobType getMobType();
}
