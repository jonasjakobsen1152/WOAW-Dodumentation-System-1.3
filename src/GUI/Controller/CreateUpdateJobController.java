package GUI.Controller;

import BE.Customer;
import BE.User;
import GUI.MODEL.CreateUpdateJobModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class CreateUpdateJobController {

    public TextField txtTitle;
    public Label lblUser;
    public CreateUpdateJobModel createUpdateJobModel;
    public CreateUpdateJobController(){
        createUpdateJobModel = CreateUpdateJobModel.getInstance();
        //lblUser.setText(createUpdateJobModel.getSelectedTechnician().getUsername());

    }

    public void handleUpdateJob(ActionEvent actionEvent) {

    }
    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    public void handleCreateJob(ActionEvent actionEvent) throws SQLException {
        String title = txtTitle.getText();
        User selectedTechnician = createUpdateJobModel.getSelectedTechnician();
        Customer selectedCustomer = createUpdateJobModel.getSelectedCustomer();

        try{
        createUpdateJobModel.createJob(title,selectedTechnician,selectedCustomer);
        }catch (SQLException e){
            e.printStackTrace();
            alertUser("Cant create job");
        }
    }
}
