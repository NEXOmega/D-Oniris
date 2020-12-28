package dandelion.oniris.players;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class PortalStone {
    String locName;
    ItemStack item;
    Location location;

    public PortalStone(String locName, Material mat, Location location) {
        this.locName = locName;
        this.item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.RED +"X:" +ChatColor.YELLOW + (int)location.getX() + ChatColor.RED +" Y:" + ChatColor.YELLOW +(int)location.getY() + ChatColor.RED +" Z:" + ChatColor.YELLOW +(int)location.getZ());
        meta.setLore(lore);
        meta.setDisplayName(locName);

        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("x"), PersistentDataType.INTEGER, (int)location.getX());
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("y"), PersistentDataType.INTEGER, (int)location.getY());
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("z"), PersistentDataType.INTEGER, (int)location.getZ());
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("world"), PersistentDataType.STRING, location.getWorld().getName());

        item.setItemMeta(meta);
        this.location = location;
    }

    public ItemStack getItem() {
        return item;
    }

    public Location getLocation() {
        return location;
    }
}
