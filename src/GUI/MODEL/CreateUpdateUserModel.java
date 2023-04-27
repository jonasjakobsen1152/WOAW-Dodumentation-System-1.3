package GUI.MODEL;

import BE.User;
import BLL.AdminManager;
import BLL.CreateUpdateUserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class CreateUpdateUserModel {
    CreateUpdateUserManager createUpdateUserManager;
    AdminModel adminModel;

    private static CreateUpdateUserModel instance;

    private CreateUpdateUserModel(){
        createUpdateUserManager = new CreateUpdateUserManager();
        adminModel = AdminModel.getInstance();

    }
    public static CreateUpdateUserModel getInstance(){
        if(instance == null){
            instance = new CreateUpdateUserModel();
        }
        return instance;
    }


    public void createUser(String username, String password, String role) {
        createUpdateUserManager.createUser(username,password,role);
        showList();
    }

    public void showList() {
        adminModel.getUsersToBeViewed().clear();
        adminModel.getUsersToBeViewed().addAll(adminModel.getAdminList());

    }

}
