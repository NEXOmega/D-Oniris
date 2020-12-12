package dandelion.oniris.commands.stats;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.utils.FonctionUtils;
import dandelion.oniris.utils.ItemType;
import dandelion.oniris.utils.Ranks;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class JobCommand implements TabExecutor {
    Oniris oniris;

    public JobCommand(Oniris oniris) {
        this.oniris = oniris;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(oniris.getPlayers().containsKey(commandSender)) {
            PlayerStats stats = oniris.getPlayers().get(commandSender);

            Gui gui = new Gui(oniris, 1, "Job Gui");
            gui.setOnGlobalClick(e -> e.setCancelled(true));
            OutlinePane pane = new OutlinePane(0,0,9,1);
            pane.addItem(new GuiItem(FonctionUtils.createItem("Forgeron", Material.DIAMOND_AXE, ItemType.DISPLAY_ITEM, Ranks.COMMON,1, 0, Arrays.asList(ChatColor.GOLD + "Level : " + stats.getJobStats().getBlacksmith().getLevel(), ChatColor.GOLD + "Exp : " + stats.getJobStats().getBlacksmith().getExp(), ChatColor.GOLD + "Exp for level up " + stats.getJobStats().getBlacksmith().getExpUntilNextLevel())).getItemStack(), clickEvent -> {
                stats.getJobStats().getBlacksmith().getKnowingRecipeGui(oniris, "Bucheron Gui", (Player) commandSender, stats).show((HumanEntity) commandSender);
            }));
            pane.addItem(new GuiItem(FonctionUtils.createItem("Alchimiste", Material.EXPERIENCE_BOTTLE,ItemType.DISPLAY_ITEM, Ranks.COMMON,1, 0, Arrays.asList(ChatColor.GOLD + "Level : " + stats.getJobStats().getAlchemist().getLevel(), ChatColor.GOLD + "Exp : " + stats.getJobStats().getAlchemist().getExp(), ChatColor.GOLD + "Exp for level up " + stats.getJobStats().getAlchemist().getExpUntilNextLevel())).getItemStack(), e-> {
                stats.getJobStats().getAlchemist().potionExamination((Player) commandSender, stats);
            }));
            gui.addPane(pane);
            gui.show((HumanEntity) commandSender);
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
