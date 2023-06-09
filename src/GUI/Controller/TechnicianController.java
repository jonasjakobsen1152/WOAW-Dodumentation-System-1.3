package GUI.Controller;

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
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class is used to control the technician window
 */
public class TechnicianController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        technicianModel = TechnicianModel.getInstance();
        try {
            technicianJobModel = TechnicianJobModel.getInstance();
            documentationModel = DocumentationModel.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
            alertUser("Could not get list");
        }
        tblWork.setOnMouseClicked(event -> {
            selectedJob = tblWork.getSelectionModel().getSelectedItem();
        });

        showWork();
    }

    /**
     * Used to show the jobs of the logged in technician
     */
    public void showWork(){
        clmTitleWork.setCellValueFactory(new PropertyValueFactory<User,String>("title"));
        tblWork.setItems(technicianModel.getWorkToBeViewed());
    }

    /**
     * This method is ued to open the documentation window based on the chosen job
     * @param actionEvent
     */
    public void handleOpenDocumentation(ActionEvent actionEvent){
        if(selectedJob == null){
            alertUser("Please select a job");
        } else
        try{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/TechnicianJobWindow.fxml"));
        selectedJob = tblWork.getSelectionModel().getSelectedItem();

            technicianJobModel = TechnicianJobModel.getInstance();
            //Sets the selected job so the documentation window knows what job is chosen.
            documentationModel.setSelectedJob(selectedJob);
            technicianJobModel.setSelectedJob(selectedJob);
            technicianJobModel.showList();

            AnchorPane pane = loader.load();


            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            //This is used to stop the user from clicking any other window while the documentation window is open.
            dialogWindow.initModality(Modality.WINDOW_MODAL);
            dialogWindow.initOwner((((Node)actionEvent.getSource()).getScene().getWindow()));

            dialogWindow.setScene(scene);
            dialogWindow.show();
        }
        catch (IOException e) {
            e.printStackTrace();
            alertUser("Error: Could not open the technician job window");
        }catch (SQLException e){
            e.printStackTrace();
            alertUser("Could not get the documentation list from the database");
        }
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    public void handleFinishJob(ActionEvent event) {
        // Retrieve the selected job from the UI
        Job selectedJob = tblWork.getSelectionModel().getSelectedItem();
        if (selectedJob == null) {
            alertUser("Please select a job to finish");

        } else {
            // Show a confirmation alert
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to finish this job?",
                    "Confirm Finish Job", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                // If the confirm button isn't click then don't finish the job
                return;
            }
            technicianModel.finishJob(selectedJob);
        }
    }


    /**
     * Used to log out of the current logged in user and open the login window again.
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
