package BLL;

import BE.Customer;
import BE.User;
import DAL.DB.CreateUpdateJobDAO_DB;
import DAL.ICreateUpdateJobDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CreateUpdateJobManager {
    private ICreateUpdateJobDAO createUpdateJobDAO;
    private ArrayList<Customer> allCustomers;
    public CreateUpdateJobManager(){
        createUpdateJobDAO = new CreateUpdateJobDAO_DB();
    }


    public void createJob(String title, User selectedTechnician, Customer selectedCustomer) throws SQLException {
        try{
        createUpdateJobDAO.createJob(title,selectedTechnician,selectedCustomer);
    }catch (SQLException e){
            throw new SQLException(e);
        }
    }

    public ArrayList<Customer> getAllCustomers() throws SQLException {
        allCustomers = createUpdateJobDAO.getAllCustomers();
        return allCustomers;
    }
}
