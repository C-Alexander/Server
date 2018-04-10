package dal.contexts;

import dal.entities.Character;

import java.util.List;

public interface CharacterContext {
    void save(Character character);

    List<Character> findAll();

    Character findOne(int id);
}
