package dal.entities;

import javax.persistence.*;

@Entity
@Table(name = "Player")
@NamedQueries({
        @NamedQuery(name = "User.getAll", query = "select u from User as u"),
        @NamedQuery(name = "User.findOne", query = "select u from User as u WHERE u.id = :id"),
        @NamedQuery(name = "User.ifExists", query = "select (COUNT(*) > 0) as exists  from User as u WHERE u.username = :username"),
        @NamedQuery(name = "User.login", query = "select (COUNT(*) > 0) as exists  from User as u WHERE u.username = :username and u.password = :password")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

    //private List<Character> characters;
    //private List<Character> characterTeam; //consists of 1 hero with maximum 3 generals
    private final int maxTeamSize = 4;

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return
     */
//    public List<Character> getCharacters() {
//        List<Character> characters = new ArrayList<Character>();
//        return characters;
//    }

    /**
     * returns all placeable characters of the characterteam (so including the minions of a general)
     *
     * @return
     */
//    public List<Character> getPlaceableCharacters() {
//        List<Character> result = new ArrayList<Character>();
//
//        for (Character c : characterTeam) {
//            result.add(c);
//            if (c.getRank().equals(RankName.GENERAL)) {
//                for (Character minion : c.getMinions()) {
//                    result.add(minion);
//                }
//            }
//        }
//
//        return result;
//    }

    /**
     * adds character to team if character isn't on team yet and maximum teamsize will not be exceeded
     * only allowed to have 1 hero, rest must be generals.
     * adding grunts not allowed, they are stored within a general
     *
     * @param character
     */
//    public void addCharacterToTeam(Character character) {
//        if (characterTeam.size() < maxTeamSize && !characterTeam.contains(character)) {
//            HashMap<RankName, Integer> rankCount = getCharacterCount(characterTeam);
//            RankName rankName = character.getRank().getRankName();
//
//            switch (rankName) {
//                case HERO:
//                    if (rankCount.get(rankName.HERO) < 1) {
//                        characterTeam.add(character);
//                    }
//                    break;
//                case GENERAL:
//                    characterTeam.add(character);
//                    break;
//                default:
//                    return;
//            }
//        }
//    }

    /**
     * returns HashMap with amount of heroes, generals and minions in list of given characters
     * doing it once for performance's sake (not needing to go through the for loop multiple times to see the amount
     * of units)
     *
     * @param characters
     * @return
     */
//    private HashMap<RankName, Integer> getCharacterCount(List<Character> characters) {
//        HashMap<RankName, Integer> result = new HashMap<RankName, Integer>();
//        int heroCount = 0;
//        int generalCount = 0;
//        int gruntCount = 0;
//
//        for (Character c : characters) {
//            RankName rankName = c.getRank().getRankName();
//            switch (rankName) {
//                case HERO:
//                    heroCount++;
//                    result.put(RankName.HERO, heroCount);
//                    break;
//                case GENERAL:
//                    generalCount++;
//                    result.put(RankName.GENERAL, heroCount);
//                    break;
//                case GRUNT:
//                    gruntCount++;
//                    result.put(RankName.GRUNT, heroCount);
//                    break;
//                default:
//                    break;
//            }
//        }
//
//        return result;
//    }

    /**
     * remove character from team
     *
     * @param character
     */
//    public void removeCharacterFromTeam(Character character) {
//        characterTeam.remove(character);
//    }
}
