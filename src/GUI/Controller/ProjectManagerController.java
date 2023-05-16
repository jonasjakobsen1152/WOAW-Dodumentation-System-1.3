package GUI.Controller;

import BE.*;
import GUI.MODEL.*;
import io.github.palexdev.materialfx.controls.MFXButton;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

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
    public MFXButton btnReadJobs;
    public MFXButton btnEditUser;
    public TextField txtFilterTechnicians;
    public TextField txtFilterSalesmen;
    public TextField txtFilterJobs;
    public MFXButton btnPublicPDF;
    public MFXButton btnShowWork;
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
    CreateUpdateUserModel createUpdateUserModel;
    CreateUpdateJobModel createUpdateJobModel;
    CustomerModel customerModel;
    TechnicianJobModel technicianJobModel;
    LoginModel loginModel;
    private User selectedTechnician;
    private Customer selectedCustomer;
    private Job selectedDocument;
    public TableView<User> tblUser;

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

        });
        tblShowDocument.setOnMouseClicked(event -> {
            selectedDocument = tblShowDocument.getSelectionModel().getSelectedItem();
        });
        searchListeners();
    }

    public void searchListeners(){
        txtFilterTechnicians.textProperty().addListener((observable, oldValue, newValue) ->{
            Runnable task = () -> projectManagerModel.searchTechnicians(newValue);
            Thread thread = new Thread(task);
            thread.start();
        });

        txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            Runnable task = () -> projectManagerModel.searchCustomers(newValue);
            Thread thread = new Thread(task);
            thread.start();
        });

        txtFilterSalesmen.textProperty().addListener((observable, oldValue, newValue) ->{
            Runnable task = () -> projectManagerModel.searchSalesmen(newValue);
            Thread thread = new Thread(task);
            thread.start();
        });

        txtFilterJobs.textProperty().addListener((observable, oldValue, newValue) ->{
            Runnable task = () -> projectManagerModel.searchJobs(newValue);
            Thread thread = new Thread(task);
            thread.start();
        });
    }

    private void showDocument() {
        clmShowDocument.setCellValueFactory(new PropertyValueFactory<Job, String>("title"));
        tblShowDocument.setItems(projectManagerModel.getDocumentsToBeViewed());
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
        createUpdateJobModel.setTechnician(selectedTechnician);

        if (selectedTechnician == null) {
            alertUser("Choose a technician");
        }else{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/CreateUpdateJob.fxml"));
        try {
            createUpdateJobModel.setTechnician(selectedTechnician);
            AnchorPane pane = loader.load();

            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.setScene(scene);
            dialogWindow.show();
        }
        catch (IOException e) {
            e.printStackTrace();
            alertUser("Error: Could not open the job creation window");
        }
        }

    }

    public void handleDeleteWork(ActionEvent actionEvent) {
        selectedDocument = tblShowDocument.getSelectionModel().getSelectedItem();
        if (selectedDocument == null){
            alertUser("Select a job");
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete: " + selectedDocument.getTitle().concat("?"));
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK){
                try {

                    projectManagerModel.deleteDocument(selectedDocument);
                    showDocument();
                }catch (SQLException e){
                    alertUser("Could not delete work");
                    e.printStackTrace();
                }
            }
        }
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
                try {


                    projectManagerModel.deleteUser(selectedUser);
                    //updateUserModel();
                    showTechnician();
                }catch (SQLException e){
                    alertUser("Could not delete technician");
                }
            }
        }
    }

    public void handleDeleteCustomer(ActionEvent actionEvent) {
        selectedCustomer = tblShowCustomers.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select a user");
            alert.setHeaderText("Choose a user to delete");
            alert.show();
        }  else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete: " + selectedCustomer.getName().concat("?"));
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                try{
                projectManagerModel.deleteCustomer(selectedCustomer);

                    showCustomer();
                } catch (SQLException e) {
                    alertUser("Could not delete customer");
                    e.printStackTrace();
                }
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
                try {


                    projectManagerModel.deleteUser(selectedUser);
                    //updateUserModel();
                    showSalesmen();
                }catch (SQLException e){
                    alertUser("Could not delete salesmen");
                    e.printStackTrace();
                }
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

    private void showCustomer() throws SQLException {
        clmShowCustomers.setCellValueFactory(new PropertyValueFactory<Customer, String>("Name"));
        tblShowCustomers.setItems(projectManagerModel.getCustomerToBeViewed());
    }

    public void handleOpenCustomer(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/Customer.fxml"));
            AnchorPane pane = loader.load();


            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.setScene(scene);
            dialogWindow.show();
        }catch (IOException e){
            e.printStackTrace();
            alertUser("Could not open the customer window");
        }
    }

    public void handleShowJobs(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/TechnicianJobWindow.fxml"));
        selectedDocument = tblShowDocument.getSelectionModel().getSelectedItem();
        try {
            technicianJobModel = TechnicianJobModel.getInstance();
            documentationModel.setSelectedJob(selectedDocument);
            technicianJobModel.setSelectedJob(selectedDocument);
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

    public void handleShowTechnicians(ActionEvent actionEvent) {
        setVisibleFalse();
        tblShowTechnicians.setVisible(true);
        btnDeleteTechnician.setVisible(true);
        btnCreateTechnician.setVisible(true);
        btnEditUser.setVisible(true);
        btnAddWork.setVisible(true);
        txtFilterTechnicians.setVisible(true);
        btnShowWork.setVisible(true);
    }

    public void handleShowCustomers(ActionEvent actionEvent) {
        setVisibleFalse();
        tblShowCustomers.setVisible(true);
        btnDeleteCustomer.setVisible(true);
        btnCreateCustomer.setVisible(true);
        txtFilter.setVisible(true);
    }

    public void handleShowSalesmen(ActionEvent actionEvent) {
        setVisibleFalse();
        tblShowSalesmen.setVisible(true);
        btnCreateSalesmen.setVisible(true);
        btnDeleteSalesmen.setVisible(true);
        btnEditUser.setVisible(true);
        txtFilterSalesmen.setVisible(true);

    }

    public void handleShowJob(ActionEvent actionEvent) {
        setVisibleFalse();
        tblShowDocument.setVisible(true);
        btnSendPDF.setVisible(true);
        btnShowJobs.setVisible(true);
        txtFilterJobs.setVisible(true);
        btnPublicPDF.setVisible(true);
    }

    public void setVisibleFalse(){
        tblShowCustomers.setVisible(false);
        tblShowDocument.setVisible(false);
        tblShowSalesmen.setVisible(false);
        tblShowTechnicians.setVisible(false);

        btnCreateCustomer.setVisible(false);
        btnCreateSalesmen.setVisible(false);
        btnDeleteTechnician.setVisible(false);
        btnDeleteSalesmen.setVisible(false);
        btnDeleteWork.setVisible(false);
        btnCreateTechnician.setVisible(false);
        btnAddWork.setVisible(false);
        btnPublicPDF.setVisible(false);
        btnReadJobs.setVisible(false);
        btnSendPDF.setVisible(false);
        btnEditUser.setVisible(false);
        btnShowJobs.setVisible(false);
        btnDeleteCustomer.setVisible(false);
        btnShowWork.setVisible(false);


        txtFilter.setVisible(false);
        txtFilterTechnicians.setVisible(false);
        txtFilterJobs.setVisible(false);
        txtFilterSalesmen.setVisible(false);

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

    public void handlePrintPDF(ActionEvent actionEvent) {
        technicianJobModel.setSelectedJob(selectedDocument);
        technicianJobModel.showList();
        ArrayList<Documentation> allNotes = new ArrayList<>();
        ArrayList<JobImage> allImages = new ArrayList<>();

        allNotes.addAll(technicianJobModel.getDocumentationsToBeViewed());
        allImages.addAll(technicianJobModel.getImagesToBeViewed());

        try {
            projectManagerModel.printPDF(allNotes, allImages);
        } catch (IOException e) {
            e.printStackTrace();
            alertUser("Could not print pdf");
        }
    }

    public void handleShowWork(ActionEvent actionEvent) {
        selectedUser = tblShowTechnicians.getSelectionModel().getSelectedItem();
        if(selectedUser == null || !selectedUser.getRole().equals("Technician")){
            alertUser("Select a technician from the user table");
        }else{
            try {
            projectManagerModel.setSelectedUser(selectedUser);
            projectManagerModel.showList();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/View/ProjectTechnicianJobWindow.fxml"));

                AnchorPane pane = loader.load();
                Stage dialogWindow = new Stage();
                Scene scene = new Scene(pane);
                dialogWindow.setScene(scene);
                dialogWindow.show();

            } catch (SQLException | IOException e) {
                e.printStackTrace();
                alertUser("Error: Could not open the project technician job window");
            }
        }
    }

    public void handlePrintPublicPDF(ActionEvent actionEvent) {
    }
}
