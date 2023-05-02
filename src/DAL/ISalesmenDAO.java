package DAL;

import BE.Customer;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ISalesmenDAO {


    ArrayList<Customer> getAllCustomers() throws SQLException;
}
