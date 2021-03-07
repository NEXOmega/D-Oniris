package dandelion.oniris.jobs;

public class JobLevel {
    int lumberjack, blacksmith, alchemist = 0;

    public JobLevel(int alchemist, int blacksmith, int lumberjack) {
        this.lumberjack = lumberjack;
        this.blacksmith = blacksmith;
        this.alchemist = alchemist;
    }
    public int getJobFromName(String jobName) {
        jobName = jobName.toLowerCase();
        switch (jobName) {
            case "alchemist": return alchemist;
            case "blacksmith": return blacksmith;
            case "lumberjack": return lumberjack;
        }
        return -1;
    }


    public int getLumberjack() {
        return lumberjack;
    }

    public void setLumberjack(int lumberjack) {
        this.lumberjack = lumberjack;
    }

    public int getBlacksmith() {
        return blacksmith;
    }

    public void setBlacksmith(int blacksmith) {
        this.blacksmith = blacksmith;
    }

    public int getAlchemist() {
        return alchemist;
    }

    public void setAlchemist(int alchemist) {
        this.alchemist = alchemist;
    }
}
