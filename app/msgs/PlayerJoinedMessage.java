package msgs;

import models.Player;

public class PlayerJoinedMessage extends Message {
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    Player player;

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    String game;
}
