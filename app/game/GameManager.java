package game;

import akka.actor.ActorRef;

public class GameManager {
    private static GameManager ourInstance = new GameManager();

    public static GameManager getInstance() {
        return ourInstance;
    }

    private GameManager() {
    }

    public int createOrJoinGame(ActorRef client, String guid) {
        return 0;
    }
}
