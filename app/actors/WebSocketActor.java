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
    private String playerId;

    public WebSocketActor(ActorRef out) {
        this.out = out;
    }

    @Override
    public Receive createReceive() {
        Logger.info(getSelf().path().toString());

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
        JoinGameMessage joinGameMessage = (JoinGameMessage)packet.data;
        Player player = new Player();
        player.setId(joinGameMessage.getPlayerId());
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