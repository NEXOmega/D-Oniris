package dandelion.oniris.commands.stats;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.classes.ClassType;
import dandelion.oniris.players.classes.list.RPGClass;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.upperlevel.spigot.book.BookUtil;

import java.util.Arrays;

public class PlayerProgress implements CommandExecutor {
    Oniris oniris;
    public PlayerProgress(Oniris oniris) {
        this.oniris = oniris;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(oniris.getPlayers().containsKey((Player) sender)){
            PlayerStats playerStats = oniris.getPlayers().get((Player) sender);
            if(playerStats.getClassType().getProgressionTree() == null) {
                sender.sendMessage(ChatColor.RED + " Vous etes au maximum de votre classe");
                return false;
            }
            if(args.length > 0){

                if(playerStats.getClassType().getProgressionTree().isProgressionValid((Player) sender, playerStats).contains(ClassType.getClassType(args[0]).getRpgClass())) {
                    playerStats.setClassType(ClassType.getClassType(args[0]));
                    playerStats.getClassType().getRpgClass().onClassSelected((Player) sender, playerStats);
                    sender.sendMessage(ChatColor.GOLD + "Bravo ! Votre classe a evolue en : " +ChatColor.RED+ args[0]);
                    playerStats.getSpells().clear();
                    playerStats.getSpells().addAll(Arrays.asList(playerStats.getClassType().getSpellList()));
                    return false;
                }
            }

            BookUtil.PageBuilder progressPage = new BookUtil.PageBuilder();
            for (RPGClass rpgClass:playerStats.getClassType().getProgressionTree().isProgressionValid((Player) sender, playerStats)) {
                progressPage.add(BookUtil.TextBuilder.of(rpgClass.name + ", ").color(ChatColor.DARK_BLUE).onClick(BookUtil.ClickAction.runCommand("/progress " + rpgClass.name)).build());
            }
            ItemStack book = BookUtil.writtenBook()
                    .author("NEXOmega")
                    .title("Progression")
                    .pages(
                            new BookUtil.PageBuilder()
                                    .add(BookUtil.TextBuilder.of("Choisir Votre prochaine classe")
                                            .color(ChatColor.GOLD)
                                            .style(ChatColor.BOLD)
                                            .onClick(BookUtil.ClickAction.changePage(1))
                                            .build()
                                    ).build(),
                            progressPage.build()
                    ).build();

            BookUtil.openPlayer((Player)sender, book);
        }
        return false;
    }
}
