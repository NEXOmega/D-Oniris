package dandelion.oniris.world;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import org.bukkit.entity.Player;

public class TickManager implements Runnable {
    Oniris oniris;

    public TickManager(Oniris oniris) {
        this.oniris = oniris;
    }

    @Override
    public void run() {
        for (Player p : oniris.getPlayers().keySet()) {
            PlayerStats stats = oniris.getPlayers().get(p);
            stats.onTick();
        }
    }
}
