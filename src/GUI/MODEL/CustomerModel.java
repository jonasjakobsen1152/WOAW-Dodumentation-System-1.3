package GUI.MODEL;

import BE.Customer;
import BLL.CustomerManager;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    ObservableList<Customer> customerToBeViewed;
    CustomerManager customerManager;
    private Customer selectedCustomer;

    private static CustomerModel instance;

    private CustomerModel(){
        customerManager = new CustomerManager();
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
        getCustomerToBeViewed().addAll(customerManager.getAllCustomer());
    }

    public ObservableList<Customer> getCustomerToBeViewed() {
        return customerToBeViewed;
    }
}
