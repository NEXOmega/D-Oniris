package dandelion.oniris.players.classes.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.events.onHitBySpell;
import dandelion.oniris.events.onSpellSense;
import dandelion.oniris.particles.Ring;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.Stats;
import dandelion.oniris.players.classes.ClassType;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.spell.SpellType;
import dandelion.oniris.players.support.Support;
import dandelion.oniris.utils.Ranks;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.CloudEffect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class WorldChampion extends RPGClass {
    public WorldChampion() {
        super("World Champion",new Stats(18, 16,7,12,2),new ItemStack(Material.DIAMOND_AXE),new String[]{"firework","leap", "propulse", "berserkmode", "despairaura", "locate", "portalspell"},
                new String[]{"sword", "broadsword", "axe", "mace", "spear", "gauntlets", "katana", "dagger", "scythe"});
        this.setSupport(new WorldChampionSupport());
    }

    @Override
    public void onClassSelected(Player player, PlayerStats playerStats) {
        Oniris.instance.getServer().broadcast(ChatColor.RED + "Un nouveau World Champion a été élu.", "Que la gloire le présèrve");
    }

    class WorldChampionSupport extends Support {
        public WorldChampionSupport() {
            super("World Champion", Ranks.MYTHIC, "Le pouvoir du champion coule en toi...");
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
        public void onHitBySpell(onHitBySpell e, Player player, PlayerStats stats, Entity caster, SpellType type, Spell s) {

        }

        @Override
        public void onSpellSense(onSpellSense e) {

        }

        @Override
        public void onTick() {
        }

        @Override
        public void onDie(Entity damager, Entity damaged, PlayerStats playerStats) {
            if(damager instanceof Player) {
                PlayerStats damagerStats = Oniris.getInstance().getPlayers().get(damager);
                Oniris.getInstance().getpUtils().setNewClass((Player) damager, damagerStats, ClassType.WORLDCHAMPION);
                Oniris.getInstance().getpUtils().setNewClass((Player) damaged, playerStats, playerStats.getPreviousClassType());
            }
        }

        public void onRespawn(PlayerRespawnEvent e) {
        }
    }
}
