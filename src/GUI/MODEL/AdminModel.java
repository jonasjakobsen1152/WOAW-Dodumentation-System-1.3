package GUI.MODEL;

import BE.Customer;
import BE.Documentation;
import BE.Job;
import BE.User;
import BLL.AdminManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminModel {
    AdminManager adminManager;
    private ObservableList<User> usersToBeViewed;
    private ObservableList<Customer> customerToBeViewed;
    private ObservableList<Job> documentsToBeViewed;
    private User selectedUser;
    private static AdminModel instance;



    private AdminModel() throws SQLException {
        adminManager = new AdminManager();
        usersToBeViewed = FXCollections.observableArrayList();
        usersToBeViewed.addAll(adminManager.getAllUsers());

        customerToBeViewed = FXCollections.observableArrayList();
        customerToBeViewed.addAll(adminManager.getAllCustomer());

        documentsToBeViewed = FXCollections.observableArrayList();
        documentsToBeViewed.addAll(adminManager.getAllJobs());

    }
    public static AdminModel getInstance() throws SQLException {
        if(instance == null){
            instance = new AdminModel();
        }
        return instance;
    }

    public ArrayList<User> getAdminList() throws SQLException {
        return adminManager.getAllUsers();
    }

    public ArrayList<Customer> getCustomerList() throws SQLException {
        return adminManager.getAllCustomer();
    }

    public ObservableList<User> getUsersToBeViewed(){return usersToBeViewed;}

    public ObservableList<Customer> getCustomerToBeViewed(){
        return customerToBeViewed;
    }

    public ArrayList<User> getAllUsers() throws SQLException {
        return adminManager.getAllUsers();
    }

    public void deleteUser(User selectedUser) throws SQLException {
        adminManager.deleteUser(selectedUser);
        showList();
    }

    public void deleteCustomer(Customer selectedCustomer) throws SQLException {
        adminManager.deleteCustomer(selectedCustomer);
        showList();
    }

    public ObservableList<Job> getDocumentsToBeViewed() {
        return documentsToBeViewed;
    }

    public void searchCustomers(String query) {
        List<Customer> searchResults = adminManager.searchCustomers(query);
        customerToBeViewed.clear();
        customerToBeViewed.addAll(searchResults);
    }

    public void searchUsers(String query) {
        List<User> searchResults = adminManager.searchUsers(query);
        usersToBeViewed.clear();
        usersToBeViewed.addAll(searchResults);
    }

    public void searchJobs(String query) {
        List<Job> searchResults = adminManager.searchJobs(query);
        documentsToBeViewed.clear();
        documentsToBeViewed.addAll(searchResults);
    }

    public void showList() {
        try {
            getUsersToBeViewed().clear();
            getUsersToBeViewed().addAll(adminManager.getAllUsers());

            getCustomerToBeViewed().clear();
            getCustomerToBeViewed().addAll(adminManager.getAllCustomer());

            getDocumentsToBeViewed().clear();
            getDocumentsToBeViewed().addAll(adminManager.getAllJobs());

        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Could not show list");
            alert.setHeaderText("Could not show list");
            alert.showAndWait();
        }

    }
    public User getSelectedUser() {
        return selectedUser;
    }
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void deleteJob(Job selectedJob) throws SQLException {
        adminManager.deleteJob(selectedJob);
        showList();
    }

    public void printPDF(Job selectedJob) {
        adminManager.printPDF(selectedJob);
    }
}
