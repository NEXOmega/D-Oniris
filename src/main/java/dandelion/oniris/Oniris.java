package dandelion.oniris;

import dandelion.oniris.commands.PartyCommands;
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
import dandelion.oniris.database.DatabaseConnect;
import dandelion.oniris.entities.EntityManager;
import dandelion.oniris.jobs.JobManager;
import dandelion.oniris.listerners.*;
import dandelion.oniris.packets.CustomPacketManager;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.PlayerUtils;
import dandelion.oniris.players.advancements.ClassesAdvancements;
import dandelion.oniris.players.parties.PartyManager;
import dandelion.oniris.players.spell.SpellManager;
import dandelion.oniris.world.RessourceManager;
import dandelion.oniris.world.TickManager;
import dandelion.oniris.world.TreasureManager;
import dandelion.soundapi.SoundAPI;
import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;
import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.CrazyAdvancements;
import eu.endercentral.crazy_advancements.manager.AdvancementManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Oniris extends JavaPlugin {

    public static Oniris instance;
    public static Oniris getInstance() {
        return instance;
    }
    public static EffectManager em = new EffectManager(getEffectLib());

    private final Map<Player, PlayerStats> players = new HashMap<>();

    SpellManager spellManager = new SpellManager(this);
    TreasureManager treasureManager;
    RessourceManager ressourceManager;
    JobManager jobManager;
    EntityManager entityManager;
    AdvancementManager advancementManager = CrazyAdvancements.getNewAdvancementManager();
    PartyManager partyManager;
    MenuGui gui;
    PlayerUtils pUtils;
    DatabaseConnect connectToDB = new DatabaseConnect();

    SoundAPI soundApi;

    @Override
    public void onEnable() {
        instance = this;
        gui = new MenuGui(this);
        soundApi = SoundAPI.getInstance();

        Objects.requireNonNull(getCommand("playerinfos")).setExecutor(new PlayerInfos(this));
        Objects.requireNonNull(getCommand("spell")).setExecutor(new LaunchSpell(this));
        Objects.requireNonNull(getCommand("menu")).setExecutor(new PlayerMenuCommands(this));
        Objects.requireNonNull(getCommand("rpgitem")).setExecutor(new RPGItemCommand(this));
        Objects.requireNonNull(getCommand("progress")).setExecutor(new PlayerProgress(this));
        Objects.requireNonNull(getCommand("test")).setExecutor(new TestCommand(this));
        Objects.requireNonNull(getCommand("treasure")).setExecutor(new TreasuresCommands(this));
        Objects.requireNonNull(getCommand("ressource")).setExecutor(new RessourcesCommand(this));
        Objects.requireNonNull(getCommand("editor")).setExecutor(new EditorCommand(this));
        Objects.requireNonNull(getCommand("showitem")).setExecutor(new ShowItemCommand());
        Objects.requireNonNull(getCommand("buff")).setExecutor(new BuffCommand(this));
        Objects.requireNonNull(getCommand("ajob")).setExecutor(new AdminJobCommands(this));
        Objects.requireNonNull(getCommand("job")).setExecutor(new JobCommand(this));
        Objects.requireNonNull(getCommand("entity")).setExecutor(new EntityCommand(this));
        Objects.requireNonNull(getCommand("party")).setExecutor(new PartyCommands(this));

        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new AdminToolsEventListener(this), this);
        pm.registerEvents(new PlayerChatListener(), this);
        pm.registerEvents(new PlayerGuiEvent(this), this);
        pm.registerEvents(new PlayerUseSpecialItemEvent(this), this);
        pm.registerEvents(new PlayerStatsEvent(this), this);
        pm.registerEvents(new PlayerRightClickEvent(this), this);
        pm.registerEvents(new PlayerJoinLeave(this), this);
        pm.registerEvents(new CustomCraftsEvent(this), this);
        pm.registerEvents(new WorldEventListener(this), this);
        this.treasureManager = new TreasureManager(this);
        pm.registerEvents(treasureManager, this);
        this.ressourceManager = new RessourceManager(this);
        pm.registerEvents(ressourceManager, this);
        this.jobManager = new JobManager(this);
        this.partyManager = new PartyManager(this);
        pm.registerEvents(partyManager, this);

        this.entityManager = new EntityManager(this);


        pUtils = new PlayerUtils(this);
        this.getServer().getScheduler().runTaskTimer(this, new TickManager(this), 0L, 1L);

        CustomPacketManager.registerPackets();

        ClassesAdvancements.registerAdvancements(advancementManager);

        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
                PlayerStats stats = this.getConnectToDB().getPlayerStats(player.getUniqueId());
                stats.getInfos().setLastPlayed(LocalDateTime.now());
                stats.init();
                stats.updateHealthBar();
                Objects.requireNonNull(player.getPlayer()).sendMessage("§eData Loaded & Updated !");
                this.getPlayers().put(player, stats);

        });

        getEntityManager().loadEntities();
    }

    @Override
    public void onDisable() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            this.getConnectToDB().updatePlayer(p.getUniqueId(), this.getPlayers().get(p));
            this.getPlayers().remove(p);
        }

        getEntityManager().resetEntities();
    }

    public void progressAdvancement(Player p, Advancement advancement, int criterion) {
        int progress = advancementManager.getCriteriaProgress(p, advancement);
        if(progress < advancement.getCriteria())
            advancementManager.setCriteriaProgress(p, advancement, progress+criterion);
    }

    public static EffectManager getEffectManager() {
        return em;
    }

    public static EffectLib getEffectLib() {
        Plugin effectLib = Bukkit.getPluginManager().getPlugin("EffectLib");
        if (!(effectLib instanceof EffectLib)) {
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

    public AdvancementManager getAdvancementManager() {
        return advancementManager;
    }

    public PartyManager getPartyManager() {
        return partyManager;
    }
}
