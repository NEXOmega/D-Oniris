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
        this.expToDrop = dandelionMob.getRewardXp();
        this.type = type;

        try {
            FonctionUtils.registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_ATTACK_DAMAGE);
            FonctionUtils.registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_ATTACK_KNOCKBACK);
            this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.ARMOR, a -> a.setValue(dandelionMob.getStats().getDefence())));

            this.getAttributeInstance(GenericAttributes.ATTACK_KNOCKBACK).setValue(1);
            this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(dandelionMob.getStats().getStrength());
            this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(dandelionMob.getStats().getVitality());
            //this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(dandelionMob.getStats().getSpeed());
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.setHealth(dandelionMob.getStats().getVitality());
    }

    @Override
    protected void initPathfinder() {

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

    @Override
    public MobType getMobType() {
        return type;
    }
}
