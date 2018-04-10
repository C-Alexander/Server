package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dal.entities.Character;
import dal.repositories.CharacterRepository;
import org.apache.commons.codec.digest.Crypt;
import org.hibernate.id.GUIDGenerator;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;


@Singleton
public class CharacterController extends Controller {
    private final CharacterRepository characterRepository;
    private ObjectMapper mapper;

    @Inject
    public CharacterController(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
        mapper = new ObjectMapper();
    }

    @Transactional
    public Result createCharacter() {
        JsonNode body = request().body().asJson();
        return null;
    }

    @Transactional(readOnly = true)
    public Result getCharacters() {
        try {
            return ok(mapper.writeValueAsString(characterRepository.getAllCharacters()));
        } catch (JsonProcessingException e) {
            return (internalServerError(e.getMessage()));
        }
    }
}
