package dandelion.oniris.entities;

import dandelion.oniris.entities.entity.CustomCow;
import dandelion.oniris.entities.entity.DandelionEntity;

public enum EntityRegistry {
    CUSTOMCOW(CustomCow.class);

    Class<? extends DandelionEntity> customClass;
    EntityRegistry(Class<? extends DandelionEntity> customClass) {
        this.customClass = customClass;
    }

    public Class<? extends DandelionEntity> getCustomClass() {
        return customClass;
    }
}
