package BLL;

import BE.User;
import DAL.DB.LoginDAO_DB;
import DAL.ILoginDAO;

import java.util.ArrayList;

public class LoginManager {
    private ILoginDAO iLoginDAO;

    public ArrayList<User> allUsers;

    public LoginManager(){
        iLoginDAO = new LoginDAO_DB();
    }

    public ArrayList<User> getAllUsers() {
    allUsers.addAll(iLoginDAO.getAllUsers());
    return allUsers;
    }
}