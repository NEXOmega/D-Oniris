package dandelion.oniris.listerners;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.upperlevel.spigot.book.BookUtil;

import java.util.Objects;

public class PlayerChatListener implements Listener {

    static Player player;

    @EventHandler
    public void playerChatEvent(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        player = e.getPlayer();
        TextComponent message = new TextComponent(ChatColor.GRAY + e.getPlayer().getName() + " : ");
        message.addExtra(formatMessage(e.getMessage()));
        e.getPlayer().spigot().sendMessage(message);
    }

    public static TextComponent formatMessage(String string) {
        TextComponent textComponent = new TextComponent();
        String[] vars = StringUtils.substringsBetween(string, "%","%");
        if(vars == null)
            return new TextComponent(string);
        for(int i = 0; i <= vars.length; i++) {
            System.out.println(string.split("%").length + " " + 2*i);
            if(string.split("%").length > 2*i)
                textComponent.addExtra(string.split("%")[(2*i)]);
            if(i != vars.length) {
                textComponent.addExtra(Objects.requireNonNull(formatVariable(vars[i], player)));
            }
        }
        return textComponent;
    }

    public static BaseComponent formatVariable(String var, Object varSource) {
        BookUtil.TextBuilder textBuilder = new BookUtil.TextBuilder();
        if(varSource instanceof Player) {
            Player pl = (Player) varSource;
            PlayerStats stats = Oniris.getInstance().getPlayers().get(pl);

            if(var.contains("item")) {
                if(var.equalsIgnoreCase("item"))
                    return textBuilder.text(pl.getInventory().getItemInMainHand().getItemMeta().getDisplayName()).onHover(BookUtil.HoverAction.showItem(pl.getInventory().getItemInMainHand())).build();
                else if(!var.replaceAll("[^\\d.]", "").equals("")) {
                    return textBuilder.text(pl.getInventory().getItem(Integer.parseInt(var.replaceAll("[^\\d.]",""))).getItemMeta().getDisplayName())
                            .onHover(BookUtil.HoverAction.showItem(pl.getInventory().getItem(Integer.parseInt(var.replaceAll("[^\\d.]",""))))).build();
                }
            } else if(var.equalsIgnoreCase("me")) {
                TextComponent base = new TextComponent();
                base.setText(pl.getDisplayName());

                BaseComponent description = new TextComponent();
                description.addExtra(ChatColor.GRAY + "Level : " + ChatColor.YELLOW + stats.getLevel());
                description.addExtra(ChatColor.GRAY + "\nClasse : " + ChatColor.YELLOW + stats.getClassType().name());
                description.addExtra(ChatColor.GRAY + "\nSupport : " + ChatColor.YELLOW + stats.getSupportType().name());
                description.addExtra(ChatColor.GRAY + "\nStats : " +
                        ChatColor.RED + stats.getMaxAttack() + " " +
                        ChatColor.DARK_PURPLE + stats.getMaxMagie()+ " " +
                        ChatColor.LIGHT_PURPLE + stats.getMaxDefence() +" " +
                        ChatColor.YELLOW + stats.getMaxVitality() + " " +
                        ChatColor.WHITE + stats.getMaxSpeed());

                base.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(description).create()));
                return base;
            }
        }
        return null;
    }
}
