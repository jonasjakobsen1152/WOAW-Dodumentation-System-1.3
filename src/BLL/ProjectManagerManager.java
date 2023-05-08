package BLL;

import BE.Customer;
import BE.Job;
import BE.User;
import BLL.UTIL.CustomerSearcher;
import DAL.DB.ProjectManagerDAO_DB;
import DAL.IProjectManagerDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectManagerManager {
    private IProjectManagerDAO projectManagerDAO;

    private CustomerSearcher customerSearcher;

    private ArrayList<Customer> allCustomers;

    public ProjectManagerManager(){
        projectManagerDAO = new ProjectManagerDAO_DB();

        allCustomers = new ArrayList<>();
        customerSearcher = new CustomerSearcher();
    }

    public ArrayList<User> getAllUsers(String role){
        return projectManagerDAO.getAllUsers(role);
    }

    public void deleteUser(User selectedUser) {
        projectManagerDAO.deleteUser(selectedUser);
    }

    public ArrayList<Job> getAllDocuments() {
        return projectManagerDAO.getAllDocuments();
    }

    public ArrayList<Customer> getAllCustomers() throws SQLException {
        allCustomers = projectManagerDAO.getAllCustomers();
        return allCustomers;
    }

    public void deletedDocument(Job selectedDocument) {
        projectManagerDAO.deleteDocument(selectedDocument);
    }

    public void deleteCustomer(Customer selectedCustomer) throws SQLException {
        projectManagerDAO.deleteCustomer(selectedCustomer);
    }

    public List<Customer> searchCustomer(String query) {
        List<Customer> searchResult = customerSearcher.search(allCustomers,query);
        return searchResult;
    }
}
