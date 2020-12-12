package dandelion.oniris.players.classes.list;

import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.Stats;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Shaman extends RPGClass {
    public Shaman() {
        super("Shaman",new Stats(4,6,5,4,1),new ItemStack(Material.IRON_SWORD),new String[]{"firework","forcedrest","zoneheal"},
                new String[]{"spear", "cards", "highstaff"});
    }

    @Override
    public void onClassSelected(Player player, PlayerStats playerStats) {

    }
}
