package dandelion.oniris.commands.admin;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.Orientable;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import dandelion.oniris.Oniris;
import dandelion.oniris.jobs.Blacksmith;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.classes.ClassType;
import dandelion.oniris.players.spell.SpellType;
import dandelion.oniris.players.support.SupportType;
import dandelion.oniris.utils.FonctionUtils;
import dandelion.oniris.utils.ItemType;
import dandelion.oniris.utils.Ranks;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.EnumUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.bukkit.ChatColor.*;

public class EditorCommand implements TabExecutor {
    Oniris oniris;
    public EditorCommand(Oniris oniris) {
        this.oniris = oniris;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player target;
        if(args.length > 0) {
            target = Bukkit.getPlayer(args[0]);
        } else {
            target = (Player) commandSender;
        }
        PaginatedPane pane = new PaginatedPane(0, 0, 7, 5);
        PlayerStats targetStats = oniris.getPlayers().get(target);
        pane.clear();

        Gui gui = new Gui( 5, "Editor");
        //gui.addPane(getSelectorPane());
        /*pane.addPane(0, getPlayerEditorPane());
        pane.addPane(1, getItemEditorPane());

        gui.addPane(pane);*/
        gui.addPane(getPlayerEditorPane(target, targetStats));
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        gui.update();
        gui.show((Player)commandSender);

        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }

    public OutlinePane getSelectorPane() {
        OutlinePane selector = new OutlinePane(0, 0,2, 5);
        selector.setOrientation(Orientable.Orientation.VERTICAL);
        selector.addItem(new GuiItem(FonctionUtils.createItem("Player Editor",Material.PLAYER_HEAD, ItemType.DISPLAY_ITEM, Ranks.COMMON, 1,1).getItemStack(), event -> {
            //pane.setPage(0);
            //gui.update();
        }));
        selector.addItem(new GuiItem(FonctionUtils.createItem(YELLOW + "Item Editor",Material.CARROT_ON_A_STICK,ItemType.DISPLAY_ITEM, Ranks.COMMON, 1,1).getItemStack(), event -> {
            //pane.setPage(1);
            //gui.update();
        }));
        return selector;
    }

    public OutlinePane getItemEditorPane() {
        OutlinePane itemPane  = new OutlinePane(2, 0, 7, 5);
        itemPane.addItem(new GuiItem(FonctionUtils.createItem(BLUE + "Data", Material.PAPER,ItemType.DISPLAY_ITEM, Ranks.COMMON, 1,1).getItemStack()));
        return  itemPane;
    }

    public OutlinePane getPlayerEditorPane(Player target, PlayerStats targetStats) {
        OutlinePane playerPane = new OutlinePane(2, 0, 7, 5);
        playerPane.addItem(new GuiItem(FonctionUtils.createItem(YELLOW +"Infos sur : " + target.getDisplayName(),Material.PAPER,ItemType.DISPLAY_ITEM, Ranks.COMMON,1, 1,
                Arrays.asList(RED + "Classe :" + GREEN + targetStats.getClassType().getClassName(),
                        RED + "Support :" + GREEN +targetStats.getSupportType().getSupport().getName(),
                        RED + "Total Stats",
                        RED + "Atk : " + GREEN + targetStats.getMaxAttack(),
                        RED +  "Def : " + GREEN + targetStats.getMaxDefence(),
                        RED +  "Vit : " + GREEN + targetStats.getMaxVitality(),
                        RED + "Magie : " + GREEN + targetStats.getMaxMagie(),
                        RED + "Spd : " + GREEN + targetStats.getMaxSpeed(),
                        RED + "Current HP : " + GREEN + targetStats.getHealth() + "/" + targetStats.getMaxVitality())).getItemStack()));
        playerPane.addItem(new GuiItem(FonctionUtils.createItem(YELLOW + "Classe : " +RED + targetStats.getClassType().getClassName(), Material.IRON_SWORD,ItemType.DISPLAY_ITEM, Ranks.COMMON, 1 , 0).getItemStack(), inventoryClickEvent -> {

            new AnvilGUI.Builder()
                    .onClose(player -> {                      //called when the inventory is closing
                        player.sendMessage("Fin de la selection de classe");
                    })
                    .onComplete((player, text) -> {           //called when the inventory output slot is clicked
                        if(EnumUtils.isValidEnum(ClassType.class, text.toUpperCase())) {
                            targetStats.setClassType(ClassType.valueOf(text.toUpperCase()));
                            targetStats.getSpells().clear();
                            targetStats.getSpells().addAll(Arrays.asList(targetStats.getClassType().getSpellList()));
                            targetStats.getClassType().getRpgClass().onClassSelected(target, targetStats);
                            player.sendMessage("Vous êtes désormais un " + text);
                            return AnvilGUI.Response.close();
                        } else {
                            return AnvilGUI.Response.text("La classe n'existe pas");
                        }
                    })                     //prevents the inventory from being closed
                    .text(targetStats.getClassType().getClassName())     //sets the text the GUI should start with
                    .item(new ItemStack(Material.GOLD_BLOCK)) //use a custom item for the first slot
                    .title("Nouvelle classe.")              //set the title of the GUI (only works in 1.14+)
                    .plugin(oniris)                 //set the plugin instance
                    .open((Player) inventoryClickEvent.getWhoClicked());
        }));
        ItemStack spellItem = FonctionUtils.createItem(YELLOW +"Sorts", Material.PAPER,ItemType.DISPLAY_ITEM, Ranks.COMMON, 1, 0, targetStats.getSpells()).getItemStack();
        playerPane.addItem(new GuiItem(spellItem, inventoryClickEvent -> {

            new AnvilGUI.Builder()
                    .onClose(player -> {                      //called when the inventory is closing
                        player.sendMessage("Fin de la selection de classe");
                    })
                    .onComplete((player, text) -> {           //called when the inventory output slot is clicked
                        if(EnumUtils.isValidEnum(SpellType.class, text.toUpperCase())) {
                            if(targetStats.getSpells().contains(text)) {
                                targetStats.getSpells().remove(text);
                                player.sendMessage("Vous avez oublié le sort : " + text);
                            } else {
                                targetStats.getSpells().add(text);
                                player.sendMessage("Vous connaissez maintenant le sort : " + text);
                            }
                            return AnvilGUI.Response.close();
                        } else {
                            return AnvilGUI.Response.text("Le sort n'existe pas");
                        }
                    })
                    .text("Sort")     //sets the text the GUI should start with
                    .item(spellItem) //use a custom item for the first slot
                    .title("Ajouter/Enlever un sort.")              //set the title of the GUI (only works in 1.14+)
                    .plugin(oniris)                 //set the plugin instance
                    .open((Player) inventoryClickEvent.getWhoClicked());
        }));
        ItemStack supportItem = FonctionUtils.createItem(YELLOW+"Support", Material.PAPER,ItemType.DISPLAY_ITEM, Ranks.COMMON, 1, 0, Arrays.asList(targetStats.getSupportType().getSupport().getName())).getItemStack();
        playerPane.addItem(new GuiItem(supportItem, inventoryClickEvent -> {
            ArrayList<String> supportList = new ArrayList<>();
            for (SupportType s: SupportType.values() ) {
                supportList.add(s.name());
            }
            new AnvilGUI.Builder()
                    .onClose(player -> {                      //called when the inventory is closing
                        player.sendMessage("Fin du changement de Support");
                    })
                    .onComplete((player, text) -> {           //called when the inventory output slot is clicked
                        if(EnumUtils.isValidEnum(SupportType.class, text.toUpperCase())) {
                            targetStats.setSupportType(SupportType.valueOf(text.toUpperCase()));
                            return AnvilGUI.Response.close();
                        } else {
                            return AnvilGUI.Response.text("Le Support n'existe pas");
                        }
                    })
                    .text(targetStats.getSupportType().getSupport().getName())     //sets the text the GUI should start with
                    .item(FonctionUtils.createItem("Support", Material.PAPER,ItemType.DISPLAY_ITEM, Ranks.COMMON, 1, 0, supportList).getItemStack()) //use a custom item for the first slot
                    .title("Changer le Support")              //set the title of the GUI (only works in 1.14+)
                    .plugin(oniris)                 //set the plugin instance
                    .open((Player) inventoryClickEvent.getWhoClicked());
        }));
        playerPane.addItem(new GuiItem(FonctionUtils.createItem("Create Recipe", Material.PAPER,ItemType.DISPLAY_ITEM, Ranks.COMMON, 2, 0).getItemStack(), e -> {
            oniris.getJobManager().createRecipeGui((Player) e.getWhoClicked());
        }));
        playerPane.addItem(new GuiItem(FonctionUtils.createItem("Edit Recipe", Material.DIAMOND_AXE,ItemType.DISPLAY_ITEM, Ranks.COMMON, 1, 0).getItemStack(), e -> {
            Gui gui = new Gui( 6, "Edit Recipe");
            gui.setOnGlobalClick(clickEvent -> clickEvent.setCancelled(true));

            OutlinePane recipesPane = new OutlinePane(0,0,9,5);
            recipesPane.setOnClick(click -> {
                if(click.getClick().equals(ClickType.LEFT)) {
                    new AnvilGUI.Builder()
                            .text("Set Exp Gained")
                            .onComplete((player, text) -> {
                                oniris.getJobManager().updateJobRecipe("blacksmith", ChatColor.stripColor(click.getCurrentItem().getItemMeta().getDisplayName()), Integer.parseInt(text), null);
                                player.sendMessage("Recipe Exp Updated !");
                                return AnvilGUI.Response.close();
                    }).plugin(oniris).open((Player) e.getWhoClicked());
                } else if(e.getClick().equals(ClickType.RIGHT)) {
                    new AnvilGUI.Builder()
                            .text("Set Level Needed")
                            .onComplete((player, text) -> {
                        oniris.getJobManager().updateJobRecipe("blacksmith", ChatColor.stripColor(click.getCurrentItem().getItemMeta().getDisplayName()), null, Integer.parseInt(text));
                        player.sendMessage("Recipe Level Updated !");
                        return AnvilGUI.Response.close();
                    }).plugin(oniris).open((Player) e.getWhoClicked());
                } else if(e.getClick().equals(ClickType.MIDDLE)) {
                    System.out.println("Test");
                    oniris.getJobManager().learnJobRecipe(targetStats, "blacksmith", ChatColor.stripColor(click.getCurrentItem().getItemMeta().getDisplayName()));
                    e.getWhoClicked().sendMessage("You have learned " + click.getCurrentItem().getItemMeta().getDisplayName() + " Recipe");
                }
            });

            OutlinePane optionPane = new OutlinePane(0,5,9,1);
            ArrayList<String> recipeArray = new ArrayList<>(Blacksmith.getRecipeList().keySet());
            AtomicInteger i = new AtomicInteger();
            for(String s : recipeArray.subList((9*5)* i.get(), (Math.min((9 * 5) * (i.get() + 1), recipeArray.size())))) {
                recipesPane.addItem(new GuiItem(Blacksmith.getRecipeList().get(s).getResult()));
            }

            optionPane.addItem(new GuiItem(new ItemStack(Material.ARROW), a -> {
                if(i.get() < Math.ceil(recipeArray.size()/(9*5))) {
                    i.getAndIncrement();
                }
                recipesPane.clear();
                for(String s : recipeArray.subList((9*5)* i.get(), (Math.min((9 * 5) * (i.get() + 1),recipeArray.size())))) {
                    recipesPane.addItem(new GuiItem(Blacksmith.getRecipeList().get(s).getResult()));
                }
                gui.update();
            }));
            optionPane.addItem(new GuiItem(new ItemStack(Material.PLAYER_HEAD), a -> {
                if(i.get() > 0) {
                    i.getAndDecrement();
                }
                recipesPane.clear();
                for(String s : recipeArray.subList((9*5)* i.get(), (Math.min((9 * 5) * (i.get() + 1), recipeArray.size())))) {
                    recipesPane.addItem(new GuiItem(Blacksmith.getRecipeList().get(s).getResult()));
                }
                gui.update();
            }));

            gui.addPane(recipesPane);
            gui.addPane(optionPane);
            gui.show(e.getWhoClicked());

        }));
        return playerPane;
    }
}
