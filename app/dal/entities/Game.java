package dal.entities;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Game.getAll", query = "select g from Game as g"),
        @NamedQuery(name = "Game.findOne", query = "select g from Game as g where g.id = :id"),
        @NamedQuery(name = "Game.findFirstOpen", query = "select g from Game as g where (SELECT COUNT(*) FROM g.sessions as gSession) < 2")
})
public class Game {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;
    @ManyToMany(cascade=CascadeType.PERSIST)
    @JoinTable(
            joinColumns = @JoinColumn(name = "gameId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sessionId", referencedColumnName = "id"))
    private List<Session> sessions;

    public Game() {
        sessions = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Session> getSessions() {
            return this.sessions;
    }

    public void addSession(Session session) {
            this.sessions.add(session);
    }

    public void removeSession(Session session) {
            this.sessions.remove(session);
    }
}
