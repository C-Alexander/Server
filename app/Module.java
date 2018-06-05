import actors.VerificationActor;
import actors.VerificationActorCreator;
import com.google.inject.AbstractModule;
import dal.contexts.JPASessionContext;
import dal.contexts.JPAGameContext;
import dal.contexts.JPAUserContext;
import dal.contexts.SessionContext;
import dal.contexts.GameContext;
import dal.contexts.UserContext;
import dal.executioncontexts.DatabaseExecutionContext;
import dal.repositories.SessionRepository;
import dal.repositories.GameRepository;
import dal.repositories.UserRepository;

/**
 * This class is a Guice module (and dank as fuck) that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 * <p>
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {

    @Override
    public void configure() {
        bind(DatabaseExecutionContext.class);

        bind(VerificationActor.class);
        bind(VerificationActorCreator.class);

        bind(UserRepository.class);
        bind(SessionRepository.class);
        bind(SessionContext.class).to(JPASessionContext.class);

        bind(UserContext.class).to(JPAUserContext.class);
        bind(GameRepository.class);
        bind(GameContext.class).to(JPAGameContext.class);
    }

}