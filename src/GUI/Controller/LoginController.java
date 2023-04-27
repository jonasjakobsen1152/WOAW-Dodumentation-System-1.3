package GUI.Controller;

import BE.User;
import BLL.UTIL.BCrypt;
import GUI.MODEL.LoginModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginController {
    @FXML
    private MFXTextField txtUsername;
    @FXML
    private MFXTextField txtPassword;
    @FXML
    private MFXButton btnLogin;
    private LoginModel loginModel;

    public LoginController(){
        loginModel = LoginModel.getInstance();

    }

    public void handleLogin(ActionEvent actionEvent) {
        String usernameFromText = txtUsername.getText();
        String passwordFromText = txtPassword.getText();

//        String salt = BCrypt.gensalt(15);
//       String passwordToHash = BCrypt.hashpw("abc",salt);
//        System.out.println(passwordToHash);
        try {
            ArrayList<User> matchingUsernames = loginModel.getAllUsers(usernameFromText);
            for (User userToMatch: matchingUsernames) {
                String hashedPassword = userToMatch.getPassword();
                if(BCrypt.checkpw(passwordFromText,hashedPassword)){ // Checks if the password is equal
                    openBasedOnRole(userToMatch);
                }
            }
        } catch (SQLException e) {
            alertUser("There was a problem connecting to the database");
            throw new RuntimeException(e);
        }

    }

    private void openBasedOnRole(User user){
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        if(user.getRole().equals("Admin")){
            handleOpenAdmin(new ActionEvent());
            stage.close();
        }
        else if (user.getRole().equals("ProjectManager")) {
            handleOpenProjectManager(new ActionEvent());
            stage.close();
        }
        else if (user.getRole().equals("Technician")) {
        //TODO handle open technician
            stage.close();
        }
        else if (user.getRole().equals("Sales")) {
            //TODO handle open technician
            stage.close();
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

    public void handleOpenProjectManager(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/VIEW/ProjectManager.fxml"));
        try {
            AnchorPane pane = loader.load();
            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.setScene(scene);
            dialogWindow.show();
        }catch (IOException e) {
            e.printStackTrace();
            alertUser("Cant open ProjectManager window");
        }
    }
}
