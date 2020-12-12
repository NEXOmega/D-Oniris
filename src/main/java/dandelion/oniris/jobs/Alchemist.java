package dandelion.oniris.jobs;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;

public class Alchemist extends Job {

    transient Gui alchimistGui = new Gui(Oniris.getInstance(), 2, "Create Potion");
    HashMap<String, ItemStack> ingredients = new HashMap<>();

    public void openGui(Player p, PlayerStats stats, ItemStack emptyPotion) {
        OutlinePane ingredientsPane = new OutlinePane(0,0,9,1);
        OutlinePane craftPane = new OutlinePane(0,1,9,1);

        PersistentDataContainer persistentDataContainer = emptyPotion.getItemMeta().getPersistentDataContainer();
        int slots = persistentDataContainer.get(NamespacedKey.minecraft("slot"), PersistentDataType.INTEGER);
        for(int i = slots; i < 9; i++) {
            ingredientsPane.addItem(new GuiItem(new ItemStack(Material.GLASS), e -> e.setCancelled(true)));
        }

        ingredientsPane.setOnClick(e -> e.setCancelled(true));
        craftPane.setOnClick(e -> e.setCancelled(true));

        craftPane.addItem(new GuiItem(new ItemStack(Material.EXPERIENCE_BOTTLE), e -> {
            int exp = 0;
            StringBuilder sb = new StringBuilder();
            for(ItemStack i : ingredients.values()) {
                if(i.getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("imbsupport"), PersistentDataType.TAG_CONTAINER)) {
                    PersistentDataContainer persistentDataContainer1 = i.getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("imbsupport"), PersistentDataType.TAG_CONTAINER);
                    String support = persistentDataContainer1.get(NamespacedKey.minecraft("support"), PersistentDataType.STRING);
                    int duration = persistentDataContainer1.get(NamespacedKey.minecraft("duration"), PersistentDataType.INTEGER);

                    sb.append(support+":"+duration+";");
                    exp += i.getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("exp"), PersistentDataType.INTEGER);
                }
            }
            ingredients.clear();
            ingredientsPane.clear();
            p.closeInventory();

            ItemMeta potionMeta = p.getInventory().getItemInMainHand().getItemMeta();
            potionMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("supportseffect"), PersistentDataType.STRING, sb.toString());
            potionMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("exp"), PersistentDataType.INTEGER, Math.round(exp/5));
            potionMeta.getPersistentDataContainer().remove(NamespacedKey.minecraft("slot"));

            p.getInventory().getItemInMainHand().setItemMeta(potionMeta);
            if(stats.getJobStats().getAlchemist().addExp(exp))
                p.sendMessage(ChatColor.YELLOW + "Bravo vous etes monté de niveau !");
            p.sendMessage("Vous avez gagné " + exp + " Exp");
            p.sendMessage(sb.toString());
        }));

        alchimistGui.setOnBottomClick(e-> {
            e.setCancelled(true);

            if(e.getCurrentItem() != null) {
                ItemStack clicked = e.getCurrentItem().clone();
                if(ingredients.containsKey(clicked.getType().name()+":"+clicked.getItemMeta().getDisplayName())) {
                    p.sendMessage(ChatColor.RED + "La potion contient déja cet ingrédient.");
                    return;
                }
                if(clicked.getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("imbsupport"), PersistentDataType.TAG_CONTAINER)) {
                    if(clicked.getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("requiredlevel"), PersistentDataType.INTEGER) <= stats.getJobStats().getAlchemist().getLevel()) {
                        clicked.setAmount(1);
                        ingredients.put(clicked.getType().name()+":"+clicked.getItemMeta().getDisplayName(), clicked);
                        ingredientsPane.addItem(new GuiItem(clicked, event -> {
                            GuiItem item = ingredientsPane.getItems().get(event.getSlot());
                            ingredientsPane.removeItem(item);
                            p.getInventory().addItem(ingredients.get(clicked.getType().name()+":"+clicked.getItemMeta().getDisplayName()));
                            ingredients.remove(clicked.getType().name()+":"+clicked.getItemMeta().getDisplayName());
                            alchimistGui.update();
                        }));
                        e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() -1);
                    } else {
                        p.sendMessage(ChatColor.RED + "Vous n'avez pas le niveau requis");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "L'ingredient ne peut pas etre mit dans une potion");
                }

                alchimistGui.update();
            }
        });

        alchimistGui.setOnClose(e -> {
            for(ItemStack item : ingredients.values()) {
                p.getInventory().addItem(item);
            }
            ingredients.clear();
            ingredientsPane.clear();
        });

        alchimistGui.addPane(ingredientsPane);
        alchimistGui.addPane(craftPane);

        alchimistGui.show(p);
    }

    public void potionExamination(Player p, PlayerStats stats) {
        Gui gui = new Gui(Oniris.getInstance(), 4, "Examination");
        gui.setOnGlobalClick(e -> e.setCancelled(true));
        OutlinePane potionPane = new OutlinePane(0,0,9,4);
        ArrayList<String> lore = new ArrayList<>();
        for(ItemStack potion : p.getInventory().getContents()) {
            if(potion != null) {
                if(potion.getType().equals(Material.POTION)) {
                    lore.clear();
                    ItemStack potionDisplay = potion.clone();
                    ItemMeta meta = potionDisplay.getItemMeta();
                    PersistentDataContainer pDataContainer = potionDisplay.getItemMeta().getPersistentDataContainer();
                    if(pDataContainer.has(NamespacedKey.minecraft("slot"), PersistentDataType.INTEGER))
                        lore.add(ChatColor.GREEN + "Slots : " + pDataContainer.get(NamespacedKey.minecraft("slot"), PersistentDataType.INTEGER));
                    if(pDataContainer.has(NamespacedKey.minecraft("heal"), PersistentDataType.STRING))
                        lore.add(ChatColor.GREEN + "Heal power : " + pDataContainer.get(NamespacedKey.minecraft("heal"), PersistentDataType.STRING));
                    if(pDataContainer.has(NamespacedKey.minecraft("exp"), PersistentDataType.INTEGER))
                        lore.add(ChatColor.GREEN + "Experience : " + pDataContainer.get(NamespacedKey.minecraft("exp"), PersistentDataType.INTEGER));
                    if(pDataContainer.has(NamespacedKey.minecraft("supportseffect"), PersistentDataType.STRING)){
                        String supports = pDataContainer.get(NamespacedKey.minecraft("supportseffect"), PersistentDataType.STRING);
                        for(String spdur : supports.split(";")) {
                            lore.add(ChatColor.GREEN + "Support : " + spdur.split(":")[0] + ", Duration : " + spdur.split(":")[1] +"s");
                        }
                    }
                    meta.setLore(lore);
                    potionDisplay.setItemMeta(meta);

                    potionPane.addItem(new GuiItem(potionDisplay));
                }
            }
        }

        gui.addPane(potionPane);
        gui.show(p);
    }
}
