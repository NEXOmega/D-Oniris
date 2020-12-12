package dandelion.oniris.players.spell.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Propulse extends Spell {

    @Override
    public void launch(Player sender, Oniris main, PlayerStats data) {
        Location dir = sender.getTargetBlock(null,25).getLocation();

        sender.setVelocity(new Vector((dir.getX() - sender.getLocation().getX()), (dir.getY() - sender.getLocation().getY()), (dir.getZ() - sender.getLocation().getZ())));
    }
}
