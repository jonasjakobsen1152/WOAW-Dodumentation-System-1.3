package GUI.Controller;

import BE.Customer;
import BE.Documentation;
import BE.Job;
import BE.User;
import GUI.MODEL.*;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The admin controller is used to control the admin window
 */
public class AdminController implements Initializable {

    public TableView<User> tblUser;
    public TableColumn clmUsername;
    public TableColumn clmRole;
    public AdminModel adminModel;
    public TableView<Customer> tblCustomer;
    public TableColumn clmName;
    public TableColumn clmPhone;
    public TableColumn clmEmail;

    public TableColumn clmTitle;
    public TableView<Job> tblDocument;
    public TextField txtSearch;
    public TableColumn clmAddress;
    public TextField txtSearchJobs;
    public TextField txtSearchUsers;
    @FXML
    private MFXCheckbox checkBoxPDF;
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

    /**
     * This method is used for all the search functions for the different tables in the admin window.
     * The method uses different threads to avoid a slow application.
     */
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
            dialogWindow.initModality(Modality.WINDOW_MODAL);
            dialogWindow.initOwner((((Node)actionEvent.getSource()).getScene().getWindow()));
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
                dialogWindow.initModality(Modality.WINDOW_MODAL);
                dialogWindow.initOwner((((Node)actionEvent.getSource()).getScene().getWindow()));
                dialogWindow.setScene(scene);
                dialogWindow.show();

            } catch (IOException e) {
                e.printStackTrace();
                alertUser("Error: Could not open the update user window");
            }
        }
    }

    /**
     *This method is used to delete the selected user from the user table.
     */
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

    /**
     * Called when an error occurs and is used to show a alert window telling the user what is wrong
     */
    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    /**
     * Used to open the TechnicianJobWindow and sets the selectedJob.
     */
    public void handleShowDocument(ActionEvent actionEvent) {
        if(selectedJob == null) {
            alertUser("Please select a job");
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/View/TechnicianJobWindow.fxml"));
            selectedJob = tblDocument.getSelectionModel().getSelectedItem();
            try {
                technicianJobModel = TechnicianJobModel.getInstance();
                //Sets the selected job to the selected job so that other windows can use it
                documentationModel.setSelectedJob(selectedJob);
                technicianJobModel.setSelectedJob(selectedJob);
                technicianJobModel.showList();

                AnchorPane pane = loader.load();

                Stage dialogWindow = new Stage();
                Scene scene = new Scene(pane);
                dialogWindow.initModality(Modality.WINDOW_MODAL);
                dialogWindow.initOwner((((Node)actionEvent.getSource()).getScene().getWindow()));
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
    }

    /**
     * Opens the customer window
     */
    public void handleCreateCustomer(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/Customer.fxml"));
            AnchorPane pane = loader.load();

            CustomerController customerController = loader.getController();
            CustomerModel.getInstance();

            customerController.removeUpdate();

            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.initModality(Modality.WINDOW_MODAL);
            dialogWindow.initOwner((((Node)actionEvent.getSource()).getScene().getWindow()));
            dialogWindow.setScene(scene);
            dialogWindow.showAndWait();
            showUsersAndDocuments();
        }catch (IOException | SQLException e){
            e.printStackTrace();
            alertUser("Could not open the customer window");
        }
    }

    /**
     * Deletes the selected customer
     */
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
                    showUsersAndDocuments();
                }catch (SQLException e){
                    e.printStackTrace();
                    alertUser("Could not delete customer");
                }
            }
        }
    }

    /**
     * This method is used to open the customer window
     * @param actionEvent
     */
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
                e.printStackTrace();
                alertUser("Could not open the update customer window");
            }
        }


    }

    /**
     * This method is used to show the work of the chosen technician.
     * @param actionEvent
     */
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
                dialogWindow.initModality(Modality.WINDOW_MODAL);
                dialogWindow.initOwner((((Node)actionEvent.getSource()).getScene().getWindow()));
                dialogWindow.setScene(scene);
                dialogWindow.show();

            } catch (IOException e) {
                e.printStackTrace();
                alertUser("Error: Could not open the admin technician job window");
            }
        }
    }

    /**
     * This method is used to log out of the current user and open the login window again.
     * @param actionEvent
     */
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

    /**
     * This method deletes the selected job.
     * @param actionEvent
     */
    public void handleDeleteJob(ActionEvent actionEvent) {
        selectedJob = tblDocument.getSelectionModel().getSelectedItem();
        if (selectedJob == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select a job");
            alert.setHeaderText("Choose a job to delete");
            alert.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete: " + selectedJob.getTitle().concat("?"));
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                try {
                    adminModel.deleteJob(selectedJob);
                    showUsersAndDocuments();
                }catch (SQLException e){
                    alertUser("Could not delete job");
                }
            }
        }
    }

    /**
     * This method is used to print a pdf file
     * @param actionEvent
     */
    public void handlePrintPDF(ActionEvent actionEvent) {
        if(selectedJob == null){
            alertUser("Please select a Job");
        }
        else{
            try {
                adminModel.printPDF(selectedJob);
            } catch (SQLException | IOException e) {
                alertUser("could not print pdf");
                e.printStackTrace();
            }
        }

    }

    /**
     * This method is used to set the strategy for the pdf.
     * @param actionEvent
     */
    public void handlePDFSetStrategy(ActionEvent actionEvent) {
        if(checkBoxPDF.isSelected()){
            adminModel.setPDFStrategy("private");
        }
        else
        {
            adminModel.setPDFStrategy("public");
        }
    }
}
