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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    @OneToOne
    private Character gameHero;


    @OneToMany
    private List<Character> characterTeam;

    public User() {
        characterTeam = new ArrayList<Character>();
    }

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

    public Character getGameHero() {
        return gameHero;
    }

    public void setGameHero(Character gameHero) {
        this.gameHero = gameHero;
    }

    private final int maxTeamSize = 10;

}