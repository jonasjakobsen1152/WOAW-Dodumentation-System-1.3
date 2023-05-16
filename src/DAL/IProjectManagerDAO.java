package DAL;

import BE.Customer;
import BE.Job;
import BE.User;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IProjectManagerDAO {
    public ArrayList<User> getAllUsers(String Role);
    public void deleteUser(User user);

    ArrayList<Job> getAllDocuments();

    ArrayList<Customer> getAllCustomers() throws SQLException;

    void deleteDocument(Job selectedDocument);

    void deleteCustomer(Customer selectedCustomer) throws SQLException;
    void deleteJob(Job selectedJob) throws SQLException;
}
