package dandelion.oniris.listerners;

import dandelion.oniris.Oniris;
import dandelion.oniris.commands.gui.EquipementGui;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.classes.ClassType;
import dandelion.oniris.players.classes.list.RPGClass;
import dandelion.oniris.utils.DandelionItem;
import dandelion.oniris.utils.ItemUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class PlayerGuiEvent implements Listener {
    Oniris oniris;

    public PlayerGuiEvent(Oniris oniris) {
        this.oniris = oniris;
    }

    @EventHandler
    public void clickInv(@NotNull InventoryClickEvent e) {
        if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
            if (e.getClick() == ClickType.SHIFT_LEFT) e.setCancelled(true);
            if (e.getSlot() <= 39 && e.getSlot() >= 36) {
                e.setCancelled(true);
            }
        }

        if(e.getClickedInventory().getType() == InventoryType.CRAFTING) {
            e.setCancelled(true);
            if(e.getSlot() == 0) {
                e.getWhoClicked().openInventory(oniris.getGui().inv());
            }
        }

        if (e.getView().getTitle().equals(ChatColor.RED + "Menu")) {
            e.setCancelled(true);
            menuInv(e);
        }

        if (e.getView().getTitle().equals(ChatColor.RED + "Spells")) {
            e.setCancelled(true);
            spellInv(e);
        }

        if (e.getView().getTitle().equals(ChatColor.RED + "Portal Stone")) {
            e.setCancelled(true);
            portalStoneInv(e);
        }

        if (e.getView().getTitle().equals(ChatColor.RED + "Stats")) {
            e.setCancelled(true);
            statsInv(e);
        }

        if(e.getView().getTitle().equals(ChatColor.RED +"Equipements")) {
            e.setCancelled(true);
            equipementGui(e);
        }
    }

    private void menuInv(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getType() == Material.IRON_SWORD) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Ouvrir le menu des stats")) {
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().openInventory(oniris.getGui().getStatsGui().inv());
                e.getWhoClicked().sendMessage("§cStats point : §e" + oniris.getPlayers().get((Player) e.getWhoClicked()).getStatsPoint());
            }
        } else if (e.getCurrentItem().getType() == Material.PAPER) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Liste des sorts")) {
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().openInventory(oniris.getGui().getSpellGui().inv(oniris, (Player) e.getWhoClicked(), oniris.getPlayers().get(((Player) e.getWhoClicked()))));
            }
        } else if (e.getCurrentItem().getType() == Material.DIAMOND_HELMET) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Ouvrir le Gui des Equipements")) {
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().openInventory(oniris.getGui().getEquipementGui().inv((Player) e.getWhoClicked(), oniris.getPlayers().get((Player) e.getWhoClicked())));
            }
        } else if (e.getCurrentItem().getType() == Material.NETHER_STAR) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Ouvrir le Gui des Portal Stones")) {
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().openInventory(oniris.getGui().getPortalStoneGui().inv((Player) e.getWhoClicked(), oniris.getPlayers().get((Player) e.getWhoClicked())));
            }
        }
        else if (e.getCurrentItem().getType() == Material.BOOK) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Ouvrir le Gui de progression")) {
                e.getWhoClicked().closeInventory();
                ((Player)e.getWhoClicked()).performCommand("progress");
            }
        } else if(e.getCurrentItem().getType() == Material.DIAMOND_AXE) {
            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Ouvrir le menu des jobs")) {
                e.getWhoClicked().closeInventory();
                ((Player)e.getWhoClicked()).performCommand("job");
            }
        }
    }

    private void statsInv(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;
        if (oniris.getPlayers().get((Player) e.getWhoClicked()).getStatsPoint() < 1) {
            e.getWhoClicked().sendMessage("§cVous n'avez plus de  Skill Point :'(");
            return;
        }
        PlayerStats stats = oniris.getPlayers().get((Player) e.getWhoClicked());

        if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "" + ChatColor.BOLD + "Force")) {
                stats.setStatsPoint(stats.getStatsPoint() - 1);
                stats.getStats().setStrength(stats.getStats().getStrength() + 1);
                e.getWhoClicked().sendMessage("§eForce augmente de 1");
            }
        }/* else if (e.getCurrentItem().getType() == Material.STICK) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "" + ChatColor.BOLD + "Magie")) {
                stats.setStatsPoint(stats.getStatsPoint() - 1);
                stats.getStats().setMagie(stats.getStats().getMagie() + 1);
                e.getWhoClicked().sendMessage("§eMagie augmente de 1");
            }
        }*/ else if (e.getCurrentItem().getType() == Material.SHIELD) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "" + ChatColor.BOLD + "Defence")) {
                stats.setStatsPoint(stats.getStatsPoint() - 1);
                stats.getStats().setDefence(stats.getStats().getDefence() + 1);
                e.getWhoClicked().sendMessage("§eDefence augmente de 1");
            }
        } else if (e.getCurrentItem().getType() == Material.APPLE) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "" + ChatColor.BOLD + "Vitality")) {
                stats.setStatsPoint(stats.getStatsPoint() - 1);
                stats.getStats().setVitality(stats.getStats().getVitality() + 1);
                e.getWhoClicked().sendMessage("§eVitality augmente de 1");
            }
        } else if (e.getCurrentItem().getType() == Material.DIAMOND_BOOTS) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "" + ChatColor.BOLD + "Speed")) {
                if (stats.getStats().getSpeed() == 200) {
                    e.getWhoClicked().sendMessage("Competance max");
                    return;
                }
                stats.setStatsPoint(stats.getStatsPoint() - 1);
                stats.getStats().setSpeed(stats.getStats().getSpeed() + 1);
                e.getWhoClicked().sendMessage("§eSpeed augmente de 1");
            }
        }

    }

    private void spellInv(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack i = p.getItemOnCursor();
        ItemMeta iMeta = i.getItemMeta();
        ItemStack clicked = e.getCurrentItem();
        ItemMeta meta = clicked.getItemMeta();
        //TODO Check if system is good with the new grimory system
        if (i.getType().equals(Material.BOOK) && i.hasItemMeta() && i.getItemMeta().getDisplayName().contains(ItemUtils.GRIMOIRE.getItem().getItemStack().getItemMeta().getDisplayName())) {
            iMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + ItemUtils.GRIMOIRE.getItem().getItemStack().getItemMeta().getDisplayName() + ChatColor.YELLOW + StringUtils.capitalize(meta.getDisplayName().substring(4)));
            iMeta.setLore(meta.getLore());
            i.setItemMeta(iMeta);

        }
    }

    private void portalStoneInv(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;
        ItemStack item = e.getCurrentItem();
        if (item.getItemMeta().hasLore()) {
            String[] coords = item.getItemMeta().getLore().get(item.getItemMeta().getLore().size() - 1).split(" ");
            Location loc = new Location(Bukkit.getWorld("world"),
                    Integer.parseInt(coords[0].replaceAll("[^0-9]", "")),
                    Integer.parseInt(coords[1].replaceAll("[^0-9]", "")),
                    Integer.parseInt(coords[2].replaceAll("[^0-9]", "")));
            e.getWhoClicked().teleport(loc);

        }
    }

    private void equipementGui(InventoryClickEvent e) {
        if (e.getCurrentItem() != null && e.getClickedInventory().equals(e.getView().getBottomInventory())) {
            ItemStack clicked = e.getCurrentItem();
            ItemMeta meta =clicked.getItemMeta();
            DandelionItem dItem = ItemUtils.getDandelionItem(clicked);
            PlayerStats stats = oniris.getPlayers().get((Player) e.getWhoClicked());
            if(stats.getClassType().getRpgClass().species != null && stats.getClassType().getRpgClass().species.contains(RPGClass.spec.NOARMOR)) {
                e.getWhoClicked().sendMessage(ChatColor.RED + "Votre classe ne vous permet pas de vous équiper");
                return;
            }

            if (clicked.getType().toString().contains("ELYTRA")) {
                if (oniris.getPlayers().get((Player) e.getWhoClicked()).getClassType().equals(ClassType.JUDGE)) {
                    if (!e.getInventory().getItem(10).getType().equals(Material.CARROT_ON_A_STICK)) {
                        e.getWhoClicked().sendMessage("§cVeuillez vous desequiper avant !");
                        return;
                    }
                    e.getInventory().setItem(10, clicked);
                    stats.getEquipements()[1] = clicked;
                    e.getWhoClicked().getInventory().setChestplate(clicked);
                    e.setCurrentItem(null);
                    oniris.getpUtils().addEquipementStat(stats, clicked, true);
                } else {
                    e.getWhoClicked().sendMessage("§cErreur, votre classe ne vous permet pas d'utiliser ca");
                }
            }
            System.out.println("0");
            if (clicked.getType().toString().contains("HELMET")) {
                if (!e.getInventory().getItem(1).getType().equals(Material.CARROT_ON_A_STICK)) {
                    e.getWhoClicked().sendMessage("§cVeuillez vous desequiper avant !");
                    return;
                }
                if (!(dItem.getLevel() <= ((Player) e.getWhoClicked()).getLevel()))
                    return;
                e.getInventory().setItem(1, clicked);
                stats.getEquipements()[0] = clicked;
                e.getWhoClicked().getInventory().setHelmet(clicked);
                e.setCurrentItem(null);
                oniris.getpUtils().addEquipementStat(stats, clicked, true);

            } else if (clicked.getType().toString().contains("CHESTPLATE")) {
                if (!e.getInventory().getItem(10).getType().equals(Material.CARROT_ON_A_STICK)) {
                    e.getWhoClicked().sendMessage("§cVeuillez vous desequiper avant !");
                    return;
                }
                if (!(Integer.parseInt(clicked.getItemMeta().getDisplayName().split(Pattern.quote("["))[1].replaceAll("[^0-9]", "")) <= ((Player) e.getWhoClicked()).getLevel()))
                    return;
                e.getInventory().setItem(10, clicked);
                stats.getEquipements()[1] = clicked;
                e.getWhoClicked().getInventory().setChestplate(clicked);
                e.setCurrentItem(null);
                oniris.getpUtils().addEquipementStat(stats, clicked, true);

            } else if (clicked.getType().toString().contains("LEGGINGS")) {
                if (!e.getInventory().getItem(19).getType().equals(Material.CARROT_ON_A_STICK)) {
                    e.getWhoClicked().sendMessage("§cVeuillez vous desequiper avant !");
                    return;
                }
                if (!(dItem.getLevel() <= ((Player) e.getWhoClicked()).getLevel()))
                    return;
                e.getInventory().setItem(19, clicked);
                stats.getEquipements()[2] = clicked;
                e.getWhoClicked().getInventory().setLeggings(clicked);
                e.setCurrentItem(null);
                oniris.getpUtils().addEquipementStat(stats, clicked, true);

            } else if (clicked.getType().toString().contains("BOOTS")) {
                if (!e.getInventory().getItem(28).getType().equals(Material.CARROT_ON_A_STICK)) {
                    e.getWhoClicked().sendMessage("§cVeuillez vous desequiper avant !");
                    return;
                }
                if (!(dItem.getLevel() <= ((Player) e.getWhoClicked()).getLevel()))
                    return;
                e.getInventory().setItem(28, clicked);
                stats.getEquipements()[3] = clicked;
                e.getWhoClicked().getInventory().setBoots(clicked);
                e.setCurrentItem(null);
                oniris.getpUtils().addEquipementStat(stats, clicked, true);

            } else if (meta.getPersistentDataContainer().has(NamespacedKey.minecraft("weapon"), PersistentDataType.STRING)) {
                if(stats.getClassType().getRpgClass().weaponTypes.contains(meta.getPersistentDataContainer().get(NamespacedKey.minecraft("weapon"), PersistentDataType.STRING))) {
                    if (!e.getInventory().getItem(9).getType().equals(Material.CARROT_ON_A_STICK)) {
                        e.getWhoClicked().sendMessage("§cVeuillez vous desequiper avant !");
                        return;
                    }
                    if (!(dItem.getLevel() <= ((Player) e.getWhoClicked()).getLevel()))
                        return;
                    e.getInventory().setItem(9, clicked);
                    stats.getEquipements()[4] = clicked;
                    oniris.getpUtils().addEquipementStat(stats, clicked, true);
                } else {
                    e.getWhoClicked().sendMessage("Cette arme n'est pas pour votre classe");
                }

            } else if (clicked.getType().equals(Material.LIGHT_BLUE_DYE)) {
                    if (!e.getInventory().getItem(18).getType().equals(Material.CARROT_ON_A_STICK)) {
                        e.getWhoClicked().sendMessage("§cVeuillez vous desequiper avant !");
                        return;
                    }
                    if (!(dItem.getLevel() <= ((Player) e.getWhoClicked()).getLevel()))
                        return;
                    e.getInventory().setItem(18, clicked);
                    stats.getEquipements()[5] = clicked;
                    e.setCurrentItem(null);
                    oniris.getpUtils().addEquipementStat(stats, clicked, true);

                } else if (clicked.getType().equals(Material.GRAY_DYE)) {
                    if (e.getInventory().getItem(11).getType().equals(Material.CARROT_ON_A_STICK)) {
                        if (!e.getInventory().getItem(11).getType().equals(Material.CARROT_ON_A_STICK)) {
                            e.getWhoClicked().sendMessage("§cVeuillez vous desequiper avant !");
                            return;
                        }
                        if (!(dItem.getLevel() <= ((Player) e.getWhoClicked()).getLevel()))
                            return;
                        e.getInventory().setItem(11, clicked);
                        stats.getEquipements()[6] = clicked;


                    } else {
                        if (!e.getInventory().getItem(20).getType().equals(Material.CARROT_ON_A_STICK)) {
                            e.getWhoClicked().sendMessage("§cVeuillez vous desequiper avant !");
                            return;
                        }
                        if (!(dItem.getLevel() <= ((Player) e.getWhoClicked()).getLevel()))
                            return;
                        e.getInventory().setItem(20, clicked);
                        stats.getEquipements()[7] = clicked;

                    }
                    e.setCurrentItem(null);
                    oniris.getpUtils().addEquipementStat(stats, clicked, true);
                }


        }else if(e.getCurrentItem() != null && e.getClickedInventory().equals(e.getView().getTopInventory())) {
            ItemStack clicked = e.getCurrentItem();
            ItemMeta meta = clicked.getItemMeta();
            PlayerStats stats = oniris.getPlayers().get((Player) e.getWhoClicked());
            EquipementGui eGui = oniris.getGui().getEquipementGui();
            if(clicked.getType().toString().contains("ELYTRA")) {
                if(oniris.getPlayers().get((Player) e.getWhoClicked()).getClassType().getRpgClass().getSpecies().contains(RPGClass.spec.ELYTRA)) {
                    e.getInventory().setItem(10,eGui.getItem(3));
                    stats.getEquipements()[1] = null;
                    e.getWhoClicked().getInventory().addItem(clicked);
                    e.getWhoClicked().getInventory().setChestplate(null);
                    oniris.getpUtils().addEquipementStat(stats,clicked, false);
                } else {
                    e.getWhoClicked().sendMessage("§cErreur, votre classe ne vous permet pas d'utiliser ca");
                }
            }

            if(clicked.getType().toString().contains("HELMET")) {
                e.getInventory().setItem(1,eGui.getItem(4));
                stats.getEquipements()[0] = null;
                e.getWhoClicked().getInventory().addItem(clicked);
                e.getWhoClicked().getInventory().setHelmet(null);
                oniris.getpUtils().addEquipementStat(stats,clicked, false);

            } else if(clicked.getType().toString().contains("CHESTPLATE")) {
                e.getInventory().setItem(10,eGui.getItem(3));
                stats.getEquipements()[1] = null;
                e.getWhoClicked().getInventory().addItem(clicked);
                e.getWhoClicked().getInventory().setChestplate(null);
                oniris.getpUtils().addEquipementStat(stats,clicked, false);

            }else if(clicked.getType().toString().contains("LEGGINGS")) {
                e.getInventory().setItem(19,eGui.getItem(5));
                stats.getEquipements()[2] = null;
                e.getWhoClicked().getInventory().addItem(clicked);
                e.getWhoClicked().getInventory().setLeggings(null);
                oniris.getpUtils().addEquipementStat(stats,clicked, false);

            }else if(clicked.getType().toString().contains("BOOTS")) {
                e.getInventory().setItem(28,eGui.getItem(2));
                stats.getEquipements()[3] = null;
                e.getWhoClicked().getInventory().addItem(clicked);
                e.getWhoClicked().getInventory().setBoots(null);
                oniris.getpUtils().addEquipementStat(stats,clicked, false);

            }else if (meta.getPersistentDataContainer().has(NamespacedKey.minecraft("weapon"), PersistentDataType.STRING)) {
                if(stats.getClassType().getRpgClass().weaponTypes.contains(meta.getPersistentDataContainer().get(NamespacedKey.minecraft("weapon"), PersistentDataType.STRING))) {

                    e.getInventory().setItem(9, eGui.getItem(8));
                    stats.getEquipements()[4] = null;
                    oniris.getpUtils().addEquipementStat(stats, clicked, false);
                }

            } else if (clicked.getType().equals(Material.LIGHT_BLUE_DYE)) {
                    e.getInventory().setItem(18,eGui.getItem(1));
                    stats.getEquipements()[5] = null;
                    e.getWhoClicked().getInventory().addItem(clicked);
                    oniris.getpUtils().addEquipementStat(stats,clicked, false);

                } else if (clicked.getType().equals(Material.GRAY_DYE)) {

                    if (e.getSlot() == 11) {
                        stats.getEquipements()[6] = null;
                        e.getWhoClicked().getInventory().addItem(clicked);

                    } else {
                        stats.getEquipements()[7] = null;
                        e.getWhoClicked().getInventory().addItem(clicked);

                    }
                    e.setCurrentItem(eGui.getItem(7));
                    oniris.getpUtils().addEquipementStat(stats,clicked, false);

            }
        }
    }

}