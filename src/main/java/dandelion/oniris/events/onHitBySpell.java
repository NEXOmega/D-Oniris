package dandelion.oniris.events;

import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.spell.SpellType;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class onHitBySpell extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    public SpellType type;
    Spell spell;
    Entity damager, damagee;
    int damages;
    public onHitBySpell(@NotNull Entity damager, @NotNull Entity damagee, SpellType type, Spell s, int damages) {
        this.type = type;
        this.spell = s;
        this.damagee = damagee;
        this.damager = damager;
        this.damages = damages;
    }

    public SpellType getType() {
        return type;
    }

    public Spell getSpell() {
        return spell;
    }

    public Entity getDamager() {
        return damager;
    }

    public Entity getDamagee() {
        return damagee;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public int getDamages() {
        return damages;
    }

    public void setDamages(int damages) {
        this.damages = damages;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }
}
