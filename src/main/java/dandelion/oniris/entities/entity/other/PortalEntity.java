package dandelion.oniris.entities.entity.other;

import dandelion.oniris.Oniris;
import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.entities.MobType;
import dandelion.oniris.entities.entity.DandelionEntity;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.PortalStone;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PortalEntity extends DandelionEntity {
    DandelionMob dandelionMob;
    PortalStone portalStone;
    public PortalEntity(EntityTypes<? extends EntityCreature> entitytypes, World world, String uuid, DandelionMob dandelionMob, MobType type) {
        super(EntityTypes.IRON_GOLEM, world, uuid, dandelionMob, MobType.PORTAL);
        this.dandelionMob = dandelionMob;
        this.setInvulnerable(true);
        this.persistentInvisibility = true;
        this.setInvisible(true);
        this.collides = false;
        Location location = dandelionMob.getMobLocations().get(uuid);
        portalStone = new PortalStone(dandelionMob.getName(), org.bukkit.Material.BAMBOO,
                new Location(Bukkit.getWorld(location.getWorld().getName()), location.getX(), location.getY(), location.getZ()));
        System.out.println(location.getX() +""+ location.getY() +""+ location.getZ());
    }

    @Override
    protected EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
        if(enumhand.equals(EnumHand.OFF_HAND)) return super.b(entityhuman, enumhand);

        if(Oniris.getInstance().getPlayers().containsKey(entityhuman.getBukkitEntity())) {
            PlayerStats data = Oniris.getInstance().getPlayers().get(entityhuman.getBukkitEntity());
            if(!Oniris.getInstance().getPlayers().get(entityhuman.getBukkitEntity()).getPortalStones().contains(portalStone)) {
                data.getPortalStones().add(portalStone);
                entityhuman.getBukkitEntity().sendMessage(ChatColor.YELLOW + "Vous avez appris la pierre " + ChatColor.RED + dandelionMob.getName());
            }

            entityhuman.getBukkitEntity().openInventory(Oniris.getInstance().getGui().getPortalStoneGui().inv((Player) entityhuman.getBukkitEntity(), data));
        }

        return super.b(entityhuman, enumhand);
    }
}
