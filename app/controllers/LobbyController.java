package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dal.entities.Game;
import dal.entities.Session;
import dal.repositories.GameRepository;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LobbyController extends Controller {

    private final GameRepository gameRepository;
    private ObjectMapper mapper;

    @Inject
    public LobbyController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        mapper = new ObjectMapper();
    }

    @Transactional
    public Result addToQueue() {
        JsonNode body = request().body().asJson();

        String uuid = body.get("sessionid").asText();

        Game game = gameRepository.getOpenGame();
        if (game == null) { game = new Game(); }
        gameRepository.saveGame(game);

        Session player = new Session();
        player.setSessionId(uuid);
        gameRepository.saveSession(player);
        game.addSession(player);

        ObjectNode json = mapper.createObjectNode();

        json.put("sessionid", player.getSessionId());
        json.put("gameid", game.getId());
        json.put("size", game.getSessionList().size());

        return ok(json.toString());

    }
}