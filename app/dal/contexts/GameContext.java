package dal.contexts;

import dal.entities.Game;

import java.util.List;

public interface GameContext {
    List<Game> getGames();

    Game getGame(String gameId);

    Game getOpenGame();

    void saveGame(Game game);

    void removeGame(Game game);
}
