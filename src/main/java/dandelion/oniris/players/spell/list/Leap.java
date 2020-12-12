package dandelion.oniris.players.spell.list;


import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.LocationSpell;
import dandelion.oniris.players.spell.Spell;
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
            EquationEffect effect = new EquationEffect(main.getEffectManager());
            effect.particle = Particle.REDSTONE;
            effect.xEquation = "4*sin(t)";
            effect.yEquation = "cos(t * rand(-4,5) + 32";
            effect.zEquation = "tan(t)";
            effect.duration = 7000;
            effect.particleCount = 50;
            effect.cycleMiniStep = true;
            effect.orient = true;
            effect.orientPitch = true;
            effect.color = Color.AQUA;
            effect.setEntity(sender);

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
