package DAL;

import BE.User;

public interface ICreateUpdateUserDAO {
    void createUser(String username, String password, String role);
    void updateUser(User user);
}
