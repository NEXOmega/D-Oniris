package dandelion.oniris.database;

import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.entities.MobType;
import dandelion.oniris.players.Stats;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.support.Support;
import net.minecraft.server.v1_16_R2.Entity;
import net.minecraft.server.v1_16_R2.EntityTypes;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class ConvertToDoc {

    String mob_name;
    int level, rewardXp;
    String name;
    Stats stats;
    ArrayList<ItemStack> equipements;
    ArrayList<Class<? extends Spell>> spells;
    ArrayList<Support> supports;
    HashMap<String, Location> mobLocations;

    MobType type;

    long respawnTime;
    boolean respawn = true;

    Class<? extends Entity> entityClass;
    EntityTypes entityTypes;

    public static Document entityToDoc(DandelionMob mob) {
        Document document = new Document();

        document.put("level", mob.getLevel());
        document.put("rewardXp", mob.getRewardXp());
        document.put("name", mob.getName());
        document.put("stats", GsonFactory.getPrettyGson().toJson(mob.getStats()));
        document.put("equipements", GsonFactory.getPrettyGson().toJson(mob.getEquipements()));
        document.put("spells", GsonFactory.getPrettyGson().toJson(mob.getSpells()));
        document.put("supports", GsonFactory.getPrettyGson().toJson(mob.getSupports()));
        document.put("mobLocations", GsonFactory.getPrettyGson().toJson("mobLocation"));
        document.put("mobType", GsonFactory.getPrettyGson().toJson(mob.getType()));
        document.put("respawnTime", mob.getRespawnTime());
        document.put("respawn", mob.isRespawn());
        document.put("entityClass", GsonFactory.getPrettyGson().toJson(mob.getEntityClass()));
        document.put("entityType", GsonFactory.getPrettyGson().toJson(mob.getEntityTypes()));
        return document;
    }
}
