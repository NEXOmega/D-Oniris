package dandelion.oniris.commands.gui;

import dandelion.oniris.players.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class EquipementGui {
    Inventory inventory;
    public Inventory inv(Player p, PlayerStats stats) {
        inventory = Bukkit.createInventory(p,36, ChatColor.RED + "Equipements");

        //Helmet
        inventory.setItem(1, (stats.getEquipements()[0] != null) ? stats.getEquipements()[0] : getItem(4));
        //Chestplate
        inventory.setItem(10, (stats.getEquipements()[1] != null) ? stats.getEquipements()[1] : getItem(3));
        //Leggings
        inventory.setItem(19, (stats.getEquipements()[2] != null) ? stats.getEquipements()[2] : getItem(5));
        //Boots
        inventory.setItem(28, (stats.getEquipements()[3] != null) ? stats.getEquipements()[3] : getItem(2));

        //Sword
        inventory.setItem(9, (stats.getEquipements()[4] != null) ? stats.getEquipements()[4] : getItem(8));

        //Belt
        inventory.setItem(18, (stats.getEquipements()[5] != null) ? stats.getEquipements()[5] : getItem(1));

        //Rings
        inventory.setItem(11, (stats.getEquipements()[6] != null) ? stats.getEquipements()[6] : getItem(7));
        inventory.setItem(20, (stats.getEquipements()[7] != null) ? stats.getEquipements()[7] : getItem(7));
        return inventory;
    }

    public ItemStack getItem(int slot) {
        ItemStack noStuff = new ItemStack(Material.CARROT_ON_A_STICK);
        ItemMeta meta = noStuff.getItemMeta();
        meta.setUnbreakable(true);
        meta.setCustomModelData(slot);
        noStuff.setItemMeta(meta);

        return  noStuff;
    }
}
