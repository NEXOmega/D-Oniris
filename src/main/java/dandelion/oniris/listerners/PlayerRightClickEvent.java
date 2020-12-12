package dandelion.oniris.listerners;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.spell.SpellType;
import dandelion.oniris.players.support.SupportType;
import dandelion.oniris.utils.FonctionUtils;
import dandelion.oniris.utils.ItemType;
import dandelion.oniris.utils.Ranks;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class PlayerRightClickEvent implements Listener {
    Oniris oniris;
    public  PlayerRightClickEvent(Oniris main) {
        this.oniris = main;
    }

    @EventHandler
    public void useArtifact(PlayerInteractEvent event) {
        if(event.getItem() == null) return;
        if(event.getItem().getType().name().contains("HELMET") ||
                event.getItem().getType().name().contains("CHESTPLATE") ||
                event.getItem().getType().name().contains("LEGGINGS") ||
                event.getItem().getType().name().contains("BOOTS")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Utilisez le menu pour vous équiper !");
        }
            if(event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                ItemStack item = event.getItem();
                if(item.getItemMeta().hasLore()) {
                    if(item.getItemMeta().getLore().contains(ChatColor.AQUA + "----"+ChatColor.RED+" Sorts "+ChatColor.AQUA +"----")) {
                        int start = 0;
                        for (int i =0; i < item.getItemMeta().getLore().size(); i++) {
                            if(ChatColor.stripColor(item.getItemMeta().getLore().get(i)).equalsIgnoreCase(ChatColor.stripColor(ChatColor.AQUA + "----"+ChatColor.RED+" Sorts "+ChatColor.AQUA +"----"))) {
                                start = (i+1);
                                break;
                            }
                        }
                        if (start == 0) return;
                        int finalStart = start;
                        Bukkit.getScheduler().runTask(oniris, () -> {
                            for (int i = finalStart; i < item.getItemMeta().getLore().size(); i++) {
                                SpellType spell = SpellType.valueOf(ChatColor.stripColor(item.getItemMeta().getLore().get(i).toUpperCase()));

                                try {
                                    oniris.getSpellManager().cast(event.getPlayer(),oniris,oniris.getPlayers().get(event.getPlayer()),spell);
                                } catch (IllegalAccessException | InstantiationException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                }
                if(item.getType().equals(Material.INK_SAC)) {
                        if(item.getItemMeta().getDisplayName().toLowerCase().contains("Parchemin de Sort".toLowerCase())) {
                            if(!(event.getPlayer().isSneaking())) return;

                            if(!item.getItemMeta().hasLore()) return;
                            Bukkit.getScheduler().runTask(oniris, () -> {
                                item.getItemMeta().getLore().forEach(l -> {
                                    Spell spell = null;
                                    try {
                                        spell = SpellType.valueOf(ChatColor.stripColor(l.toUpperCase())).getSpell().newInstance();
                                    } catch (InstantiationException | IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                    spell.onLaunched(event.getPlayer(),oniris,oniris.getPlayers().get(event.getPlayer()));
                                });
                                if(item.getAmount() > 0) {
                                    item.setAmount(item.getAmount() -1);
                                } else {
                                    event.getPlayer().setItemOnCursor(null);
                                }
                            });
                        }
                }
                if(item.getType().equals(Material.FIREWORK_STAR)) {
                    Bukkit.getScheduler().runTask(oniris, () -> {
                        SpellType s = SpellType.valueOf(ChatColor.stripColor(item.getItemMeta().getDisplayName().toUpperCase()));

                        if(oniris.getPlayers().get(event.getPlayer()).getSpells().contains(s.toString())) {
                            if(s.getLevel() <= event.getPlayer().getLevel()) {
                                try {
                                    oniris.getSpellManager().cast(event.getPlayer(),oniris,oniris.getPlayers().get(event.getPlayer()),s);
                                } catch (InstantiationException | IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                event.getPlayer().sendMessage(ChatColor.RED + "Vous debloquerez ce sort au niveau : " + ChatColor.YELLOW +s.getLevel());
                            }

                        } else {
                            event.getPlayer().sendMessage(ChatColor.RED + "Erreur, vous ne connaissez pas ce sort.");
                        }


                    });
                }
                if(item.getType().equals(Material.PAPER) && item.getItemMeta().getCustomModelData() == 2) {
                    if(item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("support"), PersistentDataType.STRING)) {
                        PlayerStats stats = oniris.getPlayers().get(event.getPlayer());
                        SupportType supportType = SupportType.valueOf(item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("support"), PersistentDataType.STRING));
                        AnvilGUI anvilGUI = new AnvilGUI.Builder()
                                .onClose(player -> {
                                    player.sendMessage("Fin du changement de Support");
                                })
                                .onComplete((player, text) -> {
                                    if(text.equalsIgnoreCase("Confirm")) {
                                        stats.setSupportType(supportType);
                                        item.setAmount(item.getAmount()-1);
                                        return AnvilGUI.Response.close();
                                    } else {
                                        return AnvilGUI.Response.text("Read Item on Left");
                                    }
                                })
                                .text("Changement de Support")     //sets the text the GUI should start with
                                .item(FonctionUtils.createItem(supportType.getSupport().getName(), Material.PAPER, ItemType.DISPLAY_ITEM, Ranks.COMMON,1, 1,
                                        Arrays.asList( supportType.getSupport().getDescription(), ChatColor.GREEN + "Ecrivez : Confirm pour changer de support", ChatColor.RED + "Ou faites echap pour annuler")).getItemStack())
                                .title("Changer le Support")
                                .plugin(oniris)
                                .open(event.getPlayer());
                        /*ItemStack enchantBook = new ItemStack(Material.ENCHANTED_BOOK);
                        EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) enchantBook.getItemMeta();
                        enchantmentStorageMeta.addStoredEnchant(Enchantment.ARROW_INFINITE, 1, false);
                        enchantBook.setItemMeta(enchantmentStorageMeta);
                        anvilGUI.getInventory().setItem(1, enchantBook);
                        anvilGUI.getInventory().setItem(2, new ItemStack(Material.PLAYER_HEAD));*/
                    }
                }
            }

            if(event.getItem().getType().equals(Material.SNOWBALL)) {
                PlayerStats stats = oniris.getPlayers().get(event.getPlayer());
                if(event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
                    ItemMeta snowball = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();
                    if(snowball.getPersistentDataContainer().has(NamespacedKey.minecraft("weapon"), PersistentDataType.STRING)) {
                        String weaponType = snowball.getPersistentDataContainer().get(NamespacedKey.minecraft("weapon"), PersistentDataType.STRING);
                        if(!stats.getClassType().getRpgClass().weaponTypes.contains(weaponType)){
                            event.getPlayer().sendMessage("Votre classe ne peux pas utiliser cette arme !");
                            event.setCancelled(true);
                            return;
                        }
                    } else {
                        event.getPlayer().sendMessage("Cet arme ne peut pas etre utilisée en combat.");
                        event.setCancelled(true);
                        return;
                    }
                }
            }


    }

    @EventHandler
    public void thrownSnowball(ProjectileLaunchEvent event) {
        if(event.getEntity().getShooter() instanceof Player) {
            if(event.getEntity() instanceof Snowball) {
                Player p = (Player) event.getEntity().getShooter();
                if(p.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() >= 1 && p.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() <= 9) {
                    if(p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("spell"), PersistentDataType.STRING)) {
                        String spell = p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("spell"), PersistentDataType.STRING);
                        event.getEntity().getPersistentDataContainer().set(NamespacedKey.minecraft("spell"), PersistentDataType.STRING, spell);
                    }
                    if(p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("damages"), PersistentDataType.INTEGER)) {
                        event.getEntity().setMetadata("damages", new FixedMetadataValue(oniris, (oniris.getPlayers().get(p).calculateDamages(p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("damages"), PersistentDataType.INTEGER))/2)));
                    }
                }

            }
        }
    }
}
