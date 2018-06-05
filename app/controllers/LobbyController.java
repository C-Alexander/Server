package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dal.entities.Game;
import dal.entities.Session;
import dal.repositories.GameRepository;
import dal.repositories.SessionRepository;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LobbyController extends Controller {

    private final GameRepository gameRepository;
    private final SessionRepository sessionRepository;
    private ObjectMapper mapper;

    @Inject
    public LobbyController(GameRepository gameRepository, SessionRepository sessionRepository) {
        this.gameRepository = gameRepository;
        this.sessionRepository = sessionRepository;
        mapper = new ObjectMapper();
    }

    @Transactional
    public Result addToQueue() {
        JsonNode body = request().body().asJson();

        String uuid = body.get("sessionId").asText();

        ObjectNode json = mapper.createObjectNode();

        // Voorkom dubbele sessions
        Session gameSession = sessionRepository.findOne(uuid);
        if (gameSession != null) {
            Game game = gameRepository.getOpenGame();
            gameRepository.saveGame(game);
            game.addSession(gameSession);

            json.put("sessionId", gameSession.getId());
            json.put("gameId", game.getId());
            json.put("size", game.getSessions().size());
        }

        return ok(json.toString());

    }
}