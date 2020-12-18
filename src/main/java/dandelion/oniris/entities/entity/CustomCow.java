package dandelion.oniris.entities.entity;

import dandelion.oniris.Oniris;
import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.entities.MobType;
import dandelion.oniris.utils.FonctionUtils;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.attribute.Attribute;

public class CustomCow extends EntityCow implements DandelionEntity {
    String uuid;
    DandelionMob dandelionMob;
    MobType type;
    public CustomCow(World world, String uuid, DandelionMob dandelionMob, MobType type) {
        super(EntityTypes.COW, world);
        this.dandelionMob = dandelionMob;
        this.uuid = uuid;
        this.setHealth(dandelionMob.getStats().getVitality());
        this.expToDrop = dandelionMob.getRewardXp();
        this.type = type;

        try {
            FonctionUtils.registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_ATTACK_DAMAGE);
            this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.ATTACK_DAMAGE, (a) -> {a.setValue(dandelionMob.getStats().getStrength());}));
            this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.MOVEMENT_SPEED, a -> a.setValue(dandelionMob.getStats().getSpeed())));
            this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.ARMOR, a -> a.setValue(dandelionMob.getStats().getDefence())));
            this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.MAX_HEALTH, a -> a.setValue(dandelionMob.getStats().getVitality())));
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void initPathfinder() {

    }

    @Override
    public void die() {
        super.die();
        System.out.println("Dead " + dandelionMob.getMob_name() +" "+dandelionMob.isRespawn());
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
