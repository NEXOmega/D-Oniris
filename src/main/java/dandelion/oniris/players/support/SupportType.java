package dandelion.oniris.players.support;

public enum SupportType {
    STEALTH(new Stealth()),
    INVIDIA(new Invidia()),
    ABSORPTIONMAGIQUE(new AbsorptionMagique()),
    WORLDOFJUMP(new WorldOfJump()),
    BERSERKMODE(new BerserkMode()),
    INFINITEARROW(new InfiniteArrow()),
    SUPERNOVA(new Supernova()),
    THEFOOL(new TheFool()),
    MAGICSENSOR(new MagicSensor()),
    BREAKINGEDGE(new BreakingEdge()),
    MAGICSICKNESS(new MagicSickness()),
    NOLONGERHUMAN(new NoLongerHuman());

    Support support;

    SupportType(Support support) {
        this.support = support;
    }

    public Support getSupport() {
        return support;
    }
}
