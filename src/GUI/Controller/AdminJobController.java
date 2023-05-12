package GUI.Controller;

import BE.Documentation;
import BE.Job;
import GUI.MODEL.AdminModel;
import GUI.MODEL.TechnicianModel;
import javafx.event.ActionEvent;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminJobController implements Initializable {

    public TableColumn clmTitleWork;
    public TableView<Job> tblWork;
    private AdminModel adminModel;
    public void AdminJobController(){
        try {
            adminModel = AdminModel.getInstance();
        } catch (SQLException e){
            e.printStackTrace();
            alertUser("Could not open the documentation window");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            adminModel = AdminModel.getInstance();
        } catch (SQLException e) {
            alertUser("Was not able to show technician job");
            e.printStackTrace();
        }
        showDocument();
    }



    public void handleOpenDocumentation(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/TechnicianJobWindow.fxml"));
        try {
            AnchorPane pane = loader.load();

            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.setScene(scene);
            dialogWindow.show();
        }
        catch (IOException e) {
            e.printStackTrace();
            alertUser("Error: Could not open the technician job window");
        }
    }

    private void showDocument() {
        clmTitleWork.setCellValueFactory(new PropertyValueFactory<Job, String>("title"));
        tblWork.setItems(adminModel.getWorkToBeViewed());
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    public void handleFinishJob(ActionEvent actionEvent) {
    }

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
