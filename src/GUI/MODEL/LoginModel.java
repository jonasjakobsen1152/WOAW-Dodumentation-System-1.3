package GUI.MODEL;

import BE.User;
import BLL.LoginManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoginModel {

    private LoginManager loginManager;

    public static LoginModel instance;

    public ArrayList<User> allUsers;

    public User loggedInUser;


    private LoginModel() {
        loginManager = new LoginManager();
        allUsers = new ArrayList<>();
    }

    public static LoginModel getInstance() {
        if (instance == null) {
            instance = new LoginModel();
        }
            return instance;
    }

    public ArrayList<User> getAllUsers(String usernameText) throws SQLException {
        allUsers = loginManager.getAllUsers(usernameText);
        return allUsers;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
