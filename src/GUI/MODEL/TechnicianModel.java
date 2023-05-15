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

    private User selectedUser;

    private LoginModel loginModel;

    public ObservableList<Job> workToBeViewed;

    private TechnicianModel(){
        technicianManager = new TechnicianManager();
        loginModel = LoginModel.getInstance();
        workToBeViewed = FXCollections.observableArrayList();

        selectedUser = loginModel.getLoggedInUser();
        showList();
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


    public void finishJob(Job selectedJob) {
        technicianManager.finishJob(selectedJob);
        showList();
    }
}
