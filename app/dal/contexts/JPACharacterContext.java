package dal.contexts;

import dal.entities.Character;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import java.util.List;

public class JPACharacterContext implements CharacterContext {

    private final JPAApi jpaApi;

    @javax.inject.Inject
    public JPACharacterContext(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    protected EntityManager getEntityManager() {
        return this.jpaApi.em();
    }

    @Override
    public void save(Character character) {
        getEntityManager().persist(Character);
    }

    @Override
    public List<Character> findAll() {
        return getEntityManager()
                .createNamedQuery("Character.getAll", Character.class)
                .getResultList();.
    }

    @Override
    public Character findOne(int id) {
        return getEntityManager()
                .createNamedQuery("Character.findOne", Character.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
