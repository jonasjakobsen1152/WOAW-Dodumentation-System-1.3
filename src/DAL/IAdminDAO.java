package DAL;

import BE.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IAdminDAO {
    ArrayList<Job> getAllJobs() throws SQLException;

    public void deleteUser(User user) throws SQLException;
    public ArrayList<User> getAllUsers() throws SQLException;
    public void deleteCustomer(Customer customer) throws SQLException;

    ArrayList<Customer> getAllCustomer() throws SQLException;

    List<Job> getWork(User selectedUser);

    void deleteJob(Job selectedJob) throws SQLException;

    ArrayList<Documentation> getAllDocumentation(Job selectedJob) throws SQLException;

    ArrayList<JobImage> getAllJobImages(Job selectedJob) throws SQLException;
}
