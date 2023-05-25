package GUI.MODEL;

import BE.Customer;
import BLL.CustomerManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    private ObservableList<Customer> customerToBeViewed;
    CustomerManager customerManager;
    private Customer selectedCustomer;
    AdminModel adminModel;

    private static CustomerModel instance;

    private CustomerModel() throws SQLException {
        customerManager = new CustomerManager();
        customerToBeViewed = FXCollections.observableArrayList();
        adminModel = AdminModel.getInstance();
        try {
            showList();
        }catch (SQLException e){
            alertUser("Cant get customers from database");
        }

    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    public static CustomerModel getInstance() throws SQLException {
        if(instance == null){
            instance = new CustomerModel();
        }
        return instance;
    }

    public void createCustomer(String name, int phone, String email, String address) throws SQLException {
        customerManager.createCustomer(name,phone,email,address);
        showList();
    }

    public void showList() throws SQLException {
        customerToBeViewed.clear();
        customerToBeViewed.addAll(customerManager.getAllCustomer());
        adminModel.showList();

    }

    public Customer getSelectedCustomer(){
        return selectedCustomer;
    }
    public void setCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public void updateCustomer(Customer customer) throws SQLException {
        customerManager.updateCustomer(customer);
        showList();
    }
}
