package msgs;

import akka.actor.ActorRef;

public class PlayerAddedToGameMessage extends Message {
    public ActorRef getGame() {
        return game;
    }

    public void setGame(ActorRef game) {
        this.game = game;
    }

    ActorRef game;
}
