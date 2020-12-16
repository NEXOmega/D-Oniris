package dandelion.oniris.commands.world;

import dandelion.oniris.Oniris;
import dandelion.oniris.database.ConvertToDoc;
import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.entities.MobType;
import dandelion.oniris.entities.entity.CustomCow;
import dandelion.oniris.entities.entity.DandelionEntity;
import dandelion.oniris.players.Stats;
import dandelion.oniris.utils.FonctionUtils;
import net.minecraft.server.v1_16_R2.EntityTypes;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;
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
                //TODO change to true mob name
                mobBuilder.setMobName(args[1]);
                mobBuilder.setLevel(Integer.parseInt(args[2]));
                mobBuilder.setRewardXp(Integer.parseInt(args[3]));
                mobBuilder.setName(args[4]);
                mobBuilder.setType(MobType.valueOf(args[5].toUpperCase()));
                mobBuilder.setRespawnTime(Long.parseLong(args[6]));
                mobBuilder.setRespawn(true);
                mobBuilder.setStats(new Stats(0,4,0,0,0));

                mobBuilder.setEntityClass(CustomCow.class);
                mobBuilder.setEntityTypes(EntityTypes.COW);

                DandelionMob dandelionMob = mobBuilder.build();

                Oniris.getInstance().getEntityManager().addEntity(dandelionMob.getMob_name(), dandelionMob);
                Oniris.getInstance().getEntityManager().addEntityLocation(uuid, dandelionMob.getMob_name(), ((Player) commandSender).getLocation());

                dandelionMob.spawnAsMob(((Player) commandSender).getLocation(), uuid);

            } else if(args[0].equalsIgnoreCase("edit")) {

            } else if(args[0].equalsIgnoreCase("spawn")) {
                if(oniris.getEntityManager().getMobMap().containsKey(args[1])) {
                    String uuid = UUID.randomUUID().toString();
                    oniris.getEntityManager().getMobMap().get(args[1]).spawnAsMob(((Player)commandSender).getEyeLocation(), uuid);
                    oniris.getEntityManager().getMobSpawn().put(uuid,((Player)commandSender).getEyeLocation());
                }
            } else if(args[0].equalsIgnoreCase("list")) {
                for(String uuid : oniris.getEntityManager().getMobMap().keySet()) {
                    DandelionMob dandelionMob = oniris.getEntityManager().getMobMap().get(uuid);
                    commandSender.sendMessage(uuid +" ; " + dandelionMob.getName());
                }
            } else if(args[0].equalsIgnoreCase("save")) {
                for(World world : Bukkit.getServer().getWorlds()) {
                    for(Entity entity : world.getEntities()) {
                        if(((CraftEntity)entity).getHandle() instanceof DandelionEntity) {
                            ((DandelionEntity)((CraftEntity)entity).getHandle()).setRespawn(false);
                            //TODO Save entities
                            System.out.println(((DandelionEntity)((CraftEntity)entity).getHandle()).getMob());
                            Document doc = ConvertToDoc.entityToDoc(((DandelionEntity)((CraftEntity)entity).getHandle()).getMob());
                            ConvertToDoc.docToEntity(doc);
                            entity.remove();
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return Arrays.asList("spawn", "create", "list", "save");
    }
}
