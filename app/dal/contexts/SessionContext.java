package dal.contexts;

import dal.entities.Session;

import java.util.concurrent.CompletableFuture;

public interface SessionContext {
    void save(Session session);

    Session findOne(String sessionId);

    CompletableFuture<Session> getAndVerify(String sessionId, int userId);

    CompletableFuture<Session> getAndVerify(String sessionId, String username);

}
