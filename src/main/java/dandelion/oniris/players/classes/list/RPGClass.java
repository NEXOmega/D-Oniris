package dandelion.oniris.players.classes.list;

import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.Stats;
import dandelion.oniris.players.support.Support;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public abstract class RPGClass {
    public String name;
    public Stats stats;
    public ItemStack itemStack;
    public String[] spellList;
    public LinkedList<String> weaponTypes = new LinkedList<>();
    public ArrayList<spec> species = new ArrayList<>();
    public Support support = null;

    public enum spec {
        NOARMOR, ELYTRA
    }

    RPGClass(String name, Stats stats, ItemStack itemStack, String[] spellList, String[] weaponTypes) {
        this.name = name;
        this.stats = stats;
        this.itemStack = itemStack;
        this.spellList = spellList;

        this.weaponTypes.addAll(Arrays.asList(weaponTypes));
    }

    public String getName() {
        return name;
    }

    public ArrayList<spec> getSpecies() {
        return species;
    }

    public void setSpecies(ArrayList<spec> species) {
        this.species = species;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    public abstract void onClassSelected(Player player, PlayerStats playerStats);

}
