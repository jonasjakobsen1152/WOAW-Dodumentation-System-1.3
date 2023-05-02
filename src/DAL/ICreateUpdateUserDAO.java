package DAL;

import BE.User;

import java.sql.SQLException;

public interface ICreateUpdateUserDAO {
    void createUser(String username, String password, String role) throws SQLException;
    void updateUser(User user);
}
