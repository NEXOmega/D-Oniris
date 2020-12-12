package dandelion.oniris.players.classes.list;

import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.Stats;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AlForge extends RPGClass {

    public AlForge() {
        super("AlForge", new Stats(6,4,10,6,3), new ItemStack(Material.DIAMOND_SWORD), new String[]{"firework","AlArmor", "AlArme"},
                new String[]{"sword", "broadsword", "spear", "bow"});
        this.getSpecies().add(spec.NOARMOR);
    }

    @Override
    public void onClassSelected(Player player, PlayerStats playerStats) {

    }
}
