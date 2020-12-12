package dandelion.oniris.players;

import dandelion.oniris.database.ConvertToDoc;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

        item.setItemMeta(meta);
        this.location = location;
    }

    public ItemStack getItem() {
        return item;
    }

    public Location getLocation() {
        return location;
    }

    public Document asDoc() {
        Document doc = new Document();
        doc.put("name", this.locName);
        doc.put("location", ConvertToDoc.locationToDocument(this.getLocation()));
        doc.put("item", ConvertToDoc.itemstackToDocument(this.getItem()));
        return doc;
    }
}
