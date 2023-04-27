package GUI.Controller;

import BE.User;
import GUI.MODEL.LoginModel;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginController {
    public MFXTextField txtUsername;
    public MFXTextField txtPassword;
    LoginModel loginModel;

    private ArrayList<User> users;

    public LoginController(){
        loginModel = LoginModel.getInstance();

    }

    public void handleLogin(ActionEvent actionEvent) {
        String usernameText = txtUsername.getText();
        try {
            ArrayList<User> matchingUsernames = loginModel.getAllUsers(usernameText);
            for (User : matchingUsernames) {

            }
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


    public void handleOpenAdmin(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/VIEW/Admin.fxml"));
        try {
            AnchorPane pane = loader.load();
            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.setScene(scene);
            dialogWindow.show();
        }catch (IOException e) {
            e.printStackTrace();
            alertUser("Cant open admin window");
        }
    }
}
