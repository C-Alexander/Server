package actors;

import akka.actor.*;
import akka.parboiled2.support.Join;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Player;
import msgs.*;
import play.Logger;

import java.io.IOException;
import java.util.HashMap;

public class GameActor extends AbstractActor {
    private HashMap<String, Player> players;

    public static Props props() {
        return Props.create(GameActor.class);
    }


    public GameActor() {
        players = new HashMap<>();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PlayerJoinedMessage.class, this::handleJoiningPlayer)
                .match(PlayerPacket.class, this::handlePlayerAction)
                .matchAny(message -> Logger.error("Unknown message: " + message))
                .build();
    }

    private void handlePlayerAction(PlayerPacket playerPacket) {
        switch (playerPacket.type) {
            default: Logger.info("Got playerpacket");
        }
    }

    private void handleJoiningPlayer(PlayerJoinedMessage message) {
        Player newPlayer = message.getPlayer();
        newPlayer.setGame(getSelf());
        players.putIfAbsent(newPlayer.getId(), newPlayer);

        PlayerAddedToGameMessage successMessage = new PlayerAddedToGameMessage();
        successMessage.setGame(getSelf());
        getSender().tell(successMessage, getSelf());

        for (Player player : players.values()) {
            Logger.info("Player found, id: " + player.getId());
            player.getOut().tell("Welcome, " + newPlayer.getId() + " to Game " + getSelf().path(), getSelf());
        }
    }

}