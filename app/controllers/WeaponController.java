package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dal.repositories.WeaponRepository;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WeaponController extends Controller {
    private final WeaponRepository weaponRepository;
    private ObjectMapper mapper;

    @Inject
    public WeaponController(WeaponRepository weaponRepository) {
        this.weaponRepository = weaponRepository;
        mapper = new ObjectMapper();
    }

    @Transactional
    public Result createWeapon() {
        JsonNode body = request().body().asJson();
        return null;
    }

    @Transactional(readOnly = true)
    public Result getWeapons() {
        try {
            return ok(mapper.writeValueAsString(weaponRepository.getAllWeapons()));
        } catch (JsonProcessingException e) {
            return (internalServerError(e.getMessage()));
        }
    }
}
