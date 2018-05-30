package dal.repositories;

import dal.contexts.UserContext;
import dal.entities.User;

import javax.inject.*;
import java.util.List;

@Singleton
public class UserRepository {

    private UserContext context;

    @Inject
    public UserRepository(UserContext context) {
        this.context = context;
    }

    public void save(User user) { context.save(user);}

    public void createUser(User user) {
        context.save(user);
    }

    public List<User> getAllUsers() { return context.findAll(); }

    public User getUser(int id) { return context.findOne(id); }

    public Boolean IfExists(String username) {
        return context.ifExists(username);
    }

    public Boolean login(User user) {
        return context.login(user);
    }

    public User getAndAuthenticate(String username, String password) {
        return context.getAndAuthenticate(username, password);
    }

    public List<Character> getTeam(int id) {return context.getTeam(id); }
}