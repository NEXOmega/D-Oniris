package dandelion.oniris.commands.gui;

import dandelion.oniris.Oniris;
import dandelion.oniris.utils.FonctionUtils;
import dandelion.oniris.utils.ItemType;
import dandelion.oniris.utils.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuGui {
    Oniris oniris;
    PortalStoneGui portalStoneGui = new PortalStoneGui();
    SpellGui spellGui = new SpellGui();
    StatsGui statsGui = new StatsGui();
    EquipementGui equipementGui = new EquipementGui();

    public MenuGui(Oniris oniris) {
        this.oniris = oniris;
    }

    public Inventory inv() {

        Inventory inv = Bukkit.createInventory(null,27,ChatColor.RED +"Menu");
        ItemStack noItem = new ItemStack(Material.WHITE_STAINED_GLASS_PANE,1,(byte)14);

        ItemStack statsMenu = new ItemStack(Material.IRON_SWORD);
        ItemMeta statsMeta = statsMenu.getItemMeta();
        statsMeta.setDisplayName(ChatColor.YELLOW +"Ouvrir le menu des stats");
        statsMenu.setItemMeta(statsMeta);

        inv.setItem(0,statsMenu);

        ItemStack spellList = new ItemStack(Material.PAPER);
        ItemMeta spellListMeta = spellList.getItemMeta();
        spellListMeta.setDisplayName(ChatColor.YELLOW +"Liste des sorts");
        spellList.setItemMeta(spellListMeta);

        inv.setItem(9,spellList);

        ItemStack equipementGui = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta equipementGuiMeta = equipementGui.getItemMeta();
        equipementGuiMeta.setDisplayName(ChatColor.RED +"Ouvrir le Gui des Equipements");
        equipementGui.setItemMeta(equipementGuiMeta);

        inv.setItem(18,equipementGui);

        ItemStack progressGui = new ItemStack(Material.BOOK);
        ItemMeta progressGuiMeta = equipementGui.getItemMeta();
        progressGuiMeta.setDisplayName(ChatColor.YELLOW +"Ouvrir le Gui de progression");
        progressGui.setItemMeta(progressGuiMeta);

        inv.setItem(1,progressGui);

        ItemStack jobGui = FonctionUtils.createItem(ChatColor.GREEN + "Ouvrir le menu des jobs", Material.DIAMOND_AXE, ItemType.DISPLAY_ITEM, Ranks.COMMON, 1, 0).getItemStack();
        inv.setItem(10, jobGui);

        for(int i = 0; i < 27; i++) {
            if(inv.getItem(i) == null) inv.setItem(i,noItem);
        }
        return inv;
    }

    public EquipementGui getEquipementGui() {
        return equipementGui;
    }

    public StatsGui getStatsGui() {
        return statsGui;
    }

    public SpellGui getSpellGui() {
        return spellGui;
    }

    public PortalStoneGui getPortalStoneGui() {
        return portalStoneGui;
    }
}
