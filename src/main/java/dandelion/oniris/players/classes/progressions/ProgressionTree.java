package dandelion.oniris.players.classes.progressions;

import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.classes.list.RPGClass;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class ProgressionTree {
    ArrayList<RPGClass> nextClasses;

    ProgressionTree(ArrayList<RPGClass> nextClasses) {
        this.nextClasses = nextClasses;
    }
    public abstract ArrayList<RPGClass> isProgressionValid(Player player, PlayerStats playerStats);
}
