package dandelion.oniris.packets;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.reflect.FieldUtils;
import com.comphenix.protocol.reflect.accessors.Accessors;
import com.google.common.collect.BiMap;
import dandelion.oniris.packets.server.SPacket;
import net.minecraft.server.v1_16_R2.EnumProtocol;
import net.minecraft.server.v1_16_R2.EnumProtocolDirection;
import net.minecraft.server.v1_16_R2.Packet;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CustomPacketManager {
    private static final HashMap<Class<? extends DPacket>, PacketType> packetToType = new HashMap<>();

    public static void registerPackets() {
/*
        registerPacket(SPacketDiscordUpdater.class, PacketIds.SPacketDiscordUpdater, PacketType.Sender.SERVER);
        registerPacket(SPacketSpellCasted.class, PacketIds.SPacketSpellCasted, PacketType.Sender.SERVER);
        registerPacket(SPacketSpellLearned.class, PacketIds.SPacketSpellLearned, PacketType.Sender.SERVER);

        registerPacket(CPacketMoreComplexData.class, PacketIds.CPacketMoreComplexData, PacketType.Sender.CLIENT);

 */
    }

    public static void registerPacket(Class<? extends DPacket> packetClass, int packetId, PacketType.Sender sender) {
        final PacketType packetType = new PacketType(PacketType.Protocol.PLAY, sender, packetId, packetId);

        packetToType.put(packetClass, packetType);

        final EnumProtocol protocol = EnumProtocol.PLAY;
        final EnumProtocolDirection protocolDirection = packetType.isClient() ? EnumProtocolDirection.SERVERBOUND : EnumProtocolDirection.CLIENTBOUND;

        try {
            //protocol.a().put()
            Map<EnumProtocolDirection, BiMap<Integer, Class<? extends Packet<?>>>> theMap = (Map<EnumProtocolDirection, BiMap<Integer, Class<? extends Packet<?>>>>) FieldUtils.readField(protocol, "h", true);
            BiMap<Integer, Class<? extends Packet<?>>> biMap = theMap.get(protocolDirection);
            biMap.put(packetId, (Class<? extends Packet<?>>) packetClass);
            theMap.put(protocolDirection, biMap);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Map<Class<?>, EnumProtocol> map = (Map<Class<?>, EnumProtocol>) Accessors.getFieldAccessor(EnumProtocol.class, Map.class, true).get(protocol);
        map.put(packetClass, protocol);
    }

    public static void sendCustomPacket(Player player, SPacket sPacket) {
/*
        //TODO Check if client is custom
        PacketContainer packetContainer = new PacketContainer(packetToType.get(sPacket.getClass()), sPacket);

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

 */
    }

    public static PacketType getCustomPacketType(Class<? extends DPacket> clazz) {
        return packetToType.get(clazz);
    }
}
