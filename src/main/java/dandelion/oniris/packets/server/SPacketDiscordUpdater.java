package dandelion.oniris.packets.server;

import net.minecraft.server.v1_16_R2.PacketDataSerializer;

public class SPacketDiscordUpdater extends SPacket {

    String state, details, largeImageKey, largeImageText, smallImageKey, smallImageText;
    long timestamp;
    boolean isStartTimestamp;

    public SPacketDiscordUpdater(String state, String details, String largeImageKey, String largeImageText, String smallImageKey, String smallImageText, long timestamp, boolean isStartTimestamp) {
        this.state = state;
        this.details = details;
        this.largeImageKey = largeImageKey;
        this.largeImageText = largeImageText;
        this.smallImageKey = smallImageKey;
        this.smallImageText = smallImageText;
        this.timestamp = timestamp;
        this.isStartTimestamp = isStartTimestamp;
    }

    @Override
    public void writePacketData(PacketDataSerializer data) {
        writeString(data, state);
        writeString(data, details);
        writeString(data, largeImageKey);
        writeString(data, largeImageText);
        writeString(data, smallImageKey);
        writeString(data, smallImageText);
        data.writeLong(timestamp);
        data.writeBoolean(isStartTimestamp);
    }
}
