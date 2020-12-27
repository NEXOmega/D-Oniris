package dandelion.oniris.entities;

import dandelion.oniris.players.Stats;

public class EntityStats extends Stats {
    public float attackSpeed;
    public float attackRange;
    public float rangedSpeed;
    public float rangedDamage;

    public EntityStats(float strength, float vitality, float magie, float defence, float speed, float attackSpeed, float attackRange, float rangedSpeed, float rangedDamage) {
        super(strength, vitality, magie, defence, speed);
        this.attackSpeed = attackSpeed;
        this.attackRange = attackRange;
        this.rangedSpeed = rangedSpeed;
        this.rangedDamage = rangedDamage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public float getRangedSpeed() {
        return rangedSpeed;
    }

    public void setRangedSpeed(float rangedSpeed) {
        this.rangedSpeed = rangedSpeed;
    }

    public float getRangedDamage() {
        return rangedDamage;
    }

    public void setRangedDamage(float rangedDamage) {
        this.rangedDamage = rangedDamage;
    }

    public float getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(float attackRange) {
        this.attackRange = attackRange;
    }
}
