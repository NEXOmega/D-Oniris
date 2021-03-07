package dandelion.oniris.players;

import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.events.onHitBySpell;
import dandelion.oniris.events.onSpellSense;
import dandelion.oniris.jobs.JobStats;
import dandelion.oniris.packets.CustomPacketManager;
import dandelion.oniris.packets.server.SPacketSpellCasted;
import dandelion.oniris.players.classes.ClassType;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.spell.SpellType;
import dandelion.oniris.players.support.Support;
import dandelion.oniris.players.support.SupportType;
import net.minecraft.server.v1_16_R2.EntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class PlayerStats {
    transient BossBar bar;
    transient BossBar ennemyBar;
    transient LinkedList<String> spells;
    transient LinkedHashMap<Support, Long> buffs = new LinkedHashMap();
    transient EntityLiving pet;

    Infos infos;
    Stats stats;
    Stats equipementStats;
    ClassType classType;
    ClassType previousClassType;
    ItemStack[] equipements;
    JobStats jobStats;
    ArrayList<PortalStone> portalStones;
    SupportType support;
    double health;
    private int statsPoint, level;

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public ClassType getClassType() {
        return classType;
    }

    public ClassType getPreviousClassType() {
        return previousClassType;
    }

    public void setPreviousClassType(ClassType previousClassType) {
        this.previousClassType = previousClassType;
    }

    public PlayerStats(Infos infos, Stats stats, ClassType classType, ClassType previousClassType) {
        this.infos = infos;
        this.stats = stats;
        this.classType = classType;
        this.previousClassType = previousClassType;
        this.equipementStats = new Stats(0,0,0,0, 0);
        this.equipements = new ItemStack[8];
        this.jobStats = new JobStats();
        this.level = 0;
        this.support = SupportType.BERSERKMODE;

        this.portalStones = new ArrayList<>();
        this.portalStones.add(new PortalStone(ChatColor.YELLOW +"Camp de base", Material.CAMPFIRE, new Location(Bukkit.getWorld("world"),303, 78, 15)));

        spells = new LinkedList<>();
        spells.addAll(Arrays.asList(classType.getSpellList()));
    }

    public PlayerStats(Infos infos, Stats stats, Stats equipementStats, ClassType classType, ClassType previousClassType, ItemStack[] equipements, JobStats jobStats, ArrayList<PortalStone> portalStones, double health, int statsPoint) {
        this.infos = infos;
        this.stats = stats;
        this.equipementStats = equipementStats;
        this.classType = classType;
        this.previousClassType = previousClassType;
        this.equipements = equipements;
        this.jobStats = new JobStats();
        this.jobStats = jobStats;
        this.portalStones = portalStones;
        this.health = health;
        this.statsPoint = statsPoint;
        this.support = SupportType.BERSERKMODE;

        spells = new LinkedList<>();
        spells.addAll(Arrays.asList(classType.getSpellList()));
    }

    public PlayerStats(LinkedList<String> spells, Infos infos, Stats stats, Stats equipementStats, ClassType classType, ClassType previousClassType, ItemStack[] equipements, JobStats jobStats, ArrayList<PortalStone> portalStones, SupportType support, double health, int statsPoint) {
        this.spells = spells;
        this.infos = infos;
        this.stats = stats;
        this.equipementStats = equipementStats;
        this.classType = classType;
        this.previousClassType = previousClassType;
        this.equipements = equipements;
        this.jobStats = jobStats;
        this.portalStones = portalStones;
        this.support = support;
        this.health = health;
        this.statsPoint = statsPoint;
        this.level = level;
    }

    public SupportType getSupportType() {
        return support;
    }

    public void setSupportType(SupportType support) {
        this.support = support;
    }

    public ArrayList<PortalStone> getPortalStones() {
        return portalStones;
    }

    public BossBar getBar() {
        return bar;
    }

    public void setBar(BossBar bar) {
        this.bar = bar;
    }

    public void updateHealthBar() {
        this.bar.setTitle(ChatColor.YELLOW + "Vie " + ChatColor.RED + "{" + ChatColor.YELLOW + Math.round(this.getHealth()) + ChatColor.RED + "/" + ChatColor.YELLOW + Math.round(this.getMaxVitality()) + ChatColor.RED + "}");
        this.bar.setProgress((getHealth()/getMaxVitality() < 1 ? getHealth()/getMaxVitality() : 1));
    }

    public BossBar getEnnemyBar() {
        return ennemyBar;
    }

    public void setEnnemyBar(BossBar ennemyBar) {
        this.ennemyBar = ennemyBar;
    }

    public void updateEnnemyBar(LivingEntity entity, DandelionMob mob) {
        this.ennemyBar.setTitle(ChatColor.YELLOW + mob.getName() + ChatColor.RED + " {" + ChatColor.YELLOW + Math.round(entity.getHealth()) + ChatColor.RED + "/" + ChatColor.YELLOW + Math.round(mob.getStats().getVitality()) + ChatColor.RED + "}");
        this.ennemyBar.setProgress((entity.getHealth()/mob.getStats().getVitality() < 1 ? entity.getHealth()/mob.getStats().getVitality() : 1));
    }

    public LinkedList<String> getSpells() {
        return spells;
    }

    public void setSpells(LinkedList<String> spells) {
        this.spells = spells;
    }

    public Infos getInfos() {
        return infos;
    }

    public Stats getStats() {
        return stats;
    }

    public Stats getEquipementStats() {
        return equipementStats;
    }

    public void setEquipementStats(Stats equipementStats) {
        this.equipementStats = equipementStats;
    }

    public ItemStack[] getEquipements() {
        return equipements;
    }

    public void setEquipements(ItemStack[] equipementsLore) {
        this.equipements = equipementsLore;
    }

    public JobStats getJobStats() {
        return jobStats;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public int getStatsPoint() {
        return statsPoint;
    }

    public void setStatsPoint(int statsPoint) {
        this.statsPoint = statsPoint;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void init() {
        bar = Bukkit.createBossBar(ChatColor.YELLOW + "" + ChatColor.BOLD +"Life", BarColor.BLUE, BarStyle.SEGMENTED_6);
        ennemyBar = Bukkit.createBossBar(ChatColor.YELLOW +"Mob Life", BarColor.GREEN,BarStyle.SEGMENTED_6);
        ennemyBar.setVisible(false);
    }

    public void setPortalStones(ArrayList<PortalStone> portalStones) {
        this.portalStones = portalStones;
    }

    public float getMaxAttack() {
        if(( stats.getStrength() + equipementStats.getStrength() + classType.getStats().getStrength()) < 1) {
            return classType.getStats().getStrength();
        }
        return stats.getStrength() + equipementStats.getStrength() + classType.getStats().getStrength();
    }

    public double calculateDamages(double weaponDamages) {
        return weaponDamages * ((((stats.getStrength()*2)+(classType.getStats().getStrength()*3)+equipementStats.getStrength())/100)+1);
    }

    public float getMaxDefence(){
        if((stats.getDefence() + equipementStats.getDefence() + classType.getStats().getDefence()) < 1) {
            return classType.getStats().getDefence();
        }
        return stats.getDefence() + equipementStats.getDefence() + classType.getStats().getDefence();
    }

    public float getMaxVitality() {
        if((stats.getVitality() + equipementStats.getVitality() + classType.getStats().getVitality()) < 1) {
            return classType.getStats().getVitality();
        }
        return stats.getVitality() + equipementStats.getVitality() + classType.getStats().getVitality();
    }

    public float getMaxSpeed() {
        if((stats.getSpeed() + equipementStats.getSpeed() + classType.getStats().getSpeed() < 1)) {
            return classType.getStats().getSpeed();
        }
        return stats.getSpeed() + equipementStats.getSpeed() + classType.getStats().getSpeed();
    }

    public float getMaxMagie() {
        if((stats.getMagie() + equipementStats.getMagie() + classType.getStats().getMagie()) < 1) {
            return classType.getStats().getMagie();
        }
        return stats.getMagie() + equipementStats.getMagie() + classType.getStats().getMagie();
    }

    //Buffs Part
    public void addBuff(Support support, Long duration) {
        this.buffs.put(support, System.currentTimeMillis() + duration);
    }

    public void removeBuff(Support support) {
        this.buffs.remove(support);
    }

    public LinkedHashMap<Support, Long> getBuffs() {
        return buffs;
    }

    public void onInteract(PlayerInteractEvent event) {
        if(this.getSupportType().getSupport() != null){
            this.getSupportType().getSupport().onInteract(event);
        }
        if(this.getClassType().getRpgClass().getSupport() != null)
            this.getClassType().getRpgClass().getSupport().onInteract(event);

        ArrayList<Support> toRemove = new ArrayList<>();
        buffs.forEach((support1, aLong) -> {
            if(aLong > System.currentTimeMillis()) {
                support1.onInteract(event);
            } else {
                toRemove.add(support1);
            }
        });
        toRemove.forEach(e -> buffs.remove(e));
    }

    public void onLaunchSpell(Player player, PlayerStats stats, SpellType type, Spell spell) {
        if(stats.getSupportType().getSupport() != null)
            stats.getSupportType().getSupport().onLaunchSpell(player, stats, type, spell);
        if(stats.getClassType().getRpgClass().getSupport() != null)
            stats.getClassType().getRpgClass().getSupport().onLaunchSpell(player, stats, type, spell);

        ArrayList<Support> toRemove = new ArrayList<>();
        buffs.forEach((support1, aLong) -> {
            if(aLong > System.currentTimeMillis()) {
                support1.onLaunchSpell(player, stats, type, spell);
            } else {
                toRemove.add(support1);
            }
        });

        CustomPacketManager.sendCustomPacket(player, new SPacketSpellCasted(type.getName(), (int) type.getDelay()));
    }

    public void onHit(EntityDamageByEntityEvent event, Player p, PlayerStats stats) {
        if(this.getSupportType().getSupport() != null)
            this.getSupportType().getSupport().onHit(event, stats, p);
        if(this.getClassType().getRpgClass().getSupport() != null)
            this.getClassType().getRpgClass().getSupport().onHit(event, stats, p);

        ArrayList<Support> toRemove = new ArrayList<>();
        buffs.forEach((support1, aLong) -> {
            if(aLong > System.currentTimeMillis()) {
                support1.onHit(event, stats, p);
            } else {
                toRemove.add(support1);
            }
        });
        toRemove.forEach(e -> buffs.remove(e));
    }

    public void onShootArrow(EntityShootBowEvent event) {
        if(this.getSupportType().getSupport() != null)
            this.getSupportType().getSupport().onShootArrow(event);
        if(this.getClassType().getRpgClass().getSupport() != null)
            this.getClassType().getRpgClass().getSupport().onShootArrow(event);

        ArrayList<Support> toRemove = new ArrayList<>();
        buffs.forEach((support1, aLong) -> {
            if(aLong > System.currentTimeMillis()) {
                support1.onShootArrow(event);
            } else {
                toRemove.add(support1);
            }
        });
        toRemove.forEach(e -> buffs.remove(e));
    }

    public void onHitByEntity(EntityDamageByEntityEvent entityEvent) {
        if(this.getSupportType().getSupport() != null)
            this.getSupportType().getSupport().onHitByEntity(entityEvent);
        if(this.getClassType().getRpgClass().getSupport() != null)
            this.getClassType().getRpgClass().getSupport().onHitByEntity(entityEvent);

        ArrayList<Support> toRemove = new ArrayList<>();
        buffs.forEach((support1, aLong) -> {
            if(aLong > System.currentTimeMillis()) {
                support1.onHitByEntity( entityEvent);
            } else {
                toRemove.add(support1);
            }
        });
        toRemove.forEach(e -> buffs.remove(e));
    }

    public void onHitBySpell(onHitBySpell e, Player player, PlayerStats stats, Entity caster, SpellType type, Spell spell) {
        if(this.getSupportType().getSupport() != null)
            this.getSupportType().getSupport().onHitBySpell(e, player, this, caster, type, spell);
        if(this.getClassType().getRpgClass().getSupport() != null)
            this.getClassType().getRpgClass().getSupport().onHitBySpell(e, player, this, caster, type, spell);

        ArrayList<Support> toRemove = new ArrayList<>();
        buffs.forEach((support1, aLong) -> {
                if(aLong > System.currentTimeMillis()) {
                    support1.onHitBySpell(e, player, this, caster, type,spell);
                } else {
                    toRemove.add(support1);
                }

        });
        toRemove.forEach(ez -> buffs.remove(ez));
    }

    public void onSpellSense(onSpellSense event) {
        if(this.getSupportType().getSupport() != null)
            this.getSupportType().getSupport().onSpellSense(event);
        if(this.getClassType().getRpgClass().getSupport() != null)
            this.getClassType().getRpgClass().getSupport().onSpellSense(event);
    }

    public void onDie(Entity damager, Entity damaged, PlayerStats stats) {
        if(this.getSupportType().getSupport() != null)
            this.getSupportType().getSupport().onDie(damager, damaged, stats);
        if(this.getClassType().getRpgClass().getSupport() != null)
            this.getClassType().getRpgClass().getSupport().onDie(damager, damaged, stats);

        ArrayList<Support> toRemove = new ArrayList<>();
        buffs.forEach((support1, aLong) -> {
            if(aLong > System.currentTimeMillis()) {
                support1.onDie(damager, damaged, stats);
            } else {
                toRemove.add(support1);
            }
        });
        toRemove.forEach(e -> buffs.remove(e));
    }

    public void onTick() {
        if(this.getSupportType().getSupport() != null)
            this.getSupportType().getSupport().onTick();
        if(this.getClassType().getRpgClass().getSupport() != null)
            this.getClassType().getRpgClass().getSupport().onTick();

        ArrayList<Support> toRemove = new ArrayList<>();
        buffs.forEach((support1, aLong) -> {
            if(aLong > System.currentTimeMillis()) {
                support1.onTick();
            } else {
                toRemove.add(support1);
            }
        });
        toRemove.forEach(e -> buffs.remove(e));
    }

    public void onRespawn(PlayerRespawnEvent e) {
        if(this.getSupportType().getSupport() != null)
            this.getSupportType().getSupport().onRespawn(e);
        if(this.getClassType().getRpgClass().getSupport() != null)
            this.getClassType().getRpgClass().getSupport().onRespawn(e);

        ArrayList<Support> toRemove = new ArrayList<>();
        buffs.forEach((support1, aLong) -> {
            if(aLong > System.currentTimeMillis()) {
                support1.onRespawn(e);
            } else {
                toRemove.add(support1);
            }
        });
        toRemove.forEach(a -> buffs.remove(a));
    }

    public EntityLiving getPet() {
        return pet;
    }

    public void setPet(EntityLiving pet) {
        this.pet = pet;
    }
}
