package dandelion.oniris.players;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.classes.ClassType;
import dandelion.oniris.utils.DandelionItem;
import dandelion.oniris.utils.ItemUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class PlayerUtils {

    Oniris main;

    public PlayerUtils(Oniris main) {
        this.main = main;
    }

    public void killPlayer(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 2));
        p.teleport(new Location(p.getWorld(), 189, 63.5, 240));

        PlayerStats stats = main.getPlayers().get(p);
        stats.setHealth(stats.getMaxVitality());

        stats.updateHealthBar();

        //p.damage(2);
        if (p.getHealth() < 10) {
            p.sendMessage("Vous êtes mort trop souvent, attention vous subissez une pénalitée");
            p.setHealth(20);
        }

    }

    public void addEquipementStat(PlayerStats stats, ItemStack item, boolean add) {
        Stats equipementStats = stats.getEquipementStats();
        DandelionItem dItem = ItemUtils.getDandelionItem(item);
        equipementStats.setStrength(equipementStats.getStrength() + (add ? dItem.getStats().getStrength() : (-1)*dItem.getStats().getStrength()));
        equipementStats.setDefence(equipementStats.getDefence() +  (add ? dItem.getStats().getDefence() : (-1)*dItem.getStats().getDefence()));
        equipementStats.setVitality(equipementStats.getVitality() +  (add ? dItem.getStats().getVitality() : (-1)*dItem.getStats().getVitality()));
        equipementStats.setSpeed(equipementStats.getSpeed() +  (add ? dItem.getStats().getSpeed() : (-1)*dItem.getStats().getSpeed()));
        equipementStats.setMagie(equipementStats.getMagie() +  (add ? dItem.getStats().getMagie() : (-1)*dItem.getStats().getMagie()));
    }

    public void setNewClass(Player player, PlayerStats stats, ClassType newClass) {
        stats.setPreviousClassType(stats.getClassType());
        stats.setClassType(newClass);
        stats.getClassType().getRpgClass().onClassSelected(player, stats);
        stats.getSpells().clear();
        stats.getSpells().addAll(Arrays.asList(stats.getClassType().getSpellList()));

    }
}
