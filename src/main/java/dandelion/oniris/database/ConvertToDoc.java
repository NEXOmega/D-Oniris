package dandelion.oniris.database;

import com.google.gson.reflect.TypeToken;
import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.entities.EntityRegistry;
import dandelion.oniris.entities.MobType;
import dandelion.oniris.players.Stats;
import dandelion.oniris.utils.FonctionUtils;
import dandelion.oniris.utils.MobUtils;
import net.minecraft.server.v1_16_R2.Entity;
import org.bson.Document;
import org.bukkit.Location;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ConvertToDoc {

    static Type type = new TypeToken<HashMap<String, Location>>(){}.getType();

    public static Document entityToDoc(DandelionMob mob) {
        Document document = new Document();

        document.put("mobName", mob.getMob_name());
        document.put("level", mob.getLevel());
        document.put("rewardXp", mob.getRewardXp());
        document.put("name", mob.getName());
        document.put("stats", GsonFactory.getPrettyGson().toJson(mob.getStats()));
        document.put("equipements", GsonFactory.getPrettyGson().toJson(mob.getEquipements()));
        document.put("spells", GsonFactory.getPrettyGson().toJson(mob.getSpells()));
        document.put("supports", GsonFactory.getPrettyGson().toJson(mob.getSupports()));
        document.put("mobLocations", GsonFactory.getPrettyGson().toJson(mob.getMobLocations()));
        document.put("mobType", mob.getType().toString());
        document.put("respawnTime", mob.getRespawnTime());
        document.put("respawn", mob.isRespawn());
        document.put("entityClass", mob.getEntityClass().getSimpleName());
        document.put("entityType", mob.getEntityTypes().f());
        System.out.println(document);
        return document;
    }

    public static DandelionMob docToEntity(Document document) {
        FonctionUtils.DandelionMobBuilder builder = new FonctionUtils.DandelionMobBuilder();
        builder.setMobName(document.getString("mobName"));
        builder.setLevel(document.getInteger("level"));
        builder.setRewardXp(document.getInteger("rewardXp"));
        builder.setName(document.getString("name"));
        builder.setStats(GsonFactory.getPrettyGson().fromJson(document.getString("stats"), Stats.class));
        builder.setEquipements(GsonFactory.getCompactGson().fromJson(document.getString("equipements"), ArrayList.class));
        builder.setSpells(GsonFactory.getCompactGson().fromJson(document.getString("spells"), ArrayList.class));
        builder.setSupports(GsonFactory.getCompactGson().fromJson(document.getString("supports"), ArrayList.class));
        builder.setType(MobType.valueOf(document.getString("mobType")));
        builder.setRespawnTime(document.getLong("respawnTime"));
        builder.setRespawn(document.getBoolean("respawn"));
        builder.setEntityClass((Class<? extends Entity>) EntityRegistry.valueOf(document.getString("entityClass").toUpperCase()).getCustomClass());
        builder.setEntityTypes(MobUtils.entityTypesFromString(document.getString("entityType")));
        System.out.println(builder.build().getEntityTypes());

        return builder.build(GsonFactory.getPrettyGson().fromJson(document.getString("mobLocations"), type));
    }
}
