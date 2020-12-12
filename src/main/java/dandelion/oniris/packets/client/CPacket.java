package dandelion.oniris.packets.client;

import dandelion.oniris.packets.DPacket;
import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.ItemStack;
import net.minecraft.server.v1_16_R2.NBTTagCompound;
import net.minecraft.server.v1_16_R2.PacketDataSerializer;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;

import java.io.IOException;
import java.util.UUID;

public abstract class CPacket extends DPacket {
    @Override
    public void writePacketData(PacketDataSerializer data) {
        //Sent from client
    }

    public String readString(PacketDataSerializer data) {
        int stringLength = data.readInt();
        return data.e(stringLength);
    }

    public byte[] readByteArray(PacketDataSerializer data) {
        return data.a();
    }

    public UUID readUUID(PacketDataSerializer data) {
        return data.k();
    }

    /*public <T extends Enum<T>> T readEnum(PacketDataSerializer data, final Class<T> oclass) {
        return oclass.getEnumConstants()[data.e()];
    }*/

    public BlockPosition readBlockPosition(PacketDataSerializer data) {
        return data.e();
    }

    public int[] readBlockPositionBukkit(PacketDataSerializer data) {
        BlockPosition pos = readBlockPosition(data);
        return new int[] {pos.getX(), pos.getY(), pos.getZ()};
    }

    public ItemStack readItemStack(PacketDataSerializer data) throws IOException {
        return data.n();
    }

    public org.bukkit.inventory.ItemStack readItemStackBukkit(PacketDataSerializer data) throws IOException {
        return CraftItemStack.asBukkitCopy(readItemStack(data));
    }

    public NBTTagCompound readNBTTagCompound(PacketDataSerializer data) throws IOException {
        return data.l();
    }
}
