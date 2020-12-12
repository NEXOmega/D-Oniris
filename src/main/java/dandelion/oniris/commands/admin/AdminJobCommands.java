package dandelion.oniris.commands.admin;

import dandelion.oniris.Oniris;
import dandelion.oniris.jobs.Blacksmith;
import dandelion.oniris.utils.Recipe;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdminJobCommands implements CommandExecutor {
    Oniris oniris;

    public AdminJobCommands(Oniris oniris) {
        this.oniris = oniris;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(args[0].equalsIgnoreCase("add")) {
            ArrayList<ItemStack> ingredients = new ArrayList<>();
            for(int i = 1; i <= 8; i++) {
                if(((Player)commandSender).getInventory().getContents()[i] != null) {
                ingredients.add(((Player) commandSender).getInventory().getContents()[i]);
                }
            }

            Recipe recipe = new Recipe(ChatColor.stripColor(((Player)commandSender).getInventory().getItem(0).getItemMeta().getDisplayName()), ((Player)commandSender).getInventory().getItem(0), ingredients, 0, 0);
            oniris.getJobManager().addJobRecipe("blacksmith", recipe);
            commandSender.sendMessage("Recipe added");
        }else {
            StringBuilder sb = new StringBuilder();
            for(String arg : args)
                sb.append(" " + arg);
            sb.deleteCharAt(0);
            if(Blacksmith.getRecipeList().containsKey(sb.toString())) {
                oniris.getJobManager().learnJobRecipe(oniris.getPlayers().get(commandSender), "blacksmith", sb.toString());
                commandSender.sendMessage(sb.toString() + " Recipe learned !");
            }
            else
                commandSender.sendMessage("La recette n'existe pas");
        }
        return false;
    }
}
