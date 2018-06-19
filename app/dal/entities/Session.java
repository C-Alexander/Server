package dal.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Session.findOne", query = "select s from Session as s WHERE s.id = :id"),
        @NamedQuery(name = "Session.verify", query = "select (CASE WHEN COUNT(*) > 0 THEN true ELSE false END) as exists from Session as s WHERE s.id = :id AND s.player.id = :userId "),
        @NamedQuery(name = "Session.getAndVerifyWithuserId", query = "select s from Session as s WHERE s.id = :id AND s.player.id = :userId "),
        @NamedQuery(name = "Session.getAndVerifyWithusername", query = "select s from Session as s WHERE s.id = :id AND s.player.username = :username ")


})
public class Session {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @ManyToOne(targetEntity = User.class)
    private User player;

    public Session(User user) {
        player = user;
    }

    public Session() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }
}
