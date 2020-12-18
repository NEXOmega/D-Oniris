package dandelion.oniris.players.spell.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import org.bukkit.entity.Player;

public class Heal extends Spell {

    @Override
    public void launch(Player sender, Oniris main, PlayerStats data) {
        float vitTot = data.getMaxVitality();
        float heal = (vitTot - data.getHealth() > vitTot/5) ? (vitTot/5) : (float) (vitTot - data.getHealth());
        data.setHealth(data.getHealth() + heal);

        double vit = data.getHealth() / vitTot;
        data.updateHealthBar();

        /*NoFlexZone particules = new NoFlexZone(main.getEffectManager());
        particules.setEntity(sender);
        particules.iterations = 5;
        particules.type = EffectType.INSTANT;
        particules.particle = Particle.HEART;*/
        //particules.start();

    }
}
