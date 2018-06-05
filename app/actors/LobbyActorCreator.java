package actors;


import akka.japi.Creator;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class LobbyActorCreator implements Creator<LobbyActor> {
    private Injector injector;

    @Inject
    public LobbyActorCreator(Injector injector) {
        this.injector = injector;
    }

    @Override
    public LobbyActor create() {
        return injector.getInstance(LobbyActor.class);
    }
}