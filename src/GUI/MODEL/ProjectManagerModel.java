package GUI.MODEL;

import BE.*;
import BLL.ProjectManagerManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    private Customer selectedCustomer;
    private Job selectedJob;

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

    public void searchCustomers(String query) {
        List<Customer> searchResults = projectManagerManager.searchCustomer(query);
        customerToBeViewed.clear();
        customerToBeViewed.addAll(searchResults);
    }

    public void searchTechnicians(String query) {
        List<User> searchResults = projectManagerManager.searchTechnician(query);
        technicianToBeViewed.clear();
        technicianToBeViewed.addAll(searchResults);
    }

    public void searchSalesmen(String query) {
        List<User> searchResults = projectManagerManager.searchSalesmen(query);
        salesmenToBeViewed.clear();
        salesmenToBeViewed.addAll(searchResults);
    }
    public void searchJobs(String query) {
        List<Job> searchResults = projectManagerManager.searchJobs(query);
        documentToBeViewed.clear();
        documentToBeViewed.addAll(searchResults);
    }

    public void printPDF(ArrayList<Documentation> allNotes, ArrayList<JobImage> allImages) throws IOException {
        projectManagerManager.printPDF(allNotes,allImages);
    }
    public void deleteJob(Job selectedJob) throws SQLException {
        projectManagerManager.deleteJob(selectedJob);
        showList();
    }

    public void setPDFStrategy(String privacy) {
        projectManagerManager.setPDFStrategy(privacy);

    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
    public Customer getSelectedCustomer(){
        return selectedCustomer;
    }

    public void addTechToJob(User selectedTechnician) throws SQLException {
        projectManagerManager.addTechToJob(selectedTechnician, selectedJob);
        showList();
    }

    public void setSelectedJob(Job selectedJob) {
        this.selectedJob = selectedJob;
    }
}
