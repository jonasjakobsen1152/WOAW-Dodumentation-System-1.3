package GUI.MODEL;

import BE.Job;
import BE.User;
import BLL.TechnicianManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class TechnicianModel {
    TechnicianManager technicianManager;
    private static TechnicianModel instance;
    public User selectedUser;
    public ObservableList<Job> workToBeViewed;

    private TechnicianModel(){
        technicianManager = new TechnicianManager();
        workToBeViewed = FXCollections.observableArrayList();

    }

    public static TechnicianModel getInstance(){
        if(instance == null){
            instance = new TechnicianModel();
        }
        return  instance;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public User getSelectedUser() {
        return selectedUser;
    }
    public ObservableList<Job> getWorkToBeViewed(){return workToBeViewed;}

    public void showList() {
        getWorkToBeViewed().clear();
        getWorkToBeViewed().addAll(technicianManager.getWork(getSelectedUser()));

    }

    public List<Job> getWork() {
        return technicianManager.getWork(getSelectedUser());
    }



}
