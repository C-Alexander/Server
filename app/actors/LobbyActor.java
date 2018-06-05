package actors;

import akka.actor.AbstractActor;
import com.google.inject.Inject;
import dal.entities.Game;
import dal.repositories.GameRepository;
import msgs.EndGameMessage;

public class LobbyActor extends AbstractActor {
    @Inject
    private GameRepository gameRepository;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(EndGameMessage.class, this::endGame)
                .build();
    }

    private void endGame(EndGameMessage message) {
        Game game = gameRepository.getGame(message.getGameId());
        if (game != null) {
            gameRepository.removeGame(game);
        }
    }
}