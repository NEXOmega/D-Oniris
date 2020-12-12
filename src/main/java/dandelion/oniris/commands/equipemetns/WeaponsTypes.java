package dandelion.oniris.commands.equipemetns;

public enum WeaponsTypes {
    STAFF("shovel",true, 1000),
    HIGHSTAFF("shovel",false, 2000),
    SPEAR("shovel",false, 3000),
    GAUNTLETS("",false, 1000),
    SWORD("sword",true, 1000),
    BROADSWORD("sword",false, 2000),
    KATANA("sword",true, 3000),
    DAGGER("sword",true, 4000),
    AXE("axe",false, 1000),
    MACE("axe",false, 2000),
    BOW("bow",false, 1000),
    LONGBOW("bow",false, 2000),
    SCYTHE("hoe",false, 1000),
    CARDS("snowball", false, 10);
    boolean dualHand;
    int modelInterval;
    String material;

    WeaponsTypes(String material, boolean dualHand, int modelInterval){
        this.dualHand = dualHand;
        this.modelInterval = modelInterval;
        this.material = material;
    }

    public boolean isDualHand() {
        return dualHand;
    }
}
