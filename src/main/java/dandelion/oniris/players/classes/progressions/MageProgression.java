package dandelion.oniris.players.classes.progressions;

import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.classes.ClassType;
import dandelion.oniris.players.classes.list.RPGClass;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class MageProgression extends ProgressionTree {

    public MageProgression() {
        super(new ArrayList<>(Collections.singleton(ClassType.PRIEST.getRpgClass())));
    }

    @Override
    public ArrayList<RPGClass> isProgressionValid(Player player, PlayerStats playerStats) {
        ArrayList<RPGClass> possibleClasses = new ArrayList<>();
        if(player.getLevel() >= 10 && playerStats.getMaxMagie() > 25) {
            possibleClasses.add(ClassType.PRIEST.getRpgClass());
        }
        return possibleClasses;
    }
}
