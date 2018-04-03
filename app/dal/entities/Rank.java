package dal.entities;

import javax.persistence.Embeddable;

/**
 *
 * @author Rick Pijnenburg - REXOTIUM
 * @email m.a.a.pijnenburg@gmail.com
 */
@SuppressWarnings("WeakerAccess")
@Embeddable
public class Rank {
    /**
     * Level cap for the ranks
     */
    private static final int LEVEL_CAP_GRUNT = 10;
    private static final int LEVEL_CAP_GENERAL = 15;
    /**
     * Modifier which specifies the percentage of damage that is converted to experience (50 means 50% of the damage is converted to experience)
     */
    private static final int DAMAGE_DEALT_MODIFIER = 75;
    private static final int DAMAGE_TAKEN_MODIFIER = 50;
    private RankName rankName;
    private int matchExperience;
    private int experience;
    private int level;
    private int levelCap;
    private int experienceCap;
    
    /**
     * Constructor
     */
    public Rank() {
        rankName = RankName.GRUNT;
        matchExperience = 0;
        experience = 0;
        level = 0;
    }
    
    /**
     * 
     * @return 
     */
    public RankName getRankName() {
        return rankName;
    }

    /**
     * setter of rankName
     * @param rankName
     */
    public void setRankName(RankName rankName) {
        this.rankName = rankName;
    }
    
    /**
     * 
     * @return 
     */
    public int getMatchExperience() {
        return matchExperience;
    }
    
    /**
     * 
     * @return 
     */
    public int getExperience() {
        return experience;
    }
    
    /**
     * 
     * @return 
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * 
     * @return 
     */
    public int getExperienceCap() {
        return (int) (100 * Math.pow(1.2, level));
    }
    
    /**
     * Returns level cap or -1 if there is no level cap.
     * 
     * @return 
     */
    public int getLevelCap() {
       switch(rankName) {
            case GRUNT:
                return LEVEL_CAP_GRUNT;
            case GENERAL:
                return LEVEL_CAP_GENERAL;
            case HERO:
                return -1;
            default:
                throw new AssertionError(rankName.name());
       }
    }

    public void setMatchExperience(int matchExperience) {
        this.matchExperience = matchExperience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setExperienceCap(int experienceCap) {
        this.experienceCap = experienceCap;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setLevelCap(int levelCap) {
        this.levelCap = levelCap;
    }

    /**
     * Adds experience to this instance depending on the amount of damage dealt.
     * 
     * @param damage 
     */
    public void addExperienceThroughDamageDealt(int damage) {
       addExperience((damage * DAMAGE_DEALT_MODIFIER) / 100);
    }
    
    /**
     * Adds experience to this instance depending on the amount of damage taken.
     * 
     * @param damage 
     */
    public void addExperienceThroughDamageTaken(int damage) {
       addExperience((damage * DAMAGE_TAKEN_MODIFIER) / 100); 
    }
    
    /**
     * Updates rank. This makes experience changes permanent
     */
    public void update() {
        experience += matchExperience;
        checkLevelUp();
    }
    
    /**
     * Adds xp to the match experience.
     * 
     * Note: wont be added to the real experience until update is called.
     * 
     * @param xp
     */
    private void addExperience(int xp) {
        if(xp <= 0)
            throw new IllegalArgumentException();
        matchExperience += xp;
    }
    
    /**
     * Checks if the experience allows leveling up.
     */
    private void checkLevelUp() {
        int xpcap = getExperienceCap();
        if(experience < xpcap)
            return;
        int remainder = experience - xpcap;
        level++;
        checkRankUp();
        experience = remainder;
        checkLevelUp();
    }
    
    /**
     * Checks if the level allows ranking up.
     */
    private void checkRankUp() {
        int lvlcap = getLevelCap();
        if(level > lvlcap && lvlcap != -1) {
            rankName = rankName.getNext();
            level = 0;
        }
    }
}