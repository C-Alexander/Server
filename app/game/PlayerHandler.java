package game;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;

public class PlayerHandler {
    ActorRef client;
    String guid;
    int gameId;

    public PlayerHandler(ActorRef client, String guid, int gameId) {
        this.client = client;
        this.guid = guid;
        this.gameId = gameId;
    }

    public void decodeMessage(JsonNode packet) {

        gameId = GameManager.getInstance().createOrJoinGame(client, guid);
    }
}
