package GUI.MODEL;

import BE.Customer;
import BE.Job;
import BE.User;
import BLL.ProjectManagerManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    private ProjectManagerModel() throws SQLException {
        projectManagerManager = new ProjectManagerManager();
        technicianToBeViewed = FXCollections.observableArrayList();
        technicianToBeViewed.addAll(projectManagerManager.getAllUsers("Technician"));

        salesmenToBeViewed = FXCollections.observableArrayList();
        salesmenToBeViewed.addAll(projectManagerManager.getAllUsers("Salesmen"));

        customerToBeViewed = FXCollections.observableArrayList();
        customerToBeViewed.addAll(projectManagerManager.getAllCustomers());

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
    public ObservableList<User> getTechnicianToBeViewed(){
        return technicianToBeViewed;
    }

    public ObservableList<User> getSalesmenToBeViewed() {
        return salesmenToBeViewed;
    }

    public void deleteUser(User selectedUser) {
        projectManagerManager.deleteUser(selectedUser);
        showList();
    }

    public void showList() {
        getTechnicianToBeViewed().clear();
        getTechnicianToBeViewed().addAll(projectManagerManager.getAllUsers("Technician"));

        getSalesmenToBeViewed().clear();
        getSalesmenToBeViewed().addAll(projectManagerManager.getAllUsers("Salesmen"));

        getDocumentsToBeViewed().clear();
        getDocumentsToBeViewed().addAll(projectManagerManager.getAllDocuments());
    }

    public ObservableList<Job> getDocumentsToBeViewed() {
        return documentToBeViewed;
    }

    public ArrayList<Job> getDocumentList(){return projectManagerManager.getAllDocuments();}

}
