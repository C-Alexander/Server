package controllers;

import actors.GameManagerActor;
import actors.LobbyActorCreator;
import actors.WebSocketActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.stream.Materializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Injector;
import msgs.Packet;
import play.Logger;
import play.libs.streams.ActorFlow;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import javax.inject.Inject;

public class GameController extends Controller {
    private final ActorSystem actorSystem;
    private final Materializer mat;
    private Injector injector;
    private ObjectMapper mapper;

    @Inject
    public GameController(ActorSystem actorSystem, Materializer mat, Injector injector) {
        super();
        this.actorSystem = actorSystem;
        this.mat = mat;
        this.injector = injector;
        actorSystem.actorOf(GameManagerActor.props(), "GameManager");
        LobbyActorCreator creator = injector.getInstance(LobbyActorCreator.class);
        ActorRef lobbyActor = actorSystem.actorOf(Props.create(creator), "LobbyManager");
        mapper = new ObjectMapper();
    }

    public Result index() {
        return ok("lol teun");
    }

    public Result newGame() {
        JsonNode body = request().body().asJson();
        Logger.info(body.toString());
        return created(body);
    }

    public Result allGames() {
            ArrayNode allGames = mapper.createArrayNode();
            ObjectNode json1 = mapper.createObjectNode();
            json1.put("name", "Game 1");
            json1.put("host", "EA Origin lol");
            ObjectNode json2 = mapper.createObjectNode();
            json2.put("name", "Game 2");
            json2.put("host", "An ancient abandoned server in Rick's backyard covered with actual specks of shit (still better than origin)");
            allGames.add(json1);
            allGames.add(json2);
            return ok(allGames.toString());
    }

    public WebSocket game() {
        Logger.debug("Trying to make a new connection...");
        return WebSocket.json(Packet.class).accept(request -> ActorFlow.actorRef(out -> WebSocketActor.props(out, injector), actorSystem, mat));
    }
}
