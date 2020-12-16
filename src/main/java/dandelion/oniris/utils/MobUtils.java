package dandelion.oniris.utils;

import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.IRegistry;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_16_R2.util.CraftNamespacedKey;

public class MobUtils {
    public static EntityTypes entityTypesFromString(String str) {
        return IRegistry.ENTITY_TYPE.get(CraftNamespacedKey.toMinecraft(NamespacedKey.minecraft(str.substring(17))));
    }
}
