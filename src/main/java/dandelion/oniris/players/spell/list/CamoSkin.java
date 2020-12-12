package dandelion.oniris.players.spell.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CamoSkin extends Spell {
    @Override
    public void launch(Player sender, Oniris oniris, PlayerStats data) {
        sender.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 30*20,3, false, false, true), false);
    }
}
