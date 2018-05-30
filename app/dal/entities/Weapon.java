package dal.entities;

import javax.persistence.*;

@Entity
@Table(name = "Weapon")
@NamedQueries({
        @NamedQuery(name = "Weapon.getAll", query = "select w from Weapon as w"),
        @NamedQuery(name = "Weapon.findOne", query = "select w from Weapon as w WHERE w.id = :id")
})
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int damage;
    private int range;
    private int healthBoost;
    private int attackBoost;
    private int defenceBoost;
    private int speedBoost;
    private int movementBoost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getHealthBoost() {
        return healthBoost;
    }

    public void setHealthBoost(int healthBoost) {
        this.healthBoost = healthBoost;
    }

    public int getAttackBoost() {
        return attackBoost;
    }

    public void setAttackBoost(int attackBoost) {
        this.attackBoost = attackBoost;
    }

    public int getDefenceBoost() {
        return defenceBoost;
    }

    public void setDefenceBoost(int defenceBoost) {
        this.defenceBoost = defenceBoost;
    }

    public int getSpeedBoost() {
        return speedBoost;
    }

    public void setSpeedBoost(int speedBoost) {
        this.speedBoost = speedBoost;
    }

    public int getMovementBoost() {
        return movementBoost;
    }

    public void setMovementBoost(int movementBoost) {
        this.movementBoost = movementBoost;
    }
}
