package dal.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Player")
@NamedQueries({
        @NamedQuery(name = "User.getAll", query = "select u from User as u"),
        @NamedQuery(name = "User.findOne", query = "select u from User as u WHERE u.id = :id"),
        @NamedQuery(name = "User.ifExists", query = "select (CASE WHEN COUNT(*) > 0 THEN true ELSE false END) as exists from User as u WHERE u.username = :username"),
        @NamedQuery(name = "User.login", query = "select (CASE WHEN COUNT(*) > 0 THEN true ELSE false END) as exists from User as u WHERE u.username = :username and u.password = :password"),
        @NamedQuery(name = "User.getAndAuthenticate", query = "select u from User as u WHERE u.username = :username and u.password = :password")

})
public class User {

    public User() {

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;




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

    public void setId(int id) { this.id = id; }

   // public List<Character> getCharacterTeam() { return characterTeam; }

    //public void setCharacterTeam(List<Character> characterTeam) { this.characterTeam = characterTeam; }

    /**
     * adds character to team if character isn't on team yet and maximum teamsize will not be exceeded
     * for now only allowed to have 1 hero, 1 general and 3 grunts.
     *
     * @param character
     */


    /**
     * returns HashMap with amount of heroes, generals and minions in list of given characters
     * doing it once for performance's sake (not needing to go through the for loop multiple times to see the amount
     * of units)
     *
     * @param characters
     * @return
     */


    /**
     * remove character from team
     *
     * @param character
     */
    //public void removeCharacterFromTeam(Character character) {
      //  characterTeam.remove(character);
    //}
}
