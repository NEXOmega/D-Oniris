package dandelion.oniris.entities.entity;

import dandelion.oniris.Oniris;
import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.utils.FonctionUtils;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.attribute.Attribute;

public class CustomCow extends EntityCow implements DandelionEntity {
    String uuid;
    DandelionMob dandelionMob;
    public CustomCow(World world, String uuid, DandelionMob dandelionMob) {
        super(EntityTypes.COW, world);
        this.dandelionMob = dandelionMob;
        this.uuid = uuid;
        this.setHealth(dandelionMob.getStats().getVitality());
        this.expToDrop = dandelionMob.getRewardXp();

        try {
            FonctionUtils.registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_ATTACK_DAMAGE);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void initPathfinder() {
        this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.ATTACK_DAMAGE, (a) -> {a.setValue(10.0);}));
    }

    @Override
    public void die() {
        super.die();
        if(dandelionMob.isRespawn())
            Oniris.getInstance().getEntityManager().respawn(dandelionMob.getMob_name(), uuid, true);
    }

    @Override
    public void setRespawn(boolean respawn) {
        this.dandelionMob.setRespawn(respawn);
    }

    @Override
    public DandelionMob getMob() {
        return dandelionMob;
    }
}
