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

    private static CreateUpdateUserModel instance;

    private CreateUpdateUserModel(){
        createUpdateUserManager = new CreateUpdateUserManager();

    }
    public static CreateUpdateUserModel getInstance(){
        if(instance == null){
            instance = new CreateUpdateUserModel();
        }
        return instance;
    }


}
