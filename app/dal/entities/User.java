package dal.entities;

import javax.persistence.*;

@Entity
@Table(name = "Player")
@NamedQueries({
        @NamedQuery(name = "User.getAll", query = "select u from User as u"),
        @NamedQuery(name = "User.findOne", query = "select u from User as u WHERE u.id = :id")
})
public class User {
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

    public void setId(int id) {
        this.id = id;
    }
}
