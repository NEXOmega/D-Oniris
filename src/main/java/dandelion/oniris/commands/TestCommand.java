package dandelion.oniris.commands;

import dandelion.oniris.Oniris;
import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.entities.entity.CustomCow;
import net.minecraft.server.v1_16_R2.EntityTypes;
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
        /*Location loc = ((Player)commandSender).getLocation();
        PetCow superZombie = new PetCow(((CraftWorld)((Player) commandSender).getWorld()).getHandle(), ((Player)commandSender));
        superZombie.setCustomName(new ChatComponentText(((Player)commandSender).getDisplayName() + " Pet"));

        oniris.getPlayers().get(commandSender).setPet(superZombie);

        ((CraftWorld)loc.getWorld()).getHandle().addEntity(superZombie, CreatureSpawnEvent.SpawnReason.CUSTOM);*/

        DandelionMob dandelionMob = new DandelionMob("Ban le furieu", ((Player)commandSender).getLocation(), EntityTypes.COW, CustomCow.class);
        dandelionMob.spawn();

        return false;
    }
}
