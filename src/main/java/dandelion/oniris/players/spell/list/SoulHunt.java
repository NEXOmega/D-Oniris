package dandelion.oniris.players.spell.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import org.bukkit.ChatColor;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SoulHunt extends Spell {

    @Override
    public void launch(Player sender, Oniris oniris, PlayerStats data) {
        for (Entity e: sender.getNearbyEntities(100,100,100)) {
            if(e instanceof Player) {
                EnderSignal enderSignal = sender.getWorld().spawn(sender.getLocation().add(0,10,0), EnderSignal.class);
                enderSignal.setTargetLocation(e.getLocation());
                enderSignal.setDropItem(false);
                if(oniris.getPlayers().get(e).getMaxMagie() > (data.getMaxMagie()*1.5)) {
                    e.sendMessage(ChatColor.RED + "Attention, " + sender.getDisplayName() + " vous a repéré.");
                }
            }
        }
    }
}
