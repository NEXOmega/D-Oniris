package dandelion.oniris.players.spell.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.ImpregnableSpell;
import dandelion.oniris.players.spell.LocationSpell;
import dandelion.oniris.players.spell.Spell;
import de.slikey.effectlib.effect.SphereEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class Fireball extends Spell implements LocationSpell, ImpregnableSpell {

    @Override
    public void launch(Player sender, Oniris oniris, PlayerStats data) {
        sender.sendMessage("§eFireball Launched");
        SmallFireball ball = sender.launchProjectile(SmallFireball.class);
        ball.setMetadata("damages", new FixedMetadataValue(oniris, 5));
        ball.setShooter(sender);
        ball.setIsIncendiary(false);

        SphereEffect sphereEffect = new SphereEffect(oniris.getEffectManager());
        sphereEffect.radius = 1;
        sphereEffect.particle = Particle.FLAME;
        sphereEffect.setEntity(ball);
        sphereEffect.start();
    }

    @Override
    public void launchFromLocation(Location location, Oniris oniris, PlayerStats stats) {
        SmallFireball fireball = (SmallFireball) location.getWorld().spawnEntity(location.add(0,5,0), EntityType.SMALL_FIREBALL);
        fireball.setDirection(new Vector(0,-1,0));
        fireball.setIsIncendiary(false);
        location.getWorld().getNearbyEntities(location,5,5,5).forEach(e -> {
        });
    }
}
