package dandelion.oniris.players.spell.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
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
                data.getBar().setProgress(vit);

                ent.sendMessage("§eHealed By : " + sender.getName());
            }
        }
    }
}
