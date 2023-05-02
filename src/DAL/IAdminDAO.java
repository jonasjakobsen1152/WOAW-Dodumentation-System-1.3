package DAL;

import BE.Customer;
import BE.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IAdminDAO {
    public void deleteUser(User user) throws SQLException;
    public ArrayList<User> getAllUsers();
    public void deleteCustomer(Customer customer);

    ArrayList<Customer> getAllCustomer();
}
