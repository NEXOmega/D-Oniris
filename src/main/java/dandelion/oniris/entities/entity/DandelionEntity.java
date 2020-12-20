package dandelion.oniris.entities.entity;

import dandelion.oniris.Oniris;
import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.entities.MobType;
import dandelion.oniris.utils.FonctionUtils;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.attribute.Attribute;

import java.util.Random;

public class DandelionEntity extends EntityCreature implements IRangedEntity {
    String uuid;
    DandelionMob dandelionMob;
    MobType type;
    boolean drop = true;

    public DandelionEntity(EntityTypes<? extends EntityCreature> entitytypes, World world, String uuid, DandelionMob dandelionMob, MobType type) {
        super(entitytypes, world);
        this.dandelionMob = dandelionMob;
        this.uuid = uuid;
        this.expToDrop = 0;
        this.type = type;
        this.drop = true;
        this.setBaby(dandelionMob.isBaby());
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
    public void die() {
        super.die();
        if(drop) {
            this.getMob().getRewardList().forEach((reward) -> {
                if(new Random().nextInt(101) <= reward.getChance()) {
                    this.getBukkitEntity().getWorld().dropItem(this.getBukkitEntity().getLocation(), reward.getItem());
                }
            });
        }
        if(this.dead) {
            if(killer != null)
                killer.giveExp(dandelionMob.getRewardXp());
        }
        if(dandelionMob.isRespawn())
            Oniris.getInstance().getEntityManager().respawn(dandelionMob.getMob_name(), uuid, true);
    }

    public void setRespawn(boolean respawn) {
        this.dandelionMob.setRespawn(respawn);
    }

    public boolean isDrop() {
        return drop;
    }

    public void setDrop(boolean drop) {
        this.drop = drop;
    }

    public DandelionMob getMob() {
        return dandelionMob;
    }

    public MobType getMobType() {
        return type;
    }

    @Override
    public void a(EntityLiving entityLiving, float v) {
        ItemStack itemstack = this.f(this.b(ProjectileHelper.a(this, Items.BOW)));
        EntityArrow entityarrow = this.bs(itemstack, this.dandelionMob.getStats().getStrength());
        double d0 = entityLiving.locX() - this.locX();
        double d1 = entityLiving.e(0.3333333333333333D) - entityarrow.locY();
        double d2 = entityLiving.locZ() - this.locZ();
        double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
        entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float)(14 - this.world.getDifficulty().a() * 4));
        this.world.addEntity(entityarrow);

    }

    protected EntityArrow bs(ItemStack itemstack, float v) {
        return ProjectileHelper.a(this, itemstack, v);
    }
}
