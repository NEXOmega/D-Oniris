package dandelion.oniris.jobs;

import org.bukkit.block.Block;

public class JobStats {
    Blacksmith blacksmith = new Blacksmith();
    Alchemist alchemist = new Alchemist();
    Lumberjack lumberjack = new Lumberjack();

    public Blacksmith getBlacksmith() {
        return blacksmith;
    }

    public Alchemist getAlchemist() {
        return alchemist;
    }

    public Job getJobFromName(String jobName) {
        jobName = jobName.toLowerCase();
        switch (jobName) {
            case "alchemist": return alchemist;
            case "blacksmith": return blacksmith;
            case "lumberjack": return lumberjack;
        }
        return null;
    }

    public Job getRessourceJob(Block bl) {
        System.out.println(bl.getType().name());
        if(bl.getType().name().contains("LOG")) {
            return lumberjack;
        } else if(bl.getType().name().contains("ore")) {
            return null;
        }
        return null;
    }
}
