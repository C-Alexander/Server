package models;

import akka.actor.ActorRef;

public class Player {
    private ActorRef out;
    private ActorRef playerActor;
    private ActorRef game;
    private String id;

    public ActorRef getGame() {
        return game;
    }

    public void setGame(ActorRef game) {
        this.game = game;
    }

    public ActorRef getPlayerActor() {
        return playerActor;
    }

    public void setPlayerActor(ActorRef playerActor) {
        this.playerActor = playerActor;
    }

    public ActorRef getOut() {
        return out;
    }

    public void setOut(ActorRef out) {
        this.out = out;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
