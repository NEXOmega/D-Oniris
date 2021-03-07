package dandelion.oniris.world;

import com.mongodb.Block;
import dandelion.oniris.Oniris;
import dandelion.oniris.database.GsonFactory;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.utils.DandelionItem;
import dandelion.oniris.utils.FonctionUtils;
import dandelion.oniris.utils.ItemUtils;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

public class RessourceManager implements Listener {
    public LinkedHashMap<Location, Ressource> ressources;
    public LinkedHashMap<Ressource, Long> cooldown;

    Oniris oniris;

    public RessourceManager(Oniris oniris) {
        this.oniris = oniris;
        this.ressources = new LinkedHashMap<>();
        this.cooldown = new LinkedHashMap<>();

        init();
    }

    public void init() {
        oniris.getConnectToDB().getRessourcesCollection().find().forEach((Block<? super Document>) doc -> {
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
            e.setCancelled(true);

            if(e.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) {
                e.getPlayer().sendMessage(ChatColor.RED + "Vous ne pouvez pas récolter à main nue.");
                return;
            }

            DandelionItem handItem = ItemUtils.getDandelionItem(e.getPlayer().getInventory().getItemInMainHand());
            if(handItem.getLevel() != null && handItem.getLevel() < stats.getLevel()) {
                e.getPlayer().sendMessage(ChatColor.RED + "Vous n'avez pas le niveau requis pour cet outil.");
                return;
            }

            Ressource r = ressources.get(e.getBlock().getLocation());
            if(cooldown.containsKey(r) && cooldown.get(r) > System.currentTimeMillis() + r.respawnTime) {
                e.getPlayer().sendMessage("Cette ressource n'est pas encore récoltable.");
                return;
            }
            int handItemLevel = handItem.getJobLevel() != null ? ItemUtils.getDandelionItem(e.getPlayer().getInventory().getItemInMainHand()).getJobLevel().getJobFromName(r.jobName) : 0;

            if(stats.getJobStats().getRessourceJob(e.getBlock()).getLevel() + handItemLevel >= r.requiredLevel) {
                r.loot(e.getPlayer());
                cooldown.put(r, System.currentTimeMillis() + r.respawnTime*1000);
            } else {
                e.getPlayer().sendMessage(ChatColor.RED + "Vous devez être niveau " + r.getRequiredLevel() + " en " + r.jobName +" pour récolter cette ressource.");
            }
        }
    }

    public void createRessource(String name, org.bukkit.block.Block block, ItemStack drop, String jobName, int requiredLevel, int respawnTime) {
        Ressource r = new Ressource(name, block.getType().name(), drop, jobName, requiredLevel, respawnTime);
        ressources.put(block.getLocation(), r);
        oniris.getConnectToDB().addRessource(r, block.getLocation());
    }

    public void addToRessource(Location block, ItemStack itemStack) {
        ressources.get(block).setDrop(itemStack);
        oniris.getConnectToDB().updateRessource(ressources.get(block), block);
    }

    public boolean isRessource(Location location) {
        if(ressources.containsKey(location))
            return true;
        return false;
    }

    public void deleteRessource(Location location) {
        Ressource r = ressources.get(location);
        if(cooldown.containsKey(r))
            cooldown.remove(r);
        ressources.remove(location);
        oniris.getConnectToDB().deleteRessource(location);
    }
}
