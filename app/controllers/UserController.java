package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dal.entities.Session;
import dal.entities.User;
import dal.entities.Character;
import dal.repositories.SessionRepository;
import dal.repositories.UserRepository;
import org.apache.commons.codec.digest.Crypt;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class UserController extends Controller {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private ObjectMapper mapper;

    @Inject
    public UserController(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        mapper = new ObjectMapper();
    }

    @Transactional
    public Result createUser() {
        JsonNode body = request().body().asJson();
        User user = new User();
        user.setUsername(body.get("username").asText());

        if (userRepository.IfExists(user.getUsername()))
            return status(Http.Status.CONFLICT, "Username already exists, have you tried logging in?");
        user.setPassword(encrypt(body.get("password")));

        userRepository.createUser(user);
            try {
                return created(mapper.writeValueAsString(user));
            } catch (JsonProcessingException e) {
                Logger.error("Unknown message: " + e.getMessage());
                return internalServerError(e.getMessage());
            }
    }

    @Transactional
    public Result login() {
        JsonNode body = request().body().asJson();
        User user = userRepository.getAndAuthenticate(body.get("username").asText(), encrypt(body.get("password")));
        if (user != null) {
            Session session = new Session(user);
            sessionRepository.createSession(session);
            try {
                return ok(mapper.writeValueAsString(session));
            } catch (JsonProcessingException e) {
                Logger.error(e.getMessage());
                return internalServerError();
            }
        } else {
                return unauthorized("Username or password incorrect.");
            }
    }

    private String encrypt(JsonNode password) {
        return Crypt.crypt(password.asText(), "LeagueofLegends");
    }

    @Transactional(readOnly = true)
    public Result getUserBySession() {
        JsonNode body = request().body().asJson();

        if (body == null
                || !body.hasNonNull("sessionId")
                || !body.hasNonNull("userId")) return badRequest("Please specify a sessionId and an userId.");

        Session session = sessionRepository.getAndVerify(body.get("sessionId").asText(), body.get("userId").asInt());

        if (session == null) return unauthorized("Incorrect userid and sessionid pair.");

        try {
            return ok(mapper.writeValueAsString(session));
        } catch (JsonProcessingException e) {
            Logger.error(e.getMessage());
            return internalServerError();
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

    @Transactional
    public Result addToTeam(int userId, Character character) {
        JsonNode body = request().body().asJson();
        User user = userRepository.getUser(userId);
        user.addCharacterToTeam(character);
        userRepository.save(user);

        try {
            return created(mapper.writeValueAsString(character) + " added to characterTeam of user " + user.getId());
        } catch (JsonProcessingException e) {
            Logger.error("Unknown message: " + e.getMessage());
            return internalServerError(e.getMessage());
        }
    }

    @Transactional
    public Result removeFromTeam(int userId, Character character) {
        JsonNode body = request().body().asJson();
        User user = userRepository.getUser(userId);
        user.removeCharacterFromTeam(character);
        userRepository.save(user);

        try {
            return created(mapper.writeValueAsString(character) + " removed from characterTeam of user " + user.getId());
        } catch (JsonProcessingException e) {
            Logger.error("Unknown message: " + e.getMessage());
            return internalServerError(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Result getTeam(int id) {
        try {
            userRepository.getTeam(id);
            return ok(mapper.writeValueAsString(userRepository.getTeam(id)));
        } catch (JsonProcessingException e) {
            return (internalServerError(e.getMessage()));
        }
    }
}
