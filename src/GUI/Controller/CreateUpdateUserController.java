package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class CreateUpdateUserController {

    public CreateUpdateUserController(){
        ArrayList<String> roleList = new ArrayList<>();
        roleList.add("Admin");
        cbRole.setItems(roleList);
    }

    public TextField txtUsername;
    public TextField txtPassword;
    public ChoiceBox cbRole;

    public void handleCreateUser(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

    }

    public void handleUpdateUser(ActionEvent actionEvent) {
    }
}
