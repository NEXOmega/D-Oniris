package dandelion.oniris.players.spell.list;


import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Random;


public class Judgement extends Spell {

    @Override
    public void launch(Player sender, Oniris main, PlayerStats data) {
        sender.getNearbyEntities(5,5,5).forEach(e -> {
            if(!(e instanceof  Player) && e instanceof LivingEntity) {
                Random rand = new Random();

                if(rand.ints(0,5).findFirst().getAsInt() == 0) {
                    ((LivingEntity) e).damage((((LivingEntity) e).getHealth() /3)*2);
                    sender.sendMessage("§eDamaged : §r" + e.getName());
                }
            }
        });

    }
}
