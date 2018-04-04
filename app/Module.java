import com.google.inject.AbstractModule;
import dal.contexts.JPALobbyContext;
import dal.contexts.JPAUserContext;
import dal.contexts.LobbyContext;
import dal.contexts.UserContext;
import dal.repositories.LobbyRepository;
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
        bind(UserRepository.class);
        bind(UserContext.class).to(JPAUserContext.class);
        bind(LobbyRepository.class);
        bind(LobbyContext.class).to(JPALobbyContext.class);
    }

}