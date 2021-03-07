package dandelion.oniris.commands.world;

import dandelion.oniris.Oniris;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class RessourcesCommand implements TabExecutor {
    Oniris oniris;

    public RessourcesCommand(Oniris oniris) {
        this.oniris = oniris;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull  String[] args) {
        Player p = (Player) commandSender;
        if(args[0].equalsIgnoreCase("create")) {
            oniris.getRessourceManager().createRessource(args[4], p.getTargetBlock(null, 10), p.getInventory().getItemInMainHand(), args[3], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            p.sendMessage(ChatColor.RED + "Ressource crée !");

        } else if(args[0].equalsIgnoreCase("add")) {
            if(oniris.getRessourceManager().ressources.containsKey(p.getTargetBlock(null, 10).getLocation())) {
                oniris.getRessourceManager().addToRessource(p.getTargetBlock(null, 10).getLocation(), p.getInventory().getItemInMainHand().clone());
                p.sendMessage("Loot ajouté !");
                return true;
            }
            p.sendMessage("Le block n'est pas une ressource, utilisez /ressource create");
            return false;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length == 1) {
            return Arrays.asList("add", "create");
        }else if(args.length>1) {
            if(args[0].equalsIgnoreCase("create")) {
                if(args.length == 2)
                    return Arrays.asList("Required Level", "1", "5");
                if (args.length == 3)
                    return Arrays.asList("RespawnTime");
                if (args.length == 4)
                    return Arrays.asList("Job Name", "lumberjack", "miner");
                if (args.length == 5)
                    return Arrays.asList("Bois_de_", "Minerais_de_", "Blé_de_");
            }
        }
        return null;
    }
}
