package dandelion.oniris.players.classes.list;

import dandelion.oniris.commands.equipemetns.WeaponsTypes;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.Stats;
import dandelion.oniris.utils.FonctionUtils;
import dandelion.oniris.utils.ItemType;
import dandelion.oniris.utils.ItemUtils;
import dandelion.oniris.utils.Ranks;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Archer extends RPGClass {
    public Archer() {
        super("Archer",new Stats(2, 7,5,3,3),new ItemStack(Material.BOW),new String[]{"firework","arrowrain"},
                new String[]{"bow", "longbow", "dagger"});
    }

    @Override
    public void onClassSelected(Player player, PlayerStats playerStats) {
        ItemStack dagger =  ItemUtils.createRPGItem(FonctionUtils.createItem("Dague d'Archer", Material.IRON_SWORD, ItemType.WEAPON, Ranks.COMMON, 1, 1),0,
                WeaponsTypes.DAGGER, null, null, 3,0,0,0,0).getItemStack();

        ItemStack bow = ItemUtils.createRPGItem(
                FonctionUtils.createItem("Arc de debutant", Material.BOW, ItemType.WEAPON, Ranks.COMMON, 1, 1),0,
                WeaponsTypes.BOW,null, null, 4,0,0,0,0).getItemStack();

        ItemStack arrows = FonctionUtils.createItem("Fleche de debutant", Material.ARROW, ItemType.ITEM, Ranks.COMMON,16, 1).getItemStack();
        player.getInventory().addItem(dagger, bow, arrows);
    }
}
