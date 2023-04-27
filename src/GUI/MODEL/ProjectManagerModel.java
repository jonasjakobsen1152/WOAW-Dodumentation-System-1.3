package GUI.MODEL;

import BE.User;
import BLL.ProjectManagerManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ProjectManagerModel {
    ProjectManagerManager projectManagerManager;
    private ObservableList<User> technicianToBeViewed;
    private static ProjectManagerModel instance;

    private ProjectManagerModel(){
        projectManagerManager = new ProjectManagerManager();
        technicianToBeViewed = FXCollections.observableArrayList();
        technicianToBeViewed.addAll(projectManagerManager.getAllUsers("Technician"));
    }

    public static ProjectManagerModel getInstance(){
        if(instance == null){
            instance = new ProjectManagerModel();
        }
        return instance;
    }

    public ObservableList<User> getTechnicianToBeViewed(){
        return technicianToBeViewed;
    }

  //  public ArrayList<User> getAllUsers(){
     //   return projectManagerManager.getAllUsers();
   // }
}
