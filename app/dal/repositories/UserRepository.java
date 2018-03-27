package dal.repositories;

import dal.contexts.UserContext;
import dal.entities.User;
import play.db.jpa.JPAApi;

import javax.inject.*;
import javax.persistence.*;
import java.util.List;
import java.util.concurrent.*;

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
}