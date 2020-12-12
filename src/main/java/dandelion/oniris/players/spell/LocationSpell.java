package dandelion.oniris.players.spell;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import org.bukkit.Location;

public interface LocationSpell {
    void launchFromLocation(Location location, Oniris oniris, PlayerStats stats);
}
