package BLL;

import BE.Customer;
import BE.Job;
import BE.User;
import BLL.UTIL.CustomerSearcher;
import DAL.DB.AdminDAO_DB;
import DAL.IAdminDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminManager {
    public ArrayList<Customer> getAllCustomer;
    private IAdminDAO adminDAO;

    private ArrayList<Customer> allCustomers;
    private CustomerSearcher customerSearcher;
    public AdminManager()
    {
        adminDAO = new AdminDAO_DB();
    customerSearcher = new CustomerSearcher();
    }

    public ArrayList<User> getAllUsers() throws SQLException {return adminDAO.getAllUsers();}

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
        return adminDAO.getAllDocuments();
    }

    public List<Customer> searchCustomers(String query) {
        List<Customer> searchResult = customerSearcher.search(allCustomers,query);
        return searchResult;
    }
}
