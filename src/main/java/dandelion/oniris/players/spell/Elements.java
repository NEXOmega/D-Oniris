package dandelion.oniris.players.spell;

public enum Elements {
    PHYSIC,
    MAGIC,
    SOUND,
    WHITE,
    BLACK,
    FIRE,
    WATER,
    EARTH,
    ELECTRIC,
    VOID,
    ARCANE;

    public static String getElements() {
        StringBuilder elements = new StringBuilder("Elements : ");
        for (Elements element : Elements.values()) {
            elements.append(element);
        }
        return elements.toString();
    }
}
