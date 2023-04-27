package BLL;

import BE.User;
import DAL.DB.LoginDAO_DB;
import DAL.ILoginDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoginManager {
    private ILoginDAO iLoginDAO;

    public ArrayList<User> allUsers;

    public LoginManager(){
        iLoginDAO = new LoginDAO_DB();
        allUsers = new ArrayList<>();
    }

    public ArrayList<User> getAllUsers(String usernameText) throws SQLException {
    allUsers = iLoginDAO.getAllUsers(usernameText);
    return allUsers;
    }
}
