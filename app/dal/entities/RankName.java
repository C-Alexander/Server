package dal.entities;

/**
 *
 * @author Rick Pijnenburg - REXOTIUM
 * @email m.a.a.pijnenburg@gmail.com
 */
public enum RankName {
    GRUNT, GENERAL, HERO;
    
    /**
     * Gets the next rank up from this rank.
     * 
     * @return 
     */
    RankName getNext() {
        switch(this) {
            case GRUNT:
                return GENERAL;
            case GENERAL:
                return HERO;
            case HERO:
            default:
                throw new AssertionError(this.name());
        }
    }
}