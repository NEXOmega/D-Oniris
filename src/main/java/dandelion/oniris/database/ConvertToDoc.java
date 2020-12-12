package dandelion.oniris.database;

import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class ConvertToDoc {
    public static Document itemstackToDocument(ItemStack itemStack) {
        return new Document();
    }

    public static Document locationToDocument(Location location) {
        Document document = new Document();
        document.put("world", location.getWorld());
        document.put("x", location.getX());
        document.put("y", location.getY());
        document.put("z", location.getZ());
        return document;
    }
}
