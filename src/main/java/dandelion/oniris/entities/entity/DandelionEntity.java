package dandelion.oniris.entities.entity;

import dandelion.oniris.entities.DandelionMob;

public interface DandelionEntity {
    void setRespawn(boolean respawn);
    DandelionMob getMob();
}
