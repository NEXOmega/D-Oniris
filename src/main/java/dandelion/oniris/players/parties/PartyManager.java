package dandelion.oniris.players.parties;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class PartyManager implements Listener {
    Oniris oniris;
    private LinkedHashMap<String, Party> partiesList;

    public PartyManager(Oniris oniris) {
        this.oniris = oniris;
        this.partiesList = new LinkedHashMap<>();
    }

    public void createParty(String name, Player player) {
        if(oniris.getPlayers().get(player).getPartyUUID() != null) {
            player.sendMessage(ChatColor.RED + "Vous devez d'abord quitter votre précédent groupe. /party leave");
            return;
        }

        Party party = new Party(name);
        partiesList.put(party.getUUID(), party);
        player.sendMessage(ChatColor.RED + "Vous avez crée le groupe " + ChatColor.GREEN + party.getName());
        inviteParty(player, party.getUUID(), player.getName(), false);
        joinParty(party.getUUID(), player);
    }

    public void inviteParty(Player sender, String partyUUID, String playerName, boolean message) {
        if(partiesList.containsKey(partyUUID)) {
            if (Bukkit.getPlayer(playerName) != null) {
                partiesList.get(partyUUID).getRequestedPlayers().add(Bukkit.getPlayer(playerName).getUniqueId().toString());
                if (message)
                    Bukkit.getPlayer(playerName).sendMessage(sender.getName() + " vous à invité à rejoindre son groupe.");
            }
        }else
                sender.sendMessage(ChatColor.RED + "Le joueur n'a pas été trouvé.");

    }

    public void joinParty(String partyUUID, Player player) {
        if(!partiesList.containsKey(partyUUID)) {
            player.sendMessage(ChatColor.RED + "Erreur, ce groupe n'existe pas.");
            return;
        }
        if(partiesList.get(partyUUID).getRequestedPlayers().contains(player.getUniqueId().toString())) {
            partiesList.get(partyUUID).getPlayers().add(player);
            oniris.getPlayers().get(player).setPartyUUID(partyUUID);
            player.sendMessage(ChatColor.RED + "Vous avez rejoins le groupe " + ChatColor.GREEN + partiesList.get(partyUUID).getName());
        } else {
            player.sendMessage(ChatColor.RED + "Vous n'avez pas été invité à rejoindre ce groupe.");
        }
    }

    public void leftParty(String partyUUID, Player player) {
        partiesList.get(partyUUID).getPlayers().remove(player);
        oniris.getPlayers().get(player).setPartyUUID(null);
        player.sendMessage(ChatColor.RED + "Vous êtes parti du groupe " + partiesList.get(partyUUID).getName());

        partiesList.get(partyUUID).getPlayers().forEach(pl -> {
            pl.sendMessage(ChatColor.GREEN + player.getName() + " est parti du groupe.");
        });

        if(partiesList.size() < 1)
            partiesList.remove(partyUUID);
    }
}
