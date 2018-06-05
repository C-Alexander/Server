package dal.repositories;

import dal.contexts.GameContext;
import dal.entities.Game;

import javax.inject.*;
import java.util.List;

@Singleton
public class GameRepository implements GameContext{

    private GameContext context;

    @Inject
    public GameRepository(GameContext context) {

        this.context = context;
    }

    @Override
    public List<Game> getGames() {
        return context.getGames();
    }

    @Override
    public Game getGame(String gameId) { return context.getGame(gameId); }

    @Override
    public Game getOpenGame() { return context.getOpenGame(); }

    @Override
    public void saveGame(Game game) { context.saveGame(game); }

    @Override
    public void removeGame(Game game) { context.removeGame(game); }
}