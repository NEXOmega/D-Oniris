package dandelion.oniris.players.spell.list;


import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Vision extends Spell {

    @Override
    public void launch(Player sender, Oniris main, PlayerStats data) {
        sender.getNearbyEntities(10,10,10).forEach(e -> {
            if(e instanceof LivingEntity) {
                if(!(e instanceof Player)) {
                    sender.sendMessage("§d--{ §cEntity §d}--");
                    sender.sendMessage("§eName : §r" + e.getCustomName() + " §eLife : §r" + (int)((LivingEntity) e).getHealth() + "§e/§r" + (int)((LivingEntity) e).getMaxHealth());
                } else {
                    PlayerStats stats = main.getPlayers().get(e);
                    sender.sendMessage("§d--{ §cPlayer §d}--");
                    sender.sendMessage("§eName : §r" + ((Player) e).getDisplayName() + "§eLife : §r" + stats.getHealth() + "§e/§r" + stats.getMaxVitality());
                    sender.sendMessage("§eClasse : §r" + stats.getClassType().getClassName() + "§eLevel : §r" + stats.getLevel());

                    e.sendMessage(sender.getDisplayName() + "§e vous a examine.");
                }
            }
        });
    }
}
