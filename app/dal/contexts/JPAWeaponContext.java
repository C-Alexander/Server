package dal.contexts;

import dal.entities.Weapon;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import java.util.List;

public class JPAWeaponContext implements WeaponContext {
    private final JPAApi jpaApi;

    @javax.inject.Inject
    public JPAWeaponContext(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    protected EntityManager getEntityManager() {
        return this.jpaApi.em();
    }

    @Override
    public void save(Weapon weapon) {
        getEntityManager().persist(weapon);
    }

    @Override
    public List<Weapon> findAll() {
        return getEntityManager()
                .createNamedQuery("Weapon.getAll", Weapon.class)
                .getResultList();
    }

    @Override
    public Weapon findOne(int id) {
        return getEntityManager()
                .createNamedQuery("Weapon.findOne", Weapon.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
