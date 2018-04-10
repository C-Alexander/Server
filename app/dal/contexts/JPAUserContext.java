package dal.contexts;

import dal.entities.User;
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
        getEntityManager().persist(User);
    }

    @Override
    public List<User> findAll() {
        return getEntityManager()
                .createNamedQuery("User.getAll", User.class)
                .getResultList();.
    }

    @Override
    public Boolean ifExists(String Username) {
        return getEntityManager()
                .createNamedQuery("User.ifExists", Boolean.class)
                .setParameter("Username", Username)
                .getSingleResult();
    }

    @Override
    public Boolean login(User user) {
        return getEntityManager()
                .createNamedQuery("User.login", Boolean.class)
                .setParameter("username", user.getUsername())
                .setParameter("password", user.getPassword())
                .getSingleResult();
    }

    @Override
    public User findOne(int id) {
        return getEntityManager()
                .createNamedQuery("User.findOne", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
