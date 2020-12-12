package dandelion.oniris.players;

public class Stats {
    float strength, vitality, magie, defence, speed;

    public Stats(float strength, float vitality, float magie, float defence, float speed) {
        this.strength = strength;
        this.vitality = vitality;
        this.magie = magie;
        this.defence = defence;
        this.speed = speed;
    }

    public float getMagie() {
        return magie;
    }

    public void setMagie(float magie) {
        this.magie = magie;
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public float getVitality() {
        return vitality;
    }

    public void setVitality(float vitality) {
        this.vitality = vitality;
    }

    public float getDefence() {
        return defence;
    }

    public void setDefence(float defence) {
        this.defence = defence;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

}
