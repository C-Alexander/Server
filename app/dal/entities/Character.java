package dal.entities;

import ch.qos.logback.classic.db.names.TableName;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import works.maatwerk.generals.ClassEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.sun.tools.doclint.Entity.and;

/**
 *
 * @author Sam Dirkx
 */
@SuppressWarnings("WeakerAccess")
@Entity
@NamedQueries({
        @NamedQuery(name = "User.getAll", query = "select c from Character c")
        @NamedQuery(name = "User.findOne", query = "select c from Character c WHERE c.id = :id")
})
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Modifier which specifies the percentage of damage that can be done by a healing weapon (25 means 25% of the attack)
     */
    @Embedded
    private Stats baseStats;
    @Embedded
    private Race race;
    @Embedded
    private Rank rank;
    @Embedded
    private Weapon weapon;

    /**
     * Creates an instance of the Character class
     * @param race
     */
    public Character(Race race) {
        this.baseStats = new Stats(1, 1, 1, 1, 1, 0);
        this.race = race;
        this.rank = new Rank();
    }

    public Character(){ }

    /**
     *
     * @return 
     */
    public Stats getBaseStats() {
        return baseStats;
    }
    
    /**
     * 
     * @return 
     */
    public Race getRace() {
        return race;
    }
    
    /**
     * 
     * @return 
     */
    public Rank getRank() {
        return rank;
    }
    
    /**
     * 
     * @return 
     */
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * @param weapon
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * Gets the stats of the character for this turn.
     * 
     * @return 
     */
    public Stats getGameStats() {
        Stats output = new Stats();
        output.addToThis(baseStats);
        output.addToThis(race.getStats());
        if(weapon != null) {
            output.addToThis(weapon.getStats());
        }

        return output;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}