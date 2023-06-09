package GUI.MODEL;

import BE.Customer;
import BE.Job;
import BE.User;
import BLL.SalesmenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesMenModel {

    private static SalesMenModel instance;

    private SalesmenManager salesmenManager;

    private ObservableList<Customer> customerToBeViewed;

    private ObservableList<Job> jobsToBeViewed;
    private SalesMenModel() throws SQLException {
       salesmenManager = new SalesmenManager();

        customerToBeViewed = FXCollections.observableArrayList();
        customerToBeViewed.addAll(salesmenManager.getAllCustomer());

        jobsToBeViewed = FXCollections.observableArrayList();
    }

    public static SalesMenModel getInstance() throws SQLException {
        if(instance == null){
            instance = new SalesMenModel();
        }
            return instance;
    }

    public ObservableList<Customer> getCustomerToBeViewed() {
        return customerToBeViewed;
    }

   public ObservableList<Job> getJobsToBeViewed(Customer selectedCustomer) throws SQLException {
        jobsToBeViewed.clear();
        jobsToBeViewed.addAll(salesmenManager.getAllJobs(selectedCustomer));
        return  jobsToBeViewed;
   }

    public void searchCustomers(String query) {
        List<Customer> searchResults = salesmenManager.searchCustomers(query);
        customerToBeViewed.clear();
        customerToBeViewed.addAll(searchResults);
    }

    public void printPDF(Job selectedJob) throws SQLException, IOException {
        salesmenManager.printPDF(selectedJob);
    }

    public void setPDFStrategy(String privacy) {
        salesmenManager.setPDFStrategy(privacy);
    }
}
