package dandelion.oniris.entities;

import dandelion.oniris.entities.entity.DandelionEntity;
import dandelion.oniris.entities.entity.other.PortalEntity;

import javax.sound.sampled.Port;

public enum EntityRegistry {
    DANDELIONENTITY(DandelionEntity.class),
    PORTALENTITY(PortalEntity.class);

    Class<? extends DandelionEntity> customClass;
    EntityRegistry(Class<? extends DandelionEntity> customClass) {
        this.customClass = customClass;
    }

    public Class<? extends DandelionEntity> getCustomClass() {
        return customClass;
    }
}
