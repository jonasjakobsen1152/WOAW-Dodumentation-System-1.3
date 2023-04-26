package GUI.Controller;

import BE.User;
import GUI.MODEL.LoginModel;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoginController {
    LoginModel loginModel;

    private ArrayList<User> allUsers;

    public LoginController(){
        loginModel = LoginModel.getInstance();

        getAllUsers();
    }

    private void getAllUsers(){
        try {
            //Get a list of all users
            allUsers = loginModel.getAllUsers();
        } catch (SQLException e) {
            alertUser("There was a problem connecting to the database");
            throw new RuntimeException(e);
        }
    }


    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }


}
