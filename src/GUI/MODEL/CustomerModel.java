package GUI.MODEL;

import BE.Customer;
import BLL.CustomerManager;

import java.sql.SQLException;

public class CustomerModel {
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

    public void showList(){

    }

}
