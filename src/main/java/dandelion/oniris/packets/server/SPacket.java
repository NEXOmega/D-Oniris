package dandelion.oniris.packets.server;

import dandelion.oniris.packets.DPacket;
import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.NBTTagCompound;
import net.minecraft.server.v1_16_R2.PacketDataSerializer;
import net.minecraft.server.v1_16_R2.PacketListener;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;

import java.util.UUID;

public abstract class SPacket extends DPacket {
    @Override
    public void readPacketData(PacketDataSerializer data) {
        //Sent from server
    }

    @Override
    public void handle(PacketListener listener) {
        //Sent from server
    }

    public void writeString(PacketDataSerializer data, String msg) {
        data.writeInt(msg.length());
        data.a(msg);
    }

    public void writeUUID(PacketDataSerializer data, UUID uuid) {
        data.a(uuid);
    }

    public void writeNBTTagCompound(PacketDataSerializer data, NBTTagCompound nbt) {
        data.a(nbt);
    }

    public void writeItemStack(PacketDataSerializer data, net.minecraft.server.v1_16_R2.ItemStack is) {
        data.a(is);
    }

    public void writeItemStackBukkit(PacketDataSerializer data, org.bukkit.inventory.ItemStack is) {
        writeItemStack(data, CraftItemStack.asNMSCopy(is));
    }

    public void writeBlockPosition(PacketDataSerializer data, BlockPosition pos) {
        data.a(pos);
    }

    public void writeBlockPosition(PacketDataSerializer data, Location loc) {
        this.writeBlockPosition(data, new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
    }

    public void writeEnum(PacketDataSerializer data, final Enum<?> in) {
        data.a(in);
    }

    public void writeByteArray(PacketDataSerializer data, byte...bytes) {
        data.a(bytes);
    }
}
