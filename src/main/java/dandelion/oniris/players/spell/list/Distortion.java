package dandelion.oniris.players.spell.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Balise;
import dandelion.oniris.players.spell.Spell;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class Distortion extends Spell {

    //TODO Corriger le sort
    @Override
    public void launch(Player sender, Oniris oniris, PlayerStats data) {
        Balise balise = new Balise(sender, 10) {
            HashMap<Player, Location> backing = new HashMap<>();
            @Override
            public void onSummon(Entity entity) {
                for(Entity victim : entity.getNearbyEntities(5,5,5)) {
                    if(victim instanceof Player)
                        backing.put((Player) victim, victim.getLocation());
                }
                backing.remove(sender);
            }

            @Override
            public void onDespawn(Entity entity) {
                backing.forEach((p, l) -> {
                    p.teleport(backing.get(p));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 1));
                });
            }
        };
        balise.summon();
    }

}
