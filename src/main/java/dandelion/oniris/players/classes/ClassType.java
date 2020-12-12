package dandelion.oniris.players.classes;

import dandelion.oniris.players.Stats;
import dandelion.oniris.players.classes.list.*;
import dandelion.oniris.players.classes.progressions.*;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;

public enum ClassType {
    JUDGE(new Judge(), null),
    ALFORGE(new AlForge(), null),

    CHAMPION(new Champion(), null),
    BERSERK(new Berserk(), new BerserkProgression()),

    PRIEST(new Priest(), null),
    MAGICIEN(new Magician(), new MageProgression()),

    HUNTER(new Hunter(), null),
    ARCHER(new Archer(), new ArcherProgression()),

    VILLAGER(new Villager(), new VillagerProgression());

    RPGClass rpgClass;
    ProgressionTree progressionTree;

    ClassType(RPGClass rpgClass, ProgressionTree progressionTree) {
        this.rpgClass = rpgClass;
        this.progressionTree = progressionTree;
    }

    public static ClassType getClassType(String s) {
        for (ClassType ct : ClassType.values()) {
            if (s.equalsIgnoreCase(ct.toString()))
                return ct;
        }
        return VILLAGER;
    }

    public RPGClass getRpgClass() {
        return rpgClass;
    }

    public ProgressionTree getProgressionTree() {
        return progressionTree;
    }

    public String getClassName()
    {
        return rpgClass.getName();
    }

    public void setName(String name) {
        rpgClass.name = name;
    }

    public Stats getStats() {
        return rpgClass.stats;
    }

    public void setStats(Stats stats) {
        rpgClass.stats = stats;
    }

    public ItemStack getItemStack() {
        return rpgClass.itemStack;
    }

    public String[] getSpellList() {
        return rpgClass.spellList;
    }

    public LinkedList<String> getWeaponType() {
        return rpgClass.weaponTypes;
    }

}
