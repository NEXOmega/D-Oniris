package dandelion.oniris.commands;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerInfos implements CommandExecutor {
    Oniris oniris;
    public PlayerInfos(Oniris oniris) {
        this.oniris = oniris;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {

                if(oniris.getPlayers().containsKey(sender)){
                    PlayerStats playerStats = oniris.getPlayers().get(sender);
                    sender.sendMessage(playerStats.getClassType().toString());
                    sender.sendMessage(getEquipementsInfos(playerStats));
                    sender.sendMessage(getStatsInfos(playerStats));
                    if(args.length>=1)  {
                        if(args[0].equalsIgnoreCase("vit")) {
                            sender.sendMessage(playerStats.getHealth() + "/" + playerStats.getMaxVitality());
                        }
                    }
                }

        }
        return false;
    }
    private String getStatsInfos(PlayerStats stats) {
        return ChatColor.RED +"--{"+ChatColor.YELLOW +"Stats" + ChatColor.RED +"}--" +
                ChatColor.RED +"\nVitalité: " + ChatColor.YELLOW + stats.getStats().getVitality() +
                ChatColor.RED +"\nForce: " + ChatColor.YELLOW + stats.getStats().getStrength() +
                ChatColor.RED +"\nMagie: " + ChatColor.YELLOW + stats.getStats().getMagie() +
                ChatColor.RED +"\nVitesse: " + ChatColor.YELLOW + stats.getStats().getSpeed() +
                ChatColor.RED +"\nDefence: " + ChatColor.YELLOW + stats.getStats().getDefence();
    }

    private String getEquipementsInfos(PlayerStats stats) {
        return ChatColor.RED +"--{"+ChatColor.YELLOW +"Equipements" + ChatColor.RED +"}--" +
                ChatColor.RED +"\nVitalité: " + ChatColor.YELLOW + stats.getEquipementStats().getVitality() +
                ChatColor.RED +"\nForce: " + ChatColor.YELLOW + stats.getEquipementStats().getStrength() +
                ChatColor.RED +"\nMagie: " + ChatColor.YELLOW + stats.getEquipementStats().getMagie() +
                ChatColor.RED +"\nVitesse: " + ChatColor.YELLOW + stats.getEquipementStats().getSpeed() +
                ChatColor.RED +"\nDefence: " + ChatColor.YELLOW + stats.getEquipementStats().getDefence();
    }
}
