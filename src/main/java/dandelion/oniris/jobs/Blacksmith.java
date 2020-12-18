package dandelion.oniris.jobs;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import dandelion.oniris.players.PlayerStats;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicInteger;

public class Blacksmith extends CraftingJob {
    /*
    Peut analyser les armes
     */

    transient Gui gui = new Gui(6, "Blacksmith");

    public void openGui(Player p, PlayerStats playerStats) {
        gui.setOnGlobalClick(e -> e.setCancelled(true));

        OutlinePane recipesPane = new OutlinePane(0,0,9,5);
        OutlinePane optionPane = new OutlinePane(0,5,9,1);
        AtomicInteger i = new AtomicInteger();
        for(String s : this.knowingRecipe.subList((9*5)* i.get(), (Math.min((9 * 5) * (i.get() + 1), this.knowingRecipe.size())))) {
            recipesPane.addItem(new GuiItem(Blacksmith.recipeList.get(s).getResult(), clickEvent -> this.getRecipe(s).craft(p, playerStats.getJobStats().getBlacksmith())));
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
        optionPane.addItem(new GuiItem(new ItemStack(Material.PAPER), e -> {

        }));

        gui.addPane(recipesPane);
        gui.addPane(optionPane);
        gui.show(p);

    }
}
