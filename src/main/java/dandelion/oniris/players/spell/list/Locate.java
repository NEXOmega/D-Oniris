package dandelion.oniris.players.spell.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Locate extends Spell {
    @Override
    public void launch(Player sender, Oniris oniris, PlayerStats data) {
        sender.getNearbyEntities(100,100,100).forEach(e -> {
            if(e instanceof Player) {
                sender.sendMessage(ChatColor.GREEN + "Quelqu'un est a " + ChatColor.YELLOW + Math.round(sender.getLocation().distance(e.getLocation())) + ChatColor.GREEN + " Block de vous");
            }
        });
    }
}
