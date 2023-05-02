package DAL;

import BE.Customer;

import java.sql.SQLException;


public interface ICustomerDAO {

    void createCustomer(String name, int phone, String email) throws SQLException;
    void updateCustomer(Customer customer);
    void deleteCustomer(Customer customer);
}
