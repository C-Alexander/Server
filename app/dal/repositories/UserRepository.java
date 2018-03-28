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

    public void createUser(User user) {
        context.save(user);
    }

    public List<User> getAllUsers() {
        return context.findAll();
    }

    public Boolean checkIfExists(String username) {
        return context.ifExists(username);
    }
}