package GUI.MODEL;

import BE.Customer;
import BE.User;

public class CreateUpdateJobModel {
    private static CreateUpdateJobModel instance;
    public User selectedTechnician;
    public Customer selectedCustomer;


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
}
