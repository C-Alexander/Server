package dal.contexts;

import dal.entities.User;
import dal.entities.Weapon;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import java.util.List;

public class JPAUserContext implements UserContext {

    private final JPAApi jpaApi;

    @javax.inject.Inject
    public JPAUserContext(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    private EntityManager getEntityManager() {
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

    @Override
    public Boolean ifExists(String username) {
        return getEntityManager()
                .createNamedQuery("User.ifExists", Boolean.class)
                .setParameter("username", username)
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
    public User getAndAuthenticate(String username, String password) {
        return getEntityManager()
                .createNamedQuery("User.getAndAuthenticate", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .setMaxResults(1)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public User findOne(int id) {
        return getEntityManager()
                .createNamedQuery("User.findOne", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
