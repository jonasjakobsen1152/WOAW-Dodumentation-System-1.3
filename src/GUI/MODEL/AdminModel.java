package GUI.MODEL;

import BE.User;
import BLL.AdminManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminModel {
    AdminManager adminManager;
    private ObservableList<User> usersToBeViewed;
    private static AdminModel instance;

    private AdminModel(){
        adminManager = new AdminManager();
        usersToBeViewed = FXCollections.observableArrayList();
        usersToBeViewed.addAll(adminManager.getAllUsers());
    }
    public static AdminModel getInstance(){
        if(instance == null){
            instance = new AdminModel();
        }
        return instance;
    }

    public ArrayList<User> getAdminList(){
        return adminManager.getAllUsers();
    }

    public ObservableList<User> getUsersToBeViewed(){return usersToBeViewed;}

    public ArrayList<User> getAllUsers(){
        return adminManager.getAllUsers();
    }

    public void deleteUser(User selectedUser) throws SQLException {
        adminManager.deleteUser(selectedUser);
        showList();
    }
    public void showList() {
        getUsersToBeViewed().clear();
        getUsersToBeViewed().addAll(adminManager.getAllUsers());

    }
}
