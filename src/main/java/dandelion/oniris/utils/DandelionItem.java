package dandelion.oniris.utils;

import dandelion.oniris.commands.equipemetns.WeaponsTypes;
import dandelion.oniris.players.Stats;
import dandelion.oniris.players.spell.Elements;
import dandelion.oniris.players.support.Support;
import jdk.internal.jline.internal.Nullable;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class DandelionItem {
    ItemStack itemStack;
    ItemMeta meta;
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

    public DandelionItem(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.meta = itemStack.getItemMeta();
    }

    public DandelionItem(ItemStack itemStack, String name, @Nullable ArrayList<String> description,@Nullable Stats stats,@Nullable Support support,@Nullable WeaponsTypes types, ItemType itemType, Ranks ranks,@Nullable Elements elements,@Nullable Integer level,@Nullable String spellList) {
        this.itemStack = itemStack;
        meta = itemStack.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        this.name = name;
        meta.setDisplayName(ranks.chatColor + name + (level != null ? "["+level+"]" : ""));
        data.set(NamespacedKey.minecraft("name"), PersistentDataType.STRING, name);
        if(description != null) {
            this.description = description;
            meta.setLore(description);
        }


        if(stats != null) {
            this.stats = stats;
            data.set(NamespacedKey.minecraft("strength"), PersistentDataType.FLOAT, stats.getStrength());
            data.set(NamespacedKey.minecraft("defence"), PersistentDataType.FLOAT, stats.getDefence());
            data.set(NamespacedKey.minecraft("magie"), PersistentDataType.FLOAT, stats.getMagie());
            data.set(NamespacedKey.minecraft("speed"), PersistentDataType.FLOAT, stats.getSpeed());
            data.set(NamespacedKey.minecraft("vitality"), PersistentDataType.FLOAT, stats.getVitality());
        }

        if(support != null) {
            this.support = support;
            data.set(NamespacedKey.minecraft("support"), PersistentDataType.STRING, support.getName().toUpperCase());
        }

        if(types != null) {
            this.types = types;
            data.set(NamespacedKey.minecraft("weapon"), PersistentDataType.STRING, types.name());
        }

        if(itemType != null) {
            this.itemType = itemType;
            data.set(NamespacedKey.minecraft("itemtype"), PersistentDataType.STRING, itemType.name());
        }

        if(ranks != null) {
            this.ranks = ranks;
            data.set(NamespacedKey.minecraft("rank"), PersistentDataType.STRING, ranks.name());
        }

        if(elements != null) {
            this.elements = elements;
            data.set(NamespacedKey.minecraft("element"), PersistentDataType.STRING, elements.name());
        }

        if(level != null) {
            this.level = level;
            data.set(NamespacedKey.minecraft("level"), PersistentDataType.INTEGER, level);
        }

        if(spellList != null) {
            this.spellList = spellList;
            data.set(NamespacedKey.minecraft("spelllist"), PersistentDataType.STRING,spellList);
        }
        itemStack.setItemMeta(meta);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        meta.setDisplayName(ranks.chatColor + name + (level != null ? "["+level+"]" : ""));
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("strength"), PersistentDataType.FLOAT, stats.getStrength());
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("defence"), PersistentDataType.FLOAT, stats.getDefence());
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("magie"), PersistentDataType.FLOAT, stats.getMagie());
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("speed"), PersistentDataType.FLOAT, stats.getSpeed());
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("vitality"), PersistentDataType.FLOAT, stats.getVitality());
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("support"), PersistentDataType.STRING, support.getName().toUpperCase());
    }

    public WeaponsTypes getWeaponType() {
        return types;
    }

    public void setWeaponType(WeaponsTypes types) {
        this.types = types;
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("weapon"), PersistentDataType.STRING, types.name());
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("itemtype"), PersistentDataType.STRING, itemType.name());
    }

    public Ranks getRanks() {
        return ranks;
    }

    public void setRanks(Ranks ranks) {
        this.ranks = ranks;
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("rank"), PersistentDataType.STRING, ranks.name());
    }

    public Elements getElements() {
        return elements;
    }

    public void setElements(Elements elements) {
        this.elements = elements;
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("element"), PersistentDataType.STRING, elements.name());
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("level"), PersistentDataType.INTEGER, level);
    }

    public String getSpellList() {
        return spellList;
    }

    public void setSpellList(String spellList) {
        this.spellList = spellList;
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("spelllist"), PersistentDataType.STRING,spellList);
    }

    public void addSpell(String spellName) {
        setSpellList(getSpellList()+spellName+";");
    }

    public void removeSpell(String spellName) {
        setSpellList(getSpellList().replace(spellName+";", ""));
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemMeta getMeta() {
        return meta;
    }

    public void setMeta(ItemMeta meta) {
        this.meta = meta;
    }

    public ItemStack getItemStack() {
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
