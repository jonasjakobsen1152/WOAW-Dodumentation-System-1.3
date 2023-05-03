package DAL;

import BE.Customer;
import BE.Job;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ISalesmenDAO {


    ArrayList<Customer> getAllCustomers() throws SQLException;

    ArrayList<Job> getAllJobs(Customer selectedCustomer);
}
