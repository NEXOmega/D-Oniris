package dandelion.oniris.players.support;

import dandelion.oniris.events.onHitBySpell;
import dandelion.oniris.events.onSpellSense;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.spell.SpellType;
import dandelion.oniris.utils.Ranks;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class InfiniteArrow extends Support {
    ItemStack arrow = new ItemStack(Material.ARROW);
    public InfiniteArrow() {
        super("INFINITEARROW", Ranks.UNCOMMON, "Permet d'utiliser son Arc sans flèche");
        arrow.getItemMeta().getPersistentDataContainer().set(NamespacedKey.minecraft("support"), PersistentDataType.INTEGER, 1);
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        if(event.getItem().getType().equals(Material.BOW)) {
            if(event.getPlayer().getInventory().getItemInOffHand().getType().equals(Material.AIR)) {
                event.getPlayer().getInventory().setItemInOffHand(arrow);
            }
        }
    }

    @Override
    public void onLaunchSpell(Player player, PlayerStats stats, SpellType s, Spell spell) {

    }

    @Override
    public void onHit(EntityDamageByEntityEvent event, PlayerStats stats, Player p) {

    }

    @Override
    public void onShootArrow(EntityShootBowEvent event) {
        if(((Player)event.getEntity()).getInventory().getItemInOffHand().getType().equals(Material.AIR)) return;
        if(((Player)event.getEntity()).getInventory().getItemInOffHand().getType().equals(Material.ARROW)) {
            ItemStack arrowOff = ((Player)event.getEntity()).getInventory().getItemInOffHand();
            if(arrowOff.hasItemMeta()) {
                if(arrowOff.getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("support"), PersistentDataType.INTEGER)) {
                    if(arrowOff.getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("support"), PersistentDataType.INTEGER).equals(1)) {
                        ((Player)event.getEntity()).getInventory().setItemInOffHand(new ItemStack(Material.AIR));
                    }
                }
            }
        }
    }

    @Override
    public void onHitByEntity(EntityDamageByEntityEvent entityEvent) {

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
}
