package dandelion.oniris.players.classes.list;

import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.Stats;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Judge extends RPGClass {
    public Judge() {
        super("Judge",new Stats(5,5,5,5,5),new ItemStack(Material.DIAMOND_HOE),new String[]{"firework","judgement","vision","portal","distortion"},
                new String[]{"scythe", "cards"});
        this.getSpecies().add(spec.ELYTRA);
    }

    @Override
    public void onClassSelected(Player player, PlayerStats playerStats) {

    }
}
