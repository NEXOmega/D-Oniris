package dandelion.oniris.packets;

import net.minecraft.server.v1_16_R2.PacketDataSerializer;
import net.minecraft.server.v1_16_R2.PacketListener;
import net.minecraft.server.v1_16_R2.Packet;

import java.io.IOException;

public abstract class DPacket implements Packet {
    @Override
    public void a(PacketDataSerializer packetDataSerializer) throws IOException {
        readPacketData(packetDataSerializer);
    }

    @Override
    public void b(PacketDataSerializer packetDataSerializer) throws IOException {
        writePacketData(packetDataSerializer);
    }

    @Override
    public void a(PacketListener packetListener) {
        handle(packetListener);
    }

    public abstract void readPacketData(PacketDataSerializer data) throws IOException;
    public abstract void writePacketData(PacketDataSerializer data) throws IOException;
    public abstract void handle(PacketListener listener);
}
