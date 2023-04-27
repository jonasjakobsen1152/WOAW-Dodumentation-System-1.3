package DAL;

import BE.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ILoginDAO {

    ArrayList<User> getAllUsers(String usernameText) throws SQLException;
}
