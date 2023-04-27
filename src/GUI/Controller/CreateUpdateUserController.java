package GUI.Controller;

import GUI.MODEL.AdminModel;
import GUI.MODEL.CreateUpdateUserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        ObservableList<String> availableChoices = FXCollections.observableArrayList("Technician", "Salesmen", "Customer", "Project Manager");
        cbRole.setItems(availableChoices);
    }

    public void handleCreateUser(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String role = cbRole.getValue();
        try{
            createUpdateUserModel.createUser(username,password,role);
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }catch (Exception e){
            alertUser("Could not create a user");
            e.printStackTrace();
        }

    }



    public void handleUpdateUser(ActionEvent actionEvent) {
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }


}
