package dandelion.oniris.players.classes.progressions;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.advancements.ClassesAdvancements;
import dandelion.oniris.players.classes.ClassType;
import dandelion.oniris.players.classes.list.RPGClass;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class ArcherProgression extends ProgressionTree {
    public ArcherProgression() {
        super(new ArrayList<>(Collections.singletonList(ClassType.HUNTER.getRpgClass())));
    }

    @Override
    public ArrayList<RPGClass> isProgressionValid(Player player, PlayerStats playerStats) {
        ArrayList<RPGClass> possibleClasses = new ArrayList<>();
        if(player.getLevel() >= 5 && playerStats.getSpells().contains("camoskin") && playerStats.getMaxSpeed() >= 15) {
            Oniris.getInstance().progressAdvancement(player, ClassesAdvancements.HUNTER.getAdvancement(), 3);
            possibleClasses.add(ClassType.HUNTER.getRpgClass());
        }
        return possibleClasses;
    }
}
