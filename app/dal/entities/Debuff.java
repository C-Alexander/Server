package dal.entities;

/**
 *
 * @author Sam Dirkx
 */
public class Debuff {
    private int turns;
    private final Stats staticDebuff;     //wat hetzelfde blijft
    private final Stats dynamicDebuff;    //wat optelt
    
    /**
     * 
     * @param turns turns that this debuff will be active. Negative numbers will mean for the rest of the game.
     * @param staticDebuff stats that count this turn
     * @param dynamicDebuff stats that will be added at the end of the turn
     */
    public Debuff(int turns, Stats staticDebuff, Stats dynamicDebuff) {
        this.turns = turns;
        this.staticDebuff = staticDebuff;
        this.dynamicDebuff = dynamicDebuff;
    }
    
    /**
     * 
     * @return 
     */
    public int getTurns() {
        return turns;
    }
    
    /**
     * 
     * @return 
     */
    public Stats getStaticDebuff() {
        return staticDebuff;
    }
    
    /**
     * 
     * @return 
     */
    public Stats getDynamicDebuff() {
        return dynamicDebuff;
    }
    
    public void update() {
        if(turns == 0) {
            return;
        }
        if(turns > 0) {
            turns--;
        }
        staticDebuff.addToThis(dynamicDebuff);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "Debuff{" + "turns=" + turns + ", staticDebuff=" + staticDebuff + ", dynamicDebuff=" + dynamicDebuff + '}';
    }
}