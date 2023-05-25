package GUI.MODEL;

import BE.Customer;
import BE.Job;
import BE.User;
import BLL.CreateUpdateJobManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class CreateUpdateJobModel {
    private static CreateUpdateJobModel instance;
    public User selectedTechnician;
    public Customer selectedCustomer;
    public CreateUpdateJobManager createUpdateJobManager;
    private ObservableList<Customer> customerToBeViewed;
    private ProjectManagerModel projectManagerModel;
    public CreateUpdateJobModel() throws SQLException {
        createUpdateJobManager = new CreateUpdateJobManager();
        projectManagerModel = ProjectManagerModel.getInstance();
        customerToBeViewed = FXCollections.observableArrayList();
        customerToBeViewed.addAll(createUpdateJobManager.getAllCustomers());
    }


    public static CreateUpdateJobModel getInstance() throws SQLException {
        if(instance == null){
            instance = new CreateUpdateJobModel();
        }
        return instance;
    }

    public ObservableList<Customer> getCustomerToBeViewed() {
        return customerToBeViewed;
    }

    public User getSelectedTechnician(){
        return selectedTechnician;
    }
    public Customer getSelectedCustomer(){
        return selectedCustomer;
    }

    public void setTechnician(User selectedTechnician) {
        this.selectedTechnician = selectedTechnician;
    }

    public void createJob(String title, User selectedTechnician, Customer selectedCustomer) throws SQLException {
        try {
            createUpdateJobManager.createJob(title,selectedTechnician,selectedCustomer);
        }catch (SQLException e){
            throw new SQLException(e);
        }
        showList();
    }

    public void showList(){
        projectManagerModel.getDocumentsToBeViewed().clear();
        projectManagerModel.getDocumentsToBeViewed().addAll(projectManagerModel.getDocumentList());
    }

}
