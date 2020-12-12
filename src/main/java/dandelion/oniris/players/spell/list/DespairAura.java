package dandelion.oniris.players.spell.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.LocationSpell;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.support.MagicSickness;
import dandelion.soundapi.sounds.DandelionSound;
import dandelion.soundapi.sounds.SoundZoneParams;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DespairAura extends Spell implements LocationSpell {

    @Override
    public void launch(Player sender, Oniris oniris, PlayerStats data) {
        sender.getNearbyEntities(5,5,5).forEach(entity -> {
            if(entity instanceof Player) {
                Player target = (Player) entity;
                DandelionSound sound = new DandelionSound("Apocalypse", "N", "ambient.apocalypse", 60);
                SoundZoneParams params = new SoundZoneParams("Apocalypse", "ambient.apocalypse", "void", SoundCategory.AMBIENT, 1,1,false,true,false);
                oniris.getSoundApi().getSoundManager().playSound(target, sound, params);
                target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30*20,1));
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60*20, 1));
                oniris.getPlayers().get(target).addBuff(new MagicSickness(), (long) (5*20));

            }
        });
    }

    @Override
    public void launchFromLocation(Location location, Oniris oniris, PlayerStats stats) {
        location.getWorld().getNearbyEntities(location,5,5,5).forEach(entity -> {
            if(entity instanceof Player) {
                Player target = (Player) entity;
                DandelionSound sound = new DandelionSound("Apocalypse", "N", "ambient.apocalypse", 60);
                SoundZoneParams params = new SoundZoneParams("Apocalypse", "ambient.apocalypse", "void", SoundCategory.AMBIENT, 1,1,false,true,false);
                oniris.getSoundApi().getSoundManager().playSound(target, sound, params);
                target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30*20,1));
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60*20, 1));
            }
        });
    }
}
