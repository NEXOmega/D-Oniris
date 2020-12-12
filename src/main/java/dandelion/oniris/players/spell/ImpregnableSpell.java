package dandelion.oniris.players.spell;

import dandelion.oniris.commands.equipemetns.WeaponsTypes;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedList;

public interface ImpregnableSpell {
    ArrayList<ItemStack> requiredItems = new ArrayList<>();
    LinkedList<WeaponsTypes> possibleTools = new LinkedList<>();

    default boolean fillRequirements(Inventory e) {
        int meetRequirements = 0;
        for (ItemStack item: requiredItems) {
            if(e.contains(item))
                meetRequirements++;
        }
        return (meetRequirements==requiredItems.size());
    }
}
