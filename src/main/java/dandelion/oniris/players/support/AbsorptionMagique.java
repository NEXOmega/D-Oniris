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

public
class AbsorptionMagique extends Support {
    public AbsorptionMagique() {
        super("Absorption Magique", Ranks.UNOBTAINABLE, "Absorbe tout les dégats magiques.");
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {

    }

    @Override
    public void onLaunchSpell(Player player, PlayerStats stats, SpellType s, Spell spell) {

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
    public void onHitBySpell(onHitBySpell e, Player player, PlayerStats stats, Entity caster, SpellType s, Spell spell) {
        player.sendMessage("Dégats magiques absorbés !");
        e.setDamages(0);
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
