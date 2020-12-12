package dandelion.oniris.commands.stats;

import dandelion.oniris.Oniris;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerMenuCommands implements CommandExecutor {
    Oniris oniris;
    public  PlayerMenuCommands(Oniris main) {
        this.oniris = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            ((Player) sender).openInventory(oniris.getGui().inv());
        }
        return false;
    }
}
