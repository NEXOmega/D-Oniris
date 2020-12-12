package dandelion.oniris.packets.server;

import net.minecraft.server.v1_16_R2.PacketDataSerializer;

import java.io.IOException;

public class SPacketSpellLearned extends SPacket {
    String spellname;

    public SPacketSpellLearned(String spellname) {
        this.spellname = spellname;
    }

    @Override
    public void writePacketData(PacketDataSerializer data) throws IOException {
        writeString(data, spellname);
    }
}
