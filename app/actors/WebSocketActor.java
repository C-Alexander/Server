package actors;

import akka.actor.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Player;
import msgs.*;
import play.Logger;

import java.io.IOException;

public class WebSocketActor extends AbstractActor {


    public static Props props(ActorRef out) {
        return Props.create(WebSocketActor.class, out);
    }

    private final ActorRef out;
    private ActorRef game;
    private int playerId;

    public WebSocketActor(ActorRef out) {
        this.out = out;
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
        Logger.debug("Handling packet of type:" + packet.type);
        switch(packet.type) {
            case JOIN_GAME:
                handleJoinGame(packet);
                break;
            case KEEPALIVE:
                 break;
            default: game.tell(new PlayerPacket(packet, playerId), out);
        }
    }

    private void handleJoinGame(Packet packet) {
        Logger.debug("New player trying to join");

        JoinGameMessage joinGameMessage = (JoinGameMessage)packet.data;
        Player player = new Player();
        player.setId(joinGameMessage.getUserId());
        player.setOut(out);
        player.setPlayerActor(getSelf());

        this.playerId = player.getId();

        PlayerJoinedMessage message = new PlayerJoinedMessage();
        message.setPlayer(player);
        message.setSessionId(joinGameMessage.getSessionId());
        message.setGame(joinGameMessage.getGameId());
        //get all actors ahead of this one in the hierarchy named GameManager
        ActorSelection selection = getContext().actorSelection("../../GameManager");
        selection.tell(message, getSelf());
//        verifyPlayer(message);

    }

    private void verifyPlayer(PlayerJoinedMessage playerToAdd) {
        ActorRef verificationActor = getContext().actorOf(VerificationActor.props());
        verificationActor.tell(playerToAdd, getSelf());
    }
}