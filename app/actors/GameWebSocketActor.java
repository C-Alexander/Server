package actors;

import akka.actor.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.PlayerHandler;
import play.Logger;
import play.libs.Json;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.io.IOException;

public class GameWebSocketActor extends AbstractActor {

    private final PlayerHandler playerHandler;
    private ActorSelection actorSelection;
    private ObjectMapper mapper;

    public static Props props(ActorRef out) {
        return Props.create(GameWebSocketActor.class, out);
    }

    private final ActorRef out;

    public GameWebSocketActor(ActorRef out) {
        this.out = out;
        mapper = new ObjectMapper();
        playerHandler = new PlayerHandler(out, "lol", 1);

        //     getContext().actorSelection("*").tell("hi", self());

    }

    @Override
    public Receive createReceive() {
        System.out.println("hi");
        return receiveBuilder()
                .match(String.class, this::handleMessage)
                .build();
    }

    private void handleMessage(String message) {

        try {
            JsonNode packet = mapper.readTree(message);
            out.tell("Hello " + packet.findValue("teun"), self());
            playerHandler.decodeMessage(packet);

        } catch (IOException e) {
            Logger.error(e.getMessage());
            out.tell("Error " + e.getMessage(), self());

        }
    }
}