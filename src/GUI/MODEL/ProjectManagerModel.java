package GUI.MODEL;

import BE.Customer;
import BE.Job;
import BE.User;
import BLL.ProjectManagerManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectManagerModel {
    ProjectManagerManager projectManagerManager;
    private ObservableList<User> technicianToBeViewed;
    private ObservableList<User> salesmenToBeViewed;
    private ObservableList<Customer> customerToBeViewed;
    private ObservableList<Job> documentToBeViewed;
    private static ProjectManagerModel instance;

    private ProjectManagerModel()  {
        projectManagerManager = new ProjectManagerManager();
        technicianToBeViewed = FXCollections.observableArrayList();
        technicianToBeViewed.addAll(projectManagerManager.getAllUsers("Technician"));

        salesmenToBeViewed = FXCollections.observableArrayList();
        salesmenToBeViewed.addAll(projectManagerManager.getAllUsers("Salesmen"));

        try {
            customerToBeViewed = FXCollections.observableArrayList();
            customerToBeViewed.addAll(projectManagerManager.getAllCustomers());
        }catch (SQLException e){
            alertUser("Cant get customers from the database");
        }
        documentToBeViewed = FXCollections.observableArrayList();
        documentToBeViewed.addAll(projectManagerManager.getAllDocuments());
    }

    public static ProjectManagerModel getInstance() throws SQLException {
        if(instance == null){
            instance = new ProjectManagerModel();
        }
        return instance;
    }

    public ObservableList<Customer> getCustomerToBeViewed() {
        return customerToBeViewed;
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    public void searchCustomers(String query) {
        //List<Customer> searchResults = projectManagerManager.searchCustomer(query);
        customerToBeViewed.clear();
        //customerToBeViewed.addAll(searchResults);
    }

    public ArrayList<User> getTechnicianList(){
        return projectManagerManager.getAllUsers("Technician");
    }

    public ArrayList<User> getSalesmenList(){
        return projectManagerManager.getAllUsers("Salesmen");
    }
    public ArrayList<Customer> getCustomerList() throws SQLException {
        return projectManagerManager.getAllCustomers();
    }
    public ObservableList<User> getTechnicianToBeViewed(){
        return technicianToBeViewed;
    }

    public ObservableList<User> getSalesmenToBeViewed() {
        return salesmenToBeViewed;
    }

    public void deleteUser(User selectedUser) throws SQLException {
        projectManagerManager.deleteUser(selectedUser);
        showList();
    }

    public void showList() throws SQLException {
        getTechnicianToBeViewed().clear();
        getTechnicianToBeViewed().addAll(projectManagerManager.getAllUsers("Technician"));

        getSalesmenToBeViewed().clear();
        getSalesmenToBeViewed().addAll(projectManagerManager.getAllUsers("Salesmen"));

        getDocumentsToBeViewed().clear();
        getDocumentsToBeViewed().addAll(projectManagerManager.getAllDocuments());

        getCustomerToBeViewed().clear();
        getCustomerToBeViewed().addAll(projectManagerManager.getAllCustomers());
    }

    public ObservableList<Job> getDocumentsToBeViewed() {
        return documentToBeViewed;
    }

    public ArrayList<Job> getDocumentList(){return projectManagerManager.getAllDocuments();}

    public void deleteDocument(Job selectedDocument) throws SQLException {
        projectManagerManager.deletedDocument(selectedDocument);
        showList();
    }

    public void deleteCustomer(Customer selectedCustomer) throws SQLException {
        projectManagerManager.deleteCustomer(selectedCustomer);
        showList();
    }
}
