package dandelion.oniris.players.spell.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.LocationSpell;
import dandelion.oniris.players.spell.Spell;
import org.bukkit.Location;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.Random;

public class ArrowRain extends Spell implements LocationSpell {

    @Override
    public void launch(Player sender, Oniris oniris, PlayerStats data) {
        for (int i = 0; i < 10; i++) {
            double coords = i*Math.PI/180;
            Arrow arrow = sender.getWorld().spawnArrow(sender.getTargetBlock(null,25).getLocation().add(Math.cos(coords)+ (new Random().nextInt(4 + 1) -2), 5+ (new Random().nextInt(4 + 1) -2), Math.sin(coords)+ (new Random().nextInt(4 + 1) -2)), new Vector(0,-2,0), 2, 3);
            arrow.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
            arrow.setMetadata("damages", new FixedMetadataValue(oniris, data.getMaxAttack()/3));
        }
    }

    @Override
    public void launchFromLocation(Location location, Oniris oniris, PlayerStats stats) {

    }
}
