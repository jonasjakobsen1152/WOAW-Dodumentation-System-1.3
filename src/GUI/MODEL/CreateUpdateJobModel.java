package GUI.MODEL;

import BE.Customer;
import BE.User;

public class CreateUpdateJobModel {
    private static CreateUpdateJobModel instance;


    public static CreateUpdateJobModel getInstance(){
        if(instance == null){
            instance = new CreateUpdateJobModel();
        }
        return instance;
    }

    public void setCustomerAndTechnician(User selectedTechnian, Customer selectedCustomer) {
    }
}
