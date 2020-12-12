package dandelion.oniris.players.spell.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.events.onHitBySpell;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.spell.SpellType;
import dandelion.oniris.utils.FonctionUtils;
import dandelion.oniris.utils.ItemType;
import dandelion.oniris.utils.Ranks;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Inspect extends Spell {

    @Override
    public void launch(Player sender, Oniris oniris, PlayerStats data) {
        AnvilGUI.Builder builder = new AnvilGUI.Builder();
        ArrayList<String> players = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(e -> players.add(e.getName()));
        builder.text("Entrez le pseudo a inspecter")
                .item(FonctionUtils.createItem("Watcher", Material.ENDER_EYE, ItemType.DISPLAY_ITEM, Ranks.COMMON,1, 1, players).getItemStack())
                .onComplete((a, e) -> {
                    PlayerStats tStats = oniris.getPlayers().get(a);
                    sender.sendMessage(ChatColor.GREEN + "Classe : " +tStats.getClassType().getRpgClass().name);
                    sender.sendMessage(ChatColor.GREEN + "Attack : " + tStats.getMaxAttack());
                    sender.sendMessage(ChatColor.GREEN + "Defense : " + tStats.getMaxDefence());
                    sender.sendMessage(ChatColor.GREEN + "Vitality : " + tStats.getMaxVitality());
                    sender.sendMessage(ChatColor.GREEN + "Magie : " + tStats.getMaxMagie());
                    sender.sendMessage(ChatColor.GREEN + "Niveau : " + tStats.getLevel());
                    sender.sendMessage(ChatColor.GREEN + "Support : " + tStats.getSupportType().getSupport().getName());

                    sender.sendMessage(String.valueOf(tStats.getSpells()));
                    Bukkit.getPluginManager().callEvent(new onHitBySpell(sender, a, SpellType.valueOf(this.getClass().getName().toUpperCase()), this, 0));
                    return AnvilGUI.Response.close();
                }).plugin(oniris)
                .open(sender);
    }
}
