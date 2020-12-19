package dandelion.oniris.entities;

import dandelion.oniris.entities.entity.DandelionEntity;
import net.minecraft.server.v1_16_R2.ControllerLook;
import net.minecraft.server.v1_16_R2.EntityInsentient;
import net.minecraft.server.v1_16_R2.EntityPlayer;

public class LookController extends ControllerLook {
    protected final DandelionEntity ridable;
    private float yawOffset = 0;
    private float pitchOffset = 0;

    public LookController(DandelionEntity ridable) {
        super((EntityInsentient) ridable);
        this.ridable = ridable;
    }

    public void setYawPitch(float yaw, float pitch) {
        a.aS = a.aS = a.lastYaw = a.yaw = (yaw + yawOffset) % 360.0F;
        a.pitch = (pitch + pitchOffset) % 360.0F;
    }

    public void setOffsets(float yaw, float pitch) {
        yawOffset = yaw;
        pitchOffset = pitch;
    }

    // tick
    @Override
    public void a() {
        if (ridable.getRidingPassenger() != null) {
            tick((EntityPlayer) ridable.getRidingPassenger());
        } else {
            tick();
        }
    }

    public void tick(EntityPlayer rider) {
        setYawPitch(rider.yaw, rider.pitch * 0.5F);
    }

    public void tick() {
        super.a();
    }
}
