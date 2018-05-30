package dal.entities;

import javax.persistence.*;

/**
 *
 * @author Sam Dirkx
 */
@SuppressWarnings("WeakerAccess")
@Entity
@Table(name = "Character")
@NamedQueries({
        @NamedQuery(name = "Character.getAll", query = "select c from Character as c"),
        @NamedQuery(name = "Character.findOne", query = "select c from Character as c WHERE c.id = :id")
})
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int health;
    private int attack;
    private int defence;
    private int movement;
    private int speed;

    @OneToOne
    private Weapon weapon;

    private String race;
    private String rankName;
    private int level;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getMovement() {
        return movement;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}