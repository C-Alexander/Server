package actors;

import akka.actor.*;
import akka.parboiled2.support.Join;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Player;
import msgs.JoinGameMessage;
import msgs.MessageType;
import msgs.Packet;
import msgs.PlayerJoinedMessage;
import play.Logger;

import java.io.IOException;
import java.util.HashMap;

public class GameManagerActor extends AbstractActor {
    private HashMap<String, ActorRef> games;

    public static Props props() {
        return Props.create(GameManagerActor.class);
    }


    public GameManagerActor() {
        games = new HashMap<>();
    }

    @Override
    public Receive createReceive() {
        Logger.info(getSelf().path().toString());
        return receiveBuilder()
                .match(PlayerJoinedMessage.class, this::handleJoiningPlayer)
                .matchAny(message -> Logger.error("Unknown message: " + message))
                .build();
    }

    private void handleJoiningPlayer(PlayerJoinedMessage message) {
        Logger.debug("Handling joining player: " + message.getPlayer().getId());
            ActorRef game = games.getOrDefault(
                    message.getGame(),
                    getContext().actorOf(GameActor.props())
            );
            game.forward(message, getContext());
            games.putIfAbsent(message.getGame(), game);
    }

}