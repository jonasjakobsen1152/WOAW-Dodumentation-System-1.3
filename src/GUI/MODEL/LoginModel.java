package GUI.MODEL;

import BE.User;
import BLL.LoginManager;

import java.util.ArrayList;

public class LoginModel {

    private LoginManager loginManager;

    public static LoginModel instance;

    public ArrayList<User> allUsers;

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

    public ArrayList<User> getAllUsers() {
        allUsers.addAll(loginManager.getAllUsers());
        return allUsers;
    }
}
