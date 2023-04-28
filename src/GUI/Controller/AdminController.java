package GUI.Controller;

import BE.User;
import GUI.MODEL.AdminModel;
import GUI.MODEL.CreateUpdateUserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    public TableView<User> tblUser;
    public TableColumn clmUsername;
    public TableColumn clmRole;
    public AdminModel adminModel;
    private User selectedUser;
    private CreateUpdateUserModel createUpdateUserModel;

    public AdminController() {
        adminModel = AdminModel.getInstance();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showUsersAndDocuments();

        tblUser.setOnMouseClicked(event -> {
            selectedUser = tblUser.getSelectionModel().getSelectedItem();
        });
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

            CreateUpdateUserController createUpdateUserController =loader.getController();
            CreateUpdateUserModel.getInstance();

            createUpdateUserController.removeUpdate();

            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.setScene(scene);
            dialogWindow.show();
        }
         catch (IOException e) {
            e.printStackTrace();
            alertUser("Error: Could not open the create user window");
        }
    }

    /**
     * Opens the CreateUpdateUser window without the create button and with the update button
     * @param actionEvent
     */
    public void handleOpenUpdateUser(ActionEvent actionEvent) {
        selectedUser = tblUser.getSelectionModel().getSelectedItem();
        createUpdateUserModel = CreateUpdateUserModel.getInstance();
        if (selectedUser == null){
            alertUser("Please select a user to edit");
        }else {
            createUpdateUserModel.setSelectedUser(selectedUser);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/View/CreateUpdateUser.fxml"));
            try {
                AnchorPane pane = loader.load();


                CreateUpdateUserController createUpdateUserController = loader.getController();
                CreateUpdateUserModel.getInstance();

                createUpdateUserController.setupUpdate(selectedUser);

                Stage dialogWindow = new Stage();
                Scene scene = new Scene(pane);
                dialogWindow.setScene(scene);
                dialogWindow.show();

            } catch (IOException e) {
                e.printStackTrace();
                alertUser("Error: Could not open the update user window");
            }
        }
    }

    public void handleDeleteUser(ActionEvent actionEvent) {
        selectedUser = tblUser.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select a user");
            alert.setHeaderText("Choose a user to delete");
            alert.show();
        } else if (Objects.equals(selectedUser.getRole(), "Admin")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("You cant delete an Admin user");
            alert.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete: " + selectedUser.getUsername().concat("?"));
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                try {
                    adminModel.deleteUser(selectedUser);
                }catch (SQLException e){
                    alertUser("Could not delete the user");
                    e.printStackTrace();
                }
                //updateUserModel();
                showUsersAndDocuments();
            }
        }
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

    public void handleShowDocument(ActionEvent actionEvent) {
    }

    public void handlePrintPDF(ActionEvent actionEvent) {
    }
}
