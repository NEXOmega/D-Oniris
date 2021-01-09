package dandelion.oniris.players.classes.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.commands.equipemetns.WeaponsTypes;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.Stats;
import dandelion.oniris.players.advancements.ClassesAdvancements;
import dandelion.oniris.utils.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Magician extends RPGClass {
    public Magician() {
        super("Magicien",
                new Stats(4, 5,5,4,2),
                new ItemStack(Material.STICK),new String[]{"firework","fireball","eyeteleport"},new String[]{"staff", "gauntlets"});
    }

    @Override
    public void onClassSelected(Player player, PlayerStats playerStats) {
        DandelionItem item = FonctionUtils.createItem("Baton du debutant", Material.IRON_SHOVEL, ItemType.WEAPON, Ranks.COMMON,1, 1);
        player.getInventory().addItem(ItemUtils.createRPGItem(item,0, WeaponsTypes.STAFF, null, null, 3,0,0,0,2).getItemStack());
        Oniris.getInstance().getAdvancementManager().grantAdvancement(player, ClassesAdvancements.MAGEPATH.getAdvancement());
    }
}
