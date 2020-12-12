package dandelion.oniris.commands.equipemetns;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.upperlevel.spigot.book.BookUtil;

import java.util.List;

public class ShowItemCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        BookUtil.TextBuilder textBuilder = new BookUtil.TextBuilder();
        textBuilder.color(ChatColor.BLUE).style(ChatColor.BOLD);
        if(args.length == 0) {
            Player p = (Player) commandSender;

            if(p.getInventory().getItemInMainHand().getType() != Material.AIR)
                if(p.getInventory().getItemInMainHand().getItemMeta().hasDisplayName())
                    textBuilder.text(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName()).onHover(BookUtil.HoverAction.showItem(p.getInventory().getItemInMainHand()));
                else
                    textBuilder.text(p.getInventory().getItemInMainHand().getType().name()).onHover(BookUtil.HoverAction.showItem(p.getInventory().getItemInMainHand()));
            else
                textBuilder.text("Erreur, vous devez avoir un item en main");
            commandSender.spigot().sendMessage(textBuilder.build());
        } else {
            Player p = (Player) commandSender;
            ItemStack i = p.getInventory().getItem(Integer.parseInt(args[0]));

            if(i.getType() != Material.AIR)
                if(i.getItemMeta().hasDisplayName())
                    textBuilder.text( "[" + i.getItemMeta().getDisplayName() + "]").onHover(BookUtil.HoverAction.showItem(i));
                else
                    textBuilder.text(i.getType().name()).onHover(BookUtil.HoverAction.showItem(i));
            else
                textBuilder.text("Erreur, vous devez avoir un item dans le slot choisit");
            commandSender.spigot().sendMessage(textBuilder.build());
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
