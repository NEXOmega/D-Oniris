package dandelion.oniris.utils;

import dandelion.oniris.commands.equipemetns.WeaponsTypes;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.Stats;
import dandelion.oniris.players.spell.Elements;
import dandelion.oniris.players.support.SupportType;
import jdk.internal.jline.internal.Nullable;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.bukkit.ChatColor.*;

public enum ItemUtils {
    BRONZE_COIN(FonctionUtils.createItem(GREEN+"Piece De Bronze", Material.INK_SAC,ItemType.MONEY, Ranks.COMMON,1,1, Arrays.asList(ChatColor.GREEN + "-Argent-", GRAY+"Valeur : 1 Bronze")), "Piece de niveau 1"),
    ARGENT_COIN(FonctionUtils.createItem(GRAY+"Piece D'Argent", Material.INK_SAC,ItemType.MONEY, Ranks.COMMON,1,1, Arrays.asList(ChatColor.GREEN + "-Argent-", GRAY+"Valeur : 64 Bronze")), "Piece de niveau 2"),
    GOLD_COIN(FonctionUtils.createItem(GOLD+"Piece D'Or", Material.INK_SAC,ItemType.MONEY, Ranks.COMMON,1,1, Arrays.asList(ChatColor.GREEN + "-Argent-", GRAY+"Valeur : 4096 Bronze")), "Piece de niveau 1"),
    GRIMOIRE(FonctionUtils.createItem("Grimoire", Material.BOOK,ItemType.GRIMORY, Ranks.COMMON, 1, 1), "Grimoire de base pour vos sorts"),
    BINDER(FonctionUtils.createItem("Binder", Material.FIREWORK_STAR,ItemType.BINDER, Ranks.COMMON,1,1, Collections.singletonList(WHITE+"Binding")), "Item to bind");

    DandelionItem item;
    String description;

    ItemUtils(DandelionItem item, String description) {
        this.item = item;
        this.description = description;
    }

    public DandelionItem getItem() {
        return item;
    }

    public String getDescription() {
        return description;
    }

    public static void potionManager(Player p, PlayerStats stats, ItemStack item, PotionData data) {
        if(item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("heal"), PersistentDataType.STRING)) {
            int healed = Integer.parseInt(item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("heal"), PersistentDataType.STRING));
            stats.setHealth(stats.getHealth()  + healed);

            double vit = stats.getHealth() / stats.getMaxVitality();
            stats.getBar().setProgress(vit);
        }

        if(item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("supportseffect"), PersistentDataType.STRING)) {
            String supports = item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("supportseffect"), PersistentDataType.STRING);
            for(String spdur : supports.split(";")) {
                System.out.println(spdur);
                stats.addBuff(SupportType.valueOf(spdur.split(":")[0]).getSupport(), Long.parseLong(spdur.split(":")[1])*1000);
            }

            int expGained = item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("exp"), PersistentDataType.INTEGER);
            if(stats.getJobStats().getAlchemist().addExp(expGained))
                p.sendMessage(ChatColor.YELLOW + "Bravo vous etes monté de niveau !");
            p.sendMessage(YELLOW + "Vous avez gagné " + expGained + " Exp en buvant la potion !");
        }
    }

    public static ArrayList<String> lore = new ArrayList<>();

    public static DandelionItem createRPGItem(DandelionItem item, Integer level, @Nullable WeaponsTypes type, @Nullable Elements elements, @Nullable SupportType support, int atk, int def, int spd, int vit, int magie) {
        ItemStack itemStack = item.getItemStack();
        ItemMeta meta = itemStack.getItemMeta();

        if (!meta.hasLore()) {
            lore.clear();
            lore.add(ChatColor.AQUA + "----" + ChatColor.RED + " Attributs " + ChatColor.AQUA + "----");
            meta.setLore(lore);
        } else {
            lore = (ArrayList<String>) meta.getLore();
            if(!lore.contains(ChatColor.AQUA + "----" + ChatColor.RED + " Attributs " + ChatColor.AQUA + "----")) {
                lore.add(ChatColor.AQUA + "----" + ChatColor.RED + " Attributs " + ChatColor.AQUA + "----");
                meta.setLore(lore);
            }
        }

        meta.setLore(lore);
        meta.setUnbreakable(true);
        itemStack.setItemMeta(meta);

        item.setItemStack(itemStack);
        item.setMeta(meta);
        item.setLevel(level);

        if(type != null)
            item.setWeaponType(type);
        if(elements != null)
            item.setElements(elements);
        if(support != null)
            item.setSupport(support.getSupport());

        item.setStats(new Stats(atk, vit, magie, def, spd));

        return item;
    }

    public static DandelionItem getDandelionItem(ItemStack itemStack) {
        PersistentDataContainer data = itemStack.getItemMeta().getPersistentDataContainer();
        DandelionItem item = new DandelionItem(itemStack);

        FonctionUtils.StatsBuilder builder = new FonctionUtils.StatsBuilder();
        if(data.has(NamespacedKey.minecraft("strength"), PersistentDataType.INTEGER))
            builder.setStrength(data.get(NamespacedKey.minecraft("strength"), PersistentDataType.INTEGER));

        if(data.has(NamespacedKey.minecraft("defence"), PersistentDataType.INTEGER))
            builder.setDefence(data.get(NamespacedKey.minecraft("defence"), PersistentDataType.INTEGER));

        if(data.has(NamespacedKey.minecraft("magie"), PersistentDataType.INTEGER))
            builder.setMagie(data.get(NamespacedKey.minecraft("magie"), PersistentDataType.INTEGER));

        if(data.has(NamespacedKey.minecraft("speed"), PersistentDataType.INTEGER))
            builder.setSpeed(data.get(NamespacedKey.minecraft("speed"), PersistentDataType.INTEGER));

        if(data.has(NamespacedKey.minecraft("vitality"), PersistentDataType.INTEGER))
            builder.setVitality(data.get(NamespacedKey.minecraft("vitality"), PersistentDataType.INTEGER));

        item.setStats(builder.build());


        if(data.has(NamespacedKey.minecraft("name"), PersistentDataType.STRING))
            item.setName(data.get(NamespacedKey.minecraft("name"), PersistentDataType.STRING));

        if(data.has(NamespacedKey.minecraft("weapon"), PersistentDataType.STRING))
            item.setWeaponType(WeaponsTypes.valueOf(data.get(NamespacedKey.minecraft("weapon"), PersistentDataType.STRING).toUpperCase()));

        if(data.has(NamespacedKey.minecraft("itemtype"), PersistentDataType.STRING))
            item.setItemType(ItemType.valueOf(data.get(NamespacedKey.minecraft("itemtype"), PersistentDataType.STRING).toUpperCase()));

        if(data.has(NamespacedKey.minecraft("rarity"), PersistentDataType.STRING))
            item.setRanks(Ranks.valueOf(data.get(NamespacedKey.minecraft("rarity"), PersistentDataType.STRING).toUpperCase()));

        if(data.has(NamespacedKey.minecraft("element"), PersistentDataType.STRING))
            item.setElements(Elements.valueOf(data.get(NamespacedKey.minecraft("element"), PersistentDataType.STRING).toUpperCase()));

        if(data.has(NamespacedKey.minecraft("support"), PersistentDataType.STRING))
            item.setSupport(SupportType.valueOf(data.get(NamespacedKey.minecraft("support"), PersistentDataType.STRING).toUpperCase()).getSupport());

        if(data.has(NamespacedKey.minecraft("level"), PersistentDataType.INTEGER))
            item.setLevel(data.get(NamespacedKey.minecraft("level"), PersistentDataType.INTEGER));

        return item;
    }
}
