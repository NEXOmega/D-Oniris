package dandelion.oniris.players.classes.list;

import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.Stats;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Villager extends RPGClass {
    public Villager() {
        super("Villageois", new Stats(1,1,1, 1, 1), new ItemStack(Material.APPLE), new String[]{"firework"}, new String[]{"gauntlet"});
    }

    @Override
    public void onClassSelected(Player player, PlayerStats playerStats) {

    }
}