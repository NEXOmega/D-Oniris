package dandelion.oniris.players.support;

import dandelion.oniris.Oniris;
import dandelion.oniris.events.onHitBySpell;
import dandelion.oniris.events.onSpellSense;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.spell.SpellType;
import dandelion.oniris.utils.Ranks;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public abstract class Support {
    Oniris oniris = Oniris.getInstance();
    String name;
    Ranks rank;
    String description;
    HashMap<String, Object> dataMap;
    public Support(String name, Ranks rank, String description) {
        this.name = name;
        this.rank = rank;
        this.description = description;
        this.dataMap = new HashMap<>();
    }

    public Support(String name, Ranks rank, String description, HashMap<String, Object> dataMap) {
        this.name = name;
        this.rank = rank;
        this.description = description;
        this.dataMap = dataMap;

    }

    public abstract void onRespawn(PlayerRespawnEvent e);
    public abstract void onInteract(PlayerInteractEvent event);
    public abstract void onLaunchSpell(Player player, PlayerStats stats, SpellType type, Spell s);
    public abstract void onHit(EntityDamageByEntityEvent event, PlayerStats stats, Player p);
    public abstract void onShootArrow(EntityShootBowEvent event);
    public abstract void onHitByEntity(EntityDamageByEntityEvent entityEvent);
    public abstract void onHitBySpell(onHitBySpell e, Player player, PlayerStats stats, Entity caster, SpellType type, Spell s);
    public abstract void onSpellSense(onSpellSense e);
    public abstract void onTick();
    public abstract void onDie(Entity damager, Entity damaged, PlayerStats playerStats);

    public String getName() {
        return name;
    }

    public Ranks getRank() {
        return rank;
    }

    public String getDescription() {
        return description;
    }

    public Support addData(String key, Object value) {
        this.setDataMap(key, value);
        return this;
    }


    public void setDataMap(@NotNull String s, @NotNull Object value) {
        dataMap.put(s, value);
    }


    public @NotNull Object getData(@NotNull String s) {
        return dataMap.get(s);
    }

    public boolean hasData(@NotNull String s) {
        return dataMap.containsKey(s);
    }

    public void removeMetadata(@NotNull String s) {
        dataMap.remove(s);
    }
}
