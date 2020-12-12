package dandelion.oniris.jobs;

public abstract class Job {
    int level;
    int exp;

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public void updateLevel() {
        this.level += 1;
    }

    public boolean addExp(int exp) {
        this.exp += exp;
        if (canLevelup()) {
            updateLevel();
            return true;
        }
        return false;
    }

    public boolean canLevelup() {
        return exp >  Math.pow(2, (level + 5));
    }

    public int getExpUntilNextLevel() {
        System.out.println("Level " + level + "Exp from 0 to next " + (level+1)*64);
        return (int) (Math.pow(2, (level + 5)) - exp);
    }
}
