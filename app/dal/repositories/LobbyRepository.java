package dal.repositories;

import dal.contexts.LobbyContext;
import dal.entities.Queue;

import javax.inject.*;
import java.util.List;

@Singleton
public class LobbyRepository {

    private LobbyContext context;

    @Inject
    public LobbyRepository(LobbyContext context) {

        this.context = context;
    }

    public List<Queue> getQueue() {
        return context.getQueue();
    }

    public void removePlayer(Queue player) { context.removePlayer(player); }

    public void addPlayer(Queue player) { context.addPlayer(player); }
}