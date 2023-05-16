package GUI.Controller;

import BE.Job;
import BE.User;
import GUI.MODEL.TechnicianJobModel;
import GUI.MODEL.TechnicianModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProjectManagerJobController implements Initializable {

    @FXML
    private TableView<Job> tblWork;
    @FXML
    private TableColumn clmTitleWork;
    private TechnicianModel technicianModel;
    private TechnicianJobModel technicianJobModel;
    public Job selectedJob;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        technicianModel = TechnicianModel.getInstance();
        try {
            technicianJobModel = TechnicianJobModel.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
            alertUser("Could not get list");
        }
        tblWork.setOnMouseClicked(event -> {
            selectedJob = tblWork.getSelectionModel().getSelectedItem();
        });
        showWork();
    }

    public void showWork(){
        clmTitleWork.setCellValueFactory(new PropertyValueFactory<User,String>("title"));
        tblWork.setItems(technicianModel.getWorkToBeViewed());
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }
}
