package GUI.Controller;

import GUI.MODEL.CreateUpdateUserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateUpdateUserController implements Initializable {
    public TextField txtUsername;
    public TextField txtPassword;
    @FXML
    public ChoiceBox<String> cbRole;
    public CreateUpdateUserModel createUpdateUserModel;

    public CreateUpdateUserController(){

        createUpdateUserModel = CreateUpdateUserModel.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("Admin", "Salesmen");
        cbRole.setItems(availableChoices);
    }

    public void handleCreateUser(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

    }

    public void handleUpdateUser(ActionEvent actionEvent) {
    }


}
