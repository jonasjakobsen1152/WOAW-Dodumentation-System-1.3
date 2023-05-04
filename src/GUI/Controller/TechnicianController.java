package GUI.Controller;

import BE.User;
import GUI.MODEL.CreateUpdateUserModel;
import GUI.MODEL.TechnicianModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TechnicianController implements Initializable {

    public TableColumn clmTitleWork;
    public TableView tblWork;
    private TechnicianModel technicianModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        technicianModel = TechnicianModel.getInstance();
        showWork();
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

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    public void handleFinishJob(ActionEvent actionEvent) {
    }


    public void showWork(){
        clmTitleWork.setCellValueFactory(new PropertyValueFactory<User,String>("title"));

        tblWork.setItems(technicianModel.getWorkToBeViewed());
    }


}
