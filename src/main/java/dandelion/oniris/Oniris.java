package dandelion.oniris;

import com.mongodb.Block;
import dandelion.oniris.commands.PlayerInfos;
import dandelion.oniris.commands.TestCommand;
import dandelion.oniris.commands.admin.AdminJobCommands;
import dandelion.oniris.commands.admin.EditorCommand;
import dandelion.oniris.commands.equipemetns.RPGItemCommand;
import dandelion.oniris.commands.equipemetns.ShowItemCommand;
import dandelion.oniris.commands.gui.MenuGui;
import dandelion.oniris.commands.spell.LaunchSpell;
import dandelion.oniris.commands.stats.BuffCommand;
import dandelion.oniris.commands.stats.JobCommand;
import dandelion.oniris.commands.stats.PlayerMenuCommands;
import dandelion.oniris.commands.stats.PlayerProgress;
import dandelion.oniris.commands.world.EntityCommand;
import dandelion.oniris.commands.world.RessourcesCommand;
import dandelion.oniris.commands.world.TreasuresCommands;
import dandelion.oniris.database.ConvertToDoc;
import dandelion.oniris.database.DatabaseConnect;
import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.entities.EntityManager;
import dandelion.oniris.entities.entity.DandelionEntity;
import dandelion.oniris.jobs.JobManager;
import dandelion.oniris.listerners.*;
import dandelion.oniris.packets.CustomPacketManager;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.PlayerUtils;
import dandelion.oniris.players.spell.SpellManager;
import dandelion.oniris.world.RessourceManager;
import dandelion.oniris.world.TickManager;
import dandelion.oniris.world.TreasureManager;
import dandelion.soundapi.SoundAPI;
import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public final class Oniris extends JavaPlugin {

    public static Oniris instance;
    public static Oniris getInstance() {
        return instance;
    }
    public static EffectManager em = new EffectManager(getEffectLib());

    private Map<Player, PlayerStats> players = new HashMap<>();

    SpellManager spellManager = new SpellManager(this);
    TreasureManager treasureManager;
    RessourceManager ressourceManager;
    JobManager jobManager;
    EntityManager entityManager;
    MenuGui gui;
    PlayerUtils pUtils;
    DatabaseConnect connectToDB = new DatabaseConnect();

    SoundAPI soundApi;
    @Override
    public void onEnable() {
        instance = this;
        gui = new MenuGui(this);
        soundApi = SoundAPI.getInstance();

        getCommand("playerinfos").setExecutor(new PlayerInfos(this));
        getCommand("spell").setExecutor(new LaunchSpell(this));
        getCommand("menu").setExecutor(new PlayerMenuCommands(this));
        getCommand("rpgitem").setExecutor(new RPGItemCommand(this));
        getCommand("progress").setExecutor(new PlayerProgress(this));
        getCommand("test").setExecutor(new TestCommand(this));
        getCommand("treasure").setExecutor(new TreasuresCommands(this));
        getCommand("ressource").setExecutor(new RessourcesCommand(this));
        getCommand("editor").setExecutor(new EditorCommand(this));
        getCommand("showitem").setExecutor(new ShowItemCommand());
        getCommand("buff").setExecutor(new BuffCommand(this));
        getCommand("ajob").setExecutor(new AdminJobCommands(this));
        getCommand("job").setExecutor(new JobCommand(this));
        getCommand("entity").setExecutor(new EntityCommand(this));

        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new AdminToolsEventListener(this), this);
        pm.registerEvents(new PlayerChatListener(), this);
        pm.registerEvents(new PlayerGuiEvent(this), this);
        pm.registerEvents(new PlayerUseSpellBookEvent(this), this);
        pm.registerEvents(new PlayerStatsEvent(this), this);
        pm.registerEvents(new PlayerRightClickEvent(this), this);
        pm.registerEvents(new PlayerJoinLeave(this), this);
        pm.registerEvents(new CustomCraftsEvent(this), this);
        pm.registerEvents(new WorldEventListener(), this);
        this.treasureManager = new TreasureManager(this);
        pm.registerEvents(treasureManager, this);
        this.ressourceManager = new RessourceManager(this);
        pm.registerEvents(ressourceManager, this);
        this.jobManager = new JobManager(this);

        this.entityManager = new EntityManager(this);

        pUtils = new PlayerUtils(this);
        this.getServer().getScheduler().runTaskTimer(this, new TickManager(this), 0L, 1L);

        CustomPacketManager.registerPackets();

        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
                PlayerStats stats = this.getConnectToDB().getPlayerStats(player.getUniqueId());
                stats.getInfos().setLastPlayed(LocalDateTime.now());
                stats.init();
                double vit = stats.getHealth() / stats.getMaxVitality();
                stats.updateHealthBar();
                player.getPlayer().sendMessage("§eData Loaded & Updated !");
                this.getPlayers().put(player, stats);

        });

        this.getConnectToDB().getEntityCollecion().find().forEach((Block<? super Document>) e -> {
            DandelionMob mob = ConvertToDoc.docToEntity(e);
            this.getEntityManager().addEntity(mob.getMob_name(), mob);
            mob.getMobLocations().forEach((uuid, loc) -> {
                this.getEntityManager().addEntityLocation(uuid, mob.getMob_name(), loc);
                mob.spawnAsMob(loc, uuid);
            });
        });
    }

    @Override
    public void onDisable() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            this.getConnectToDB().updatePlayer(p.getUniqueId(), this.getPlayers().get(p));
            this.getPlayers().remove(p);
            /*p.getNearbyEntities(1,1,1).forEach(entity -> {
                if(entity instanceof ArmorStand) {
                    if(entity.hasMetadata("isTitle")){
                        entity.remove();
                    }
                }
            });*/
            //p.kickPlayer("Restarting");
        }

        for(World world : Bukkit.getServer().getWorlds()) {
            for(Entity entity : world.getEntities()) {
                if(((CraftEntity)entity).getHandle() instanceof DandelionEntity) {
                    ((DandelionEntity)((CraftEntity)entity).getHandle()).setRespawn(false);

                    entity.remove();
                }
            }
        }
    }



    public static EffectManager getEffectManager() {
        return em;
    }

    public static EffectLib getEffectLib() {
        Plugin effectLib = Bukkit.getPluginManager().getPlugin("EffectLib");
        if (effectLib == null || !(effectLib instanceof EffectLib)) {
            return null;
        }
        return (EffectLib)effectLib;
    }

    public PlayerUtils getpUtils() {
        return pUtils;
    }

    public Map<Player, PlayerStats> getPlayers() {
        return players;
    }

    public SpellManager getSpellManager() {
        return spellManager;
    }

    public MenuGui getGui() {
        return gui;
    }

    public DatabaseConnect getConnectToDB() {
        return connectToDB;
    }

    public TreasureManager getTreasureManager() {
        return treasureManager;
    }

    public RessourceManager getRessourceManager() {
        return ressourceManager;
    }

    public JobManager getJobManager() {
        return jobManager;
    }

    public SoundAPI getSoundApi() {
        return soundApi;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
