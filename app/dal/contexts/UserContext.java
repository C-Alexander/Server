package dal.contexts;

import dal.entities.User;

import java.util.List;

public interface UserContext {
    void save(User user);

    List<User> findAll();

    Boolean ifExists(String username);

    Boolean login(User user);

    User findOne(int id);
}
