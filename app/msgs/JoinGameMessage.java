package msgs;

public class JoinGameMessage extends Message {
    private String gameId;
    private String sessionId;
    private int userId;

    public JoinGameMessage(String gameId, String sessionId, int userId) {
        this.gameId = gameId;
        this.userId = userId;
        this.sessionId = sessionId;
    }

    public JoinGameMessage () {}

    public String getGameId() {
        return gameId;
    }

    public int getUserId() {
        return userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
