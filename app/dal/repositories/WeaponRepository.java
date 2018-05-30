package dal.repositories;

import dal.contexts.WeaponContext;
import dal.entities.Weapon;

import javax.inject.*;
import java.util.List;

@Singleton
public class WeaponRepository {

    private WeaponContext context;

    @Inject
    public WeaponRepository(WeaponContext context) {
        this.context = context;
    }

    public void createWeapon(Weapon weapon) {
        context.save(weapon);
    }

    public List<Weapon> getAllWeapons() {
        return context.findAll();
    }

    public Weapon findOne(int id){return context.findOne(id);}
}