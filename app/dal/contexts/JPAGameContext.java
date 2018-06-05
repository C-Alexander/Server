package dal.contexts;

import dal.entities.Game;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import java.util.List;

public class JPAGameContext implements GameContext {

    private final JPAApi jpaApi;

    @javax.inject.Inject
    public JPAGameContext(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    private EntityManager getEntityManager() {
        return this.jpaApi.em();
    }

    @Override
    public List<Game> getGames() {
        return getEntityManager()
                .createNamedQuery("Game.getAll", Game.class)
                .getResultList();
    }

    @Override
    public Game getGame(String gameId) {
        return getEntityManager()
                .createNamedQuery("Game.findOne", Game.class)
                .setParameter("id", gameId)
                .getSingleResult();
    }

    @Override
    public Game getOpenGame() {
        List<Game> results = getEntityManager()
                .createNamedQuery("Game.findFirstOpen", Game.class)
                .setMaxResults(1)
                .getResultList();
        if (results.isEmpty()) return new Game();
        else return results.get(0);
    }

    @Override
    public void saveGame(Game game) {
        getEntityManager().persist(game);
    }

    @Override
    public void removeGame(Game game) {
        getEntityManager().remove(game);
    }
}
