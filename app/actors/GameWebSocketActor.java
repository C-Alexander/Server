package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class GameWebSocketActor extends AbstractActor {

    public static Props props(ActorRef out) {
        return Props.create(GameWebSocketActor.class, out);
    }

    private final ActorRef out;

    public GameWebSocketActor(ActorRef out) {
        this.out = out;
        getContext().actorSelection("*").tell("hi", self());

    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> System.out.println(message))
                .build();
    }
}