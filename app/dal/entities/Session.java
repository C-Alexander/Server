package dal.entities;

import javax.persistence.*;

@Entity
@Table(name = "Session")
public class Session {
    @OneToOne(cascade=CascadeType.ALL)
    @JoinTable(name = "Game_Sessions",
            joinColumns = {@JoinColumn(name = "sessionId", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "gameId", referencedColumnName = "id")})
    private Game game;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String sessionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
