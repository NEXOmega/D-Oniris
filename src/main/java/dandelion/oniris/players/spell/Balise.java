package dandelion.oniris.players.spell;

import dandelion.oniris.Oniris;
import org.bukkit.Bukkit;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public abstract class Balise {
    public int time;
    Player player;
    public Balise(Player player, int time) {
        this.time = time;
        this.player = player;
    }


    public void summon(){
        Entity ent = player.getWorld().spawnEntity(player.getLocation().add(0,1,0), EntityType.ENDER_CRYSTAL);
        ((EnderCrystal)ent).setShowingBottom(false);
        onSummon(ent);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Oniris.getInstance(), () -> {
            ent.remove();
            onDespawn(ent);
        }, time*20);
    };

    public abstract void onSummon(Entity entity);
    public abstract void onDespawn(Entity entity);
}
