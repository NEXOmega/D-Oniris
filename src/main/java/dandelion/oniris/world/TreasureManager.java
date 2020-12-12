package dandelion.oniris.world;

import com.mongodb.Block;
import dandelion.oniris.Oniris;
import dandelion.oniris.database.GsonFactory;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

public class TreasureManager implements Listener {
    public LinkedHashMap<Location, Treasure> treasures;
    public LinkedHashMap<Location, Long> cooldown;

    Oniris oniris;

    public TreasureManager(Oniris oniris) {
        this.oniris = oniris;
        this.treasures = new LinkedHashMap<>();
        this.cooldown = new LinkedHashMap<>();

        init();
    }

    public void init() {
        oniris.getConnectToDB().getTreasuresCollection().find().forEach((Block<? super Document>) doc -> {
            System.out.println(doc);
            Treasure stats = GsonFactory.getPrettyGson().fromJson(doc.getString("treasure"), (Type) Treasure.class);
            Location loc = GsonFactory.getPrettyGson().fromJson(doc.getString("location"), Location.class);
            treasures.put(loc, stats);
        });
        System.out.println(treasures);
    }

    @EventHandler
    public void onClickTreasure(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getClickedBlock().getType() == Material.CHEST) {
                if(treasures.containsKey(e.getClickedBlock().getLocation())) {
                    //e.setCancelled(true);
                    if(cooldown.containsKey(e.getClickedBlock().getLocation())){
                        if(cooldown.get(e.getClickedBlock().getLocation()) > System.currentTimeMillis()) {
                            e.getPlayer().sendMessage(ChatColor.YELLOW + "Ce coffre sera rafraichit dans : " +((cooldown.get(e.getClickedBlock().getLocation()) -System.currentTimeMillis()) / 1000) +"s");
                            return;
                        }
                    }
                    e.getPlayer().sendMessage(ChatColor.YELLOW + "Vous avez trouvé un trésor !");
                    Treasure treasure = treasures.get(e.getClickedBlock().getLocation());
                    Chest chest = (Chest) e.getClickedBlock().getState();
                    chest.getBlockInventory().clear();
                    for (Treasure.Reward r: treasure.getRewards()) {
                        chest.getBlockInventory().addItem(r.item);
                    }
                    /*Gui gui = new Gui(oniris, 1, "Tresor");
                    OutlinePane pane = new OutlinePane(0 , 0 , 9, 1);
                    for (Treasure.Reward r: treasure.getRewards()) {
                        pane.addItem(new GuiItem(r.item, event -> {
                            event.setCancelled(true);
                        }));
                    }
                    gui.setOnClose(event -> {
                        for (GuiItem item: gui.getPanes().get(0).getItems()) {
                            System.out.println(item.getItem().getItemMeta());
                            e.getPlayer().getWorld().dropItem(e.getPlayer().getLocation(), item.getItem());
                        }
                    });
                    gui.addPane(pane);
                    gui.show(e.getPlayer());*/
                    System.out.println(treasure.respawnTime + " : " + treasure.respawnTime * 1000 + " : " + ((treasure.respawnTime*1000)+System.currentTimeMillis()));
                    System.out.println(System.currentTimeMillis());
                    cooldown.put(e.getClickedBlock().getLocation(), ((treasure.respawnTime*1000)+System.currentTimeMillis()));
                }
            }
        }
    }

    public void addTreasure(Location loc, int respawnTime,int itemNumber, boolean isRandom) {
        Treasure t = new Treasure(respawnTime, itemNumber, isRandom);
        /*t.addReward(new ItemStack(Material.SPONGE), 1, 1);
        t.addReward(new ItemStack(Material.PRISMARINE), 1, 1);*/
        treasures.put(loc, t);
        oniris.getConnectToDB().addTreasure(t, loc);
    }

    public void addReward(Location loc, ItemStack itemStack, int chance, int chanceToGetMultiple) {
        Treasure treasure = treasures.get(loc);
        treasure.addReward(itemStack, chance, chanceToGetMultiple);
        treasures.replace(loc, treasure);
        System.out.println(treasure);
        oniris.getConnectToDB().updateTreasure(treasure, loc);
    }
}
