package dandelion.oniris.listerners;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.SpellType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class CustomCraftsEvent implements Listener {
    Oniris oniris;

    public CustomCraftsEvent(Oniris oniris) {
        this.oniris = oniris;
    }
    List<String> lore = new ArrayList<>();

    @EventHandler
    public void playerUseCCraft(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(e.getClickedBlock().getType() == Material.ENCHANTING_TABLE) {
                e.setCancelled(true);

                PlayerStats stats = oniris.getPlayers().get(e.getPlayer());
                ItemStack itemInHand = e.getPlayer().getInventory().getItemInMainHand();
                ItemStack borderSlotItem = new ItemStack(Material.CARROT_ON_A_STICK);
                ItemMeta metaBorder = borderSlotItem.getItemMeta();
                metaBorder.setCustomModelData(10);
                borderSlotItem.setItemMeta(metaBorder);

                if (itemInHand.getType().equals(Material.AIR)) {
                    e.getPlayer().sendMessage("Vous n'avez pas d'items a modifier.");
                    return;
                }
                //Learn by lore
                if (itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
                    for (String s : itemInHand.getItemMeta().getLore()) {
                        s = ChatColor.stripColor(s);
                        if (s.startsWith("Enseignement")) {

                            if (s.split(" ")[2].equalsIgnoreCase("support")) {

                                e.getPlayer().sendMessage("Vous avez appris la capacité de support : " + s.split(" ")[4]);
                            } else {
                                stats.getSpells().add(s.split(" ")[4]);
                                e.getPlayer().sendMessage("Vous avez appris le sort : " + s.split(" ")[4]);
                            }
                            itemInHand.setAmount(itemInHand.getAmount() - 1);
                        }
                    }
                }
                // Learn by Metadata
                if (itemInHand.hasItemMeta() && itemInHand.getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("toteachspell"), PersistentDataType.STRING)) {
                    String sSpell = itemInHand.getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("toteachspell"), PersistentDataType.STRING);
                    SpellType spell = SpellType.valueOf(sSpell.toUpperCase());
                    stats.getSpells().add(sSpell);
                    e.getPlayer().sendMessage(ChatColor.YELLOW + "Vous avez appris le sort : " + spell.getRank().getChatColor() + sSpell);
                    itemInHand.setAmount(itemInHand.getAmount() - 1);
                    return;
                }
                if (itemInHand.hasItemMeta() && itemInHand.getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("toteachsupport"), PersistentDataType.STRING)) {
                    //stats.getSpells().add(itemInHand.getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("toteachsupport"), PersistentDataType.STRING));
                    e.getPlayer().sendMessage("Apprendre le support");
                    return;
                }
                if (itemInHand.getType().equals(Material.FIREWORK_STAR)) {

                    if (!e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
                        e.getPlayer().sendMessage("Aucun sort ne peut être associé a cet objet.");
                        return;
                    }
                    if (!e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                        e.getPlayer().sendMessage("Aucun sort ne peut être associé a cet objet.");
                        return;
                    }
                    if (!ChatColor.stripColor(itemInHand.getItemMeta().getLore().get(0)).equalsIgnoreCase("Binding")) {
                        e.getPlayer().sendMessage("Aucun sort ne peut être associé a cet objet.");
                        return;
                    }

                    Gui gui = new Gui(oniris, 3, "Binding");
                    gui.setOnGlobalClick(event -> event.setCancelled(true));
                    OutlinePane spellpane = new OutlinePane(2, 0, 7, 3);

                    StaticPane staticPane = new StaticPane(0, 0, 2, 3);
                    GuiItem guiItem = new GuiItem(e.getPlayer().getInventory().getItemInMainHand());
                    staticPane.addItem(guiItem, 0, 0);
                    ItemStack arrow = new ItemStack(Material.CARROT_ON_A_STICK);
                    ItemMeta arrowMeta = arrow.getItemMeta();
                    arrowMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Transformation en");
                    arrowMeta.setCustomModelData(15);
                    arrow.setItemMeta(arrowMeta);
                    staticPane.addItem(new GuiItem(arrow), 0, 1);
                    for(int i = 0; i< 3; i++) {
                        staticPane.addItem(new GuiItem(borderSlotItem), 1, i);
                    }
                    for (String s : stats.getSpells()) {
                        SpellType spell = SpellType.valueOf(s.toUpperCase());
                        ItemStack item = spell.getItemStack();
                        ItemMeta itemMeta = item.getItemMeta();
                        lore.clear();
                        lore.add(ChatColor.WHITE + "Binding");
                        lore.addAll(spell.getLore());
                        itemMeta.setLore(lore);
                        itemMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + spell.getName());
                        item.setItemMeta(itemMeta);
                        spellpane.addItem(new GuiItem(item, event -> {

                            String name = spell.getName();
                            ItemMeta iMeta = item.getItemMeta();
                            iMeta.setDisplayName(ChatColor.RED + name);
                            iMeta.setCustomModelData(spell.getItemStack().getItemMeta().getCustomModelData());

                            guiItem.getItem().setItemMeta(e.getPlayer().getInventory().getItemInMainHand().getItemMeta());

                            ItemStack itemS = new ItemStack(Material.FIREWORK_STAR);
                            itemS.setItemMeta(iMeta);

                            staticPane.addItem(new GuiItem(itemS), 0, 2);
                            e.getPlayer().getInventory().getItemInMainHand().setItemMeta(iMeta);
                            gui.update();
                        }));
                    }

                    gui.addPane(staticPane);
                    gui.addPane(spellpane);

                    gui.show(e.getPlayer());
                }
            }
            else if(e.getClickedBlock().getType() == Material.SMITHING_TABLE) {
                e.getPlayer().sendMessage("Opening Smithing Table");
                e.setCancelled(true);

                PlayerStats playerStats = oniris.getPlayers().get(e.getPlayer());

                playerStats.getJobStats().getBlacksmith().openGui(e.getPlayer(), playerStats);
            } else if (e.getClickedBlock().getType() == Material.BREWING_STAND) {
                e.setCancelled(true);
                e.getPlayer().sendMessage("Opening Alchemy Table");

                PlayerStats playerStats = oniris.getPlayers().get(e.getPlayer());
                if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.POTION)) {
                    if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("slot"), PersistentDataType.INTEGER)) {
                        playerStats.getJobStats().getAlchemist().openGui(e.getPlayer(), playerStats, e.getPlayer().getInventory().getItemInMainHand());
                    } else {
                        e.getPlayer().sendMessage(ChatColor.RED + "Votre potion ne peux pas etre modifie.");
                    }
                } else {
                    e.getPlayer().sendMessage(ChatColor.RED + "Vous devez avoir une potion en main.");
                }
                ItemStack emptyBottle = new ItemStack(Material.POTION);
                ItemMeta meta = emptyBottle.getItemMeta();
                meta.getPersistentDataContainer().set(NamespacedKey.minecraft("slot"), PersistentDataType.INTEGER, 3);
                emptyBottle.setItemMeta(meta);


            }
        }

    }
}
