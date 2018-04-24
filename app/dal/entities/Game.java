package dal.entities;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Game")
@NamedQueries({
        @NamedQuery(name = "Game.getAll", query = "select g from Game as g"),
        @NamedQuery(name = "Game.findOne", query = "select g from Game as g where id = :id"),
        @NamedQuery(name = "Game.findFirstOpen", query = "select g from Game as g where (SELECT COUNT(*) FROM g.sessions as gu) < 2")
})
public class Game {
    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "Game_Sessions",
            joinColumns = @JoinColumn(name = "gameId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sessionId", referencedColumnName = "id"))
    private List<Session> sessions;

    public Game() {
        sessions = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Session> getSessionList() { return this.sessions; }

    public void addSession(Session session) { this.sessions.add(session); }

    public void removeSession(Session session) { this.sessions.remove(session); }
}

