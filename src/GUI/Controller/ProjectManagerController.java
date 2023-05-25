package GUI.Controller;

import BE.*;
import GUI.MODEL.*;
import io.github.palexdev.materialfx.controls.MFXButton;
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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.GRAY;

/**
 * This class is used to control the project manager window
 */
public class ProjectManagerController implements Initializable {
    public MFXButton btnAddWork;
    public MFXButton btnDeleteWork;
    public MFXButton btnCreateTechnician;
    public MFXButton btnDeleteTechnician;
    public MFXButton btnCreateSalesmen;
    public MFXButton btnDeleteCustomer;
    public MFXButton btnCreateCustomer;
    public MFXButton btnDeleteSalesmen;
    public MFXButton btnShowJobs;
    public MFXButton btnSendPDF;
    public MFXButton btnEditUser;
    public TextField txtFilterTechnicians;
    public TextField txtFilterSalesmen;
    public TextField txtFilterJobs;
    public MFXButton btnShowWork;
    public MFXButton btnDeleteJob;
    public MFXCheckbox checkBoxPDF;
    public Text txtPDFText;
    public MFXButton btnEditCustomer;
    public MFXButton btnAddTechnicianToJob;
    public MFXButton btnTechnician;
    public MFXButton btnCustomer;
    public MFXButton btnSalesmen;
    public MFXButton btnJobs;
    @FXML
    private TextField txtFilter;
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
    private TableView<Job> tblShowDocument;
    @FXML
    private TableColumn clmShowDocument;
    public ProjectManagerModel projectManagerModel;
    public DocumentationModel documentationModel;
    private User selectedUser;
    CreateUpdateJobModel createUpdateJobModel;
    CustomerModel customerModel;
    TechnicianJobModel technicianJobModel;
    LoginModel loginModel;
    TechnicianModel technicianModel;
    private User selectedTechnician;
    private Customer selectedCustomer;
    private Job selectedDocument;
    public Job selectedJob;

    public ProjectManagerController() throws SQLException {
        try {
            projectManagerModel = ProjectManagerModel.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createUpdateJobModel = CreateUpdateJobModel.getInstance();
        customerModel = CustomerModel.getInstance();
        documentationModel = DocumentationModel.getInstance();
        technicianJobModel = TechnicianJobModel.getInstance();
        loginModel = LoginModel.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showTechnician();
        showSalesmen();
        handleShowTechnicians(new ActionEvent());
        try {
            showCustomer();
        } catch (SQLException e) {
            e.printStackTrace();
            alertUser("Experienced a problem while loading Customers up");
        }
        showDocument();
        lambdaMouseClick();
        searchListeners();
    }

    /**
     * Used to check if an object is clicked in the tables
     */
    public void lambdaMouseClick(){
        tblShowTechnicians.setOnMouseClicked(event -> {
            selectedUser = tblShowTechnicians.getSelectionModel().getSelectedItem();
            loginModel.setLoggedInUser(selectedUser);
            tblShowSalesmen.getSelectionModel().clearSelection();

        });
        tblShowSalesmen.setOnMouseClicked(event -> {
            selectedUser = tblShowSalesmen.getSelectionModel().getSelectedItem();
            tblShowTechnicians.getSelectionModel().clearSelection();
            tblShowDocument.getSelectionModel().clearSelection();
            tblShowCustomers.getSelectionModel().clearSelection();
        });
        tblShowCustomers.setOnMouseClicked(event -> {
            selectedCustomer = tblShowCustomers.getSelectionModel().getSelectedItem();
            projectManagerModel.setSelectedCustomer(selectedCustomer);

        });
        tblShowDocument.setOnMouseClicked(event -> {
            selectedDocument = tblShowDocument.getSelectionModel().getSelectedItem();
            projectManagerModel.setSelectedJob(selectedDocument);
        });
    }

    /**
     * Used for the different search functions so the user can search through the different tables.
     */
    public void searchListeners() {
        txtFilterTechnicians.textProperty().addListener((observable, oldValue, newValue) -> {
            Runnable task = () -> projectManagerModel.searchTechnicians(newValue);
            Thread thread = new Thread(task);
            thread.start();
        });

        txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            Runnable task = () -> projectManagerModel.searchCustomers(newValue);
            Thread thread = new Thread(task);
            thread.start();
        });

        txtFilterSalesmen.textProperty().addListener((observable, oldValue, newValue) -> {
            Runnable task = () -> projectManagerModel.searchSalesmen(newValue);
            Thread thread = new Thread(task);
            thread.start();
        });

        txtFilterJobs.textProperty().addListener((observable, oldValue, newValue) -> {
            Runnable task = () -> projectManagerModel.searchJobs(newValue);
            Thread thread = new Thread(task);
            thread.start();
        });
    }

    private void showDocument() {
        clmShowDocument.setCellValueFactory(new PropertyValueFactory<Job, String>("title"));
        tblShowDocument.setItems(projectManagerModel.getDocumentsToBeViewed());
    }

    /**
     * Used to open the create update user window where the user can create new user.
     * @param actionEvent
     */
    public void handleOpenCreateUser(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/CreateUpdateUser.fxml"));
        try {
            AnchorPane pane = loader.load();
            CreateUpdateUserController createUpdateUserController = loader.getController();

            //Used to remove the update button in the create update user window.
            createUpdateUserController.removeUpdate();

            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.initModality(Modality.WINDOW_MODAL);
            dialogWindow.initOwner((((Node)actionEvent.getSource()).getScene().getWindow()));
            dialogWindow.setScene(scene);
            dialogWindow.show();
        } catch (IOException e) {
            alertUser("Error: Could not open the create user window");
        }
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    /**
     * Used to open the create update job window where the user can create a new job
     * @param actionEvent
     */
    public void handleAddWork(ActionEvent actionEvent) {
        selectedTechnician = tblShowTechnicians.getSelectionModel().getSelectedItem();
        if (selectedTechnician == null) {
            alertUser("Choose a technician");
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/View/CreateUpdateJob.fxml"));
            try {
                createUpdateJobModel.setTechnician(selectedTechnician);
                AnchorPane pane = loader.load();

                Stage dialogWindow = new Stage();
                Scene scene = new Scene(pane);
                dialogWindow.initModality(Modality.WINDOW_MODAL);
                dialogWindow.initOwner((((Node)actionEvent.getSource()).getScene().getWindow()));
                dialogWindow.setScene(scene);
                dialogWindow.show();
            } catch (IOException e) {
                e.printStackTrace();
                alertUser("Error: Could not open the job creation window");
            }
        }

    }

    /**
     * Used to delete the selected job
     * @param actionEvent
     */
    public void handleDeleteWork(ActionEvent actionEvent) {
        selectedDocument = tblShowDocument.getSelectionModel().getSelectedItem();
        if (selectedDocument == null) {
            alertUser("Select a job");
        } else {
            //Warns the user about the current action
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete: " + selectedDocument.getTitle().concat("?"));
            Optional<ButtonType> action = alert.showAndWait();
            //If ok is clicked then proceed with deleting the job
            if (action.get() == ButtonType.OK) {
                try {
                    projectManagerModel.deleteDocument(selectedDocument);
                    showDocument();
                } catch (SQLException e) {
                    alertUser("Could not delete work");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Deletes the selected technician
     * @param actionEvent
     */
    public void handleDeleteTechnician(ActionEvent actionEvent) {
        selectedUser = tblShowTechnicians.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select a user");
            alert.setHeaderText("Choose a user to delete");
            alert.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete: " + selectedUser.getUsername().concat("?"));
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                try {
                    projectManagerModel.deleteUser(selectedUser);
                    showTechnician();
                } catch (SQLException e) {
                    alertUser("Could not delete technician");
                }
            }
        }
    }

    /**
     * Deletes the selected customer
     * @param actionEvent
     */
    public void handleDeleteCustomer(ActionEvent actionEvent) {
        selectedCustomer = tblShowCustomers.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select a user");
            alert.setHeaderText("Choose a user to delete");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete: " + selectedCustomer.getName().concat("?"));
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                try {
                    projectManagerModel.deleteCustomer(selectedCustomer);
                    showCustomer();
                } catch (SQLException e) {
                    alertUser("Could not delete customer");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Deletes the selected salesmen
     * @param actionEvent
     */
    public void handleDeleteSalesmen(ActionEvent actionEvent) {
        selectedUser = tblShowSalesmen.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select a user");
            alert.setHeaderText("Choose a user to delete");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete: " + selectedUser.getUsername().concat("?"));
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                try {
                    projectManagerModel.deleteUser(selectedUser);
                    showSalesmen();
                } catch (SQLException e) {
                    alertUser("Could not delete salesmen");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Opens the create update user window where the user can edit/update a user.
     * @param actionEvent
     */
    public void handleOpenUpdateUser(ActionEvent actionEvent) {
        if (selectedUser == null) {
            alertUser("Please select a user to edit");
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CreateUpdateUser.fxml"));
                AnchorPane pane = loader.load();

                CreateUpdateUserController createUpdateUserController = loader.getController();
                //Removes the create button in the create update user window.
                createUpdateUserController.setupUpdate(selectedUser);

                Stage dialogWindow = new Stage();
                Scene scene = new Scene(pane);
                dialogWindow.initModality(Modality.WINDOW_MODAL);
                dialogWindow.initOwner((((Node)actionEvent.getSource()).getScene().getWindow()));
                dialogWindow.setScene(scene);
                dialogWindow.show();
            } catch (IOException e) {
                alertUser("Error: Could not open the update user window");
                e.printStackTrace();
            }
        }
    }

    /**
     * Used to show all the technicians in the technician table
     */
    private void showTechnician() {
        clmShowTechnicians.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        tblShowTechnicians.setItems(projectManagerModel.getTechnicianToBeViewed());
    }

    /**
     * Used to show all the salesmen in the salesmen table
     */
    private void showSalesmen() {
        clmShowSalesmen.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        tblShowSalesmen.setItems(projectManagerModel.getSalesmenToBeViewed());
    }

    /**
     * Used to show all the customers in the customers table
     */
    private void showCustomer() throws SQLException {
        clmShowCustomers.setCellValueFactory(new PropertyValueFactory<Customer, String>("Name"));
        tblShowCustomers.setItems(projectManagerModel.getCustomerToBeViewed());
    }

    /**
     * Used to open the customer window
     * @param actionEvent
     */
    public void handleOpenCustomer(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/Customer.fxml"));
            AnchorPane pane = loader.load();


            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.initModality(Modality.WINDOW_MODAL);
            dialogWindow.initOwner((((Node)actionEvent.getSource()).getScene().getWindow()));
            dialogWindow.setScene(scene);
            dialogWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
            alertUser("Could not open the customer window");
        }
    }

    /**
     * Used to open the technician job window where the user can see information about the selected job
     * @param actionEvent
     */
    public void handleShowJobs(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/TechnicianJobWindow.fxml"));
        if(selectedDocument == null){
            alertUser("Please select a document");
        } else {
        selectedDocument = tblShowDocument.getSelectionModel().getSelectedItem();
        try {
            technicianJobModel = TechnicianJobModel.getInstance();
            documentationModel.setSelectedJob(selectedDocument);
            technicianJobModel.setSelectedJob(selectedDocument);
            technicianJobModel.showList();

            AnchorPane pane = loader.load();

            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.initModality(Modality.WINDOW_MODAL);
            dialogWindow.initOwner((((Node)actionEvent.getSource()).getScene().getWindow()));
            dialogWindow.setScene(scene);
            dialogWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
            alertUser("Error: Could not open the technician job window");
        } catch (SQLException e) {
            e.printStackTrace();
            alertUser("Could not get the documentation list from the database");
        }
        }
    }

    /**
     * Used to show the technician table and the buttons that is used with that table.
     * @param actionEvent
     */
    public void handleShowTechnicians(ActionEvent actionEvent) {
        setVisibleFalse();
        String clickedButtonStyle = "-fx-background-color: #fa9f1c;";
        tblShowTechnicians.setVisible(true);
        btnDeleteTechnician.setVisible(true);
        btnCreateTechnician.setVisible(true);
        btnEditUser.setVisible(true);
        btnAddWork.setVisible(true);
        txtFilterTechnicians.setVisible(true);
        btnShowWork.setVisible(true);
        btnTechnician.getStylesheets().clear();
        btnTechnician.setStyle(clickedButtonStyle);
    }

    /**
     * Used to show the customer table and the buttons that is used with that table.
     * @param actionEvent
     */
    public void handleShowCustomers(ActionEvent actionEvent) {
        setVisibleFalse();
        String clickedButtonStyle = "-fx-background-color: #fa9f1c;";
        tblShowCustomers.setVisible(true);
        btnDeleteCustomer.setVisible(true);
        btnCreateCustomer.setVisible(true);
        btnEditCustomer.setVisible(true);
        txtFilter.setVisible(true);
        btnCustomer.setStyle(clickedButtonStyle);
    }

    /**
     * Used to show the salesmen table and the buttons that is used with that table.
     * @param actionEvent
     */
    public void handleShowSalesmen(ActionEvent actionEvent) {
        setVisibleFalse();
        String clickedButtonStyle = "-fx-background-color: #fa9f1c;";
        tblShowSalesmen.setVisible(true);
        btnCreateSalesmen.setVisible(true);
        btnDeleteSalesmen.setVisible(true);
        btnEditUser.setVisible(true);
        txtFilterSalesmen.setVisible(true);
        btnSalesmen.setStyle(clickedButtonStyle);

    }

    /**
     * Used to show the job table and the buttons that is used with that table.
     * @param actionEvent
     */
    public void handleShowJob(ActionEvent actionEvent) {
        setVisibleFalse();
        String clickedButtonStyle = "-fx-background-color: #fa9f1c;";
        tblShowDocument.setVisible(true);
        btnSendPDF.setVisible(true);
        btnShowJobs.setVisible(true);
        txtFilterJobs.setVisible(true);
        btnDeleteJob.setVisible(true);
        btnAddTechnicianToJob.setVisible(true);
        checkBoxPDF.setVisible(true);
        txtPDFText.setVisible(true);
        btnJobs.setStyle(clickedButtonStyle);
    }

    /**
     * This method is used to hide most of the buttons and all of the tables.
     * it is used in combination with the methods handleShowJob, handleShowTechnicians, handleShowSalesmen and handleShowCustomer
     */
    public void setVisibleFalse() {
        String normalButtonStyle = "-fx-background-color: D65A31;";

        //Hides all of the tables
        tblShowCustomers.setVisible(false);
        tblShowDocument.setVisible(false);
        tblShowSalesmen.setVisible(false);
        tblShowTechnicians.setVisible(false);

        //Hides all of the buttons
        btnCreateCustomer.setVisible(false);
        btnCreateSalesmen.setVisible(false);
        btnDeleteTechnician.setVisible(false);
        btnDeleteSalesmen.setVisible(false);
        btnDeleteWork.setVisible(false);
        btnCreateTechnician.setVisible(false);
        btnAddWork.setVisible(false);
        btnSendPDF.setVisible(false);
        btnEditUser.setVisible(false);
        btnShowJobs.setVisible(false);
        btnDeleteCustomer.setVisible(false);
        btnShowWork.setVisible(false);
        btnDeleteJob.setVisible(false);
        btnEditCustomer.setVisible(false);
        btnAddTechnicianToJob.setVisible(false);
        btnTechnician.setStyle(normalButtonStyle);
        btnCustomer.setStyle(normalButtonStyle);
        btnJobs.setStyle(normalButtonStyle);
        btnSalesmen.setStyle(normalButtonStyle);

        checkBoxPDF.setVisible(false);

        //Hides all of the Search fields
        txtPDFText.setVisible(false);
        txtFilter.setVisible(false);
        txtFilterTechnicians.setVisible(false);
        txtFilterJobs.setVisible(false);
        txtFilterSalesmen.setVisible(false);

    }

    /**
     * Used to log out the logged in user and open the login window again.
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
            alertUser("Could not log out");
        }
    }

    /**
     * Used to change the strategy for the pdf printing based on if the checkbox is checked or not.
     * @param actionEvent
     */
    public void handleSetPDFStrategy(ActionEvent actionEvent) {
        if(checkBoxPDF.isSelected()){
            projectManagerModel.setPDFStrategy("private");
        }
        else
        {
            projectManagerModel.setPDFStrategy("public");
        }

    }

    /**
     * Used to print a new pdf
     * @param actionEvent
     */
    public void handlePrintPDF(ActionEvent actionEvent) {
        if(selectedDocument == null){
            alertUser("Please select a job to print");
        }
        else {
            technicianJobModel.setSelectedJob(selectedDocument);
            technicianJobModel.showList();
            ArrayList<Documentation> allNotes = new ArrayList<>();
            ArrayList<JobImage> allImages = new ArrayList<>();

            //Gets all the notes and images from the selected job
            allNotes.addAll(technicianJobModel.getDocumentationsToBeViewed());
            allImages.addAll(technicianJobModel.getImagesToBeViewed());

            try {
                //Prints the pdf based on the notes and images.
                projectManagerModel.printPDF(allNotes, allImages);
            } catch (IOException e) {
                e.printStackTrace();
                alertUser("Could not print pdf");
            }
        }
    }

    /**
     * Used to open the project technician job window
     * @param actionEvent
     */
    public void handleShowWork(ActionEvent actionEvent) {
        selectedUser = tblShowTechnicians.getSelectionModel().getSelectedItem();
        if (selectedUser == null || !selectedUser.getRole().equals("Technician")) {
            alertUser("Select a technician from the user table");
        } else {
            try {
                technicianModel = TechnicianModel.getInstance();
                technicianModel.setSelectedUser(selectedUser);

                technicianModel.showList();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/GUI/View/ProjectTechnicianJobWindow.fxml"));

                AnchorPane pane = loader.load();
                Stage dialogWindow = new Stage();
                Scene scene = new Scene(pane);
                dialogWindow.initModality(Modality.WINDOW_MODAL);
                dialogWindow.initOwner((((Node)actionEvent.getSource()).getScene().getWindow()));
                dialogWindow.setScene(scene);
                dialogWindow.show();

            } catch (IOException e) {
                e.printStackTrace();
                alertUser("Error: Could not open the project technician job window");
            }
        }
    }

    /**
     * Used to delete the selected job from the job table.
     * @param actionEvent
     */
    public void handleDeleteJob(ActionEvent actionEvent) {
        selectedJob = tblShowDocument.getSelectionModel().getSelectedItem();
        if (selectedJob == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select a Job");
            alert.setHeaderText("Choose a Job to delete");
            alert.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete: " + selectedJob.getTitle().concat("?"));
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                try {
                    projectManagerModel.deleteJob(selectedJob);
                } catch (SQLException e) {
                    alertUser("Could not delete the job");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Used to open the customer window where the user can edit/update customers.
     * @param actionEvent
     */
    public void handleOpenEditCustomer(ActionEvent actionEvent) {
        selectedCustomer = tblShowCustomers.getSelectionModel().getSelectedItem();
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

                    //Hides the create button in the customer window.
                    customerController.setupUpdate(selectedCustomer);

                    Stage dialogWindow = new Stage();
                    Scene scene = new Scene(pane);
                    dialogWindow.initModality(Modality.WINDOW_MODAL);
                    dialogWindow.initOwner((((Node) actionEvent.getSource()).getScene().getWindow()));
                    dialogWindow.setScene(scene);
                    dialogWindow.showAndWait();

                } catch (IOException e) {
                    e.printStackTrace();
                    alertUser("Error: Could not open the update customer window");
                }
            }catch (SQLException e){
                alertUser("Could not open the update customer window");
            }
        }
    }

    /**
     * Used to add technicians to a job
     * @param actionEvent
     */
    public void handleAddTechToJob(ActionEvent actionEvent) {
        selectedJob = tblShowDocument.getSelectionModel().getSelectedItem();
        if(selectedJob == null){
            alertUser("Select a Job to add a technician to it");
        }else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/View/AddTechToJob.fxml"));
            try {
                AnchorPane pane = loader.load();

                Stage dialogWindow = new Stage();
                Scene scene = new Scene(pane);
                dialogWindow.initModality(Modality.WINDOW_MODAL);
                dialogWindow.initOwner((((Node) actionEvent.getSource()).getScene().getWindow()));
                dialogWindow.setScene(scene);
                dialogWindow.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
                alertUser("Error: Could not open the add technician to job window");
            }
        }
    }
}
