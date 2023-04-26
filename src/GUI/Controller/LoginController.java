package GUI.Controller;

import BE.User;
import GUI.MODEL.LoginModel;

import java.util.ArrayList;

public class LoginController {
    LoginModel loginModel;

    private ArrayList<User> allUsers;

    public LoginController(){
        //allUsers = new ArrayList<>();

        loginModel = LoginModel.getInstance();
        //allUsers = loginModel.getAllUsers();
    }




}
