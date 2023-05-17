package DAL;

import BE.Customer;
import BE.Documentation;
import BE.Job;
import BE.JobImage;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ISalesmenDAO {


    ArrayList<Customer> getAllCustomers() throws SQLException;

    ArrayList<Job> getAllJobs(Customer selectedCustomer) throws SQLException;

    ArrayList<JobImage> getAllJobImages(Job selectedJob) throws SQLException;

    ArrayList<Documentation> getallDocuments(Job selectedJob) throws SQLException;
}
