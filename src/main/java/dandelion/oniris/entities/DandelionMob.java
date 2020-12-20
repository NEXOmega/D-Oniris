package dandelion.oniris.entities;

import dandelion.oniris.Oniris;
import dandelion.oniris.entities.entity.DandelionEntity;
import dandelion.oniris.entities.pathfinders.MoveTowardsTargetIfRange;
import dandelion.oniris.entities.pathfinders.PathfinderGoalPet;
import dandelion.oniris.players.Stats;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.support.Support;
import dandelion.oniris.utils.FonctionUtils;
import dandelion.oniris.world.Treasure;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class DandelionMob {
    String mob_name;
    int level, rewardXp;
    String name;
    Stats stats;
    ArrayList<ItemStack> equipements;
    ArrayList<Class<? extends Spell>> spells;
    ArrayList<Support> supports;
    HashMap<String, Location> mobLocations;
    ArrayList<Treasure.Reward> rewardList;
    boolean baby = false;

    long respawnTime;
    boolean respawn = true;

    Class<? extends Entity> entityClass;
    EntityTypes entityTypes;
    MobType type;

    public DandelionMob(String mob_name, MobType type, int level, int rewardXp, String name, Stats stats, ArrayList<ItemStack> equipements, ArrayList<Class<? extends Spell>> spells, ArrayList<Support> supports, long respawnTime, boolean respawn, Class<? extends Entity> entityClass, EntityTypes entityTypes) {
        this.mob_name = mob_name;
        this.level = level;
        this.rewardXp = rewardXp;
        this.name = name;
        this.stats = stats;
        this.equipements = equipements;
        this.spells = spells;
        this.supports = supports;
        this.respawnTime = respawnTime;
        this.respawn = respawn;
        this.entityClass = entityClass;
        this.entityTypes = entityTypes;
        this.mobLocations = new HashMap<>();
        this.rewardList = new ArrayList<>();
        this.type = type;
    }

    public DandelionMob(String mob_name, MobType type, int level, int rewardXp, String name, Stats stats, ArrayList<ItemStack> equipements, ArrayList<Class<? extends Spell>> spells, ArrayList<Support> supports, long respawnTime, boolean respawn, Class<? extends Entity> entityClass, EntityTypes entityTypes, HashMap<String, Location> mobLocations, ArrayList<Treasure.Reward> rewardList, boolean baby) {
        this.mob_name = mob_name;
        this.level = level;
        this.rewardXp = rewardXp;
        this.name = name;
        this.stats = stats;
        this.equipements = equipements;
        this.spells = spells;
        this.supports = supports;
        this.respawnTime = respawnTime;
        this.respawn = respawn;
        this.entityClass = entityClass;
        this.entityTypes = entityTypes;
        this.mobLocations = mobLocations;
        this.rewardList = rewardList;
        this.type = type;
        this.baby = baby;
    }

    public void spawnAsMob(Location location, String uuid) {
        if(location.isWorldLoaded()) {
            EntityInsentient entity = FonctionUtils.createLivingEntity(entityTypes, entityClass, type, uuid, location, this);
            if(type.equals(MobType.HOSTILE)) {
                entity.goalSelector.a(1, new PathfinderGoalMeleeAttack((EntityCreature) entity,this.stats.getSpeed(),true));
                entity.goalSelector.a(2, new MoveTowardsTargetIfRange((EntityCreature) entity, this.stats.getSpeed(),100, location));
                entity.goalSelector.a(3, new PathfinderGoalRandomStrollLand((EntityCreature) entity,this.stats.getSpeed()));
                entity.targetSelector.a(1, new PathfinderGoalHurtByTarget((EntityCreature) entity));
                entity.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(entity, EntityHuman.class, true));
            } else if(type.equals(MobType.PASSIVE)) {
                entity.goalSelector.a(1, new PathfinderGoalMeleeAttack((EntityCreature) entity,this.stats.getSpeed(),true));
                entity.goalSelector.a(1, new MoveTowardsTargetIfRange((EntityCreature) entity, this.stats.getSpeed(),100, location));
                entity.goalSelector.a(3, new PathfinderGoalRandomStrollLand((EntityCreature) entity,this.stats.getSpeed()));
                entity.targetSelector.a(1, new PathfinderGoalHurtByTarget((EntityCreature) entity));
            } else if(type.equals(MobType.INOFFENSIVE)) {
                entity.goalSelector.a(1, new PathfinderGoalRandomStrollLand((EntityCreature) entity,this.stats.getSpeed()));
                entity.goalSelector.a(2, new PathfinderGoalPanic((EntityCreature) entity, this.getStats().getSpeed()));
                entity.targetSelector.a(1, new PathfinderGoalHurtByTarget((EntityCreature) entity));
                entity.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(entity, EntityHuman.class, true));
            } else if(type.equals(MobType.RANGED_HOSTILE)) {
                //var1 = damages var3 = speed var 4 = distance from target
                entity.goalSelector.a(1, new PathfinderGoalArrowAttack((DandelionEntity)entity, this.getStats().getSpeed(), 20, 10.0F));
                entity.targetSelector.a(1, new PathfinderGoalHurtByTarget((EntityCreature) entity));
                entity.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(entity, EntityHuman.class, true));
            }else if(type.equals(MobType.RANGED_PASSIVE)) {
                //var1 = move speed var3 = speed var 4 = distance from target
                entity.goalSelector.a(1, new PathfinderGoalArrowAttack((DandelionEntity)entity, this.getStats().getSpeed(), 20, 10.0F));
                entity.targetSelector.a(1, new PathfinderGoalHurtByTarget((EntityCreature) entity));
            }else if(type.equals(MobType.PATHTEST)) {
            }
            entity.setCustomName(new ChatComponentText(ChatColor.GRAY + name + ChatColor.RED + " ["+level+"]"));
            entity.setCustomNameVisible(true);
        } else {
            Oniris.getInstance().getEntityManager().respawn(this.getMob_name(), uuid, false);
        }
    }

    public void spawnAsPet(Location location, Player owner, String uuid) {
        if(location.isWorldLoaded()) {
            EntityInsentient entity = FonctionUtils.createLivingEntity(entityTypes, entityClass, MobType.INOFFENSIVE, uuid, location, this);
            entity.setInvulnerable(true);
            this.setRespawn(false);
            entity.goalSelector.a(0, new PathfinderGoalFloat(entity));
            entity.goalSelector.a(1,new PathfinderGoalPet(entity, 2,25));
            entity.goalSelector.a(2, new PathfinderGoalLookAtPlayer(entity, EntityHuman.class, 8.0F));
            entity.setGoalTarget(((CraftPlayer) owner).getHandle(), EntityTargetEvent.TargetReason.CUSTOM, true);
            entity.setCustomName(new ChatComponentText(owner.getDisplayName() + " Familier"));

            Oniris.getInstance().getPlayers().get(owner).setPet(entity);
        } else {
            Oniris.getInstance().getEntityManager().respawn(this.getMob_name(), uuid, false);
        }

    }

    public String getMob_name() {
        return mob_name;
    }

    public void setMob_name(String mob_name) {
        this.mob_name = mob_name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRewardXp() {
        return rewardXp;
    }

    public void setRewardXp(int rewardXp) {
        this.rewardXp = rewardXp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public ArrayList<ItemStack> getEquipements() {
        return equipements;
    }

    public void setEquipements(ArrayList<ItemStack> equipements) {
        this.equipements = equipements;
    }

    public ArrayList<Class<? extends Spell>> getSpells() {
        return spells;
    }

    public void setSpells(ArrayList<Class<? extends Spell>> spells) {
        this.spells = spells;
    }

    public ArrayList<Support> getSupports() {
        return supports;
    }

    public void setSupports(ArrayList<Support> supports) {
        this.supports = supports;
    }

    public long getRespawnTime() {
        return respawnTime;
    }

    public void setRespawnTime(long respawnTime) {
        this.respawnTime = respawnTime;
    }

    public Class<? extends Entity> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<? extends Entity> entityClass) {
        this.entityClass = entityClass;
    }

    public EntityTypes getEntityTypes() {
        return entityTypes;
    }

    public void setEntityTypes(EntityTypes entityTypes) {
        this.entityTypes = entityTypes;
    }

    public boolean isRespawn() {
        return respawn;
    }

    public void setRespawn(boolean respawn) {
        this.respawn = respawn;
    }

    public HashMap<String, Location> getMobLocations() {
        return mobLocations;
    }

    public void setMobLocations(HashMap<String, Location> mobLocations) {
        this.mobLocations = mobLocations;
    }

    public ArrayList<Treasure.Reward> getRewardList() {
        return rewardList;
    }

    public void setDropMap(ArrayList<Treasure.Reward> rewardList) {
        this.rewardList = rewardList;
    }

    public void addDrop(Treasure.Reward reward) {
        this.getRewardList().add(reward);
    }

    public MobType getType() {
        return type;
    }

    public void setType(MobType type) {
        this.type = type;
    }

    public boolean isBaby() {
        return baby;
    }

    public void setBaby(boolean baby) {
        this.baby = baby;
    }
}
