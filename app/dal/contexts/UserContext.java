package dal.contexts;

import dal.entities.User;

import java.util.List;

public interface UserContext {
    void save(User user);

    List<User> findAll();

    User findOne(int id);

    Boolean ifExists(String username);

    Boolean login(User user);

    User getAndAuthenticate(String username, String password);

    List<Character> getTeam(int id);
}
