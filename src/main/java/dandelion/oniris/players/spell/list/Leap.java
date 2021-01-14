package dandelion.oniris.players.spell.list;


import dandelion.oniris.Oniris;
import dandelion.oniris.particles.Ring;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.LocationSpell;
import dandelion.oniris.players.spell.Spell;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.EquationEffect;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Leap extends Spell implements LocationSpell {

    @Override
    public void launch(Player sender, Oniris main, PlayerStats data) {
        if(sender.isOnGround()) {
            sender.setVelocity(sender.getVelocity().add(new Vector(0,1.5,0)));
            Ring effect = new Ring(main.getEffectManager());
            effect.particle = Particle.SMOKE_NORMAL;
            effect.type = EffectType.INSTANT;
            effect.iterations = 5;
            effect.setLocation(sender.getLocation().add(0,1,0));

            effect.start();

        }
        else
            sender.setVelocity(sender.getVelocity().add(new Vector(0,-1.5,0)));
    }

    @Override
    public void launchFromLocation(Location location, Oniris oniris, PlayerStats stats) {
        location.getWorld().getNearbyEntities(location, 5,5,5).forEach(e -> {
            if(e instanceof LivingEntity) {
                e.setVelocity(e.getVelocity().add(new Vector(0,1.5,0)));
            }
        });
    }
}
