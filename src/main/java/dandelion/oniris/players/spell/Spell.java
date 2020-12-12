package dandelion.oniris.players.spell;

import dandelion.oniris.Oniris;
import dandelion.oniris.events.onSpellSense;
import dandelion.oniris.players.PlayerStats;
import de.slikey.effectlib.EffectType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static de.slikey.effectlib.EffectType.INSTANT;

public abstract class Spell {
    public boolean asynchronous = true;
    public int delay = 0;
    public int period = 1;
    private EffectType type = INSTANT;

    public boolean canceled = false;

    private void castFromPlayer(Player p, PlayerStats stats, Oniris oniris, boolean forcing) {
        if(!forcing) {
            if(stats.getSpells().contains(this.toString())) {
                if(p.getLevel() >= SpellType.valueOf(this.getClass().getName().toUpperCase()).getLevel()) {
                    onLaunched(p,oniris,stats);
                } else {
                    p.sendMessage("Niveau du joueur trop bas");
                }
            } else {
                p.sendMessage("Le joueur n'a pas ce sort");
            }
        } else {
            onLaunched(p,oniris,stats);
        }
    }

    public void onLaunched(Player sender, Oniris oniris, PlayerStats stats) {
        String name = this.getClass().getSimpleName().toUpperCase();
        System.out.println(name);
        stats.onLaunchSpell(sender, stats, SpellType.valueOf(name), this);
        SpellType spell = SpellType.valueOf(name);
        sender.getNearbyEntities(spell.getTier() * 10, spell.getTier() *10, spell.getTier() *10).forEach(e -> {
            if(e instanceof Player)
                Bukkit.getPluginManager().callEvent(new onSpellSense(false, sender, (Player)e, SpellType.valueOf(name), this, onSpellSense.Target.GLOBAL));
        });
        if(isCanceled()) {
            sender.sendMessage("Le sort a été annulé !");
            this.setCanceled(false);
            return;
        }
        launch(sender, oniris, stats);
    };

    public abstract void launch(Player sender, Oniris oniris, PlayerStats data);

    //Spell Type
    public EffectType getType() {
        return type;
    }

    public void setType(EffectType type) {
        this.type = type;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public boolean isAsynchronous() {
        return asynchronous;
    }

    public void setAsynchronous(boolean asynchronous) {
        this.asynchronous = asynchronous;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
