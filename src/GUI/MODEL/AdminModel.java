package GUI.MODEL;

import BE.Customer;
import BE.Job;
import BE.User;
import BLL.AdminManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminModel {
    AdminManager adminManager;
    private ObservableList<User> usersToBeViewed;
    private ObservableList<Customer> customerToBeViewed;
    private ObservableList<Job> documentsToBeViewed;
    private static AdminModel instance;

    private AdminModel(){
        adminManager = new AdminManager();
        usersToBeViewed = FXCollections.observableArrayList();
        usersToBeViewed.addAll(adminManager.getAllUsers());

        customerToBeViewed = FXCollections.observableArrayList();
        customerToBeViewed.addAll(adminManager.getAllCustomer());

        documentsToBeViewed = FXCollections.observableArrayList();
        documentsToBeViewed.addAll(adminManager.getAllDocuments());
    }
    public static AdminModel getInstance(){
        if(instance == null){
            instance = new AdminModel();
        }
        return instance;
    }

    public ArrayList<User> getAdminList(){
        return adminManager.getAllUsers();
    }

    public ArrayList<Customer> getCustomerList(){
        return adminManager.getAllCustomer();
    }

    public ObservableList<User> getUsersToBeViewed(){return usersToBeViewed;}

    public ObservableList<Customer> getCustomerToBeViewed(){
        return customerToBeViewed;
    }

    public ArrayList<User> getAllUsers(){
        return adminManager.getAllUsers();
    }

    public ArrayList<Customer> getAllCustomer(){
        return adminManager.getAllCustomer();
    }

    public ArrayList<Job> getAllDocuments(){return  adminManager.getAllDocuments();}

    public void deleteUser(User selectedUser) throws SQLException {
        adminManager.deleteUser(selectedUser);
        showList();
    }

    public void deleteCustomer(Customer selectedCustomer){
        adminManager.deleteCustomer(selectedCustomer);
        showList();
    }
    public void showList() {
        getUsersToBeViewed().clear();
        getUsersToBeViewed().addAll(adminManager.getAllUsers());

        getCustomerToBeViewed().clear();
        getCustomerToBeViewed().addAll(adminManager.getAllCustomer());

    }

    public ObservableList<Job> getDocumentsToBeViewed() {
        return documentsToBeViewed;
    }
}
