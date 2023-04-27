package GUI.Controller;

import BE.User;
import GUI.MODEL.AdminModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    public TableView tblUser;
    public TableColumn clmUsername;
    public TableColumn clmRole;
    public AdminModel adminModel;

    public AdminController() {
        adminModel = AdminModel.getInstance();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showUsersAndDocuments();

    }

    /**
     * Opens the CreateUpdateUser window without the update button and with the create button
     * @param actionEvent
     */
    public void handleOpenCreateUser(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/CreateUpdateUser.fxml"));
        try {
            AnchorPane pane = loader.load();

            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.setScene(scene);
            dialogWindow.show();
        }
         catch (IOException e) {
            alertUser("Error: Could not open the create user window");
        }
    }

    /**
     * Opens the CreateUpdateUser window without the create button and with the update button
     * @param actionEvent
     */
    public void handleOpenUpdateUser(ActionEvent actionEvent) {
    }

    public void handleDeleteUser(ActionEvent actionEvent) {
    }

    /**
     * Shows the users and documents in the table views
     */
    private void showUsersAndDocuments(){
        clmUsername.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        clmRole.setCellValueFactory(new PropertyValueFactory<User, String>("role"));

        tblUser.setItems(adminModel.getUsersToBeViewed());
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }
}
