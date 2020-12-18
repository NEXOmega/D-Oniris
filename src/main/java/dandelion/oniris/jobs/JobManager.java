package dandelion.oniris.jobs;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.mongodb.client.model.Filters;
import dandelion.oniris.Oniris;
import dandelion.oniris.database.GsonFactory;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.utils.FonctionUtils;
import dandelion.oniris.utils.ItemType;
import dandelion.oniris.utils.Ranks;
import dandelion.oniris.utils.Recipe;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class JobManager {
    Oniris oniris;

    public JobManager(Oniris oniris) {
        this.oniris = oniris;

        oniris.getConnectToDB().initJobs();
    }

    public void learnJobRecipe(PlayerStats playerStats, String job, String recipeName) {
        playerStats.getJobStats().blacksmith.learnRecipe(recipeName);

    }

    public void addJobRecipe(String job, Recipe recipe) {
        Document doc = new Document();
        doc.put("job", job);
        doc.put("name", recipe.getName());
        doc.put("recipe", GsonFactory.getPrettyGson().toJson(recipe, Recipe.class));
        if(oniris.getConnectToDB().getJobsCollection().find(Filters.eq("name", recipe.getName())).first() != null)
            oniris.getConnectToDB().getJobsCollection().replaceOne(Filters.eq("name", recipe.getName()), doc);
        else
            oniris.getConnectToDB().getJobsCollection().insertOne(doc);
        Blacksmith.getRecipeList().put(recipe.getName(), recipe);
    }

    public void updateJobRecipe(String job, String recipeName, Integer exp, Integer levelNeeded) {
        Recipe recipe = Blacksmith.getRecipeList().get(recipeName);
        System.out.println(recipeName + " " + recipe.getName());
        if(exp != null)
            recipe.setExp(exp);
        if(levelNeeded != null)
            recipe.setLevelRequired(levelNeeded);

        Document doc = new Document();
        doc.put("job", job);
        doc.put("name", recipe.getName());
        doc.put("recipe", GsonFactory.getPrettyGson().toJson(recipe, Recipe.class));

        if(oniris.getConnectToDB().getJobsCollection().find(Filters.eq("name", recipe.getName())).first() != null)
            oniris.getConnectToDB().getJobsCollection().replaceOne(Filters.eq("name", recipe.getName()), doc);
        Blacksmith.getRecipeList().put(recipeName, recipe);
    }

    public void createRecipeGui(Player p) {

        ArrayList<ItemStack> ingredientList = new ArrayList<>();
        AtomicReference<ItemStack> resultItem = new AtomicReference<>();
        Gui gui = new Gui(5,"Create Recipe");
        gui.setOnTopClick(e -> e.setCancelled(true));

        OutlinePane ingredients = new OutlinePane(0,0,7,5);
        OutlinePane result = new OutlinePane(8,0,1,5);

        OutlinePane separation = new OutlinePane(7,0,1,5);
        separation.addItem(new GuiItem(new ItemStack(Material.GLASS_PANE)));
        separation.setRepeat(true);

        result.addItem(new GuiItem(new ItemStack(Material.IRON_AXE), e -> {
            Recipe recipe = new Recipe(ChatColor.stripColor(resultItem.get().getItemMeta().getDisplayName()), resultItem.get(), ingredientList, 0,0);

            oniris.getJobManager().addJobRecipe(result.getItems().get(1).getItem().getItemMeta().getDisplayName(), recipe);
        }));

        result.addItem(new GuiItem(FonctionUtils.createItem("blacksmith", Material.PAPER, ItemType.DISPLAY_ITEM, Ranks.COMMON, 1, 0).getItemStack(),e -> {
            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("blacksmith"))
                e.getCurrentItem().getItemMeta().setDisplayName("chimiste");
            else
                e.getCurrentItem().getItemMeta().setDisplayName("blacksmith");
            gui.update();
        }));

        gui.setOnBottomClick( e -> {
            e.setCancelled(true);
            if(e.getClick().equals(ClickType.RIGHT)) {
                result.insertItem(new GuiItem(Objects.requireNonNull(e.getCurrentItem())), 2);
                resultItem.set(e.getCurrentItem());
            } else if (e.getClick().equals(ClickType.LEFT)) {
                ingredients.addItem(new GuiItem(Objects.requireNonNull(e.getCurrentItem())));
                ingredientList.add(e.getCurrentItem());
            }
            gui.update();
        });


        gui.addPane(ingredients);
        gui.addPane(result);
        gui.addPane(separation);

        gui.show(p);
    }
}
