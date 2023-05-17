package BLL;

import BE.Customer;
import BE.Documentation;
import BE.Job;
import BE.JobImage;
import BLL.UTIL.CustomerSearcher;
import DAL.DB.SalesMenDAO_DB;
import DAL.ICustomerDAO;
import DAL.ISalesmenDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesmenManager {

    private ISalesmenDAO salesmenDAO;

    private ArrayList<Customer> allCustomers;

    private CustomerSearcher customerSearcher;

    public SalesmenManager(){
        salesmenDAO = new SalesMenDAO_DB();
        allCustomers = new ArrayList<>();
        customerSearcher = new CustomerSearcher();
    }

    public ArrayList<Customer> getAllCustomer() throws SQLException {
        allCustomers = salesmenDAO.getAllCustomers();
        return allCustomers;
    }

    public ArrayList<Job> getAllJobs(Customer selectedCustomer) throws SQLException {
        return salesmenDAO.getAllJobs(selectedCustomer);
    }

    public List<Customer> searchCustomers(String query) {
        List<Customer> searchResult = customerSearcher.search(allCustomers,query);
        return searchResult;
    }

    public void printPDF(Job selectedJob) throws SQLException {
        ArrayList<JobImage> jobImages = salesmenDAO.getAllJobImages(selectedJob);
        ArrayList<Documentation> allNotes = salesmenDAO.getallDocuments(selectedJob);

    }
}
