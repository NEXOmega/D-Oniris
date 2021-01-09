package dandelion.oniris.players.spell.list;


import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.AnimatedBallEffect;
import de.slikey.effectlib.effect.WarpEffect;
import org.bukkit.entity.Player;

public class EyeTeleport extends Spell {

    @Override
    public void launch(Player sender, Oniris oniris, PlayerStats data) {
        if(sender.getLocation().distance(sender.getTargetBlock(null,25).getLocation()) < 25) {

            AnimatedBallEffect teleportEffect = new AnimatedBallEffect(oniris.getEffectManager());
            teleportEffect.setLocation(sender.getLocation());
            teleportEffect.iterations = 20;

            teleportEffect.start();

            sender.teleport(sender.getTargetBlock(null,25).getLocation());
        }
    }
}
