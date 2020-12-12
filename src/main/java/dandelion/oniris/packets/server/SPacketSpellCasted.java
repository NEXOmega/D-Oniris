package dandelion.oniris.packets.server;

import net.minecraft.server.v1_16_R2.PacketDataSerializer;

import java.io.IOException;

public class SPacketSpellCasted extends SPacket {
    String spellname;
    int cooldown;

    public SPacketSpellCasted(String spellname, int cooldown) {
        this.spellname = spellname;
        this.cooldown = cooldown;
    }

    @Override
    public void writePacketData(PacketDataSerializer data) throws IOException {
        writeString(data, spellname);
        data.writeInt(cooldown);
    }
}
