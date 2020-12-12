package dandelion.oniris.commands.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class StatsGui {
    public Inventory inv() {
        ArrayList<String> lore = new ArrayList<>();
        Inventory inv = Bukkit.createInventory(null, 9,ChatColor.RED + "Stats");

        ItemStack str = new ItemStack(Material.DIAMOND_SWORD,1);
        ItemMeta swordM = str.getItemMeta();
        swordM.setDisplayName(ChatColor.RED + "" +ChatColor.BOLD + "Force");
        lore.add(ChatColor.YELLOW + "Ajoute 1 point a votre force.");
        swordM.setLore(lore);
        lore.clear();

        str.setItemMeta(swordM);

        ItemStack def = new ItemStack(Material.SHIELD,1);
        ItemMeta shieldM = def.getItemMeta();
        shieldM.setDisplayName(ChatColor.RED + "" +ChatColor.BOLD + "Defence");
        lore.add(ChatColor.YELLOW + "Ajoute 1 point a votre defence.");
        shieldM.setLore(lore);
        lore.clear();

        def.setItemMeta(shieldM);

        ItemStack vit = new ItemStack(Material.APPLE,1);
        ItemMeta lifeM = vit.getItemMeta();
        lifeM.setDisplayName(ChatColor.RED + "" +ChatColor.BOLD + "Vitality");
        lore.add(ChatColor.YELLOW + "Ajoute 1 point a votre vitalite.");
        lifeM.setLore(lore);
        lore.clear();

        vit.setItemMeta(lifeM);

        ItemStack spd = new ItemStack(Material.DIAMOND_BOOTS,1);
        ItemMeta bootM = spd.getItemMeta();
        bootM.setDisplayName(ChatColor.RED + "" +ChatColor.BOLD + "Speed");
        lore.add(ChatColor.YELLOW + "Ajoute 1 point a votre force.");
        bootM.setLore(lore);
        lore.clear();

        spd.setItemMeta(bootM);

        //TODO Faire les infos
        ItemStack infos = new ItemStack(Material.STICK,1);
        ItemMeta infosM = str.getItemMeta();
        infosM.setDisplayName(ChatColor.RED + "" +ChatColor.BOLD + "Infos");
        lore.add(ChatColor.YELLOW + "Informations : .");
        infosM.setLore(lore);
        lore.clear();

        infos.setItemMeta(infosM);

        inv.setItem(0,str);
        inv.setItem(2,def);
        inv.setItem(4,vit);
        inv.setItem(6,spd);

        inv.setItem(8,infos);

        return inv;
    }
}
