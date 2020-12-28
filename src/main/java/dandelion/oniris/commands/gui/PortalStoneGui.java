package dandelion.oniris.commands.gui;

import dandelion.oniris.players.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PortalStoneGui {

    public Inventory inv(Player player, PlayerStats stats) {
        Inventory inv = Bukkit.createInventory(player,9, ChatColor.RED +"Portal Stone");

        stats.getPortalStones().forEach(stone -> {
            inv.addItem(stone.getItem());
        });

        return inv;
    }

}
