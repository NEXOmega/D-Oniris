package dandelion.oniris.database;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.jobs.Blacksmith;
import dandelion.oniris.jobs.JobStats;
import dandelion.oniris.players.Infos;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.Stats;
import dandelion.oniris.players.classes.ClassType;
import dandelion.oniris.players.support.SupportType;
import dandelion.oniris.utils.Recipe;
import dandelion.oniris.world.Ressource;
import dandelion.oniris.world.Treasure;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class DatabaseConnect {
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    MongoDatabase database = mongoClient.getDatabase("dandelion");
    MongoCollection<Document> collection = database.getCollection("players");
    MongoCollection<Document> treasuresCollection = database.getCollection("treasures");
    MongoCollection<Document> ressourcesCollection = database.getCollection("ressources");
    MongoCollection<Document> jobsCollection = database.getCollection("jobs");
    MongoCollection<Document> entityCollecion = database.getCollection("entities");

    public boolean hasPlayer(UUID uuid) {
        Document doc = collection.find(eq("uuid", uuid.toString())).first();
        return (doc != null);
    }

    public PlayerStats getPlayerStats(UUID uuid) {
        if(hasPlayer(uuid)) {
            Document doc = collection.find(eq("uuid", uuid.toString())).first();
            PlayerStats stats = GsonFactory.getPrettyGson().fromJson(doc.getString("stats"), PlayerStats.class);
            LinkedList<String> spells = new LinkedList<String>((Collection<? extends String>) doc.get("sorts"));
            Infos infos = GsonFactory.getPrettyGson().fromJson(doc.getString("infos"), Infos.class);
            Stats playerStats = GsonFactory.getPrettyGson().fromJson(doc.getString("playerStats"), Stats.class);
            Stats equipementsStats = GsonFactory.getPrettyGson().fromJson(doc.getString("equipementStats"), Stats.class);
            ItemStack[] equipements = GsonFactory.getPrettyGson().fromJson(doc.getString("equipements"), ItemStack[].class);
            JobStats jobStats = GsonFactory.getPrettyGson().fromJson(doc.getString("jobStats"), JobStats.class);
            ClassType classType = ClassType.valueOf(doc.getString("classType").toUpperCase());
            double health = doc.getDouble("health");
            int statsPoints = doc.getInteger("statsPoints");
            SupportType support = SupportType.valueOf(doc.getString("support"));
            ArrayList portalStones = GsonFactory.getPrettyGson().fromJson(doc.getString("portalStones"), ArrayList.class);

            PlayerStats stats1 = new PlayerStats(spells, infos, playerStats, equipementsStats, classType, equipements, jobStats, portalStones, support, health, statsPoints);
            return stats1;
        }
        return null;
    }

    public void updatePlayer(UUID id, PlayerStats stats) {
        Document doc = collection.find(eq("uuid", id.toString())).first();
        //doc.replace("stats", GsonFactory.getPrettyGson().toJson(stats));
        doc.put("sorts", stats.getSpells());
        doc.put("infos", GsonFactory.getPrettyGson().toJson(stats.getInfos()));
        doc.put("playerStats", GsonFactory.getPrettyGson().toJson(stats.getStats()));
        doc.put("equipementStats",GsonFactory.getPrettyGson().toJson(stats.getEquipementStats()));
        doc.put("equipements", GsonFactory.getPrettyGson().toJson(stats.getEquipements()));
        doc.put("jobStats", GsonFactory.getPrettyGson().toJson(stats.getJobStats()));
        doc.put("classType", stats.getClassType().name());
        doc.put("health", stats.getHealth());
        doc.put("statsPoints", stats.getStatsPoint());
        doc.put("support", stats.getSupportType().toString());
        doc.put("portalStones", GsonFactory.getPrettyGson().toJson(stats.getPortalStones()));
        collection.replaceOne(eq("uuid", id.toString()), doc);
    }

    public void addPlayer(UUID id, PlayerStats stats) {
        Document doc = new Document("uuid", id.toString());
        doc.put("sorts", stats.getSpells());
        doc.put("infos", GsonFactory.getPrettyGson().toJson(stats.getInfos()));
        doc.put("playerStats", GsonFactory.getPrettyGson().toJson(stats.getStats()));
        doc.put("equipementStats",GsonFactory.getPrettyGson().toJson(stats.getEquipementStats()));
        doc.put("equipements", GsonFactory.getPrettyGson().toJson(stats.getEquipements()));
        doc.put("jobStats", GsonFactory.getPrettyGson().toJson(stats.getJobStats()));
        doc.put("classType", stats.getClassType().name());
        doc.put("health", stats.getHealth());
        doc.put("statsPoints", stats.getStatsPoint());
        doc.put("support", stats.getSupportType().toString());
        doc.put("portalStones", GsonFactory.getPrettyGson().toJson(stats.getPortalStones()));
        collection.insertOne(doc);
    }

    public void addEntity(DandelionMob mob) {
        Document doc = ConvertToDoc.entityToDoc(mob);
        entityCollecion.insertOne(doc);
    }

    public boolean hasEntity(String mobName) {
        Document doc = entityCollecion.find(eq("mobName", mobName)).first();
        return (doc != null);
    }

    public void updateEntity(DandelionMob mob) {
        System.out.println(mob.getMobLocations().size());
        entityCollecion.replaceOne(eq("mobName", mob.getMob_name()), ConvertToDoc.entityToDoc(mob));
    }

    public void addTreasure(Treasure treasure, Location location) {
        Document treasureDoc = new Document("location", GsonFactory.getPrettyGson().toJson(location));
        treasureDoc.put("treasure", GsonFactory.getPrettyGson().toJson(treasure));

        treasuresCollection.insertOne(treasureDoc);
    }

    public void updateTreasure(Treasure treasure, Location location) {
        Document doc = treasuresCollection.find(eq("location", GsonFactory.getPrettyGson().toJson(location))).first();
        System.out.println(GsonFactory.getPrettyGson().toJson(location));
        System.out.println(doc);
        doc.put("treasure", GsonFactory.getPrettyGson().toJson(treasure));
        treasuresCollection.replaceOne(eq("location", GsonFactory.getPrettyGson().toJson(location)), doc);
    }

    public void addRessource(Ressource ressource, Location location) {
        Document ressourceDoc = new Document("location", GsonFactory.getPrettyGson().toJson(location));
        ressourceDoc.put("ressource", GsonFactory.getPrettyGson().toJson(ressource));

        ressourcesCollection.insertOne(ressourceDoc);
    }

    public void initJobs() {
       jobsCollection.find(eq("job", "blacksmith")).forEach((Block<? super Document>) doc -> {
           Recipe recipe = GsonFactory.getPrettyGson().fromJson(doc.getString("recipe"), Recipe.class);
           /*System.out.println(doc.getString("recipe"));
           System.out.println(recipe);*/
           Blacksmith.addRecipe(recipe.getName(), recipe);
       });

    }

    public MongoCollection<Document> getTreasuresCollection() {
        return treasuresCollection;
    }

    public MongoCollection<Document> getRessourcesCollection() {
        return ressourcesCollection;
    }

    public MongoCollection<Document> getJobsCollection() {
        return jobsCollection;
    }

    public MongoCollection<Document> getEntityCollecion() {
        return entityCollecion;
    }
}
