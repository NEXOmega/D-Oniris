package dandelion.oniris.listerners;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import dandelion.oniris.Oniris;
import dandelion.oniris.packets.CustomPacketManager;
import dandelion.oniris.packets.server.SPacketDiscordUpdater;
import dandelion.oniris.packets.server.SPacketSpellLearned;
import dandelion.oniris.players.Infos;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.Stats;
import dandelion.oniris.players.advancements.ClassesAdvancements;
import dandelion.oniris.players.classes.ClassType;
import dandelion.oniris.utils.FonctionUtils;
import dandelion.oniris.utils.ItemType;
import dandelion.oniris.utils.ItemUtils;
import dandelion.oniris.utils.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.EnumUtils;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.util.Arrays;

public class PlayerJoinLeave implements Listener {

    private Oniris main;

    public PlayerJoinLeave(Oniris main) {
        this.main = main;
    }
    PlayerStats dStats;
    @EventHandler
    public void join(PlayerJoinEvent e) {
        if(main.getConnectToDB().hasPlayer(e.getPlayer().getUniqueId())) {
            PlayerStats stats = main.getConnectToDB().getPlayerStats(e.getPlayer().getUniqueId());
            stats.getInfos().setLastPlayed(LocalDateTime.now());
            stats.init();
            stats.updateHealthBar();
            stats.getBar().addPlayer(e.getPlayer());
            stats.getEnnemyBar().addPlayer(e.getPlayer());
            e.getPlayer().sendMessage("§eData Loaded & Updated !");
            main.getPlayers().put(e.getPlayer(), stats);
            this.dStats = stats;
        } else {

            PlayerStats pStats = new PlayerStats(new Infos(e.getPlayer().getName(),
                    LocalDateTime.now(), LocalDateTime.now(),
                    0),
                    new Stats(5, 20, 20, 5 , 5), ClassType.VILLAGER, ClassType.VILLAGER);

            main.getPlayers().put(e.getPlayer(), pStats);
            e.getPlayer().getInventory().addItem(ItemUtils.GRIMOIRE.getItem().getItemStack());

            pStats.setHealth(1);
            pStats.init();
            pStats.getBar().addPlayer(e.getPlayer());
            pStats.getEnnemyBar().addPlayer(e.getPlayer());
            e.getPlayer().sendMessage("§eProfile Created !");
            main.getConnectToDB().addPlayer(e.getPlayer().getUniqueId(), pStats);
            e.getPlayer().sendMessage("/progress pour choisir votre classe");
            double vit = pStats.getHealth() / pStats.getMaxVitality();
            pStats.getBar().setProgress(vit);
            this.dStats = pStats;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                CustomPacketManager.sendCustomPacket(e.getPlayer(), new SPacketDiscordUpdater("En jeu !", "Oh que oui", "large", "That work", "large", dStats.getClassType().getClassName() + " Lvl : " + e.getPlayer().getLevel(), 0, true));
                for(String s : dStats.getSpells())
                    CustomPacketManager.sendCustomPacket(e.getPlayer(), new SPacketSpellLearned(s));

                main.getAdvancementManager().addPlayer(e.getPlayer());
                main.getAdvancementManager().loadProgress(e.getPlayer(), e.getPlayer().getUniqueId().toString());
            }
        }.runTaskLater(main, 2);
    }

    @EventHandler
    public void leave(PlayerQuitEvent e) {
        PlayerStats stats = main.getPlayers().get(e.getPlayer());
        if(stats.getPet() != null) {
            if(stats.getPet().isAlive())
                stats.getPet().killEntity();
        }
        if(stats.getPartyUUID() != null)
            main.getPartyManager().leftParty(stats.getPartyUUID(), e.getPlayer());
        //main.getAdvancementManager().saveProgress(e.getPlayer(),"dandelion");
        //main.getAdvancementManager().saveProgress(e.getPlayer(),"party");
//        main.getPlayers().get(e.getPlayer()).getInfos().setPlayerTime(e.getPlayer().getPlayerTime());
        main.getConnectToDB().updatePlayer(e.getPlayer().getUniqueId(), stats);
        main.getPlayers().remove(e.getPlayer());

        main.getAdvancementManager().saveProgress(e.getPlayer(), e.getPlayer().getUniqueId().toString());

        e.getPlayer().getNearbyEntities(1,1,1).forEach(entity -> {
            if(entity instanceof ArmorStand) {
                if(entity.hasMetadata("isTitle")){
                    entity.remove();
                }
            }
        });
    }


    private void openClass(Player p, PlayerStats stats) {
        Gui gui = new Gui( 1, "Selection de classe");
        gui.setOnGlobalClick(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        OutlinePane pane = new OutlinePane(9,1);
        pane.addItem(new GuiItem(FonctionUtils.createItem(ChatColor.translateAlternateColorCodes('&', "&eMagicien"),
                Material.WOODEN_SHOVEL, ItemType.DISPLAY_ITEM, Ranks.COMMON,1, 1, null).getItemStack()));
        pane.addItem(new GuiItem(FonctionUtils.createItem(ChatColor.translateAlternateColorCodes('&', "&eBerserk"),
                Material.WOODEN_SWORD, ItemType.DISPLAY_ITEM, Ranks.COMMON,1, 1, null).getItemStack()));
        pane.addItem(new GuiItem(FonctionUtils.createItem(ChatColor.translateAlternateColorCodes('&', "&eArcher"),
                Material.BOW, ItemType.DISPLAY_ITEM, Ranks.COMMON,1, 1, null).getItemStack()));
        pane.setOnClick(inventoryClickEvent -> {
            if(EnumUtils.isValidEnum(ClassType.class, ChatColor.stripColor(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().toUpperCase()))) {
                stats.setClassType(ClassType.valueOf(ChatColor.stripColor(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().toUpperCase())));
            }
        });
        gui.addPane(pane);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            /*main.getAdvancementManager().grantAdvancement(p,main.getAdvancementManager().getAdvancement(new NameKey("dandelion","root")));
            if(stats.getPlayerClass().equals(ClassType.VILLAGER)) {
                p.openInventory(main.getGui().getClasseSelectionGui().inv());
            }*/
            if(stats.getClassType().equals(ClassType.VILLAGER)) {
              gui.show(p);
            }
        }, 5,5);
    }
}
