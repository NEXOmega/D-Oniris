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

public class TheFool extends Support {
    public TheFool() {
        super("The Fool", Ranks.MYTHIC, "Invalide toute magie peut importe la source.");
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
        if(e.getSender().getLocation().distance(e.getTarget().getLocation()) < e.getType().getTier() * 10)
            e.getSpell().setCanceled(true);
    }

    @Override
    public void onTick() {

    }

    @Override
    public void onDie() {

    }
}
