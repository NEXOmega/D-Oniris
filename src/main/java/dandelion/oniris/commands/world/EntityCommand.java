package dandelion.oniris.commands.world;

import com.mongodb.Block;
import dandelion.oniris.Oniris;
import dandelion.oniris.database.ConvertToDoc;
import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.entities.MobType;
import dandelion.oniris.entities.entity.DandelionEntity;
import dandelion.oniris.players.Stats;
import dandelion.oniris.utils.FonctionUtils;
import dandelion.oniris.utils.MobUtils;
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

import java.util.*;

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
                mobBuilder.setMobName(args[1]);
                mobBuilder.setMobType(MobType.valueOf(args[7]));
                System.out.println(MobType.valueOf(args[7]));
                mobBuilder.setLevel(Integer.parseInt(args[2]));
                mobBuilder.setRewardXp(Integer.parseInt(args[3]));
                mobBuilder.setName(args[4].replace("_"," "));
                mobBuilder.setRespawnTime(Long.parseLong(args[5]));
                mobBuilder.setRespawn(true);
                mobBuilder.setStats(new Stats(10,10,10,10,0));

                mobBuilder.setEntityClass(DandelionEntity.class);
                mobBuilder.setEntityTypes(MobUtils.entityTypesFromString("entity.minecraft."+args[6].toLowerCase()));

                DandelionMob dandelionMob = mobBuilder.build();

                Oniris.getInstance().getEntityManager().addEntity(dandelionMob.getMob_name(), dandelionMob);
                Oniris.getInstance().getEntityManager().addEntityLocation(uuid, dandelionMob.getMob_name(), ((Player) commandSender).getLocation());

                oniris.getConnectToDB().addEntity(dandelionMob);

                dandelionMob.spawnAsMob(((Player) commandSender).getLocation(), uuid);
                ConvertToDoc.entityToDoc(dandelionMob);

            } else if(args[0].equalsIgnoreCase("edit")) {

            } else if(args[0].equalsIgnoreCase("spawn")) {
                if(oniris.getEntityManager().getMobMap().containsKey(args[1])) {
                    String uuid = UUID.randomUUID().toString();
                    if(args[2].equalsIgnoreCase("mob")) {
                        oniris.getEntityManager().getMobMap().get(args[1]).spawnAsMob(((Player)commandSender).getLocation(), uuid);
                        if(Boolean.parseBoolean(args[3])) {
                            oniris.getEntityManager().addEntityLocation(uuid,args[1],((Player)commandSender).getLocation());
                            oniris.getEntityManager().getMobMap().get(args[1]).getMobLocations().put(uuid,((Player)commandSender).getLocation());
                            if(oniris.getConnectToDB().hasEntity(args[1])) {//TODO args 3 not working check if right
                                oniris.getConnectToDB().updateEntity(oniris.getEntityManager().getMobMap().get(args[1]));
                            }
                        }
                    } else if(args[2].equalsIgnoreCase("pet")) {
                        oniris.getEntityManager().getMobMap().get(args[1]).spawnAsPet(((Player)commandSender).getLocation(), ((Player)commandSender), uuid);
                    }

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
                            oniris.getConnectToDB().addEntity(((DandelionEntity)((CraftEntity)entity).getHandle()).getMob());
                            entity.remove();
                        }
                    }
                }
            } else if(args[0].equalsIgnoreCase("load")) {
                oniris.getConnectToDB().getEntityCollecion().find().forEach((Block<? super Document>) e -> {
                    DandelionMob mob = ConvertToDoc.docToEntity(e);
                    oniris.getEntityManager().addEntity(mob.getMob_name(), mob);
                    mob.getMobLocations().forEach((uuid, loc) -> {
                        oniris.getEntityManager().addEntityLocation(uuid, mob.getMob_name(), loc);
                        mob.spawnAsMob(loc, uuid);
                    });
                });
            } else if(args[0].equalsIgnoreCase("reset")) {
                for(World world : Bukkit.getServer().getWorlds()) {
                    for(Entity entity : world.getEntities()) {
                        if(((CraftEntity)entity).getHandle() instanceof DandelionEntity) {
                            ((DandelionEntity)((CraftEntity)entity).getHandle()).setRespawn(false);
                            //TODO Save entities
                            entity.remove();
                        }
                    }
                }
                oniris.getEntityManager().getMobMap().clear();
                oniris.getEntityManager().getMobSpawn().clear();
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        ArrayList<String> tab = new ArrayList<>();
        if(args[0].equalsIgnoreCase("create")) {
            if(args.length == 2)
                return Arrays.asList("mob_identifier");
            if(args.length == 3)
                return Arrays.asList("level");
            if(args.length == 4)
                return Arrays.asList("expDropped");
            if(args.length == 5)
                return Arrays.asList("mobName");
            if(args.length == 6)
                return Arrays.asList("respawnTimeInSeconds");
            if(args.length == 7)
                return Arrays.asList("cow","chicken","zombie","skeleton","wither","hoglin","pig","villager","fish","guardian");
            if(args.length == 8)
                for (MobType type : MobType.values()) {
                    System.out.println(type.name());
                    if (type.name().startsWith(args[7])) {
                        tab.add(type.name());
                    }
                }
            return tab;
        } else if(args[0].equalsIgnoreCase("spawn")) {
            if(args.length == 2) {
                for (String name : oniris.getEntityManager().getMobMap().keySet()) {
                    if (name.startsWith(args[1])) {
                        tab.add(name);
                    }
                }
                return tab;
            }
            if(args.length == 3) {

                return Arrays.asList("mob", "pet");
            }
        }
        return Arrays.asList("spawn", "create", "list", "save", "load");
    }
}
