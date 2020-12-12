package dandelion.oniris.players.spell;

import dandelion.oniris.Oniris;
import dandelion.oniris.events.onSpellLaunched;
import dandelion.oniris.players.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SpellManager {
    private Oniris oniris;

    private static List<SpellManager> effectManagers;
    private static Map<String, Class<? extends Spell>> effectClasses = new HashMap<>();
    private final Map<Spell, BukkitTask> effects;
    private boolean disposed;
    private boolean disposeOnTermination;
    private Map<UUID,HashMap<SpellType,Long>> spellDelay = new HashMap<>();
    public Map<UUID, HashMap<SpellType, Long>> getSpellDelay() {
        return spellDelay;
    }

    public SpellManager(Oniris oniris) {
        this.oniris = oniris;
        effects = new HashMap<>();
        disposed = false;
        disposeOnTermination = false;
    }

    public void cast(Player p, Oniris oniris, PlayerStats stats, SpellType type) throws InstantiationException, IllegalAccessException {
        if(spellDelay.containsKey(p.getUniqueId())) {
            HashMap<SpellType,Long> list = spellDelay.get(p.getUniqueId());
            if(list.containsKey(type)) {
                if(list.get(type) <= System.currentTimeMillis()) {
                    list.replace(type,type.getDelay() + System.currentTimeMillis());
                    Spell sp = type.getSpell().newInstance();
                    Bukkit.getPluginManager().callEvent(new onSpellLaunched(p, stats, sp));
                    //castSpell(p,oniris,stats, spell);
                    sp.onLaunched(p, oniris, stats);
                } else {
                    String time = (list.get(type) - System.currentTimeMillis()) / 1000 + "S";
                    p.sendMessage("§eRechagement  : §c" + time);
                }
            } else {
                Spell sp = type.getSpell().newInstance();
                sp.onLaunched(p,oniris,stats);
                list.put(type,type.getDelay() + System.currentTimeMillis());
            }
        } else {
            HashMap<SpellType,Long> list = new HashMap<>();
            list.put(type,type.getDelay() + System.currentTimeMillis());
            spellDelay.put(p.getUniqueId(),list);
            Spell sp = type.getSpell().newInstance();
            Bukkit.getPluginManager().callEvent(new onSpellLaunched(p, stats, sp));
            //castSpell(p,oniris,stats, spell);
            sp.onLaunched(p, oniris, stats);
        }
    }
    public void castSpell(Player p, Oniris oniris, PlayerStats stats, Spell spell) {
        BukkitScheduler s = Bukkit.getScheduler();
        BukkitTask task = null;
        SpellRunnable spellRunnable = new SpellRunnable(spell, p, oniris, stats);
        switch (spell.getType()) {
            case INSTANT:
                if(spell.isAsynchronous()) {
                    task = s.runTaskAsynchronously(oniris, spellRunnable);
                } else {
                    task = s.runTask(oniris, spellRunnable);
                }
                break;
            case DELAYED:
                if (spell.isAsynchronous()) {
                    task = s.runTaskLaterAsynchronously(oniris, spellRunnable, spell.getDelay());
                } else {
                    task = s.runTaskLater(oniris, spellRunnable, spell.getDelay());
                }
                break;
            case REPEATING:
                if (spell.isAsynchronous()) {
                    task = s.runTaskTimerAsynchronously(oniris, spellRunnable, spell.getDelay(), spell.getPeriod());
                } else {
                    task = s.runTaskTimer(oniris, spellRunnable, spell.getDelay(), spell.getPeriod());
                }
                break;
        }
        synchronized (this) {
            effects.put(spell, task);
        }
    }
    static class SpellRunnable implements Runnable{
        Spell spell;
        Player player;
        PlayerStats stats;
        Oniris oniris;

        public SpellRunnable(Spell spell,Player player, Oniris oniris, PlayerStats stats) {
            this.spell = spell;
            this.player = player;
            this.stats = stats;
            this.oniris = oniris;
        }

        @Override
        public void run() {
            spell.onLaunched(player, oniris, stats);
        }
    }
}
