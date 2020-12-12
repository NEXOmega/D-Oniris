package dandelion.oniris.jobs;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.utils.Recipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class CraftingJob extends Job {
    transient static HashMap<String, Recipe> recipeList = new HashMap<>();
    transient Gui gui;
    ArrayList<String> knowingRecipe = new ArrayList<>();

    public Gui getKnowingRecipeGui(Plugin pl, String name, Player p, PlayerStats playerStats) {
        this.gui = new Gui(pl, 6, name);

        gui.setOnGlobalClick(e -> e.setCancelled(true));

        OutlinePane recipesPane = new OutlinePane(0,0,9,5);
        OutlinePane optionPane = new OutlinePane(0,5,9,1);
        AtomicInteger i = new AtomicInteger();
        for(String s : this.knowingRecipe.subList((9*5)* i.get(), (Math.min((9 * 5) * (i.get() + 1), this.knowingRecipe.size())))) {
            ItemStack craft = Blacksmith.recipeList.get(s).getResult();
            ItemMeta meta = craft.getItemMeta();
            meta.getLore().add(ChatColor.GOLD + "---- Craft -----");
            meta.getLore().add(ChatColor.GOLD + "Required Level " + Blacksmith.recipeList.get(s).getLevelRequired());
            meta.getLore().add(ChatColor.GOLD + "Exp gained " + Blacksmith.recipeList.get(s).getExp());
            craft.setItemMeta(meta);
            recipesPane.addItem(new GuiItem(craft, clickEvent -> {
                Gui g = new Gui(pl, 6, "Recipe");
                g.setOnGlobalClick(e -> e.setCancelled(true));
                OutlinePane recipePane = new OutlinePane(0,0,9,6);
                for(ItemStack itemStack : this.getRecipe(s).getIngredients()) {
                    recipePane.addItem(new GuiItem(itemStack));
                }
                g.addPane(recipePane);
                g.show(p);
            }));
        }

        optionPane.addItem(new GuiItem(new ItemStack(Material.ARROW), e -> {
            if(i.get() < Math.ceil(this.knowingRecipe.size()/(9*5))) {
                i.getAndIncrement();
            }
            recipesPane.clear();
            for(String s : this.knowingRecipe.subList((9*5)* i.get(), (Math.min((9 * 5) * (i.get() + 1), this.knowingRecipe.size())))) {
                recipesPane.addItem(new GuiItem(Blacksmith.recipeList.get(s).getResult(), clickEvent -> this.getRecipe(s).craft(p, playerStats.getJobStats().getBlacksmith())));
            }
        }));
        optionPane.addItem(new GuiItem(new ItemStack(Material.PLAYER_HEAD), e -> {
            if(i.get() > 0) {
                i.getAndDecrement();
            }
            recipesPane.clear();
            for(String s : this.knowingRecipe.subList((9*5)* i.get(), (Math.min((9 * 5) * (i.get() + 1), this.knowingRecipe.size())))) {
                recipesPane.addItem(new GuiItem(Blacksmith.recipeList.get(s).getResult(), clickEvent -> this.getRecipe(s).craft(p, playerStats.getJobStats().getBlacksmith())));
            }
        }));

        gui.addPane(recipesPane);
        gui.addPane(optionPane);
        return gui;
    }

    public static void addRecipe(String name, Recipe recipe) {
        recipeList.put(name, recipe);
    }

    public Recipe getRecipe(String recipeName) {
        return recipeList.get(recipeName);
    }

    public void learnRecipe(String recipeName) {
        knowingRecipe.add(recipeName);
    }

    public boolean knowRecipe(String recipeName) {
        return  knowingRecipe.contains(recipeName);
    }

    public static HashMap<String, Recipe> getRecipeList() {
        return recipeList;
    }
}
