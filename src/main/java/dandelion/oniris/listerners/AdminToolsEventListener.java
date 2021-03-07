package dandelion.oniris.listerners;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import dandelion.oniris.Oniris;
import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.entities.MobType;
import dandelion.oniris.entities.entity.DandelionEntity;
import dandelion.oniris.utils.*;
import dandelion.oniris.world.Ressource;
import dandelion.oniris.world.Treasure;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Arrays;
import java.util.List;

public class AdminToolsEventListener implements Listener {

    Oniris oniris;

    public AdminToolsEventListener(Oniris oniris) {
        this.oniris = oniris;
    }

    @EventHandler
    public void useOnEntity(PlayerInteractEntityEvent e) {
        if(e.getPlayer().getInventory().getItemInMainHand().equals(ItemUtils.ADMIN_WRENCH.getItem().getItemStack()) && e.getHand().equals(EquipmentSlot.HAND)) {
            if(((CraftEntity)e.getRightClicked()).getHandle() instanceof DandelionEntity) {
                DandelionMob dandelionMob = ((DandelionEntity) ((CraftEntity)e.getRightClicked()).getHandle()).getMob();

                Gui gui = new Gui( 1, dandelionMob.getName() + " Gui");

                OutlinePane pane = new OutlinePane(0,0,9,1);
                pane.setOnClick(clickPane -> clickPane.setCancelled(true));
                List<String> mobInfos = Arrays.asList(
                        ChatColor.GRAY + dandelionMob.getMob_name(),
                        ChatColor.GRAY + dandelionMob.getEntityTypes().f(),
                        ChatColor.GRAY + dandelionMob.getEntityClass().getName(),
                        ChatColor.RED + ""+ dandelionMob.getStats().getStrength() + ChatColor.DARK_PURPLE + " " + dandelionMob.getStats().getMagie() + ChatColor.LIGHT_PURPLE + " " + dandelionMob.getStats().getDefence() + ChatColor.YELLOW + " " +dandelionMob.getStats().getVitality() + ChatColor.WHITE + " " + dandelionMob.getStats().getSpeed(),
                        ChatColor.GRAY + String.valueOf(dandelionMob.getLevel()),
                        ChatColor.GRAY + ((DandelionEntity) ((CraftEntity)e.getRightClicked()).getHandle()).getMobType().name());
                pane.addItem(new GuiItem(FonctionUtils.createItem(dandelionMob.getName(), Material.ZOMBIE_HEAD, ItemType.DISPLAY_ITEM, Ranks.HERO, 1 ,1, mobInfos).getItemStack()));

                pane.addItem(new GuiItem(FonctionUtils.createItem("Edit stats", Material.IRON_SWORD, ItemType.DISPLAY_ITEM, Ranks.HERO,1,1).getItemStack(), click -> {
                    AnvilGUI.Builder anvilGui = new AnvilGUI.Builder().plugin(oniris);
                    anvilGui.itemLeft(FonctionUtils.createItem("Stats list", Material.IRON_SWORD, ItemType.DISPLAY_ITEM, Ranks.LEGENDARY, 1,1,
                            Arrays.asList(
                                    ChatColor.GRAY + "strength int",
                                    ChatColor.GRAY + "magie int",
                                    ChatColor.GRAY + "defence int",
                                    ChatColor.GRAY + "vitality int",
                                    ChatColor.GRAY + "speed int",
                                    ChatColor.GRAY + "attackSpeed int",
                                    ChatColor.GRAY + "attackRange int",
                                    ChatColor.GRAY + "rangedDamage int",
                                    ChatColor.GRAY + "rangedSpeed int",
                                    ChatColor.GRAY + "level int",
                                    ChatColor.GRAY + "respawntime int",
                                    ChatColor.GRAY + "exp int",
                                    ChatColor.GRAY + "name String",
                                    ChatColor.GRAY + "type MobType",
                                    ChatColor.GRAY + "baby boolean"
                                    )).getItemStack());
                    anvilGui.onComplete((player,text) -> {
                        String[] args = text.split(" ");
                        if(args[0].equalsIgnoreCase("strength"))
                            dandelionMob.getStats().setStrength(Float.parseFloat(args[1]));
                        else if(args[0].equalsIgnoreCase("magie"))
                            dandelionMob.getStats().setMagie(Float.parseFloat(args[1]));
                        else if(args[0].equalsIgnoreCase("defence"))
                            dandelionMob.getStats().setDefence(Float.parseFloat(args[1]));
                        else if(args[0].equalsIgnoreCase("vitality"))
                            dandelionMob.getStats().setVitality(Float.parseFloat(args[1]));
                        else if(args[0].equalsIgnoreCase("speed"))
                            dandelionMob.getStats().setSpeed(Float.parseFloat(args[1]));
                        else if(args[0].equalsIgnoreCase("attackSpeed"))
                            dandelionMob.getStats().setAttackSpeed(Float.parseFloat(args[1]));
                        else if(args[0].equalsIgnoreCase("attackRange"))
                            dandelionMob.getStats().setAttackRange(Float.parseFloat(args[1]));
                        else if(args[0].equalsIgnoreCase("rangedDamage"))
                            dandelionMob.getStats().setRangedDamage(Float.parseFloat(args[1]));
                        else if(args[0].equalsIgnoreCase("rangedSpeed"))
                            dandelionMob.getStats().setRangedSpeed(Float.parseFloat(args[1]));
                        else if(args[0].equalsIgnoreCase("level"))
                            dandelionMob.setLevel(Integer.parseInt(args[1]));
                        else if(args[0].equalsIgnoreCase("respawntime"))
                            dandelionMob.setRespawnTime(Long.parseLong(args[1]));
                        else if(args[0].equalsIgnoreCase("exp"))
                            dandelionMob.setRewardXp(Integer.parseInt(args[1]));
                        else if(args[0].equalsIgnoreCase("name"))
                            dandelionMob.setName(args[1].replace("_"," "));
                        else if(args[0].equalsIgnoreCase("type"))
                            dandelionMob.setType(MobType.valueOf(args[1].toUpperCase()));
                        else if(args[0].equalsIgnoreCase("baby"))
                            dandelionMob.setBaby(Boolean.parseBoolean(args[1]));

                        oniris.getConnectToDB().updateEntity(dandelionMob);
                        oniris.getEntityManager().getMobMap().put(dandelionMob.getMob_name(), dandelionMob);
                        return AnvilGUI.Response.close();
                    });
                    anvilGui.open(e.getPlayer());
                }));

                pane.addItem(new GuiItem(FonctionUtils.createItem("Supprimer", Material.BARRIER, ItemType.EQUIPEMENT.DISPLAY_ITEM, Ranks.MYTHIC, 1,1).getItemStack(), click -> {
                    oniris.getConnectToDB().removeEntity(dandelionMob.getMob_name());

                    dandelionMob.getMobLocations().forEach((uuid, loc) -> {
                        oniris.getEntityManager().getMobSpawn().remove(uuid);
                    });
                    oniris.getEntityManager().getMobMap().remove(dandelionMob.getMob_name());

                    e.getRightClicked().remove();
                    e.getPlayer().sendMessage(ChatColor.RED + "Entité effacée");
                }));

                pane.addItem(new GuiItem(FonctionUtils.createItem("Drop List", Material.ROTTEN_FLESH, ItemType.DISPLAY_ITEM, Ranks.HERO, 1, 1).getItemStack(), click -> {
                    Gui dropGui = new Gui(3, "Drops de" + dandelionMob.getName());
                    OutlinePane dropPane = new OutlinePane(0,0,9,3);
                    dandelionMob.getRewardList().forEach( r -> {
                        dropPane.addItem(new GuiItem(r.getItem()));
                    });
                    dropGui.addPane(dropPane);
                    e.getPlayer().closeInventory();
                    dropGui.show(e.getPlayer());

                }));
                gui.setOnBottomClick(clickEvent -> {
                    clickEvent.setCancelled(true);
                    if(clickEvent.isLeftClick()) {
                        AnvilGUI.Builder builder =new AnvilGUI.Builder().plugin(oniris).title("Add Item");
                        builder.itemLeft(clickEvent.getCurrentItem().clone());
                        builder.onComplete((pl, text) -> {
                            dandelionMob.addDrop(new Treasure.Reward(clickEvent.getCurrentItem().clone(), Integer.parseInt(text), 1));
                            oniris.getConnectToDB().updateEntity(dandelionMob);
                            oniris.getEntityManager().getMobMap().put(dandelionMob.getMob_name(), dandelionMob);
                            return AnvilGUI.Response.close();
                        });
                        builder.open(e.getPlayer());
                    } else if (clickEvent.isRightClick()) {
                        AnvilGUI.Builder builder = new AnvilGUI.Builder().plugin(oniris).title("Set Equipement");
                        builder.itemLeft(clickEvent.getCurrentItem().clone());
                        builder.onComplete((pl, text) -> {
                            dandelionMob.getEquipements()[Integer.parseInt(text)] = clickEvent.getCurrentItem().clone();
                            oniris.getConnectToDB().updateEntity(dandelionMob);
                            oniris.getEntityManager().getMobMap().put(dandelionMob.getMob_name(), dandelionMob);
                            return AnvilGUI.Response.close();
                        });
                        builder.open(e.getPlayer());
                    }

                });
                gui.addPane(pane);
                gui.show(e.getPlayer());

            } else {
                e.getPlayer().sendMessage(ChatColor.RED + "Errur, cette entitée n'est pas modifiable");
            }
        }
    }

    @EventHandler
    public void useOnBlock(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getHand().equals(EquipmentSlot.HAND)) {
            if(oniris.getRessourceManager().isRessource(e.getClickedBlock().getLocation())) {
                Ressource r = oniris.getRessourceManager().ressources.get(e.getClickedBlock().getLocation());
                Gui gui = new Gui(1, r.getName());
                gui.setOnGlobalClick(click -> click.setCancelled(true));

                OutlinePane infoPane = new OutlinePane(0,0,9,1);
                infoPane.addItem(new GuiItem(FonctionUtils.createItem("Delete ressource", Material.BARRIER, ItemType.DISPLAY_ITEM, Ranks.LEGENDARY, 1, 0).getItemStack(), click -> {
                    oniris.getRessourceManager().deleteRessource(e.getClickedBlock().getLocation());
                    e.getPlayer().sendMessage(ChatColor.RED + "Ressource supprimée !");
                    e.getPlayer().closeInventory();
                }));

                infoPane.addItem(new GuiItem(FonctionUtils.createItem(r.getName(), Material.valueOf(r.getMaterial()), ItemType.RESSOURCE, Ranks.RARE, 1, 0, Arrays.asList("Job : " + r.getJobName(),"Niveau Requis : " + r.getRequiredLevel(), "Temps de respawn : " + r.getRespawnTime() + "s")).getItemStack()));

                DandelionItem indicator = ItemUtils.RIGHT_ARROW.getItem();
                indicator.setName("Drop : ");
                indicator.setRanks(Ranks.SUPERRARE);
                infoPane.addItem(new GuiItem(indicator.getItemStack()));
                infoPane.addItem(new GuiItem(r.getDrop()));

                gui.addPane(infoPane);
                gui.show(e.getPlayer());
            }
        }
    }
}
