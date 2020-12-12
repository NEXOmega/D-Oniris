package dandelion.oniris.utils;

import dandelion.oniris.jobs.Job;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Recipe {
    String name;
    ItemStack result;
    ArrayList<ItemStack> ingredients;
    int exp;
    int levelRequired;

    public Recipe(String name, ItemStack result, ArrayList<ItemStack> ingredients, int exp, int levelRequired) {
        this.name = name;
        this.result = result;
        this.ingredients = ingredients;
        this.exp = exp;
        this.levelRequired = levelRequired;
    }

    public void craft(Player p, Job job) {
        if(hasIngredients(p)){
            if(job.getLevel() < this.levelRequired) {
                p.sendMessage("Vous devez être niveau " + levelRequired + " pour créer cet objet.");
            } else {
                ArrayList<ItemStack> itemTaken = new ArrayList<>();
                itemTaken.clear();
                ingredients.forEach((item) -> {
                    if(!itemTaken.contains(item)) {
                        itemTaken.add(item);
                        p.getInventory().removeItem(item);
                        System.out.println(itemTaken);
                    }
                });
                p.getInventory().addItem(result);
                if(job.addExp(this.exp))
                    p.sendMessage("Brave vous avez monté niveau " + job.getLevel() + " !");
                p.sendMessage("Item Crafted " + this.exp + " Exp Gained !");
            }
        } else {
            p.sendMessage("Missing ingredients");
        }
    }

    public boolean hasIngredients(Player p) {
        AtomicInteger numberOfItems = new AtomicInteger();
        ingredients.forEach((item) -> {
            if(p.getInventory().containsAtLeast(item, item.getAmount()))
                numberOfItems.getAndIncrement();
        });
        return numberOfItems.get() == ingredients.size();
    }

    public String getName() {
        return name;
    }

    public ItemStack getResult() {
        return result;
    }

    public ArrayList<ItemStack> getIngredients() {
        return ingredients;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLevelRequired() {
        return levelRequired;
    }

    public void setLevelRequired(int levelRequired) {
        this.levelRequired = levelRequired;
    }
}
