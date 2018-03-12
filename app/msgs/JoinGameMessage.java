package msgs;

public class JoinGameMessage extends Message {
    String gameId;
    String playerId;

    public JoinGameMessage(String gameId, String playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public JoinGameMessage () {}

    public String getGameId() {
        return gameId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
