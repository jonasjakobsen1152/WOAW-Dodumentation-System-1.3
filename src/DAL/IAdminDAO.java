package DAL;

import BE.Customer;
import BE.Job;
import BE.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IAdminDAO {
    public void deleteUser(User user) throws SQLException;
    public ArrayList<User> getAllUsers() throws SQLException;
    public void deleteCustomer(Customer customer) throws SQLException;

    ArrayList<Customer> getAllCustomer() throws SQLException;

    ArrayList<Job> getAllDocuments() throws SQLException;

    List<Job> getWork(User selectedUser);

    void deleteJob(Job selectedJob) throws SQLException;
}
