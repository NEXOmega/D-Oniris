package dandelion.oniris.listerners;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.SpellType;
import dandelion.oniris.utils.DandelionItem;
import dandelion.oniris.utils.ItemType;
import dandelion.oniris.utils.ItemUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PlayerUseSpellBookEvent implements Listener {
    Oniris oniris;
    String currentSpell = "";

    public PlayerUseSpellBookEvent(Oniris oniris) {
        this.oniris = oniris;
    }

    @EventHandler
    public void playerUseSpellBook(PlayerInteractEvent e) {
        if(!e.hasItem()) return;
            if(e.getItem().hasItemMeta()) {
                if(!e.getItem().getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("itemtype"), PersistentDataType.STRING)) return;
                if(e.getItem().getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("itemtype"), PersistentDataType.STRING).equalsIgnoreCase(ItemType.GRIMORY.name())) {
                    DandelionItem book = ItemUtils.getDandelionItem(e.getItem());
                    if(e.getAction().equals(Action.LEFT_CLICK_AIR)  ||e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                        String name = ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName());
                        String spell = name.split(" ")[2];
                        if(spell.length() < 1)  {
                            e.getPlayer().sendMessage("Vous n'avez pas encore debloqué de sorts.");
                            return;
                        }

                        Bukkit.getScheduler().runTask(oniris, () -> {
                            SpellType s = SpellType.valueOf(spell.toUpperCase());
                                if(oniris.getPlayers().get(e.getPlayer()).getSpells().contains(s.toString().toLowerCase())) {
                                    if(s.getLevel() <= e.getPlayer().getLevel()) {
                                        try {
                                            oniris.getSpellManager().cast(e.getPlayer(),oniris,oniris.getPlayers().get(e.getPlayer()),s);
                                        } catch (IllegalAccessException | InstantiationException ex) {
                                            ex.printStackTrace();
                                        }
                                    } else {
                                        e.getPlayer().sendMessage(ChatColor.RED + "Vous debloquerez ce sort au niveau : " + ChatColor.YELLOW +s.getLevel());
                                    }
                                } else {
                                    e.getPlayer().sendMessage(ChatColor.RED + "Erreur, vous ne connaissez pas ce sort");
                                }
                        });
                    }
                    else if(e.getAction().equals(Action.RIGHT_CLICK_AIR) ||e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                        Player p = e.getPlayer();
                        if(!p.isSneaking()) {
                            PlayerStats stats = oniris.getPlayers().get(p);

                            ItemMeta iMeta = p.getItemInHand().getItemMeta();

                            currentSpell = stats.getSpells().getFirst();
                            stats.getSpells().removeFirst();
                            stats.getSpells().add(currentSpell);
                            assert iMeta != null;
                            iMeta.setCustomModelData(SpellType.valueOf(currentSpell.toUpperCase()).getItemStack().getItemMeta().getCustomModelData());
                            //TODO Check with new spell book system
                            iMeta.setDisplayName(book.getRanks().getChatColor() + book.getName() + " : "+ ChatColor.YELLOW + StringUtils.capitalize(currentSpell));
                            p.getItemInHand().setItemMeta(iMeta);
                        } else {
                            if(e.getItem().getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("spell"), PersistentDataType.STRING)) {
                                SpellType s = SpellType.valueOf(e.getItem().getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("spell"), PersistentDataType.STRING));
                                try {
                                    oniris.getSpellManager().cast(p, oniris, oniris.getPlayers().get(p),s);
                                } catch (IllegalAccessException | InstantiationException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                }
        }
    }
}
