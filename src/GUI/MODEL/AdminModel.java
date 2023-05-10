package GUI.MODEL;

import BE.Customer;
import BE.Job;
import BE.User;
import BLL.AdminManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminModel {
    AdminManager adminManager;
    private ObservableList<User> usersToBeViewed;
    private ObservableList<Customer> customerToBeViewed;
    private ObservableList<Job> documentsToBeViewed;
    private static AdminModel instance;

    private AdminModel() throws SQLException {
        adminManager = new AdminManager();
        usersToBeViewed = FXCollections.observableArrayList();
        usersToBeViewed.addAll(adminManager.getAllUsers());

        customerToBeViewed = FXCollections.observableArrayList();
        customerToBeViewed.addAll(adminManager.getAllCustomer());

        documentsToBeViewed = FXCollections.observableArrayList();
        documentsToBeViewed.addAll(adminManager.getAllDocuments());
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

    public ArrayList<Customer> getAllCustomer() throws SQLException {
        return adminManager.getAllCustomer();
    }

    public ArrayList<Job> getAllDocuments() throws SQLException {return  adminManager.getAllDocuments();}

    public void deleteUser(User selectedUser) throws SQLException {
        adminManager.deleteUser(selectedUser);
        showList();
    }

    public void deleteCustomer(Customer selectedCustomer) throws SQLException {
        adminManager.deleteCustomer(selectedCustomer);
        showList();
    }
    public void showList() {
        try {
            getUsersToBeViewed().clear();
            getUsersToBeViewed().addAll(adminManager.getAllUsers());

            getCustomerToBeViewed().clear();
            getCustomerToBeViewed().addAll(adminManager.getAllCustomer());
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Could not show list");
            alert.setHeaderText("Could not show list");
            alert.showAndWait();
        }

    }

    public ObservableList<Job> getDocumentsToBeViewed() {
        return documentsToBeViewed;
    }

    public void searchCustomers(String query) {
        List<Customer> searchResults = adminManager.searchCustomers(query);
        customerToBeViewed.clear();
        customerToBeViewed.addAll(searchResults);
    }
}
