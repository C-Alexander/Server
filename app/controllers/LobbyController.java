package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dal.entities.Queue;
import dal.repositories.LobbyRepository;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;


@Singleton
public class LobbyController extends Controller {

    private final LobbyRepository lobbyRepository;
    private ObjectMapper mapper;
    private List<Queue> queueList;

    @Inject
    public LobbyController(LobbyRepository lobbyRepository) {
        this.lobbyRepository = lobbyRepository;
        mapper = new ObjectMapper();
        queueList = new ArrayList<>();
    }

    @Transactional
    public Result addToQueue() {
        JsonNode body = request().body().asJson();

        Queue newPlayer = new Queue();
        newPlayer.setId(body.get("id").asInt());
        lobbyRepository.addPlayer(newPlayer);

        queueList = lobbyRepository.getQueue();

        if (queueList.size() >= 2) {

            Queue player1 = queueList.get(0);
            queueList.remove(0);
            lobbyRepository.removePlayer(player1);

            Queue player2 = queueList.get(0);
            queueList.remove(0);
            lobbyRepository.removePlayer(player2);

            //add first 2 users to a game

        }

        return ok(body.get("id").asText());

    }
}