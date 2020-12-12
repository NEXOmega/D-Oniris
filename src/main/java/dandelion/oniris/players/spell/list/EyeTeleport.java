package dandelion.oniris.players.spell.list;


import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import org.bukkit.entity.Player;

public class EyeTeleport extends Spell {

    @Override
    public void launch(Player sender, Oniris oniris, PlayerStats data) {
        if(sender.getLocation().distance(sender.getTargetBlock(null,25).getLocation()) < 25) {
            sender.teleport(sender.getTargetBlock(null,25).getLocation());
        }
    }
}
