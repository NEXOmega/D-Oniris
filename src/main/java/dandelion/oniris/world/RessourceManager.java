package dandelion.oniris.world;

import com.mongodb.Block;
import dandelion.oniris.Oniris;
import dandelion.oniris.database.GsonFactory;
import dandelion.oniris.players.PlayerStats;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

public class RessourceManager implements Listener {
    public LinkedHashMap<Location, Ressource> ressources;
    public LinkedHashMap<Location, Long> cooldown;

    Oniris oniris;

    public RessourceManager(Oniris oniris) {
        this.oniris = oniris;
        this.ressources = new LinkedHashMap<>();
        this.cooldown = new LinkedHashMap<>();

        init();
    }

    public void init() {
        oniris.getConnectToDB().getRessourcesCollection().find().forEach((Block<? super Document>) doc -> {
            System.out.println(doc);
            Ressource stats = GsonFactory.getPrettyGson().fromJson(doc.getString("ressource"), (Type) Ressource.class);
            Location loc = GsonFactory.getPrettyGson().fromJson(doc.getString("location"), Location.class);
            ressources.put(loc, stats);
        });
        System.out.println(ressources);
    }

    @EventHandler
    public void onBreakRessource(BlockBreakEvent e) {
        PlayerStats stats = oniris.getPlayers().get(e.getPlayer());
        if(ressources.containsKey(e.getBlock().getLocation())) {
            if(stats.getJobStats().getRessourceJob(e.getBlock()).getLevel() >= ressources.get(e.getBlock().getLocation()).requiredLevel) {
                e.setCancelled(true);
                ressources.get(e.getBlock().getLocation()).loot(e.getPlayer());
                System.out.println("Ressource drop !");
            }
        }
    }

    public void addRessource(org.bukkit.block.Block block, ItemStack drop, String jobName, int requiredLevel, int respawnTime) {
        Ressource r = new Ressource(block.getType().name(), drop, jobName, requiredLevel, respawnTime);
        /*t.addReward(new ItemStack(Material.SPONGE), 1, 1);
        t.addReward(new ItemStack(Material.PRISMARINE), 1, 1);*/
        ressources.put(block.getLocation(), r);
        oniris.getConnectToDB().addRessource(r, block.getLocation());
    }
}
