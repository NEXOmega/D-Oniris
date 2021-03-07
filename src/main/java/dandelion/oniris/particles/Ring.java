package dandelion.oniris.particles;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import org.bukkit.Location;
import org.bukkit.Particle;

public class Ring extends Effect {

    /**
     * Radius of the spawned circles
     */
    public float radius = 0.7f;

    /**
     * Particles per circle
     */
    public int particles = 20;

    /**
     * Particle to display
     */
    public Particle particle = Particle.FIREWORKS_SPARK;

    /**
     * Interval of the circles
     */
    public float grow = .1f;

    public double yOffset = 0;

    /**
     * Circles to display
     */
    public int rings = 15 + DEFAULT_STEP;

    /**
     * Internal counter
     */
    private static final int DEFAULT_STEP = -8;
    private static final int PAUSE = 4;
    protected int step = DEFAULT_STEP;

    public Ring(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        period = 10;
        iterations = -1;
    }

    @Override
    public void onRun() {
        Location location = getLocation();
        if (step > rings + PAUSE) {
            step = DEFAULT_STEP;
        } else if (step > rings) {
            step++;
            return;
        }
        double x, y, z;
        y = step * grow + yOffset;
        location.add(0, y, 0);
        for (int i = 0; i < particles; i++) {
            double angle = (double) 2 * Math.PI * i / particles;
            x = Math.cos(angle) * radius;
            z = Math.sin(angle) * radius;
            location.add(x, 0, z);
            display(particle, location);
            location.subtract(x, 0, z);
        }

        location.subtract(0, y, 0);
        step++;
    }


}
