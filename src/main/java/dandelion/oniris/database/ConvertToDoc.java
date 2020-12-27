package dandelion.oniris.database;

import com.google.gson.reflect.TypeToken;
import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.entities.EntityRegistry;
import dandelion.oniris.entities.EntityStats;
import dandelion.oniris.entities.MobType;
import dandelion.oniris.players.Stats;
import dandelion.oniris.utils.FonctionUtils;
import dandelion.oniris.utils.MobUtils;
import dandelion.oniris.world.Treasure;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ConvertToDoc {

    static Type locationType = new TypeToken<HashMap<String, Location>>(){}.getType();
    static Type rewardListType = new TypeToken<ArrayList<Treasure.Reward>>(){}.getType();

    public static Document entityToDoc(DandelionMob mob) {
        Document document = new Document();

        document.put("mobName", mob.getMob_name());
        document.put("mobType", mob.getType().name());
        document.put("level", mob.getLevel());
        document.put("rewardXp", mob.getRewardXp());
        document.put("name", mob.getName());
        document.put("stats", GsonFactory.getPrettyGson().toJson(mob.getStats()));
        document.put("equipements", GsonFactory.getPrettyGson().toJson(mob.getEquipements()));
        document.put("spells", GsonFactory.getPrettyGson().toJson(mob.getSpells()));
        document.put("supports", GsonFactory.getPrettyGson().toJson(mob.getSupports()));
        document.put("mobLocations", GsonFactory.getPrettyGson().toJson(mob.getMobLocations()));
        document.put("mobDrops", GsonFactory.getPrettyGson().toJson(mob.getRewardList()));
        document.put("respawnTime", mob.getRespawnTime());
        document.put("respawn", mob.isRespawn());
        document.put("entityClass", mob.getEntityClass().getSimpleName());
        document.put("entityType", mob.getEntityTypes().f());
        document.put("baby", mob.isBaby());
        return document;
    }

    public static DandelionMob docToEntity(Document document) {
        FonctionUtils.DandelionMobBuilder builder = new FonctionUtils.DandelionMobBuilder();
        builder.setMobName(document.getString("mobName"));
        System.out.println("TEST " + document.getString("mobName") +" : " + document.getString("mobType"));
        builder.setMobType(MobType.valueOf(document.getString("mobType")));
        builder.setLevel(document.getInteger("level"));
        builder.setRewardXp(document.getInteger("rewardXp"));
        builder.setName(document.getString("name"));
        builder.setStats(GsonFactory.getPrettyGson().fromJson(document.getString("stats"), EntityStats.class));
        builder.setEquipements(GsonFactory.getCompactGson().fromJson(document.getString("equipements"), ItemStack[].class));
        builder.setSpells(GsonFactory.getCompactGson().fromJson(document.getString("spells"), ArrayList.class));
        builder.setSupports(GsonFactory.getCompactGson().fromJson(document.getString("supports"), ArrayList.class));
        builder.setRespawnTime(document.getLong("respawnTime"));
        builder.setRespawn(document.getBoolean("respawn"));
        builder.setEntityClass(EntityRegistry.valueOf(document.getString("entityClass").toUpperCase()).getCustomClass());
        builder.setEntityTypes(MobUtils.entityTypesFromString(document.getString("entityType")));
        builder.setBaby(document.getBoolean("baby"));

        return builder.build(GsonFactory.getPrettyGson().fromJson(document.getString("mobLocations"), locationType),GsonFactory.getPrettyGson().fromJson(document.getString("mobDrops"), rewardListType));
    }
}
