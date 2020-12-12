package dandelion.oniris.players.spell.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.LocationSpell;
import dandelion.oniris.players.spell.Spell;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ForcedRest extends Spell implements LocationSpell {

    @Override
    public void launch(Player sender, Oniris oniris, PlayerStats data) {
        sender.getNearbyEntities(15,15,15).forEach(e -> {
            if(e instanceof LivingEntity) {
                ((LivingEntity)e).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 1));
                ((LivingEntity)e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 25));
            }
        });
    }

    @Override
    public void launchFromLocation(Location location, Oniris oniris, PlayerStats stats) {
        location.getWorld().getNearbyEntities(location, 5,5,5).forEach(e -> {
            if(e instanceof LivingEntity) {
                ((LivingEntity)e).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 1));
                ((LivingEntity)e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 25));
            }
        });
    }
}
