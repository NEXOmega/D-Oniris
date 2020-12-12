package dandelion.oniris.commands.world;

import dandelion.oniris.Oniris;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class TreasuresCommands implements TabExecutor {

    Oniris oniris;

    public TreasuresCommands(Oniris oniris) {
        this.oniris = oniris;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player p = (Player) commandSender;
        if(args[0].equalsIgnoreCase("create")) {
            if(p.getTargetBlock(null, 10).getType().equals(Material.CHEST)) {
                oniris.getTreasureManager().addTreasure(p.getTargetBlock(null, 10).getLocation(), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Boolean.parseBoolean(args[3]));
                p.sendMessage(ChatColor.RED + "Trésor crée !");
            }
        } else if(args[0].equalsIgnoreCase("add")) {
            if(p.getTargetBlock(null, 10).getType().equals(Material.CHEST)) {
                if(oniris.getTreasureManager().treasures.containsKey(p.getTargetBlock(null, 10).getLocation())) {
                    oniris.getTreasureManager().addReward(p.getTargetBlock(null, 10).getLocation(), p.getInventory().getItemInMainHand(), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                    p.sendMessage("Loot ajouté !");
                    return true;
                }
                p.sendMessage("Le coffre n'est pas un trésor, utilisez /treasure create");
                return false;
            }
            p.sendMessage("Vous ne regardez pas un coffre !");
            return false;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length == 1) {
            return Arrays.asList("add", "create");
        }else if(args.length>1) {
            if(args[0].equalsIgnoreCase("create")) {
                if(args.length == 2)
                    return Arrays.asList("Respawn Time");
                if (args.length == 3)
                    return Arrays.asList("Item Drop Number");
                if (args.length == 4)
                    return Arrays.asList("true", "false", "Is random");
            } else if(args[0].equalsIgnoreCase("add")) {
                if(args.length == 2)
                    return Arrays.asList("Chance");
                if(args.length == 3)
                    return Arrays.asList("Chance to get multiple");
            }
        }
        return null;
    }
}
