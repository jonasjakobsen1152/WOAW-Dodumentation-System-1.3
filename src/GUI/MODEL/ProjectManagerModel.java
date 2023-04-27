package GUI.MODEL;

import BE.User;
import BLL.ProjectManagerManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ProjectManagerModel {
    ProjectManagerManager projectManagerManager;
    private ObservableList<User> technicianToBeViewed;
    private ObservableList<User> salesmenToBeViewed;
    private ObservableList<User> customerToBeViewed;
    private ObservableList<User> documentToBeViewed;
    private static ProjectManagerModel instance;

    private ProjectManagerModel(){
        projectManagerManager = new ProjectManagerManager();
        technicianToBeViewed = FXCollections.observableArrayList();
        technicianToBeViewed.addAll(projectManagerManager.getAllUsers("Technician"));

        salesmenToBeViewed = FXCollections.observableArrayList();
        salesmenToBeViewed.addAll(projectManagerManager.getAllUsers("Salesmen"));

        customerToBeViewed = FXCollections.observableArrayList();
        customerToBeViewed.addAll(projectManagerManager.getAllUsers("Customer"));
    }

    public static ProjectManagerModel getInstance(){
        if(instance == null){
            instance = new ProjectManagerModel();
        }
        return instance;
    }

    public ArrayList<User> getTechnicianList(){
        return projectManagerManager.getAllUsers("Technician");
    }

    public ArrayList<User> getSalesmenList(){
        return projectManagerManager.getAllUsers("Salesmen");
    }

    public ArrayList<User> getCustomerList(){
        return projectManagerManager.getAllUsers("Customer");
    }

    public ObservableList<User> getTechnicianToBeViewed(){
        return technicianToBeViewed;
    }

    public ObservableList<User> getSalesmenToBeViewed() {
        return salesmenToBeViewed;
    }

    public ObservableList<User> getCustomerToBeViewed() {
        return customerToBeViewed;
    }
}
