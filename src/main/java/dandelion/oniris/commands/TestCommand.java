package dandelion.oniris.commands;

import dandelion.oniris.Oniris;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    Oniris oniris;

    public TestCommand(Oniris oniris) {
        this.oniris = oniris;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        double pitch = ((((Player)commandSender).getEyeLocation().getPitch() + 90) * Math.PI) / 180;
        double yaw  = ((((Player)commandSender).getEyeLocation().getYaw() + 90)  * Math.PI) / 180;

        commandSender.sendMessage(" Radians Yaw : " + yaw + " Basic Yaw : " + ((Player)commandSender).getEyeLocation().getYaw());
        commandSender.sendMessage("X : " + Math.cos(yaw) + " Z : " + Math.sin(yaw * -1));

        return false;
    }
}
