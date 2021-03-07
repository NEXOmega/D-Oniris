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

public class MagicSensor extends Support {
    public MagicSensor() {
        super("Magic Sensor", Ranks.RARE, "Detecte les sorts lancé aux alentours");
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {

    }

    @Override
    public void onLaunchSpell(Player player, PlayerStats stats, SpellType type, Spell s) {

    }

    @Override
    public void onHit(EntityDamageByEntityEvent event, PlayerStats stats, Player p) {

    }

    @Override
    public void onShootArrow(EntityShootBowEvent event) {

    }

    @Override
    public void onHitByEntity(EntityDamageByEntityEvent entityEvent) {

    }

    @Override
    public void onHitBySpell(onHitBySpell e, Player player, PlayerStats stats, Entity caster, SpellType type,  Spell s) {

    }

    @Override
    public void onSpellSense(onSpellSense e) {
        e.getTarget().sendMessage("Attention on a utilise " + e.getType().getName());
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
