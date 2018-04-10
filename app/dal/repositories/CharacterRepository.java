package dal.repositories;

import dal.contexts.CharacterContext;
import dal.entities.Character;

import javax.inject.*;
import java.util.List;

@Singleton
public class CharacterRepository {

    private CharacterContext context;

    @Inject
    public CharacterRepository(CharacterContext context) {
        this.context = context;
    }

    public void createCharacter(Character character) {
        context.save(character);
    }

    public List<Character> getAllCharacters() {
        return context.findAll();
    }

    public Character findOne(int id){return context.findOne(id);}
}