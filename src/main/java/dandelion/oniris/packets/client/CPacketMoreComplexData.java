package dandelion.oniris.packets.client;

import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.ItemStack;
import net.minecraft.server.v1_16_R2.PacketDataSerializer;
import net.minecraft.server.v1_16_R2.PacketListener;

import java.io.IOException;
import java.util.UUID;

public class CPacketMoreComplexData extends CPacket {

    int rInt;
    double rDouble;
    float rFloat;
    byte[] rByteArray;
    boolean rBoolean;
    String rString;
    UUID rUuid;
    BlockPosition blockPosition;
    ItemStack itemStack;

    @Override
    public void readPacketData(PacketDataSerializer data) throws IOException {
        rInt = data.readInt();
        rDouble = data.readDouble();
        rFloat = data.readFloat();
        rByteArray = readByteArray(data);
        rBoolean = data.readBoolean();
        rString = readString(data);
        rUuid = readUUID(data);
        blockPosition = readBlockPosition(data);
        itemStack = readItemStack(data);
    }

    @Override
    public void handle(PacketListener listener) {
        System.out.println(rInt);
        System.out.println(rDouble);
        System.out.println(rFloat);
        System.out.println(rByteArray);
        System.out.println(rBoolean);
        System.out.println(rString);
        System.out.println(rUuid);
        System.out.println(blockPosition);
        System.out.println(itemStack);
    }
}
