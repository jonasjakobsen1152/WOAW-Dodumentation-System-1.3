package GUI.Controller;

import BE.User;
import BLL.UTIL.BCrypt;
import GUI.MODEL.AdminModel;
import GUI.MODEL.CreateUpdateUserModel;
import io.github.palexdev.materialfx.controls.MFXButton;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateUpdateUserController implements Initializable {
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private ChoiceBox<String> cbRole;
    public CreateUpdateUserModel createUpdateUserModel;
    @FXML
    private MFXButton btnCreate;
    @FXML
    private MFXButton btnUpdate;

    public CreateUpdateUserController() throws SQLException {

        createUpdateUserModel = CreateUpdateUserModel.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("Technician", "Salesmen", "Project Manager","Admin");
        cbRole.setItems(availableChoices);
    }

    public void handleCreateUser(ActionEvent actionEvent) {
        System.out.println(cbRole.getValue());
        if(txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty() || cbRole.getValue() == null) {
            alertUser("Please fill out the information");
        } else {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String role = cbRole.getValue();
            String salt = BCrypt.gensalt(15);
            String passwordToHash = BCrypt.hashpw(password,salt);
            try{
                createUpdateUserModel.createUser(username,passwordToHash,role);
                Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                stage.close();
            }catch (Exception e){
                alertUser("Could not create a user");
                e.printStackTrace();
            }
        }
    }



    public void handleUpdateUser(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String role = cbRole.getValue();
        String salt = BCrypt.gensalt(15);
        String passwordToHash = BCrypt.hashpw(password,salt);
        try {
            createUpdateUserModel.updateUser(username,passwordToHash,role);

            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }catch (Exception e){
            alertUser("Could not update the user");
            e.printStackTrace();
        }
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }
    public void removeUpdate() {
        btnUpdate.setVisible(false);
    }


    public void setupUpdate(User selectedUser){
        btnCreate.setVisible(false);
        txtUsername.setText(selectedUser.getUsername());
        cbRole.setValue(selectedUser.getRole());

    }


}
