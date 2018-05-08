package actors;

import akka.actor.*;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.google.inject.Injector;
import models.Player;
import msgs.*;
import play.Logger;

public class WebSocketActor extends AbstractActor {


    private Injector injector;

    private final ActorRef out;
    private ActorRef game;
    private int playerId;

    public WebSocketActor(ActorRef out, Injector injector) {
        this.out = out;
        this.injector = injector;
    }

    public static Props props(ActorRef out, Injector injector) {
        return Props.create(WebSocketActor.class, out, injector);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Packet.class, this::handlePacket)
                .match(PlayerAddedToGameMessage.class, this::handleGameJoined)
                .matchAny(message -> Logger.error("Unknown message: " + message))
                .build();
    }

    private void handleGameJoined(PlayerAddedToGameMessage playerAddedToGameMessage) {
        this.game = playerAddedToGameMessage.getGame();

        this.out.tell("You have joined game " + getSender().path() + ".", getSelf());
    }

    private void handlePacket(Packet packet) {
        Logger.info("Handling packet " + packet.messageType);
        switch(packet.messageType) {
            case JOIN_GAME:
                handleJoinGame(packet);
                break;
            case KEEPALIVE:
                 break;
            default: game.tell(new PlayerPacket(packet, playerId), out);
        }
    }

    private void handleJoinGame(Packet packet) {
        JoinGameMessage joinGameMessage = (JoinGameMessage)packet.data;
        Player player = new Player();
        player.setId(joinGameMessage.getUserId());
        player.setOut(out);
        player.setPlayerActor(getSelf());
        this.playerId = player.getId();
        //get all actors ahead of this one in the hierarchy named GameManager
        ActorSelection selection = getContext().actorSelection("../../GameManager");
        PlayerJoinedMessage message = new PlayerJoinedMessage();
        message.setPlayer(player);
        message.setGame(joinGameMessage.getGameId());

        selection.tell(message, getSelf());
    }
}