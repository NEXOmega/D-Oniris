package dandelion.oniris.players.support;

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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class NoLongerHuman extends Support {
    public NoLongerHuman() {
        super("No Longer Human", Ranks.MYTHIC, "The ability annuls the skills of anyone he touches");
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {

    }

    @Override
    public void onLaunchSpell(Player player, PlayerStats stats, SpellType type, Spell spell) {

    }

    @Override
    public void onHit(EntityDamageByEntityEvent event, PlayerStats stats, Player p) {
        if(event.getEntity() instanceof Player) {
            PlayerStats pStats = oniris.getPlayers().get(event.getEntity());
            pStats.addBuff(SupportType.MAGICSICKNESS.getSupport(), 2*1000L);
        }
    }

    @Override
    public void onShootArrow(EntityShootBowEvent event) {

    }

    @Override
    public void onHitByEntity(EntityDamageByEntityEvent entityEvent) {
        if(entityEvent.getDamager() instanceof Player) {
            PlayerStats pStats = oniris.getPlayers().get(entityEvent.getDamager());
            pStats.addBuff(SupportType.MAGICSICKNESS.getSupport(), 2*1000L);
        }
    }

    @Override
    public void onHitBySpell(onHitBySpell e, Player player, PlayerStats stats, Entity caster, SpellType s, Spell spell) {

    }

    @Override
    public void onSpellSense(onSpellSense e) {

    }

    @Override
    public void onTick() {

    }

    @Override
    public void onDie(Entity damager, Entity damaged, PlayerStats playerStats) {

    }

    public void onRespawn(PlayerRespawnEvent e) {}

    public void onJoin(PlayerJoinEvent e, PlayerStats playerStats){}
}
