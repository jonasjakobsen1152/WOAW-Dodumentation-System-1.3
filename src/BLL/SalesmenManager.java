package BLL;

import BE.Customer;
import BE.Job;
import DAL.DB.SalesMenDAO_DB;
import DAL.ICustomerDAO;
import DAL.ISalesmenDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class SalesmenManager {

    private ISalesmenDAO salesmenDAO;

    public SalesmenManager(){
        salesmenDAO = new SalesMenDAO_DB();
    }

    public ArrayList<Customer> getAllCustomer() throws SQLException {
        return salesmenDAO.getAllCustomers();
    }

    public ArrayList<Job> getAllJobs(Customer selectedCustomer) throws SQLException {
        return salesmenDAO.getAllJobs(selectedCustomer);
    }
}
