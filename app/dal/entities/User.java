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
    private List<Character> characterTeam;

    User() {
        characterTeam = new ArrayList<Character>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) { this.username = name; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public List<Character> getCharacterTeam() { return characterTeam; }

    public void setCharacterTeam(List<Character> characterTeam) { this.characterTeam = characterTeam; }

    /**
     * adds character to team if character isn't on team yet and maximum teamsize will not be exceeded
     * for now only allowed to have 1 hero, 1 general and 3 grunts.
     *
     * @param character
     */
    public void addCharacterToTeam(Character character) {
        if (characterTeam.size() < maxTeamSize && !characterTeam.contains(character)) {
            HashMap<RankName, Integer> rankCount = getCharacterCount(characterTeam);

            RankName rankName = character.getRank().getRankName();
            switch (rankName) {
                case HERO:
                    if (rankCount.get(rankName) < 1) {
                        characterTeam.add(character);
                    }
                    break;
                case GENERAL:
                    if (rankCount.get(rankName) < 1) {
                        characterTeam.add(character);
                    }
                    break;
                case GRUNT:
                    if (rankCount.get(rankName) < 3) {
                        characterTeam.add(character);
                    }
                default:
                    return;
            }
        }
    }

    /**
     * returns HashMap with amount of heroes, generals and minions in list of given characters
     * doing it once for performance's sake (not needing to go through the for loop multiple times to see the amount
     * of units)
     *
     * @param characters
     * @return
     */
    private HashMap<RankName, Integer> getCharacterCount(List<Character> characters) {
        HashMap<RankName, Integer> result = new HashMap<RankName, Integer>();
        result.put(RankName.HERO, 0);
        result.put(RankName.GENERAL, 0);
        result.put(RankName.GRUNT, 0);

        for (Character c : characters) {
            RankName rankName = c.getRank().getRankName();
            switch (rankName) {
                case HERO:
                    result.put(RankName.HERO, (Integer)result.get(rankName)++);
                    break;
                case GENERAL:
                    result.put(RankName.GENERAL, (Integer)result.get(rankName)++);
                    break;
                case GRUNT:
                    result.put(RankName.GRUNT, (Integer)result.get(rankName)++);
                    break;
                default:
                    break;
            }
        }

        return result;
    }

    /**
     * remove character from team
     *
     * @param character
     */
    public void removeCharacterFromTeam(Character character) {
        characterTeam.remove(character);
    }
}
