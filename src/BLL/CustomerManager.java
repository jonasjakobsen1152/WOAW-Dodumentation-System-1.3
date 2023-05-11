package BLL;

import BE.Customer;
import DAL.DB.CustomerDAO_DB;
import DAL.ICustomerDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerManager {

    private ICustomerDAO customerDAO;

    public CustomerManager(){
        customerDAO = new CustomerDAO_DB();
    }

    public void createCustomer(String name, int phone, String email, String address) throws SQLException {
        customerDAO.createCustomer(name,phone,email,address);
    }
    public ArrayList<Customer> getAllCustomer() throws SQLException {
        return customerDAO.getAllCustomer();
    }

    public void updateCustomer(Customer customer) {
        customerDAO.updateCustomer(customer);
    }
}
