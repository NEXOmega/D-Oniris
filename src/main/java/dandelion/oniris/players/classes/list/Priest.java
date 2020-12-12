package dandelion.oniris.players.classes.list;

import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.Stats;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Priest extends RPGClass {
    public Priest() {
        super("Pretre",new Stats(3,7,4,5,1),new ItemStack(Material.GOLDEN_APPLE),new String[]{"firework","heal","zoneheal"},
                new String[]{"staff", "highstaff"});
    }

    @Override
    public void onClassSelected(Player player, PlayerStats playerStats) {

    }
}

