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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
/*
TODO: Finir l'implementation du Magician
 */
public class TheMagician extends Spell {

    @Override
    public void launch(Player sender, Oniris oniris, PlayerStats data) {
        data.addBuff(new TheMagicianSupport(), 60*1000L);
    }

    public static class TheMagicianSupport extends Support {
        ArrayList<String> spells;
        public TheMagicianSupport() {
            super("The Magician", Ranks.UNOBTAINABLE, "Protect from already hitted spell");
            spells = new ArrayList<>();
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
            if(spells.contains(s.getName()))
                e.setCancelled(true);
            else
                spells.add(s.getName());
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
}
