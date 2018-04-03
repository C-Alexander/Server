package dal.entities;

import javax.persistence.Embeddable;
import java.text.MessageFormat;

/**
 *
 * @author Sam Dirkx
 */
@Embeddable
public class Stats {
    private int healthPoints;
    private int attack;
    private int defence;
    private int movement;
    private int speed;
    private int weaponClass;
    
    /**
     * Default constructor. All attributes are 0.
     */
    public Stats() {
        healthPoints = 0;
        attack = 0;
        defence = 0;
        movement = 0;
        speed = 0;
        weaponClass = 0;
    }
    
    /**
     * 
     * @param healthpoints
     * @param attack
     * @param defence
     * @param movement
     * @param speed 
     * @param weaponclass 
     */
    public Stats(int healthpoints, int attack, int defence, int movement, int speed, int weaponclass) {
        this.healthPoints = healthpoints;
        this.attack = attack;
        this.defence = defence;
        this.movement = movement;
        this.speed = speed;
        this.weaponClass = weaponclass;
    }
    
    /**
     * 
     * @return 
     */
    public int getHealthPoints() {
        return healthPoints;
    }
    
    /**
     * 
     * @return 
     */
    public int getAttack() {
        return attack;
    }
    
    /**
     * 
     * @return 
     */
    public int getDefence() {
        return defence;
    }
    
    /**
     * 
     * @return 
     */
    public int getMovement() {
        return movement;
    }
    
    /**
     * 
     * @return 
     */
    public int getSpeed() {
        return speed;
    }
    
    /**
     * Use class WeaponClass to decode this.
     * 
     * @return 
     */
    public int getWeaponClass() {
        return weaponClass;
    }
    
    /**
     * 
     * @param healthPoints 
     */
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }
    
    /**
     * 
     * @param attack 
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }
    
    /**
     * 
     * @param defence 
     */
    public void setDefence(int defence) {
        this.defence = defence;
    }
    
    /**
     * 
     * @param movement 
     */
    public void setMovement(int movement) {
        this.movement = movement;
    }
    
    /**
     * 
     * @param speed 
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    /**
     * 
     * @param weaponClass 
     */
    public void setWeaponClass(int weaponClass) {
        this.weaponClass = weaponClass;
    }
    
    /**
     * Adds the stats argument to this instance
     * 
     * @param stats 
     */
    public void addToThis(Stats stats) {
        if(stats == null)
            return;
        this.attack += stats.attack;
        this.defence += stats.defence;
        this.healthPoints += stats.healthPoints;
        this.movement += stats.movement;
        this.speed += stats.speed;
        this.weaponClass |= stats.weaponClass;
    }
    
    /**
     * Adds the stats argument to this instance values and creates a new Stats instance.
     * 
     * @param stats
     * @return 
     */
    public Stats addToNew(Stats stats) {
        Stats output = new Stats();
        output.addToThis(this);
        output.addToThis(stats);
        return output;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "Stats{" + "hp=" + healthPoints + ", att=" + attack + ", def=" + defence + ", mvd=" + movement + ", spd=" + speed + '}';
    }

    public String toUsefulString(){
        return MessageFormat.format("hp={0}  att={1}  def={2}  mvd={3}  spd={4}", healthPoints, attack, defence, movement, speed);
    }
}