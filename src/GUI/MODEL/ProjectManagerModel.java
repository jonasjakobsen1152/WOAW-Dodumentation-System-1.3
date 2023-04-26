package GUI.MODEL;

import BE.User;
import BLL.ProjectManagerManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ProjectManagerModel {
    ProjectManagerManager projectManagerManager;
    private ObservableList<User> usersToBeViewed;
    private static ProjectManagerModel instance;

    private ProjectManagerModel(){
        projectManagerManager = new ProjectManagerManager();
        usersToBeViewed = FXCollections.observableArrayList();
        usersToBeViewed.addAll(projectManagerManager.getAllUsers());
    }

    public static ProjectManagerModel getInstance(){
        if(instance == null){
            instance = new ProjectManagerModel();
        }
        return instance;
    }

    public ObservableList<User> getUsersToBeViewed(){
        return usersToBeViewed;
    }

    public ArrayList<User> getAllUsers(){
        return projectManagerManager.getAllUsers();
    }
}
