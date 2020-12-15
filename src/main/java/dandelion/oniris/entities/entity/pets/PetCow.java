package dandelion.oniris.entities.entity.pets;

import net.minecraft.server.v1_16_R2.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Map;

public class PetCow  extends EntityCow {
    public PetCow(World world, Player player) {
        super(EntityTypes.COW, world);
        this.setPosition(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

        try {
            registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_ATTACK_DAMAGE);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    

    @Override
    protected void initPathfinder() {

        this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.ATTACK_DAMAGE, (a) -> {a.setValue(10.0);}));


        //this.goalSelector.a(0, new PathfinderGoalMoveTowardsRestriction());
        this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this , 2, true));

        this.targetSelector.a(0, new PathfinderGoalHurtByTarget(this, EntityHuman.class));
        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));

        //this.goalSelector.a(1, new PathfinderGoalPet(this, 1.5,20));
        /*this.goalSelector.a(1, new PathfinderGoalMount(this));
        this.goalSelector.a(2, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));*/
    }
    private static Field attributeField;

    static {
        try {
            attributeField = net.minecraft.server.v1_16_R2.AttributeMapBase.class.getDeclaredField("b");
            attributeField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void registerGenericAttribute(org.bukkit.entity.Entity entity, Attribute attribute) throws IllegalAccessException {
        net.minecraft.server.v1_16_R2.AttributeMapBase attributeMapBase = ((org.bukkit.craftbukkit.v1_16_R2.entity.CraftLivingEntity)entity).getHandle().getAttributeMap();
        Map<net.minecraft.server.v1_16_R2.AttributeBase, net.minecraft.server.v1_16_R2.AttributeModifiable> map = (Map<net.minecraft.server.v1_16_R2.AttributeBase, net.minecraft.server.v1_16_R2.AttributeModifiable>) attributeField.get(attributeMapBase);
        net.minecraft.server.v1_16_R2.AttributeBase attributeBase = org.bukkit.craftbukkit.v1_16_R2.attribute.CraftAttributeMap.toMinecraft(attribute);
        net.minecraft.server.v1_16_R2.AttributeModifiable attributeModifiable = new net.minecraft.server.v1_16_R2.AttributeModifiable(attributeBase, net.minecraft.server.v1_16_R2.AttributeModifiable::getAttribute);
        map.put(attributeBase, attributeModifiable);
    }
}