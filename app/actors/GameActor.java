package actors;

import akka.actor.*;
import game.GameServer;
import models.Player;
import msgs.*;
import play.Logger;
import play.libs.Json;

import java.util.HashMap;

public class GameActor extends AbstractActor {
    GameServer gameServer =  new GameServer(getSelf());
    private HashMap<Integer, Player> players;
    private String gameId;

    public static Props props() {
        return Props.create(GameActor.class);
    }

    public GameActor() {
        players = new HashMap<>();
        }

    @Override
    public Receive createReceive() {
        Logger.debug("Received");
        return receiveBuilder()
                .match(PlayerJoinedMessage.class, this::handleJoiningPlayer)
                .match(PlayerPacket.class, this::handlePlayerAction)
                .match(Packet.class, this::handlePacketToPlayers)
                .match(PoisonPill.class, this::handleGameEnd)
                .matchAny(message -> Logger.error("Unknown message: " + message))
                .build();
    }

    private void handlePacketToPlayers(Packet packet) {
        broadcastToPlayers(packet);
    }

    private void handleGameEnd(PoisonPill poisonpill) {
        ActorSelection selection = getContext().actorSelection("../../../LobbyManager");
        selection.tell(new EndGameMessage(gameId), getSelf());
    }

    private void handlePlayerAction(PlayerPacket playerPacket) {

        switch (playerPacket.messageType) {
            case MOVE :
                this.gameServer.moveCharacter(playerPacket);
                break;
            case ATTACK:
                this.gameServer.attackCharacter(playerPacket);
                break;
            case ENDTURN:
                this.gameServer.endTurn(playerPacket);
                break;

            default: Logger.info("Got playerpacket");
        }
    }

    private void handleJoiningPlayer(PlayerJoinedMessage message) {
        Logger.info("Broadcasting new player: " + message.getPlayer().getId());
        gameId = message.getGame();
        Player newPlayer = message.getPlayer();
        newPlayer.setGame(getSelf());
        players.putIfAbsent(newPlayer.getId(), newPlayer);
        PlayerAddedToGameMessage successMessage = new PlayerAddedToGameMessage();
        successMessage.setGame(getSelf());
        getSender().tell(successMessage, getSelf());


        for (Player player : players.values()) {
            player.getOut().tell("Welcome, " + newPlayer.getId() + " to Game " + getSelf().path(), getSelf());
            if(player.getId() == newPlayer.getId()){
               this.gameServer.sendClientInfo(player.getOut());
            }

        }
    }

    public void broadcastToPlayers(Packet packet){
        Logger.debug("Broadcasting new player: " + "Sending PackageS");
        for (Player player : players.values())
            player.getOut().tell(Json.toJson(packet).toString(), getSelf());
    }

}
