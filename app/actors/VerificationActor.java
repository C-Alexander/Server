package actors;

import akka.actor.*;
import akka.parboiled2.support.Join;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import dal.repositories.SessionRepository;
import dal.repositories.UserRepository;
import models.Player;
import msgs.*;
import play.Logger;

import java.io.IOException;
import java.util.HashMap;

public class VerificationActor extends AbstractActor {
    @Inject
    public SessionRepository sessionRepository;

    public static Props props() {
        return Props.create(VerificationActor.class);
    }


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