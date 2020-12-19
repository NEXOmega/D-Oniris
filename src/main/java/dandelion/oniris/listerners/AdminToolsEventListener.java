package dandelion.oniris.listerners;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import dandelion.oniris.Oniris;
import dandelion.oniris.entities.DandelionMob;
import dandelion.oniris.entities.entity.DandelionEntity;
import dandelion.oniris.utils.FonctionUtils;
import dandelion.oniris.utils.ItemType;
import dandelion.oniris.utils.ItemUtils;
import dandelion.oniris.utils.Ranks;
import dandelion.oniris.world.Treasure;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
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
                                    ChatColor.GRAY + "level int",
                                    ChatColor.GRAY + "respawntime int",
                                    ChatColor.GRAY + "exp int",
                                    ChatColor.GRAY + "name String"
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
                        else if(args[0].equalsIgnoreCase("level"))
                            dandelionMob.setLevel(Integer.parseInt(args[1]));
                        else if(args[0].equalsIgnoreCase("respawntime"))
                            dandelionMob.setRespawnTime(Long.parseLong(args[1]));
                        else if(args[0].equalsIgnoreCase("exp"))
                            dandelionMob.setRewardXp(Integer.parseInt(args[1]));
                        else if(args[0].equalsIgnoreCase("name"))
                            dandelionMob.setName(args[1]);

                        oniris.getConnectToDB().updateEntity(dandelionMob);
                        oniris.getEntityManager().getMobMap().put(dandelionMob.getMob_name(), dandelionMob);
                        return AnvilGUI.Response.close();
                    });
                    anvilGui.open(e.getPlayer());
                }));

                gui.setOnBottomClick(clickEvent -> {
                    clickEvent.setCancelled(true);
                    AnvilGUI.Builder builder =new AnvilGUI.Builder().plugin(oniris).title("Add Item");
                    builder.itemLeft(clickEvent.getCurrentItem().clone());
                    builder.onComplete((pl, text) -> {
                        dandelionMob.addDrop(new Treasure.Reward(clickEvent.getCurrentItem().clone(), Integer.parseInt(text), 1));
                        oniris.getConnectToDB().updateEntity(dandelionMob);
                        oniris.getEntityManager().getMobMap().put(dandelionMob.getMob_name(), dandelionMob);
                        return AnvilGUI.Response.close();
                    });
                    builder.open(e.getPlayer());
                });
                gui.addPane(pane);
                gui.show(e.getPlayer());

            } else {
                e.getPlayer().sendMessage(ChatColor.RED + "Errur, cette entitée n'est pas modifiable");
            }
        }

    }
}
