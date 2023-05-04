package GUI.MODEL;

import BE.User;
import BLL.AdminManager;
import BLL.CreateUpdateUserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreateUpdateUserModel {
    CreateUpdateUserManager createUpdateUserManager;
    AdminModel adminModel;
    ProjectManagerModel projectManagerModel;
    private User selectedUser;


    private static CreateUpdateUserModel instance;

    private CreateUpdateUserModel() throws SQLException {
        createUpdateUserManager = new CreateUpdateUserManager();
        adminModel = AdminModel.getInstance();
        projectManagerModel = ProjectManagerModel.getInstance();
    }
    public static CreateUpdateUserModel getInstance() throws SQLException {
        if(instance == null){
            instance = new CreateUpdateUserModel();
        }
        return instance;
    }


    public void createUser(String username, String password, String role) throws SQLException {
        createUpdateUserManager.createUser(username,password,role);
        showList();
    }

    public void showList() throws SQLException {
        adminModel.getUsersToBeViewed().clear();
        adminModel.getUsersToBeViewed().addAll(adminModel.getAdminList());

        adminModel.getCustomerToBeViewed().clear();
        adminModel.getCustomerToBeViewed().addAll(adminModel.getCustomerList());

        projectManagerModel.getTechnicianToBeViewed().clear();
        projectManagerModel.getTechnicianToBeViewed().addAll(projectManagerModel.getTechnicianList());

        projectManagerModel.getSalesmenToBeViewed().clear();
        projectManagerModel.getSalesmenToBeViewed().addAll(projectManagerModel.getSalesmenList());

        projectManagerModel.getCustomerToBeViewed().clear();
        projectManagerModel.getCustomerToBeViewed().addAll(projectManagerModel.getCustomerList());
    }
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
    public void updateUser(String username,String password,String role) throws SQLException {
        User user = new User(selectedUser.getId(),username,password,role);
        createUpdateUserManager.updateUser(user);
        showList();
    }

}
