package dal.entities;

import javax.persistence.Embeddable;

/**
 *
 * @author Sam Dirkx
 */
@Embeddable
public class Race {
    private String name;
    private Stats stats;
    
    /**
     * 
     * @param name
     * @param stats 
     */
    public Race(String name, Stats stats) {
        this.name = name;
        this.stats = stats;
    }

    public Race() {}
    
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
     * setter of name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setter of stats
     * @param stats
     */
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "Race{" + "name=" + name + ", stats=" + stats + '}';
    }
}