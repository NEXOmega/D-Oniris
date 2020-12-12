package dandelion.oniris.players.classes.progressions;

import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.classes.ClassType;
import dandelion.oniris.players.classes.list.RPGClass;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class VillagerProgression extends ProgressionTree {
    public VillagerProgression() {
        super(new ArrayList<>(Arrays.asList(ClassType.BERSERK.getRpgClass(), ClassType.MAGICIEN.getRpgClass(), ClassType.ARCHER.getRpgClass())));
    }

    @Override
    public ArrayList<RPGClass> isProgressionValid(Player player, PlayerStats playerStats) {
        return this.nextClasses;
    }
}
