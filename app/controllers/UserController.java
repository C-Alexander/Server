package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import dal.entities.User;
import dal.repositories.UserRepository;
import org.apache.commons.codec.digest.Crypt;
import play.api.libs.Crypto;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;


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
        user.setPassword(Crypt.crypt(body.get("password").asText()));
        userRepository.createUser(user);
        try {
            return created(mapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return internalServerError(e.getMessage());
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