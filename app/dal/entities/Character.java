package works.maatwerk.generals.models;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import works.maatwerk.generals.ClassEnum;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Sam Dirkx
 */
@Entity
@Table(name = "Character")
@NamedQueries({
        @NamedQuery(name = "Character.getAll", query = "select c from Character as c"),
        @NamedQuery(name = "Character.findOne", query = "select c from Character as c WHERE c.id = :id"),
        @NamedQuery(name = "Character.ifExists", query = "select (COUNT(*) > 0) as exists  from Character as c WHERE c.id = :id"),
        @NamedQuery(name = "Character.getMinions", query = "select m from Minion as m JOIN Character as c WHERE c.id = m.id")
})
@SuppressWarnings("WeakerAccess")
public class Character extends Actor {
    /**
     * Modifier which specifies the percentage of damage that can be done by a healing weapon (25 means 25% of the attack)
     */
    private static final int HEALER_DAMAGE_MODIFIER = 25;
    private static final int MAX_MINIONS = 3;
    private Stats baseStats;
    private Race race;
    private Rank rank;
    private List<Debuff> debuffs;
    private Weapon weapon;
    @Entity
    @Table(name = "minion")
    private List<Character> minions;
    private Vector2 location;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private AssetManager assetManager;
    private ClassEnum classEnum;
    private String name;

    /**
     * Creates an instance of the Character class
     * @param race
     * @param assetManager
     * @param classEnum
     * @param location
     */
    public Character(Race race,AssetManager assetManager,ClassEnum classEnum,Vector2 location) {
        this.baseStats = new Stats(1, 1, 1, 1, 1, 0);
        this.race = race;
        this.rank = new Rank();
        this.assetManager = assetManager;
        this.classEnum = classEnum;
        this.location =location;
        debuffs = new ArrayList<Debuff>();
        minions = new ArrayList<Character>();
    }


    public Character(){ }

    /**
     *
     * @return a Vector2
     */
    public Vector2 getLocation() {
        return location;
    }

    /**
     * @param location
     */
    public void setLocation(Vector2 location) {
        this.location = location;
    }

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
     *
     * @return
     */
    public Stats getDebuffs() {
        Stats output = new Stats();
        for(Debuff d : debuffs) {
            output.addToThis(d.getStaticDebuff());
        }
        return output;
    }
    
    /**
     *
     * @return
     */
    public List<Character> getMinions() {
        return minions;
    }

    /**
     * 
     * @param debuff 
     */
    public void addDebuffs(Debuff debuff) {
        this.debuffs.add(debuff);
    }
    
    /**
     * 
     * @param minion
     * @return 
     */
    public boolean addMinion(Character minion) {
        if(rank.getRankName() != RankName.HERO || minion.rank.getRankName() != RankName.GENERAL)
            throw new IllegalArgumentException();
        if(minions.size() >= MAX_MINIONS)
            return false;
        if(minions.contains(minion))
            return false;
        minions.add(minion);
        return true;
    }
    
    /**
     * 
     * @param minion
     * @return 
     */
    public boolean removeMinion(Character minion) {
       return minions.remove(minion);
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

        output.addToThis(getDebuffs());
        return output;
    }
    
    /**
     * To check if this character is still alive
     * 
     * @return 
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isAlive() {
        return getGameStats().getHealthPoints() > 0;
    }
    
    /**
     * Manipulates gameStats of this character and enemy character according to 
     * battle calculations
     * 
     * @param enemy
     */
    public void attack(Character enemy) {
        Stats enemyStats = enemy.getGameStats();
        Stats ownStats = this.getGameStats();
        int damageToEnemy = calculateDamage(((weapon != null) && weapon.isCanHeal()), enemyStats, ownStats);
        int damageToSelf = calculateDamage(((enemy.weapon != null) && enemy.weapon.isCanHeal()), ownStats, enemyStats);
        this.addDamageToCharacter(enemy, damageToEnemy);
        this.addDamageToCharacter(this, damageToSelf);
    }
    
    /**
     * Manipulates gameStats of this character according to healing calculations
     *
     * @param ally
     */
    public void heal(Character ally) {
        if(weapon == null)
            return;
        if(!weapon.isCanHeal())
            return;
        Stats added = new Stats();
        added.setHealthPoints(this.getGameStats().getAttack());
        ally.addDebuffs(new Debuff(-1, added, null));
    }
    
    /**
     * manipulates gameStats temporarily according to tile effect
     *
     * @param bonusTile
     */
    @SuppressWarnings("EmptyMethod")
    public void bonus(Tile bonusTile) {
        //implement
    }
    
    /**
     * updates the class's properties
     * 
     */
    public void update() {
        Iterator<Debuff> iterator = debuffs.iterator();
        while(iterator.hasNext()) {
            Debuff debuff = iterator.next();
            if(debuff.getTurns() == 0) {
                iterator.remove();
                continue;
            }
            debuff.update();
        }
    }
    
    /**
     * For generals checks if the amount of grunts is correct otherwise spawns them.
     */
    public void matchStart() {
        if(rank.getRankName() != RankName.GENERAL)
            return;
        while (minions.size() < MAX_MINIONS) {
            minions.add(new Character(race,this.assetManager,this.classEnum,this.location));
        }
    }
    
    /**
     * Called when match has ended to clear debuff list and set experience.
     * Also returns list of minions which reached the same rank as their master character.
     * This function calls the same function in all its minions.
     * 
     * @return 
     */
    public List<Character> matchEnded() {
        if(!this.isAlive())
            return new ArrayList<Character>();
        rank.update();
        debuffs.clear();
        return matchEndedMinions();
    }
    
    /**
     * Calculates the damage that will be done by the character.
     * 
     * @param weaponCanHeal
     * @param defence
     * @param attack
     * @return 
     */
    private int calculateDamage(boolean weaponCanHeal, Stats defence, Stats attack) {
        return ((defence.getDefence() - (weaponCanHeal ? ((attack.getAttack() * HEALER_DAMAGE_MODIFIER) / 100) : attack.getAttack())) * WeaponClass.getWeaponModifier(attack.getWeaponClass(), defence.getWeaponClass())) / 100;
    }
    
    /**
     * Adds the damage the the debufflist for infinity (and beyond!)
     * 
     * @param character
     * @param damage 
     */
    private void addDamageToCharacter(Character character, int damage) {
        if(damage < 0) {
            Stats dmg = new Stats();
            dmg.setHealthPoints(damage);
            character.addDebuffs(new Debuff(-1, dmg, null));
        }
    }
    
    /**
     * Calls matchEnded in all minions and checks of one of the minions gained the same rank as this character.
     * If same rank is achieved removes minion from the minion list and output it in the return list.
     * 
     * @return 
     */
    private List<Character> matchEndedMinions() {
        List<Character> output = new ArrayList<Character>();
        Iterator<Character> iterator = minions.iterator();
        while(iterator.hasNext()) {
            Character c = iterator.next();
            output.addAll(c.matchEnded());
            if(c.rank.getRankName() != rank.getRankName())
                continue;
            iterator.remove();
            c.minions.clear();
            output.add(c);
        }
        return output;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(this.getTexture(),this.getLocation().x*32,this.getLocation().y*32);



    }

    public Texture getTexture() {
        switch (this.rank.getRankName()) {
            case GRUNT:
                switch (this.classEnum) {
                    case AXE:
                        return assetManager.get("characters/mAxe.png");
                    case SWORD:
                        return assetManager.get("characters/mSword.png");
                    case SPEAR:
                        return assetManager.get("characters/mSpear.png");
                    case ARCANE:
                        return assetManager.get("characters/mArcane.png");
                    case CORRUPT:
                        return assetManager.get("characters/mCorrupt.png");
                    case DIVINE:
                        return assetManager.get("characters/mDivine.png");
                    case HEALER:
                        return assetManager.get("characters/mHealer.png");
                    case ARCHER:
                        return assetManager.get("characters/mBow.png");
                    default:
                }
                break;
            case GENERAL:
                switch (this.classEnum) {
                    case AXE:
                        return assetManager.get("characters/gAxe.png");
                    case SWORD:
                        return assetManager.get("characters/gSword.png");
                    case SPEAR:
                        return assetManager.get("characters/gSpear.png");
                    case ARCANE:
                        return assetManager.get("characters/gArcane.png");
                    case CORRUPT:
                        return assetManager.get("characters/gCorrupt.png");
                    case DIVINE:
                        return assetManager.get("characters/gDivine.png");
                    case HEALER:
                        return assetManager.get("characters/gHealer.png");
                    case ARCHER:
                        return assetManager.get("characters/gBow.png");
                    default:
                }
                break;
            case HERO:
                switch (this.classEnum) {
                    case VALKYRIE:
                        return assetManager.get("characters/hValkyrie.png");
                    default:
                }
                break;
            default:
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName(){
        return this.name != null ? this.name : "Generic Unit";
    }

    @Override
    public void setName(String name){ this.name = name; }
}