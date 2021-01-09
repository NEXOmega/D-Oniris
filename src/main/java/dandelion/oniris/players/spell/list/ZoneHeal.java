package dandelion.oniris.players.spell.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.particles.Ring;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import de.slikey.effectlib.EffectType;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ZoneHeal extends Spell {

    @Override
    public void launch(Player sender, Oniris main, PlayerStats data) {
        for(Entity ent : sender.getNearbyEntities(10,10,10)) {
            if(ent instanceof Player) {
                data = main.getPlayers().get(ent);
                float vitTot = data.getMaxVitality();
                float heal = ((vitTot / 5) >= vitTot) ? (int)data.getHealth() + ((vitTot / 5) * 5) : vitTot;
                data.setHealth(heal);
                double vit = data.getHealth() / (data.getStats().getVitality() + data.getClassType().getStats().getVitality());
                data.updateHealthBar();

                Ring particules = new Ring(main.getEffectManager());
                particules.setEntity(ent);
                particules.iterations = 5;
                particules.type = EffectType.INSTANT;
                particules.particle = Particle.HEART;
                particules.start();

                ent.sendMessage("§eHealed By : " + sender.getName());
            }
        }
    }
}
