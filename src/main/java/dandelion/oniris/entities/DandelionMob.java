package dandelion.oniris.entities;

import dandelion.oniris.Oniris;
import dandelion.oniris.entities.pathfinders.MoveTowardsTargetIfRange;
import dandelion.oniris.entities.pathfinders.PathfinderGoalPet;
import dandelion.oniris.players.Stats;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.support.Support;
import dandelion.oniris.utils.FonctionUtils;
import net.minecraft.server.v1_16_R2.*;
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

    MobType type;

    long respawnTime;
    boolean respawn = true;

    Class<? extends Entity> entityClass;
    EntityTypes entityTypes;

    public DandelionMob(String mob_name, int level, int rewardXp, String name, Stats stats, ArrayList<ItemStack> equipements, ArrayList<Class<? extends Spell>> spells, ArrayList<Support> supports, MobType type, long respawnTime, boolean respawn, Class<? extends Entity> entityClass, EntityTypes entityTypes) {
        this.mob_name = mob_name;
        this.level = level;
        this.rewardXp = rewardXp;
        this.name = name;
        this.stats = stats;
        this.equipements = equipements;
        this.spells = spells;
        this.supports = supports;
        this.type = type;
        this.respawnTime = respawnTime;
        this.respawn = respawn;
        this.entityClass = entityClass;
        this.entityTypes = entityTypes;
        this.mobLocations = new HashMap<>();
    }

    public DandelionMob(String mob_name, int level, int rewardXp, String name, Stats stats, ArrayList<ItemStack> equipements, ArrayList<Class<? extends Spell>> spells, ArrayList<Support> supports, MobType type, long respawnTime, boolean respawn, Class<? extends Entity> entityClass, EntityTypes entityTypes, HashMap<String, Location> mobLocations) {
        this.mob_name = mob_name;
        this.level = level;
        this.rewardXp = rewardXp;
        this.name = name;
        this.stats = stats;
        this.equipements = equipements;
        this.spells = spells;
        this.supports = supports;
        this.type = type;
        this.respawnTime = respawnTime;
        this.respawn = respawn;
        this.entityClass = entityClass;
        this.entityTypes = entityTypes;
        this.mobLocations = mobLocations;
    }

    public void spawnAsMob(Location location, String uuid) {
        if(location.isWorldLoaded()) {
            System.out.println("DD Name : " + this);
            EntityInsentient entity = FonctionUtils.createLivingEntity(entityClass, uuid, location, this);

            entity.goalSelector.a(1, new PathfinderGoalMeleeAttack((EntityCreature) entity,1,true));
            entity.goalSelector.a(2, new MoveTowardsTargetIfRange((EntityCreature) entity, 1,100, location));
            entity.targetSelector.a(1, new PathfinderGoalHurtByTarget((EntityCreature) entity));
            entity.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(entity, EntityHuman.class, true));

            entity.setCustomName(new ChatComponentText(name));
        } else {
            Oniris.getInstance().getEntityManager().respawn(this.getMob_name(), uuid, false);
        }
    }

    public void spawnAsPet(Location location, Player owner, String uuid) {
        if(location.isWorldLoaded()) {
            //TODO Faire de l'ordre et automatiser grace au MobType
            EntityInsentient entity = FonctionUtils.createLivingEntity(entityClass, uuid, location, this);
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

    public MobType getType() {
        return type;
    }

    public void setType(MobType type) {
        this.type = type;
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
}
