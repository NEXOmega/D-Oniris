package dandelion.oniris.commands.spell;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.spell.LocationSpell;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.spell.SpellType;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static dandelion.oniris.utils.FonctionUtils.capitalize;

public class LaunchSpell implements TabExecutor {
    Oniris oniris;
    public LaunchSpell(Oniris oniris) {
        this.oniris = oniris;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(args.length == 0) {
                oniris.getPlayers().get(sender).getSpells().forEach(spell -> {
                    SpellType sp = SpellType.valueOf(spell.toUpperCase());
                    sender.sendMessage(sp.getRank().getChatColor() + capitalize(sp.getName()) + ", Element: " + sp.getElements() + ", Rang: " + capitalize(sp.getRank().getRarity()) + ", Delay: " +sp.getDelay());
                    sender.sendMessage(sp.getRank().getChatColor() + sp.getLore().toString());
                });
                return true;
            }
            SpellType s = SpellType.valueOf(args[0].toUpperCase());
            if(oniris.getPlayers().get(sender).getSpells().contains(s.toString().toLowerCase())) {
                if(s.getLevel() <= ((Player) sender).getLevel()) {
                    try {
                        oniris.getSpellManager().cast((Player) sender,oniris,oniris.getPlayers().get(sender),s);
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    sender.sendMessage(ChatColor.RED +"Vous debloquerez ce sort au niveau : " +ChatColor.YELLOW + s.getLevel());
                }

            } else {
                sender.sendMessage(ChatColor.RED +"Erreur, vous ne connaissez pas ce sort");
            }
        } else {
            Spell s = null;
            try {
                s = SpellType.valueOf(args[0].toUpperCase()).getSpell().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            BlockCommandSender blockSender = (BlockCommandSender) sender;
            if(s instanceof LocationSpell) {
                Location loc = new Location(blockSender.getBlock().getWorld(), Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));
                ((LocationSpell) s).launchFromLocation(loc, oniris, null);
            }
        }

        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> tab = new ArrayList<>();
        for (String s : oniris.getPlayers().get(sender).getSpells()) {
            if(s.startsWith(args[args.length-1]))
                tab.add(s);
        }
        return tab;
    }
}

