package GUI.Controller;

import BE.Customer;
import BE.User;
import GUI.MODEL.CreateUpdateJobModel;
import GUI.MODEL.CreateUpdateUserModel;
import GUI.MODEL.ProjectManagerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProjectManagerController implements Initializable {
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<User> tblShowTechnicians;
    @FXML
    private TableColumn clmShowTechnicians;
    @FXML
    private TableView<User> tblShowSalesmen;
    @FXML
    private TableColumn clmShowSalesmen;
    @FXML
    private TableView<Customer> tblShowCustomers;
    @FXML
    private TableColumn clmShowCustomers;
    @FXML
    private TableView<User> tblShowDocument;
    @FXML
    private TableColumn clmShowDocument;
    public ProjectManagerModel projectManagerModel;
    private User selectedUser;
    CreateUpdateUserModel createUpdateUserModel;
    CreateUpdateJobModel createUpdateJobModel;
    private User selectedTechnician;
    private Customer selectedCustomer;

    public ProjectManagerController() {
        projectManagerModel = ProjectManagerModel.getInstance();
        createUpdateJobModel = CreateUpdateJobModel.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showTechnician();
        showSalesmen();
        showCustomer();
        //showDocument();

        tblShowTechnicians.setOnMouseClicked(event -> {
            selectedUser = tblShowTechnicians.getSelectionModel().getSelectedItem();
            tblShowCustomers.getSelectionModel().clearSelection();
            tblShowSalesmen.getSelectionModel().clearSelection();

        });
        tblShowSalesmen.setOnMouseClicked(event -> {
            selectedUser = tblShowSalesmen.getSelectionModel().getSelectedItem();
            tblShowTechnicians.getSelectionModel().clearSelection();
            tblShowCustomers.getSelectionModel().clearSelection();
        });
        tblShowCustomers.setOnMouseClicked(event -> {
            selectedCustomer = tblShowCustomers.getSelectionModel().getSelectedItem();

        });
        tblShowDocument.setOnMouseClicked(event -> {
            selectedUser = tblShowDocument.getSelectionModel().getSelectedItem();
        });


    }

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

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    public void handleAddWork(ActionEvent actionEvent) {
        selectedTechnician = tblShowTechnicians.getSelectionModel().getSelectedItem();
        selectedCustomer = tblShowCustomers.getSelectionModel().getSelectedItem();
        createUpdateJobModel.setCustomerAndTechnician(selectedTechnician,selectedCustomer);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/CreateUpdateJob.fxml"));
        try {
            AnchorPane pane = loader.load();

            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.setScene(scene);
            dialogWindow.show();
        }
        catch (IOException e) {
            alertUser("Error: Could not open the job creation window");
        }

    }

    public void handleDeleteWork(ActionEvent actionEvent) {
    }

    public void handleDeleteTechnician(ActionEvent actionEvent) {
        selectedUser = tblShowTechnicians.getSelectionModel().getSelectedItem();
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
                projectManagerModel.deleteUser(selectedUser);
                //updateUserModel();
                showTechnician();
            }
        }
    }

    public void handleDeleteCustomer(ActionEvent actionEvent) {
        selectedCustomer = tblShowCustomers.getSelectionModel().getSelectedItem();
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
                //projectManagerModel.deleteUser(selectedUser);
                //updateUserModel();
                showCustomer();
            }
        }
    }


    public void handleDeleteSalesmen(ActionEvent actionEvent) {
        selectedUser = tblShowSalesmen.getSelectionModel().getSelectedItem();
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
                projectManagerModel.deleteUser(selectedUser);
                //updateUserModel();
                showSalesmen();
            }
        }
    }

    public void handleOpenUpdateUser(ActionEvent actionEvent) {
        if (selectedUser == null) {
            alertUser("Please select a user to edit");
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CreateUpdateUser.fxml"));
                AnchorPane pane = loader.load();

                CreateUpdateUserController createUpdateUserController = loader.getController();
                createUpdateUserController.setupUpdate(selectedUser);

                Stage dialogWindow = new Stage();
                Scene scene = new Scene(pane);
                dialogWindow.setScene(scene);
                dialogWindow.show();
            } catch (IOException e) {
                alertUser("Error: Could not open the update user window");
                e.printStackTrace();
            }
        }
    }


    public void handleReadJob(ActionEvent actionEvent) {
    }

    private void showTechnician(){
        clmShowTechnicians.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        tblShowTechnicians.setItems(projectManagerModel.getTechnicianToBeViewed());
    }

    private void showSalesmen(){
        clmShowSalesmen.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        tblShowSalesmen.setItems(projectManagerModel.getSalesmenToBeViewed());
    }

    private void showCustomer(){
        //clmShowCustomers.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        //tblShowCustomers.setItems(projectManagerModel.getCustomerToBeViewed());
    }
}
