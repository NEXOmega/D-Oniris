package dandelion.oniris.entities.entity.pets;

import dandelion.oniris.utils.FonctionUtils;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class PetCow  extends EntityCow {
    public PetCow(World world, Player player) {
        super(EntityTypes.COW, world);
        this.setPosition(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

        try {
            FonctionUtils.registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_ATTACK_DAMAGE);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    

    @Override
    protected void initPathfinder() {

        this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.ATTACK_DAMAGE, (a) -> {a.setValue(10.0);}));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 1));

    }

}