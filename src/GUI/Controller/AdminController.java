package GUI.Controller;

import BE.Customer;
import BE.Job;
import BE.User;
import GUI.MODEL.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
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
    public TableView<Customer> tblCustomer;
    public TableColumn clmName;
    public TableColumn clmPhone;
    public TableColumn clmEmail;
    public TableColumn clmDescription;
    public TableColumn clmTitle;
    public TableView<Job> tblDocument;
    public TextField txtSearch;
    public TableColumn clmAddress;
    public TextField txtSearchJobs;
    public TextField txtSearchUsers;
    public TableColumn clmTitleWork;
    public TableView<Job> tblWork;
    private User selectedUser;
    private Customer selectedCustomer;
    private Job selectedJob;
    private CreateUpdateUserModel createUpdateUserModel;
    private CustomerModel customerModel;
    private TechnicianJobModel technicianJobModel;
    private DocumentationModel documentationModel;
    private LoginModel loginModel;

    public AdminController() {
        try {
            loginModel = LoginModel.getInstance();
            adminModel = AdminModel.getInstance();
            technicianJobModel = TechnicianJobModel.getInstance();
            documentationModel = DocumentationModel.getInstance();
        }catch (SQLException e){
            e.printStackTrace();
            alertUser("Could not get lists from database");
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showUsersAndDocuments();

        tblUser.setOnMouseClicked(event -> {
            selectedUser = tblUser.getSelectionModel().getSelectedItem();
            loginModel.setLoggedInUser(selectedUser);
        });
        tblDocument.setOnMouseClicked(event -> {
            selectedJob = tblDocument.getSelectionModel().getSelectedItem();
        });

        searchListeners();
    }

    public void searchListeners(){
        txtSearchUsers.textProperty().addListener((observable, oldValue, newValue) ->{
            Runnable task = () -> adminModel.searchUsers(newValue);
            Thread thread = new Thread(task);
            thread.start();
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                    Runnable task = () -> adminModel.searchCustomers(newValue);
                    Thread thread = new Thread(task);
                    thread.start();
        });

        txtSearchJobs.textProperty().addListener((observable, oldValue, newValue) ->{
            Runnable task = () -> adminModel.searchJobs(newValue);
            Thread thread = new Thread(task);
            thread.start();
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
        } catch (SQLException e) {
            e.printStackTrace();
            alertUser("Error: Could not open the create user window");
        }
    }

    /**
     * Opens the CreateUpdateUser window without the create button and with the update button
     * @param actionEvent
     */
    public void handleOpenUpdateUser(ActionEvent actionEvent) throws SQLException {
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

        clmName.setCellValueFactory(new PropertyValueFactory<Customer,String>("name"));
        clmPhone.setCellValueFactory(new PropertyValueFactory<Customer,String>("phone"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<Customer,String>("email"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<Customer,String>("address"));

        tblCustomer.setItems(adminModel.getCustomerToBeViewed());

        clmTitle.setCellValueFactory(new PropertyValueFactory<Job,String>("title"));

        tblDocument.setItems(adminModel.getDocumentsToBeViewed());
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    public void handleShowDocument(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/TechnicianJobWindow.fxml"));
        selectedJob = tblDocument.getSelectionModel().getSelectedItem();
        try {
            technicianJobModel = TechnicianJobModel.getInstance();
            documentationModel.setSelectedJob(selectedJob);
            technicianJobModel.setSelectedJob(selectedJob);
            technicianJobModel.showList();

            AnchorPane pane = loader.load();

            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.setScene(scene);
            dialogWindow.show();
        }
        catch (IOException e) {
            e.printStackTrace();
            alertUser("Error: Could not open the technician job window");
        }catch (SQLException e){
            e.printStackTrace();
            alertUser("Could not get the documentation list from the database");
        }
    }

    public void handlePrintPDF(ActionEvent actionEvent) {
    }

    public void handleCreateCustomer(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/Customer.fxml"));
            AnchorPane pane = loader.load();


            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.setScene(scene);
            dialogWindow.showAndWait();
            showUsersAndDocuments();
        }catch (IOException e){
            e.printStackTrace();
            alertUser("Could not open the customer window");
        }
    }

    public void handleDeleteCustomer(ActionEvent actionEvent) {
        selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select a customer");
            alert.setHeaderText("Choose a customer to delete");
            alert.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete: " + selectedCustomer.getName().concat("?"));
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                try {
                    adminModel.deleteCustomer(selectedCustomer);
                    //updateUserModel();
                    showUsersAndDocuments();
                }catch (SQLException e){
                    alertUser("Could not delete customer");
                }
            }
        }
    }

    public void handleUpdateCustomer(ActionEvent actionEvent) {
        selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null){
            alertUser("Select a customer to update");
        }else {
            try {
                customerModel = CustomerModel.getInstance();
                customerModel.setCustomer(selectedCustomer);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/GUI/View/Customer.fxml"));
                try {
                    AnchorPane pane = loader.load();


                    CustomerController customerController = loader.getController();
                    CustomerModel.getInstance();

                    customerController.setupUpdate(selectedCustomer);

                    Stage dialogWindow = new Stage();
                    Scene scene = new Scene(pane);
                    dialogWindow.initModality(Modality.WINDOW_MODAL);
                    dialogWindow.initOwner((((Node) actionEvent.getSource()).getScene().getWindow()));
                    dialogWindow.setScene(scene);
                    dialogWindow.showAndWait();
                    showUsersAndDocuments();

                } catch (IOException e) {
                    e.printStackTrace();
                    alertUser("Error: Could not open the update customer window");
                }
            }catch (SQLException e){
                alertUser("Could not open the update customer window");
            }
        }


    }

    public void handleShowWork(ActionEvent actionEvent) {

        selectedUser = tblUser.getSelectionModel().getSelectedItem();
        if(selectedUser == null || !selectedUser.getRole().equals("Technician")){
            alertUser("Select a technician from the user table");
        }else{
            adminModel.setSelectedUser(selectedUser);
            adminModel.showList();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/View/AdminTechnicianJobWindow.fxml"));
            try {
                AnchorPane pane = loader.load();
                Stage dialogWindow = new Stage();
                Scene scene = new Scene(pane);
                dialogWindow.setScene(scene);
                dialogWindow.show();

            } catch (IOException e) {
                e.printStackTrace();
                alertUser("Error: Could not open the admin technician job window");
            }
        }
    }

    public void handleLogOut(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

        // Show the LogIn window
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/Login.fxml"));
            Parent root = loader.load();
            Stage loginStage = new Stage();
            loginStage.setTitle("LogIn");
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
