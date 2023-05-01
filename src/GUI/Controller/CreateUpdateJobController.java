package GUI.Controller;

import GUI.MODEL.CreateUpdateJobModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateUpdateJobController {

    public TextField txtTitle;
    public Label lblUser;
    public CreateUpdateJobModel createUpdateJobModel;
    public CreateUpdateJobController(){
        createUpdateJobModel = CreateUpdateJobModel.getInstance();

    }

    public void handleUpdateJob(ActionEvent actionEvent) {

    }

    public void handleCreateJob(ActionEvent actionEvent) {
        String title = txtTitle.getText();

    }
}
