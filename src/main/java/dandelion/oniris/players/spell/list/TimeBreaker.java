package dandelion.oniris.players.spell.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class TimeBreaker extends Spell {

    @Override
    public void launch(Player sender, Oniris oniris, PlayerStats data) {
        List<Entity> entities = sender.getNearbyEntities(5,5,5);
        sender.sendMessage("e:"+entities.size() + " d:"+2*entities.size());
        entities.forEach(e -> {
            if (e instanceof LivingEntity) {
                ((LivingEntity)e).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 ,1));
                ((LivingEntity)e).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100 ,100));
                ((LivingEntity)e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40 ,10));
            }
        });

        sender.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*entities.size(), (data.getMaxMagie() < 100 ? (entities.size() > 0 ? 15/entities.size() : 1) : 15)));
        sender.addPotionEffect(new PotionEffect((data.getMaxMagie() < 50 ? PotionEffectType.GLOWING : PotionEffectType.INVISIBILITY), 20*entities.size(), 1));

    }
}
