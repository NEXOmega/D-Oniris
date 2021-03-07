package dandelion.oniris.players.support;

import dandelion.oniris.events.onHitBySpell;
import dandelion.oniris.events.onSpellSense;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.spell.SpellType;
import dandelion.oniris.utils.Ranks;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Stealth extends Support {
    public Stealth() {
        super("STEALTH", Ranks.RARE, "Permet de ne pas utiliser les plaques de pression en pierre en vous accroupissant.");
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        if(event.getPlayer().isSneaking()) {
            if(event.getAction().equals(Action.PHYSICAL)) {
                if(event.getClickedBlock().getType().equals(Material.STONE_PRESSURE_PLATE)) {
                    event.setCancelled(true);
                }
            }
        }
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
    public void onHitBySpell(onHitBySpell e, Player player, PlayerStats stats, Entity entity, SpellType type,  Spell spell) {

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
