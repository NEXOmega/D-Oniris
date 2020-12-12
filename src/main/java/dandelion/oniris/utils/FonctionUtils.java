package dandelion.oniris.utils;

import dandelion.oniris.commands.equipemetns.WeaponsTypes;
import dandelion.oniris.players.Stats;
import dandelion.oniris.players.spell.Elements;
import dandelion.oniris.players.support.Support;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FonctionUtils {
    public static String capitalize(String str) {
        if(str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static DandelionItem createItem(String name, Material material, ItemType itemType, Ranks ranks, int amount, int modelData) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setCustomModelData(modelData);
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("itemtype"), PersistentDataType.STRING, itemType.name().toUpperCase());
        item.setAmount(amount);
        item.setItemMeta(meta);

        return new DandelionItem(item, name, null,null, null,null, itemType, ranks,null,null,null);
    }

    public static DandelionItem createItem(String name, Material material, ItemType itemType, Ranks ranks, int amount, int modelData, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.setCustomModelData(modelData);
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("itemtype"), PersistentDataType.STRING, itemType.name().toUpperCase());
        item.setAmount(amount);
        item.setItemMeta(meta);

        return new DandelionItem(item, name,new ArrayList<>(lore),null, null,null, itemType, ranks,null,null,null);
    }

    public static DandelionItem createItem(String name, Material material, ItemType itemType, Ranks ranks, int amount, int modelData, List<String> lore, HashMap<String, String> data) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.setCustomModelData(modelData);
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("itemtype"), PersistentDataType.STRING, itemType.name().toUpperCase());
        item.setAmount(amount);
        item.setItemMeta(meta);

        data.forEach((string, d) -> {
            item.getItemMeta().getPersistentDataContainer().set(NamespacedKey.minecraft(string), PersistentDataType.STRING, d);
        });

        return new DandelionItem(item, name, new ArrayList<>(lore),null, null,null, itemType, ranks,null,null,null);
    }

    public static String getDirection(Location pos1, Location pos2) {
        if(pos1.getX() > pos1.getX() && pos2.getZ() > pos1.getZ()) {
            return "Up Left";
        } else if(pos1.getX() > pos1.getX() && pos2.getZ() < pos1.getZ()) {
            return "Up Right";
        } else if(pos1.getX() < pos1.getX() && pos2.getZ() > pos1.getZ()) {
            return "Down Left";
        } else if(pos1.getX() < pos1.getX() && pos2.getZ() < pos1.getZ()) {
            return "Down Right";
        }
        return "Wtf Dude";
    }

    public static class StatsBuilder {
        float strength = 0, vitality = 0, magie = 0, defence = 0, speed = 0;

        public StatsBuilder setStrength(float strength) {
            this.strength = strength;
            return this;
        }

        public StatsBuilder setVitality(float vitality) {
            this.vitality = vitality;
            return this;
        }

        public StatsBuilder setMagie(float magie) {
            this.magie = magie;
            return this;
        }

        public StatsBuilder setDefence(float defence) {
            this.defence = defence;
            return this;
        }

        public StatsBuilder setSpeed(float speed) {
            this.speed = speed;
            return this;
        }

        public Stats build() {
            return new Stats(strength, vitality, magie, defence, speed);
        }
    }

    public static class DandelionItemBuilder {
        ItemStack itemStack;
        String name;
        ArrayList<String> description;
        Stats stats;
        Support support;
        WeaponsTypes types;
        ItemType itemType;
        Ranks ranks;
        Elements elements;
        Integer level;
        String spellList;

        public void setItemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(ArrayList<String> description) {
            this.description = description;
        }

        public void setStats(Stats stats) {
            this.stats = stats;
        }

        public void setSupport(Support support) {
            this.support = support;
        }

        public void setTypes(WeaponsTypes types) {
            this.types = types;
        }

        public void setItemType(ItemType itemType) {
            this.itemType = itemType;
        }

        public void setRanks(Ranks ranks) {
            this.ranks = ranks;
        }

        public void setElements(Elements elements) {
            this.elements = elements;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public void setSpellList(String spellList) {
            this.spellList = spellList;
        }

        public DandelionItem build() {
            return new DandelionItem(itemStack,name,description,stats,support,types,itemType, ranks, elements, level, spellList);
        }
    }
}
