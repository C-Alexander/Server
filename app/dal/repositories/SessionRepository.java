package dal.repositories;

import dal.contexts.SessionContext;
import dal.entities.Session;
import play.Logger;
import play.cache.*;

import javax.inject.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Singleton
public class SessionRepository {

    private SessionContext context;
    private AsyncCacheApi cacheApi;

    @Inject
    public SessionRepository(SessionContext context, AsyncCacheApi cacheApi) {
        this.cacheApi = cacheApi;
        this.context = context;
    }

    public void createSession(Session session) {
        context.save(session);
    }

    public Boolean verify(String sessionId, int userId) {
        try {
            return cacheApi.getOrElseUpdate(sessionId, () -> verifyFromDatabase(sessionId, userId))
                    .toCompletableFuture()
                    .get() != null;
        } catch (ExecutionException | InterruptedException e) {
            Logger.error(e.getMessage());
            return false;
        }
    }

    private CompletableFuture<Session> verifyFromDatabase(String sessionId, int userId) {
        return context.getAndVerify(sessionId, userId);
    }

    private CompletableFuture<Session> verifyFromDatabase(String sessionId, String username) {
        return context.getAndVerify(sessionId, username);
    }

    public Session findOne(String sessionId) {
        return context.findOne(sessionId);
    }

    public Session getAndVerify(String sessionId, String username) {
        try {
            return cacheApi.getOrElseUpdate(sessionId, () -> verifyFromDatabase(sessionId, username))
                    .toCompletableFuture()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            Logger.error(e.getMessage());
            return null;
        }
    }

    public Session getAndVerify(String sessionId, int userId) {
        try {
            return cacheApi.getOrElseUpdate(sessionId, () -> verifyFromDatabase(sessionId, userId))
                    .toCompletableFuture()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            Logger.error(e.getMessage());
            return null;
        }
    }
}