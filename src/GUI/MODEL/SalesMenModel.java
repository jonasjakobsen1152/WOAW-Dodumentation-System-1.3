package GUI.MODEL;

import BE.Customer;
import BE.Job;
import BE.User;
import BLL.SalesmenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;

import java.sql.SQLException;
import java.util.ArrayList;

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

   public ObservableList<Job> getJobsToBeViewed(Customer selectedCustomer){
        jobsToBeViewed.addAll(salesmenManager.getAllJobs(selectedCustomer));
        return  jobsToBeViewed;
   }
}
