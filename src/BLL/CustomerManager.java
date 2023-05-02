package BLL;

import DAL.DB.CustomerDAO_DB;
import DAL.ICustomerDAO;

import java.sql.SQLException;

public class CustomerManager {

    private ICustomerDAO customerDAO;

    public CustomerManager(){
        customerDAO = new CustomerDAO_DB();
    }

    public void createCustomer(String name, int phone, String email) throws SQLException {
        customerDAO.createCustomer(name,phone,email);
    }
}
