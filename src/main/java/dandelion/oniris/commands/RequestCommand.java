package dandelion.oniris.commands;

import dandelion.oniris.Oniris;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiFunction;

//DISABLED

public class RequestCommand implements TabExecutor {
    Oniris oniris;

    public RequestCommand(Oniris oniris) {
        this.oniris = oniris;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        String type = args[0];
        String from = args[1];
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }

    public enum AcceptAction {
        TELEPORTPORTAL((player, accepting) -> {

            return true;
        });

        AcceptAction(BiFunction<Player, Boolean, Boolean> o) {
        }
    }
}
