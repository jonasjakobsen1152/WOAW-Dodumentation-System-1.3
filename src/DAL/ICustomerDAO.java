package DAL;

import BE.Customer;
import BE.User;

import java.sql.SQLException;
import java.util.ArrayList;


public interface ICustomerDAO {

    void createCustomer(String name, int phone, String email) throws SQLException;
    void updateCustomer(Customer customer);
    public ArrayList<Customer> getAllCustomer() throws SQLException;
}
