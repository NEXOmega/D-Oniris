package dandelion.oniris.world;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Random;

public class Treasure {
    long respawnTime;
    int itemNumber;
    boolean isRandom;
    public ArrayList<Reward> itemList;

    public Treasure(long respawnTime, int itemNumber, boolean isRandom) {
        this.respawnTime = respawnTime;
        this.itemNumber = itemNumber;
        this.isRandom = isRandom;
        this.itemList = new ArrayList<Reward>();
    }

    public ArrayList<Reward> getRewards() {
        if(isRandom) {
            ArrayList<Reward> rewards = new ArrayList<>();
            LinkedHashMap<Reward, Integer> doubles = new LinkedHashMap<>();
            for (int i = 0; i < itemNumber; i++) {
                Random rand = new Random();
                Reward r = itemList.get(rand.nextInt(itemList.size()));
                if(doubles.containsKey(r)) {
                    if(doubles.get(r) < r.chanceToGetMultiple) {
                        rewards.add(r);
                        doubles.replace(r, (doubles.get(r)+1));
                    } else {
                        rewards.add(new Reward(badLuckDust(), 0, 0));
                    }
                } else {
                    rewards.add(r);
                    doubles.put(r, 1);
                }
            }
            return rewards;
        } else {
            return (ArrayList<Reward>) Collections.singletonList(itemList.get(0));
        }
    }

    public void addReward(ItemStack item, int chance, int chanceToGetMultiple) {
        itemList.add(new Reward(item, chance, chanceToGetMultiple));
    }

    static class Reward {
        ItemStack item;
        int chance;
        int chanceToGetMultiple = 1;

        public Reward(ItemStack item, int chance, int chanceToGetMultiple) {
            this.item = item;
            this.chance = chance;
            this.chanceToGetMultiple = chanceToGetMultiple;
        }
    }

    public ItemStack badLuckDust() {
        ItemStack badLuck = new ItemStack(Material.GUNPOWDER);
        ItemMeta meta = badLuck.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Poudre de malchance");
        badLuck.setItemMeta(meta);
        return badLuck;
    }
}
