package dandelion.oniris.entities;

import dandelion.oniris.players.Stats;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.support.Support;
import dandelion.oniris.utils.FonctionUtils;
import net.minecraft.server.v1_16_R2.Entity;
import net.minecraft.server.v1_16_R2.EntityTypes;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class DandelionMob {
    int level, rewardXp;
    String name;
    Stats stats;
    ArrayList<ItemStack> equipements;
    ArrayList<Spell> spells;
    ArrayList<Support> supports;

    MobType type;

    Location spawnPoint;
    long respawnTime;

    Class<? extends Entity> entityClass;
    EntityTypes entityTypes;

    public DandelionMob(String name, Location spawnPoint,EntityTypes entityTypes, Class<? extends Entity> entityClass) {
        this.name = name;
        this.spawnPoint = spawnPoint;
        this.entityClass = entityClass;
        this.entityTypes = entityTypes;
    }

    public void spawn() {
        if(spawnPoint.isWorldLoaded()) {
            LivingEntity entity = FonctionUtils.createLivingEntity(entityClass, spawnPoint, this);
            entity.setCustomName(name);
        }
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

    public ArrayList<Spell> getSpells() {
        return spells;
    }

    public void setSpells(ArrayList<Spell> spells) {
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

    public Location getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Location spawnPoint) {
        this.spawnPoint = spawnPoint;
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
}
