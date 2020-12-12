package dandelion.oniris.commands.stats;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.support.SupportType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuffCommand implements TabExecutor {

    Oniris oniris;

    public BuffCommand(Oniris oniris) {
        this.oniris = oniris;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length == 0) {

        } else if(args.length == 4) {
            Player p = Bukkit.getPlayer(args[0]);
            PlayerStats stats = oniris.getPlayers().get(p);
            if(args[1].equalsIgnoreCase("add")) {
                stats.addBuff(SupportType.valueOf(args[2]).getSupport(), (long) (Integer.parseInt(args[3])*1000));
                p.sendMessage(args[2] + " Added for " + args[3] + "s");
            } else if(args[1].equalsIgnoreCase("remove")) {
                stats.removeBuff(SupportType.valueOf(args[2]).getSupport());
            } else if(args[1].equalsIgnoreCase("list")) {
                commandSender.sendMessage(stats.getBuffs().keySet().toString());
            }
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        ArrayList<String> tab = new ArrayList<>();
        if(args.length == 2) {
            return Arrays.asList("add", "remove", "list");
        } if (args.length > 2) {
            if(args[1].equalsIgnoreCase("add")) {
                if(args.length == 3) {
                    for (SupportType sp : SupportType.values()) {
                        if (sp.getSupport().getName().startsWith(args[2]))
                            tab.add(sp.name());
                    }
                }
                return tab;
            } else if(args[1].equalsIgnoreCase("remove")) {
                PlayerStats stats = oniris.getPlayers().get(Bukkit.getPlayer(args[0]));
                if(args.length == 3) {
                    stats.getBuffs().keySet().forEach(support -> {
                        if (support.getName().startsWith(args[2]))
                            tab.add(support.getName());
                    });
                }
            }
            return tab;
        }
        return null;
    }
}
