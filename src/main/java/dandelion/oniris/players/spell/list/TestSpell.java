package dandelion.oniris.players.spell.list;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import de.slikey.effectlib.effect.AnimatedBallEffect;
import de.slikey.effectlib.effect.CircleEffect;
import de.slikey.effectlib.effect.DonutEffect;
import de.slikey.effectlib.effect.TextEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;

public class TestSpell extends Spell {
    @Override
    public void launch(Player sender, Oniris oniris, PlayerStats data) {
        TextEffect textEffect = new TextEffect(Oniris.getEffectManager());
        textEffect.setLocation(sender.getLocation());
        textEffect.text = "Je dis oui au chomage";
        textEffect.start();
        /*Villager entityCobaye = (Villager) sender.getWorld().spawnEntity(sender.getEyeLocation().add(0,1,0), EntityType.VILLAGER);
        entityCobaye.setAI(false);

        DonutEffect teleportEffect = new DonutEffect(oniris.getEffectManager());
        teleportEffect.setLocation(entityCobaye.getLocation());

        //teleportEffect.xRotation = sender.getLocation().getPitch();
        teleportEffect.yRotation = sender.getLocation().getYaw();
        //teleportEffect.zRotation = sender.getLocation().getPitch();


        teleportEffect.start();

        new BukkitRunnable() {
            @Override
            public void run() {
                if(teleportEffect.isDone()) {
                    entityCobaye.remove();
                    sender.sendMessage("End of Particle");
                    this.cancel();
                }
            }
        }.runTaskTimer(oniris, 20,20);*/
    }
}
