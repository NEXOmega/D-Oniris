package dandelion.oniris.players.spell.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.support.SupportType;
import org.bukkit.entity.Player;

public class BerserkMode extends Spell {

    @Override
    public void launch(Player sender, Oniris oniris, PlayerStats data) {
        data.addBuff(SupportType.BERSERKMODE.getSupport(), (long) (30*1000));
        sender.sendMessage("Vous entrez en mode Berserk.");
    }

}
