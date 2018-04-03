package dal.entities;

import javax.persistence.Embeddable;

/**
 *
 * @author Sam Dirkx
 */
@Embeddable
public class Weapon {
    private String name;
    private Stats stats;
    private Debuff statusEffect;
    private boolean canHeal;
    private int range; //attack range
    
    /**
     * 
     * @param name
     * @param range
     * @param stats
     * @param canHeal
     * @param statuseffect 
     */
    public Weapon(String name, int range, Stats stats, boolean canHeal, Debuff statuseffect) {
        this.name = name;
        this.range = range;
        this.stats = stats;
        this.canHeal = canHeal;
        this.statusEffect = statuseffect;
    }
    
    /**
     * 
     * @return 
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return 
     */
    public Stats getStats() {
        return stats;
    }
    
    /**
     * 
     * @return 
     */
    public Debuff getStatusEffect() {
        return statusEffect;
    }
    
    /**
     * 
     * @return 
     */
    public boolean getCanHeal() {
        return canHeal;
    }
    
    /**
     * 
     * @return 
     */
    public int getRange() {
        return range;
    }

    /**
     *
     * @return
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    /**
     *
     * @return
     */
    public void setStatusEffect(Debuff statusEffect) {
        this.statusEffect = statusEffect;
    }

    /**
     *
     * @return
     */
    public void setCanHeal(Boolean canHeal) {
        this.canHeal = canHeal;
    }

    /**
     *
     * @return
     */
    public void setRange(int range) {
        this.range = range;
    }


    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "Weapon{" + "name=" + name + ", stats=" + stats + ", canHeal=" + canHeal + ", range=" + range + '}';
    }
}