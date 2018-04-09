package dal.contexts;

import dal.entities.Session;
import dal.entities.User;
import dal.executioncontexts.DatabaseExecutionContext;
import play.Logger;
import play.db.jpa.JPAApi;
import scala.concurrent.ExecutionContext;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class JPASessionContext implements SessionContext {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @javax.inject.Inject
    public JPASessionContext(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    private EntityManager getEntityManager() {
        return this.jpaApi.em();
    }

    @Override
    public void save(Session session) {
        getEntityManager().persist(session);
    }

    @Override
    public Session findOne(String sessionId) {
        return getEntityManager()
                .createNamedQuery("Session.findOne", Session.class)
                .setParameter("id", sessionId)
                .setMaxResults(1)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public CompletableFuture<Session> getAndVerify(String sessionId, String username) {
        return CompletableFuture.supplyAsync(() -> jpaApi.withTransaction(entityManager -> entityManager
                .createNamedQuery("Session.getAndVerifyWithusername", Session.class)
                .setParameter("id", sessionId)
                .setParameter("username", username)
                .setMaxResults(1)
                .getResultList().stream().findFirst().orElse(null)), executionContext);
    }

    @Override
    public CompletableFuture<Session> getAndVerify(String sessionId, int userId) {
        return CompletableFuture.supplyAsync(() -> jpaApi.withTransaction(entityManager -> entityManager
                .createNamedQuery("Session.getAndVerifyWithuserId", Session.class)
                .setParameter("id", sessionId)
                .setParameter("userId", userId)
                .setMaxResults(1)
                .getResultList().stream().findFirst().orElse(null)), executionContext);
    }
}
