package BLL;

import BE.Customer;
import BE.Job;
import BE.User;
import DAL.DB.AdminDAO_DB;
import DAL.IAdminDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminManager {
    public ArrayList<Customer> getAllCustomer;
    private IAdminDAO adminDAO;
    public AdminManager(){adminDAO = new AdminDAO_DB();}

    public ArrayList<User> getAllUsers(){return adminDAO.getAllUsers();}

    public ArrayList<Customer> getAllCustomer(){
        return adminDAO.getAllCustomer();
    }

    public void deleteUser(User selectedUser) throws SQLException {
        adminDAO.deleteUser(selectedUser);
    }

    public void deleteCustomer(Customer selectedCustomer){
        adminDAO.deleteCustomer(selectedCustomer);
    }

    public ArrayList<Job> getAllDocuments() {
        return adminDAO.getAllDocuments();
    }
}
