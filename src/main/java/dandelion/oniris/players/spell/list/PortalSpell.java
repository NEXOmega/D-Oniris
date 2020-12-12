package dandelion.oniris.players.spell.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import org.bukkit.entity.Player;

public class PortalSpell extends Spell {


    @Override
    public void launch(Player sender, Oniris main, PlayerStats data) {
        sender.openInventory(main.getGui().getPortalStoneGui().inv(sender,data));
    }
}
