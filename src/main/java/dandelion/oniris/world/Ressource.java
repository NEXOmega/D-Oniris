package dandelion.oniris.world;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Ressource {
    String name;
    String material;
    long respawnTime;
    int requiredLevel;
    String jobName;
    ItemStack drop;

    public Ressource(String name, String material, ItemStack drop, String jobName, int requiredLevel, long respawnTime) {
        this.name = name;
        this.material = material;
        this.respawnTime = respawnTime;
        this.requiredLevel = requiredLevel;
        this.jobName = jobName;
        this.drop = drop;
    }

    public boolean loot(Player p) {
        if(requiredLevel > p.getLevel()) {
            p.sendMessage(ChatColor.RED + "Vous ne pouvez pas prendre cette ressource.");
            return false;
        } else {
            p.getInventory().addItem(drop);
            return true;
        }
    }

    public String getName() {
        return name;
    }

    public String getMaterial() {
        return material;
    }

    public long getRespawnTime() {
        return respawnTime;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public String getJobName() {
        return jobName;
    }

    public ItemStack getDrop() {
        return drop;
    }

    public void setDrop(ItemStack drop) {
        this.drop = drop;
    }
}
