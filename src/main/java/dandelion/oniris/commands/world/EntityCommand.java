package dandelion.oniris.commands.world;

import dandelion.oniris.Oniris;
import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.entities.MobType;
import dandelion.oniris.entities.entity.CustomCow;
import dandelion.oniris.utils.FonctionUtils;
import net.minecraft.server.v1_16_R2.EntityTypes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class EntityCommand implements TabExecutor {
    Oniris oniris;
    public EntityCommand(Oniris oniris) {
        this.oniris = oniris;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("create")) {

                FonctionUtils.DandelionMobBuilder mobBuilder = new FonctionUtils.DandelionMobBuilder();
                String uuid = UUID.randomUUID().toString();
                mobBuilder.setUuid(uuid);
                mobBuilder.setLevel(Integer.parseInt(args[1]));
                mobBuilder.setRewardXp(Integer.parseInt(args[2]));
                mobBuilder.setName(args[3]);
                mobBuilder.setType(MobType.valueOf(args[4].toUpperCase()));
                mobBuilder.setSpawnPoint(((Player) commandSender).getLocation());
                mobBuilder.setRespawnTime(Long.parseLong(args[5]));
                mobBuilder.setRespawn(true);

                mobBuilder.setEntityClass(CustomCow.class);
                mobBuilder.setEntityTypes(EntityTypes.COW);

                DandelionMob dandelionMob = mobBuilder.build();

                dandelionMob.spawnAsPet(dandelionMob.getSpawnPoint(), (Player) commandSender);

                Oniris.getInstance().getEntityManager().addEntity(uuid, dandelionMob);

            } else if(args[0].equalsIgnoreCase("edit")) {

            } else if(args[0].equalsIgnoreCase("spawn")) {
                if(oniris.getEntityManager().getMobMap().containsKey(args[1])) {
                    oniris.getEntityManager().getMobMap().get(args[1]).spawnAsMob(((Player)commandSender).getEyeLocation());
                }
            } else if(args[0].equalsIgnoreCase("list")) {
                for(String uuid : oniris.getEntityManager().getMobMap().keySet()) {
                    DandelionMob dandelionMob = oniris.getEntityManager().getMobMap().get(uuid);
                    commandSender.sendMessage(uuid +" ; " + dandelionMob.getName());
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return Arrays.asList("spawn", "create", "list");
    }
}
