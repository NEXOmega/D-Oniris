package dandelion.oniris.players.parties;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class Party {
    private String name, uuid, objective;
    private ArrayList<Player> players;
    private ArrayList<String> requestedPlayers;
    private PartyRules rules;

    public Party(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID().toString();
        this.rules = new PartyRules();
        players = new ArrayList<>();
        requestedPlayers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getUUID() {
        return uuid;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public ArrayList<String> getRequestedPlayers() {
        return requestedPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    static class PartyRules {
        protected boolean sharedExp = true;

        public boolean isSharedExp() {
            return sharedExp;
        }

        public void setSharedExp(boolean sharedExp) {
            this.sharedExp = sharedExp;
        }
    }
}
