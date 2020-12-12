package dandelion.oniris.players.support;

import dandelion.oniris.events.onHitBySpell;
import dandelion.oniris.events.onSpellSense;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.spell.SpellType;
import dandelion.oniris.utils.Ranks;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Invidia extends Support {
    public Invidia() {
        super("INVIDIA", Ranks.UNOBTAINABLE, "Vous apprends tout les sorts lancé contre vous");
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
    public void onHitBySpell(onHitBySpell e, Player player, PlayerStats stats, Entity entity, SpellType type, Spell spell) {
        stats.getSpells().add(type.getName());
        player.sendMessage(ChatColor.DARK_RED + "Vous avez appris le sort : " + type.getRank().getChatColor() + type.getName());
    }

    @Override
    public void onSpellSense(onSpellSense e) {

    }

    @Override
    public void onTick() {

    }

    @Override
    public void onDie() {

    }
}
