package dandelion.oniris.commands.gui;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.SpellType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpellGui {

    int slot;
    public Inventory inv(Oniris oniris, Player player, PlayerStats playerStats) {
        Inventory inv = Bukkit.createInventory(player, 36, ChatColor.RED +"Spells");

        slot = 0;
        for(String str : playerStats.getSpells()) {
            SpellType spell = SpellType.valueOf(str.toUpperCase());
            ItemStack item = spell.getItemStack();
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setLore(spell.getLore());
            itemMeta.setDisplayName(ChatColor.RED +"" + ChatColor.BOLD + spell.getName());
            item.setItemMeta(itemMeta);

            inv.setItem(slot++,item);
        }

        return inv;
    }
}
