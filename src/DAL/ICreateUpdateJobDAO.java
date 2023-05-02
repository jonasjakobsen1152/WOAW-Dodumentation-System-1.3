package DAL;

import BE.Customer;
import BE.User;

import java.sql.SQLException;

public interface ICreateUpdateJobDAO {
    void createJob(String title, User selectedTechnician, Customer selectedCustomer) throws SQLException;
}
