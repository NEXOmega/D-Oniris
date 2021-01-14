package dandelion.oniris.players.classes.list;

import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.Stats;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Champion extends RPGClass {
    public Champion() {
        super("Champion",new Stats(9, 4,5,6,1),new ItemStack(Material.DIAMOND_AXE),new String[]{"firework","leap", "propulse", "berserkmode"},
                new String[]{"sword", "broadsword", "axe", "mace", "spear"});
    }

    @Override
    public void onClassSelected(Player player, PlayerStats playerStats) {

    }
}
