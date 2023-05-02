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

    private static CustomerModel instance;

    private CustomerModel(){
        customerManager = new CustomerManager();
        customerToBeViewed = FXCollections.observableArrayList();
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

    public static CustomerModel getInstance(){
        if(instance == null){
            instance = new CustomerModel();
        }
        return instance;
    }

    public void createCustomer(String name, int phone, String email) throws SQLException {
        customerManager.createCustomer(name,phone,email);
        showList();
    }

    public void showList() throws SQLException {
        getCustomerToBeViewed().clear();
        try {
            getCustomerToBeViewed().addAll(customerManager.getAllCustomer());
        }catch (SQLException e){
            throw new SQLException();
        }
    }

    public ObservableList<Customer> getCustomerToBeViewed() {
        return customerToBeViewed;
    }
}
