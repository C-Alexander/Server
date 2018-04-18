package actors;

import akka.japi.Creator;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class VerificationActorCreator implements Creator<VerificationActor> {
    private Injector injector;

    @Inject
    public VerificationActorCreator(Injector injector) {
        this.injector = injector;
    }

    @Override
    public VerificationActor create() {
        return injector.getInstance(VerificationActor.class);
    }
}
