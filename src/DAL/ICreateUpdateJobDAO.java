package DAL;

import BE.Customer;
import BE.User;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ICreateUpdateJobDAO {
    void createJob(String title, User selectedTechnician, Customer selectedCustomer) throws SQLException;

    ArrayList<Customer> getAllCustomers() throws SQLException;
}
