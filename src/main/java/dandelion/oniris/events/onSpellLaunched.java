package dandelion.oniris.events;

import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class onSpellLaunched extends Event {
    Player sender;
    PlayerStats stats;
    Spell s;
    public onSpellLaunched(Player sender, PlayerStats stats, Spell s) {
        this.sender = sender;
        this.stats = stats;
        this.s = s;
    }

    public Player getSender() {
        return sender;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public PlayerStats getStats() {
        return stats;
    }

    public void setStats(PlayerStats stats) {
        this.stats = stats;
    }

    public Spell getS() {
        return s;
    }

    public void setS(Spell s) {
        this.s = s;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
