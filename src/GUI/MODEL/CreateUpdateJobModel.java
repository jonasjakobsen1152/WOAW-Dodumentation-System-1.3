package GUI.MODEL;

import BE.Customer;
import BE.User;
import BLL.CreateUpdateJobManager;

import java.sql.SQLException;

public class CreateUpdateJobModel {
    private static CreateUpdateJobModel instance;
    public User selectedTechnician;
    public Customer selectedCustomer;
    public CreateUpdateJobManager createUpdateJobManager;
    public CreateUpdateJobModel(){
        createUpdateJobManager = new CreateUpdateJobManager();
    }


    public static CreateUpdateJobModel getInstance(){
        if(instance == null){
            instance = new CreateUpdateJobModel();
        }
        return instance;
    }
    public User getSelectedTechnician(){
        return selectedTechnician;
    }
    public Customer getSelectedCustomer(){
        return selectedCustomer;
    }

    public void setCustomerAndTechnician(User selectedTechnician, Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
        this.selectedTechnician = selectedTechnician;
    }

    public void createJob(String title, User selectedTechnician, Customer selectedCustomer) throws SQLException {
        try {
        createUpdateJobManager.createJob(title,selectedTechnician,selectedCustomer);
    }catch (SQLException e){
            throw new SQLException(e);
        }
    }
}
