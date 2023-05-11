package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class TechnicianJobController {
    public TableColumn clmImages;
    public TableView tblImages;
    public TableColumn clmNotes;
    public TableView tblNotes;


    public void handleAddDocumentation(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/DocumentationWindow.fxml"));
            AnchorPane pane = loader.load();


            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.setScene(scene);
            dialogWindow.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
            alertUser("Could not open the image window");
        }
    }

    public void handleUpdateDocumentation(ActionEvent actionEvent) {
    }

    public void handleDeleteDocumentation(ActionEvent actionEvent) {
    }

    public void handleDeleteImage(ActionEvent actionEvent) {
    }

    public void HandleUpdateImage(ActionEvent actionEvent) {
    }

    public void HandleAddImage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/AddImageWindow.fxml"));
            AnchorPane pane = loader.load();


            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.setScene(scene);
            dialogWindow.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
            alertUser("Could not open the image window");
        }
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

}
