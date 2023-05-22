package GUI.Controller;

import BE.User;
import BLL.UTIL.BCrypt;
import GUI.MODEL.LoginModel;
import GUI.MODEL.TechnicianModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Button btnAdmin;
    public Button btnProject;
    public Button btnSakes;
    public Button btnTech;
    public Text txtLoginFailed;
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
        try {
            ArrayList<User> matchingUsernames = loginModel.getAllUsers(usernameFromText);
            for (User userToMatch: matchingUsernames) {
                String hashedPassword = userToMatch.getPassword();
                if(BCrypt.checkpw(passwordFromText,hashedPassword)){ // Checks if the password is equal
                    openBasedOnRole(userToMatch);
                }
                else {
                    txtLoginFailed.setVisible(true);
                }
            }
        } catch (SQLException e) {
            alertUser("There was a problem connecting to the database");
            throw new RuntimeException(e);
        }



    }

    private void openBasedOnRole(User user){
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        loginModel.setLoggedInUser(user);
        if(user.getRole().equals("Admin")){
            handleOpenAdmin(new ActionEvent());
            stage.close();
        }
        else if (user.getRole().equals("Project Manager")) {
            handleOpenProjectManager(new ActionEvent());
            stage.close();
        }
        else if (user.getRole().equals("Technician")) {
        handleOpenTechnician(new ActionEvent());
            stage.close();
        }
        else if (user.getRole().equals("Salesmen")) {
            handleOpenSales(new ActionEvent());
            stage.close();
        }
    }

    public void handleOpenSales(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/VIEW/Salesmen.fxml"));
        try{
            AnchorPane pane = loader.load();
            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.setScene(scene);
            dialogWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
            alertUser("Cannot open Sales window");
        }
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

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    public void handleOpenTechnician(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/VIEW/Technician.fxml"));
        try {
            AnchorPane pane = loader.load();
            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.setScene(scene);
            dialogWindow.show();
        }catch (IOException e) {
            e.printStackTrace();
            alertUser("Cant open Technician window");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAdmin.setVisible(false);
        btnSakes.setVisible(false);
        btnProject.setVisible(false);
        btnTech.setVisible(false);
        txtLoginFailed.setVisible(false);
    }
}
