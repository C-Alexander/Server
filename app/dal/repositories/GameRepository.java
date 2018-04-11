package dal.repositories;

import dal.contexts.GameContext;
import dal.entities.Game;
import dal.entities.Session;

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
    public Game getGame(int gameId) { return context.getGame(gameId); }

    @Override
    public Game getOpenGame() { return context.getOpenGame(); }

    @Override
    public void saveGame(Game game) { context.saveGame(game); }

    @Override
    public void saveSession(Session session) { context.saveSession(session); }
}