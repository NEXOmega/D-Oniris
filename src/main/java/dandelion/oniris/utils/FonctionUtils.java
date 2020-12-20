package dandelion.oniris.utils;

import dandelion.oniris.commands.equipemetns.WeaponsTypes;
import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.entities.MobType;
import dandelion.oniris.players.Stats;
import dandelion.oniris.players.spell.Elements;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.support.Support;
import dandelion.oniris.world.Treasure;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FonctionUtils {
    public static String capitalize(String str) {
        if(str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static DandelionItem createItem(String name, Material material, ItemType itemType, Ranks ranks, int amount, int modelData) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setCustomModelData(modelData);
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("itemtype"), PersistentDataType.STRING, itemType.name().toUpperCase());
        item.setAmount(amount);
        item.setItemMeta(meta);

        return new DandelionItem(item, name, null,null, null,null, itemType, ranks,null,null,null);
    }

    public static DandelionItem createItem(String name, Material material, ItemType itemType, Ranks ranks, int amount, int modelData, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.setCustomModelData(modelData);
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("itemtype"), PersistentDataType.STRING, itemType.name().toUpperCase());
        item.setAmount(amount);
        item.setItemMeta(meta);

        return new DandelionItem(item, name,new ArrayList<>(lore),null, null,null, itemType, ranks,null,null,null);
    }

    public static DandelionItem createItem(String name, Material material, ItemType itemType, Ranks ranks, int amount, int modelData, List<String> lore, HashMap<String, String> data) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.setCustomModelData(modelData);
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("itemtype"), PersistentDataType.STRING, itemType.name().toUpperCase());
        item.setAmount(amount);
        item.setItemMeta(meta);

        data.forEach((string, d) -> {
            item.getItemMeta().getPersistentDataContainer().set(NamespacedKey.minecraft(string), PersistentDataType.STRING, d);
        });

        return new DandelionItem(item, name, new ArrayList<>(lore),null, null,null, itemType, ranks,null,null,null);
    }

    public static String getDirection(Location pos1, Location pos2) {
        if(pos1.getX() > pos1.getX() && pos2.getZ() > pos1.getZ()) {
            return "Up Left";
        } else if(pos1.getX() > pos1.getX() && pos2.getZ() < pos1.getZ()) {
            return "Up Right";
        } else if(pos1.getX() < pos1.getX() && pos2.getZ() > pos1.getZ()) {
            return "Down Left";
        } else if(pos1.getX() < pos1.getX() && pos2.getZ() < pos1.getZ()) {
            return "Down Right";
        }
        return "Wtf Dude";
    }

    public static class StatsBuilder {
        float strength = 0, vitality = 0, magie = 0, defence = 0, speed = 0;

        public StatsBuilder setStrength(float strength) {
            this.strength = strength;
            return this;
        }

        public StatsBuilder setVitality(float vitality) {
            this.vitality = vitality;
            return this;
        }

        public StatsBuilder setMagie(float magie) {
            this.magie = magie;
            return this;
        }

        public StatsBuilder setDefence(float defence) {
            this.defence = defence;
            return this;
        }

        public StatsBuilder setSpeed(float speed) {
            this.speed = speed;
            return this;
        }

        public Stats build() {
            return new Stats(strength, vitality, magie, defence, speed);
        }
    }

    public static class DandelionItemBuilder {
        ItemStack itemStack;
        String name;
        ArrayList<String> description;
        Stats stats;
        Support support;
        WeaponsTypes types;
        ItemType itemType;
        Ranks ranks;
        Elements elements;
        Integer level;
        String spellList;

        public void setItemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(ArrayList<String> description) {
            this.description = description;
        }

        public void setStats(Stats stats) {
            this.stats = stats;
        }

        public void setSupport(Support support) {
            this.support = support;
        }

        public void setTypes(WeaponsTypes types) {
            this.types = types;
        }

        public void setItemType(ItemType itemType) {
            this.itemType = itemType;
        }

        public void setRanks(Ranks ranks) {
            this.ranks = ranks;
        }

        public void setElements(Elements elements) {
            this.elements = elements;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public void setSpellList(String spellList) {
            this.spellList = spellList;
        }

        public DandelionItem build() {
            return new DandelionItem(itemStack,name,description,stats,support,types,itemType, ranks, elements, level, spellList);
        }
    }

    public static EntityInsentient createLivingEntity(EntityTypes<? extends EntityCreature> entityType, Class<? extends Entity> type, MobType mobType, String uuid, Location loc, DandelionMob dandelionMob) {
        net.minecraft.server.v1_16_R2.World world = ((CraftWorld) (loc.getWorld())).getHandle();
        net.minecraft.server.v1_16_R2.EntityInsentient e = null;
        try {
            e = (EntityInsentient) (type.getDeclaredConstructor(EntityTypes.class, net.minecraft.server.v1_16_R2.World.class, String.class, DandelionMob.class, MobType.class).newInstance(entityType,  world, uuid, dandelionMob, mobType));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        e.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((LivingEntity)e.getBukkitEntity()).setRemoveWhenFarAway(false);
        world.addEntity(e, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return e;
    }

    public static class DandelionMobBuilder {
        String mob_name;
        int level, rewardXp;
        String name;
        Stats stats;
        ArrayList<ItemStack> equipements;
        ArrayList<Class<? extends Spell>> spells;
        ArrayList<Support> supports;

        long respawnTime;
        boolean respawn = true;

        Class<? extends Entity> entityClass;
        EntityTypes entityTypes;
        MobType mobType;

        boolean baby;

        public DandelionMobBuilder setMobName(String mob_name) {
            this.mob_name = mob_name;
            return this;
        }

        public DandelionMobBuilder setLevel(int level) {
            this.level = level;
            return this;
        }

        public DandelionMobBuilder setRewardXp(int rewardXp) {
            this.rewardXp = rewardXp;
            return this;
        }

        public DandelionMobBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public DandelionMobBuilder setStats(Stats stats) {
            this.stats = stats;
            return this;
        }

        public DandelionMobBuilder setEquipements(ArrayList<ItemStack> equipements) {
            this.equipements = equipements;
            return this;
        }

        public DandelionMobBuilder setSpells(ArrayList<Class<? extends Spell>> spells) {
            this.spells = spells;
            return this;
        }

        public DandelionMobBuilder setSupports(ArrayList<Support> supports) {
            this.supports = supports;
            return this;
        }

        public DandelionMobBuilder setRespawnTime(long respawnTime) {
            this.respawnTime = respawnTime;
            return this;
        }

        public DandelionMobBuilder setRespawn(boolean respawn) {
            this.respawn = respawn;
            return this;
        }

        public DandelionMobBuilder setEntityClass(Class<? extends Entity> entityClass) {
            this.entityClass = entityClass;
            return this;
        }

        public DandelionMobBuilder setEntityTypes(EntityTypes entityTypes) {
            this.entityTypes = entityTypes;
            return this;
        }

        public DandelionMobBuilder setBaby(boolean baby) {
            this.baby = baby;
            return this;
        }

        public DandelionMobBuilder setMobType(MobType mobType) {
            this.mobType = mobType;
            return this;
        }

        public DandelionMob build(){
            return new DandelionMob(mob_name, mobType, level,rewardXp,name,stats,equipements,spells,supports,respawnTime,respawn,entityClass,entityTypes);
        }

        public DandelionMob build(HashMap<String, Location> mobLocations, ArrayList<Treasure.Reward> rewardList) {
            return new DandelionMob(mob_name, mobType, level,rewardXp,name,stats,equipements,spells,supports,respawnTime,respawn,entityClass,entityTypes, mobLocations, rewardList, baby);
        }
    }

    private static Field attributeField;

    static {
        try {
            attributeField = net.minecraft.server.v1_16_R2.AttributeMapBase.class.getDeclaredField("b");
            attributeField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void registerGenericAttribute(org.bukkit.entity.Entity entity, Attribute attribute) throws IllegalAccessException {
        net.minecraft.server.v1_16_R2.AttributeMapBase attributeMapBase = ((org.bukkit.craftbukkit.v1_16_R2.entity.CraftLivingEntity)entity).getHandle().getAttributeMap();
        Map<AttributeBase, AttributeModifiable> map = (Map<net.minecraft.server.v1_16_R2.AttributeBase, net.minecraft.server.v1_16_R2.AttributeModifiable>) attributeField.get(attributeMapBase);
        net.minecraft.server.v1_16_R2.AttributeBase attributeBase = org.bukkit.craftbukkit.v1_16_R2.attribute.CraftAttributeMap.toMinecraft(attribute);
        net.minecraft.server.v1_16_R2.AttributeModifiable attributeModifiable = new net.minecraft.server.v1_16_R2.AttributeModifiable(attributeBase, net.minecraft.server.v1_16_R2.AttributeModifiable::getAttribute);
        map.put(attributeBase, attributeModifiable);
    }
}
