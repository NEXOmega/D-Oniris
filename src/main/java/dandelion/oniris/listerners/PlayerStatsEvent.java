package dandelion.oniris.listerners;

import dandelion.oniris.Oniris;
import dandelion.oniris.commands.equipemetns.WeaponsTypes;
import dandelion.oniris.entities.entity.DandelionEntity;
import dandelion.oniris.events.onHitBySpell;
import dandelion.oniris.events.onSpellSense;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.LocationSpell;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.spell.SpellType;
import dandelion.oniris.players.support.SupportType;
import dandelion.oniris.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.Objects;

public class PlayerStatsEvent implements Listener {
    Oniris oniris;
    public PlayerStatsEvent(Oniris oniris) {
        this.oniris = oniris;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) throws IllegalAccessException, InstantiationException {
        if(e.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) e.getDamager();
            if(projectile.hasMetadata("damages")) {
                e.setDamage(projectile.getMetadata("damages").get(0).asDouble());
                ((Player) projectile.getShooter()).sendMessage(String.valueOf(e.getDamage()));
            }
        }

        if(e.getDamager() instanceof Snowball) {
            Snowball snowball = (Snowball) e.getDamager();
            if(snowball.getPersistentDataContainer().has(NamespacedKey.minecraft("spell"), PersistentDataType.STRING)){
                Spell s = SpellType.valueOf(snowball.getPersistentDataContainer().get(NamespacedKey.minecraft("spell"), PersistentDataType.STRING).toUpperCase()).getSpell().newInstance();
                if(s instanceof LocationSpell) {
                    ((LocationSpell)s).launchFromLocation(e.getEntity().getLocation(), oniris,oniris.getPlayers().get((Player) snowball.getShooter()) );
                }
            }
            if(snowball.getShooter() instanceof Player) {
                PlayerStats stats = oniris.getPlayers().get(snowball.getShooter());
                //Graph Part
                Bukkit.getScheduler().scheduleSyncDelayedTask(oniris, () -> {
                    if(((CraftEntity)e.getEntity()).getHandle() instanceof DandelionEntity) {
                        stats.getEnnemyBar().setVisible(true);
                        stats.updateEnnemyBar((LivingEntity) e.getEntity(), ((DandelionEntity)((CraftEntity)e.getEntity()).getHandle()).getMob());
                    }
                },1);
            }

        }

        if(e.getDamager() instanceof Arrow) {
            AbstractArrow arrow = (AbstractArrow) e.getDamager();
            if(arrow.getShooter() instanceof Player) {
                PlayerStats stats = oniris.getPlayers().get(arrow.getShooter());

                //Graph Part
                Bukkit.getScheduler().scheduleSyncDelayedTask(oniris, () -> {
                    if(((CraftEntity)e.getEntity()).getHandle() instanceof DandelionEntity) {
                        System.out.println("test");
                        stats.getEnnemyBar().setVisible(true);
                        stats.updateEnnemyBar((LivingEntity) e.getEntity(), ((DandelionEntity)((CraftEntity)e.getEntity()).getHandle()).getMob());
                    }
                },1);
            }
        }

        if(e.getDamager() instanceof Player) {
            //Attacker part
            if(!e.getEntity().isDead() && !e.getEntity().isInvulnerable()) {
                if(!((Player) e.getDamager()).getItemInHand().equals(oniris.getPlayers().get((Player)e.getDamager()).getEquipements()[4])) {
                    e.getDamager().sendMessage(ChatColor.YELLOW + "" +ChatColor.BOLD + "Erreur, l'arme n'est pas equipe !");
                    e.setCancelled(true);
                    return;
                }
                PlayerStats stats = oniris.getPlayers().get(e.getDamager());
                stats.onHit(e, (Player) e.getDamager(), stats);
                //e.getDamager().sendMessage(String.format("Calculated: %s, weapon damages: %s, buffing: %s", stats.calculateDamages(e.getDamage()), e.getDamage(), stats.calculateDamages(e.getDamage())/e.getDamage()));
                e.setDamage(stats.calculateDamages(e.getDamage()));

                //Graph Part

                Bukkit.getScheduler().scheduleSyncDelayedTask(oniris, () -> {
                    if(((CraftEntity)e.getEntity()).getHandle() instanceof DandelionEntity) {
                        stats.getEnnemyBar().setVisible(true);
                        stats.updateEnnemyBar((LivingEntity) e.getEntity(), ((DandelionEntity)((CraftEntity)e.getEntity()).getHandle()).getMob());
                        if(e.getEntity().isDead())
                            stats.getEnnemyBar().setVisible(false);
                    }
                    },1);
            }
        }

        if(e.getEntity() instanceof Player) {
            //Damaged Part
            PlayerStats stats = oniris.getPlayers().get(e.getEntity());
            if(((Player) e.getEntity()).isHandRaised()) {
                if(((Player) e.getEntity()).getInventory().getItemInOffHand().getType().equals(Material.SHIELD)) {
                    ItemStack item = ((Player) e.getEntity()).getInventory().getItemInOffHand();
                    if(item.hasItemMeta()) {
                        if(Objects.requireNonNull(item.getItemMeta()).getPersistentDataContainer().has(NamespacedKey.minecraft("support"), PersistentDataType.STRING)) {
                            String support = item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("support"), PersistentDataType.STRING);
                            SupportType.valueOf(support.toUpperCase()).getSupport().onHitByEntity(e);
                        }
                    }
                }
            }


            stats.onHitByEntity(e);
            double def = stats.getMaxDefence();
            def = ((def / 100) + 1);
            if(((CraftEntity)e.getDamager()).getHandle() instanceof DandelionEntity)
                e.setDamage(((DandelionEntity)((CraftEntity)e.getDamager()).getHandle()).getMob().getStats().getStrength());
            stats.setHealth(stats.getHealth() - (e.getDamage() / def));

            //e.getEntity().sendMessage(e.getDamage() + ":" + def + "=" +(e.getDamage() / def) + "; " + stats.getHealth());
            e.setDamage(0);
            //Graph Part

            double vit = stats.getHealth() / stats.getMaxVitality();
            if(vit <= 0) {
                stats.onDie(e.getDamager(), e.getEntity(), stats);
                if(stats.getEnnemyBar().isVisible())
                    stats.getEnnemyBar().setVisible(false);
                oniris.getpUtils().killPlayer((Player) e.getEntity());
                return;
            }
            stats.updateHealthBar();
        }
    }

    /*@EventHandler
    public void onWalk(PlayerToggleSprintEvent e) {
        float spd = oniris.getPlayers().get(e.getPlayer()).getMaxSpeed();
        e.getPlayer().setWalkSpeed(e.getPlayer().getWalkSpeed() * ((spd /250) + 1));
    }*/

    @EventHandler
    public void cancelDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                e.setCancelled(true);
            } else if(e.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {
                e.setCancelled(true);
            } else if(e.getCause().equals(EntityDamageEvent.DamageCause.FIRE)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDead(EntityDeathEvent e) {
        e.getDrops().clear();
        if(e.getEntity().getKiller() instanceof Player) {
            if(oniris.getPlayers().get(e.getEntity().getKiller()).getEnnemyBar().isVisible())
                oniris.getPlayers().get(e.getEntity().getKiller()).getEnnemyBar().setVisible(false);
        }
    }

    @EventHandler
    public void onLevelUp(PlayerLevelChangeEvent e) {
        if(oniris.getPlayers().get(e.getPlayer()).getLevel() < e.getNewLevel()) {
            PlayerStats stats = oniris.getPlayers().get(e.getPlayer());
            stats.setLevel(e.getNewLevel());

            e.getPlayer().sendMessage(ChatColor.YELLOW+"Bravo, vous avez levelup");
            if((e.getNewLevel() % 3) == 0) {
                stats.setStatsPoint(stats.getStatsPoint() + 3);
                e.getPlayer().sendMessage(ChatColor.YELLOW + "Vous avez gagner 3 Stats point");
            }
        }
    }

    @EventHandler
    public void onHitBySpell(onHitBySpell e) {
        if(e.getDamagee() instanceof Player) {
            PlayerStats playerStats = oniris.getPlayers().get(e.getDamagee());
            if(((Player) e.getDamagee()).getInventory().getItemInOffHand().getType().equals(Material.SHIELD)) {
                ItemStack item = ((Player) e.getDamagee()).getInventory().getItemInOffHand();
                if(item.hasItemMeta()) {
                    if(Objects.requireNonNull(item.getItemMeta()).getPersistentDataContainer().has(NamespacedKey.minecraft("support"), PersistentDataType.STRING)) {
                        String support = item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("support"), PersistentDataType.STRING);
                        SupportType.valueOf(support.toUpperCase()).getSupport().onHitBySpell(e, (Player) e.getDamagee(), playerStats, e.getDamager(), e.getType(), e.getSpell());
                    }
                }
            }

            playerStats.onHitBySpell(e, (Player) e.getDamagee(), playerStats, e.getDamager(), e.getType(), e.getSpell());

            System.out.println("Damaged " + e.getDamages());
            PlayerStats stats = oniris.getPlayers().get(e.getDamagee());
            stats.setHealth(stats.getHealth() - calculateDamages(stats, e.getDamages()));
            e.setCancelled(true);
            //Graph Part

            double vit = stats.getHealth() / stats.getMaxVitality();
            if(vit <= 0) {
                stats.onDie(e.getDamager(), e.getDamagee(), stats);
                oniris.getpUtils().killPlayer((Player) e.getDamagee());
                return;
            }
            stats.updateHealthBar();
        }
    }

    @EventHandler
    public void onSpellSense(onSpellSense e) {
        oniris.getPlayers().get(e.getTarget()).onSpellSense(e);
    }

    @EventHandler
    public void playerSwitch(PlayerSwapHandItemsEvent event) {
        Player p = event.getPlayer();
        PlayerStats stats = oniris.getPlayers().get(event.getPlayer());

        if(stats.getEquipements()[4] != null) {
            ItemMeta meta = stats.getEquipements()[4].getItemMeta();
           String str = meta.getPersistentDataContainer().get(NamespacedKey.minecraft("weapon"), PersistentDataType.STRING);
           if(!(WeaponsTypes.valueOf(str.toUpperCase()).isDualHand())) {
               event.setCancelled(true);
               p.sendMessage("Votre arme ne vous permet pas d'utiliser vos 2 mains");
           }
        }
    }

    @EventHandler
    public void interactEvent(PlayerInteractEvent e) {
        PlayerStats stats = oniris.getPlayers().get(e.getPlayer());
        stats.onInteract(e);
    }

    @EventHandler
    public void useBow(EntityShootBowEvent e) {
        if(e.getEntity() instanceof Player) {
            PlayerStats stats = oniris.getPlayers().get(e.getEntity());
            if(((Player) e.getEntity()).getInventory().getItemInMainHand().hasItemMeta()) {
                ItemMeta bow = ((Player) e.getEntity()).getInventory().getItemInMainHand().getItemMeta();
                if(bow.getPersistentDataContainer().has(NamespacedKey.minecraft("weapon"), PersistentDataType.STRING)) {
                    String weaponType = bow.getPersistentDataContainer().get(NamespacedKey.minecraft("weapon"), PersistentDataType.STRING);
                    if(!stats.getClassType().getRpgClass().weaponTypes.contains(weaponType)){
                        e.getEntity().sendMessage("Votre classe ne peux pas utiliser cette arme !");
                        e.setCancelled(true);
                        return;
                    }
                    stats.onShootArrow(e);
                    if(bow.getPersistentDataContainer().has(NamespacedKey.minecraft("damages"), PersistentDataType.INTEGER)) {
                        e.getProjectile().setMetadata("damages", new FixedMetadataValue(oniris, (stats.calculateDamages(bow.getPersistentDataContainer().get(NamespacedKey.minecraft("damages"), PersistentDataType.INTEGER))/2)));
                    }

                } else {
                    e.getEntity().sendMessage("Cet arme ne peut pas etre utiliser en combat.");
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void drinkPotion(PlayerItemConsumeEvent e) {
        if(e.getItem().getType().equals(Material.POTION)) {
            if(e.getItem().getItemMeta() instanceof PotionMeta) {
                final PotionMeta meta = (PotionMeta) e.getItem().getItemMeta();
                final PotionData data = meta.getBasePotionData();
                if(data.getType().equals(PotionType.UNCRAFTABLE)) {
                    ItemUtils.potionManager(e.getPlayer(), oniris.getPlayers().get(e.getPlayer()), e.getItem(), data);
                }
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        oniris.getPlayers().get(e.getPlayer()).onRespawn(e);
    }

    public static double calculateDamages(PlayerStats stats, double damages) {
        double def = stats.getMaxDefence();
        def = ((def / 100) + 1);
        return damages/def;
    }
}
