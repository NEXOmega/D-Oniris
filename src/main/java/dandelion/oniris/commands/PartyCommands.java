package dandelion.oniris.commands;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PartyCommands implements TabExecutor {
    Oniris oniris;

    public PartyCommands(Oniris oniris) {
        this.oniris = oniris;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args[0].equalsIgnoreCase("create")) {
            if(args.length < 2)
                commandSender.sendMessage(ChatColor.RED + "Vous devez donner un nom a votre groupe.");
            if(commandSender instanceof Player)
                oniris.getPartyManager().createParty(args[1], (Player) commandSender);
            else
                commandSender.sendMessage(ChatColor.RED + "Seul les joueurs peuvent faire cette commande.");
        } else if(args[0].equalsIgnoreCase("join")) {
            if (args.length < 2)
                commandSender.sendMessage(ChatColor.RED + " Vous devez donner l'uuid de la party");
            if(commandSender instanceof Player){
                PlayerStats stats = oniris.getPlayers().get(commandSender);
                if(stats.getPartyUUID() == null)
                    oniris.getPartyManager().joinParty(args[1], (Player) commandSender);
                else
                    commandSender.sendMessage(ChatColor.RED + "Vous devez d'abord quitter votre précédent groupe. /party leave");
            } else
                commandSender.sendMessage(ChatColor.RED + "Seul les joueurs peuvent faire cette commande.");
        } else if(args[0].equalsIgnoreCase("leave")) {
            if(commandSender instanceof Player) {
                PlayerStats stats = oniris.getPlayers().get(commandSender);
                if(stats.getPartyUUID() != null)
                    oniris.getPartyManager().leftParty(stats.getPartyUUID(), (Player) commandSender);
                else
                    commandSender.sendMessage(ChatColor.RED + "Erreur, vous n'avez pas de groupe.");
            }
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
