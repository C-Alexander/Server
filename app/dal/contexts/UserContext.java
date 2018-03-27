package dal.contexts;

import dal.entities.User;

import java.util.List;

public interface UserContext {
    void save(User user);

    List<User> findAll();
}