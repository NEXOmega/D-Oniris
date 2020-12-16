package dandelion.oniris.utils;

import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.IRegistry;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_16_R2.util.CraftNamespacedKey;

public class MobUtils {
    public static EntityTypes entityTypesFromString(String str) {
        //TODO correct entity type name (cf Doc & split)
        System.out.println(str);
        System.out.println(str.toLowerCase().split(".")[2]);
        return IRegistry.ENTITY_TYPE.get(CraftNamespacedKey.toMinecraft(NamespacedKey.minecraft(str.toLowerCase().split(".")[2])));
    }
}
