package dandelion.oniris.players.spell.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.events.onHitBySpell;
import dandelion.oniris.events.onSpellSense;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.spell.SpellType;
import dandelion.oniris.players.support.Support;
import dandelion.oniris.utils.Ranks;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.LinkedList;

public class PortalTrap extends Spell {
    private PortalTrapSupport PortalTrapSupport;

    @Override
    public void launch(Player sender, Oniris oniris, PlayerStats data) {
        if(data.getBuffs().containsKey(this)) {
            sender.getNearbyEntities(10,10,10).forEach(e -> {
                if(e instanceof Player)
                    PortalTrapSupport.addPlayer((Player) e);
            });
        } else {
            delay = 0;
            PortalTrapSupport = new PortalTrapSupport(10*1000, sender);
            data.addBuff(PortalTrapSupport, (long) (60*1000));
        }
    }

    static class PortalTrapSupport extends Support {
        public LinkedList<Player> playersVictim;
        final long timeBeforeTeleport;
        final Player target;
        public PortalTrapSupport(long timeBeforeTeleport, Player target) {
            super("Portal Trap", Ranks.MYTHIC, "Teleport a group of player to you after a cooldown");
            this.timeBeforeTeleport = System.currentTimeMillis() + timeBeforeTeleport;
            this.target = target;
            playersVictim = new LinkedList<>();
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
            if(entityEvent.getDamager() instanceof Player)
                if(playersVictim.contains(entityEvent.getDamager()))
                    playersVictim.clear();
        }

        @Override
        public void onHitBySpell(onHitBySpell e, Player player, PlayerStats stats, Entity caster, SpellType s, Spell spell) {

        }

        @Override
        public void onSpellSense(onSpellSense e) {

        }

        @Override
        public void onTick() {
            if(System.currentTimeMillis() == timeBeforeTeleport) {
                this.playersVictim.forEach(p -> p.teleport(target));
            }
        }

        @Override
        public void onDie(Entity damager, Entity damaged, PlayerStats playerStats) {

        }

        public void addPlayer(Player player) {
            this.playersVictim.add(player);
        }

        public void onRespawn(PlayerRespawnEvent e) {}
    }
}
