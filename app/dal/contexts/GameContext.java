package dal.contexts;

import dal.entities.Game;
import dal.entities.Session;
import dal.entities.User;

import java.util.List;

public interface GameContext {
    List<Game> getGames();

    Game getGame(int gameId);

    Game getOpenGame();

    void saveGame(Game game);

    void saveSession(Session session);
}
