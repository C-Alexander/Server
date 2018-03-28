package dal.contexts;

import com.google.inject.Inject;
import dal.contexts.UserContext;
import dal.entities.User;
import dal.repositories.UserRepository;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import java.util.List;

public class JPAUserContext implements UserContext {

    private final JPAApi jpaApi;

    @javax.inject.Inject
    public JPAUserContext(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    protected EntityManager getEntityManager() {
        return this.jpaApi.em();
    }

    @Override
    public void save(User user) {
        getEntityManager().persist(user);
    }

    @Override
    public List<User> findAll() {
        return getEntityManager()
                .createNamedQuery("User.getAll", User.class)
                .getResultList();
    }
}
