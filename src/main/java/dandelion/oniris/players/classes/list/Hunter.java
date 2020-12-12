package dandelion.oniris.players.classes.list;

import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.Stats;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Hunter extends RPGClass {
    public Hunter() {
        super("Hunter",new Stats(2, 10,5,3,5),new ItemStack(Material.BOW),new String[]{"firework","arrowrain", "camoskin", "soulhunt"},
                new String[]{"bow", "longbow", "dagger"});
    }

    @Override
    public void onClassSelected(Player player, PlayerStats playerStats) {

    }
}
