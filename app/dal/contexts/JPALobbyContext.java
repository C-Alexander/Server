package dal.contexts;

import dal.entities.Queue;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import java.util.List;

public class JPALobbyContext implements LobbyContext{

    private final JPAApi jpaApi;

    @javax.inject.Inject
    public JPALobbyContext(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    protected EntityManager getEntityManager() {
        return this.jpaApi.em();
    }

    @Override
    public List<Queue> getQueue() {
        return getEntityManager()
                .createNamedQuery("Queue.findAll", Queue.class)
                .getResultList();
    }

    @Override
    public void removePlayer(Queue player) {
        getEntityManager().remove(player);
    }

    @Override
    public void addPlayer(Queue player) {
        getEntityManager().persist(player);
    }
}
