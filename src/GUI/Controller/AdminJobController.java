package GUI.Controller;

import BE.Documentation;
import BE.Job;
import BE.User;
import GUI.MODEL.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class is used to control the admin technician job window
 */
public class AdminJobController implements Initializable {

    @FXML
    private TableView<Job> tblWork;
    @FXML
    private TableColumn clmTitleWork;
    private TechnicianModel technicianModel;
    private User selectedUser;

    private LoginModel loginModel;
    private TechnicianJobModel technicianJobModel;
    private DocumentationModel documentationModel;
    public Job selectedJob;

    public AdminJobController(){
        technicianModel = TechnicianModel.getInstance();
        try {
            technicianJobModel = TechnicianJobModel.getInstance();
            documentationModel = DocumentationModel.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
            alertUser("Could not get list");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    /**
     * This method is used to finish the selected job
     * @param event
     */
    public void handleFinishJob(ActionEvent event) {
        // Retrieve the selected job from the UI
        Job selectedJob = tblWork.getSelectionModel().getSelectedItem();
        if (selectedJob == null) {
            // No job is selected, do nothing
            return;
        }

        // Show a confirmation dialog
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to finish this job?",
                "Confirm Finish Job", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            // User canceled, do nothing
            return;
        }
        technicianModel.finishJob(selectedJob);

    }


    /**
     * This method is used to log out of the current user.
     * @param actionEvent
     */
    public void handleLogOut(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

        // Show the LogIn window
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/Login.fxml"));
            Parent root = loader.load();
            Stage loginStage = new Stage();
            loginStage.setTitle("LogIn");
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
