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

public class Berserk extends RPGClass {

    public Berserk() {
        super("Berserk",new Stats(7, 2,5,5,1),new ItemStack(Material.DIAMOND_AXE),new String[]{"firework","leap", "berserkmode"},
                new String[]{"sword", "broadsword", "axe"});
    }

    @Override
    public void onClassSelected(Player player, PlayerStats playerStats) {
        DandelionItem item = FonctionUtils.createItem("Epee de debutant", Material.IRON_SWORD, ItemType.WEAPON, Ranks.COMMON,1, 1);
        player.getInventory().addItem(ItemUtils.createRPGItem(item,0, WeaponsTypes.SWORD, null, null, 5,0,0,0,0).getItemStack());
        Oniris.getInstance().getAdvancementManager().grantAdvancement(player, ClassesAdvancements.WARRIORPATH.getAdvancement());
    }
}
