package dandelion.oniris.players.classes.progressions;

import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.classes.ClassType;
import dandelion.oniris.players.classes.list.RPGClass;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class BerserkProgression extends ProgressionTree{

    public BerserkProgression() {
        super(new ArrayList<>(Collections.singletonList(ClassType.CHAMPION.getRpgClass())));
    }

    @Override
    public ArrayList<RPGClass> isProgressionValid(Player player, PlayerStats stats) {
        ArrayList<RPGClass> possibleClasses = new ArrayList<>();
        if(player.getLevel() >= 10 && stats.getMaxAttack() > 25) {
            possibleClasses.add(ClassType.CHAMPION.getRpgClass());
        }
        return possibleClasses;
    }
}
