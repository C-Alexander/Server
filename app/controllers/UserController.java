package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dal.entities.User;
import dal.repositories.UserRepository;
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
public class UserController extends Controller {
    private final UserRepository userRepository;
    private ObjectMapper mapper;

    @Inject
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        mapper = new ObjectMapper();
    }

    @Transactional
    public Result createUser() {
        JsonNode body = request().body().asJson();
        User user = new User();
        user.setUsername(body.get("username").asText());
        if(!userRepository.IfExists(user.getUsername())){
            user.setPassword(Crypt.crypt(body.get("password").asText(), "LeagueofLegends"));
            userRepository.createUser(user);
            try {
                return created(mapper.writeValueAsString(user));
            } catch (JsonProcessingException e) {
                Logger.error("Unknown message: " + e.getMessage());
                return internalServerError(e.getMessage());
            }
        }
        else {
            return status(409, "Username already exists, have you tried logging in?");
        }
    }

    @Transactional
    public Result login() {
        JsonNode body = request().body().asJson();
        User user = new User();
        UUID uuid = UUID.randomUUID();
        user.setUsername(body.get("username").asText());
        user.setPassword(Crypt.crypt(body.get("password").asText(), "LeagueofLegends"));
        if(userRepository.login(user)){
                return ok(uuid.toString());
            }else{
                return unauthorized("Username or password incorrect.");
            }
    }

    @Transactional(readOnly = true)
    public Result getUsers() {
        try {
            return ok(mapper.writeValueAsString(userRepository.getAllUsers()));
        } catch (JsonProcessingException e) {
            return (internalServerError(e.getMessage()));
        }
    }
}
