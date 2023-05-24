package GUI.Controller;

import BE.User;
import GUI.MODEL.ProjectManagerModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class is used to add technicians to jobs.
 */
public class AddTechToJobController implements Initializable {
    public TableView<User> tblTechnicians;
    public User selectedTechnician;
    public TableColumn clmUsername;
    private ProjectManagerModel projectManagerModel;

    public AddTechToJobController(){
        try {
            projectManagerModel = ProjectManagerModel.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
            alertUser("Could not open the ");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showTechnician();
        tblTechnicians.setOnMouseClicked(event -> {
            selectedTechnician = tblTechnicians.getSelectionModel().getSelectedItem();
        });
    }
    /**
     * This method adds a technician to a job
     * @param actionEvent
     */
    public void handleAddTechToJob(ActionEvent actionEvent) {
        selectedTechnician = tblTechnicians.getSelectionModel().getSelectedItem();
        if(selectedTechnician == null){
            alertUser("Select a technician to add to the job");
        }else {
            try {
                projectManagerModel.addTechToJob(selectedTechnician);
                Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                stage.close();
            }catch (SQLException e){
                alertUser("Could not add the technician to the job because he already is assigned to the job");
                e.printStackTrace();
            }
            }
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    private void showTechnician() {
        clmUsername.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        tblTechnicians.setItems(projectManagerModel.getTechnicianToBeViewed());
    }


}
