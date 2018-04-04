package dal.contexts;

import dal.entities.Queue;

import java.util.List;

public interface LobbyContext {
    List<Queue> getQueue();

    void removePlayer(Queue player);

    void addPlayer(Queue player);
}
