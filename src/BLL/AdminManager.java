package BLL;

import BE.Customer;
import BE.Job;
import BE.User;
import BLL.UTIL.CustomerSearcher;
import BLL.UTIL.JobSearcher;
import BLL.UTIL.UserSearcher;
import DAL.DB.AdminDAO_DB;
import DAL.IAdminDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminManager {
    public ArrayList<Customer> getAllCustomer;
    private IAdminDAO adminDAO;

    private ArrayList<Customer> allCustomers;
    private ArrayList<User> allUsers;
    private CustomerSearcher customerSearcher;
    private UserSearcher userSearcher;
    private JobSearcher jobSearcher;
    private ArrayList<Job> allJobs;
    public AdminManager()
    {
        adminDAO = new AdminDAO_DB();
        customerSearcher = new CustomerSearcher();
        userSearcher = new UserSearcher();
        jobSearcher = new JobSearcher();
    }

    public ArrayList<User> getAllUsers() throws SQLException {
        allUsers = adminDAO.getAllUsers();
        return allUsers;
    }

    public ArrayList<Customer> getAllCustomer() throws SQLException {
        allCustomers = adminDAO.getAllCustomer();
        return allCustomers;
    }

    public void deleteUser(User selectedUser) throws SQLException {
        adminDAO.deleteUser(selectedUser);
    }

    public void deleteCustomer(Customer selectedCustomer) throws SQLException {
        adminDAO.deleteCustomer(selectedCustomer);
    }

    public ArrayList<Job> getAllDocuments() throws SQLException {
        allJobs =adminDAO.getAllDocuments();
        return allJobs;
    }

    public List<Customer> searchCustomers(String query) {
        List<Customer> searchResult = customerSearcher.search(allCustomers,query);
        return searchResult;
    }

    public List<User> searchUsers(String query) {
        List<User> searchResult = userSearcher.search(allUsers,query);
        return searchResult;
    }

    public List<Job> searchJobs(String query) {
        List<Job> searchResult = jobSearcher.search(allJobs,query);
        return searchResult;
    }

    public List<Job> getWork(User selectedUser) {
        return adminDAO.getWork(selectedUser);
    }
}
