package DAL;

import BE.Customer;
import BE.Job;
import BE.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IAdminDAO {
    public void deleteUser(User user) throws SQLException;
    public ArrayList<User> getAllUsers() throws SQLException;
    public void deleteCustomer(Customer customer) throws SQLException;

    ArrayList<Customer> getAllCustomer() throws SQLException;

    ArrayList<Job> getAllDocuments() throws SQLException;
}
