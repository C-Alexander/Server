package dal.contexts;

import dal.entities.Weapon;

import java.util.List;

public interface WeaponContext {
    void save(Weapon weapon);

    List<Weapon> findAll();

    Weapon findOne(int id);
}
