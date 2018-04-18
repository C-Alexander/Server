package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.actor.PoisonPill;
import com.google.inject.Inject;
import dal.repositories.SessionRepository;
import msgs.PlayerJoinedMessage;
import play.Logger;

public class VerificationActor extends AbstractActor {
    @Inject
    public SessionRepository sessionRepository;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PlayerJoinedMessage.class, this::verifyPlayer)
                .build();
    }

    private void verifyPlayer(PlayerJoinedMessage message) {
        Logger.debug("Verifying joining player: " + message.getPlayer().getId());

        if (sessionRepository.verify(message.getSessionId(), message.getPlayer().getId())) {
            //get all actors ahead of this one in the hierarchy named GameManager
            ActorSelection selection = getContext().actorSelection("../../../GameManager");
            selection.forward(message, getContext());
            getSelf().tell(PoisonPill.getInstance(), getSelf());
        } else {
            getSender().tell(PoisonPill.getInstance(), getSelf());
        }
    }
}