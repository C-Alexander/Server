package game;

import actors.GameActor;
import akka.actor.ActorRef;
import akka.actor.ActorRefFactory;
import msgs.*;


public class GameServer {
    private ActorRef gameActor;

    public GameServer(ActorRef gameActor) {
        this.gameActor = gameActor;
    }

    public void moveCharacter(PlayerPacket moveMessage) {

        if (moveMessage == null) System.out.println("hallo");
        MoveMessage mm = (MoveMessage) moveMessage.data;
//        System.out.println(mm.getX());
//        System.out.println(mm.getY());
//        System.out.println(mm.getId());
        if (mm == null) System.out.println("lol");

        MoveUpdate mu = new MoveUpdate(mm.getX(), mm.getY(), mm.getId());
        Packet packet = new Packet();
        packet.data = mu;
        packet.messageType = MessageType.MOVE;
        gameActor.tell(packet, ActorRef.noSender());
    }
}
